/*
 * personal formatting:
 * global variables: has data type as prefix
 * local variables: does not have data type as prefix
*/
import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

/**
 * Processing CPT
 * @author EdricLai
*/
public class Sketch extends PApplet {
  /**
   * enemy object
   * class
  */
  class ObjEnemy {
    // variables
    double dblVector;
    double dblDistX;
    double dblDistY;
    double dblDx;
    double dblDy;
    float fltPosX;
    float fltPosY;
    float fltDia;
    float fltSpeed;
    float fltOrigSpeed;
    int intHealth;
    // constructor
    public ObjEnemy() {
      this.fltPosX = random(width);
      this.fltPosY = 0;
      this.fltDia = random(fltSize / 50000, fltSize / 10000);
      this.fltSpeed = random(fltSize / 500000, fltSize / 50000);
      this.fltOrigSpeed = fltSpeed;
      this.intHealth = (int) (fltDia * intLevel);
    }
  }

  /*
   * global variables
   * and data structures
  */
  // data structures
  ArrayList<ObjEnemy> arrListEnemy = new ArrayList<ObjEnemy>();
  // general
  int intLevel;
  int intLives;
  int intMana;
  int intManaConsum;
  float fltSize;
  float fltGuiX;
  float fltGuiY;
  // inputs
  int intDelayTimer;
  int intInputTimer;
  String strInputs;
  boolean boolW;
  boolean boolA;
  boolean boolS;
  boolean boolD;
  
  /**
   * called once
   * settings function
   */
  public void settings() {
    size(600, 600);
  }

  /**
   * called once
   * setup functions
   */
  public void setup() {
    // initialize variables
    intLevel = 1;
    intLives = 10;
    fltSize = width * height;
    fltGuiX = 0 + fltSize / 5000;
    fltGuiY = 0 + fltSize / 5000;
    strInputs = "";
    // general
    background(0, 0, 0);
    strokeWeight(fltSize / 200000);
    textSize(fltSize / 20000);
  }

  /**
   * called repeatedly
   * draw function
   */
  public void draw() {
    // game running
    if (intLives > 0) {
      disintegrate();
      noStroke();
      enemyMain();
      statsMain();
      inputsMain();
    }
    // game end
    else {
      String endScreen = "Game Over";
      text(endScreen, (width / 2) - (textWidth(endScreen) / 2), height / 2);
    }
  }

  /**
   * called in draw
   * enemy main code
  */
  private void enemyMain() {
    for (int i = 0; i < arrListEnemy.size(); i++) {
      ObjEnemy indivEnemy = arrListEnemy.get(i);
      // vfx
      fill(255, 255, 255);
      ellipse(indivEnemy.fltPosX, indivEnemy.fltPosY, indivEnemy.fltDia, indivEnemy.fltDia);
      // collision
      if (indivEnemy.intHealth <= 0) {explosion(indivEnemy);}
      if (dist(mouseX, mouseY, indivEnemy.fltPosX, indivEnemy.fltPosY) < indivEnemy.fltDia) {
        explosion(indivEnemy);
        intLives -= 1;
      }
      // movement: get distance
      indivEnemy.dblDistX = mouseX - indivEnemy.fltPosX;
      indivEnemy.dblDistY = mouseY - indivEnemy.fltPosY;
      // movement: get vector
      indivEnemy.dblVector = Math.sqrt(Math.pow(indivEnemy.dblDistX, 2) + Math.pow(indivEnemy.dblDistY, 2));
      indivEnemy.dblDx = (indivEnemy.fltSpeed / indivEnemy.dblVector) * indivEnemy.dblDistX;
      indivEnemy.dblDy = (indivEnemy.fltSpeed / indivEnemy.dblVector) * indivEnemy.dblDistY;
      // movement: apply vector
      indivEnemy.fltPosX += indivEnemy.dblDx;
      indivEnemy.fltPosY += indivEnemy.dblDy;
    }
  }

  /**
   * called on command
   * damage indicator
   * @param dmgEnemy    damaged enemy
  */
  private void damageIndic(ObjEnemy dmgEnemy) {
    // local variables
    String health = Integer.toString(dmgEnemy.intHealth);
    // display health
    fill(255, 0, 0);
    text(health, dmgEnemy.fltPosX - (textWidth(health) / 2), dmgEnemy.fltPosY - dmgEnemy.fltDia);
  }

  /**
   * called on command
   * explosion
   * @param explEnemy   exploded enemy
  */
  private void explosion(ObjEnemy explEnemy) {
    // vfx
    fill(255, 0, 0);
    ellipse(explEnemy.fltPosX, explEnemy.fltPosY, explEnemy.fltDia * 3, explEnemy.fltDia * 3);
    // collateral damage
    for (int i = 0; i < arrListEnemy.size(); i++) {
      ObjEnemy indivEnemy = arrListEnemy.get(i);
      if (dist(indivEnemy.fltPosX, indivEnemy.fltPosY, explEnemy.fltPosX, explEnemy.fltPosY) < indivEnemy.fltDia + explEnemy.fltDia * 3) {
        indivEnemy.intHealth -= 20;
        damageIndic(indivEnemy);
      }
    }
    // remove from list
    arrListEnemy.remove(explEnemy);
  }

