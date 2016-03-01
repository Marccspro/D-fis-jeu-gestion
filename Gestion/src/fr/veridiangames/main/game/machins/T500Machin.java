package fr.veridiangames.main.game.machins;

import fr.veridiangames.main.game.Game;

public class T500Machin extends Machin
{
	public T500Machin() {
		super("T500", 10000, 500, 100, 8);
	}
	
	public void onChangingDay(Game game)
	{
		super.onChangingDay(game);
	}
}