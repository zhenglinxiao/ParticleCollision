import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.paint.ImagePattern;

public class AssetManager {
	 static private Background backgroundImage = null;
	 static private ImagePattern logo = null;
	 static protected ImagePattern proton = null;
	 static private Media backgroundMusic = null;
	 static private ImagePattern deuteron =null;
	  static private String fileURL(String relativePath)
	    {
	        return new File(relativePath).toURI().toString();
	    }
	 static public void preloadAllAssets()
	    {
		 Image background = new Image(fileURL("./assets/background.png"));
		 backgroundImage = new Background(
                 new BackgroundImage(background, 
                                     BackgroundRepeat.ROUND, 
                                     BackgroundRepeat.ROUND, 
                                     BackgroundPosition.DEFAULT,
                                     BackgroundSize.DEFAULT));
		 logo = new ImagePattern(new Image(fileURL("./assets/logo.png")));
		 proton = new ImagePattern(new Image(fileURL("./assets/proton.png")));
		 deuteron = new ImagePattern(new Image(fileURL("./assets/deuteron.png")));
	    }
	 
}
