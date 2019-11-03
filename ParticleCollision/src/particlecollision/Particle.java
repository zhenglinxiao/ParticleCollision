package particlecollision;

import javafx.scene.paint.Color;

/**
 * This code implements a particle in the program.
 *
 * @author Sara Dellidj
 *
 */
public class Particle extends GameObject {

    private String name;
    private double mass;
    private double charge;
    private double minimalEnergy;
    private Color color;

    // Mass of proton in MeV/c^2.
    final private double mP = 938;

    // Rest mass of mesons in MeV/c^2.
    final static protected double mPion = 139.57018;
    final static protected double mKaon = 493.677;
    final static protected double mDMeson = 1869.62;
    final static protected double mStrangeDMeson = 1968.49;
    final static protected double mCharmedBMeson = 6276;

    // Speed of light in m/s^2.
    final private double c = 299792458;

    public double getMinimalEnergy() {
        return minimalEnergy;
    }
    
    public double getMass(){
        return mass;
    }
    
    public String getName(){
        return name;
    }

    public Color getColor(){
        return color;
    }
    
    public Particle(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius, String name) {
        super(position, velocity, acceleration, radius);
        name = name.toLowerCase();
        this.name = name;
        
        switch (name) {
            case "pion":
                this.circle.setFill(Color.GREEN);
                this.color = Color.GREEN;
                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mPion * Math.pow(c, 2); //MeV
                this.mass = mPion; 
                break;
            case "kaon":
                this.circle.setFill(Color.BLUE);
                this.color = Color.BLUE;
                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mKaon * Math.pow(c, 2);
                this.mass = mKaon;
                break;
            case "dmeson":
                this.circle.setFill(Color.AQUAMARINE);
                this.color = Color.AQUAMARINE;
                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mDMeson * Math.pow(c, 2);
                this.mass = mDMeson;
                break;
            case "strangedmeson":
                this.circle.setFill(Color.CHARTREUSE);
                this.color = Color.CHARTREUSE;
                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mStrangeDMeson * Math.pow(c, 2);
                this.mass = mStrangeDMeson;
                break;
            case "charmedbmeson":
                this.circle.setFill(Color.DEEPPINK);
                this.color = Color.DEEPPINK;
                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mCharmedBMeson * Math.pow(c, 2);
                this.mass = mCharmedBMeson;
                break;
            case "proton":
                this.circle.setFill(AssetManager.proton);
                this.minimalEnergy = 2 * mP * Math.pow(c, 2);
                break;
            default:
                break;
        }
    }
}
