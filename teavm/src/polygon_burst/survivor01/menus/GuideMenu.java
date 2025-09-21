package polygon_burst.survivor01.menus;

import polygon_burst.survivor01.menus.Menu;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class GuideMenu extends Menu {  
  final protected PagesBox pagesBox;
  final protected TextButton backButton;
  final protected Box backgroundBox;
  final protected Vector2 backgroundPosition, pagesPosition, backPosition;
  protected int page;
  
  public GuideMenu() {    
    I18NBundle pageTexts = I18NBundle.createBundle(Gdx.files.internal("texts/guide_pages"));
    String[] pages = new String[Integer.parseInt(pageTexts.format("pages"))];
    for (int index = 0; index != pages.length; index++) pages[index] = pageTexts.format("page_" + index);  
    backgroundBox = new Box(2400.0F, 2200.0F, 40.0F, 20.0F, 40.0F, 20.0F);      
    pagesBox = new PagesBox(pages, 2200.0F, 1800.0F, 20.0F, 10.0F, 10.0F, 10.0F, 100.0F, 240.0F, 50.0F, 60.0F);     
    backButton = new TextButton("Back to Main", 2200.0F, 175.0F, 20.0F, 10.0F, 20.0F, 10.0F); 
    backgroundPosition = new Vector2(1100.0F, (HEIGHT - backgroundBox.height) / 2.0F);    
    pagesPosition = new Vector2(1200.0F, 425.0F);     
    backPosition = new Vector2(1200.0F, 175.0F); 
    selectedBox = backButton;
  }

  @Override public void update() {
    if (isClicked(pagesBox, true)) {
      if (selectedBox == pagesBox) turnPage(true);
      else selectedBox = pagesBox;           
    } else if (isClicked(pagesBox, false)) {
      if (selectedBox == pagesBox) turnPage(false);
      else selectedBox = pagesBox; 
    } else if (isAxisPushed(pagesBox) == 1) turnPage(true);
    else if (isAxisPushed(pagesBox) == -1) turnPage(false);
    else if (selectedBox == pagesBox) {
      if (control.axisY == -1) selectedBox = backButton;
    } else if (selectedBox == backButton) {
      if (control.axisY == 1) selectedBox = pagesBox;
    }
  }

  @Override public void drawContent() {
    backgroundBox.draw(backgroundPosition);    
    pagesBox.draw(pagesPosition, page, isSelected(pagesBox) || isPointed(pagesBox));    
    backButton.draw(backPosition, isSelected(backButton) || isPointed(backButton));
  }
  
  protected void turnPage(boolean forward) {
    if (forward && page == pagesBox.pages.length - 1 || !forward && page == 0) return;
    page += forward ? 1 : -1;
    audio.playSound("option_select", 1.0F);
  }
}
