package sound;


import java.util.HashMap;
import java.util.Iterator;

import entity.Entity;

public class SoundManager {
	
	private static SoundManager soundManager = new SoundManager();
	private HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	Entity listener;	
	
	private SoundManager()
	{
		return;
	}
	
	/*
	 * @return SoundManager - a singleton instance
	 */
	public static SoundManager getInstance()
	{
		return soundManager;
	}
	
	/*
	 * The listener will be the object that modifies playing sounds volume based on its location in the level
	 * @param listener -  the sound listener, an entity usually being the player
	 */
	public void setListener(Entity listener)
	{
		this.listener = listener;
		sounds.put("Level1", new Sound("/sounds/townTheme.wav", true, listener));
		sounds.put("Hit", new Sound("/sounds/wishoo.wav", true, listener));
		
	}
	
	/*
	 * Simply plays sound that has no volume adjustments
	 * @param soundName - A shorthand reference to a sound in the sounds hashmap
	 * @param loop - True if the sound should be played repeatedly. 
	 */
	public void playSound(String soundName, boolean loop)
	{
		sounds.get(soundName).play(loop);
		
	}
	
	/* Fade a sound out slowly
	 * @param soundName - A shorthand reference to a sound in the sounds hashmap
	 */
	public void fadeOut(String soundName)
	{
		sounds.get(soundName).fadeOut();
	}
	
	/*
	 * Play a sound at a specific coordinate, adjusts volume based on input object listener position (usually player)
	 * @param soundName - A shorthand reference to a sound in the sounds hashmap
	 * @param loop - True if the sound should be played repeatedly. 
	 * @param listener -  the sound listener, an entity usually being the player
	 */
	public void playSound(String soundName, boolean loop, int xCoord, int yCoord)
	{
		sounds.get(soundName).play(loop);
		sounds.get(soundName).setCoords(xCoord, yCoord);
	}
	
	/*
	 * Play a sound that follows an object (sound source), adjusting its volume based on a second object listener (usually player)
	 * @param soundName - A shorthand reference to a sound in the sounds hashmap
	 * @param loop - True if the sound should be played repeatedly. 
	 * @param source - where the sound originates, moves with the source position
	 */
	public void playSound(String soundName, boolean loop, Entity source)
	{
		sounds.get(soundName).play(loop);
		sounds.get(soundName).setSource(source);
	}
	
	/*
	 * @param soundName - A shorthand reference to a sound in the sounds hashmap
	 * @return boolean true if the specified sound is playing 
	 */
	public boolean isPlaying(String soundName)
	{
		return sounds.get(soundName).isPlaying();
	}
	
	public void update()
	{
		Iterator<Sound> soundIterator = sounds.values().iterator();
		while(soundIterator.hasNext())
		{
			Sound nextSound = soundIterator.next();
			nextSound.updateSound();
		}
	}
	
	/*
	 * Given a sound that is playing, turn it off
	 * @param soundName - A shorthand reference to a sound in the sounds hashmap
	 */
	public void stopSound(String soundName)
	{
		sounds.get(soundName).stopPlaying();
	}
}
