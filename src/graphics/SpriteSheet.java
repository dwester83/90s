package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class SpriteSheet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 472561152658247507L;
	
	private int width;
	private int height;
	private int totalWidth;
	private int totalHeight;

	private int[] pixels;

	private Sprite[] sprites;

	/**
	 * Default constructor, will take a file path, and break it the sprite sheet into sprites.
	 * 
	 * @param path This is the Sprite Sheet image that will be loaded
	 * @param height This is the height of a sprite in the sheet
	 * @param width This is the width of a sprite in the sheet
	 */
	public SpriteSheet(String path, int height, int width) {
		this.height = height;
		this.width = width;

		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
			totalWidth = image.getWidth();
			totalHeight = image.getHeight();

			sprites = new Sprite[(totalWidth / width) * (totalHeight / height)];
			pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Sprite Sheet");
		}

		breakup();
	}

	/**
	 * This will break up the sprite sheet into individual sprites.
	 */
	private void breakup(){
		int id = 0;

		for (int yPos = 0; yPos < (totalHeight / height); yPos++){
			for (int xPos = 0; xPos < (totalWidth / width); xPos++){
				Sprite sprite = new Sprite(height, width, xPos * width, yPos * height * totalWidth, totalWidth, pixels);
				
				sprites[id++] = sprite;
			}
		}
	}

	/**
	 * Returns the sprite at the assigned ID.
	 * 
	 * @param id
	 * @return
	 */
	public Sprite getSprite(int id){
		return sprites[id];
	}

	/**
	 * Returns the sprite at the assigned X, Y coordinate.
	 * 
	 * @param x column on sprite sheet
	 * @param y row on spritesheet
	 * @return Sprite
	 */
	public Sprite getSprite(int x, int y){
		return sprites[x + y * (totalWidth/width)];
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
}
