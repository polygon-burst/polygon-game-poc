package polygon_burst.survivor01;

import polygon_burst.survivor01.sections.Section;
import com.badlogic.gdx.ApplicationAdapter;

public class Adapter extends ApplicationAdapter {

  @Override public void create() {Section.createInitialize();}

  @Override public void render() {Section.updateRender();}

  @Override public void dispose() {Section.saveDispose();}
}
