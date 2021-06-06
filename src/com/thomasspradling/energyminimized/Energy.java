package com.thomasspradling.energyminimized;

public class Energy {	
	
	//////// POTENTIALS ////////
	public static double singlePotentialStretch(Particle atom1, Particle atom2, double r0, double k) {
		Vector r1 = atom1.getPosition();
		Vector r2 = atom2.getPosition();
		// Get r2 - r1 displacement:
		Vector displacement = Vector.add(r2, Vector.multiply(-1, r1));
		double U = (k * Math.pow(displacement.getMagnitude() - r0, 2))/2;
		return U;
	}
	public static double singlePotentialVanDer(Particle atom1, Particle atom2, double e, double s) {
		Vector r1 = atom1.getPosition();
		Vector r2 = atom2.getPosition();
		// Get r2 - r1 displacement
		Vector displacement = Vector.add(r1, Vector.multiply(-1, r2));
		double U = 4*e*(Math.pow((s/displacement.getMagnitude()), 12)
				- Math.pow((s/displacement.getMagnitude()), 6));
		return U;
	}
		// Total
	public static double totalPotential(Particle[] molecule, double r0, double k, double e, double s) {
		int N = molecule.length;
		double U = 0;
		// Stretch. Iterate through each atom N and add each stretching component belonging to that atom
		for(int i = 0; i < N; i++) {
			double U1 = molecule[i].atom1 >= 0 ?
					singlePotentialStretch(molecule[i], molecule[molecule[i].atom1], r0, k) : 0;
			double U2 = molecule[i].atom2 >= 0 ?
					singlePotentialStretch(molecule[i], molecule[molecule[i].atom2], r0, k) : 0;
			double U3 = molecule[i].atom3 >= 0 ?
					singlePotentialStretch(molecule[i], molecule[molecule[i].atom3], r0, k) : 0;
			double U4 = molecule[i].atom4 >= 0 ?
					singlePotentialStretch(molecule[i], molecule[molecule[i].atom4], r0, k) : 0;
			double stretchU = U1 + U2 + U3 + U4;
			U += stretchU;
		}
		// van der Waals. Iterate through all without double counting:
		for (int i = 0; i < N - 1; i++) {
			for (int j = i + 1; j < N; j++) {
				double vanU = singlePotentialVanDer(molecule[i], molecule[j], e, s);
				U += vanU;
			}
		}
		return U;
	}
	
	//////// FORCES ////////
	public static Vector singleForceStretch(Particle atom1, Particle atom2, double r0, double k) {
		Vector r1 = atom1.getPosition();
		Vector r2 = atom2.getPosition();
		// Get r2 - r1 displacement:
		Vector displacement = Vector.add(r2, Vector.multiply(-1, r1));
		
		double Fmag = k * (displacement.getMagnitude() - r0)*2;
		Vector Fdir = Vector.multiply(1/displacement.getMagnitude(), displacement);
		
		Vector F = Vector.multiply(Fmag, Fdir);
		return F;
	}
	public static Vector singleForceVanDer(Particle atom1, Particle atom2, double e, double s) {
		Vector r1 = atom1.getPosition();
		Vector r2 = atom2.getPosition();
		// Get r2 - r1 displacement:
		Vector displacement = Vector.add(r1, Vector.multiply(-1, r2));
		double Fmag = 24*e*(2*(Math.pow(s, 12)/Math.pow(displacement.getMagnitude(), 13))
				- (Math.pow(s, 6)/Math.pow(displacement.getMagnitude(), 7)));
		Vector Fdir = Vector.multiply(1/displacement.getMagnitude(), displacement);
		Vector F = Vector.multiply(Fmag, Fdir);
		return F;
	}
		// Total
	public static Vector totalForce(int atom, Particle[] molecule, double r0, double k, double e, double s) {
		int N = molecule.length;
		Vector F = new Vector(0, 0, 0);
		// Stretch:
		Vector zero = new Vector(0, 0, 0);
		Vector F1 = molecule[atom].atom1 >= 0 ?
				singleForceStretch(molecule[atom], molecule[molecule[atom].atom1], r0, k) : zero;
		Vector F2 = molecule[atom].atom2 >= 0 ?
				singleForceStretch(molecule[atom], molecule[molecule[atom].atom2], r0, k) : zero;
		Vector F3 = molecule[atom].atom3 >= 0 ?
				singleForceStretch(molecule[atom], molecule[molecule[atom].atom3], r0, k) : zero;
		Vector F4 = molecule[atom].atom4 >= 0 ?
				singleForceStretch(molecule[atom], molecule[molecule[atom].atom4], r0, k) : zero;
		Vector stretchF = Vector.add4(F1, F2, F3, F4);
		F = Vector.add(F, stretchF);
		// van der Waals:
		for(int i = 0; i < N; i++) {
			if (i != atom) {
				Vector vanF = singleForceVanDer(molecule[atom], molecule[i], e, s);
				F = Vector.add(F, vanF);
			}
		}
		return F;
	}
}
