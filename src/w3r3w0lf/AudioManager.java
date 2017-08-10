package w3r3w0lf;

import java.io.File;
import java.net.Socket;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class AudioManager {
  public static void PlayAudio(String sourceFile)
  {
    String audioFile= "\\src\\resources\\" + sourceFile.toString();
    Media hit = new Media(new File(audioFile).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(hit); 
    mediaPlayer.play();
  }
  
}