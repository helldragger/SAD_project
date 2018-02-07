package SAD.Controls.Move;

import SAD.Game.Game;

import java.util.HashSet;
import java.util.Set;

public abstract class Move {
	abstract public boolean is_impossible();

	public static Set<Move> get_all_protecc(Game g){
		Set<Move> all_move = new HashSet<>();

		for(Integer s : g.map.get_all_servers()){
			Set<Set<Integer>> combinaison = powerset(g.map.get_neighbours(s));
			for(Set<Integer> links : combinaison){
				all_move.add(new Protecc(s,links));
			}
		}
		return all_move;
	}

	//Rosetta Code
	public static Set<Set<Integer>> powerset(Set<Integer> list_links) {
		Set<Set<Integer>> combinaison = new HashSet<Set<Integer>>();
		combinaison.add(new HashSet<Integer>());   // add the empty set

		// for every item in the original list
		for (Integer link : list_links) {
			Set<Set<Integer>> new_comb = new HashSet<Set<Integer>>();

			for (Set<Integer> subset : combinaison) {
				// copy all of the current powerset's subsets
				new_comb.add(subset);

				// plus the subsets appended with the current item
				Set<Integer> newSubset = new HashSet<Integer>(subset);
				newSubset.add(link);
				new_comb.add(newSubset);
			}

			// powerset is now powerset of list.subList(0, list.indexOf(item)+1)
			combinaison = new_comb;
		}
		return combinaison;
	}

	public static Set<Move> get_all_attacc(Game game){
		Set<Move> moves = new HashSet<>();
		for(Integer t : game.map.get_uninfected_neighbours(game.map.get_infected_servers()))
			moves.add(new Attacc(t));
		return moves;
	}
}
