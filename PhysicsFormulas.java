
public class PhysicsFormulas {

	final private static double PI = Math.PI;
	private static double c = 299792458;
	final private static double massProton = 1.672649*Math.pow(10, -27); // in kg
	double GevScale = 1/(1.60218*Math.pow(10, -10));
	
	double velocity;
	double gamma =  1.0/(Math.sqrt(1-(Math.pow(velocity, 2)/(c*c))));
	double protonEnergy = (gamma-1)*massProton*c*c*GevScale ;
	double num = (-0.577)+(0.394)*Math.log(protonEnergy*protonEnergy)+(0.213)*Math.pow(Math.log(protonEnergy*protonEnergy), 2)+(0.005)*protonEnergy*protonEnergy;   // number
	
	
	
	public double parNum(double energy) {
		switch (energy) {
		case (energy) num = 
		case
	}
		Particle
	public double parTypeGen(double energy) {
		switch (energy) {
		case ( <=  &&):4e
			
		}
	}
		public static Vector2D getNewPosition(Particle p, double t) {
			
		}
}
