package sound;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

import javax.sound.sampled.*;

import entity.Entity;

/**
 * This is to play individual audio files. Should play any type of wav, but seems to have a 4MB limit on OSX.
 * 
 * @author Dan, Erin
 */
public class Sound {

	private String filename;
	private boolean loop;
	private Clip clip;
	private AudioInputStream sound;
	private Integer x,y;
	private Entity source, listener;

	/**
	 * This is the sound constructor, takes a path of the file, boolean to loop, 
	 * and an Entity for the player it's going to adjust to.
	 * 
	 * @param path String, string path of the audio file.
	 * @param loop boolean, whether the audio will loop.
	 * @param listener Entity, entity that's the sound will adjust to ie. the player.
	 */
	public Sound(String path, boolean loop, Entity listener) {
		filename = path;
		this.loop = loop;
		this.listener = listener;
		try {
			InputStream is = new BufferedInputStream(this.getClass().getResourceAsStream(path));
			
			sound = AudioSystem.getAudioInputStream(is);

			clip = AudioSystem.getClip();

			System.out.println(clip.getBufferSize());
			clip.open(sound);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the entity that the source of the sound comes from.
	 * 
	 * @param source
	 */
	public void setSource(Entity source)
	{
		this.source=source;
	}

	/**
	 * Play the audio, will go into a sub-thread and repeat if on a loop.
	 * 
	 * @param loop boolean, if the sound should loop.
	 */
	public void play(boolean loop)
	{
		this.loop=loop;

		clip.setFramePosition(0);  
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		volume.setValue(-60f); //Begin Quietly
		if(loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
		else clip.start();

	}

	/**
	 * The source of the audio based on a x, y coordinates.
	 * 
	 * @param x int, x coordinant of the sound.
	 * @param y int, y coordinant of the sound.
	 */
	public void setCoords(int x,int y)
	{
		this.x=x;
		this.y=y;
	}

	/**
	 * Will return if the sound is still playing.
	 * 
	 * @return boolean, if the sound is active.
	 */
	public boolean isPlaying() {
		return clip.isActive();
	}

	/**
	 * Will return the file name path.
	 * 
	 * @return String, will return the file path as a string.
	 */
	public String getFileName()
	{
		return filename;
	}

	/**
	 * Will return if the sound is going to loop.
	 * 
	 * @return boolean, will return the loop boolean.
	 */
	public boolean getLoopStatus()
	{
		return loop;
	}

	/**
	 * Will change the loop boolean state.
	 * 
	 * @param loop boolean, true for the audio to loop, false to stop audio from looping.
	 */
	public void changeLoopStatus(boolean loop)
	{
		this.loop=loop;
	}

	/**
	 * Change the file path of the sound.
	 * 
	 * @param newPath String, the path of the new audio file that'll be played.
	 */
	public void changePath(String newPath)
	{
		filename = newPath;
		try {
			sound = AudioSystem.getAudioInputStream(new File(newPath));

			clip = AudioSystem.getClip();
			clip.open(sound);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stops the sound from playing.
	 */
	public void stopPlaying(){

		clip.stop();
	}
	
	/**
	 * Fades out the sound at a certain decibel.
	 */
	public void fadeOut()
	{
		fadeOut(0.2f);
	}
	
	/**
	 * Fades out the sound, but at an decimal increment.
	 * 
	 * @param increments float, the value that the sound will fade out at, greater numbers will fade quicker.
	 */
	public void fadeOut(float increments)
	{
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		if(volume.getValue() < -60)
		{
			clip.stop();
			return;
		}
		volume.setValue(volume.getValue()-increments);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		fadeOut(increments);
	}

	/**
	 * Will update the sound based on the float that adjusted it.
	 */
	public void updateSound()
	{
		FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		double distanceToSource=0;
		if(x != null && y != null || source !=null)
		{
			if(x != null && y!=null)
			{
				distanceToSource = Math.sqrt( Math.pow(x-listener.getPositionX(),2) + Math.pow(y-listener.getPositionY(),2) );
			}
			else if(source !=null)
			{
				distanceToSource = Math.sqrt( Math.pow(source.getPositionX()-listener.getPositionX(),2) + Math.pow(source.getPositionY()-listener.getPositionY(),2) );
			}
			double newValue = (1000 / (4*Math.PI*Math.pow((distanceToSource/2), 2)))*100;
			float dB = (float) (Math.log(newValue) / Math.log(10.0) * 20.0);
			dB = Math.max(Math.min(dB, 6), -60);
			volume.setValue(dB);
		}
		else
		{
			volume.setValue(1);
		}
	}
}
