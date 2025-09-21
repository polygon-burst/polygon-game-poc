package polygon_burst.survivor01.menus;

import polygon_burst.survivor01.menus.MainMenu;
import polygon_burst.survivor01.menus.OverviewMenu;
import polygon_burst.survivor01.sections.Section;
import polygon_burst.survivor01.sections.Control;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.GeometryUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

abstract public class Menu extends Section {

  static public class RepeatControl extends Control {
    static final public float REPEAT_TIME = 0.25F;  
    static protected float[] timeStamps = new float[4];
    
    @Override synchronized public void process() {
      super.process(); 
      if (axisX == 1) {
        if (elapsedTime - timeStamps[3] > REPEAT_TIME) timeStamps[3] = elapsedTime;
        else axisX = 0;
      } else timeStamps[3] = 0.0F; 
      if (axisX == -1) {
        if (elapsedTime - timeStamps[2] > REPEAT_TIME) timeStamps[2] = elapsedTime;
        else axisX = 0;
      } else timeStamps[2] = 0.0F; 
      if (axisY == 1) {
        if (elapsedTime - timeStamps[1] > REPEAT_TIME) timeStamps[1] = elapsedTime;
        else axisY = 0;
      } else timeStamps[1] = 0.0F; 
      if (axisY == -1) {
        if (elapsedTime - timeStamps[0] > REPEAT_TIME) timeStamps[0] = elapsedTime;
        else axisY = 0;
      } else timeStamps[0] = 0.0F;
    }
  }
  
  static public class Box {
    final public Polygon polygon;
    final public Color[] boxColors = new Color[]{new Color(0x88888844), new Color(0xffffff88)}; 
    final public float width, height;
    
    public Box(float width, float height, float cornerNE, float cornerSE, float cornerSW, float cornerNW) {
      this.width = width;
      this.height = height;
      float[] coordinates = new float[]{cornerSW, 0.0F, 0.0F, cornerSW, 0.0F, height - cornerNW, 
        cornerNW, height, width - cornerNE, height, width, height - cornerNE, width, cornerSE, width - cornerSE, 0.0F};        
      GeometryUtils.ensureClockwise(coordinates);        
      polygon = new Polygon(coordinates);
    }
  
    public void draw(Vector2 position) {
      polygon.resetTransformations();
      polygon.translate(position.x, position.y);
      setDrawerColor(boxColors[0]);      
      drawer.filledPolygon(polygon);       
      setDrawerColor(boxColors[1]);
      drawer.polygon(polygon);
    }      
  }
  
  static public class CursorBox extends Box {
    final public Color[] cursorColors = new Color[]{new Color(0x80b3cc88), new Color(0xcce6ffe6)};
    
    public CursorBox(float width, float height, float cornerNE, float cornerSE, float cornerSW, float cornerNW) {
      super(width, height, cornerNE, cornerSE, cornerSW, cornerNW);
    }
  
    public void draw(Vector2 position, boolean active) {
      polygon.resetTransformations();
      polygon.translate(position.x, position.y);      
       if (active) {
        setDrawerColor(cursorColors[0]);
        drawer.filledPolygon(polygon);
        setDrawerColor(cursorColors[1]);
      } else {
        setDrawerColor(boxColors[0]);
        drawer.filledPolygon(polygon);
        setDrawerColor(boxColors[1]);
      }    
      drawer.polygon(polygon);
    }      
  }

  static public class LabelButton extends CursorBox {
    final public String label;
    final public Color[] labelColors = new Color[]{new Color(0x80b3cce6), new Color(0xcce6ffff)};
    
    public LabelButton(String label, float width, float height, 
      float cornerNE, float cornerSE, float cornerSW, float cornerNW) {
      super(width, height, cornerNE, cornerSE, cornerSW, cornerNW);
      this.label = label; 
    }
  
