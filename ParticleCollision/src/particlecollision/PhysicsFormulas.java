package particlecollision;

/**
 *
 * @author lxz19
 */
public class PhysicsFormulas {
    
    final private static double c = 299792458;
    
    public static void updatePosition(Particle p, double t){
        Vector2D newPos = p.getPosition().add(p.getVelocity().mult(t)).add(p.getAcceleration().mult(Math.pow(t, 2) / 2));
        Vector2D newVel = p.getVelocity().add(p.getAcceleration().mult(t));
        
        p.setPosition(newPos);
        p.setVelocity(newVel);
    }
    
    public static double getMomentum(Particle p){
        double velocity = scale(p.getVelocity().magnitude());
        double mass = mevToKg(p.getMass());  
        double momentum = mass * velocity;        
        return momentum;
    }
    
    protected static double scale(double val){
        double newVal = val * 0.99999 * c / (400);
        
        if(newVal >= c){
            System.out.println("this");
            newVal = 0.999 * c;
        }
        
        return newVal;
    }
    
    private static double mevToKg(double meVMass){
        return meVMass * (16/9) * Math.pow(10, -30);
    }
   
    
}
