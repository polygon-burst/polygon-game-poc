package polygon_burst.survivor01.shapes;

import polygon_burst.survivor01.logic.BulletLogic;
import polygon_burst.survivor01.shapes.Shape;
import polygon_burst.survivor01.sections.Section;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

public class BulletShape extends Shape {  
  static final protected Polygon[] ellipses = 
    new Polygon[]{createEllipse(16, 24.0F, 8.0F), createEllipse(16, 16.0F, 16.0F), createEllipse(16, 12.0F, 36.0F)};
  static final protected Color[] fillColors = new Color[]{new Color(0x579fc7ff), new Color(0xa72c6dff), new Color(0x444444ff)}, 
    outlineColors = new Color[]{new Color(0xe6f9f3ff), new Color(0xd3959aff), new Color(0xeeeeeeff)};
  
  static public void draw(BulletLogic bulletLogic) {   
    ellipses[bulletLogic.type].resetTransformations();
    ellipses[bulletLogic.type].rotate(bulletLogic.rotation * MathUtils.radDeg);
    ellipses[bulletLogic.type].translate(bulletLogic.position.x, bulletLogic.position.y);  
    Section.setDrawerColor(fillColors[bulletLogic.owner]);    
    drawer.filledPolygon(ellipses[bulletLogic.type]);         
    Section.setDrawerColor(outlineColors[bulletLogic.owner]);    
    drawer.polygon(ellipses[bulletLogic.type]); 
  }
}
