package assets;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import math.Vector;

public class Sprite {
	private BufferedImage spriteSheet;
	private BufferedImage img;
	private int width;
	private int height;

	public enum ImageTransformation {
		Vertical, Horizontal
	};

	public Sprite() {

	}

	public Sprite(String imagePath) {
		this.img = this.loadImage(imagePath);
		this.width = this.img.getWidth();
		this.height = this.img.getHeight();
	}

	public Sprite(String imagePath, int row, int col, int width, int height) {
		this.img = this.grabImage(this.loadImage(imagePath), row, col, width, height);
		this.width = this.img.getWidth();
		this.height = this.img.getHeight();
	}

	public Sprite(BufferedImage spriteSheet, int row, int col, int width, int height) {
		this.spriteSheet = spriteSheet;
		this.img = this.grabImage(spriteSheet, row, col, width, height);
		this.width = this.img.getWidth();
		this.height = this.img.getHeight();
	}

	public void render(Graphics2D g, Vector position) {
		g.drawImage(this.img, (int) position.x(), (int) position.y(), this.width, this.height, null);
	}

	private BufferedImage grabImage(BufferedImage img, int row, int col, int width, int height) {
		return img.getSubimage(col * width, row * height, width, height);
	}

	// flip a sprite vertically or horizontally
	public void flipSprite(ImageTransformation transformation) {
		AffineTransform trans = new AffineTransform();

		if (transformation == ImageTransformation.Vertical) {
			trans = AffineTransform.getScaleInstance(1, -1);
			trans.translate(0, -this.img.getHeight(null));
		} else if (transformation == ImageTransformation.Horizontal) {
			trans = AffineTransform.getScaleInstance(-1, 1);
			trans.translate(-this.img.getWidth(null), 0);
		}

		AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		this.img = op.filter(this.img, null);
	}

	// scale a sprite by different amounts in each dimension
	public void scaleSprite(double xScl, double yScl) {
		this.width = (int) (this.width * xScl);
		this.height = (int) (this.height * yScl);
	}

	// scale a sprite uniformly
	public void scaleSprite(double scl) {
		this.scaleSprite(scl, scl);
	}

	public void setSpriteSheet(String imagePath) {
		this.spriteSheet = this.loadImage(imagePath);
	}

	public BufferedImage loadImage(String path) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(this.getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}