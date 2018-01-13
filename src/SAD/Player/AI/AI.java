package SAD.Player.AI;


import SAD.Controls.Move.Attacc;
import SAD.Controls.Move.Protecc;
import SAD.Data;
import SAD.Player.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class AI extends Player {
	public Protecc protecc(Data game){
		
		//TODO Add a real AI to determine what to protect or not
		// for the time being, we will just cut links from a random infected server neighbour
		
		HashSet<Integer> potential_risk = game.get_infected_neighbours( game.get_uninfected_servers());
		// no more infected neighbours connected to the servers?
		if (potential_risk.isEmpty())
			return new Protecc();
		// risk to mitigate, let's disconnect something randomly!
		else
		{
			
			Integer target_i = new Random().nextInt(potential_risk.size());
			Integer server = (Integer) potential_risk.toArray()[target_i];
			HashSet<Integer> risk_links = game.get_infected_neighbours(server);
			return new Protecc(server, risk_links);
		}
		
	}
	
	public Attacc attacc(Data game){
		//TODO Add a real attack decision
		// For the time being we just attack a random neighbor until we can't
		
		HashSet<Integer> potential_target = game.get_uninfected_neighbours( game.get_infected_servers());
		// no targets to infect anymore
		if (potential_target.isEmpty())
			return new Attacc();
		//targets to infect,, we choose one randomly
		else
		{
			Integer target_i = new Random().nextInt(potential_target.size());
			return new Attacc((Integer) potential_target.toArray()[target_i]);

		}
		
	}
	
}