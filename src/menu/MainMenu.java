package menu;

import graphics.Screen;
import graphics.SpriteSheet;
import input.Keyboard;

public class MainMenu extends Menu {

	private static final int SIZE = 16;

	private boolean menuUp = false;
	private Keyboard input;
	private int position;
	private boolean pressed=false;

	private int totalSelections = 3;

	private SpriteSheet sheet = new SpriteSheet("/textures/spriteSheets/menuSheets/MenuSpriteSheet.png", SIZE, SIZE);

	private Font font = new Font();

	public MainMenu(Keyboard input){
		this.input  = input;
	}

	public void update(){
		
		if (input.menu){
			menuUp = true;
		} else if (input.cancel && menuUp) {
			menuUp = false;
			position = 0;
		}
		if (menuUp) {
		
			if (pressed==false){
				if (input.up) {
					position = --position % totalSelections;
					if (position < 0)
						position = position + totalSelections;
				}
				if (input.down) {
					position = ++position % totalSelections;
				}
				if(input.up || input.down)
				{
					pressed=true;
				}
			}
			if(!input.up && !input.down)
				pressed=false;
		}
	}

	public void render(Screen screen)
	{
		if (menuUp) {
			// Rendering of the Menu
			for (int y = 0; y < 13; y++)
				for (int x = 0; x < 20; x++) 
					screen.render(screen.getXOffset() + x * SIZE, screen.getYOffset() + y*SIZE, sheet.getSprite(11));
			for (int x = 0; x < 19; x++) {
				screen.render(screen.getXOffset() + x * SIZE, screen.getYOffset() + 0, sheet.getSprite(1));
				screen.render(screen.getXOffset() + x * SIZE, screen.getYOffset() + SIZE * 13, sheet.getSprite(21));
			}
			for (int y = 0; y < 14; y++) {
				screen.render(screen.getXOffset(), screen.getYOffset() + y * SIZE, sheet.getSprite(10));
				screen.render(screen.getXOffset() + 19 * SIZE, screen.getYOffset() + y * SIZE, sheet.getSprite(12));
			}
			screen.render(screen.getXOffset(), screen.getYOffset(), sheet.getSprite(0));
			screen.render(screen.getXOffset() + 19 * SIZE, screen.getYOffset(), sheet.getSprite(2));
			screen.render(screen.getXOffset(), screen.getYOffset() + 13 * SIZE, sheet.getSprite(20));
			screen.render(screen.getXOffset() + 19 * SIZE, screen.getYOffset() + 13 * SIZE, sheet.getSprite(22));


			// Render the font into it?
			font.render("Items", screen.getXOffset() + 30, screen.getYOffset() + 10, screen);
			font.render("Stats", screen.getXOffset() + 30, screen.getYOffset() + 30, screen);
			font.render("Exit", screen.getXOffset() + 30, screen.getYOffset() + 50, screen);


			// Render the Arrow
			screen.render(screen.getXOffset() + 15, (screen.getYOffset() + 10) + (position * 20), sheet.getSprite(3));
		}
	}

	public boolean MenuUp() {
		return menuUp;
	}	
}
