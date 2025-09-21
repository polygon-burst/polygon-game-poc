package polygon_burst.survivor01.shapes;

import polygon_burst.survivor01.shapes.Shape;
import polygon_burst.survivor01.logic.ChimeraLogic;
import polygon_burst.survivor01.sections.Section;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class ChimeraShape extends Shape {  
  static final protected Polygon ellipse = createEllipse(128, 1.0F, 1.0F);
  static final protected Vector2[] sizes = new Vector2[]{new Vector2(100.0F, 100.0F)};
  static final protected Color[] colors = new Color[]{new Color(0x888888ff), new Color(0xffffffff)};
  
  static public void draw(ChimeraLogic chimeraLogic) {   
    if (chimeraLogic.lives == 0) Section.setDrawerColor(0.8F, 0.8F, 0.8F, 0.05F);
    else Section.setDrawerColor(0.8F, 0.8F, 0.8F, 0.1F);    
    if (chimeraLogic.gravity) {
      ellipse.resetTransformations();
      ellipse.scale(chimeraLogic.gravityRadius);
      ellipse.translate(chimeraLogic.position.x, chimeraLogic.position.y);
      drawer.filledPolygon(ellipse);
    }  
    if (chimeraLogic.blink) Section.setDrawerColor(0.95F, 0.95F, 0.95F, 1.0F);     
    else if (chimeraLogic.lives == 0) Section.setDrawerColor(0.8F, 0.8F, 0.8F, 0.1F); 
    else Section.setDrawerColor(0.8F, 0.8F, 0.8F, 0.5F);      
    ellipse.resetTransformations();
    ellipse.scale(chimeraLogic.forceRadius);
    ellipse.translate(chimeraLogic.position.x, chimeraLogic.position.y);
    drawer.filledPolygon(ellipse);      
    if (chimeraLogic.lives == 0) Section.setDrawerColor(0.8F, 0.8F, 0.8F, 0.2F);    
    else Section.setDrawerColor(0.95F, 0.95F, 0.95F, 1.0F);        
    drawer.polygon(ellipse);
  }
}
