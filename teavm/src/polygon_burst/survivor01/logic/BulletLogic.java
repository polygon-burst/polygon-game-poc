package polygon_burst.survivor01.logic;

import polygon_burst.survivor01.episodes.Episode;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayDeque;

public class BulletLogic {
  static final public int TYPE_FAST = 0, TYPE_NORMAL = 1, TYPE_HEAVY = 2, OWNER_WEST = 0, OWNER_EAST = 1, OWNER_REST = 2;
  static protected ArrayDeque<BulletLogic> deque = new ArrayDeque<>(Episode.MAX_BULLETS);
  public Vector2 position = new Vector2();
  public float rotation;
  public int type, owner;
  
  static public int getStackSize() {return deque.size();}
  
  static public BulletLogic pop() {return deque.isEmpty() ? new BulletLogic() : deque.pop();}
  
  static public void push(BulletLogic bulletLogic) {deque.push(bulletLogic);}
    
  protected BulletLogic() { }
}
