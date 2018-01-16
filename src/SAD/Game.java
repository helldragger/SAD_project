package SAD;

import SAD.Controls.Move.Attacc;
import SAD.Controls.Move.Move;
import SAD.Controls.Move.Protecc;
import SAD.Player.Player;

public class Game {
	private final Player attacker;
	private final Player defender;
	private Data map;
	private Boolean is_attacker_turn = true;
	private Boolean has_ended = false;
	
	public Game(final Player attacker, final Player defender, final Data map) {
		this.attacker = attacker;
		this.defender = defender;
		this.map = map;
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
		else if (infected_servers <= 1)
			System.out.println("\tWait, what? You're not supposed to convert infected servers?!");
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
			SAD.io.Out.print_servers(this.map);
			sleep();
		}
		
	}
	
	private void next_turn() {
		Move move;
		if (this.is_attacker_turn)
			move = this.attacker.attacc(this.map);
		else
			move = this.defender.protecc(this.map);
		if (move.is_impossible())
			// The game has ended!
			this.has_ended = true;
		else {
			if (move instanceof Attacc) {
				map.infect_server(((Attacc) move).get_target());
				System.out.println("INFECTION OF COMPUTER " + ((Attacc) move).get_target());
			}
			else {
				map.cut_links(((Protecc) move).get_server(),
						((Protecc) move).get_links());
				System.out.println("ISOLATING SERVER " + ((Protecc) move).get_server() + " FROM SERVERS " + ((Protecc) move).get_links());
			}
		}
		is_attacker_turn = !is_attacker_turn;
	}
	
	protected void sleep() {
		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return;
	}
}
