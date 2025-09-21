package polygon_burst.survivor01.sections;

import polygon_burst.survivor01.sections.Section;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Gdx;
//import org.jasypt.util.text.AES256TextEncryptor;

public class Profile implements Disposable {  
  static final public String PREFERENCES_FILE = 
    "polygon_burst.survivor01_" + (Section.HTML ? "html" : "desktop") + ".preferences";
  static final public int PROFILE_VERSION = 1; 
  //static protected AES256TextEncryptor encryptor = new AES256TextEncryptor();
  public String gameState = "NA";
  public int textSize = Section.HTML ? 1 : 0, soundVolume = 3, musicVolume = 1;
  
  public Profile() {
    //encryptor.setPassword("25DF43937ACF411D94E14DF748CEC196");
    Preferences preferences = Gdx.app.getPreferences(PREFERENCES_FILE);
    preferences.clear();
    preferences.flush();//*/
    textSize = preferences.getInteger("text_size", textSize);
    soundVolume = preferences.getInteger("sound_volume", soundVolume);      
    musicVolume = preferences.getInteger("music_volume", musicVolume);
    gameState = preferences.getString("game_state", gameState);
    //if (!gameState.equals("NA")) gameState = encryptor.decrypt(gameState);
  }
  
  @Override public void dispose() {
    Preferences preferences = Gdx.app.getPreferences(PREFERENCES_FILE);
    preferences.clear();
    preferences.putInteger("version", PROFILE_VERSION);
    preferences.putInteger("text_size", textSize);
    preferences.putInteger("sound_volume", soundVolume);
    preferences.putInteger("music_volume", musicVolume);     
    //preferences.putString("game_state", gameState.equals("NA") ? gameState : encryptor.encrypt(gameState));    
    preferences.flush();
  }
}
