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
    int intHealth;
    float fltPosX;
    float fltPosY;
    float fltDia;
    float fltSpeed;
    // constructor
    public ObjEnemy (int health, float posX, float posY, float dia, float speed) {
      this.intHealth = health;
      this.fltPosX = posX;
      this.fltPosY = posY;
      this.fltDia = dia;
      this.fltSpeed = speed;
    }
  }

  /*
   * global variables
   * and data structures
  */
  // data structures
  ArrayList<ObjEnemy> arrListEnemy = new ArrayList<ObjEnemy>();
  // general
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
    size(1000, 600);
  }

  /** 
   * called once
   * setup functions
   */
  public void setup() {
    // initialize variables
    fltSize = width * height;
    fltGuiX = 0 + fltSize / 5000;
    fltGuiY = 0 + fltSize / 5000;
    strInputs = "";
    // general
    background(0, 0, 0);
    strokeWeight(fltSize / 200000);
    textSize(fltSize / 20000);
    // tests
    enemyInit();
  }

  /**
   * called repeatedly
   * draw function
   */
  public void draw() {
    clear();
    noStroke();
    enemyMain();
    inputsMain();
  }

  /**
   * called in draw
   * enemy main code
  */
  private void enemyMain() {
    for (int i = 0; i < arrListEnemy.size(); i++) {
      // local variables
      ObjEnemy indivEnemy = arrListEnemy.get(i);
      // sprite
      ellipse(indivEnemy.fltPosX, indivEnemy.fltPosY, indivEnemy.fltDia, indivEnemy.fltDia);
      // movement
      indivEnemy.fltPosY += indivEnemy.fltSpeed;
    }
  }

  /**
   * called on command
   * enemy initialize
  */
  private void enemyInit() {
    // local variables
    float numEnemies = random(1, 10);
    int health;
    float posX;
    float posY;
    float dia;
    float speed;
    // initialize
    for (int i = 0; i < numEnemies; i++) {
      health = 20;
      posX = random(width);
      posY = 0;
      dia = random(fltSize / 100000, fltSize / 10000);
      speed = random(fltSize / 200000, fltSize / 100000);
      arrListEnemy.add(new ObjEnemy(health, posX, posY, dia, speed));
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
      // input combos
      if (strInputs.equals("wws")) {
        meteor();
      }
      else if (strInputs.equals("sss")) {
        if (intDelayTimer < 0) {intDelayTimer = 40;}
        if (intDelayTimer % 20 == 0) {
          lightning(mouseX, mouseY);
        }
      }
      // resets record
      if (intDelayTimer <= 0) {
        strInputs = "";
      }
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

  /**
   * called on command
   * meteor
  */
  private void meteor() {
    //
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
      bposX = rposX;
      bposY = rposY;
      // 1-5 branches
      for (int branches = 0; branches < random(1, 5); branches++) {
        bposX2 = bposX + random(-space, space);
        bposY2 = bposY + random(-space, space);
        line(bposX, bposY, bposX2, bposY2);
        bposX = bposX2;
        bposY = bposY2;
      }
    }
  }
}