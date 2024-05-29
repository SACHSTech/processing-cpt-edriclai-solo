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
    float fltPosX;
    float fltPosY;
    float fltDia;
    float fltSpeed;
    // constructor
    public ObjEnemy (float posX, float posY, float dia, float speed) {
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
  int intTimerInput;
  float fltSize;
  float fltSizeStroke;
  float fltGuiX;
  float fltGuiY;
  // inputs
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
    fltSizeStroke = fltSize / 200000;
    fltGuiX = 0 + fltSize / 5000;
    fltGuiY = 0 + fltSize / 5000;
    strInputs = "";
    // general
    background(0, 0, 0);
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
    float posX;
    float posY;
    float dia;
    float speed;
    // initialize
    for (int i = 0; i < numEnemies; i++) {
      posX = random(width);
      posY = 0;
      dia = random(fltSize / 100000, fltSize / 10000);
      speed = random(fltSize / 200000, fltSize / 100000);
      arrListEnemy.add(new ObjEnemy(posX, posY, dia, speed));
    }
  }

  /**
   * called in draw
   * inputs
  */
  private void inputsMain() {
    // displays record
    text(strInputs, fltGuiX, fltGuiY);
    intTimerInput -= 1;
    if (keyPressed && intTimerInput <= 0) {
      // records inputs
      if (boolW) {
        strInputs += 'w';
      }
      if (boolA) {
        strInputs += 'a';
      }
      if (boolS) {
        strInputs += 's';
      }
      if (boolD) {
        strInputs += 'd';
      }
      intTimerInput = 10;
      // when record reaches limit
      if (strInputs.length() >= 3) {
        // input combos
        if (strInputs == "wws") {
          meteor();
        }
        else if (strInputs == "sss") {
          lightning();
        }
        else if (strInputs == "daa") {
          push();
        }
        else if (strInputs == "add") {
          arrows();
        }
        // resets record
        strInputs = "";
        intTimerInput = 20;
      }
    }
  }

  /**
   * called on key pressed
   * keyPressed function
  */
  public void keyPressed() {
    if (key == 'w') {
      boolW = true;
    }
    else if (key == 'a') {
      boolA = true;
    }
    else if (key == 's') {
      boolS = true;
    }
    else if (key == 'd') {
      boolD = true;
    }
  }

  /**
   * called on key released
   * keyReleased function
  */
  public void keyReleased() {
    if (key == 'w') {
      boolW = false;
    }
    else if (key == 'a') {
      boolA = false;
    }
    else if (key == 's') {
      boolS = false;
    }
    else if (key == 'd') {
      boolD = false;
    }
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
  */
  private void lightning() {
    //   
  }

  /**
   * called on command
   * push
  */
  private void push() {
    //
  }

  /**
   * called on command
   * arrows
  */
  private void arrows() {
    //
  }
}