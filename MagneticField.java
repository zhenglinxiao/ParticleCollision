
public class MagneticField {
	
	private double X;
	private double Y;

	public MagneticField(double X, double Y) {
		this.X = X;
		this.Y = Y;
	}
	double magnitude;
	public double getMagnitude() {
		magnitude =Math.sqrt(Math.pow(X, 2)+Math.pow(Y, 2));
		return magnitude;
	}
	
	double angle;
	public double getAngle() {
		angle = Math.atan(Y/X);
		return angle;
	}
}
