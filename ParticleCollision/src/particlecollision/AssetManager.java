package particlecollision;

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

    static protected Background backgroundImage;
    static protected ImagePattern logo;
    static protected ImagePattern proton;
    static protected Media backgroundMusic;

    static private String fileURL(String relativePath) {
        return new File(relativePath).toURI().toString();
    }

    static public void preloadAllAssets() {
        Image background = new Image(fileURL("./assets/backgroundsmall.png"));
        backgroundImage = new Background(new BackgroundImage(background,BackgroundRepeat.ROUND,BackgroundRepeat.ROUND,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT));
        logo = new ImagePattern(new Image(fileURL("./assets/logo.png")));
        proton = new ImagePattern(new Image(fileURL("./assets/proton.png")));
    }

}
