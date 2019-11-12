package objects;

import java.awt.Rectangle;

import assets.Sprite;
import math.Vector;

public class Block extends StaticGameObject {
	private BlockType texture;

	public enum BlockType {
		Left, MiddleOne, MiddleTwo, Right
	};

	public Block(Vector position, int width, int height, BlockType textureType) {
		super(position, width, height);
		this.texture = textureType;

		if (textureType == BlockType.Left) {
			this.staticSprite = new Sprite("/block_grass_night_left.png");
			this.staticSprite.scaleSprite(.25);
		} else if (textureType == BlockType.MiddleOne) {
			this.staticSprite = new Sprite("/block_grass_night_mid_1.png");
			this.staticSprite.scaleSprite(.25);
		} else if (textureType == BlockType.MiddleTwo) {
			this.staticSprite = new Sprite("/block_grass_night_mid_2.png");
			this.staticSprite.scaleSprite(.25);
		} else if (textureType == BlockType.Right) {
			this.staticSprite = new Sprite("/block_grass_night_right.png");
			this.staticSprite.scaleSprite(.25);
		}
	}

	public Block(Vector position, int width, int height) {
		super(position, width, height);
	}

	public BlockType getType() {
		return this.texture;
	}

	int amountEdges = 4;
	int amountMid = 8;

	@Override
	public Rectangle getBounds() {
		if (this.texture == Block.BlockType.Left || this.texture == Block.BlockType.Right) {
			return new Rectangle((int) this.position.x(), (int) this.position.y() + this.amountEdges, this.width,
					this.height);
		} else {
			return new Rectangle((int) this.position.x(), (int) this.position.y() + this.amountMid, this.width,
					this.height);
		}

	}

	@Override
	public boolean topCollision(GameObject other) {
		return this.topBounds().intersects(other.getBounds());
	}

	@Override
	public Rectangle topBounds() {
		if (this.texture == Block.BlockType.Left || this.texture == Block.BlockType.Right) {
			return new Rectangle((int) (this.getPosition().x() + this.width / 4),
					(int) this.getPosition().y() - 1 + this.amountEdges, this.width / 2, this.height / 2);
		} else {
			return new Rectangle((int) (this.getPosition().x() + this.width / 4),
					(int) this.getPosition().y() - 1 + this.amountMid, this.width / 2, this.height / 2);
		}
	}

	@Override
	public Rectangle leftBounds() {
		if (this.texture == Block.BlockType.Left || this.texture == Block.BlockType.Right) {
			return new Rectangle((int) this.getPosition().x() - 1,
					(int) (this.getPosition().y() + this.height / 8 + this.amountEdges), this.width / 2,
					3 * this.height / 4);
		} else {
			return new Rectangle((int) this.getPosition().x() - 1,
					(int) (this.getPosition().y() + this.height / 8 + this.amountMid), this.width / 2,
					3 * this.height / 4);
		}
	}

	@Override
	public Rectangle rightBounds() {
		if (this.texture == Block.BlockType.Left || this.texture == Block.BlockType.Right) {
			return new Rectangle((int) (this.getPosition().x() + this.width / 2 + 1),
					(int) (this.getPosition().y() + this.height / 8) + this.amountEdges, this.width / 2,
					3 * this.height / 4);
		} else {
			return new Rectangle((int) (this.getPosition().x() + this.width / 2 + 1),
					(int) (this.getPosition().y() + this.height / 8) + this.amountMid, this.width / 2,
					3 * this.height / 4);
		}
	}
}