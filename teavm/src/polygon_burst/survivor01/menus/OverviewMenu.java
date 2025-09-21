package polygon_burst.survivor01.menus;

import polygon_burst.survivor01.episodes.Episode;
import polygon_burst.survivor01.menus.Menu;
import com.badlogic.gdx.math.Vector2;

public class OverviewMenu extends Menu { 
  static final public float EPISODE_ALPHA = 0.1F;
  /*final protected BarBox soundBar, musicBar;  
  final protected LabelButton exitButton, backButton;
  final protected Box backgroundBox;
  final protected Vector2 backgroundPosition, soundPosition, musicPosition, backPosition, exitPosition;*/
  
  public OverviewMenu() {
    /*backgroundBox = new Box(2000.0F, 2000.0F, 40.0F, 20.0F, 40.0F, 20.0F, 0x88888844, 0xffffff88); 
    BarBox.Units units = new BarBox.Units(new String[]{"0%", "25%", "50%", "75%", "100%"},
      65.0F, 55.0F, 100.0F, 15.0F, 15.0F, 0x66666688, 0x88888888, 0xffffff88, 0xffffffff, 0x77777788);
    soundBar = new BarBox(units, "Sound Volume", 1600.0F, 200.0F, -155.0F, 40.0F, 20.0F, 10.0F, 20.0F, 10.0F);
    musicBar = new BarBox(units, "Music Volume", 1600.0F, 200.0F, -155.0F, 40.0F, 20.0F, 10.0F, 20.0F, 10.0F);      
    exitButton = new LabelButton(textFont, "Exit to Main", 750.0F, 200.0F, 
      20.0F, 10.0F, 20.0F, 10.0F, 0x88888822, 0x88888888, 0x80b3cc88, 0xcce6ffe6, 0x80b3cce6, 0xcce6ffff);
    backButton = new LabelButton(textFont, "Return to Game",  750.0F, 200.0F, 
      20.0F, 10.0F, 20.0F, 10.0F, 0x88888822, 0x88888888, 0x80b3cc88, 0xcce6ffe6, 0x80b3cce6, 0xcce6ffff);
    backgroundPosition = new Vector2((WIDTH - backgroundBox.width) / 2.0F, (HEIGHT - backgroundBox.height) / 2.0F);
    soundPosition = new Vector2((WIDTH - soundBar.width) / 2.0F, HEIGHT / 2.0F + 250.0F);  
    musicPosition = new Vector2((WIDTH - musicBar.width) / 2.0F, (HEIGHT - exitButton.height) / 2.0F);
    exitPosition = new Vector2(WIDTH / 2.0F - exitButton.width - 50.0F, HEIGHT / 2.0F - exitButton.height - 250.0F);    
    backPosition = new Vector2(WIDTH / 2.0F + 5.0F, HEIGHT / 2.0F - backButton.height - 250.0F);
    selectedBox = backButton;*/
  }

  @Override public void update() {    
    /*if (isClicked(soundBar, true)) {
      if (selectedBox == soundBar) adjustSound(true);
      else selectedBox = soundBar;           
    } else if (isClicked(soundBar, false)) {
      if (selectedBox == soundBar) adjustSound(false);
      else selectedBox = soundBar; 
    } else if (isAxisPushed(soundBar) == 1) adjustSound(true);
    else if (isAxisPushed(soundBar) == -1) adjustSound(false);
    else if (isClicked(musicBar, true)) {
      if (selectedBox == musicBar) adjustMusic(true);
      else selectedBox = musicBar;  
    } else if (isClicked(musicBar, false)) {
      if (selectedBox == musicBar) adjustMusic(false);
      else selectedBox = musicBar;  
    } else if (isAxisPushed(musicBar) == 1) adjustMusic(true);
    else if (isAxisPushed(musicBar) == -1) adjustMusic(false);
    else if (control.buttons[4] || isPushed(backButton, true) || isClicked(backButton, true)) {
      selectedBox = backButton;
      audio.playSound("option_select", 1.0F);
      setControl(defaultControl);
      setSection(Episode.s01e01);
    } else if (isPushed(exitButton, true) || isClicked(exitButton, true)) {
      selectedBox = exitButton;
      audio.playSound("option_select", 1.0F);
      setMenu(main); 
    } else if (selectedBox == soundBar) {
      if (control.axisY == -1) selectedBox = musicBar;
    } else if (selectedBox == musicBar) {
      if (control.axisY == -1) selectedBox = exitButton;
      else if (control.axisY == 1) selectedBox = soundBar;
    } else if (selectedBox == exitButton) {
      if (control.axisY == 1) selectedBox = musicBar;
      else if (control.axisX == 1) selectedBox = backButton;
    } else if (selectedBox == backButton) {
      if (control.axisY == 1) selectedBox = musicBar;
      else if (control.axisX == -1) selectedBox = exitButton;
    } */
  }
  
  @Override public void render() { 
    alpha = Episode.VEIL_ALPHA; 
    Episode.s01e01.renderBackground(1.0F);
    alpha = 1.0F;
    drawContent();
  }
  
  @Override public void renderEnter(float progress) { 
    alpha = 1.0F - progress * (1.0F - Episode.VEIL_ALPHA); 
    Episode.s01e01.renderBackground(progress);
    alpha = progress;
    drawContent();
    alpha = 1.0F;
  }
  
  @Override public void renderLeave(float progress) {
    if (current == Menu.main) {
      alpha = (1.0F - progress) * Episode.VEIL_ALPHA; 
      Episode.s01e01.renderBackground(1.0F);
    } 
    alpha = 1.0F - progress;
    drawContent();
    alpha = 1.0F;
  }

  @Override public void drawContent() { 
    /*backgroundBox.draw(backgroundPosition);
    soundBar.draw(soundPosition, profile.soundVolume, isSelected(soundBar) || control.isPointed(soundBar.polygon));
    musicBar.draw(musicPosition, profile.musicVolume, isSelected(musicBar) || control.isPointed(musicBar.polygon));
    exitButton.draw(exitPosition, isSelected(exitButton) || control.isPointed(exitButton.polygon));
    backButton.draw(backPosition, isSelected(backButton) || control.isPointed(backButton.polygon));*/
  }
  
  protected void adjustSound(boolean forward) {
    if (forward && profile.soundVolume == 10 || !forward && profile.soundVolume == 0) return;    
    audio.setVolumes((profile.soundVolume += forward ? 1 : -1) * 0.1F, profile.musicVolume * 0.1F);
    audio.playSound("option_select", 1.0F);
  }
  
  protected void adjustMusic(boolean forward) {
    if (forward && profile.musicVolume == 10 || !forward && profile.musicVolume == 0) return;
    audio.setVolumes(profile.soundVolume * 0.1F, (profile.musicVolume += forward ? 1 : -1) * 0.1F);    
  }
}
