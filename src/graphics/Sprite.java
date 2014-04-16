package graphics;

import java.io.Serializable;

public class Sprite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2736398037104165473L;
	
	private int[] pixels;
	private int height;
	private int width;
	
	/**
	 * The default contructor, will create an individual sprite off of a sheet.
	 * 
	 * @param height in pixels
	 * @param width in pixels
	 * @param sheet
	 */
	public Sprite(int height, int width, int xPos, int yPos, int totalWidth, int[] sheet) {
		this.width = width;
		this.height = height;
		this.pixels = new int[height * width];
		
		load(xPos, yPos, totalWidth, sheet);
	}
	
	/**
	 * This is to make a sprite without the need for a sprite sheet.
	 * 
	 * @param height
	 * @param width
	 * @param color
	 */
	public Sprite(int height, int width, int color) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}
	
	private void load(int xPos, int yPos, int totalWidth, int[] sheet) {
		for(int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet[xPos + yPos + x + y * totalWidth];
			}
		}
	}
	
	private void setColor(int color) {
		for(int i = 0; i < width * height; i++)
		{
			pixels[i] = color;
		}
	}
	
	public int[] getPixels(){
		return pixels;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
}
