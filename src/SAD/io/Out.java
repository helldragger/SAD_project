package SAD.io;

import SAD.Data;

import java.util.TreeSet;

public abstract class Out {
	
	//TODO Stats logging?
	
	
	public static void print_servers(final Data map) {
		for (final Integer server : map.get_all_servers()) {
			final TreeSet<Integer> neighbourgs = map.get_neighbours(server);
			final String state = (map.get_infected_servers().contains(server)) ? "INFECTED\t\t" : "UNINFECTED\t\t";
			System.out.println(state + server + "\t=>\t" + neighbourgs);
		}
	}
}
