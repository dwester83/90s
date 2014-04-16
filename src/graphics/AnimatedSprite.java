package graphics;

import java.io.Serializable;

/**
 * An Animated Sprite is 1 or More Sprites that will cycled through to create animation
 * @author 
 *
 */
public class AnimatedSprite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7413337708973047917L;

	private SpriteSheet sheet = null;
	private int startingColumn,startingRow,numberOfDirections,framesPerDirection;
	private boolean skipFirstFrame;
	private int frame = 0;
	private int directionFacing = 0;
	private int time = 0;
	private int frameRate = 0;
	private int color = 0;
	private int size = 0;

	public AnimatedSprite(SpriteSheet sheet, int startingColumn, int startingRow, int numberOfDirections, int framesPerDirection, int frameRate, boolean skipFirstFrame){
		this.sheet = sheet;
		this.startingColumn = startingColumn;
		this.startingRow = startingRow;
		if (numberOfDirections > 1) {
			this.numberOfDirections = numberOfDirections;
		} else this.numberOfDirections = 0;

		this.framesPerDirection = framesPerDirection;
		this.frameRate = frameRate;
		this.skipFirstFrame=skipFirstFrame;
	}
	public AnimatedSprite(int color, int size){
		this.color = color;
		this.size = size;
	}
	/**
	 * The getter for the sprite Width
	 * @return int for sprite Width
	 */
	public int getSpriteWidth(){
		if (size > 0) {
			return size;
		}
		return sheet.getSprite(startingColumn + directionFacing, startingRow + frame).getWidth();
	}
	/**
	 * The Getter for the sprite Height
	 * @return int for sprite height
	 */
	public int getSpriteHeight(){
		if (size > 0) {
			return size;
		}
		return sheet.getSprite(startingColumn + directionFacing, startingRow + frame).getHeight();
	}

	/**
	 * Get the current frame
	 */

	public int getFrame()
	{
		return frame;
	}

	/**
	 * update what frame you are on in the animation
	 */
	public boolean updateAnimation(){
		//System.out.println("update");
		time++;
		if (frameRate != 1 && frameRate != 0) {
			if(time % frameRate == 0){
				frame = (frame + ((skipFirstFrame == true) ? 1 : 0)) % framesPerDirection;
				if (frame == 0){
					frame++;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * The getter for the current Sprite in the Animation
	 * @return a Sprite
	 */
	public Sprite getSprite(){
		return sheet.getSprite(startingColumn + directionFacing, startingRow + frame);
	}

	/**
	 * Sets the frame Rate
	 * @param frames is the speed at which frames change higher is slower
	 */
	public void setFrameRate(int frames){
		if(frames < framesPerDirection) frameRate = frames;
	}

	/**
	 * Sets the direction facing 0(up), 1(down), 2(left), 3(right)
	 * @param directionFacing is the
	 */
	public void setDirectionFacing(int directionFacing){
		if(directionFacing > numberOfDirections || directionFacing < 0){
			System.out.println(directionFacing + " is an invalid direction");
		}else{
			this.directionFacing = directionFacing;
			//frame = 0; //sets the frame back to the starting frame 
		}
	}

	public int getNumberOfDirections(){
		return numberOfDirections;
	}

	/**
	 * sets the frame you are currently on in this animation
	 * @param index int representing a frame
	 */
	public void setFrame(int index) {
		if(index > framesPerDirection || index < 0) {
			System.err.println("Index out of Bounds in " + this);
			return;
		}
		frame = index;
	}

}