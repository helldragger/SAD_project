package SAD.Game;

import SAD.Controls.Move.Attacc;
import SAD.Controls.Move.Move;
import SAD.Controls.Move.Protecc;
import SAD.GUI.GUI;
import SAD.Player.Player;

import java.util.TreeSet;

public class Game {
	private static GameSpeed game_speed = GameSpeed.NORMAL;
	private final Player attacker;
	private final Player defender;
	public Data map;
	private Boolean is_attacker_turn = true;
	private Boolean has_ended = false;
	private Boolean is_simulated = false;
	
	public Game(final Player attacker, final Player defender, final int server_qty, final int probability, final int max_links, final GameSpeed speed) {
		this.attacker = attacker;
		this.defender = defender;
		this.map = Data.load_random_map(server_qty, probability, max_links);
		game_speed = speed;
	}
	
	public Game(final Player attacker, final Player defender, final int preset, final GameSpeed speed) {
		this.attacker = attacker;
		this.defender = defender;
		this.map = Data.load_predefined_map(preset);
		game_speed = speed;
	}
	
	public void run() {
		//present the board.
		SAD.io.Out.print_servers(this.map);
		
		//let's rumbleeeeee
		game_loop();
		
		//TODO implement a result screen for the attacker and the defender
		
		Integer infected_servers = this.map.get_infected_servers().size();
		Integer uninfected_servers = this.map.get_uninfected_servers().size();
		Integer infected_net = this.map.get_max_infected_server_graph_size();
		Integer uninfected_net = this.map.get_max_uninfected_server_graph_size();
		
		// end of the game
		System.out.println("=========== END OF THE GAME ============\n\n");
		if (uninfected_servers == 0)
			System.out.println("\tThe attacker just destroyed the game!");
		else if (infected_servers < 1)
			System.out.println("\tWait, what? You're not supposed to convert infected servers?!");
		else if (infected_servers == 1)
			System.out.println("\tOuch, what a burn Attacker, I can even feel the heat!");
		else if (infected_servers == 3)
			System.out.println("\t2+2 is 4, minus 1 that's 3, quick infection!");
		else if (infected_net == uninfected_net)
			System.out.println("\tThis is where I DRAW the line ;D");
		else if (uninfected_net > infected_net)
			System.out.println("\tNot too bad Defender! You managed to avoid taking too much damage!");
		else
			System.out.println("\tWow Defender! You got rekted!");
	}
	
	private void game_loop() {
		while (!this.has_ended) {
			next_turn();
			if (!this.is_simulated) {
				SAD.io.Out.print_servers(this.map);
				sleep();
			}
			
		}
		
	}
	
	private void next_turn() {
		Move move;
		if (this.is_attacker_turn)
			move = this.attacker.attacc(this);
		else
			move = this.defender.protecc(this);
		if (move.is_impossible())
			// The game has ended!
			this.has_ended = true;
		
		
		else {
			if (move instanceof Attacc) {
				
				final Integer target = ((Attacc) move).get_target();
				
				if (!this.is_simulated) {
					GUI.infect_node(target);
					System.out.println("INFECTION OF COMPUTER " + target);
				}
				
				map.infect_server(target);
			}
			else {
				final Integer server = ((Protecc) move).get_server();
				final TreeSet<Integer> neighbours = ((Protecc) move).get_links();
				
				if (!this.is_simulated) {
					GUI.cut_links(server, neighbours);
					System.out.println("ISOLATING SERVER " + server + " FROM SERVERS " + neighbours);
				}
				
				map.cut_links(server, neighbours);
			}
		}
		is_attacker_turn = !is_attacker_turn;
	}
	
	protected void sleep() {
		try {
			Thread.sleep(game_speed.step_duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return;
	}
}

