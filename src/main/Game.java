package main;

import graphics.*;
import input.*;
import inventory.Inventory;
import items.*;
import debug.Debug;
import level.Level;
import menu.MainMenu;
import saveload.SaveLoad;
import sound.SoundManager;
import entity.Player;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * Our main game engine, this is just the engine itself, loading up the additional classes
 * that are needed.
 * 
 * @author Josh, Dan, Erin, Tony
 */
public class Game extends Canvas implements Runnable {

	/**
	 * This is the serialized number to identify game.
	 */
	private static final long serialVersionUID = -3429405132991480340L;

	// Resolution of the Game
	private static int width = 320;
	private static int height = 225;

	//scales "width" by the variable "scale" also scales the size of pixels by that same amount
	private static int scale = 3;

	// Game Title
	public final static String TITLE = "It came from the 90s";

	// 
	private Thread thread;
	public JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private Inventory gameInventory = new Inventory(25); //used to create game inventory
	private int itemsInGameInventory = 0; // used to create game inventory
	private MainMenu menu;
	private boolean running = false;//an indicator that the game is running
	private SoundManager soundManager = SoundManager.getInstance();
	private Screen screen;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	
	// gets the array of pixels that makes up the image, then gets the databuffer that 
	// makes up that array that we can manipulate
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

	private Graphics g;
	private BufferStrategy bs;

	private Debug debug;

