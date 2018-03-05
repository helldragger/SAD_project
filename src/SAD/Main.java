package SAD;

import SAD.AI.AI;
import SAD.Game.Game;
import SAD.Game.GameSpeed;

public class Main {
	public static void main(String[] args) throws Exception {
		
		
		int server_qty = 6;
		int infected = 1;
		int probability = 5;
		boolean preset = false;
		boolean science = false;
		
		String help = "VALID FLAGS (n designate an integer) :\n" +
				"AI RELATED\n" +
				format_flag_command("e", "elagage", "Uses an alphabeta pruning instead of a simple minmax") +
				format_num_command("a", "attacker", AI.attacker_depth, "Determines the depth of the attacker search") +
				format_num_command("d", "defender", AI.defender_depth, "Determines the depth of the defender search") +
				"MAP RELATED\n" +
				format_flag_command("t", "test", "Uses a predefined testing map, ignores the parameters below") +
				format_num_command("i", "infected", infected, "Determines the quantity of starting infected servers") +
				format_num_command("l", "linking", probability, "Determines the probability of linking two servers") +
				format_num_command("s", "servers", server_qty, "Determines the quantity of servers to generate") +
				"UI RELATED\n" +
				format_flag_command("v", "verbose", "Disable the GUI and switch to a text-only representation") +
				format_num_command("u", "update", Game.game_speed.get_index(), "Change the GUI update speed. Slowest:1 Fastest:6") +
				"MISC\n" +
				format_flag_command("h", "help", "Display this message and exits the program") +
				format_flag_command("x", "", "Starts an in-depth analysis between minmax and alphabeta. Take into account above options. (WARNING: CPU INTENSIVE AND TIME COMSUMING)");
		
		
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
				// AI RELATED
				case "-e":
				case "--elagage":
					AI.set_alphabeta(true);
					break;
				case "-d":
				case "--defender":
					i++;
					AI.set_defender_depth(Integer.valueOf(args[i]));
					break;
				case "-a":
				case "--attacker":
					i++;
					AI.set_attacker_depth(Integer.valueOf(args[i]));
					break;
				
				
				// MAP RELATED
				case "-i":
				case "--infected":
					i++;
					infected = Integer.valueOf(args[i]);
					break;
				case "-l":
				case "--linking":
					i++;
					probability = Integer.valueOf(args[i]);
					break;
				case "-s":
				case "--servers":
					i++;
					server_qty = Integer.valueOf(args[i]);
					break;
				case "-t":
				case "--test":
					i++;
					preset = true;
					break;
				
				
				// UI RELATED
				case "-v":
				case "--verbose":
					Game.verbose = true;
					break;
				case "--speed":
					i++;
					Game.game_speed = GameSpeed.from_index(Integer.valueOf(args[i]));
					break;
				// OTHER
				case "-x":
					science = true;
					break;
				default:
					System.out.println("ERROR: " + args[i] + " is not a valid option\n");
				case "-h":
				case "--help":
					System.out.println(help);
					return;
			}
		}
		
		if (science) {
			Stats.analyze_pruning(6, 5, 1);
			return;
		}
		if (probability > 100 | probability < 0)
			throw new Exception("Probability must be between 0 and 100");
		if (server_qty < 1)
			throw new Exception("Server quantity must be >= 1");
		if (infected < 1)
			throw new Exception("Quantity of starting infected servers must be >= 1");
		
		Game game;
		if (preset)
			game = new Game(1);
		else
			game = new Game(server_qty, probability, infected);
		
		//run the game 'til the end of it.
		game.run();
		//that's it.
		//TODO maybe an infinite loop to continue the game or statistics, logs etc?
		
	}
	
	private static String format_flag_command(String s, String l, String desc) {
		String header = (l == "") ? "-" + s : "-" + s + "/--" + l;
		return header + "\t\t" + desc + "\n";
	}
	
	private static String format_num_command(String s, String l, int value, String desc) {
		String header = (l == "") ? "-" + s : "-" + s + "/--" + l;
		return header + " n\t(default: " + value + ")\t" + desc + "\n";
	}
}
