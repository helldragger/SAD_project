package SAD.io;

import SAD.Game.Data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

public abstract class Out {
	
	//TODO Stats logging?
	
	public static void log_stats(String filename, List<String> data) {
		
		try (PrintWriter log = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)))) {
			for (String stat : data) {
				System.out.print(stat + "\t");
				log.print(stat + '\t');
			}
			
			System.out.println();
			log.println();
		} catch (IOException e) {
		
		} finally {
		}
		
	}
	
	
	public static void print_servers(final Data map) {
		for (final Integer server : map.get_all_servers()) {
			final Set<Integer> neighbourgs = map.get_neighbours(server);
			final String state = (map.get_infected_servers().contains(server)) ? "INFECTED\t\t" : "UNINFECTED\t\t";
			System.out.println(state + server + "\t=>\t" + neighbourgs);
		}
	}
}