	private SaveLoad saveLoad = new SaveLoad();
	private ArrayList<Object> objectList;
	private int temp = 0;
	
	
	//Number of frames for player to wait until reset after death
	private int deathLength=500;
	private int deathCounter=deathLength;

	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);//create a size to the JFrame
		setPreferredSize(size);

		screen = new Screen(width, height);

		frame = new JFrame();
		key = new Keyboard();

		debug = new Debug(key);

		level = new Level("/textures/spawnlevel.png","/textures/cityTiles.csv",32,screen);
		menu = new MainMenu(key);
		/*
		 * mouse and key listener 
		 */
		try {
			getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(gameInventory);
		addKeyListener(key);	
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		SpriteSheet spritesheet = new SpriteSheet("/textures/spritesheet.png", 32, 32);
		AnimatedSprite sprite = new AnimatedSprite(spritesheet, 0, 5, 4, 8, 9, true);

		player = new Player(20 * 32, 70 * 32,level,sprite, key, mouse, gameInventory);
		level.add(player, 3);
		soundManager.setListener(player);
		level.playBackgroundMusic();
		level.addMenu(menu);

		//TODO A test for the create to be able to save.
		objectList = createList();

	}

	/**
	 * returns the width of the frame * scale
	 * @return int
	 */
	public static int getWindowWidth() {
		return width * scale;
	}

	/**
	 * returns the height of the frame * scale
	 * @return int
	 */
	public static int getWindowHeight() {
		return height * scale;
	}

	/**
	 * The start of the game thread
	 */
	public synchronized void start()
	{
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	/**
	 * A Mystical place where all threads go to die
	 */
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The game threads run method calling start() method
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		//Timer code1
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; 
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		//The game loop
		while(running)
		{
			//Timer code2
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;//adds the difference and divides by 
			lastTime = now;

			while(delta >= 1)
			{
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println(updates + " updates, frames " + frames + " fps");
				frame.setTitle(TITLE + "  |  " + updates + " updates, frames " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	/**
	 * this method controls all of the logic in the game
	 * restricted method so that your code only gets called 60 times a second
	 */
	public void update()
	{
		key.update(); //key listener update
		level.update();
		menu.update(); //trigger if menu goes up

		//TODO This is a debug only update, should be removed from final version!!!!!
		debug.debugButton();

		/*
		try {
			if ((temp % 1000) == 0)
				saveLoad.save(objectList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		temp++;
		*/
		
	}
	/**
	 * this method controls the drawing the the screen
	 * unrestricted method so that it can render all it wants
	 */
	public void render()
	{
		//storing the next rendered screen
		bs = getBufferStrategy();
		if(bs == null)//so we don't create BufferStrategy every time we render
		{
			//buffers the next 3 renders 
			createBufferStrategy(3);
			return;
		}

		screen.clear();//clears the previous pixels
		double xScroll = player.getPositionX()-screen.getWidth() / 2;
		double yScroll = player.getPositionY()-screen.getHeight() / 2;
		level.render((int)xScroll, (int)yScroll);


		menu.render(screen);

		for(int i = 0; i < pixels.length; i ++)
		{	//sets the rendered screen pixel array to the game pixels array
			pixels[i] = screen.getPixels()[i];
		}

		if (debug.turnOffGraphics()) {
			for(int i = 0; i < pixels.length; i ++)
			{	//sets the rendered screen pixel array to the game pixels array
				pixels[i] = 0xFF000000;
			}
		}

		g = bs.getDrawGraphics();//creates a way to draw what is in the Buffer to the screen

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		if (debug.turnOffGraphics())
			level.getCollisionManager().drawHitBoxes(g,screen);
		
		
		if(player.getStats().isDead())
		{
			g.setColor(new Color(0, 0, 0, 150 ));
			g.fillRect(0,250,1024, 150);
			g.setColor(Color.red);
			g.setFont(new Font("OptimusPrinceps", Font.PLAIN,100)); 
			g.drawString("YOU DIED", 240, 350);
			
			deathCounter--;
			if(deathCounter == 0)
			{
				player.reset();
				deathCounter=deathLength;
			}
		}
		g.setFont(new Font("OptimusPrinceps", Font.PLAIN,12)); 
		g.setColor(Color.black);
		

		// Will turn off the display health if the debug mode is on.
		if (!debug.turnOffGraphics())
			displayHealth(menu);
		if(debug.turnOffGraphics()){
			level.drawStates(g);
		}
		//level.drawPaths(g, screen);
		//g.fillRect(Mouse.getX() - 32, Mouse.getY() - 32, 64, 64);
		//g.drawString("Button : " + Mouse.getButton(),Mouse.getX() - 32, Mouse.getY() - 32);
		g.dispose();//removes the graphics after you remove them
		bs.show();
	}
	/**
	 * initializing of the items in game from a .csv file
	 * @throws IOException
	 */
	private void getFile() throws IOException
	{
		InputStream is = this.getClass().getResourceAsStream("/Items.csv");
		
		BufferedReader CSVFile = 
				new BufferedReader(new InputStreamReader(is));

		String dataRow = CSVFile.readLine();
		dataRow = CSVFile.readLine();
		String itemType;
		String itemName;
		double itemCost;
		int maxItemsInStack;
		double itemWeight;
		double foodCalories;
		int bodyPart;
		int defense;
		int attack;
		int hands; // the number of hands this weapon needs to weild it

		while (dataRow != null)
		{
			itemType = "";
			itemName = "";
			itemCost = 0;
			maxItemsInStack = 0;
			itemWeight = 0;
			foodCalories = 0;
			bodyPart = 0;
			defense = 0;
			attack = 0;
			hands = 0;

			String[] dataArray = dataRow.split(",");
			int i = 0;
			for (String item:dataArray) 
			{ 
				switch (i)
				{
				case 0:
					itemType = item;
					i++;
					break;
				case 1:
					itemName = item;
					i++;
					break;
				case 2:
					itemCost = Double.parseDouble(item);
					i++;
					break;
				case 3:
					maxItemsInStack = Integer.parseInt(item);
					i++;
					break;
				case 4:
					itemWeight = Double.parseDouble(item);
					i++;
					break;
				case 5:
					foodCalories = Double.parseDouble(item);
					i++;
					break;
				case 6:
					bodyPart = Integer.parseInt(item);
					i++;
					break;
				case 7:
					defense = Integer.parseInt(item);
					i++;
					break;
				case 8:
					attack = Integer.parseInt(item);
					i++;
					break;
				case 9:
					hands = Integer.parseInt(item);
					i++;
					break;
				default:
					i++;
				}
			}
			if(itemType.equals("Food"))
			{
				Food newFoodItem = new Food(itemName,itemCost,maxItemsInStack,itemWeight,foodCalories);
				gameInventory.add(newFoodItem,itemsInGameInventory);
				itemsInGameInventory++;
			}
			else if(itemType.equals("Armor"))
			{
				Armor newArmorItem = new Armor(itemName, itemCost, maxItemsInStack,itemWeight,bodyPart,defense);
				gameInventory.add(newArmorItem, itemsInGameInventory);
				itemsInGameInventory++;
			}
			else if(itemType.equals("Weapon"))
			{
				Weapon newWeaponItem = new Weapon(itemName, itemCost, maxItemsInStack,itemWeight,attack,hands);
				gameInventory.add(newWeaponItem, itemsInGameInventory);
				itemsInGameInventory++;
			}
			else
			{

			}
			dataRow = CSVFile.readLine();
		}

		CSVFile.close();
	}

	/**
	 * This will display the health and MP in the bottom left of the screen.
	 */
	private void displayHealth(MainMenu menu) {
		// If the menu is up, will not render the bars.
		if (menu.MenuUp())
			return;
		
		int barWidth=275;
		int subDivisionLength = barWidth/4;
		
		// This is setting the color of the life bar.
		if (((double)player.getStats().getCurrentHP()/(double)player.getStats().getMaxHP()) <= 0.25) {
			g.setColor(Color.red);
		} else if (((double)player.getStats().getCurrentHP()/(double)player.getStats().getMaxHP()) <= 0.5) {
			g.setColor(Color.yellow);
		} else {
			g.setColor(Color.green);
		}

		// This is drawing the life bar and MP bar.
		g.fillRect(31, 531, (int)(((double)player.getStats().getCurrentHP()/(double)player.getStats().getMaxHP()) * barWidth), 25);
		
		g.setColor(Color.black);
		// Lines for the HP
		for(int x=(30+subDivisionLength);x<(30+barWidth-subDivisionLength);x+=subDivisionLength)
		{
			g.drawLine(x, 546, x, 554);
		}


		// This will change the text color based on the lifebar changing color.
		if (((double)player.getStats().getCurrentHP()/(double)player.getStats().getMaxHP()) <= 0.25) {
			g.setColor(Color.white);
		} else if (((double)player.getStats().getCurrentHP()/(double)player.getStats().getMaxHP()) <= 0.5) {
			g.setColor(Color.black);
		} else {
			g.setColor(Color.white);
		}

		
		// This will display the HP in the life bar as an int.
		g.drawString(("HP " + player.getStats().getCurrentHP() + "/" + player.getStats().getMaxHP()), 40, 550);

		// Setting the color of the MP bar.
		g.setColor(Color.blue);
		g.fillRect(31, 582, (int)(((double)player.getStats().getCurrentMP()/(double)player.getStats().getMaxMP()) * barWidth), 25);

		g.setColor(Color.black);
		// Lines for the MP
		for(int x=(30+subDivisionLength);x<(30+barWidth-subDivisionLength);x+=subDivisionLength)
		{
			g.drawLine(x, 596, x, 604);
		}
		
		
		// Color that is set for the words being displayed.
		g.setColor(Color.white);
		g.drawString(("MP " + player.getStats().getCurrentMP() + "/" + player.getStats().getMaxMP()), 40, 600);

		// Draws the black borders
		g.setColor(Color.black);
		g.drawRect(30, 530, barWidth, 26);
		g.drawRect(30, 581, barWidth, 26);


	}

	public ArrayList<Object> createList() {
		ArrayList<Object> list = new ArrayList<Object>();

		list.add(player);

		return list;
	}

}
