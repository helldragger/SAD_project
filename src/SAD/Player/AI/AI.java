package SAD.Player.AI;


import SAD.Controls.Move.Attacc;
import SAD.Controls.Move.Move;
import SAD.Controls.Move.Protecc;
import SAD.Game.Game;
import SAD.Player.Player;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import static SAD.Controls.Move.Move.get_all_protecc;

public class AI extends Player {

	private int depth = 3;

	private static Integer getValue(final Game s){
		if(s.is_attacker_turn){ //if Player is Attacc
			return s.map.get_max_infected_server_graph_size();
		}else{
			return s.map.get_max_uninfected_server_graph_size();
		}
	}

	public static MinmaxResult minmax(final Game s, final Integer d){
		if(d ==0 || s.has_ended){
			return new MinmaxResult(getValue(s), null);
		}
		MinmaxResult m = new MinmaxResult(0, null);

		Set<Move> moves;
		if (s.is_attacker_turn)
			moves = Move.get_all_attacc(s);
		else
			moves = Move.get_all_protecc(s);

		for(Move move : moves)
		{
			Game temp = s.clone();
			temp.play_move(move);
			MinmaxResult result = minmax(temp,d-1);
			if(m.max < result.max){
				m.max = result.max;
				m.move = move;
			}
		}

		return m;
	}

	public Attacc attacc(final Game game) {
		game.start_simulation();
		Attacc move = (Attacc) minmax(game.clone(), depth).move;
		game.stop_simulation();
		return (move != null)? move : new Attacc();
		/*
		//TODO Add a real attack decision

		// For the time being we just attack a random neighbor until we can't
		
		Set<Integer> potential_target = game.map.get_uninfected_neighbours(game.map.get_infected_servers());
		// no targets to infect anymore
		if (potential_target.isEmpty())
			return new Attacc();
			//targets to infect,, we choose one randomly
		else {
			Integer target_i = new Random().nextInt(potential_target.size());
			return new Attacc((Integer) potential_target.toArray()[target_i]);
			
		}
		*/
		
	}
	
	public Protecc protecc(final Game game) {
		game.start_simulation();
		Protecc move = (Protecc) minmax(game.clone(), depth).move;
		game.stop_simulation();
		return (move != null)? move: new Protecc();

		/*
		//TODO Add a real AI to determine what to protect or not
		// for the time being, we will just cut links from a random infected server neighbour
		
		Set<Integer> potential_risk = game.map.get_uninfected_neighbours(game.map.get_infected_servers());
		// no more infected neighbours connected to the servers?
		if (potential_risk.isEmpty())
			return new Protecc();
			// risk to mitigate, let's disconnect something randomly!
		else {
			
			Integer target_i = new Random().nextInt(potential_risk.size());
			Integer server = (Integer) potential_risk.toArray()[target_i];
			Set<Integer> risks_to_cut = game.map.get_infected_neighbours(server);
			
			return new Protecc(server, risks_to_cut);
		}*/
		
	}


	static class MinmaxResult{
		public Integer max;
		public Move move;

		public MinmaxResult(Integer m, Move e){
			max = m;
			move = e;
		}
	}
}
