package SAD.Game;

import SAD.GUI.GUI;
import SAD.Move.Attacc;
import SAD.Move.Move;
import SAD.Move.Protecc;
import SAD.Player.Player;

import java.util.Set;

public class Game {
	public static boolean verbose = false;
	public static GameSpeed game_speed = GameSpeed.NORMAL;
	private final Player attacker;
	private final Player defender;
	public Data map;
	public Boolean is_attacker_turn = true;
	public Boolean has_ended = false;
	public Boolean is_simulated = false;
	
	public Game(final Player attacker, final Player defender, final int server_qty, final int probability, final int infected) {
		this.attacker = attacker;
		this.defender = defender;
		this.map = Data.load_random_map(server_qty, probability, infected);
	}
	
	public Game(final Player attacker, final Player defender, final int preset) {
		this.attacker = attacker;
		this.defender = defender;
		this.map = Data.load_predefined_map(preset);
	}

	public Game(final Game g) {
		this.attacker = g.attacker;
		this.defender = g.defender;
		this.map = new Data(g.map);
		this.is_attacker_turn = g.is_attacker_turn;
		this.has_ended = g.has_ended;
		this.is_simulated = g.is_simulated;
	}

	public void start_simulation() {
		is_simulated = true;
	}
	
	public void stop_simulation() {
		is_simulated = false;
	}
	
	public void run() throws InterruptedException {
		//present the board.
		if (verbose)
			SAD.io.Out.print_servers(this.map);
		else {
			GUI.load_graph(this);
			Thread.sleep(1500);
		}
		
		//let's rumbleeeeee
		game_loop();
		
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
			move = this.attacker.attacc(this);
		else
			move = this.defender.protecc(this);
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
			if (!this.is_simulated)
				System.out.println("DEFENDER CANNOT PLAY");
			return;
		}
		else if (move.is_empty()) {
			if (!this.is_simulated)
				System.out.println("DEFENDER CHOOSES TO DO NOTHING");
			return;
		}
		
		final Integer server = move.get_server();
		final Set<Integer> neighbours = move.get_links();
		if (!this.is_simulated) {
			if (!verbose)
				GUI.cut_links(server, neighbours);
			System.out.println("DEFENDER ISOLATE SERVER " + server + " FROM SERVERS " + neighbours);
		}
		map.cut_links(server, neighbours);
	}

	private void play_move(Attacc move){
		
		if (Move.get_all_attacc(this).isEmpty()) {
			if (!this.is_simulated)
				System.out.println("ATTACKER CANNOT PLAY");
			return;
		}
		else if (move.is_empty()) {
			if (!this.is_simulated)
				System.out.println("ATTACKER CHOOSES TO DO NOTHING");
			return;
		}
		
		final Integer target =  move.get_target();
		if (!this.is_simulated) {
			if (!verbose)
				GUI.infect_node(target);
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
			System.out.println("ISOLATING SERVER " + server + " FROM SERVERS " + neighbours);
		}
		map.create_links(server, neighbours);
	}

	private void revert_move(Attacc move) {
		final Integer target = move.get_target();
		if (!this.is_simulated) {
			if (!verbose)
				GUI.infect_node(target);
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

