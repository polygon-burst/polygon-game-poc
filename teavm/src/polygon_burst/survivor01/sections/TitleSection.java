package polygon_burst.survivor01.sections;

import polygon_burst.survivor01.episodes.Episode;
import polygon_burst.survivor01.menus.Menu;
import polygon_burst.survivor01.sections.Section;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

public class TitleSection extends Section {
  static final public String HTML_TEXT = 
    "If this version is too slow on your computer, try downloading the desktop version.",
    CLICK_TEXT = "CLICK TO START", MADE_TEXT = "made with lib", GDX_TEXT = "GDX",
    LOGO_TEXT = "POLYGON_BURST", TITLE_TEXT = "POLYGON GAME [ PROOF OF CONCEPT ]";
  static final public float LOGO_TIME = 2.0F, TITLE_TIME = 1.0F;
  final protected Vector2 htmlPosition = new Vector2(0.0F, HEIGHT / 2.0F + 200.0F),
    clickPosition = new Vector2(0.0F, HEIGHT / 2.0F - 100.0F),
    madePosition = new Vector2(WIDTH - 160.0F, 80.0F), logoScale = new Vector2(100.0F, 100.0F),    
    logoPosition = new Vector2((WIDTH - logoScale.x) / 2.0F - 400.0F, (HEIGHT - logoScale.y) / 2.0F), 
    logotextPosition = new Vector2(WIDTH / 2.0F - 100.0F, (HEIGHT + 64.0F) / 2.0F + 100.0F), 
    titlePosition = new Vector2(WIDTH / 2.0F - 100.0F, (HEIGHT + 64.0F) / 2.0F - 100.0F); 
  final protected Color htmlColor = new Color(0xddddffff), clickColor = new Color(0xaaaaffff),  
    madeColor = new Color(0xccccccff), gdxColor = new Color(0xff6661ff), logoColor = new Color(0xaaaaffff), 
    logotextColor = new Color(0xaaaaffff), titleColor = new Color(0xffffffff);  
  final protected float[][] logoVertices = new float[][]{{10.0F, 0.0F, 0.0F, 10.0F, 
      0.0F, 100.0F, 20.0F, 120.0F, 110.0F, 120.0F, 120.0F, 110.0F, 120.0F, 20.0F, 100.0F, 0.0F}, 
    {10.0F, 0.0F, 0.0F, 10.0F, 0.0F, 100.0F, 20.0F, 120.0F, 110.0F, 120.0F, 120.0F, 110.0F, 120.0F, 20.0F, 100.0F, 0.0F}};  
  protected float timeStamp = 0.0F; 

  @Override public void update() {
    if (timeStamp == 0.0F) {
     if (!HTML || control.clicks[0] || control.clicks[1]) timeStamp = elapsedTime;
    } else if (elapsedTime - timeStamp > LOGO_TIME) {
      audio.playMusic("in_the_club");  
      
      //setEpisode(Episode.s01e01);   
      setMenu(Menu.main);
    }
  }

  @Override public void drawContent() {      
    if (HTML && timeStamp == 0.0F) {
      setFontColor(textFonts[2], htmlColor);
      textFonts[2].draw(batch, HTML_TEXT, htmlPosition.x, htmlPosition.y, WIDTH, Align.center, false);
      if (elapsedTime % 1.2F < 0.8F) {
        setFontColor(labelFont, clickColor);
        labelFont.draw(batch, CLICK_TEXT, clickPosition.x, clickPosition.y, WIDTH, Align.center, false);
      }
    } else {  
      // TODO: draw logo path
      setFontColor(labelFont, logotextColor);
      labelFont.draw(batch, LOGO_TEXT, logotextPosition.x, logotextPosition.y, WIDTH / 2.0F, Align.left, false);    
      setFontColor(labelFont, titleColor);
      labelFont.draw(batch, TITLE_TEXT, titlePosition.x, titlePosition.y, WIDTH / 2.0F, Align.left, false);    
      setFontColor(textFont, madeColor);
      textFont.draw(batch, MADE_TEXT, madePosition.x - WIDTH - 12.0F, madePosition.y, WIDTH, Align.right, false);
      setFontColor(textFont, gdxColor);
      textFont.draw(batch, GDX_TEXT, madePosition.x, madePosition.y, WIDTH, Align.left, false);
    }
  }
}
