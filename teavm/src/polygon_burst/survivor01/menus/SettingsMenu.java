package polygon_burst.survivor01.menus;

import polygon_burst.survivor01.menus.Menu;
import com.badlogic.gdx.math.Vector2;

public class SettingsMenu extends Menu {    
  final protected BarBox soundBar, musicBar, fontBar;  
  final protected TextButton backButton;
  final protected Box backgroundBox;
  final protected Vector2 backgroundPosition, soundPosition, musicPosition, fontPosition, backPosition;
  
  public SettingsMenu() {
    backgroundBox = new Box(2000.0F, 2000.0F, 40.0F, 20.0F, 40.0F, 20.0F);    
    BarBox.Units units = new BarBox.Units(new String[]{"0%", "25%", "50%", "75%", "100%"}, 
      180.0F, 165.0F, 90.0F, 15.0F, 15.0F);
    soundBar = new BarBox(units, "Sound Volume", 1600.0F, 200.0F, -300.0F, 40.0F, 20.0F, 10.0F, 20.0F, 10.0F); 
    musicBar = new BarBox(units, "Music Volume", 1600.0F, 200.0F, -300.0F, 40.0F, 20.0F, 10.0F, 20.0F, 10.0F);       
    fontBar = new BarBox(new BarBox.Units(new String[]{"Small", "Medium", "Huge"}, 310.0F, 280.0F, 90.0F, 15.0F, 15.0F), 
      "Text Font Size", 1600.0F, 200.0F, -300.0F, 40.0F, 20.0F, 10.0F, 20.0F, 10.0F);       
    backButton = new TextButton("Return to Main",  1600.0F, 200.0F, 20.0F, 10.0F, 20.0F, 10.0F); 
    backgroundPosition = new Vector2(1200.0F, (HEIGHT - backgroundBox.height) / 2.0F);
    soundPosition = new Vector2(1400.0F, HEIGHT / 2.0F + 500.0F);  
    musicPosition = new Vector2(1400.0F, HEIGHT / 2.0F + 100.0F);     
    fontPosition = new Vector2(1400.0F, HEIGHT / 2.0F - 300.0F);     
    backPosition = new Vector2(1400.0F, HEIGHT / 2.0F - 700.0F);
    selectedBox = backButton;
  }

  @Override public void update() {  
    if (isClicked(soundBar, true)) {
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
    else if (isClicked(fontBar, true)) {
      if (selectedBox == fontBar) adjustFont(true);
      else selectedBox = fontBar;  
    } else if (isClicked(fontBar, false)) {
      if (selectedBox == fontBar) adjustFont(false);
      else selectedBox = fontBar;  
    } else if (isAxisPushed(fontBar) == 1) adjustFont(true);
    else if (isAxisPushed(fontBar) == -1) adjustFont(false);    
    else if (selectedBox == soundBar) {
      if (control.axisY == -1) selectedBox = musicBar;
    } else if (selectedBox == musicBar) {
      if (control.axisY == -1) selectedBox = fontBar;
      else if (control.axisY == 1) selectedBox = soundBar;
    } else if (selectedBox == fontBar) {
      if (control.axisY == -1) selectedBox = backButton;
      else if (control.axisY == 1) selectedBox = musicBar;
    } else if (selectedBox == backButton) {
      if (control.axisY == 1) selectedBox = fontBar;
    } 
  }

  @Override public void drawContent() { 
    backgroundBox.draw(backgroundPosition);
    soundBar.draw(soundPosition, profile.soundVolume, isSelected(soundBar) || control.isPointed(soundBar.polygon));
    musicBar.draw(musicPosition, profile.musicVolume, isSelected(musicBar) || control.isPointed(musicBar.polygon));
    fontBar.draw(fontPosition, profile.textSize, isSelected(fontBar) || control.isPointed(fontBar.polygon));
    backButton.draw(backPosition, isSelected(backButton) || control.isPointed(backButton.polygon));
  }
  
  protected void adjustSound(boolean forward) {
    if (forward && profile.soundVolume == 4 || !forward && profile.soundVolume == 0) return;    
    audio.setVolumes((profile.soundVolume += forward ? 1 : -1) * 0.25F, profile.musicVolume * 0.25F);
    audio.playSound("option_select", 1.0F);
  }
  
  protected void adjustMusic(boolean forward) {
    if (forward && profile.musicVolume == 4 || !forward && profile.musicVolume == 0) return;
    audio.setVolumes(profile.soundVolume * 0.25F, (profile.musicVolume += forward ? 1 : -1) * 0.25F);    
  }
  
  protected void adjustFont(boolean forward) {
    if (forward && profile.textSize == 2 || !forward && profile.textSize == 0) return;        
    textFont = textFonts[profile.textSize += forward ? 1 : -1];
    audio.playSound("option_select", 1.0F);
  }
}
