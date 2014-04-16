package level;

import entity.Entity;
import entity.NPC;
import entity.Player;
import entity.SnowSpawner;
import events.DialogManager;
import events.EventManager;
import graphics.AnimatedSprite;
import graphics.Screen;
import graphics.SpriteSheet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import menu.MainMenu;
import tile.Tile;
import utility.CollisionManager;
import utility.HitBox;
import utility.Node;
import sound.*;

public class Level implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6647763203109780958L;

	private Screen screen;
	private String csvFile;
	private HashMap<Integer, Tile> tile = new HashMap<Integer, Tile>();
	private MainMenu menu;
	private CollisionManager collisionManager =  new CollisionManager();
	private EventManager eventManager = new EventManager(new DialogManager("/events/dialogs.csv","/events/choices.csv"));
	private int[] tiles;
	private SoundManager soundManager = SoundManager.getInstance();

	private Map<Double, Entity> render = new TreeMap<Double, Entity>();
	private ArrayList<Entity>[] layerEntities;
	Double postionRender;
	Set<Double> set;
	Iterator<Double> itr;


	private int height;
	private int width;
	private int h, w;

	public Level(String map,String csvFile, int tileSize, Screen screen) {
		this.screen = screen;	
		this.csvFile = csvFile;

		layerEntities = new ArrayList[5];

		for (int i = 0; i < layerEntities.length; i++) {
			layerEntities[i] = new ArrayList<Entity>();
		}


		try{
			BufferedImage image = ImageIO.read(Level.class.getResource(map));
			w = image.getWidth();
			h = image.getHeight();

			width = image.getWidth() * tileSize;
			height = image.getHeight() * tileSize;
			tiles = image.getRGB(0, 0, w, h, null, 0, w);

		}catch(IOException e){
			e.printStackTrace();
			System.out.println("LoadLevel Exception!");
		}
		getLevel();
		/*
		 * Testing area for code
		 */
		SnowSpawner ss = new SnowSpawner(1,1,1, this);
		add(ss, 4);

		SpriteSheet spritesheet = new SpriteSheet("/textures/spritesheet.png", 32, 32);
		AnimatedSprite sprite = new AnimatedSprite(spritesheet, 4, 5, 4, 3, 5,true);
		NPC npc = new NPC((16)*32, (60)*32, this, sprite);
		add(npc, 3);
		AnimatedSprite sprite1 = new AnimatedSprite(spritesheet, 4, 5, 4, 3, 5,true);
		NPC npc1 = new NPC((17)*32, (60)*32, this, sprite1);
		add(npc1, 3);
		AnimatedSprite sprite2 = new AnimatedSprite(spritesheet, 4, 5, 4, 3, 5,true);
		NPC npc2 = new NPC((18)*32, (60)*32, this, sprite2);
		add(npc2, 3);
		AnimatedSprite sprite3 = new AnimatedSprite(spritesheet, 4, 5, 4, 3, 5,true);
		NPC npc3 = new NPC((19)*32, (60)*32, this, sprite3);
		add(npc3, 3);
		AnimatedSprite sprite4 = new AnimatedSprite(spritesheet, 4, 5, 4, 3, 5,true);
		NPC npc4 = new NPC((20)*32, (60)*32, this, sprite4);
		add(npc4, 3);


		//EventBox levelChoiceEvent = new EventBox(20*32, 70*32, 256, 256,eventManager, 1);
		//collisionManager.addEventBox(levelChoiceEvent);

		//backgroundMusic.play();


	}

	public void playBackgroundMusic()
	{
		soundManager.playSound("Level1", true);
	}


	public void load(){
		//System.out.println("Loading.  h is " + h + " and w is " + w);
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				getTile(x , y);
			}
		}
	}

	public void getTile(int x, int y){
		int[] hitBox;
		Tile temp;
		Tile newTile;
		if(x < 0 || y < 0 || x >= w || y >= height){
			System.out.println("Outside Map");
			tilesPosition(new Tile(x * width, y * height, this, true, false, tile.get(1).getAnimatedSprite()));
		}else if(tile.containsKey(tiles[x + y * w])){
			temp = tile.get(tiles[x + y * w]);
			newTile = new Tile(x * temp.getHeight(), y * temp.getWidth(), this, temp.isSolid(), temp.isBreakable(), temp.getAnimatedSprite());
			if(temp.isSolid()){
				hitBox = temp.getImmovableBox();
				newTile.setImmovableBox(hitBox[0], hitBox[1], hitBox[2], hitBox[3]);
			}
			if(temp.isBreakable()){
				hitBox = temp.getVulnerableBox();
				newTile.setVulnerableBox(hitBox[0], hitBox[1], hitBox[2], hitBox[3]);
			}
			tilesPosition(newTile);
		} else {
			System.out.println("NOTHING!!!");
		}

	}
	private void remove(){
		for(int i = 0; i < layerEntities.length; i++){
			for(int j = 0; j < layerEntities[i].size(); j++){
				if(layerEntities[i].get(j).isRemoved()) layerEntities[i].remove(j);
			}
		}
	}
	public void addTile(Tile tile, int color){
		this.tile.put(color, tile);
	}

	private void tilesPosition(Entity entity) {
		layerEntities[0].add(entity);
		//tilesPos.add(entity);
	}

	public void add(Entity entity, int layer){
		layerEntities[layer].add(entity);
		//entities.add(entity);
	}

	public void addMenu(MainMenu menu) {
		this.menu = menu;
	}

	public void addHitBox(HitBox hitBox){
		collisionManager.add(hitBox);	

	}

	public CollisionManager getCollisionManager(){
		return collisionManager;
	}

	public void update(){
		if (!menu.MenuUp())
			for(int i = 0; i < layerEntities.length; i++){
				for(int j = 0; j < layerEntities[i].size(); j++){
					layerEntities[i].get(j).update();
				}
			}
		//backgroundMusic.update();
		soundManager.update();

	}

	public void render(int xScroll, int yScroll){

		remove();//removes all entities that are no longer relevant

		// This will set the screen to not go over the corners of the map
		if (xScroll < 0){xScroll = 0;}
		if (xScroll > (width << 3) - screen.getWidth()){xScroll = (width << 3) - screen.getWidth();}
		if (yScroll < 0) {yScroll = 0;}
		if (yScroll > (height << 3) - screen.getHeight()){yScroll = (height << 3) - screen.getHeight();}
		screen.setOffset(xScroll, yScroll);

		// This will render the entities so that they don't overlap each other.
		for(int i = 0; i < layerEntities.length; i++){
			for(int j = 0; j < layerEntities[i].size(); j++){
				postionRender = (double)layerEntities[i].get(j).getPositionY();

				while (render.containsKey(postionRender)) {
					postionRender = (postionRender + 0.0001);
				}

				render.put(postionRender, layerEntities[i].get(j));
			}

			// Iterate through the list
			set = render.keySet();
			itr = set.iterator();
			while (itr.hasNext()) {
				render.get(itr.next()).render(screen);;
			}

			// Clear up the render list.
			render.clear();

		}
	}

	private void getLevel(){

		BufferedReader CSVFile;
		try {
			InputStream is = this.getClass().getResourceAsStream(csvFile);
			CSVFile = new BufferedReader(new InputStreamReader(is));
			String dataRow = CSVFile.readLine();
			dataRow = CSVFile.readLine();
			Tile tempTile;
			int color;
			boolean solid;
			boolean breakable;
			int[] solidBox;
			int[] breakableBox;

			AnimatedSprite sprite;
			SpriteSheet sheet;
			int height;
			int width;
			String path;
			int tileRow;
			int tileColumn; 
			int numberOfDirections;
			int framesPerDirection;
			int frameRate;

			while (dataRow != null)
			{
				color = 0;
				tileRow = 0;
				tileColumn = 0;
				solid = false;
				breakable = false;
				solidBox = new int[4];
				breakableBox = new int[4];
				numberOfDirections = 0;
				framesPerDirection = 0;
				frameRate = 0;
				path = "";
				height = 0;
				width = 0;

				String[] dataArray = dataRow.split(",");
				int i = 0;
				for (String item:dataArray) 
				{ 
					switch (i)
					{
					case 0:
						color = Integer.parseInt(item,16);
						color = color + 0xff000000;
						i++;
						break;
					case 1:
						tileRow = Integer.parseInt(item);
						i++;
						break;
					case 2:
						tileColumn = Integer.parseInt(item);
						i++;
						break;
					case 3:
						if(item.equals("TRUE"))solid = true;
						i++;
						break;
					case 4:
						if(item.equals("TRUE"))breakable = true;
						i++;
						break;
					case 5:
					case 6:
					case 7:
					case 8:
						solidBox[i-5] = Integer.parseInt(item);
						i++;
						break;
					case 9:
					case 10:
					case 11:
					case 12:
						breakableBox[i-9] = Integer.parseInt(item);
						i++;
						break;
					case 13:
						numberOfDirections = Integer.parseInt(item);
						i++;
						break;
					case 14:
						framesPerDirection = Integer.parseInt(item);
						i++;
						break;
					case 15:
						frameRate = Integer.parseInt(item);
						i++;
						break;
					case 16:
						path = item;
						i++;
						break;
					case 17:
						height = Integer.parseInt(item);
						i++;
						break;
					case 18:
						width = Integer.parseInt(item);
						i++;
						break;
					default:
						i++;

					}
				}

				sheet = new SpriteSheet(path,height,width);
				sprite = new AnimatedSprite(sheet,tileRow,tileColumn,numberOfDirections,framesPerDirection,frameRate,true);
				tempTile = new Tile(0,0,this,solid,breakable,sprite);
				addTile(tempTile, color);
				//System.out.print("added");
				if(solid)
				{
					tempTile.setImmovableBox(solidBox[0], solidBox[1], solidBox[2], solidBox[3]);
				}
				if(breakable)
				{
					tempTile.setVulnerableBox(solidBox[0], solidBox[1], solidBox[2], solidBox[3]);
				}
				dataRow = CSVFile.readLine();
			}

			CSVFile.close();
			load();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException ex){
			ex.printStackTrace();
		}

	}
	public void drawPaths(Graphics g, Screen screen){
		//
		for(int i = 0; i < layerEntities.length; i++){
			for(int j = 0; j < layerEntities[i].size(); j++){
				if(layerEntities[i].get(j) instanceof NPC){
					NPC npc = (NPC)layerEntities[i].get(j);
					List<Node> path = npc.getPath();
					if(path != null){
						for(int z = 0; z < path.size(); z++){
							g.setColor(Color.black);

							g.fillRect((int)(path.get(z).getX()*3-screen.getXOffset()*3), (int)(path.get(z).getY()*3-screen.getYOffset()*3), 10, 10);

						}
					}
				}
			}
		}
	}
	public void drawStates(Graphics g){
		int entityNumber = 0;
		g.setFont(new Font("OptimusPrinceps", Font.PLAIN,20));
		g.setColor(Color.white);
		for(int i = 0; i < layerEntities.length; i++){
			for(int j = 0; j < layerEntities[i].size(); j++){
				if(layerEntities[i].get(j) instanceof NPC){
					entityNumber++;
					NPC npc = (NPC)layerEntities[i].get(j);
					String state = "NPC #" + entityNumber + npc.getState();
					g.drawString(state, 10, 10 + entityNumber * 22);
				}
			}
		}


	}
	public Player getPlayer() {
		for(int i = 0; i < layerEntities.length; i++){
			for(int j = 0; j < layerEntities[i].size(); j++){
				if(layerEntities[i].get(j) instanceof Player){
					System.out.println("found");
					return (Player)layerEntities[i].get(j);
				}
			}
		}
		return null;
	}


}
