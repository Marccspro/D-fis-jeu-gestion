package fr.veridiangames.main.game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.veridiangames.main.game.factory.Factory;
import fr.veridiangames.main.game.factory.LargeFactory;
import fr.veridiangames.main.game.factory.MidFactory;
import fr.veridiangames.main.game.factory.TinyFactory;
import fr.veridiangames.main.game.machins.M1Machin;
import fr.veridiangames.main.game.machins.M2Machin;
import fr.veridiangames.main.game.machins.Machin;
import fr.veridiangames.main.game.machins.T100Machin;
import fr.veridiangames.main.game.machins.T200Machin;
import fr.veridiangames.main.game.machins.T500Machin;
import fr.veridiangames.main.guis.Gui;

public class Game 
{
	List<Loan> loans;
	
	List<Factory> factories;
	List<Machin> machins;
	
	List<Machin> machinsStore;
	List<Factory> factoryStore;
	
	private int time;
	private int years;
	private int days;
	
	private int capital;
	private int incoms;
	private int charges;
	private int profits;
	
	private int profitable;
	private int positive;
	
	private int maxLoans;
	
	public int machinsCapacity;
	public int machinsSize;
	
	public Game()
	{
		time = 0;
		days = 0;
		
		capital = 0;
		profits = 0;
		
		profitable = 0;
		positive = 0;
		machinsCapacity = 0;
		maxLoans = 2;
		
		factoryStore = new ArrayList<Factory>();
		factoryStore.add(new TinyFactory());
		factoryStore.add(new MidFactory());
		factoryStore.add(new LargeFactory());
		
		machinsStore = new ArrayList<Machin>();
		machinsStore.add(new M1Machin());
		machinsStore.add(new M2Machin());
		machinsStore.add(new T100Machin());
		machinsStore.add(new T200Machin());
		machinsStore.add(new T500Machin());
		
		loans = new ArrayList<Loan>();
		factories = new ArrayList<Factory>();
		machins = new ArrayList<Machin>();
	}
	
	public void update()
	{
		time++;
		if (time % 10 == 0)
		{
			time = 0;
			days++;
			capital += profits;
			
			for (Factory f : factories)
			{
				f.onChangingDay(this);
			}
			for (int i = 0; i < machins.size(); i++)
			{
				Machin m = machins.get(i);
				m.onChangingDay(this);
				if (m.isRemoved())
					removeMachin(m);
			}
			for (int i = 0; i < loans.size(); i++)
			{
				Loan loan = loans.get(i);
				if (loan.time <= 0)
				{
					charges -= loan.price;
					loans.remove(i);
				}
				loan.time--;
			}
		}
		if (days > 365)
		{
			years++;
			days = 0;
		}
		
		if (capital > 50000)
			maxLoans = 3;

		if (capital > 100000)
			maxLoans = 5;
		
		profits = incoms - charges;
		
		if (profits > 0)
			profitable = 1;
		if (profits == 0)
			profitable = 0;
		if (profits < 0)
			profitable = -1;
		
		if (capital > 0)
			positive = 1;
		if (capital == 0)
			positive = 0;
		if (capital < 0)
			positive = -1;
		
	}
	
	public void render()
	{
		factoryMenu();
		loansMenu();
		machinesMenu();
		sideMenu();
	}
	
	public void machinesMenu()
	{
		for (int i = 0; i < machins.size(); i++)
		{
			Machin m = machins.get(i);
			
			Gui.color(0.2f, 0.2f, 0.2f, 1);
			int x = 400 + 20 + (i%5) * 170;
			int y = 90 + (i/5) * 120;
			Gui.drawQuad(x, y, 150, 100);
			Gui.color(0.9f, 0.9f, 0.9f, 1);
			Gui.drawString(m.getName(), x + 3, y + 10, 24);

			Gui.color(0.0f, 0.9f, 0.0f, 1);
			Gui.drawString("+" + m.getIncome() + "€", x + 3, y + 10 + 30, 16);

			Gui.color(0.9f, 0.0f, 0.0f, 1);
			Gui.drawString("-" + m.getCharges() + "€/D", x + 3, y + 10 + 50, 16);
			
			float time = (float) m.getTime() / (float)m.getLife();
			Gui.color(time, 1-time, 0, 1);
			Gui.drawQuad(x, y + 95, (int) (150 * (1 - time)), 5);
			
			int mx = Mouse.getX();
			int my = Display.getHeight() - Mouse.getY();

			if (mx > x && my > y && mx < x + 150 && my < y + 100)
			{
				if (Gui.button("x", x, y, 32, 5))
				{
					removeMachin(m);
				}
			}
		}
	}
	
