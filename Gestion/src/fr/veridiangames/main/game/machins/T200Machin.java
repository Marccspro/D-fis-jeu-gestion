package fr.veridiangames.main.game.machins;

import fr.veridiangames.main.game.Game;

public class T200Machin extends Machin
{
	public T200Machin() {
		super("T200", 5000, 200, 30, 6);
	}
	
	public void onChangingDay(Game game)
	{
		super.onChangingDay(game);
	}
}