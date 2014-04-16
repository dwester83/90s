package entity;

import level.Level;

@SuppressWarnings("serial")
public class SnowSpawner extends Spawner {

	int time = 0;
	public SnowSpawner(int life, int amount, int totalIterations, Level level) {
		super(totalIterations, amount, level);

	}
	
	public void update(){

			makeFlakes();

	}
	private void makeFlakes(){
		for( int i = 0; i < amount; i++){
			level.add(new SnowSkyParticle(random.nextInt(1000), random.nextInt(2000), 1, level), 4);
			level.add(new SnowSkyParticle(random.nextInt(1000), random.nextInt(2000), 2, level), 4);
			
		}
		for( int i = 0; i < amount*10; i++){
			level.add(new SnowGroundParticle(random.nextInt(1000),random.nextInt(2375), 1, level), 2);
			//level.add(new SnowGroundParticle(random.nextInt(1000),random.nextInt(2000), 2, level));
		}
	}
}