    public void draw(Vector2 position, boolean active) {
      super.draw(position, active);
      polygon.getCentroid(centroid);  
      setFontColor(labelFont, labelColors[active ? 1 : 0]);
      labelFont.draw(batch, label, polygon.getX(), centroid.y + labelFont.getCapHeight() / 2.0F, width, Align.center, false);
    }     
  } 
  
  static public class TextButton extends CursorBox {
    final public String text;
    final public Color[] textColors = new Color[]{new Color(0x80b3cce6), new Color(0xcce6ffff)}; 
    
    public TextButton(String text, float width, float height, float cornerNE, float cornerSE, float cornerSW, float cornerNW) {
      super(width, height, cornerNE, cornerSE, cornerSW, cornerNW);
      this.text = text; 
    }
  
    public void draw(Vector2 position, boolean active) {
      super.draw(position, active);
      polygon.getCentroid(centroid);  
      setFontColor(textFont, textColors[active ? 1 : 0]);
      textFont.draw(batch, text, polygon.getX(), centroid.y + textFont.getCapHeight() / 2.0F, width, Align.center, true);
    }     
  } 
  
  static public class IconButton extends CursorBox {
    final public TextureAtlas.AtlasRegion icon;
    final public Color[] iconColors = new Color[]{new Color(0x80b3cce6), new Color(0xcce6ffff)}; 
    
    public IconButton(TextureAtlas.AtlasRegion icon, float width, float height, 
      float cornerNE, float cornerSE, float cornerSW, float cornerNW) {
      super(width, height, cornerNE, cornerSE, cornerSW, cornerNW);
      this.icon = icon;
    }
  
    @Override public void draw(Vector2 position, boolean active) {
      super.draw(position, active);
      polygon.getCentroid(centroid);   
      setBatchColor(iconColors[active ? 1 : 0]);
      batch.draw(icon, centroid.x - icon.getRegionWidth() / 2.0F, centroid.y - icon.getRegionHeight() / 2.0F);
    }      
  }  
  
  static public class BarBox extends CursorBox {
    
    static public class Units {
      final public Polygon border, unit, first;      
      final public String[] labels;
      final public Color[] borderColors = new Color[]{new Color(0x66666688), new Color(0x88888888)}, 
        unitColors = new Color[]{new Color(0x77777788), new Color(0xaaaaaa88)}, 
        selectedColors = new Color[]{new Color(0xffffff88), new Color(0xffffffff)},
        unselectedColors = new Color[]{new Color(0xffffff44), new Color(0xffffff88)};
      final public float borderWidth, firstWidth, unitWidth, height;
      
      public Units(String[] labels, float firstWidth, float unitWidth, float height, float borderWidth, float corner) {       
        this.labels = labels;
        this.firstWidth = firstWidth;
        this.unitWidth = unitWidth;
        this.height = height;
        this.borderWidth = borderWidth; 
        float[] coordinates = new float[]{corner, 0.0F, 0.0F, corner, 0.0F, height - corner, corner, height, 
          firstWidth - corner, height, firstWidth, height - corner, firstWidth, corner, firstWidth - corner, 0.0F};
        GeometryUtils.ensureClockwise(coordinates);        
        first = new Polygon(coordinates);        
        coordinates = new float[]{-corner, 0.0F, 0.0F, corner, 0.0F, height - corner, -corner, height, 
          unitWidth - corner, height, unitWidth, height - corner, unitWidth, corner, unitWidth - corner, 0.0F};
        GeometryUtils.ensureClockwise(coordinates);          
        unit = new Polygon(coordinates); 
        float width = firstWidth + (labels.length - 1) * unitWidth + (labels.length + 1) * borderWidth;
        height += 2.0F * borderWidth;        
        borderWidth *= 0.75F;
        coordinates = new float[]{corner + borderWidth, 0.0F, 0.0F, corner + borderWidth, 
          0.0F, height - corner - borderWidth, corner + borderWidth, height, width - corner - borderWidth, height, 
          width, height - corner - borderWidth, width, corner + borderWidth, width - corner - borderWidth, 0.0F};        
        GeometryUtils.ensureClockwise(coordinates);  
        border = new Polygon(coordinates);
      }
      
