package polygon_burst.survivor01.sections;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.Gdx;
import java.util.HashMap;

public class Audio implements Disposable {  
  protected HashMap<String, Music> music = new HashMap<>();
  protected HashMap<String, Sound> sounds = new HashMap<>();
  protected Sound[] optionSelect, ah1zGun, ka50Gun;
  protected Music currentMusic;
  protected float soundVolume, musicVolume;

  public Audio() { 
    music.put("in_the_club", currentMusic = Gdx.audio.newMusic(Gdx.files.internal("music/escape_the_grid.ogg")));    
    for (HashMap.Entry<String, Music> entry : music.entrySet()) entry.getValue().setLooping(true);
    for (int index = (optionSelect = new Sound[4]).length; index-- != 0; ) 
      optionSelect[index] = Gdx.audio.newSound(Gdx.files.internal("sounds/confirmation_" + index + ".ogg"));
    for (int index = (ah1zGun = new Sound[4]).length; index-- != 0; ) 
      ah1zGun[index] = Gdx.audio.newSound(Gdx.files.internal("sounds/ah1z_gun_" + index + ".ogg"));
    for (int index = (ka50Gun = new Sound[2]).length; index-- != 0; ) 
      ka50Gun[index] = Gdx.audio.newSound(Gdx.files.internal("sounds/ka50_gun_" + index + ".ogg"));
    //sounds.put("ufo_flight", Gdx.audio.newSound(Gdx.files.internal("sounds/ufo_flight.ogg")));    
    //if (!musicMute) currentMusic.play();
  }
  
  @Override public void dispose() {    
    for (Sound sound : ka50Gun) sound.dispose();
    for (Sound sound : ah1zGun) sound.dispose();
    for (Sound sound : optionSelect) sound.dispose();
    for (HashMap.Entry<String, Music> entry : music.entrySet()) {
      entry.getValue().stop();
      entry.getValue().dispose();    
    }
    for (HashMap.Entry<String, Sound> entry : sounds.entrySet()) entry.getValue().dispose();    
  }
  
  public void setVolumes(float soundVolume, float musicVolume) {
    this.soundVolume = soundVolume;
    currentMusic.setVolume(this.musicVolume = musicVolume);
  } 
  
  public void playSound(String title, float pan) {
    if (title.equals("option_select")) optionSelect[MathUtils.random(optionSelect.length - 1)].play(soundVolume, 1.0F, pan);
    else if (title.equals("ah1z_gun")) ah1zGun[MathUtils.random(ah1zGun.length - 1)].play(soundVolume, 1.0F, pan);
    else if (title.equals("ka50_gun")) ka50Gun[MathUtils.random(ka50Gun.length - 1)].play(soundVolume, 1.0F, pan);
    else sounds.get(title).play(soundVolume, 1.0F, pan);
  } 
  
  public void playMusic(String title) {
    currentMusic.pause();
    (currentMusic = music.get(title)).setVolume(musicVolume);
    currentMusic.play();
  }
}
