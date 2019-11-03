package particlecollision;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author lxz19
 */
public class FXMLDocumentController implements Initializable {

    // GUI instance variables
    private int buttonY = 450;
    @FXML
    private AnchorPane pane; // 800 x 514 or 14:9 aspect ratio
    @FXML
    private Button restart = new Button();
    @FXML
    private Button quit = new Button();
    @FXML
    private Button acc = new Button();
    @FXML
    private Button start = new Button();
    @FXML
    private Label fieldLabel = new Label();
    @FXML
    private Slider fieldStrength = new Slider();

    // Animation instance variables
    private double lastFrameTime = 0.0;
    long initialTime = System.nanoTime();

    // Game mechanics instance variables
    private ArrayList<Particle> partList = new ArrayList();
    private ArrayList<Particle> jetPart = new ArrayList();
    private boolean collision;
    final private int ACCINCREMENT = 5;
    Vector2D zero = new Vector2D(0, 0);
    Vector2D leftIniPos = new Vector2D(200, 300);
    Vector2D rightIniPos = new Vector2D(600, 300);
    double iniRadius = 8;
    Particle rightProton;
    Particle leftProton;

    public void addToPane(Node node) {
        pane.getChildren().add(node);
    }

    public void removeFromPane(Node node) {
        pane.getChildren().remove(node);
    }

    AnimationTimer anim = new AnimationTimer() {
        @Override
        public void handle(long now) {
            // Time
            double currentTime = (now - initialTime) / 1000000000.0;
            double frameDeltaTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

//            System.out.println(collision);
            // Update particle locations
            if (!collision) {
                //set position to getnewposition(Particle, frameDeltaTime)
                PhysicsFormulas.updatePosition(rightProton, frameDeltaTime);
                PhysicsFormulas.updatePosition(leftProton, frameDeltaTime);
//                System.out.println(right.getPosition());
            } else {

            }

            // Pre-collision
            if (!collision) {
                // Check when initial particles collide
                if (rightProton.getPosition().getX() - rightProton.getCircle().getRadius() <= leftProton.getPosition().getX() + leftProton.getCircle().getRadius()) {
                    // get energy and momentum
                    // Collision animation thing
                    collision = true;
                    // Calculate energy using sara's methods
                    
                }
            } // Post-collision
            else {

            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lastFrameTime = 0.0f;

        AssetManager.preloadAllAssets();
        pane.setBackground(AssetManager.backgroundImage);

        // Initial particles
        rightProton = new Particle(rightIniPos, zero, zero, iniRadius, "proton");
        leftProton = new Particle(leftIniPos, zero, zero, iniRadius, "proton");
        addToPane(rightProton.getCircle());
        addToPane(leftProton.getCircle());

        // Buttons
        restart.setText("Restart simulation");
        restart.setLayoutX(350);
        restart.setLayoutY(buttonY);
        restart.setDisable(true);
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Restarting...");
                anim.stop();
                jetPart.forEach((part) -> {
                    removeFromPane(part.getCircle());
                });
                jetPart.clear();
                removeFromPane(rightProton.getCircle());
                removeFromPane(leftProton.getCircle());
                rightProton = new Particle(rightIniPos, zero, zero, iniRadius, "proton");
                leftProton = new Particle(leftIniPos, zero, zero, iniRadius, "proton");
                addToPane(rightProton.getCircle());
                addToPane(leftProton.getCircle());
                collision = false;
                start.setDisable(false);
                acc.setDisable(true);
                restart.setDisable(true);
            }
        });

        quit.setText("Quit simulation");
        quit.setLayoutX(500);
        quit.setLayoutY(buttonY);
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        start.setText("Start simulation");
        start.setLayoutX(60);
        start.setLayoutY(buttonY);
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Starting...");
                restart.setDisable(false);
                acc.setDisable(false);
                start.setDisable(true);
                anim.start();
            }
        });

        acc.setText("Click to accerelate!!");
        acc.setLayoutX(200);
        acc.setLayoutY(buttonY);
        acc.setDisable(true);
        acc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicking...");
                leftProton.setAcceleration(leftProton.getAcceleration().getX() + ACCINCREMENT, leftProton.getAcceleration().getY());
                rightProton.setAcceleration(rightProton.getAcceleration().getX() - ACCINCREMENT, rightProton.getAcceleration().getY());
                System.out.println(rightProton.getAcceleration());
            }
        });

        addToPane(restart);
        addToPane(quit);
        addToPane(start);
        addToPane(acc);
    }
}