      public void draw(Vector2 position, int value, boolean active) { 
        border.resetTransformations();
        border.translate(position.x - borderWidth, position.y - borderWidth);
        setDrawerColor(borderColors[active ? 1 : 0]);      
        drawer.filledPolygon(border); 
        first.resetTransformations();
        first.translate(position.x, position.y);
        setDrawerColor(unitColors[active ? 1 : 0]);      
        drawer.filledPolygon(first); 
        first.getCentroid(centroid);
        if (value == 0) {
          setDrawerColor(selectedColors[active ? 1 : 0]);    
          drawer.polygon(first);                       
          setFontColor(textFont, selectedColors[active ? 1 : 0]);                          
        } else setFontColor(textFont, unselectedColors[active ? 1 : 0]);          
        textFont.draw(batch, labels[0], 
          first.getX(), centroid.y + textFont.getCapHeight() / 2.0F, firstWidth, Align.center, false);            
        for (int index = labels.length - 1; index-- != 0; ) { 
          unit.resetTransformations();
          unit.translate(position.x + firstWidth + borderWidth + index * (unitWidth + borderWidth), position.y);
          setDrawerColor(unitColors[active ? 1 : 0]);      
          drawer.filledPolygon(unit);  
          unit.getCentroid(centroid);
          if (index == value - 1) {
            setDrawerColor(selectedColors[active ? 1 : 0]);    
            drawer.polygon(unit);                       
            setFontColor(textFont, selectedColors[active ? 1 : 0]);                          
          } else setFontColor(textFont, unselectedColors[active ? 1 : 0]);    
          textFont.draw(batch, labels[index + 1], 
            unit.getX(), centroid.y + textFont.getCapHeight() / 2.0F, unitWidth, Align.center, false);
        }
      }
    }
    
    final public Units units;
    final public String label; 
    final public Vector2 unitsPosition = new Vector2();
    final public Color[] labelColors = new Color[]{new Color(0x80b3cce6), new Color(0xcce6ffff)}; 
    final public float offsetPosition, offset;
        
    public BarBox(Units units, String label, float width, float height, 
      float offsetPosition, float offset, float cornerNE, float cornerSE, float cornerSW, float cornerNW) {  
      super(width, height, cornerNE, cornerSE, cornerSW, cornerNW);
      this.units = units;
      this.label = label; 
      this.offsetPosition = offsetPosition;
      this.offset = offset;
    } 
  
    public void draw(Vector2 position, int value, boolean active) {
      super.draw(position, active);      
      polygon.getCentroid(centroid); 
      setFontColor(textFont, labelColors[active ? 1 : 0]);
      textFont.draw(batch, label, 
        centroid.x + offsetPosition - offset, centroid.y + textFont.getCapHeight() / 2.0F, 0.0F, Align.right, false);
      units.draw(unitsPosition.set(centroid.x + offsetPosition + offset, centroid.y - units.height / 2.0F), value, active);
    }
  }
  
  static public class PagesBox extends CursorBox {    
    final public Polygon line, scroll, counter;
    final public String[] pages;    
    final public Color[] textColors = new Color[]{new Color(0x80b3cce6), new Color(0xcce6ffff)}, 
      scrollColors = new Color[]{new Color(0x66809944), new Color(0x80b3cc88)}, 
      counterColors = new Color[]{new Color(0x80b3cce6), new Color(0xcce6ffff)};
    final public Vector2 textOffset;
    final public float scrollHeight, pageWidth, scrollWidth;
    
