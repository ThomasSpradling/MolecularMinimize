package com.thomasspradling.energyminimized;

public class Vector {
	private double x;
	private double y;
	private double z;

	public Vector (double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getMagnitude() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	public static Vector add(Vector a, Vector b) {
		Vector vec = new Vector(0, 0, 0);
		vec.x = a.x + b.x;
		vec.y = a.y + b.y;
		vec.z = a.z + b.z;
		return vec;
	}
	public static Vector multiply(double a, Vector b) {
		Vector vec = new Vector(0, 0, 0);
		vec.x = a * b.x;
		vec.y = a * b.y;
		vec.z = a * b.z;
		return vec;
	}
	@Override
	public String toString() {
		return "[" + this.x + "," + this.y + "," + this.z + "]";
	}
	public static Vector add4(Vector a, Vector b, Vector c, Vector d) {
		Vector vec = new Vector(0, 0, 0);
		vec.x = a.x + b.x + c.x + d.x;
		vec.y = a.y + b.y + c.y + c.y;
		vec.z = a.z + b.z + c.z + c.z;
		return vec;
	}
	public static double dotProduct(Vector a, Vector b) {
		double prod = a.x * b.x + a.y * b.y + a.z * b.z;
		return prod;
	}
	public static Vector crossProduct(Vector a, Vector b) {
		Vector vec = new Vector(0, 0, 0);
		vec.x = a.y*b.z - b.y*a.z;
		vec.y = a.z*b.x - a.x*b.z;
		vec.z = a.x*b.y - b.x*a.y;
		return vec;
	}
}
