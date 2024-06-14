/*
personal formatting:
global variables: has data type as prefix
local variables: does not have data type as prefix
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
    int intCellPosX;
    int intCellPosY;
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
  int arrGrid[][];
  int arrGamble[];
  float arrArrowsPosX[];
  float arrArrowsPosY[];
  ArrayList<ObjEnemy> arrListEnemy;
  // general
  int intCellSize;
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
  boolean boolIsW;
  boolean boolIsA;
  boolean boolIsS;
  boolean boolIsD;
  // misc
  int intNumArrows;
  boolean boolIsGameStart;
  // images
  PImage imgAbilitiesArrow;
  PImage imgAbilitiesFreeze;

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
    // init data structures
    intCellSize = 40;
    intNumArrows = 50;
    arrGrid = new int[width / intCellSize][height / intCellSize];
    arrGamble = new int[3];
    arrArrowsPosX = new float[intNumArrows];
    arrArrowsPosY = new float[intNumArrows];
    arrListEnemy = new ArrayList<ObjEnemy>();
    // init variables
    intLevel = 1;
    intLives = 10;
    fltSize = width * height;
    fltGuiX = 0 + fltSize / 5000;
    fltGuiY = 0 + fltSize / 5000;
    strInputs = "";
    boolIsGameStart = false;
    // init images
    imgAbilitiesArrow = loadImage("vfx/abilities/arrow.png");
    imgAbilitiesFreeze = loadImage("vfx/abilities/freeze.png");
    imgAbilitiesArrow.resize((int) fltSize / 50000, 0);
    imgAbilitiesFreeze.resize((int) fltSize / 20000, 0);
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
    // game
    if (boolIsGameStart) {
      // game running
      if (intLives > 0) {
        disintegrate();
        noStroke();
        enemyMain();
        obstacleMain();
        statsMain();
        inputsMain();
      }
      // game end
      else {overScreen();}
    }
    // starting screen
    else {startScreen();}
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
      // obstacle interactions
      indivEnemy.intCellPosX = (int) (indivEnemy.fltPosX / intCellSize);
      indivEnemy.intCellPosY = (int) (indivEnemy.fltPosY / intCellSize);
      if (arrGrid[indivEnemy.intCellPosX][indivEnemy.intCellPosY] == 1) {explosion(indivEnemy);}
      if (arrGrid[indivEnemy.intCellPosX][indivEnemy.intCellPosY] == 2) {indivEnemy.fltSpeed *= 1.1;}
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
    centerText(health, dmgEnemy.fltPosX, dmgEnemy.fltPosY - dmgEnemy.fltDia);
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
   * obstacle main code
  */
  private void obstacleMain() {
    // loops through all cells
    for (int row = 0; row < arrGrid.length; row++) {
      for (int column = 0; column < arrGrid[0].length; column++) {
        // cell status: 1 = wall
        if (arrGrid[row][column] == 1) {
          fill(255, 255, 0);
          rect(row * intCellSize, column * intCellSize, intCellSize / 2, intCellSize / 2);
        }
        // cell status: 2 = speed
        if (arrGrid[row][column] == 2) {
          fill(0, 255, 255);
          rect(row * intCellSize, column * intCellSize, intCellSize / 2, intCellSize / 2);
        }
      }
    }
  }

  /**
   * called in draw
   * stats main code
  */
  private void statsMain(){
    // displays
    fill(255, 255, 255);
    text("Level: " + intLevel, fltGuiX, fltGuiY * 1.5f);
    text("Lives: " + intLives, fltGuiX, fltGuiY * 2.0f);
    text("Mana: " + intMana, fltGuiX, fltGuiY * 2.5f);
    // mana regeneration
    if (intMana < 200 && mousePressed) {intMana += 1;}
    // when 0 enemies
    if (arrListEnemy.size() <= 0) {
      // new enemies
      intLevel += 1;
      for (int i = 0; i < (random(30)); i++) {
        arrListEnemy.add(new ObjEnemy());
      }
      // new obstacles
      for (int row = 0; row < arrGrid.length; row++) {
        for (int column = 0; column < arrGrid[0].length; column++) {
          arrGrid[row][column] = 0;
        }
      }
      for (int i = 0; i < random(10); i++) {
        arrGrid[(int)random(arrGrid.length)][(int)random(arrGrid[0].length)] = 1;
        arrGrid[(int)random(arrGrid.length)][(int)random(arrGrid[0].length)] = 2;
      }
    }
  }

  /**
   * called in draw
   * inputs main code
  */
  private void inputsMain() {
    // general
    intDelayTimer -= 1;
    intInputTimer -= 1;
    text(strInputs, fltGuiX, fltGuiY);
    // records inputs
    if (strInputs.length() < 3 && keyPressed && intInputTimer <= 0) {
      if (boolIsW) {strInputs += 'w';}
      if (boolIsA) {strInputs += 'a';}
      if (boolIsS) {strInputs += 's';}
      if (boolIsD) {strInputs += 'd';}
      intInputTimer = 10;
    }
    // when record reaches limit
    if (strInputs.length() >= 3) {
      // input combo (add): gamble
      if (strInputs.equals("add") && intMana >= 100) {
        // runs once
        if (intDelayTimer < 0) {
          intManaConsum = 100;
          intDelayTimer = 60;
          // resets slots
          for (int i = 0; i < arrGamble.length; i++) {
            arrGamble[i] = 0;
          }
        }
        // rolls slot machine
        if (intDelayTimer % 20 == 0) {
          gamble();
        }
      }
      // input combo (ddd): freeze
      else if (strInputs.equals("ddd") && intMana >= 150) {
        // runs once
        if (intDelayTimer < 0) {
          intManaConsum = 150;
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
          indivEnemy.intHealth -= 1000;
          damageIndic(indivEnemy);
        }
      }
      // input combo (wws): arrows
      else if (strInputs.equals("wws") && intMana >= 100) {
        // runs once
        if (intDelayTimer < 0) {
          intManaConsum = 100;
          intDelayTimer = 100;
          // resets arrow positions
          for (int i = 0; i < intNumArrows; i++) {
            arrArrowsPosX[i] = random(width);
            arrArrowsPosY[i] = -random(height);
          }
        }
        // arrows
        arrows();
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
   * arrows
  */
  private void arrows() {
    // local variables
    int i = 0;
    float arrowSpeed = fltSize / 20000;
    // using while loops
    while(i < intNumArrows) {
      // vfx
      centerImage(imgAbilitiesArrow, arrArrowsPosX[i], arrArrowsPosY[i]);
      // movement
      arrArrowsPosY[i] += arrowSpeed;
      if (arrArrowsPosY[i] >= width) {
        arrArrowsPosX[i] = random(width);
        arrArrowsPosY[i] = -random(height);
      }
      // collision
      for (int i2 = 0; i2 < arrListEnemy.size(); i2++) {
        ObjEnemy indivEnemy = arrListEnemy.get(i2);
        if (dist(arrArrowsPosX[i], arrArrowsPosY[i], indivEnemy.fltPosX, indivEnemy.fltPosY) < indivEnemy.fltDia * 2) {
          indivEnemy.intHealth -= 5;
          damageIndic(indivEnemy);
        }
      }
      // iterations
      i += 1;
    }
  }

  /**
   * called on command
   * gamble
  */
  private void gamble() {
    // local variables
    int index = 0;
    String slots = "X | X | X";
    // when all slots filled
    if (arrGamble[2] != 0) {
      // 3 of a kind
      if (arrGamble[0] == arrGamble[1] && arrGamble[1] == arrGamble[2]) {
        // damages all enemies
        for (int i = 0; i < arrListEnemy.size(); i++) {
          ObjEnemy indivEnemy = arrListEnemy.get(i);
          indivEnemy.intHealth -= 5000;
        }
        // win vfx
        fill(255, 255, 0);
        slots = "$ | $ | $";
      }
    }
    // using for each loops
    for(int currSlot : arrGamble) {
      // updates slot value
      if (currSlot == 0) {
        arrGamble[index] = (int) random(6, 8);
        slots = arrGamble[0] + " | " + arrGamble[1] + " | " + arrGamble[2];
        break;
      }
      // index
      index += 1;
    }
    // roll vfx
    centerText(slots, width / 2, height / 2);
  }

  /**
   * called on command
   * freeze
   * @param target  freezes target
  */
  private void freeze(ObjEnemy target) {
    // vfx
    float posX = target.fltPosX + random(-target.fltDia / 2, target.fltDia / 2);
    float posY = target.fltPosY + random(-target.fltDia / 2, target.fltDia / 2);
    centerImage(imgAbilitiesFreeze, posX, posY);
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
   * @param strikePosX  x coord where lightning lands
   * @param strikePosY  y coord where lightning lands
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
   * called in draw
   * starting screen
  */
  private void startScreen() {
    // local variables
    String startScreen = "Press any key to start";
    String info = "combos: add, ddd, sss, wws";
    // display
    clear();
    centerText(startScreen, width / 2, height / 2.2f);
    centerText(info, width / 2, height / 1.8f);
    // start conditions
    if (keyPressed) {boolIsGameStart = true;}
  }

  /**
   * called in draw
   * game over screen
  */
  private void overScreen() {
    String endScreen = "Game Over";
    clear();
    centerText(endScreen, width / 2, height / 2);
  }

  /**
   * called on command
   * centers text
   * @param text  text that needs to be centered
   * @param posX  x position of text on screen
   * @param posY  y position of text on screen
  */
  private void centerText(String text, float posX, float posY) {
    text(text, posX - (textWidth(text) / 2), posY);
  }

  /**
   * called on command
   * centers image
   * @param image image that needs to be centered
   * @param posX  x position of image on screen
   * @param posY  y position of image on screen
  */
  private void centerImage(PImage image, float posX, float posY) {
    image(image, posX - image.width / 2, posY - image.height / 2);
  }

  /**
   * called on key pressed
   * keyPressed function
  */
  public void keyPressed() {
    if (key == 'w') {boolIsW = true;}
    else if (key == 'a') {boolIsA = true;}
    else if (key == 's') {boolIsS = true;}
    else if (key == 'd') {boolIsD = true;}
  }

  /**
   * called on key released
   * keyReleased function
  */
  public void keyReleased() {
    if (key == 'w') {boolIsW = false;}
    else if (key == 'a') {boolIsA = false;}
    else if (key == 's') {boolIsS = false;}
    else if (key == 'd') {boolIsD = false;}
  }
}