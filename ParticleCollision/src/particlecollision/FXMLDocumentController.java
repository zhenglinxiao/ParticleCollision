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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author lxz19
 */
public class FXMLDocumentController implements Initializable {

    // GUI instance variables
    private int buttonY = 450;
    @FXML
    private AnchorPane pane; // 800 x 514 or 14:9 aspect ratio
    private Button restart = new Button();
    private Button quit = new Button();
    private Button acc = new Button();
    private Button start = new Button();
    private Label status = new Label();
    private Label fieldLabel = new Label();
    private Slider fieldStrength = new Slider();
    private ArrayList<Circle> lines = new ArrayList();
    
    // Animation instance variables
    private double lastFrameTime = 0.0;
    long initialTime = System.nanoTime();

    // Sim mechanics instance variables
    private ArrayList<Particle> jetList = new ArrayList();
    private boolean collision;
    private boolean jetCollision;
    private Vector2D collisionPos;
    private static int INC = 18;
    Vector2D zero = new Vector2D(0, 0);
    Vector2D leftIniPos = new Vector2D(200, 250);
    Vector2D rightIniPos = new Vector2D(600, 250);
    Vector2D magneticField = new Vector2D(0, 150);
    double iniRadius = 12;
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

            // Pre-collision
            if (!collision) {
                PhysicsFormulas.updatePosition(rightProton, frameDeltaTime);
                PhysicsFormulas.updatePosition(leftProton, frameDeltaTime);

                // Check when initial particles collide
                if (rightProton.getPosition().getX() - rightProton.getCircle().getRadius() <= leftProton.getPosition().getX() + leftProton.getCircle().getRadius()) {
                    // Collision animation thing?
                    collision = true;
                    collisionPos = new Vector2D(rightProton.getPosition().getX() - rightProton.getCircle().getRadius(), rightProton.getPosition().getY());
                    acc.setDisable(true);
                    // Calculate energy
                    switch (QuantumFormulas.typeCollision(leftProton)) {
                        case 0:
                            status.setText("The protons are repelling each other.");
                            leftProton.setVelocity(-INC * 3, leftProton.getVelocity().getY());
                            rightProton.setVelocity(INC * 3, rightProton.getVelocity().getY());
                            break;// REPEL
                        case 1:
                            System.out.println("deuteron");
                            status.setText("You've created a deuteron!");
                            Particle deuteron = new Particle(collisionPos, zero, zero, 30, "else");
                            deuteron.getCircle().setFill(AssetManager.deuteron);
                            removeFromPane(rightProton.getCircle());
                            removeFromPane(leftProton.getCircle());
                            jetList.add(deuteron);
                            break;// deuteron
                        case 2:
                            jetCollision = true;
                            jetList.addAll(QuantumFormulas.getJetParticles(leftProton));
                            if(jetList.size() == 0){
                                status.setText("The collision did not generate enough energy to expel quarks.");
                            }
                            else{
                                status.setText("You've created a jet of particles!");
                            }
                            removeFromPane(rightProton.getCircle());
                            removeFromPane(leftProton.getCircle());
                            break;// jet particles!!!!!
                        default:
                            System.out.println("type collision fail");
                    }
                    
                    int range = (20 + 20) + 1;
                    Vector2D pSum = new Vector2D(0, 0);
                    for(int i = 0; i < jetList.size(); i++){
                        Particle tmp = jetList.get(i);
                        tmp.setPosition(collisionPos);
                        tmp.getCircle().setCenterX(collisionPos.getX());
                        tmp.getCircle().setCenterY(collisionPos.getY());
                        addToPane(tmp.getCircle());
                        
                        // set velocities
                        if(i == jetList.size() - 1){
                            Vector2D vel = (zero.sub(pSum)).mult(1/tmp.getMass());
                            tmp.setVelocity(vel);
                        }else{
                            Vector2D vel = new Vector2D(Math.random() * range - 10, Math.random() * range - 20);
                            tmp.setVelocity(vel);
                            pSum = pSum.add(vel.mult(tmp.getMass()));
                        }
                    }
                }
            } // Post-collision
            else {
                if (jetCollision) {
                    // update jetparticles positions
                    jetList.forEach((part) -> {
                        // line
                        Circle temp = new Circle();
                        temp.setRadius(1.5);
                        temp.setCenterX(part.getPosition().getX());
                        temp.setCenterY(part.getPosition().getY());
                        temp.setFill(part.getColor());
                        temp.setOpacity(0.2);
                        lines.add(temp);
                        addToPane(temp);
                        
                        part.setAcceleration(magneticField.mult(part.getCharge()/part.getMass()));
                        PhysicsFormulas.updatePosition(part, frameDeltaTime);
                    });
                    System.out.println(jetList.get(0).getAcceleration());
                } else if (collision) {
                    // update right/left positions
                    leftProton.setVelocity(leftProton.getVelocity().getX() - 0.1*INC, leftProton.getVelocity().getY());
                    rightProton.setVelocity(rightProton.getVelocity().getX() + 0.1*INC, rightProton.getVelocity().getY());
                    PhysicsFormulas.updatePosition(rightProton, frameDeltaTime);
                    PhysicsFormulas.updatePosition(leftProton, frameDeltaTime);
                }
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

        // Labels
        status.setText("");
        status.setTextFill(Color.WHITE);
        status.setLayoutX(350);
        status.setLayoutY(100);
        addToPane(status);

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
                lines.forEach((circle) -> {
                    removeFromPane(circle);
                });
                lines.clear();
                jetList.forEach((part) -> {
                    removeFromPane(part.getCircle());
                });
                jetList.clear();
                if (collision && !jetCollision) {
                    removeFromPane(rightProton.getCircle());
                    removeFromPane(leftProton.getCircle());
                }
                status.setText("");
                rightProton = new Particle(rightIniPos, zero, zero, iniRadius, "proton");
                leftProton = new Particle(leftIniPos, zero, zero, iniRadius, "proton");
                addToPane(rightProton.getCircle());
                addToPane(leftProton.getCircle());
                collision = false;
                jetCollision = false;
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
                collision = false;
                jetCollision = false;
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
                leftProton.setVelocity(leftProton.getVelocity().getX() + INC, leftProton.getVelocity().getY());
                rightProton.setVelocity(rightProton.getVelocity().getX() - INC, rightProton.getVelocity().getY());
            }
        });

        addToPane(restart);
        addToPane(quit);
        addToPane(start);
        addToPane(acc);
    }
}
