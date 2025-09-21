package polygon_burst.survivor01.shapes;

import polygon_burst.survivor01.shapes.BulletShape;
import space.earlygrey.shapedrawer.ShapeDrawer;
import com.badlogic.gdx.math.GeometryUtils;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;

abstract public class Shape {
  static protected ShapeDrawer drawer;  

  static public void initialize(ShapeDrawer drawer) {Shape.drawer = drawer;}  
  
  static protected Polygon createEllipse(int points, float width, float height) {
    float[] coordinates = new float[points * 2];
    for (int index = 0; index != coordinates.length; index += 2) {
      coordinates[index] = MathUtils.cos(index * MathUtils.PI / points) * width;
      coordinates[index + 1] = MathUtils.sin(index * MathUtils.PI / points) * height;
    }
    GeometryUtils.ensureClockwise(coordinates);   
    return new Polygon(coordinates);
  } 
  
  /*static protected void drawLayers(Polygon[] polygonLayers) { }
  
  public void draw() {drawLayers();}
  
  public boolean collide(Shape targetShape) { }*/
}
  
