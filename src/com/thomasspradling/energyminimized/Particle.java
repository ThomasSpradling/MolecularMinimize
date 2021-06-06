package com.thomasspradling.energyminimized;

public class Particle {
	Vector position;
	int identity;
	int atom1;
	int atom2;
	int atom3;
	int atom4;
	public Particle(int identity, Vector position, int atom1, int atom2, int atom3, int atom4) {
		this.position = position;
		this.identity = identity;
		this.atom1 = atom1;
		this.atom2 = atom2;
		this.atom3 = atom3;
		this.atom4 = atom4;
	}
	public Vector getPosition() {
		return this.position;
	}
	public void setPosition(Vector pos) {
		this.position = pos;
	}
	public int getIdentity() {
		return identity;
	}
	public void setIdentity(int identity) {
		this.identity = identity;
	}
	public int getAtom1() {
		return atom1;
	}
	public void setAtom1(int atom1) {
		this.atom1 = atom1;
	}
	public int getAtom2() {
		return atom2;
	}
	public void setAtom2(int atom2) {
		this.atom2 = atom2;
	}
	public int getAtom3() {
		return atom3;
	}
	public void setAtom3(int atom3) {
		this.atom3 = atom3;
	}
	public int getAtom4() {
		return atom4;
	}
	public void setAtom4(int atom4) {
		this.atom4 = atom4;
	}
	public String toString() {
		return + this.identity + ": " + this.position.toString();
	}
}
