package main;

import java.applet.Applet;
import java.awt.BorderLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends Applet {
	
	private static Game game = new Game();
	
	public static void main(String[] args) {
		game.frame.setResizable(false);//needs to be the first thing done to frame
		game.frame.setTitle(Game.TITLE);
		game.frame.add(game);
		game.frame.pack();//sets the frame to the size of its components
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);//null will set the screen in the center of the screen
		game.frame.setVisible(true);
		game.start();//starts the game thread
	}
	
	public void init() {
		setLayout(new BorderLayout());
		add(game, BorderLayout.CENTER);
		setMaximumSize(game.getMaximumSize());
		setMinimumSize(game.getMinimumSize());
		setPreferredSize(game.getPreferredSize());
	}
	
	public void start() {
		game.start();
	}
	
	public void stop() {
		game.stop();
	}
}