    public PagesBox(String[] pages, float width, float height, float cornerNE, float cornerSE, 
      float cornerSW, float cornerNW, float scrollHeight, float pageWidth, float textX, float textY) {      
      super(width, height, cornerNE, cornerSE, cornerSW, cornerNW);
      this.pages = pages; 
      this.scrollHeight = scrollHeight;
      this.pageWidth = pageWidth;
      textOffset = new Vector2(textX, textY);      
      scrollWidth = (width - pageWidth) / pages.length; 
      float[] coordinates = new float[]{0.0F, scrollHeight, 0.0F, height - cornerNW, 
        cornerNW, height, width - cornerNE, height, width, height - cornerNE, width, scrollHeight};        
      GeometryUtils.ensureClockwise(coordinates);        
      line = new Polygon(coordinates);      
      coordinates = new float[]{width - pageWidth, scrollHeight, 
        width, scrollHeight, width, cornerSE, width - cornerSE, 0.0F, width - pageWidth, 0.0F};
      GeometryUtils.ensureClockwise(coordinates);        
      counter = new Polygon(coordinates);      
      coordinates = 
        new float[]{0.0F, scrollHeight, scrollWidth, scrollHeight, scrollWidth, 0.0F, cornerSW, 0.0F, 0.0F, cornerSW};  
      GeometryUtils.ensureClockwise(coordinates);        
      scroll = new Polygon(coordinates);
    }
    
    public void draw(Vector2 position, int page, boolean active) {
      polygon.resetTransformations();
      polygon.translate(position.x, position.y);      
      setDrawerColor(active ? cursorColors[0] : boxColors[0]);
      drawer.filledPolygon(polygon);      
      setFontColor(textFont, textColors[active ? 1 : 0]);
      textFont.draw(batch, pages[page], position.x + textOffset.x, 
        position.y + height - textOffset.y, width - textOffset.x * 2.0F, Align.left, true);             
      scroll.resetTransformations();
      scroll.translate(position.x + page * scrollWidth, position.y);      
      setDrawerColor(scrollColors[active ? 1 : 0]);
      drawer.filledPolygon(scroll);      
      counter.resetTransformations();
      counter.translate(position.x, position.y);      
      setDrawerColor(scrollColors[active ? 1 : 0]);
      drawer.filledPolygon(counter);
      counter.getCentroid(centroid);  
      setFontColor(textFont, counterColors[active ? 1 : 0]);
      textFont.draw(batch, "# " + (page + 1), centroid.x - pageWidth / 2.0F, 
        centroid.y + textFont.getCapHeight() / 2.0F, pageWidth, Align.center, false);      
      line.resetTransformations();
      line.translate(position.x, position.y);      
      setDrawerColor(active ? cursorColors[1] : boxColors[1]);
      drawer.polygon(line);            
      setDrawerColor(active ? cursorColors[1] : boxColors[1]);
      drawer.polygon(counter);            
    }
  }
  
  static final public RepeatControl repeatControl = new RepeatControl();  
  static public MainMenu main;
  static public OverviewMenu overview;
  protected CursorBox selectedBox;
  
  static public void initialize() {
    main = new MainMenu();
    overview = new OverviewMenu();
  }
  
  protected boolean isPointed(CursorBox box) {return repeatControl.isPointed(box.polygon);} 
  
  protected boolean isSelected(CursorBox selectedBox) {return this.selectedBox == selectedBox;}
  
  protected boolean isClicked(CursorBox box, boolean accept) {return repeatControl.isClicked(box.polygon, accept);}
  
  protected boolean isPushed(CursorBox selectedBox, boolean accept) {
    return this.selectedBox == selectedBox && (repeatControl.buttons[accept ? 0 : 1] || repeatControl.buttons[accept ? 2 : 3]);
  }
  
  protected int isAxisPushed(CursorBox selectedBox) {
    if (this.selectedBox == selectedBox) {
      if (control.axisX == 1 || repeatControl.buttons[0] || repeatControl.buttons[2]) return 1;
      else if (control.axisX == -1 || repeatControl.buttons[1] || repeatControl.buttons[3]) return -1;
    } 
    return 0;
  }
}
