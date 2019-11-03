import java.util.ArrayList;

public class QuantumFormulas {

	final private static double PI = Math.PI;
	private static double c = 299792458;
	final private static double massProton = 1.672649 * Math.pow(10, -27); // in kg
	static double GevScale = 1 / (1.60218 * Math.pow(10, -10));
	static Vector2D zero = new Vector2D(0, 0);
	static int jetRadius = 5;

	public static int typeCollision(Particle p) {
		if (protonEnergy(p) > 0 && protonEnergy(p) < 0.01) {
			return 0;
		} else if (protonEnergy(p) > 0.01 && protonEnergy(p) < 1) {
			return 1;
		} else if (protonEnergy(p) > 1) {
			return 2;
		} else {
			return -1;
		}
	}

	public static double getGamma(Particle p) {
		double velocity = p.getVelocity().magnitude();
		double gamma = 1.0 / (Math.sqrt(1 - (Math.pow(velocity, 2) / (c * c))));
		return gamma;

	}

	static double pEnergy;

	public static double protonEnergy(Particle p) {
		pEnergy = (getGamma(p) - 1) * massProton * c * c * GevScale; // in GeV
		return pEnergy;
	}

	static int num;

	public static int parNum(Particle p) {
		num = (int) ((-0.577) + (0.394) * Math.log(protonEnergy(p) * protonEnergy(p))
				+ (0.213) * Math.pow(Math.log(protonEnergy(p) * protonEnergy(p)), 2)
				+ (0.005) * protonEnergy(p) * protonEnergy(p)); // number
		return num;

	}

	public static ArrayList<Particle> getJetParticles(Particle p) {
		double energy = protonEnergy(p) * 1000;
		int jetNum = parNum(p);
		ArrayList<Particle> list = new ArrayList();
		int pionNum = 0;
		int kaonNum = 0;
		int dMesonNum = 0;
		int strangeNum = 0;
		int charmedNum = 0;

		if (energy <= Particle.mPion) {
			pionNum = jetNum;
		} else if (energy <= Particle.mKaon) {
			pionNum = (int) (0.75 * jetNum);
			kaonNum = jetNum - pionNum;
		} else if (energy <= Particle.mDMeson) {
			pionNum = (int) (0.6 * jetNum);
			kaonNum = (int) (0.25 * jetNum);
			dMesonNum = jetNum - pionNum - kaonNum;
		} else if (energy <= Particle.mStrangeDMeson) {
			pionNum = (int) (0.50 * jetNum);
			kaonNum = (int) (0.20 * jetNum);
			dMesonNum = (int) (0.18 * jetNum);
			strangeNum = jetNum - dMesonNum - pionNum - kaonNum;
		} else if (energy <= Particle.mCharmedBMeson) {
			pionNum = (int) (0.40 * jetNum);
			kaonNum = (int) (0.20 * jetNum);
			dMesonNum = (int) (0.17 * jetNum);
			strangeNum = (int) (0.13 * jetNum);
			charmedNum = jetNum - strangeNum - dMesonNum - pionNum - kaonNum;
		}

		for (int i = 0; i < jetNum; i++) {
			if (i < pionNum) {
				list.add(new Particle(zero, zero, zero, jetRadius, "pion"));
			} else if (i < kaonNum + pionNum) {
				list.add(new Particle(zero, zero, zero, jetRadius, "kaon"));
			} else if (i < dMesonNum + pionNum + kaonNum) {
				list.add(new Particle(zero, zero, zero, jetRadius, "DMeson"));
			} else if (i < strangeNum + pionNum + kaonNum + dMesonNum) {
				list.add(new Particle(zero, zero, zero, jetRadius, "strangeDMeson"));
			} else if (i < charmedNum + pionNum + kaonNum + dMesonNum + strangeNum) {
				list.add(new Particle(zero, zero, zero, jetRadius, "charmedBMeson"));
			}
		}
		return list;
	}

}
