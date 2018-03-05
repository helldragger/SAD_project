package SAD.AI;


import SAD.Game.Game;
import SAD.Move.Attacc;
import SAD.Move.Move;
import SAD.Move.Protecc;

import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AI {
	
	public static int attacker_depth = 5;
	public static int defender_depth = 5;
	static boolean use_alphabeta = false;
	
	private static int getValue(final Game s) {
		int sum = 0;
		for (Integer server : s.map.get_uninfected_servers()) {
			sum += s.map.get_neighbours(server).size();
		}
		return sum;
	}
	
	public static MinmaxResult alphabeta(final Game s, final Integer d, int alpha, int beta) {
		if (d == 0 || s.has_ended) {
			return new MinmaxResult(getValue(s), null);
		}
		
		
		MinmaxResult best;
		Set<Move> moves;
		
		if (s.is_attacker_turn) { // min
			
			moves = Move.get_all_attacc(s);
			best = new MinmaxResult(999999, new Attacc());
			for (Move move : moves) {
				
				s.explored_nodes_attacker++;
				
				s.play_move(move);
				MinmaxResult result = alphabeta(s, d - 1, alpha, beta);
				if (result.score < best.score | best.score == 999999) {
					best.score = result.score;
					best.move = move;
				}
				s.revert_move(move);
				if (alpha >= best.score) {
					return best;
				}
				beta = min(beta, best.score);
			}
			
		}
		else { // max
			moves = Move.get_all_protecc(s);
			best = new MinmaxResult(-999999, new Protecc());
			for (Move move : moves) {
				s.explored_nodes_defender++;
				s.play_move(move);
				
				MinmaxResult result = alphabeta(s, d - 1, alpha, beta);
				
				if (result.score > best.score | best.score == -999999) {
					best.score = result.score;
					best.move = move;
				}
				s.revert_move(move);
				if (best.score >= beta) {
					return best;
				}
				
				alpha = max(alpha, best.score);
			}
		}
		return best;
	}
	
	public static MinmaxResult minmax(final Game s, final Integer d){
		if (d == 0 || s.has_ended) {
			return new MinmaxResult(getValue(s), null);
		}
		MinmaxResult best;
		
		Set<Move> moves;
		
		if (s.is_attacker_turn) { // min
			
			moves = Move.get_all_attacc(s);
			best = new MinmaxResult(999999, new Attacc());
			for (Move move : moves) {
				s.explored_nodes_attacker++;

				s.play_move(move);
				MinmaxResult result = minmax(s, d - 1);
				if (result.score < best.score | best.score == 999999) {
					best.score = result.score;
					best.move = move;
				}
				s.revert_move(move);
			}
			
		}
		else { // max
			moves = Move.get_all_protecc(s);
			best = new MinmaxResult(-999999, new Protecc());
			for (Move move : moves) {
				s.explored_nodes_defender++;

				s.play_move(move);
				MinmaxResult result = minmax(s, d - 1);
				
				if (result.score > best.score | best.score == -999999) {
					best.score = result.score;
					best.move = move;
				}
				s.revert_move(move);
				
			}
		}
		return best;
	}
	
	static public void set_defender_depth(int d) {
		defender_depth = d;
	}
	
	static public void set_attacker_depth(int d) {
		attacker_depth = d;
	}
	
	static public void set_alphabeta(boolean ab) {
		use_alphabeta = ab;
	}
	
	public static Attacc attacc(final Game game) {
		
		Attacc move;
		game.start_simulation();
		
		if (use_alphabeta)
			move = (Attacc) alphabeta(game, attacker_depth, 0, game.map.get_servers_count()).move;
		else
			move = (Attacc) minmax(game, attacker_depth).move;
		
		game.stop_simulation();
		return (move != null)? move : new Attacc();
		
	}
	
	public static Protecc protecc(final Game game) {
		
		Protecc move;
		game.start_simulation();
		if (use_alphabeta)
			move = (Protecc) alphabeta(game, defender_depth, 0, game.map.get_servers_count()).move;
		else
			move = (Protecc) minmax(game, defender_depth).move;
		game.stop_simulation();
		return (move != null)? move: new Protecc();

	}
	
	static class MinmaxResult{
		public Integer score;
		public Move move;

		public MinmaxResult(Integer m, Move e){
			score = m;
			move = e;
		}
	}
}
