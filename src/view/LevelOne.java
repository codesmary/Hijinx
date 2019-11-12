package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import assets.Sprite;
import assets.Sprite.ImageTransformation;
import math.Vector;
import objects.Block;
import objects.DynamicGameObject;
import players.Cat;
import players.Witch;
import screen.GenericScreen;

public class LevelOne implements GenericScreen {
	private int width;
	private int height;
	private ArrayList<Block> blocks;
	private ArrayList<DynamicGameObject> players;

	Sprite test;

	public LevelOne(int width, int height) {
		this.blocks = new ArrayList<>();
		this.players = new ArrayList<>();
		this.width = width;
		this.height = height;

		this.test = new Sprite("/mirror_spritesheet.png", 0, 0, 352, 464);
		this.test.scaleSprite(0.25);
		this.test.flipSprite(ImageTransformation.Horizontal);

		int blockWidth = 56;

		for (int i = blockWidth; i < this.width - 2 * blockWidth; i += blockWidth) {
			Block.BlockType current;
			int yPos;

			if (i == blockWidth) {
				current = Block.BlockType.Left;
				yPos = height - blockWidth + 4;
			} else if (i + blockWidth >= this.width - 2 * blockWidth) {
				current = Block.BlockType.Right;
				yPos = height - blockWidth + 4;
			} else if (i % 2 == 1) {
				current = Block.BlockType.MiddleOne;
				yPos = height - blockWidth;
			} else {
				current = Block.BlockType.MiddleTwo;
				yPos = height - blockWidth;
			}

			this.blocks.add(new Block(new Vector(i, yPos), blockWidth, blockWidth, current));
		}

		DynamicGameObject obj = new Cat(new Vector(70, 200));
		this.players.add(obj);
		DynamicGameObject allyOne = new Witch(new Vector(170, 200));
		this.players.add(allyOne);
	}

	@Override
	public void first() {

	}

	boolean doOnce = false;
	Vector storePos = new Vector(0, 0);

	@Override
	public void tick() {
		// if (this.getCat().leftCollision(this.getAllyOne())) {
		// Witch witch = (Witch) this.getAllyOne();
		// witch.overlapped = true;
		// witch.updateAnimation();
		// if (!this.doOnce) {
		// this.getCat().setPositionX(this.getCat().getPositionX() + 400);
		// this.storePos = this.getCat().getPosition().clone();
		// this.doOnce = true;
		// }
		// }

		for (int i = 0; i < this.players.size(); i++) {
			DynamicGameObject player = this.players.get(i);
			player.updateInputForce();
			player.applyScenePhysics(this.blocks);
			player.runAnimation();
			player.updateAnimation();
		}
	}

	@Override
	public void render(Graphics2D g) {
		Sprite bg = new Sprite("/background_night.png");

		bg.render(g, new Vector(0, 0));

		g.setColor(Color.gray);

		for (int i = 0; i < this.blocks.size(); i++) {
			Block block = this.blocks.get(i);
			block.render(g);
		}

		for (int j = this.players.size() - 1; j >= 0; j--) {
			DynamicGameObject player = this.players.get(j);
			player.render(g);
			// if (player instanceof Witch) {
			// Witch witch = (Witch) player;
			// if (witch.overlapped) {
			// int yDiff = (int) Math.abs(this.getCat().getPositionY() -
			// this.getAllyOne().getPositionY());
			// Vector pos = this.storePos.minus(new Vector(0, yDiff));
			// this.test.render(g, pos);
			//
			// }
			// }
		}
	}

	@Override
	public boolean isDoneRendering() {
		return false;
	}

	@Override
	public void handleKeyPress(KeyEvent keyEvent) {
		int keyVal = keyEvent.getKeyCode();
		for (DynamicGameObject player : this.players) {
			if (player.inputPressed(keyVal)) {
				player.keyPressed(keyVal);

				// if (keyVal == KeyEvent.VK_Q && player instanceof Witch) {
				// Witch witch = (Witch) player;
				// witch.transform();
				// // witch.updateAnimation();
				// }
			}
		}
	}

	@Override
	public void handleKeyRelease(KeyEvent keyEvent) {
		int keyVal = keyEvent.getKeyCode();
		for (DynamicGameObject player : this.players) {
			if (player.inputPressed(keyVal)) {
				player.keyReleased(keyVal);
			}
		}
	}

	public DynamicGameObject getCat() {
		return this.players.get(0);
	}

	public DynamicGameObject getAllyOne() {
		return this.players.get(1);
	}

}