package polygon_burst.survivor01.episodes;

import polygon_burst.survivor01.episodes.Episode;
import polygon_burst.survivor01.menus.Menu;
import polygon_burst.survivor01.logic.ChimeraLogic;
import polygon_burst.survivor01.shapes.ChimeraShape;
import polygon_burst.survivor01.logic.BulletLogic;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import java.util.Iterator;

public class S01E01Episode extends Episode {
  static final public Vector2 buttonPosition = new Vector2(WIDTH - 220.0F, 80.0F);
  static final public float SIZE = 100.0F; 
  final protected TextureAtlas.AtlasRegion logo;  
  final protected Menu.IconButton menuButton;
  final protected Vector2 axes = new Vector2(), vector1 = new Vector2(40.0F, 320.0F), 
    vector2 = new Vector2(320.0F, 40.0F), vector3 = new Vector2(WIDTH - 40.0F, HEIGHT - 320.0F), 
    vector4 = new Vector2(WIDTH - 320.0F, HEIGHT - 40.0F), speed1 = new Vector2(-600.0F, 300.0F), 
    speed2 = new Vector2(-600.0F, 300.0F), speed3 = new Vector2(520.0F, -240.0F), speed4 = new Vector2(520.0F, -240.0F);  
  protected float fastTime, normalTime, heavyTime, respawnTime, radians, rotation1, rotation2, rotation3, rotation4;  
  protected int hp1 = 200, hp2 = 200, hp3 = 200, hp4 = 200;
  protected boolean blink1, blink2, blink3, blink4, moving;
  
  public S01E01Episode() {
    chimeraLogic = ChimeraLogic.pop(WIDTH / 2.0F, HEIGHT / 2.0F);
    logo = atlas.findRegion("logo");       
    menuButton = new Menu.IconButton(atlas.findRegion("gear"), 120.0F, 120.0F, 10.0F, 20.0F, 10.0F, 20.0F);
  }

