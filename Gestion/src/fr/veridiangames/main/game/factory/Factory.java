package fr.veridiangames.main.game.factory;

import fr.veridiangames.main.game.Game;

public class Factory {
	String name;
	int capacity;
	int cost;
	int charges;

	public Factory(String name, int capacity, int cost, int charges)
	{
		this.name = name;
		this.capacity = capacity;
		this.cost = cost;
		this.charges = charges;
	}
	
	public void onStart(Game game)
	{
		game.remove(cost);
		game.addCharge(charges);
		game.machinsCapacity += capacity;
	}
	
	public void onChangingDay(Game game)
	{
	}

	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCharges() {
		return charges;
	}

	public void setCharges(int charges) {
		this.charges = charges;
	}
}
