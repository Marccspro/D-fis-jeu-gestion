package fr.veridiangames.main.guis;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Gui {
	
	private static Texture font = Texture.loadTexture("/font.png");
	
	private static String chars = 
			"abcdefghijklmnopqrstuvwxyz " +
			"0123456789:!?.,()€+-/      " +
			"                           " +
			"                           " +
			"";
	
	public static void drawString(String msg, int x, int y, int size)
	{
		msg = msg.toLowerCase();
		glEnable(GL_TEXTURE_2D);
		font.bind();
		glBegin(GL_QUADS);
		
		for (int i = 0; i < msg.length(); i++)
		{
			char c = msg.charAt(i);
			int offs = i * size; 
			charData(c, x + offs, y, size);
		}
		
		glEnd();
		font.unbind();
		glDisable(GL_TEXTURE_2D);
	}
	
	public static void charData(char c, int x, int y, int size)
	{
		int i = chars.indexOf(c);
		int xo = i % 27;
		int yo = i / 27;
		
		glTexCoord2f((0 + xo) / 27.0f, (0 + yo) / 4.0f); glVertex2f(x, y);
		glTexCoord2f((1 + xo) / 27.0f, (0 + yo) / 4.0f); glVertex2f(x + size, y);
		glTexCoord2f((1 + xo) / 27.0f, (1 + yo) / 4.0f); glVertex2f(x + size, y + size);
		glTexCoord2f((0 + xo) / 27.0f, (1 + yo) / 4.0f); glVertex2f(x, y + size);
	}
	
	public static void drawQuad(int x, int y, int w, int h)
	{
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x + w, y);
		glVertex2f(x + w, y + h);
		glVertex2f(x, y + h);
		glEnd();
	}
	
	public static void drawTexture(int tex, int x, int y, int w, int h)
	{
		int xo = tex % 8;
		int yo = tex / 8;
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_QUADS);
		glTexCoord2f((0 + xo) / 8.0f, (0 + yo) / 8.0f); glVertex2f(x, y);
		glTexCoord2f((0 + xo) / 8.0f, (0 + yo) / 8.0f); glVertex2f(x + w, y);
		glTexCoord2f((0 + xo) / 8.0f, (0 + yo) / 8.0f); glVertex2f(x + w, y + h);
		glTexCoord2f((0 + xo) / 7.0f, (0 + yo) / 8.0f); glVertex2f(x, y + h);
		glEnd();
		glDisable(GL_TEXTURE_2D);
	}
	
	public static boolean button(String name, int x, int y, int w)
	{
		return button(name, x, y, w, 32);
	}
	
	public static boolean button(String name, int x, int y, int w, int textStart)
	{
		boolean r = false;
		
		int mx = Mouse.getX();
		int my = Display.getHeight() - Mouse.getY();
		if (mx > x && mx < x + w && my > y && my < y + 32)
		{
			Gui.color(0.5f, 0.5f, 0.5f, 1.0f);
			while (Mouse.next()) {
				if (Mouse.isButtonDown(0))
				{
					Gui.color(0.6f, 0.6f, 0.6f, 1.0f);
					
					r = true;
				}
			}
		}
		else
		{
			Gui.color(0.4f, 0.4f, 0.4f, 1.0f);			
		}
		
		Gui.drawQuad(x, y, w, 32);
		Gui.drawQuad(x, y, w, 32);
		Gui.color(1f, 1f, 1f, 1.0f);
		
		Gui.drawString(name, x + textStart, y + 6, 24);
		
		return r;
	}
	
	public static void color(float r, float g, float b, float a)
	{
		glColor4f(r, g, b, a);
	}
}
