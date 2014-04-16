package graphics;

public class Screen {

	private int width, height; //off our image that we want to write to the screen
	/**
	 * @return the pixels
	 */
	public int[] getPixels() {
		return pixels;
	}
	/**
	 * @param pixels the pixels to set
	 */
	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	private int[] pixels;//contains all the pixel data
	private int xOffset, yOffset;
	private int colorToIgnore = 0xffed008c; //add an extra ff to fix issues
	private int colorToIgnore2 = 0xffff00ff; //for different pink
	/**
	 * creates an array to hold the int corresponding with the color in each pixel
	 */
	public Screen(int width, int height){
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	public void clear(){
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = 0;
		}
	}

	public void render(int xp, int yp, Sprite sprite){
		xp -= xOffset;
		yp -= yOffset;

		for(int y = 0; y < sprite.getHeight(); y++){
			int ya = y + yp; //y absolute position is equal to y + yOffset
			int ys = y;
			for(int x = 0; x < sprite.getWidth(); x++){
				int xa = x + xp;
				int xs = x;
				if(xa < -(sprite.getWidth()) || xa >= width || ya < 0 || ya >= height) break;
				if(xa < 0) xa = 0;
				int color = sprite.getPixels()[xs + ys * sprite.getWidth()];
				if(color != colorToIgnore && color != colorToIgnore2) pixels[xa + ya * width] = color;
			}
		}
	}
	public void setOffset(int xOffset, int yOffset)
	{
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	public int getXOffset(){
		return xOffset;
	}
	public int getYOffset(){
		return yOffset;
	}
	
}