	public void loansMenu()
	{
		Gui.color(0.2f, 0.2f, 0.2f, 1);
		Gui.drawQuad(400, Display.getHeight() - 100, Display.getWidth() - 400, 100);
		Gui.color(0.1f, 0.1f, 0.1f, 1);
		Gui.drawQuad(400, Display.getHeight() - 100 - 5, Display.getWidth() - 400, 5);
		
		
		for (int i = 0; i < loans.size(); i++)
		{
			Loan loan = loans.get(i);
			int price = loan.price;
			int time = loan.time;
			int income = loan.income;
			
			Gui.color(0.1f, 0.1f, 0.1f, 1);
			Gui.drawQuad(400 + 10 + i * 125, Display.getHeight() - 95, 120, 90);
			Gui.color(0.9f, 0.9f, 0.9f, 1);
			Gui.drawString(income + "k", 400 + 10 + 12 + i * 125, Display.getHeight() - 95 + 10, 32);

			Gui.color(0.9f, 0.0f, 0.0f, 1);
			Gui.drawString("-" + price + "€/D", 400 + 10 + 5 + i * 125, Display.getHeight() - 95 + 48, 16);
			Gui.color(0.6f, 0.6f, 0.6f, 1);
			Gui.drawString("" + time + "D", 400 + 10 + 25 + i * 125, Display.getHeight() - 95 + 68, 16);
		
			int x = 400 + 10 + i * 125;
			int y = Display.getHeight() - 95;
			int w = 120;
			int h = 90;
				
			int mx = Mouse.getX();
			int my = Display.getHeight() - Mouse.getY();
			
			if (mx > x && my > y && mx < x + w && my < y + h)
			{
				if (Gui.button("x", x, y, 32, 5))
				{
					charges -= loan.price;
					capital -= loan.price * loan.time;
					loans.remove(loan);
				}
			}
		}
	}
	
	public void factoryMenu()
	{
		Gui.color(0.2f, 0.2f, 0.2f, 1);
		Gui.drawQuad(400, 0, Display.getWidth() - 400, 70);
		Gui.color(0.1f, 0.1f, 0.1f, 1);
		Gui.drawQuad(400, 70, Display.getWidth() - 400, 5);
		
		Gui.color(0.9f, 0.9f, 0.9f, 1);
		Gui.drawString("Factories: " + factories.size(), 410, 10, 24);
		Gui.drawString("Total capacity: " + machinsCapacity, 410, 10 + 32, 24);
	}
	
