package polygon_burst.survivor01.sections;

import polygon_burst.survivor01.sections.Section;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;

public class Control implements InputProcessor, ControllerListener {
  static final public Vector2 pointer = new Vector2();  
  static final public boolean[] buttons = new boolean[5], clicks = new boolean[2];   
  static public int axisX, axisY;
  static protected boolean[] triggers = new boolean[11];  
  
  public Control() {    
    int[] catchKeys = new int[]{Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, 
      Input.Keys.W, Input.Keys.SPACE, Input.Keys.ENTER, Input.Keys.A, Input.Keys.BACKSPACE, 
      Input.Keys.S, Input.Keys.D, Input.Keys.ESCAPE, Input.Keys.CONTROL_LEFT, Input.Keys.CONTROL_RIGHT};
    for (int key : catchKeys) Gdx.input.setCatchKey(key, true);
  }   

  @Override public boolean keyDown(int keyCode) {  
    if (keyCode == Input.Keys.UP) triggers[0] = true;
    else if (keyCode == Input.Keys.DOWN) triggers[1] = true;
    else if (keyCode == Input.Keys.LEFT) triggers[2] = true;
    else if (keyCode == Input.Keys.RIGHT) triggers[3] = true;
    else if (keyCode == Input.Keys.W || keyCode == Input.Keys.SPACE || keyCode == Input.Keys.ENTER) triggers[4] = true;
    else if (keyCode == Input.Keys.A || keyCode == Input.Keys.BACKSPACE) triggers[5] = true;
    else if (keyCode == Input.Keys.S) triggers[6] = true;
    else if (keyCode == Input.Keys.D) triggers[7] = true;
    else if (keyCode == Input.Keys.ESCAPE) triggers[8] = true;
    else return false;    
    return true;
  }
  
  @Override public boolean keyUp(int keyCode) {  
    if (keyCode == Input.Keys.UP) triggers[0] = false;
    else if (keyCode == Input.Keys.DOWN) triggers[1] = false;
    else if (keyCode == Input.Keys.LEFT) triggers[2] = false;
    else if (keyCode == Input.Keys.RIGHT) triggers[3] = false;
    else return false;    
    return true;
  }    
  
  @Override public boolean mouseMoved(int x, int y) {
    pointer.set(Section.WIDTH * x / Gdx.graphics.getWidth(), Section.HEIGHT - (Section.HEIGHT * y / Gdx.graphics.getHeight())); 
    return true;
  }
    
  @Override public boolean touchDown(int x, int y, int identifier, int button) {
    if (button != Input.Buttons.LEFT && button != Input.Buttons.RIGHT) return false;
    pointer.set(Section.WIDTH * x / Gdx.graphics.getWidth(), Section.HEIGHT - (Section.HEIGHT * y / Gdx.graphics.getHeight()));    
    if (button == Input.Buttons.LEFT) triggers[9] = true;
    else if (button == Input.Buttons.RIGHT) triggers[10] = true;
    return true;
  } 
  
  @Override public boolean buttonDown(Controller controller, int button) {
    final ControllerMapping mapping = controller.getMapping();
    if (button == mapping.buttonDpadUp) triggers[0] = true;
    else if (button == mapping.buttonDpadDown) triggers[1] = true;
    else if (button == mapping.buttonDpadLeft) triggers[2] = true;
    else if (button == mapping.buttonDpadRight) triggers[3] = true;
    else if (button == mapping.buttonA || button == mapping.buttonR1) triggers[4] = true;
    else if (button == mapping.buttonB || button == mapping.buttonL1) triggers[5] = true;
    else if (button == mapping.buttonX) triggers[6] = true;
    else if (button == mapping.buttonY) triggers[7] = true;
    else if (button == mapping.buttonStart || button == mapping.buttonBack) triggers[8] = true;
    else return false;
    return true;
  }
  
  @Override public boolean buttonUp(Controller controller, int button) {
    final ControllerMapping mapping = controller.getMapping();
    if (button == mapping.buttonDpadUp) triggers[0] = false;
    else if (button == mapping.buttonDpadDown) triggers[1] = false;
    else if (button == mapping.buttonDpadLeft || button == mapping.buttonL1) triggers[2] = false;
    else if (button == mapping.buttonDpadRight || button == mapping.buttonR1) triggers[3] = false;
    else return false;
    return true;
  }
  
  @Override public boolean axisMoved(Controller controller, int axis, float value) {
    ControllerMapping mapping = controller.getMapping();
    if (axis == mapping.axisLeftY) {
      triggers[0] = value < -0.5F;
      triggers[1] = value > 0.5F;
      return true;
    } else if (axis == mapping.axisLeftX) {
      triggers[2] = value < -0.5F;
      triggers[3] = value > 0.5F;
      return true;
    }
    return true;
  } 
  
  synchronized public void process() {
    axisX = triggers[2] ? (triggers[3] ? 0 : -1) : triggers[3] ? 1 : 0;
    axisY = triggers[0] ? (triggers[1] ? 0 : 1) : triggers[1] ? -1 : 0;
    for (int index = buttons.length; index-- != 0;) buttons[index] = triggers[index + 4];      
    for (int index = clicks.length; index-- != 0;) clicks[index] = triggers[index + 9];    
    for (int index = triggers.length; index-- != 4;) triggers[index] = false;
  }
    
  public boolean isPointed(Polygon polygon) {return polygon.contains(pointer);} 
  
  public boolean isClicked(Polygon polygon, boolean accept) {return clicks[accept ? 0 : 1] && polygon.contains(pointer);} 
  
  @Override public boolean keyTyped(char character) {return true;}    
  @Override public boolean scrolled(float x, float y) {return true;}
  @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {return true;}
  @Override public boolean touchCancelled(int x, int y, int identifier, int button) {return true;}
  @Override public boolean touchDragged(int screenX, int screenY, int pointer) {return true;}      
  @Override public void connected(Controller controller) { }
  @Override public void disconnected(Controller controller) { }	
}
