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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author lxz19
 */
public class FXMLDocumentController implements Initializable {

    // GUI instance variables
    private int buttonW = 150;
    private int buttonH = 30;
    private int buttonX = 1011 - buttonW - 50;
    @FXML
    private AnchorPane pane;
    private Button restart = new Button();
    private Button quit = new Button();
    private Button acc = new Button();
    private Button start = new Button();
    private Label status = new Label();
    private Label fieldLabel = new Label();
    private Label protonData = new Label();
    private Label pionLabel = new Label();
    private Label kaonLabel = new Label();
    private Label mesLabel = new Label();
    private Label strangeLabel = new Label();
    private Label chaLabel = new Label();
    private ArrayList<Circle> lines = new ArrayList();
    private MediaPlayer mediaPlayer;
    private AudioClip collisionSound;

    // Animation instance variables
    private double lastFrameTime = 0.0;
    long initialTime = System.nanoTime();

    // Sim mechanics instance variables
    private ArrayList<Particle> jetList = new ArrayList();
    private boolean collision;
    private boolean jetCollision;
    private boolean repel;
    private Vector2D collisionPos;
    private static int INC = 30;
    private Vector2D zero = new Vector2D(0, 0);
    private Vector2D leftIniPos = new Vector2D(200, 300);
    private Vector2D rightIniPos = new Vector2D(800, 300);
    private Vector2D magneticField = new Vector2D(150, 0);
    private double iniRadius = 12;
    private Particle rightProton;
    private Particle leftProton;
    final private double k = 9 * Math.pow(10, 42);

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
                protonData.setText(String.format("Proton speed: %.2f", PhysicsFormulas.scale(leftProton.getVelocity().magnitude())));
                if (repel) {
                    double coulombs = k * Math.pow(rightProton.getCharge(), 2) / Math.pow((rightProton.getPosition().getX() - leftProton.getPosition().getX()), 2);
                    rightProton.setAcceleration(coulombs, 0);
                    leftProton.setAcceleration(-coulombs, 0);
                }
                PhysicsFormulas.updatePosition(rightProton, frameDeltaTime);
                PhysicsFormulas.updatePosition(leftProton, frameDeltaTime);

