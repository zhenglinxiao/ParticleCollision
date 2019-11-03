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

    // Mass of proton in MeV/c^2.
    final private double mP = 938;

    // Rest mass of mesons in MeV/c^2.
    final private double mPion = 139.57018;
    final private double mKaon = 493.677;
    final private double mDMeson = 1869.62;
    final private double mStrangeDMeson = 1968.49;
    final private double mCharmedBMeson = 6276;

    // Speed of light in m/s^2.
    final private double c = 299792458;

    public double getMinimalEnergy() {
        return minimalEnergy;
    }
    
    public double getMass(){
        return mass;
    }

    public Particle(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius, String name) {
        super(position, velocity, acceleration, radius);
        name = name.toLowerCase();
        this.name = name;
        
        switch (name) {
            case "pion":
                this.circle.setFill(Color.GREEN);
                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mPion * Math.pow(c, 2);
                this.mass = mPion;
                break;
            case "kaon":
                this.circle.setFill(Color.BLUE);
                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mKaon * Math.pow(c, 2);
                this.mass = mKaon;
                break;
            case "dmeson":
                this.circle.setFill(Color.AQUAMARINE);
                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mDMeson * Math.pow(c, 2);
                this.mass = mDMeson;
                break;
            case "strangedmeson":
                this.circle.setFill(Color.CHARTREUSE);
                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mStrangeDMeson * Math.pow(c, 2);
                this.mass = mStrangeDMeson;
                break;
            case "charmedbmeson":
                this.circle.setFill(Color.DEEPPINK);
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
