package fr.veridiangames.main;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import fr.veridiangames.main.game.Game;

public class Main {
	
	private Game game;
	
	public Main()
	{
		game = new Game();
	}
	
	public void update()
	{
		game.update();
	}
	
	public void render()
	{
		glClear(GL_COLOR_BUFFER_BIT);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		game.render();
	}
	
	public static void main(String[] args)
	{
		try {
			Display.setDisplayMode(new DisplayMode(1280, 720));
			Display.setResizable(false);
			Display.create();
			
			glClearColor(0.95f, 0.95f, 0.95f, 1.0f);
			
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		Main main = new Main();
		
		while (!Display.isCloseRequested()) 
		{
			Display.sync(60);
			main.update();
			main.render();
			Display.update();
		}
		
		Display.destroy();
		System.exit(0);
	}
}
