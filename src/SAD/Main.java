package SAD;

import SAD.Player.AI.AI;
import SAD.Player.Player;

import java.util.Random;

public class Main {
    public static void main(String[] args){
    	// Load maps into memory
    	Data.load_maps();
    	
    	// generate a random map to play
        Data map = Data.generate_map();
        // generate the players
	    //TODO menu to choose who have to play, a human or a AI
        Player atk = new AI();
        Player def = new AI();
        //create the game
        Game game = new Game(atk, def, map);
        //run the game til the end of it.
        game.run();
        //that's it.
	    //TODO maybe an infinite loop to continue the game or statistics, logs etc?
	    
    }
}
