package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound(){
        soundURL[0] = getClass().getResource("/sound/Hyrule_Field.wav");
        soundURL[1] = getClass().getResource("/sound/object/LOZ_Get_Item.wav");
        soundURL[2] = getClass().getResource("/sound/unlock.wav");
        soundURL[3] = getClass().getResource("/sound/combat/LOZ_Enemy_Die.wav");
        soundURL[4] = getClass().getResource("/sound/combat/LOZ_Enemy_Hit.wav");
        soundURL[5] = getClass().getResource("/sound/combat/LOZ_Link_Hurt.wav");
        soundURL[6] = getClass().getResource("/sound/combat/LOZ_Sword_Slash.wav");
        soundURL[7] = getClass().getResource("/sound/object/LOZ_Fanfare.wav");
        soundURL[8] = getClass().getResource("/sound/Game_Over.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch (Exception e){
            e.printStackTrace();

        }

    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}