                // Check when initial particles collide
                if (rightProton.getPosition().getX() - rightProton.getCircle().getRadius() <= leftProton.getPosition().getX() + leftProton.getCircle().getRadius()) {
                    collisionSound.play();
                    collision = true;
                    collisionPos = new Vector2D(rightProton.getPosition().getX() - rightProton.getCircle().getRadius(), rightProton.getPosition().getY());
                    acc.setDisable(true);
                    // Calculate energy
                    switch (QuantumFormulas.typeCollision(leftProton)) {
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
                            fieldLabel.setText("Magnetic Field: ON");
                            jetList.addAll(QuantumFormulas.getJetParticles(leftProton));
                            if (jetList.isEmpty()) {
                                status.setText("Energy is too low: no quarks");
                            } else {
                                status.setText("You've created a jet of particles!");
                            }
                            removeFromPane(rightProton.getCircle());
                            removeFromPane(leftProton.getCircle());
                            break;// jet particles!!!!!
                        default:
                            System.out.println("type collision fail");
                    }

                    int m = 25;
                    int range = (m + m) + 1;
                    Vector2D pSum = new Vector2D(0, 0);
                    for (int i = 0; i < jetList.size(); i++) {
                        Particle tmp = jetList.get(i);
                        tmp.setPosition(collisionPos);
                        tmp.getCircle().setCenterX(collisionPos.getX());
                        tmp.getCircle().setCenterY(collisionPos.getY());
                        addToPane(tmp.getCircle());

                        // set velocities
                        if (i == jetList.size() - 1) {
                            Vector2D vel = (zero.sub(pSum)).mult(1 / tmp.getMass());
                            pSum = pSum.add(vel.mult(tmp.getMass()));
                            tmp.setVelocity(vel);
                        } else {
                            Vector2D vel = new Vector2D(Math.random() * range - m, Math.random() * range - m);
                            tmp.setVelocity(vel);
                            pSum = pSum.add(vel.mult(tmp.getMass()));
                        }
                    }
//                    System.out.println(pSum);
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
                        lines.add(temp);
                        addToPane(temp);

                        part.setAcceleration(magneticField.mult(part.getCharge() / part.getMass()));
                        PhysicsFormulas.updatePosition(part, frameDeltaTime);
                    });
                    lines.forEach((circle) -> {
                        circle.setOpacity(circle.getOpacity() - 0.002);
                    });
                }
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lastFrameTime = 0.0f;

        AssetManager.preloadAllAssets();
        pane.setBackground(AssetManager.backgroundImage);
        collisionSound =  AssetManager.collisionEffect;
        mediaPlayer = new MediaPlayer(AssetManager.backgroundMusic);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();

        // Initial particles
        rightProton = new Particle(rightIniPos, zero, zero, iniRadius, "proton");
        leftProton = new Particle(leftIniPos, zero, zero, iniRadius, "proton");
        addToPane(rightProton.getCircle());
        addToPane(leftProton.getCircle());

        // Labels
        status.setTextFill(Color.WHITE);
        status.setLayoutX(0);
        status.setPrefWidth(1011);
        status.setLayoutY(580);
        status.setAlignment(Pos.CENTER);
        status.setFont(Font.font("Balls on the rampage", 35));
        addToPane(status);

        int leftDataAlign = 50;
        fieldLabel.setTextFill(Color.WHITE);
        fieldLabel.setLayoutX(leftDataAlign);
        fieldLabel.setLayoutY(50);
        fieldLabel.setFont(Font.font("Britannic Bold", 15));
        addToPane(fieldLabel);
        protonData.setTextFill(Color.WHITE);
        protonData.setLayoutX(leftDataAlign);
        protonData.setLayoutY(70);
        protonData.setFont(Font.font("Britannic Bold", 15));
        addToPane(protonData);
        
        int rightDataAlign = 1011 - 150;
        Label legend = new Label("Subatomic particles");
        legend.setTextFill(Color.WHITE);
        legend.setFont(Font.font("Britannic Bold", 15));
        legend.setUnderline(true);
        legend.setLayoutX(rightDataAlign);
        legend.setLayoutY(40);
        addToPane(legend);
        
        Particle pion = new Particle(new Vector2D(rightDataAlign, 80), zero, zero, 5, "pion");
        pionLabel.setText("Pion");
        pionLabel.setTextFill(Color.WHITE);
        pionLabel.setFont(Font.font("Britannic Bold", 15));
        pionLabel.setLayoutX(rightDataAlign + 15);
        pionLabel.setLayoutY(80 - 8);
        Particle kaon = new Particle(new Vector2D(rightDataAlign, 100), zero, zero, 5, "kaon");
        kaonLabel.setText("Kaon");
        kaonLabel.setTextFill(Color.WHITE);
        kaonLabel.setFont(Font.font("Britannic Bold", 15));
        kaonLabel.setLayoutX(rightDataAlign + 15);
        kaonLabel.setLayoutY(100 - 8);
        Particle meson = new Particle(new Vector2D(rightDataAlign, 120), zero, zero, 5, "dmeson");
        mesLabel.setText("D Meson");
        mesLabel.setTextFill(Color.WHITE);
        mesLabel.setFont(Font.font("Britannic Bold", 15));
        mesLabel.setLayoutX(rightDataAlign + 15);
        mesLabel.setLayoutY(120 - 8);        
        Particle strange = new Particle(new Vector2D(rightDataAlign, 140), zero, zero, 5, "strangedmeson");
        strangeLabel.setText("Strange D Meson");
        strangeLabel.setTextFill(Color.WHITE);
        strangeLabel.setFont(Font.font("Britannic Bold", 15));
        strangeLabel.setLayoutX(rightDataAlign + 15);
        strangeLabel.setLayoutY(140 - 8);         
        Particle charmed = new Particle(new Vector2D(rightDataAlign, 160), zero, zero, 5, "charmedbmeson");
        chaLabel.setText("Charmed B Meson");
        chaLabel.setTextFill(Color.WHITE);
        chaLabel.setFont(Font.font("Britannic Bold", 15));
        chaLabel.setLayoutX(rightDataAlign + 15);
        chaLabel.setLayoutY(160 - 8); 
        addToPane(pionLabel);
        addToPane(kaonLabel);
        addToPane(mesLabel);
        addToPane(strangeLabel);
        addToPane(chaLabel);
        addToPane(pion.getCircle());
        addToPane(kaon.getCircle());
        addToPane(meson.getCircle());
        addToPane(strange.getCircle());
        addToPane(charmed.getCircle());

        // Buttons
        restart.setText("Restart");
        restart.setPrefWidth(buttonW);
        restart.setPrefHeight(buttonH);
        restart.setLayoutX(buttonX);
        restart.setLayoutY(520);
        restart.setDisable(true);
        restart.setFont(Font.font("Balls on the rampage", 25));
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
//                if (collision && !jetCollision) {
                    removeFromPane(rightProton.getCircle());
                    removeFromPane(leftProton.getCircle());
//                }
                status.setText("");
                protonData.setText("");
                fieldLabel.setText("");
                rightProton = new Particle(rightIniPos, zero, zero, iniRadius, "proton");
                leftProton = new Particle(leftIniPos, zero, zero, iniRadius, "proton");
                addToPane(rightProton.getCircle());
                addToPane(leftProton.getCircle());
                collision = false;
                jetCollision = false;
                repel = false;
                start.setDisable(false);
                acc.setDisable(true);
                restart.setDisable(true);
            }
        });

        quit.setText("Quit");
        quit.setPrefWidth(buttonW);
        quit.setPrefHeight(buttonH);
        quit.setLayoutX(buttonX);
        quit.setLayoutY(570);
        quit.setFont(Font.font("Balls on the rampage", 25));
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        start.setText("Start");
        start.setPrefWidth(buttonW);
        start.setPrefHeight(buttonH);
        start.setLayoutX(50);
        start.setLayoutY(520);
        start.setFont(Font.font("Balls on the rampage", 25));
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Starting...");
                protonData.setText("Proton speed: 0");
                fieldLabel.setText("Magnetic Field: OFF");
                restart.setDisable(false);
                acc.setDisable(false);
                start.setDisable(true);
                repel = false;
                collision = false;
                jetCollision = false;
                anim.start();
            }
        });

        acc.setText("Accelerate!");
        acc.setPrefWidth(buttonW);
        acc.setPrefHeight(buttonH);
        acc.setLayoutX(50);
        acc.setLayoutY(570);
        acc.setDisable(true);
        acc.setFont(Font.font("Balls on the rampage", 25));
        acc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicking...");
                repel = true;
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
