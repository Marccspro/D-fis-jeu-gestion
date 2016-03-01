package fr.veridiangames.main.game.machins;

import fr.veridiangames.main.game.Game;

public abstract class Machin {
	
	private String name;
	private int cost;
	private int income;
	private int charges;
	private int size;
	private int life;
	private boolean removed;
	private int time;
	
	public boolean isRemoved() {
		return removed;
	}

	public Machin(String name, int cost, int income, int charges, int size)
	{
		this.name = name;
		this.cost = cost;
		this.income = income;
		this.charges = charges;
		this.size = size;
		this.life = 100;
		this.time = 0;
	}
	
	public void onStart(Game game)
	{
		game.remove(cost);
		game.addCharge(charges);
		game.addIncome(income);
	}
	
	public void onRemove(Game game)
	{
		game.addCharge(-charges);
		game.addIncome(-income);
	}
	
	public void onChangingDay(Game game)
	{
		time++;
		if (time > life)
		{
			removed = true;
		}
	}

	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	public int getIncome() {
		return income;
	}

	public int getCharges() {
		return charges;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getLife() {
		return life;
	}

	public int getTime() {
		return time;
	}	
}