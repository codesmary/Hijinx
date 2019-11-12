package math;

public class Matrix {
	private double[][] matrix;

	public Matrix(double[][] matrix) {
		this.matrix = matrix;
	}

	public Matrix(Vector v) {
		this.matrix = new double[2][1];
		this.matrix[0][0] = v.x();
		this.matrix[1][0] = v.y();
	}

	//// BASIC MATRIX OPERATIONS ////

	public Matrix plus(Matrix matrixB) {
		if (this.rows() != matrixB.rows() || this.columns() != matrixB.columns())
			throw new IllegalArgumentException("Mismatched dimensions for matrix addition");

		double[][] newMatrix = this.getDeepCopy(this.matrix);
		for (int i = 0; i < this.rows(); i++) {
			for (int j = 0; j < this.columns(); j++) {
				newMatrix[i][j] += matrixB.matrix[i][j];
			}
		}
		return new Matrix(newMatrix);
	}

	public Matrix minus(Matrix matrixB) {
		if (this.rows() != matrixB.rows() || this.columns() != matrixB.columns())
			throw new IllegalArgumentException("Mismatched dimensions for matrix subtraction");

		double[][] newMatrix = this.getDeepCopy(this.matrix);
		for (int i = 0; i < this.rows(); i++) {
			for (int j = 0; j < this.columns(); j++) {
				newMatrix[i][j] -= matrixB.matrix[i][j];
			}
		}
		return new Matrix(newMatrix);
	}

	public Matrix times(Matrix matrixB) {
		if (this.columns() != matrixB.rows())
			throw new IllegalArgumentException("Mismatched dimensions for matrix multiplication");

		double[][] newMatrix = new double[this.rows()][matrixB.columns()];
		for (int i = 0; i < this.rows(); i++) {
			for (int j = 0; j < matrixB.columns(); j++) {
				double indexVal = 0;
				// each new matrix element is the dot product of row(A) . col(B)
				for (int k = 0; k < this.columns(); k++)
					indexVal += this.matrix[i][k] * matrixB.matrix[k][j];
				newMatrix[i][j] = indexVal;
			}
		}
		return new Matrix(newMatrix);
	}

	public Vector times(Vector vector) {
		Matrix convertedToMatrix = new Matrix(vector);
		Matrix product = this.times(convertedToMatrix);
		return new Vector(product);
	}

	public Matrix times(double scalar) {
		double[][] newMatrix = this.getDeepCopy(this.matrix);
		for (int i = 0; i < this.rows(); i++) {
			for (int j = 0; j < this.columns(); j++) {
				newMatrix[i][j] *= scalar;
			}
		}
		return new Matrix(newMatrix);
	}

	//// MISC METHODS ////

	private double[][] getDeepCopy(double[][] matrix) {
		double[][] cpy = new double[this.rows()][this.columns()];
		for (int i = 0; i < this.rows(); i++) {
			for (int j = 0; j < this.columns(); j++) {
				cpy[i][j] = matrix[i][j];
			}
		}
		return cpy;
	}

	public int rows() {
		return this.matrix.length;
	}

	public int columns() {
		return this.matrix[0].length;
	}

	public double[][] getArray() {
		return this.matrix;
	}

	@Override
	public String toString() {
		StringBuilder matrixOutput = new StringBuilder();
		for (double[] row : this.matrix) {
			for (double element : row)
				matrixOutput.append(element + " ");
			matrixOutput.append("\n");
		}
		return matrixOutput.toString();
	}
}