package SAD;

import SAD.AI.AI;
import SAD.Game.Game;
import SAD.Game.GameSpeed;
import SAD.io.Out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats {
	
	
	//repetitions for accuracy as we won't have exactly the same map every time
	// 95% confidence, 100 sample size => 10% margin of error
	static int sample_size = 100;
	
	public static void analyze_pruning(int server_qty, int link_prob, int infected) {
		String filename_header = server_qty + "servers_" + infected + "infected_";
		String filename_min_depth = filename_header + "minmax_depth_pruning_analysis.dat";
		String filename_ab_depth = filename_header + "alphabeta_depth_pruning_analysis.dat";
		String filename_min_link = filename_header + "minmax_link_prob_pruning_analysis.dat";
		String filename_ab_link = filename_header + "alphabeta_link_prob_pruning_analysis.dat";
		
		
		System.out.println("PRUNING ANALYSIS, MINMAX  X  ALPHABETA PRUNING \n");
		System.out.println("FOR BETTER ANALYSIS, EACH EXPERIMENT WILL BE REPEATED 100 TIMES WITH A CONFIDENCE LEVEL OF 95% TO REACH AS LOW AS 10% ERROR MARGINS\n");
		
		
		System.out.println("COMMON INITIAL CONDITIONS:");
		System.out.println("SERVER QUANTITY: " + server_qty);
		System.out.println("INITIAL INFECTIONS: " + infected);
		
		//first analysis : let's compare depth influence with a link prob of 5%!
		System.out.println("=== FIRST TEST : DEPTH INFLUENCE (1->5) ===");
		System.out.println("LINK PROBABILITY: " + link_prob);
		System.out.println("--STATISTICS FORMAT--");
		
		
		List<String> data_format = new ArrayList<>();
		
		data_format.add("depth");
		data_format.add("attacker");
		data_format.add("attacker_err");
		data_format.add("defender");
		data_format.add("defender_err");
		data_format.add("total");
		data_format.add("total_err");
		
		Out.log_stats(filename_min_depth, data_format);
		data_format.clear();
		//no alphabeta first
		for (int depth = 1; depth <= 5; depth++) {
			Map<String, Double> data = repeat_depth_experiment(false, depth, server_qty, link_prob, infected);
			
			data_format.add(String.valueOf(depth));
			data_format.add(String.valueOf(data.get("attacker")));
			data_format.add(String.valueOf(data.get("attacker") * 0.1));
			data_format.add(String.valueOf(data.get("defender")));
			data_format.add(String.valueOf(data.get("defender") * 0.1));
			data_format.add(String.valueOf(data.get("total")));
			data_format.add(String.valueOf(data.get("total") * 0.1));
			
			Out.log_stats(filename_min_depth, data_format);
			data_format.clear();
		}
		System.out.println("END OF ANALYSIS, RESULTS LOGGED INTO " + filename_min_depth);
		
		data_format.add("depth");
		data_format.add("attacker");
		data_format.add("attacker_err");
		data_format.add("defender");
		data_format.add("defender_err");
		data_format.add("total");
		data_format.add("total_err");
		
		Out.log_stats(filename_ab_depth, data_format);
		data_format.clear();
		//now alphabeta
		for (int depth = 1; depth <= 5; depth++) {
			Map<String, Double> data = repeat_depth_experiment(true, depth, server_qty, link_prob, infected);
			
			data_format.add(String.valueOf(depth));
			data_format.add(String.valueOf(data.get("attacker")));
			data_format.add(String.valueOf(data.get("attacker") * 0.1));
			data_format.add(String.valueOf(data.get("defender")));
			data_format.add(String.valueOf(data.get("defender") * 0.1));
			data_format.add(String.valueOf(data.get("total")));
			data_format.add(String.valueOf(data.get("total") * 0.1));
			
			Out.log_stats(filename_ab_depth, data_format);
			data_format.clear();
		}
		System.out.println("END OF ANALYSIS, RESULTS LOGGED INTO " + filename_ab_depth);
		
		// second analysis: let's compare link probability at a reasonable depth of 3!
		int depth = 3;
		
		System.out.println("=== SECOND TEST : LINK PROBABILITY INFLUENCE (5->75) ===");
		System.out.println("DEPTH: " + depth);
		System.out.println("--STATISTICS FORMAT--");
		
		data_format.add("link_prob");
		data_format.add("attacker");
		data_format.add("attacker_err");
		data_format.add("defender");
		data_format.add("defender_err");
		data_format.add("total");
		data_format.add("total_err");
		
		Out.log_stats(filename_min_link, data_format);
		data_format.clear();
		
		
		for (int prob = 5; prob <= 95; prob = prob + 10) {
			Map<String, Double> data = repeat_depth_experiment(false, depth, server_qty, link_prob, infected);
			
			data_format.add(String.valueOf(prob));
			data_format.add(String.valueOf(data.get("attacker")));
			data_format.add(String.valueOf(data.get("attacker") * 0.1));
			data_format.add(String.valueOf(data.get("defender")));
			data_format.add(String.valueOf(data.get("defender") * 0.1));
			data_format.add(String.valueOf(data.get("total")));
			data_format.add(String.valueOf(data.get("total") * 0.1));
			
			Out.log_stats(filename_min_link, data_format);
			data_format.clear();
		}
		
		System.out.println("END OF ANALYSIS, RESULTS LOGGED INTO " + filename_min_link);
		data_format.add("link_prob");
		data_format.add("attacker");
		data_format.add("attacker_err");
		data_format.add("defender");
		data_format.add("defender_err");
		data_format.add("total");
		data_format.add("total_err");
		
		Out.log_stats(filename_ab_link, data_format);
		data_format.clear();
		
		for (int prob = 5; prob <= 95; prob = prob + 10) {
			Map<String, Double> data = repeat_depth_experiment(true, depth, server_qty, link_prob, infected);
			
			data_format.add(String.valueOf(prob));
			data_format.add(String.valueOf(data.get("attacker")));
			data_format.add(String.valueOf(data.get("attacker") * 0.1));
			data_format.add(String.valueOf(data.get("defender")));
			data_format.add(String.valueOf(data.get("defender") * 0.1));
			data_format.add(String.valueOf(data.get("total")));
			data_format.add(String.valueOf(data.get("total") * 0.1));
			
			Out.log_stats(filename_ab_link, data_format);
			data_format.clear();
		}
		
		System.out.println("END OF ANALYSIS, RESULTS LOGGED INTO " + filename_ab_link);
		
	}
	
	private static Map<String, Double> repeat_depth_experiment(boolean use_alphabeta, int depth, int server_qty, int link_prob, int infected) {
		Map<String, Double> data = new HashMap<>();
		
		AI.set_alphabeta(use_alphabeta);
		AI.attacker_depth = depth;
		AI.defender_depth = depth;
		Game.verbose = false;
		Game.game_speed = GameSpeed.INSTANT;
		
		for (int c = 0; c < sample_size; c++) {
			
			Game g = new Game(server_qty, link_prob, infected);
			g.run();
			if (c % 10 == 0)
				System.out.print('|');
			else if (c % 5 == 0)
				System.out.print(',');
			else
				System.out.print('.');
			data.put("attacker", data.getOrDefault("attacker", 0.) + g.explored_nodes_attacker / sample_size);
			data.put("defender", data.getOrDefault("defender", 0.) + g.explored_nodes_defender / sample_size);
			data.put("total", data.getOrDefault("total", 0.) + (g.explored_nodes_defender + g.explored_nodes_attacker) / sample_size);
		}
		System.out.print(" SAMPLE COMPLETE - result preview:\n");
		
		return data;
	}
	
	
}
