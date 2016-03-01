package fr.veridiangames.main.game.machins;

import fr.veridiangames.main.game.Game;

public class M1Machin extends Machin
{
	public M1Machin() {
		super("M1", 1000, 50, 10, 1);
	}
	
	public void onChangingDay(Game game)
	{
		super.onChangingDay(game);
	}
}