	public void sideMenu()
	{
		Gui.color(0.2f, 0.2f, 0.2f, 1);
		Gui.drawQuad(0, 0, 400, Display.getHeight());
		Gui.color(0.1f, 0.1f, 0.1f, 1);
		Gui.drawQuad(400, 0, 5, Display.getHeight());

		color(positive);
		Gui.drawString((positive >= 0 ? "+" : "") + capital + " €", 10, 10, 24);
		
		color(profitable);
		Gui.drawString((profitable >= 0 ? "+" : "") + profits + " €/day", 10, 10 + 32, 24);
		
		Gui.color(0.9f, 0.9f, 0.9f, 1);
		Gui.drawString("Year: " + years, 10, 10 + 32*2, 24);
		Gui.drawString("Day: " + days, 10, 10 + 32*3, 24);
		
		// STORE
		int h = 10 + 32*4 + 20;
		Gui.drawString("Store", 10, h, 32);
		Gui.drawQuad(10, h + 32, 380, 4);
		
		Gui.drawString("Machines", 10, h + 48, 24);
		for (int i = 0; i < machinsStore.size(); i++)
		{
			Machin m = machinsStore.get(i);
			if (Gui.button(m.getName(), 10, h + 48 + 26 + i * 36, 380))
			{
				addMachin(m);
			}
			int x = 10;
			int y = h + 48 + 26 + i * 36;
			int w = 380;
			int hh = 32;
			int mx = Mouse.getX();
			int my = Display.getHeight() - Mouse.getY();
			
			if (mx > x && my > y && mx < x + w && my < y + hh)
			{
				Gui.color(0.0f, 0.0f, 0.0f, 0.9f);
				Gui.drawQuad(x, y, w + 100, hh);
				
				Gui.color(0.0f, 0.7f, 1f, 1.0f);
				Gui.drawString((m.getCost() / 1000) + "k€", x + 10, y + 5, 24);
				
				Gui.color(0.9f, 0.0f, 0.0f, 1.0f);
				Gui.drawString(m.getCharges() + "€/D", x + 130, y + 5, 24);

				Gui.color(0.0f, 0.6f, 0.0f, 1.0f);
				Gui.drawString("+" + m.getIncome() + "€/D", x + 300, y + 5, 24);
			}
		}
		Gui.color(0.9f, 0.9f, 0.9f, 1.0f);
		
		Gui.drawString("Factories", 10, h + 48*6, 24);
		for (int i = 0; i < factoryStore.size(); i++)
		{
			Factory f = factoryStore.get(i);
			if (Gui.button(f.getName(), 10, h + 48*6 + 26 + i * 36, 380))
			{
				addFactory(f);
			}
			
			int x = 10;
			int y = h + 48*6 + 26 + i * 36;
			int w = 380;
			int hh = 32;
			int mx = Mouse.getX();
			int my = Display.getHeight() - Mouse.getY();
			
			if (mx > x && my > y && mx < x + w && my < y + hh)
			{
				Gui.color(0.0f, 0.0f, 0.0f, 0.9f);
				Gui.drawQuad(x, y, w - 100, hh);
				
				Gui.color(0.0f, 0.7f, 1f, 1.0f);
				Gui.drawString((f.getCost() / 1000) + "k€", x + 10, y + 5, 24);
				
				Gui.color(0.9f, 0.0f, 0.0f, 1.0f);
				Gui.drawString(f.getCharges() + "€/D", x + 130, y + 5, 24);
			}
		}
		Gui.color(0.9f, 0.9f, 0.9f, 1.0f);
		
		// BANQUE
		h = Display.getHeight() - 130;
		Gui.drawString("Bank", 10, h, 32);
		Gui.drawQuad(10, h + 32, 380, 5);

		if (Gui.button("+10k", 10, h + 48, 110, 10) && loans.size() < maxLoans)
		{
			add(10000);
			addLoan(10, 100, 110);
		}
		if (Gui.button("+50k", 10, h + 48*2 - 5, 110, 10) && loans.size() < maxLoans)
		{
			add(50000);
			addLoan(50, 500, 110);
		}			
		
		Gui.drawString("-100€/day", 150, h + 48 + 8, 24);
		Gui.drawString("-500€/day", 150, h + 48*2, 24);
	}
	
	public void color(int situation)
	{
		if (situation == -1)
		{
			Gui.color(0.9f, 0, 0, 1);
		}
		else if (situation == 0)
		{
			Gui.color(0.5f, 0.5f, 0.5f, 1);
		}
		else if (situation == 1)
		{
			Gui.color(0, 0.9f, 0, 1);
		}
	}
	
	public int getTime() {
		return time;
	}

	public int getDays() {
		return days;
	}
	
	public void add(int money)
	{
		capital += money;
	}
	
	public void remove(int money)
	{
		capital -= money;
	}
	
	public void addCharge(int money)
	{
		charges += money;
	}
	
	public void addIncome(int money)
	{
		incoms += money;
	}
	
	public void addFactory(Factory f)
	{
		if (capital - f.getCost() < 0)
			return;
		
		f.onStart(this);
		factories.add(f);
	}
	
	public void addMachin(Machin m)
	{
		if (capital - m.getCost() < 0)
			return;
		
		if ((machinsSize + m.getSize()) <= machinsCapacity) {
			m.onStart(this);
			machinsSize += m.getSize();
			try {
				machins.add(m.getClass().newInstance());
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removeMachin(Machin m)
	{
		m.onRemove(this);
		machins.remove(m);
	}
	
	public void addLoan(int income, int price, int time)
	{
		loans.add(new Loan(income, price, time));
		addCharge(price);
	}
	
	class Loan
	{
		int income;
		int price;
		int time;
		
		Loan(int income, int price, int time)
		{
			this.income = income;
			this.price = price;
			this.time = time;
		}
	}
}
