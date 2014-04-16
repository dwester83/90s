package entity;

import graphics.AnimatedSprite;
import graphics.SpriteSheet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import level.Level;
import projectile.RangedOrb;
import utility.Node;
import utility.Position;


public class NPC extends SmartMob implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8774624757677507182L;
	
	private AI brain;
	private int xa = 0;//x adjustment for next move
	private int ya = 0;//y adjustment for next move
	private SmartMob enemy = null;
	private boolean firstTime = true;
	private int shootTime = 0;
	public NPC(int x, int y, Level level, AnimatedSprite sprite) {
		super(x, y, level, sprite);
		setPushBox(10,-3,10,-10);
		setVulnerableBox(1,-1,8,-8);
	}
	/**
	 * This method is the logic for this NPC object
	 */
	public void update(){
		if(stats.getCurrentHP() == 0) {
			remove();
			pushBox.remove();
			vulnerableBox.remove();
		}
		
		shootTime++;
		if(firstTime){
			firstTime = false;
			brain = new AI(5, this);		
		}
		if(enemy != null && enemy.getStats().getCurrentHP() <= 0){enemy = null;}
		incrementTime();
		distanceToEnemy();
		if(shootTime % 45 == 0){
			shoot(position.getX(), position.getY());
		}
		if(time % speedOfMovement == 0){//speed
			brain.logic();//call to the AI
			if(moving) sprite.updateAnimation();
			else sprite.setFrame(0);
			updateFacing();
			if(xa != 0 || ya != 0){
				if(move(position.getX() + xa, position.getY() + ya) && hasMeleeProjectile()){
					projectile.updatePosition(xa, ya);
				}
				moving = true;
			}else
				moving = false;
		}
	}
	/**
	 * creates a projectile and adds it to the list in level
	 * @param x double
	 * @param y double
	 */
	private void shoot(double x, double y){
		if(enemy != null){
			double newDir = 0;
			for(int i = 0; i < 1; i++){
				switch(i){
				case 0:
					newDir = facing;
					break;
				case 1:
					newDir = facing - .25;
					break;
				case 2:
					newDir = facing + .25;
					break;
				}
				projectile = new RangedOrb(position.getX()+(sprite.getSpriteWidth()/2) - 8, position.getY()+(sprite.getSpriteHeight()/2) - 8, newDir, level, this, new AnimatedSprite(new SpriteSheet("/textures/wizardprojectiles.png",16,16), 0, 0, 8, 3, 2,true));
				level.add(projectile, 2);
			}
			
		}
	}
	private void updateFacing(){
		if(enemy != null){
			Position enemyPosition = tellTheFuture();
			double dx = enemyPosition.getX()+(enemy.getWidth()/2)-(position.getX()+this.getWidth()/2);
			double dy = enemyPosition.getY()+(enemy.getHeight()/2)-(position.getY()+this.getHeight()/2);
			facing = Math.atan2(dy, dx);
		}else{
			if(ya < 0){
				sprite.setDirectionFacing(0);
			}else if(ya > 0){
				sprite.setDirectionFacing(2);
			}
			if(xa < 0) {
				sprite.setDirectionFacing(1);
			}
			else if(xa > 0) {
				sprite.setDirectionFacing(3);
			}
		}

	}
	private void distanceToEnemy(){
		if(enemy != null){
			double dist = Math.sqrt(Math.abs((enemy.getPositionX() - position.getX())*(enemy.getPositionX() - position.getX())
					+ (enemy.getPositionY() - position.getY())*(enemy.getPositionY() - position.getY())));
			if(dist > 400){
				enemy = null;
				brain.noPath();
				brain.setSubState(1);
				brain.resetLogicTime();
			}
		}
	}
	public void setEnemy(SmartMob enemy){
		this.enemy = enemy;
	}
	/**
	 * increments the time variable
	 */
	private void incrementTime(){
		time++;
	}
	/**
	 * returns the list of nodes that make up the path of this NPC
	 * @return
	 */
	public List<Node> getPath(){
		return brain.getAIPath();
	}
	/**
	 * used to round doubles to either -1, 0 or 1
	 * if number is greater than .5 it becomes a 1
	 * if number is less than -.5 it becomes a -1
	 * else the number is a 0
	 * @param toRound a double
	 * @return and int 0, 1, or -1
	 */
	private int rounding(double toRound){
		if(toRound >= .5)return 1;
		if(toRound <= -.5)return -1;
		return 0; 
	}
	public String getState(){
		return brain.getState();
	}
	/**
	 * Determinds where the enemy may be in the next half a second 
	 * @return
	 */
	private Position tellTheFuture(){
		if(enemy.isMoving()){
			Position previous = enemy.getPreviousPosition();
			double dx = (enemy.getPositionX() + enemy.getWidth())-(previous.getX() + enemy.getWidth());
			double dy = (enemy.getPositionY() + enemy.getHeight())-(previous.getY() + enemy.getHeight());
			double dir = Math.atan2(dy, dx);
			int nx = rounding(Math.cos(dir));
			int ny = rounding(Math.sin(dir));
			
			if(dx > dy) dy = 0;
			else dx = 0;
			return new Position((enemy.getPositionX()+enemy.getWidth()/2)+(dx*2), (enemy.getPositionY()+enemy.getHeight()/2)+(dy*2));
		}
		return enemy.getPosition();
	}
	
	
	public static class AI implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -3793826318068503214L;
		
		private Random random = new Random();
		private int waitTime = 0;
		private int moveTime = 0;
		private int logicTime = 0;
		private final int RANDOM = 1;
		private final int DIALOG = 2;
		private final int PATHING = 3;
		private final int ATTACKING = 4;
		private final int CHASING = 5;
		private final int DEAD = 6;
		private final int WAITING = 7;
		private List<Node> path = null; 

		private int state = 0;
		private int subState = 0;
		
		private NPC npc;
		
		private Comparator<Node> nodeSortor = new Comparator<Node>() {
			public int compare(Node node1, Node node2){
				if(node2.getFCost() < node1.getFCost()) return +1;
				if(node2.getFCost() > node1.getFCost()) return -1;	
				return 0;
			}
		};
		
		public AI(int startState, NPC npc) {
			//System.out.println("ai start state is " + startState );
			setState(startState);
			this.npc = npc;
		}
		
		public List<Node> getAIPath(){
			return path;
		}
		public void setState(int state) {			
			switch(state){
				case 1://state is random move
					this.state = state;
					subState = RANDOM;
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					this.state = state;
					subState = RANDOM;
					break;
				case 6:
					break;
				case 7:
					subState = state;
					break;
				}
			
		}
		public void setSubState(int subState){
			this.subState = subState;
		}
		public String getState(){
			String currentState = "";
			String currentSubState = "";
			switch(state){
			case 1:
				currentState = "RANDOM";
				break;
			case 2:
				currentState = "DIALOG";
				break;
			case 3:
				currentState = "PATHING";
				break;
			case 4:
				currentState = "ATTACKING";
				break;
			case 5:
				currentState = "CHASING";
				break;
			}
			switch(subState){
			case 1:
				currentSubState = "RANDOM";
				break;
			case 2:
				currentSubState = "DIALOG";
				break;
			case 3:
				currentSubState = "PATHING";
				break;
			case 4:
				currentSubState = "ATTACKING";
				break;
			case 5:
				currentSubState = "CHASING";
				break;
			case 6:
				currentSubState = "BLABLA";
				break;
			case 7:
				currentSubState = "WAITING";
				break;
			}
			return "is in state: " + currentState + " and Sub State: " + currentSubState;
		}
		public void logic(){
			
			logicTime++;
			//System.out.println("state: " + state);
			switch(state){
			case 1:
				random();
				break;
			case 2:
				dialog();
				break;
			case 3:
				pathing();
				break;
			case 4:
				attacking();
				break;
			case 5:
				chaseMove();
				break;
			}
		}
		private void dead() {
			System.out.println("dead state");
			
		}
		private void attacking() {
			System.out.println("attacking state");
			if(subState == 1){
				randomMove();
			}else if(subState == 5){
				//chaseMove();
			}else if(subState == 4){
				//attack();
			}
		}
		private void pathing() {
			System.out.println("pathing state");		
		}
		private void dialog() {
			System.out.println("dialog state");				
		}
		private void random() {
			//System.out.println("random state");
			//System.out.println("SubState of " + subState);
			if(subState == RANDOM){
				if(random.nextInt(10) == 0) subState = WAITING;
				else randomMove();
			}
			else if(subState == WAITING){
					randomWait();
			}
		}
		private void randomMove(){
			if(moveTime % logicTime == 0){
				npc.xa = random.nextInt(3) - 1;//randomly moves in a direction
				npc.ya = random.nextInt(3) - 1;
				moveTime = random.nextInt(30)+60;
			}
		}
		private void randomWait(){
			if(waitTime % logicTime == 0 && waitTime != 0){ 
				subState = RANDOM;
				waitTime = 0;
				logicTime = 0;
			}
			if(waitTime == 0){
				waitTime = random.nextInt(30)+60;
				npc.xa = 0;
				npc.ya = 0;
				logicTime = 0;
			}
		}
		
		private void chaseMove(){

			if(npc.enemy == null){
				subState = RANDOM;
				random();
			}else if(npc.enemy != null){
				subState = CHASING;
				npc.xa = 0;
				npc.ya = 0;
			Position start = new Position(npc.position.getX(), npc.position.getY());
			Position destination = new Position(npc.enemy.getPositionX() + 16, npc.enemy.getPositionY() + 16);
			if(logicTime % 60 == 0) path = findPath(start, destination);//recalculates the path once per second
			if(path != null){
				if(path.size() > 0){
					Position vec = path.get(path.size() - 1).getPosition();
					if(vec.equals(npc.position) && path.size() > 1){//to remove already reached nodes
						path.remove(path.size() - 1);
						vec = path.get(path.size() - 1).getPosition();
					}
					if(npc.position.getX() < vec.getX()) npc.xa += 1;
					else if(npc.position.getX() > vec.getX()) npc.xa -= 1;
					if(npc.position.getY() < vec.getY()) npc.ya += 1;
					else if(npc.position.getY() > vec.getY()) npc.ya -= 1;
				}	
			}
			}
		}
		public void noPath(){
			path = null;
		}
		public void resetLogicTime(){
			logicTime = 0;
		}
		private void attack(){
			
		}
		/**
		 * A* search algorithm
		 * @param start
		 * @param goal
		 * @return
		 */
		public List<Node> findPath(Position start, Position goal){
			List<Node> openList = new ArrayList<Node>();
			List<Node> closedList = new ArrayList<Node>();
			Node current = new Node(start, null, 0, getDistance(start, goal));
			openList.add(current);

			while(openList.size() > 0){
				Collections.sort(openList, nodeSortor);
				current = openList.get(0);

				if(current.getPosition().getX() < (goal.getX()) &&
					current.getPosition().getX() > (goal.getX() - 32) &&
					current.getPosition().getY() < (goal.getY()) &&
					current.getPosition().getY() > (goal.getY() - 32)){
					List<Node> path = new ArrayList<Node>();
					while(current.getParent() != null){
						
						path.add(current);
						current = current.getParent();

					}
					openList.clear();
					closedList.clear();
					return path;
				}
				openList.remove(current);
				closedList.add(current);
				for(int i = 0; i < 9; i++){
					if(i == 4) continue;
					int x = (int)current.getX();
					int y = (int)current.getY();
					int xi = ((i % 3) - 1)*16;
					int yi = ((i / 3) - 1)*16;
					if(npc.level.getCollisionManager().collision(x + xi, y + yi, npc.pushBox)){
						Entity sm = npc.level.getCollisionManager().collisionEntity(x + xi, y + yi, npc.pushBox);				
						if(!sm.equals(npc.enemy)) continue;
					}
					Position newNode = new Position(x + xi, y + yi);
					double gCost = current.getGCost() + (getDistance(current.getPosition(), newNode) == 1 ? 1 : 0.95);
					double hCost = getDistance(newNode, goal);
					Node node = new Node(newNode, current, gCost, hCost);
					if(nodeInList(closedList, newNode) && gCost >= node.getGCost()) continue;
					if(!nodeInList(openList, newNode) || gCost < node.getGCost()){
						//System.out.println("new Node Position: " + newNode.getX() + ", " +newNode.getY());
						openList.add(node);
					}
				}
			}
			closedList.clear();
			return null;
		}
		
		private boolean nodeInList(List<Node> list, Position position){
			for(Node n : list){
				if(n.getPosition().equals(position)) return true;

			}
			return false;
		}

		private double getDistance(Position current, Position goal){
			double dx = current.getX() - goal.getX();
			double dy = current.getY() - goal.getY();
			return Math.sqrt(dx * dx + dy * dy);
		}
	} 
}