  /**
   * called in draw
   * stats main code
  */
  private void statsMain(){
    // displays
    text("Level: " + intLevel, fltGuiX, fltGuiY * 1.5f);
    text("Lives: " + intLives, fltGuiX, fltGuiY * 2.0f);
    text("Mana: " + intMana, fltGuiX, fltGuiY * 2.5f);
    // mana regeneration
    if (intMana < 200) {intMana += 1;}
    // when 0 enemies
    if (arrListEnemy.size() <= 0) {
      // creates new level
      intLevel += 1;
      for (int i = 0; i < (random(10) * intLevel); i++) {
        arrListEnemy.add(new ObjEnemy());
      }
    }
  }

  /**
   * called in draw
   * inputs
  */
  private void inputsMain() {
    // general
    intDelayTimer -= 1;
    intInputTimer -= 1;
    text(strInputs, fltGuiX, fltGuiY);
    // records inputs
    if (strInputs.length() < 3 && keyPressed && intInputTimer <= 0) {
      if (boolW) {strInputs += 'w';}
      if (boolA) {strInputs += 'a';}
      if (boolS) {strInputs += 's';}
      if (boolD) {strInputs += 'd';}
      intInputTimer = 10;
    }
    // when record reaches limit
    if (strInputs.length() >= 3) {
      // input combo (sdd): freeze
      if (strInputs.equals("sdd") && intMana >= 200) {
        // runs once
        if (intDelayTimer < 0) {
          intManaConsum = 200;
          intDelayTimer = 100;
        }
        // freezes targets
        for (int i = 0; i < arrListEnemy.size(); i++) {
          ObjEnemy indivEnemy = arrListEnemy.get(i);
          freeze(indivEnemy);
        }
      }
      // input combo (sss): lightning
      else if (strInputs.equals("sss") && intMana >= 100) {
        // runs once
        if (intDelayTimer < 0) {
          intManaConsum = 100;
          intDelayTimer = 40;
        }
        // strikes 3 times
        if (intDelayTimer % 20 == 0) {
          // local variables
          int highestHealth = 0;
          int targetIndex = 0;
          // finds highest hp
          for (int i = 0; i < arrListEnemy.size(); i++) {
            ObjEnemy indivEnemy = arrListEnemy.get(i);
            if (highestHealth < indivEnemy.intHealth) {
              highestHealth = indivEnemy.intHealth;
              targetIndex = i;
            }
          }
          // strikes target
          ObjEnemy indivEnemy = arrListEnemy.get(targetIndex);
          lightning(indivEnemy.fltPosX, indivEnemy.fltPosY);
          indivEnemy.intHealth -= 200;
          damageIndic(indivEnemy);
        }
      }
      // invalid input combo
      else {
        text("INVALID", fltGuiX, fltGuiY);
        intManaConsum = 0;
      }
      // resets record
      if (intDelayTimer <= 0) {
        intMana -= intManaConsum;
        strInputs = "";
      }
    }
  }

  /**
   * called on command
   * freeze
   * @param target  freezes target
  */
  private void freeze(ObjEnemy target) {
    // vfx
    for (int i = 0; i < 10; i++) {
      // local variables
      float posX = target.fltPosX + random(-target.fltDia / 2, target.fltDia / 2);
      float posY = target.fltPosY + random(-target.fltDia / 2, target.fltDia / 2);
      // particles
      fill(0, 255, 255);
      ellipse(posX, posY, target.fltDia / 10, target.fltDia / 10);
    }
    // slows movement
    target.fltSpeed /= 1.05;
    // restores speed after duration
    if (intDelayTimer <= 0) {
      target.fltOrigSpeed /= 2;
      target.fltSpeed = target.fltOrigSpeed;
    }
  }

  /**
   * called on command
   * lightning
   * @param strikePosX  X coord where lightning lands
   * @param strikePosY  Y coord where lightning lands
  */
  private void lightning(float strikePosX, float strikePosY) {
    // local variables
    float rposX = strikePosX;
    float rposY = 0;
    float rposX2 = 0;
    float rposY2 = 0;
    float bposX = 0;
    float bposY = 0;
    float bposX2 = 0;
    float bposY2 = 0;
    float space = fltSize / 10000;
    // 1-5 main roots
    for (int roots = 0; roots < random(1, 5); roots++) {
      rposX2 = rposX + random(-space, space);
      rposY2 = rposY + random(strikePosY / 3, strikePosY / 2);
      stroke(0, 255, 255);
      line(rposX, rposY, rposX2, rposY2);
      rposX = rposX2;
      rposY = rposY2;
      // 1-3 branches
      for (int branches = 0; branches < random(1, 3); branches++) {
        bposX = rposX;
        bposY = rposY;
        // extending branch
        for (int extensions = 0; extensions < random(1, 5); extensions++) {
          bposX2 = bposX + random(-space, space);
          bposY2 = bposY + random(-space, space);
          line(bposX, bposY, bposX2, bposY2);
          bposX = bposX2;
          bposY = bposY2;
        }
      }
    }
  }

  /**
   * called in draw
   * disintegration effect
  */
  private void disintegrate() {
    stroke(0, 0, 0);
    for (int i = 0; i <= 50; i++) {
      line(random(width), random(height), random(width), random(height));
    }
  }

  /**
   * called on key pressed
   * keyPressed function
  */
  public void keyPressed() {
    if (key == 'w') {boolW = true;}
    else if (key == 'a') {boolA = true;}
    else if (key == 's') {boolS = true;}
    else if (key == 'd') {boolD = true;}
  }

  /**
   * called on key released
   * keyReleased function
  */
  public void keyReleased() {
    if (key == 'w') {boolW = false;}
    else if (key == 'a') {boolA = false;}
    else if (key == 's') {boolS = false;}
    else if (key == 'd') {boolD = false;}
  }
}