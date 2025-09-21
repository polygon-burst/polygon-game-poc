package polygon_burst.survivor01.logic;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class ChimeraLogic {
  static final public float GRAVITY = 1.8F, ANTIGRAVITY = 2.4F;
  static protected ChimeraLogic chimera;
  //final public Vector2[] sizes;
  //final public Polygon[] hitPolygons;  
  final public Vector2 speed = new Vector2(240.0F, 240.0F), axes = new Vector2(), position = new Vector2(); 
  public float forceRadius, gravityRadius, rotation;
  public int lives;
  public boolean gravity = true, blink;
  
  static public ChimeraLogic pop(float x, float y) {
    if (chimera == null) chimera = new ChimeraLogic(); 
    chimera.position.set(x, y);
    chimera.forceRadius = 80.0F;
    chimera.gravityRadius = 400.0F;
    chimera.lives = 500;
    return chimera;
  }
  
  public void update(float time, int axisX, int axisY) {    
    axes.set(axisX, axisY).nor();  
    position.x += time * speed.x * axes.x;
    position.y += time * speed.y * axes.y; 
    rotation += time * 10.0F;
  }
    
  public boolean contains(Vector2 point, boolean forceField) {
    return position.dst(point) < (forceField ? forceRadius : gravityRadius);
  }
  
  protected ChimeraLogic() {
    //hitPolygons = new Vector2[]{new Polygon()}; 
    //sizes = new Vector2[]{new Vector2(100.0F, 100.0F)};
  }
}
