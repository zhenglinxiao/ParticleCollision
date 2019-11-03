package particlecollision;

import javafx.scene.paint.Color;

/**
 * This code implements a particle in the program.
 *
 * @author Sara Dellidj
 *
 */
public class Particle extends GameObject {
    
    final private double e = 1.60217646;//* Math.pow(10, -19);

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
        double tmp =  this.mass;
        System.out.println(tmp);
        return tmp;
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
    
    public double getCharge(){
        return charge;
    }
    
    public Particle(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius, String name) {
        super(position, velocity, acceleration, radius);
        name = name.toLowerCase();
        this.name = name;
        
        int sign = 1;
        if(Math.random() < 0.5){
            sign = -1;
        }
        
        switch (name) {
            case "pion":
                this.circle.setFill(Color.GREEN);
                this.color = Color.GREEN;
//                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mPion * Math.pow(c, 2); //MeV
                this.mass = mPion; 
                this.charge = sign * e;
                break;
            case "kaon":
                this.circle.setFill(Color.BLUE);
                this.color = Color.BLUE;
//                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mKaon * Math.pow(c, 2);
                this.mass = mKaon;
                this.charge = sign * e;
                break;
            case "dmeson":
                this.circle.setFill(Color.AQUAMARINE);
                this.color = Color.AQUAMARINE;
//                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mDMeson * Math.pow(c, 2);
                this.mass = mDMeson;
                this.charge = sign * e;
                break;
            case "strangedmeson":
                this.circle.setFill(Color.CHARTREUSE);
                this.color = Color.CHARTREUSE;
//                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mStrangeDMeson * Math.pow(c, 2);
                this.mass = mStrangeDMeson;
                this.charge = -1 * e / 3;
                break;
            case "charmedbmeson":
                this.circle.setFill(Color.DEEPPINK);
                this.color = Color.DEEPPINK;
//                this.minimalEnergy = 2 * mP * Math.pow(c, 2) + mCharmedBMeson * Math.pow(c, 2);
                this.mass = mCharmedBMeson;
                this.charge = 2 * e / 3;
                break;
            case "proton":
                this.circle.setFill(AssetManager.proton);
                this.minimalEnergy = 2 * mP * Math.pow(c, 2);
                this.charge = e * Math.pow(10, -19);
                break;
            default:
                break;
        }
    }
}
