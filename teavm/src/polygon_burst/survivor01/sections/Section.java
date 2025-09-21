package polygon_burst.survivor01.sections;

import polygon_burst.survivor01.episodes.Episode;
import polygon_burst.survivor01.menus.Menu;
import polygon_burst.survivor01.sections.TitleSection;
import polygon_burst.survivor01.shapes.Shape;
import polygon_burst.survivor01.sections.Control;
import polygon_burst.survivor01.sections.Audio;
import polygon_burst.survivor01.sections.Profile;
import space.earlygrey.shapedrawer.ShapeDrawer;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.util.MixEffect;
import com.crashinvaders.vfx.effects.FxaaEffect;
import com.crashinvaders.vfx.effects.BloomEffect;
import com.crashinvaders.vfx.effects.MotionBlurEffect;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

abstract public class Section {
  static final public boolean HTML = false;
  static final public float  WIDTH = 3840.0F, HEIGHT = 2400.0F, LINE_WIDTH = 4.0F, MOVE_TIME = 0.5F;
  static final protected Control defaultControl = new Control();
  static final protected Polygon pointer = new Polygon(new float[]{0.0F, 0.0F, 0.0F, -48.0F, 15.0F, -34.0F, 35.0F, -34.0F}),
    veilPolygon1 = new Polygon(new float[]{0.0F, 0.0F, 0.0F, HEIGHT, WIDTH, HEIGHT, WIDTH, 0.0F}), 
    veilPolygon2 = new Polygon(new float[]{0.0F, 0.0F, 0.0F, HEIGHT, 100.0F, 100.0F, 0.0F});
  static final protected BitmapFont[] textFonts = new BitmapFont[3];
  static final protected Color pointerFill = new Color(0x888888ff), 
    pointerLine = new Color(0xffffffff), veilColor1 = new Color(0x00000044), veilColor2 = new Color(0x00000022);
  static final protected Matrix4 projectionMatrix = new Matrix4();
  static final protected Vector2 centroid = new Vector2();
  static protected Control control;
  static protected Audio audio;  
  static protected Section current, previous;
  static protected TitleSection title;  
  static protected VfxManager vfxManager;
  static protected FxaaEffect fxaaEffect;
  static protected BloomEffect bloomEffect;  
  static protected MotionBlurEffect blurEffect; 
  static protected ShapeDrawer drawer;    
  static protected PolygonSpriteBatch batch;  
  static protected BitmapFont labelFont, textFont;	
  static protected TextureAtlas atlas; 
  static protected Profile profile;    
  static protected float progress = 1.0F, alpha = 1.0F, elapsedTime, frameTime;
  static protected boolean forward = true;

