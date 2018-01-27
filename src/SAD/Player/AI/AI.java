package SAD.Player.AI;


import SAD.Controls.Move.Attacc;
import SAD.Controls.Move.Protecc;
import SAD.Game.Game;
import SAD.Player.Player;

import java.util.Random;
import java.util.TreeSet;

public class AI extends Player {
	public Attacc attacc(Game game) {
		//TODO Add a real attack decision
		// For the time being we just attack a random neighbor until we can't
		
		TreeSet<Integer> potential_target = game.map.get_uninfected_neighbours(game.map.get_infected_servers());
		// no targets to infect anymore
		if (potential_target.isEmpty())
			return new Attacc();
			//targets to infect,, we choose one randomly
		else {
			Integer target_i = new Random().nextInt(potential_target.size());
			return new Attacc((Integer) potential_target.toArray()[target_i]);
			
		}
		
	}
	
	public Protecc protecc(Game game) {
		
		//TODO Add a real AI to determine what to protect or not
		// for the time being, we will just cut links from a random infected server neighbour
		
		TreeSet<Integer> potential_risk = game.map.get_uninfected_neighbours(game.map.get_infected_servers());
		// no more infected neighbours connected to the servers?
		if (potential_risk.isEmpty())
			return new Protecc();
			// risk to mitigate, let's disconnect something randomly!
		else {
			
			Integer target_i = new Random().nextInt(potential_risk.size());
			Integer server = (Integer) potential_risk.toArray()[target_i];
			TreeSet<Integer> risks_to_cut = game.map.get_infected_neighbours(server);
			
			return new Protecc(server, risks_to_cut);
		}
		
	}
	
}
