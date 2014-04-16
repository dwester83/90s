package entity;

import level.Level;

@SuppressWarnings("serial")
public class BloodSpawner extends Spawner{
	
	int x,y;
	
	public BloodSpawner(int x, int y, int life, int amount, int totalIterations, Level level) {
		super(totalIterations, amount, level);
		this.x = x;
		this.y = y;

	}
	
	public void update(){
		if(iterationsDone <= totalIterations){
			for( int i = 0; i < amount; i++){
				level.add(new Particle(x, y, level, 10, 0xff0000, 2, false), 2);
			
			}
		}else{
			remove();
		}
		iterationsDone++;
	}
	
}