  static public void createInitialize() {
    profile = new Profile();
    (audio = new Audio()).setVolumes(profile.soundVolume * 0.1F, profile.musicVolume * 0.1F);   
    Gdx.graphics.setSystemCursor(SystemCursor.None);
    Gdx.input.setCursorPosition(0, Gdx.graphics.getHeight());
    vfxManager = new VfxManager(Pixmap.Format.RGBA8888);    
    vfxManager.addEffect(fxaaEffect = new FxaaEffect(0.2F, 0.5F, 4.0F, true));  
    vfxManager.addEffect(bloomEffect = new BloomEffect(new BloomEffect.Settings(4, 0.6F, 1.0F, 1.0F, 1.4F, 1.0F)));    
    vfxManager.addEffect(blurEffect = new MotionBlurEffect(Pixmap.Format.RGBA8888, MixEffect.Method.MAX, 0.2F)); 
    Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);     
    (batch =  new PolygonSpriteBatch()).setProjectionMatrix(projectionMatrix.setToOrtho2D(0, 0, WIDTH, HEIGHT));
    atlas = new TextureAtlas(Gdx.files.internal("graphics/textures.atlas"));  
    (drawer = new ShapeDrawer(batch, atlas.findRegion("fill"))).setDefaultLineWidth(LINE_WIDTH);           
    Shape.initialize(drawer);  
    for (int index = textFonts.length; index-- != 0; ) {
      String fontName = "chakra_petch_" + (54 + index * 4);
      textFonts[index] = new BitmapFont(Gdx.files.internal("graphics/" + fontName + ".fnt"), atlas.findRegion(fontName));
    }    
    textFont = textFonts[profile.textSize];
    labelFont = new BitmapFont(Gdx.files.internal("graphics/alpha_prota_64.fnt"), atlas.findRegion("alpha_prota_64")); 
    Menu.initialize();
    Episode.initialize();         
    current = new TitleSection(); 
    setControl(defaultControl);    
    System.gc();  
  }
  
  static public void updateRender() {
    control.process();
    elapsedTime += frameTime = Gdx.graphics.getDeltaTime();
    if (progress == 1.0F) current.update();
    vfxManager.cleanUpBuffers();
    vfxManager.beginInputCapture();    
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
		batch.begin();		
		if (progress == 1.0F) current.render();    
		else current.renderEnter(progress = Math.min(1.0F, progress + frameTime / MOVE_TIME));
    pointer.setPosition(control.pointer.x, control.pointer.y);  
    setDrawerColor(pointerFill);
    drawer.filledPolygon(pointer);
    setDrawerColor(pointerLine);
    drawer.polygon(pointer); 
		batch.end();		
		vfxManager.endInputCapture();
    vfxManager.applyEffects();
    vfxManager.renderToScreen();
  }

  static public void saveDispose() { 
    profile.dispose();
    labelFont.dispose();   
    textFont.dispose();
    atlas.dispose();
    vfxManager.dispose();   
    blurEffect.dispose();
    bloomEffect.dispose();
    fxaaEffect.dispose(); 
    audio.dispose();  
    System.gc();
  }
  
  static public void setDrawerColor(Color color) {drawer.setColor(color.r, color.g, color.b, color.a * alpha);}
  
  static public void setBatchColor(Color color) {batch.setColor(color.r, color.g, color.b, color.a * alpha);}
  
  static public void setFontColor(BitmapFont font, Color color) {font.setColor(color.r, color.g, color.b, color.a * alpha);}  
  
  static public void setDrawerColor(float r, float g, float b, float a) {drawer.setColor(r, g, b, a * alpha);}
  
  static public void setBatchColor(float r, float g, float b, float a) {batch.setColor(r, g, b, a * alpha);}
  
  static public void setFontColor(BitmapFont font, float r, float g, float b, float a) {font.setColor(r, g, b, a * alpha);}  
  
  static protected void setControl(Control control) {
    if (control == Section.control) return;
    Gdx.input.setInputProcessor(Section.control = control);
    Controllers.clearListeners();
    Controllers.addListener(control);
  }
  
  static protected void setSection(Section next) {
    previous = current; 
    current = next;
    progress = 0.0F;
  }
  
  static protected void setMenu(Menu next) {
    setSection(next);
    setControl(Menu.repeatControl);
  }
  
  static protected void setEpisode(Episode next) {
    setSection(next);
    setControl(defaultControl);
  }

  static protected void startNewRender(float xFactor, float yFactor) {
    batch.end();
    Gdx.gl.glViewport((int)(Gdx.graphics.getWidth() * xFactor),
      (int)(Gdx.graphics.getHeight() * yFactor), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glScissor((int)(Gdx.graphics.getWidth() * xFactor),
      (int)(Gdx.graphics.getHeight() * yFactor), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    batch.begin();
  }  
  
  abstract public void update();  
  
  abstract public void drawContent();
  
  public void render() {drawContent();}
  
  public void renderEnter(float progress) { 
    previous.renderLeave(progress);
    alpha = progress;
    drawContent();
    alpha = 1.0F;
  }
  
  public void renderLeave(float progress) {
    alpha = 1.0F - progress;
    drawContent();
    alpha = 1.0F;
  }
}
