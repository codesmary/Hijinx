package math;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Vector {
	private double x;
	private double y;

	public enum CoordinateSystem {
		Cartesian, Polar, Spherical
	};

	//// CONSTRUCTORS ////
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// allows for 2d cartesian/polar coordinates
	public Vector(CoordinateSystem type, double v1, double v2) {
		switch (type) {
		case Cartesian:
			this.x = v1;
			this.y = v2;
			break;
		case Polar:
			this.x = v1 * Math.cos(v2);
			this.y = v1 * Math.sin(v2);
			break;
		default:
			throw new IllegalArgumentException("Invalid coordinate system for this vector.");
		}
	}

	public Vector(double[] arr) {
		this.x = arr[0];
		this.y = arr[1];
	}

	public Vector(int[] arr) {
		this.x = arr[0];
		this.y = arr[1];
	}

	public Vector(Point p) {
		this.x = p.getX();
		this.y = p.getY();
	}

	public Vector(Vector vector) {
		this.x = vector.x();
		this.y = vector.y();
	}

	public Vector(Matrix matrix) {
		if (matrix.columns() != 1 || matrix.rows() != 2)
			throw new IllegalArgumentException("Mismatched dimensions for composing a vector from a matrix.");
		this.x = matrix.getArray()[0][0];
		this.y = matrix.getArray()[1][0];
	}

	//// VECTOR OPERATIONS ////
	public Vector plus(Vector b) {
		double x_plus = this.x + b.x;
		double y_plus = this.y + b.y;
		return new Vector(x_plus, y_plus);
	}

	public Vector plusScaledVector(Vector b, double scalar) {
		double x_plusSV = this.x + b.times(scalar).x;
		double y_plusSV = this.y + b.times(scalar).y;
		return new Vector(x_plusSV, y_plusSV);
	}

	public Vector minus(Vector b) {
		double x_minus = this.x - b.x;
		double y_minus = this.y - b.y;
		return new Vector(x_minus, y_minus);
	}

	public Vector times(double a) {
		double x_t = this.x * a;
		double y_t = this.y * a;
		return new Vector(x_t, y_t);
	}

	public Vector componentProduct(Vector v2) {
		double x_cp = this.x * v2.x;
		double y_cp = this.y * v2.y;
		return new Vector(x_cp, y_cp);
	}

	public Vector divide(double a) {
		double x_div = this.x / a;
		double y_div = this.y / a;
		return new Vector(x_div, y_div);
	}

	public Vector unit() {
		if (this.x() == 0 && this.y() == 0)
			return this;
		double mag = this.magnitude();
		return new Vector(this.x() / mag, this.y() / mag);
	}

	public Vector setMagnitude(double magnitude) {
		return this.unit().times(magnitude);
	}

	// set the maximum value a vector can have to magnitude
	public Vector limit(double magnitude) {
		Vector limitedMagnitude = new Vector(this.x, this.y);
		if (this.magnitudeOptimized() > magnitude * magnitude)
			limitedMagnitude = limitedMagnitude.setMagnitude(magnitude);
		return limitedMagnitude;
	}

	//// SCALAR OPERATIONS ////
	public double dot(Vector b) {
		return this.x * b.x + this.y * b.y;
	}

	public double magnitude() {
		return Math.sqrt(this.magnitudeOptimized());
	}

	public double magnitudeOptimized() {
		return this.dot(this);
	}

	public double distanceOptimized(Vector v2) {
		return this.minus(v2).magnitudeOptimized();
	}

	public double distance(Vector v2) {
		return this.minus(v2).magnitude();
	}

	//// 2D METHODS ////
	public double scalarProjectionOn(Vector on) {
		return this.dot(on.unit());
	}

	public Vector vectorProjectionOn(Vector on) {
		return on.times(this.dot(on) / on.dot(on));
	}

	public Vector rotateBy(double θ) {
		Matrix rotationMatrix = new Matrix(
				new double[][] { { Math.cos(θ), -Math.sin(θ) }, { Math.sin(θ), Math.cos(θ) } });
		return rotationMatrix.times(this);
	}

	public double angleBetween(Vector v2) {
		return Math.acos(this.dot(v2) / (this.magnitude() * v2.magnitude()));
	}

	public double getAngle() {
		return Math.atan2(this.y(), this.x());
	}

	public void drawVector2D(Graphics g, Vector magnitude) {
		g.drawLine((int) this.x(), (int) this.y(), (int) (this.x() + magnitude.x()), (int) (this.y() + magnitude.y()));
		g.drawOval((int) (this.x() + magnitude.x()), (int) (this.y() + magnitude.y()), 2, 2);
	}

	public void drawPoint(Graphics g) {
		g.drawOval((int) this.x(), (int) this.y(), 1, 1);
	}

	public void drawPoint(Graphics g, Color col) {
		g.setColor(col);
		this.drawPoint(g);
	}

	//// GETTERS AND SETTERS ////
	public void clear() {
		this.x = 0;
		this.y = 0;
	}

	public double x() {
		return this.x;
	}

	public double y() {
		return this.y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setVector(double x, double y) {
		this.setX(x);
		this.setY(y);
	}

	public double[] getArray() {
		return new double[] { this.x(), this.y() };
	}

	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}

	@Override
	public boolean equals(Object obj) {
		boolean equalsOther = true;
		Vector vectorb = (Vector) obj;
		double[] comp = vectorb.getArray();
		if (this.x != comp[0] || this.y != comp[1]) {
			equalsOther = false;
		}
		return equalsOther;
	}

	@Override
	public int hashCode() {
		int hash = ((Double) this.x()).hashCode() * 13 ^ ((Double) this.y()).hashCode() * 17;
		return hash;
	}

	@Override
	public Vector clone() {
		return new Vector(this.x(), this.y());
	}
}