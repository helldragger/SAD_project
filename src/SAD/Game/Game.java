package SAD.Game;

import SAD.AI.AI;
import SAD.GUI.GUI;
import SAD.Move.Attacc;
import SAD.Move.Move;
import SAD.Move.Protecc;

import java.util.Set;

public class Game {
	//UI STUFF
	public static boolean verbose = false;
	public static GameSpeed game_speed = GameSpeed.NORMAL;
	public Boolean is_simulated = false;
	
	//DATA-GAME STUFF
	public Data map;
	public Boolean is_attacker_turn = true;
	public Boolean has_ended = false;
	
	//STAT LOGGING STUFF
	public double explored_nodes_defender = 0.;
	public double explored_nodes_attacker = 0.;
	
	public Game(final int server_qty, final int probability, final int infected) {
		this.map = Data.load_random_map(server_qty, probability, infected);
	}
	
	public Game(final int preset) {
		this.map = Data.load_predefined_map(preset);
	}
	
	public void start_simulation() {
		is_simulated = true;
	}
	
	public void stop_simulation() {
		is_simulated = false;
	}
	
	
	public void run() {
		//present the board.
		if (verbose)
			SAD.io.Out.print_servers(this.map);
		else {
			GUI.load_graph(this);
			
		}
	
		
		//let's rumbleeeeee
		game_loop();
		
		if (verbose) {
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
	}
	
	private void game_loop() {
		while (!this.has_ended) {
			
			has_ended = (Move.get_all_attacc(this).isEmpty()
					| Move.get_all_protecc(this).isEmpty());
			next_turn();
			if (!this.is_simulated) {
				if (verbose)
					SAD.io.Out.print_servers(this.map);
				sleep();
			}
			
		}
		
	}
	
	private void next_turn() {
		if (has_ended)
			return;
		Move move;
		if (this.is_attacker_turn)
			move = AI.attacc(this);
		else
			move = AI.protecc(this);
		play_move(move);
		
	}

	public void play_move(Move move){
		is_attacker_turn = !is_attacker_turn;
		
		
		if ((move instanceof Protecc)) {
			play_move((Protecc) move);
		} else {
			play_move((Attacc) move);
		}
		
	}

	private void play_move(Protecc move){
		if (Move.get_all_protecc(this).isEmpty()) {
			if (!this.is_simulated & verbose)
				System.out.println("DEFENDER CANNOT PLAY");
			return;
		}
		else if (move.is_empty()) {
			if (!this.is_simulated & verbose)
				System.out.println("DEFENDER CHOOSES TO DO NOTHING");
			return;
		}
		
		final Integer server = move.get_server();
		final Set<Integer> neighbours = move.get_links();
		if (!this.is_simulated) {
			if (!verbose)
				GUI.cut_links(server, neighbours);
			else
				System.out.println("DEFENDER ISOLATE SERVER " + server + " FROM SERVERS " + neighbours);
		}
		map.cut_links(server, neighbours);
	}

	private void play_move(Attacc move){
		
		if (Move.get_all_attacc(this).isEmpty()) {
			if (!this.is_simulated & verbose)
				System.out.println("ATTACKER CANNOT PLAY");
			return;
		}
		else if (move.is_empty()) {
			if (!this.is_simulated & verbose)
				System.out.println("ATTACKER CHOOSES TO DO NOTHING");
			return;
		}
		
		final Integer target =  move.get_target();
		if (!this.is_simulated) {
			if (!verbose)
				GUI.infect_node(target);
			else
				System.out.println("INFECTION OF COMPUTER " + target);
		}
		map.infect_server(target);
	}

	public void revert_move(Move move) {
		is_attacker_turn = !is_attacker_turn;
		
		if (move.is_empty()) {
			// The game has ended!
			this.has_ended = false;
			return;
		} else if ((move instanceof Protecc)) {
			revert_move((Protecc) move);
		} else {
			revert_move((Attacc) move);
		}
	}

	private void revert_move(Protecc move) {
		final Integer server = move.get_server();
		final Set<Integer> neighbours = move.get_links();
		if (!this.is_simulated) {
			if (!verbose)
				GUI.cut_links(server, neighbours);
			else
				System.out.println("ISOLATING SERVER " + server + " FROM SERVERS " + neighbours);
		}
		map.create_links(server, neighbours);
	}

	private void revert_move(Attacc move) {
		final Integer target = move.get_target();
		if (!this.is_simulated) {
			if (!verbose)
				GUI.infect_node(target);
			else
				System.out.println("INFECTION OF COMPUTER " + target);
		}
		map.desinfect_server(target);
	}


	protected void sleep() {
		try {
			Thread.sleep(game_speed.step_duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}

