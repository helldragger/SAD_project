package SAD;

import SAD.GUI.GUI;
import SAD.Game.Game;
import SAD.Game.GameSpeed;
import SAD.Player.AI.AI;
import SAD.Player.Player;

public class Main {
	public static void main(String[] args) {
		// Load maps into memory
		// Prepare the Graphical UI
		
		boolean randomness = true;
		int preset = 1;
		int server_qty = 20;
		int probability = 5;
		int max_link = 3;
		
		// generate a random map to play
		// generate the players
		//TODO menu to choose who have to play, a human or a AI
		Player atk = new AI();
		Player def = new AI();
		//create the game
		//preset choice:
//		Game game = new Game(atk, def, preset);
		//random choice:
		Game game = new Game(atk, def, server_qty, probability, max_link, GameSpeed.SLOW);
		
		
		//initialize the GUI
		GUI.load_graph(game);
		//run the game til the end of it.
		game.run();
		//that's it.
		//TODO maybe an infinite loop to continue the game or statistics, logs etc?
		
	}
}