  @Override public void update() {     
    if (control.buttons[0]) {      
      chimeraLogic.gravityRadius = Math.min(800.0F, chimeraLogic.gravityRadius + 80.0F);    
      audio.playSound("option_select", 0.0F);
    } else if (control.buttons[1]) {
      chimeraLogic.gravityRadius = Math.max(160.0F, chimeraLogic.gravityRadius - 80.0F);  
      audio.playSound("option_select", 0.0F);
    } if (control.buttons[2]) {      
      chimeraLogic.gravity = !chimeraLogic.gravity;
      audio.playSound("option_select", 0.0F);
    } if (control.buttons[3]) {
      hp1 = hp2 = hp3 = hp4 = 200;
      chimeraLogic.lives = 500;
      respawnTime = 0.0F;
      audio.playSound("option_select", 0.0F);
    } else if (control.buttons[4] || control.isClicked(menuButton.polygon, true)) {
      audio.playSound("option_select", 0.8F);
      setMenu(Menu.main);
    }
        
    /*if (control.axes.x > 0.25F || control.axes.x < -0.25F || control.axes.y > 0.25F || control.axes.y < -0.25F) {
      // handle throttle particles
    }*/
    
    if (hp1 == 0 && hp2 == 0 && chimeraLogic.lives == 0 
      || hp1 == 0 && hp2 == 0 && hp3 == 0 && hp4 == 0 || chimeraLogic.lives == 0 && hp3 == 0 && hp4 == 0) {
      if (respawnTime == 0.0F) respawnTime = frameTime;
      else respawnTime += frameTime;
      if (respawnTime >= 3.0F) {
        hp1 = hp2 = hp3 = hp4 = 200;
        chimeraLogic.lives = 500;
        respawnTime = 0.0F;
      }
    }   
    
    chimeraLogic.update(frameTime, control.axisX, control.axisY);
    
    vector1.x += frameTime * speed1.x;
    vector1.y += frameTime * speed1.y;
    if (speed1.x < 0.0F && vector1.x < SIZE / 2.0F + 20.0F 
      || speed1.x > 0.0F && vector1.x > WIDTH - SIZE / 2.0F - 20.0F) 
      speed1.x *= -1.0F;
    if (speed1.y < 0.0F && vector1.y < SIZE / 2.0F + 20.0F 
      || speed1.y > 0.0F && vector1.y > HEIGHT - SIZE / 2.0F - 20.0F) 
      speed1.y *= -1.0F;   
    //rotation1 = Vector2.angleRad(vector1);  
    
    vector2.x += frameTime * speed2.x;
    vector2.y += frameTime * speed2.y;
    if (speed2.x < 0.0F && vector2.x < SIZE / 2.0F + 20.0F 
      || speed2.x > 0.0F && vector2.x > WIDTH - SIZE / 2.0F - 20.0F) 
      speed2.x *= -1.0F;
    if (speed2.y < 0.0F && vector2.y < SIZE / 2.0F + 20.0F 
      || speed2.y > 0.0F && vector2.y > HEIGHT - SIZE / 2.0F - 20.0F) 
      speed2.y *= -1.0F;      
             
    vector3.x += frameTime * speed3.x;
    vector3.y += frameTime * speed3.y;   
    if (speed3.x < 0.0F && vector3.x < SIZE / 2.0F + 20.0F 
      || speed3.x > 0.0F && vector3.x > WIDTH - SIZE / 2.0F - 20.0F) 
      speed3.x *= -1.0F;
    if (speed3.y < 0.0F && vector3.y < SIZE / 2.0F + 20.0F 
      || speed3.y > 0.0F && vector3.y > HEIGHT - SIZE / 2.0F - 20.0F) 
      speed3.y *= -1.0F;  
      
    vector4.x += frameTime * speed4.x;
    vector4.y += frameTime * speed4.y;   
    if (speed4.x < 0.0F && vector4.x < SIZE / 2.0F + 20.0F 
      || speed4.x > 0.0F && vector4.x > WIDTH - SIZE / 2.0F - 20.0F) 
      speed4.x *= -1.0F;
    if (speed4.y < 0.0F && vector4.y < SIZE / 2.0F + 20.0F 
      || speed4.y > 0.0F && vector4.y > HEIGHT - SIZE / 2.0F - 20.0F) 
      speed4.y *= -1.0F;  
    
    fastTime += frameTime;
    normalTime += frameTime;
    heavyTime += frameTime;   
    Vector2 closestVector;       
    if (hp1 != 0) {    
      closestVector = chimeraLogic.lives == 0 ? null : chimeraLogic.position;      
      if (closestVector == null || hp3 != 0 && vector1.dst(closestVector) > vector1.dst(vector3)) closestVector = vector3;
      if (closestVector == null || hp4 != 0 && vector1.dst(closestVector) > vector1.dst(vector4)) closestVector = vector4;      
      if (closestVector != null) {      
        if (heavyTime > 0.5F) 
          addBullet(BulletLogic.TYPE_HEAVY, BulletLogic.OWNER_EAST, vector1.x, vector1.y, 
            Vector2.angleRad(closestVector.x - vector1.x + (closestVector == chimeraLogic.position ? 0.0F : speed3.x), 
              closestVector.y - vector1.y + (closestVector == chimeraLogic.position ? 0.0F : speed3.y)));                 
        else if (normalTime > 0.25F)
          addBullet(BulletLogic.TYPE_NORMAL, BulletLogic.OWNER_EAST, vector1.x, vector1.y, 
            Vector2.angleRad(closestVector.x - vector1.x + (closestVector == chimeraLogic.position ? 0.0F : speed3.x), 
            closestVector.y - vector1.y + (closestVector == chimeraLogic.position ? 0.0F : speed3.y)));
        else if (fastTime > 0.12F)
          addBullet(BulletLogic.TYPE_FAST, BulletLogic.OWNER_EAST, vector1.x, vector1.y, 
            Vector2.angleRad(closestVector.x - vector1.x + (closestVector == chimeraLogic.position ? 0.0F : speed3.x), 
              closestVector.y - vector1.y + (closestVector == chimeraLogic.position ? 0.0F : speed3.y)));
      }
    } 
    
    if (hp2 != 0) {    
      closestVector = chimeraLogic.lives == 0 ? null : chimeraLogic.position;      
      if (closestVector == null || hp3 != 0 && vector1.dst(closestVector) > vector1.dst(vector3)) closestVector = vector3;
      if (closestVector == null || hp4 != 0 && vector1.dst(closestVector) > vector1.dst(vector4)) closestVector = vector4;      
      if (closestVector != null) {      
        if (heavyTime > 0.5F)
          addBullet(BulletLogic.TYPE_HEAVY, BulletLogic.OWNER_EAST, vector2.x, vector2.y, 
            Vector2.angleRad(closestVector.x - vector2.x + (closestVector == chimeraLogic.position ? 0.0F : speed3.x), 
              closestVector.y - vector2.y + (closestVector == chimeraLogic.position ? 0.0F : speed3.y)));
        else if (normalTime > 0.25F)
         addBullet(BulletLogic.TYPE_NORMAL, BulletLogic.OWNER_EAST, vector2.x, vector2.y, 
            Vector2.angleRad(closestVector.x - vector2.x + (closestVector == chimeraLogic.position ? 0.0F : speed3.x), 
            closestVector.y - vector2.y + (closestVector == chimeraLogic.position ? 0.0F : speed3.y)));
        else if (fastTime > 0.12F)
          addBullet(BulletLogic.TYPE_FAST, BulletLogic.OWNER_EAST, vector2.x, vector2.y, 
            Vector2.angleRad(closestVector.x - vector2.x + (closestVector == chimeraLogic.position ? 0.0F : speed3.x), 
              closestVector.y - vector2.y + (closestVector == chimeraLogic.position ? 0.0F : speed3.y)));
      }
    }
        
    if (hp3 != 0) {    
      closestVector = chimeraLogic.lives == 0 ? null : chimeraLogic.position;      
      if (closestVector == null || hp1 != 0 && vector3.dst(closestVector) > vector3.dst(vector1)) closestVector = vector1;
      if (closestVector == null || hp2 != 0 && vector3.dst(closestVector) > vector3.dst(vector2)) closestVector = vector2;      
      if (closestVector != null) {
        if (heavyTime > 0.5F)
          addBullet(BulletLogic.TYPE_HEAVY, BulletLogic.OWNER_WEST, vector3.x, vector3.y, 
            Vector2.angleRad(closestVector.x - vector3.x + (closestVector == chimeraLogic.position ? 0.0F : speed1.x), 
              closestVector.y - vector3.y + (closestVector == chimeraLogic.position ? 0.0F : speed1.y)));             
        else if (normalTime > 0.25F)
          addBullet(BulletLogic.TYPE_NORMAL, BulletLogic.OWNER_WEST, vector3.x, vector3.y, 
            Vector2.angleRad(closestVector.x - vector3.x + (closestVector == chimeraLogic.position ? 0.0F : speed1.x), 
              closestVector.y - vector3.y + (closestVector == chimeraLogic.position ? 0.0F : speed1.y)));  
        else if (fastTime > 0.12F)
          addBullet(BulletLogic.TYPE_FAST, BulletLogic.OWNER_WEST, vector3.x, vector3.y, 
            Vector2.angleRad(closestVector.x - vector3.x + (closestVector == chimeraLogic.position ? 0.0F : speed1.x), 
              closestVector.y - vector3.y + (closestVector == chimeraLogic.position ? 0.0F : speed1.y))); 
      }
    }    
    
    if (hp4 != 0) {    
      closestVector = chimeraLogic.lives == 0 ? null : chimeraLogic.position;      
      if (closestVector == null || hp1 != 0 && vector3.dst(closestVector) > vector3.dst(vector1)) closestVector = vector1;
      if (closestVector == null || hp2 != 0 && vector3.dst(closestVector) > vector3.dst(vector2)) closestVector = vector2;      
      if (closestVector != null) {
        if (heavyTime > 0.5F)
          addBullet(BulletLogic.TYPE_HEAVY, BulletLogic.OWNER_WEST, vector4.x, vector4.y, 
            Vector2.angleRad(closestVector.x - vector4.x + (closestVector == chimeraLogic.position ? 0.0F : speed1.x), 
              closestVector.y - vector4.y + (closestVector == chimeraLogic.position ? 0.0F : speed1.y)));             
        else if (normalTime > 0.25F)
          addBullet(BulletLogic.TYPE_NORMAL, BulletLogic.OWNER_WEST, vector4.x, vector4.y, 
            Vector2.angleRad(closestVector.x - vector4.x + (closestVector == chimeraLogic.position ? 0.0F : speed1.x), 
              closestVector.y - vector4.y + (closestVector == chimeraLogic.position ? 0.0F : speed1.y)));  
        else if (fastTime > 0.12F)
          addBullet(BulletLogic.TYPE_FAST, BulletLogic.OWNER_WEST, vector4.x, vector4.y, 
            Vector2.angleRad(closestVector.x - vector4.x + (closestVector == chimeraLogic.position ? 0.0F : speed1.x), 
              closestVector.y - vector4.y + (closestVector == chimeraLogic.position ? 0.0F : speed1.y))); 
      }
    }    
    
    if (fastTime > 0.12F) fastTime %= 0.125F;
    if (normalTime > 0.25F) normalTime %= 0.25F;
    if (heavyTime > 0.5F) heavyTime %= 0.5F;  
    
    chimeraLogic.blink = blink1 = blink2 = blink3 = blink4 = false;
    BulletLogic bullet;
    float bulletSpeed, previousDistance, bulletAngle;
    for (Iterator<BulletLogic> iterator = bullets.iterator(); iterator.hasNext(); ) {
      bullet = iterator.next();
      bulletSpeed = 15.0F;
      if (bullet.type == BulletLogic.TYPE_FAST) bulletSpeed *= 1.25F;
      else if (bullet.type == BulletLogic.TYPE_HEAVY) bulletSpeed /= 1.25F; 
      previousDistance = chimeraLogic.position.dst(bullet.position);      
      bullet.position.x += bulletSpeed * MathUtils.cos(bullet.rotation);
      bullet.position.y += bulletSpeed * MathUtils.sin(bullet.rotation);          
      if (bullet.position.x < WIDTH * -0.6F || bullet.position.x > WIDTH * 1.6F 
        || bullet.position.y < HEIGHT * -0.8F || bullet.position.y > HEIGHT * 1.8F) {
        BulletLogic.push(bullet);
        iterator.remove(); 
        continue;
      }   
      if (bullet.owner == BulletLogic.OWNER_WEST) {
        if (hp1 != 0 && bullet.position.dst(vector1) < SIZE / 2.0F) {
          hp1 = Math.max(0, hp1 - 1);          
          blink1 = true;
          BulletLogic.push(bullet);
          iterator.remove(); 
        } else if (hp2 != 0 && bullet.position.dst(vector2) < SIZE / 2.0F) {
          hp2 = Math.max(0, hp2 - 1);          
          blink2 = true;
          BulletLogic.push(bullet);
          iterator.remove(); 
        } else if (chimeraLogic.lives != 0) {
          if (chimeraLogic.contains(bullet.position, true)) {
            chimeraLogic.lives = Math.max(0, chimeraLogic.lives - 1);
            chimeraLogic.blink = true;
            bullet.owner = BulletLogic.OWNER_REST;
            bullet.rotation = Vector2.angleRad(bullet.position.x - chimeraLogic.position.x, bullet.position.y - chimeraLogic.position.y);          
          } else if (chimeraLogic.gravity && chimeraLogic.contains(bullet.position, false)) {
            if (previousDistance < chimeraLogic.position.dst(bullet.position)) bullet.owner = BulletLogic.OWNER_REST;            
            bulletAngle = Vector2.angleRad(chimeraLogic.position.x - bullet.position.x, chimeraLogic.position.y - bullet.position.y);
            if (bullet.rotation < bulletAngle || Math.abs(bullet.rotation - bulletAngle) > MathUtils.PI) 
              bullet.rotation = (bullet.rotation - ChimeraLogic.ANTIGRAVITY * frameTime + MathUtils.PI2) % MathUtils.PI2; 
            else bullet.rotation = (bullet.rotation + ChimeraLogic.ANTIGRAVITY * frameTime) % MathUtils.PI2;  
          }
        } 
      } else if (bullet.owner == BulletLogic.OWNER_REST) {  
        if (chimeraLogic.gravity && chimeraLogic.contains(bullet.position, false)) {
          bulletAngle = Vector2.angleRad(chimeraLogic.position.x - bullet.position.x, chimeraLogic.position.y - bullet.position.y);
          if (bullet.rotation < bulletAngle || Math.abs(bullet.rotation - bulletAngle) > MathUtils.PI) 
            bullet.rotation = (bullet.rotation + ChimeraLogic.GRAVITY * frameTime + MathUtils.PI2) % MathUtils.PI2; 
          else bullet.rotation = (bullet.rotation - ChimeraLogic.GRAVITY * frameTime) % MathUtils.PI2;  
        }      
        if (hp1 != 0 && bullet.position.dst(vector1) < SIZE / 2.0F) {
          hp1 = Math.max(0, hp1 - 8);
          blink1 = true;
          BulletLogic.push(bullet);
          iterator.remove(); 
        } else if (hp2 != 0 && bullet.position.dst(vector2) < SIZE / 2.0F) {
          hp2 = Math.max(0, hp2 - 8);
          blink2 = true;
          BulletLogic.push(bullet);
          iterator.remove(); 
        } else if (hp3 != 0 && bullet.position.dst(vector3) < SIZE / 2.0F) {
          hp3 = Math.max(0, hp3 - 8);
          blink3 = true;
          BulletLogic.push(bullet);
          iterator.remove(); 
        } else if (hp4 != 0 && bullet.position.dst(vector4) < SIZE / 2.0F) {
          hp4 = Math.max(0, hp4 - 8);
          blink4 = true;
          BulletLogic.push(bullet);
          iterator.remove(); 
        } 
      } else {
        if (hp3 != 0 && bullet.position.dst(vector3) < SIZE / 2.0F) {
          hp3 = Math.max(0, hp3 - 3);
          blink3 = true;
          BulletLogic.push(bullet);
          iterator.remove(); 
        } else if (hp4 != 0 && bullet.position.dst(vector4) < SIZE / 2.0F) {
          hp4 = Math.max(0, hp4 - 3);
          blink4 = true;
          BulletLogic.push(bullet);
          iterator.remove(); 
        } else if (chimeraLogic.lives != 0) {
          if (chimeraLogic.contains(bullet.position, true)) {
            chimeraLogic.lives = Math.max(0, chimeraLogic.lives - 3);
            chimeraLogic.blink = true;
            bullet.owner = BulletLogic.OWNER_REST;
            bullet.rotation = Vector2.angleRad(bullet.position.x - chimeraLogic.position.x, bullet.position.y - chimeraLogic.position.y);          
          } else if (chimeraLogic.gravity && chimeraLogic.contains(bullet.position, false)) {
            if (previousDistance < chimeraLogic.position.dst(bullet.position)) bullet.owner = BulletLogic.OWNER_REST;            
            bulletAngle = Vector2.angleRad(chimeraLogic.position.x - bullet.position.x, chimeraLogic.position.y - bullet.position.y);
            if (bullet.rotation < bulletAngle || Math.abs(bullet.rotation - bulletAngle) > MathUtils.PI) 
              bullet.rotation = (bullet.rotation - ChimeraLogic.ANTIGRAVITY * frameTime + MathUtils.PI2) % MathUtils.PI2; 
            else bullet.rotation = (bullet.rotation + ChimeraLogic.ANTIGRAVITY * frameTime) % MathUtils.PI2;
          }
        }    
      }        
    }      
  }
  
