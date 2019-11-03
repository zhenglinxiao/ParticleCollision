package particlecollision;

/**
 *
 * @author lxz19
 */
public class PhysicsFormulas {
    
    public static void updatePosition(Particle p, double t){
        Vector2D newPos = new Vector2D(p.getPosition().getX() + p.getVelocity().getX() * t + 0.5*p.getAcceleration().getX() * Math.pow(t,2),
                                       p.getPosition().getY() + p.getVelocity().getY() * t + 0.5*p.getAcceleration().getY() * Math.pow(t, 2));
        
        Vector2D newVel = new Vector2D(p.getVelocity().getX() + p.getAcceleration().getX() * t, p.getVelocity().getY() + p.getAcceleration().getY() * t);
        
        p.setPosition(newPos);
        p.setVelocity(newVel);
    }
    
    public static double getMomentum(Particle p){
        double velocity = p.getVelocity().magnitude();
        double mass = p.getMass(); // mass will be MeV/c^2
        return 2;
    }
    
    
   
    
}
