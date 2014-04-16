package menu;

import graphics.Screen;
import graphics.SpriteSheet;

public class Font {

	private static final int SIZE = 16;
	private static SpriteSheet sheet = new SpriteSheet("/textures/spriteSheets/menuSheets/MenuSpriteSheet.png", SIZE, SIZE);
	private static final String CHARS = ""
			+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ.!?,1234567890 ";

	public void render(String message, int x, int y, Screen screen) {

		message = message.toUpperCase();

		for (int i = 0; i < message.length(); i++){
			int charIndex = CHARS.indexOf(message.charAt(i));
			
			if (charIndex >= 0){
				screen.render(x + (i * SIZE), y, sheet.getSprite(charIndex + 50));
			}
		}
	}
}