  @Override public void render() {
    batch.setProjectionMatrix(projectionMatrix.setToOrtho2D(0, -PROJECTION_Y, WIDTH, HEIGHT + PROJECTION_Y * 2.0F));
    drawContent();
    setFontColor(textFont, 1.0F, 0.9F, 0.9F, hp1 == 0 ? 0.5F : 1.0F);
    textFont.draw(batch, "HP1: " + hp1, 80.0F, HEIGHT - 80.0F);   
    setFontColor(textFont, 1.0F, 0.9F, 0.9F, hp2 == 0 ? 0.5F : 1.0F);
    textFont.draw(batch, "HP2: " + hp2, 80.0F, HEIGHT - 150.0F);
    setFontColor(textFont, 0.9F, 0.9F, 1.0F, hp3 == 0 ? 0.5F : 1.0F);
    textFont.draw(batch, "HP3: " + hp3, 80.0F, HEIGHT - 220.0F);      
    setFontColor(textFont, 0.9F, 0.9F, 1.0F, hp4 == 0 ? 0.5F : 1.0F);
    textFont.draw(batch, "HP4: " + hp4, 80.0F, HEIGHT - 290.0F);          
    setFontColor(textFont, 0.9F, 0.9F, 0.9F, chimeraLogic.lives == 0 ? 0.5F : 1.0F);       
    textFont.draw(batch, "CHI: " + chimeraLogic.lives, 80.0F, HEIGHT - 360.0F);      
    setFontColor(textFont, 1.0F, 1.0F, 1.0F, 1.0F);
    textFont.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 80.0F, HEIGHT - 430.0F);    
    setFontColor(textFont, Color.WHITE);
    textFont.draw(batch, "Target Practice", 80.0F, 270.0F);
    textFont.draw(batch, "Bullets: " + bullets.size(), 80.0F, 190.0F);
    textFont.draw(batch, "Deque:  " +  BulletLogic.getStackSize(), 80.0F, 110.0F);    
    drawCounter();      
    menuButton.draw(buttonPosition, control.isPointed(menuButton.polygon));
  }
  
  @Override public void renderEnter(float progress) { 
    alpha = previous == Menu.overview ? VEIL_ALPHA + progress * (1.0F - VEIL_ALPHA) : progress; 
    renderBackground(1.0F - progress);
    previous.renderLeave(progress);
    alpha = 1.0F;
  }

  @Override public void drawContent() {
    setBatchColor(1.0F, 1.0F, 1.0F, 1.0F);
    batch.draw(logo, (WIDTH - logo.getRegionWidth()) / 2.0F, (HEIGHT - logo.getRegionHeight()) / 2.0F);   
    ChimeraShape.draw(chimeraLogic);     
    if (blink4) setDrawerColor(0.95F, 0.95F, 1.0F, 1.0F);    
    else if (hp4 == 0) setDrawerColor(0.8F, 0.8F, 0.9F, 0.1F);    
    else setDrawerColor(0.8F, 0.8F, 0.9F, 0.5F);    
    drawer.filledTriangle(vector4.x - SIZE / 2.0F, vector4.y - SIZE / 2.5F, 
      vector4.x + SIZE / 2.0F, vector4.y - SIZE / 2.5F, vector4.x, vector4.y + SIZE / 2.0F);
    if (hp4 == 0) setDrawerColor(0.8F, 0.8F, 0.9F, 0.2F);         
    else setDrawerColor(0.85F, 0.85F, 1.0F, 1.0F);        
    drawer.triangle(vector4.x - SIZE / 2.0F, vector4.y - SIZE / 2.5F, 
      vector4.x + SIZE / 2.0F, vector4.y - SIZE / 2.5F, vector4.x, vector4.y + SIZE / 2.0F);
      
    if (blink3) setDrawerColor(0.95F, 0.95F, 1.0F, 1.0F);    
    else if (hp3 == 0) setDrawerColor(0.8F, 0.8F, 0.9F, 0.1F);    
    else setDrawerColor(0.8F, 0.8F, 0.9F, 0.5F);    
    drawer.filledTriangle(vector3.x - SIZE / 2.0F, vector3.y - SIZE / 2.5F, 
      vector3.x + SIZE / 2.0F, vector3.y - SIZE / 2.5F, vector3.x, vector3.y + SIZE / 2.0F);
    if (hp3 == 0) setDrawerColor(0.8F, 0.8F, 0.9F, 0.2F);         
    else setDrawerColor(0.85F, 0.85F, 1.0F, 1.0F);        
    drawer.triangle(vector3.x - SIZE / 2.0F, vector3.y - SIZE / 2.5F, 
      vector3.x + SIZE / 2.0F, vector3.y - SIZE / 2.5F, vector3.x, vector3.y + SIZE / 2.0F);
        
    if (blink2) setDrawerColor(1.0F, 0.95F, 0.95F, 1.0F);
    else if (hp2 == 0) setDrawerColor(0.9F, 0.8F, 0.8F, 0.1F); 
    else setDrawerColor(0.9F, 0.8F, 0.8F, 0.5F);     
    drawer.filledRectangle(vector2.x - SIZE / 2.0F, vector2.y - SIZE / 2.0F, SIZE, SIZE, speed2.angleRad());
    if (hp2 == 0) setDrawerColor(0.9F, 0.8F, 0.8F, 0.2F);
    else setDrawerColor(1.0F, 0.85F, 0.85F, 1.0F);
    drawer.rectangle(vector2.x - SIZE / 2.0F, vector2.y - SIZE / 2.0F, SIZE, SIZE, LINE_WIDTH, speed2.angleRad());   
    
    if (blink1) setDrawerColor(1.0F, 0.95F, 0.95F, 1.0F);
    else if (hp1 == 0) setDrawerColor(0.9F, 0.8F, 0.8F, 0.1F); 
    else setDrawerColor(0.9F, 0.8F, 0.8F, 0.5F);     
    drawer.filledRectangle(vector1.x - SIZE / 2.0F, vector1.y - SIZE / 2.0F, SIZE, SIZE, speed1.angleRad());
    if (hp1 == 0) setDrawerColor(0.9F, 0.8F, 0.8F, 0.2F);
    else setDrawerColor(1.0F, 0.85F, 0.85F, 1.0F);
    drawer.rectangle(vector1.x - SIZE / 2.0F, vector1.y - SIZE / 2.0F, SIZE, SIZE, LINE_WIDTH, speed1.angleRad());     
    
    drawBullets();
    
    batch.setProjectionMatrix(projectionMatrix.setToOrtho2D(0, 0, WIDTH, HEIGHT));                
  } 
  
  public void renderBackground(float progress) { 
    batch.setProjectionMatrix(projectionMatrix.setToOrtho2D(WIDTH * progress * -0.5F, 
      HEIGHT * progress * -0.5F - PROJECTION_Y, WIDTH + WIDTH * progress, HEIGHT + HEIGHT * progress + PROJECTION_Y * 2.0F));
    drawContent();
    alpha = 1.0F;
  }
}
