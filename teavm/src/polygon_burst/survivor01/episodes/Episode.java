package polygon_burst.survivor01.episodes;

import polygon_burst.survivor01.episodes.S01E01Episode;
import polygon_burst.survivor01.logic.ChimeraLogic;
import polygon_burst.survivor01.shapes.ChimeraShape;
import polygon_burst.survivor01.logic.BulletLogic;
import polygon_burst.survivor01.shapes.BulletShape;
import polygon_burst.survivor01.sections.Section;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import java.util.ArrayDeque;

abstract public class Episode extends Section {
  static final public float PROJECTION_Y = 400.0F, VEIL_ALPHA = 0.1F;
  static final public int MAX_BULLETS = 1024, COUNTER_MILLIS = 300000, COUNTER_DIGITS = 3;
  static public S01E01Episode s01e01;    
  static protected ArrayDeque<BulletLogic> bullets = new ArrayDeque<>(MAX_BULLETS);
  static protected ChimeraLogic chimeraLogic;
  static protected ChimeraShape chimeraShape;  
  static protected StringBuffer counterBuffer = new StringBuffer(COUNTER_DIGITS);
  static protected Color counterColor = new Color(0xffffffff);
  static protected int counterMillis;
  
  static public void initialize() {s01e01 = new S01E01Episode();}
  
  static protected void drawCounter() {
    counterMillis = COUNTER_MILLIS - (int)(elapsedTime % 300.0F * 1000.0F);
    counterColor.r = 0.5F + Math.max(0.0F , 1.0F - counterMillis * 2.0F / COUNTER_MILLIS) * 0.5F;
    counterColor.g = 0.5F + (counterMillis < COUNTER_MILLIS / 2 ? 
      counterMillis * 2.0F / COUNTER_MILLIS : 2.0F - counterMillis * 2.0F / COUNTER_MILLIS) * 0.5F;
    counterColor.b = 0.5F + Math.max(0.0F, counterMillis * 2.0F / COUNTER_MILLIS - 1.0F) * 0.5F;
    setFontColor(labelFont, counterColor);    
    labelFont.draw(batch, ":", WIDTH / 2.0F - 256.0F, HEIGHT - 70.0F, 512.0F, Align.center, false);      
    counterBuffer.setLength(0);
    counterBuffer.insert(0, counterMillis / 1000);    
    while (counterBuffer.length() < COUNTER_DIGITS) counterBuffer.insert(0, '0');
    labelFont.draw(batch, counterBuffer, WIDTH / 2.0F - 528.0F, HEIGHT - 80.0F, 512.0F, Align.right, false);       
    counterBuffer.setLength(0);
    counterBuffer.insert(0, counterMillis % 1000);    
    while (counterBuffer.length() < COUNTER_DIGITS) counterBuffer.insert(0, '0');
    labelFont.draw(batch, counterBuffer, WIDTH / 2.0F + 16.0F, HEIGHT - 80.0F, 512.0F, Align.left, false);
  }
  
  static protected void addBullet(int type, int owner, float x, float y, float rotation) { 
    BulletLogic bulletLogic = bullets.size() == MAX_BULLETS ? bullets.pollFirst() : BulletLogic.pop();
    bulletLogic.type = type;
    bulletLogic.owner = owner;
    bulletLogic.position.x = x;
    bulletLogic.position.y = y;
    bulletLogic.rotation = rotation; 
    bullets.addLast(bulletLogic);
  }
  
  static protected void updateBullets() {
  
  }
  
  static protected void drawBullets() {for (BulletLogic bulletLogic : bullets) BulletShape.draw(bulletLogic);}
}
