package com.thomasspradling.energyminimized;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Main {
	// Add a source file here:
	
	final static String SOURCE = ######;
	static LinkedList<LinkedList<Double>> data = new LinkedList<LinkedList<Double>>();
	public static void main(String[] args) throws IOException {
		// btw for those of you who wanna use: create your molecule here. I know it's not very user friendly. The code here is for ethane
		Particle c1 = new Particle(0, new Vector(0, 0, 0), 1, 2, 3, 4);
		Particle c2 = new Particle(1, new Vector(1.428, 0, 0), 0, 5, 6, 7);
		Particle h1 = new Particle(2, new Vector(-0.363, 0.727, 0.727), 0, -1, -1, -1);
		Particle h2 = new Particle(3, new Vector(-0.363, -0.993, 0.266), 0, -1, -1, -1);
		Particle h3 = new Particle(4, new Vector(-0.363, 0.266, -0.993), 0, -1, -1, -1);
		Particle h4 = new Particle(5, new Vector(1.791, 0.733, 0.721), 1, -1, -1, -1);
		Particle h5 = new Particle(6, new Vector(1.791, 0.258, -0.995), 1, -1, -1, -1);
		Particle h6 = new Particle(7, new Vector(1.791, -0.990, 0.274), 1, -1, -1, -1);
		Particle[] molecule = {c1, c2, h1, h2, h3, h4, h5, h6};
		
		// Now use the minimize function and create a new file. It should then write a file to the directory specified above. To parse data, use excel etc..
		// Now what data do we obtain? Look below. It was very hardcoded
		minimize(molecule, 1.090, 340, 0.0190, 1.490,  0.00001, 20000);
		newFile("EthaneData.txt", SOURCE, data);
	}
	public static void minimize(Particle[] molecule, double r0, double k, double e, double s, double step, int maxCount) {
		Particle[] mol = molecule;
		int N = mol.length;
		Vector[] F = {null, null, null, null, null, null, null, null}; // Enlarge array as needed. Yes ik its sloppy
		for(int i = 0; i < N; i++) {
			F[i] = Energy.totalForce(i, molecule, r0, k, e, s);
		}
		double n = 0;
		while (n <= maxCount) {
			n = n + 1;
			
			// Writing data://
				// Bond length
			Vector b1 = Vector.add(molecule[0].getPosition(), Vector.multiply(-1, molecule[4].getPosition()));
			Vector b2 = Vector.add(molecule[1].getPosition(), Vector.multiply(-1, molecule[0].getPosition()));
			Vector b3 = Vector.add(molecule[6].getPosition(), Vector.multiply(-1, molecule[1].getPosition()));
				// Orthonormal vectors to bond
			Vector n1 = Vector.crossProduct(b1, b2);
			Vector n2 = Vector.crossProduct(b2, b3);
				// Find angle between two bonds to get dihedral angle:
			double angleRad = Math.acos(Vector.dotProduct(n1, n2) / (n1.getMagnitude() * n2.getMagnitude()));
			double angle = angleRad * 180 / Math.PI;
			LinkedList<Double> row = new LinkedList<Double>();
			double U = Energy.totalPotential(molecule, r0, k, e, s);
			row.add(angle);
			row.add(U);
			data.add(row);
			// End writing data//
			
			for(int i = 0; i < N; i++) {
				Vector currentPos = mol[i].getPosition();
				Vector stepSize = Vector.multiply(step, F[i]);
				mol[i].setPosition(Vector.add(currentPos, stepSize));
			}
			for(int i = 0; i < mol.length; i++) {
				F[i] = Energy.totalForce(i, molecule, r0, k, e, s);
			}
		}
	}
	
	public static void newFile(String name, String source, LinkedList<LinkedList<Double>> arr) throws IOException {
		String dir = SOURCE + name;
		File file = new File(dir + name);
		FileWriter writer = new FileWriter(dir);
		for(int i = 0; i < arr.size(); i++) {
			for(int j = 0; j < arr.get(i).size(); j++) {
				if (j != arr.get(i).size() - 1) {
					writer.write(arr.get(i).get(j).toString() + "\t");
				} else {
					writer.write(arr.get(i).get(j).toString());
				}
			}
			writer.write("\n");
		}
		writer.close();
		System.out.println("file written");

	}
}
