package polygon_burst.survivor01.menus;

import polygon_burst.survivor01.episodes.Episode;
import polygon_burst.survivor01.menus.EpisodesMenu;
import polygon_burst.survivor01.menus.GuideMenu;
import polygon_burst.survivor01.menus.SettingsMenu;
import polygon_burst.survivor01.menus.Menu;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;

public class MainMenu extends Menu {
  final protected EpisodesMenu episodes = new EpisodesMenu();
  final protected GuideMenu guide = new GuideMenu();
  final protected SettingsMenu settings = new SettingsMenu();
  final protected LabelButton gameButton, guideButton, settingsButton, exitButton;
  final protected Vector2 gamePosition, guidePosition, settingsPosition, exitPosition;
  protected Menu subMenu/* = settings*/;
  
  public MainMenu() {
    I18NBundle labelTexts = I18NBundle.createBundle(Gdx.files.internal("texts/main_menu"));
    gameButton = new LabelButton(labelTexts.format("continue_to_game"), 800.0F, 200.0F, 20.0F, 10.0F, 20.0F, 10.0F);
    guideButton = new LabelButton(labelTexts.format("guide_to_the_game"), 800.0F, 200.0F, 20.0F, 10.0F, 20.0F, 10.0F);
    settingsButton = new LabelButton(labelTexts.format("go_to_settings"), 800.0F, 200.0F, 20.0F, 10.0F, 20.0F, 10.0F);
    exitButton = new LabelButton(labelTexts.format("exit_to_desktop"), 800.0F, 200.0F, 20.0F, 10.0F, 20.0F, 10.0F);  
    gameButton.boxColors[0] = guideButton.boxColors[0] = 
      settingsButton.boxColors[0] = exitButton.boxColors[0] = new Color(0x22222222);
    gameButton.boxColors[1] = guideButton.boxColors[1] = settingsButton.boxColors[1] = exitButton.boxColors[1] = new Color(0);
    gameButton.cursorColors[0] = guideButton.cursorColors[0] = 
      settingsButton.cursorColors[0] = exitButton.cursorColors[0] = new Color(0x80b3cc22);
    gameButton.cursorColors[1] = guideButton.cursorColors[1] = 
      settingsButton.cursorColors[1] = exitButton.cursorColors[1] = new Color(0xcce6ff22);
    gameButton.labelColors[0] = guideButton.labelColors[0] = 
      settingsButton.labelColors[0] = exitButton.labelColors[0] = new Color(0xaaaaccff);
    gameButton.labelColors[1] = guideButton.labelColors[1] = 
      settingsButton.labelColors[1] = exitButton.labelColors[1] = new Color(0xeeeeffff);
    gamePosition = new Vector2(100.0F, 850.0F); 
    guidePosition = new Vector2(100.0F, 600.0F); 
    settingsPosition = new Vector2(100.0F, 350.0F); 
    exitPosition = new Vector2(100.0F, 100.0F); 
    selectedBox = gameButton;
  }

  @Override public void update() {
    if (subMenu == null)  {
      if (isPushed(gameButton, true) || isClicked(gameButton, true)) {
        selectedBox = gameButton;
        audio.playSound("option_select", 1.0F);
        subMenu = episodes;
      } else if (isPushed(guideButton, true) || isClicked(guideButton, true)) {
        selectedBox = guideButton;
        audio.playSound("option_select", 1.0F);
        subMenu = guide;        
      } else if (control.buttons[4] || isPushed(settingsButton, true) || isClicked(settingsButton, true)) {
        selectedBox = settingsButton;
        audio.playSound("option_select", 1.0F);
        subMenu = settings;
      } else if (!HTML && (isPushed(exitButton, true) || isClicked(exitButton, true))) {
        selectedBox = exitButton;
        Gdx.app.exit();  
      } else if (selectedBox == gameButton) {
        if (control.axisY == -1) selectedBox = guideButton;
      } else if (selectedBox == guideButton) {
        if (control.axisY == -1) selectedBox = settingsButton;
        else if (control.axisY == 1) selectedBox = gameButton;
      } else if (selectedBox == settingsButton) {
        if (!HTML && control.axisY == -1) selectedBox = exitButton;
        else if (control.axisY == 1) selectedBox = guideButton;
      } else if (selectedBox == exitButton) {
        if (control.axisY == 1) selectedBox = settingsButton;
      } 
    } else {      
      if (control.buttons[4]
        || subMenu == episodes && (episodes.isPushed(episodes.backButton, true) 
          || episodes.isClicked(episodes.backButton, true))      
        || subMenu == guide && (guide.isPushed(guide.backButton, true) || guide.isClicked(guide.backButton, true))
        || subMenu == settings && (settings.isPushed(settings.backButton, true) 
          || settings.isClicked(settings.backButton, true))) 
        subMenu = null;
      else subMenu.update();
      if (subMenu == episodes && (episodes.isPushed(episodes.startButton, true) 
        || episodes.isClicked(episodes.startButton, true))) 
        subMenu = null; 
    }
  }

  @Override public void drawContent() {    
    gameButton.draw(gamePosition, subMenu == null && (isSelected(gameButton) || control.isPointed(gameButton.polygon)));
    guideButton.draw(guidePosition, 
      subMenu == guide || subMenu == null && (isSelected(guideButton) || control.isPointed(guideButton.polygon)));
    settingsButton.draw(settingsPosition, 
      subMenu == settings || subMenu == null && (isSelected(settingsButton) || control.isPointed(settingsButton.polygon)));
    if (!HTML) 
      exitButton.draw(exitPosition, subMenu == null && (isSelected(exitButton) || control.isPointed(exitButton.polygon)));
    if (subMenu != null) subMenu.drawContent();
  }
}
