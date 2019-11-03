package particlecollision;

/**
 *
 * @author lxz19
 */
public class PhysicsFormulas {
    
    final private static double c = 299792458;
    
    public static void updatePosition(Particle p, double t){
        Vector2D newPos = new Vector2D(p.getPosition().getX() + p.getVelocity().getX() * t, p.getPosition().getY() + p.getVelocity().getY() * t);
//        Vector2D newPos = new Vector2D(p.getPosition().getX() + p.getVelocity().getX() * t + 0.5*p.getAcceleration().getX() * Math.pow(t,2),
//                                       p.getPosition().getY() + p.getVelocity().getY() * t + 0.5*p.getAcceleration().getY() * Math.pow(t, 2));
//        
//        Vector2D newVel = new Vector2D(p.getVelocity().getX() + p.getAcceleration().getX() * t, p.getVelocity().getY() + p.getAcceleration().getY() * t);
        
        p.setPosition(newPos);
//        p.setVelocity(newVel);
    }
    
    public static double getMomentum(Particle p){
        double velocity = scale(p.getVelocity().magnitude());
        double mass = mevToKg(p.getMass());  
        double momentum = mass * velocity;        
        return momentum;
    }
    
    protected static double scale(double val){
        return val * 0.99 * c / (250);
    }
    
    private static double mevToKg(double meVMass){
        return meVMass * (16/9) * Math.pow(10, -30);
    }
   
    
}
