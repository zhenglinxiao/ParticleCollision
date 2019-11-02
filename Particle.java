import com.sun.prism.paint.Color;

import javafx.scene.shape.Circle;

/*
 * This code implements a particle in the program.
 *
 */

public class Particle extends GameObject {

	final private double MASSPROTON = 1.602 * Math.pow(10, -19);// mass of
																// proton in Kg.
	final private double CHARGEPROTON = 1.67262192369 * Math.pow(10, -27);;// charge
																			// of
																			// proton
																			// in
																			// Coulombs.

	public Particle(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius) {
		super(position, velocity, acceleration, radius);

		this.circle.setFill(AssetManager.getRIADIMAGE());
	}

	public double getMassProton() {
		return MASSPROTON;
	}

	public double getChargeProton() {
		return CHARGEPROTON;
	}
}
