package SAD.Game;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;


public class Data {
	// Game of links between servers (server to which server)
	private HashMap<Integer, HashSet<Integer>> links;
	// List of servers per state (infected or not)
	private HashMap<Boolean, HashSet<Integer>> infected_servers;
	
	
	public Data() {
		this.links = new HashMap<>();
		this.infected_servers = new HashMap<Boolean, HashSet<Integer>>();
		this.infected_servers.put(true, new HashSet<>());
		this.infected_servers.put(false, new HashSet<>());
	}

    public Data(Data d) {
        this.links = new HashMap<>();
		this.infected_servers = new HashMap<>();
        this.links.putAll(d.links);
        this.infected_servers.putAll(d.infected_servers);
    }


	static Data load_predefined_map(final Integer index) {
		Data map_data = new Data();
		Random rand = new Random();
		
		int server_quantity;
		//TODO load maps
		// TODO determine the number of maps
		// TODO load from a file or something easily modifiable
		//TODO read serialized data.
		
		
		switch (index) {
			case 1:
				// while waiting for it, there is a little TESTING map with all type of situations included
				// ie.: 1 to 2 link, 1 to 3, 1 to 4, 2 to 2 etc...
			
		    	
		    	/*
		    	                0   1   2 - 3
		    	              /   /   /
		    	4 - 5 - 6 - 7 - 8 - 9  - 10
								   /
		    	            11 - 12 - 13
		    	                /
		    	              14
		    	
		    	 */
				server_quantity = 15;
				
				HashSet<Integer> servers = new HashSet<>();
				for (int j = 0; j < 15; j++) {
					servers.add(j);
				}
				
				map_data.infected_servers.put(false, servers);
				map_data.links.put(0, new HashSet<>(singletonList(7)));
				map_data.links.put(1, new HashSet<>(singletonList(8)));
				map_data.links.put(2, new HashSet<>(asList(3, 9)));
				map_data.links.put(3, new HashSet<>(singletonList(2)));
				map_data.links.put(4, new HashSet<>(singletonList(5)));
				map_data.links.put(5, new HashSet<>(asList(4, 6)));
				map_data.links.put(6, new HashSet<>(asList(5, 7)));
				map_data.links.put(7, new HashSet<>(asList(0, 6, 8)));
				map_data.links.put(8, new HashSet<>(asList(1, 7, 9)));
				map_data.links.put(9, new HashSet<>(asList(2, 8, 10, 12)));
				map_data.links.put(10, new HashSet<>(singletonList(9)));
				map_data.links.put(11, new HashSet<>(singletonList(12)));
				map_data.links.put(12, new HashSet<>(asList(9, 11, 13, 14)));
				map_data.links.put(13, new HashSet<>(singletonList(12)));
				map_data.links.put(14, new HashSet<>(singletonList(12)));
				break;
			default:
				throw new NoSuchElementException();
		}
		
		map_data.infect_server(rand.nextInt(server_quantity));
		
		
		return map_data;
	}
	
	public void infect_server(final Integer server) {
		this.infected_servers.get(false).remove(server);
		this.infected_servers.get(true).add(server);
	}


    public void desinfect_server(final Integer server) {
        this.infected_servers.get(true).remove(server);
        this.infected_servers.get(false).add(server);
    }
	
	static Data load_random_map(final int server_quantity, final int link_probability, final int infected_quantity) {
		
		Random rand = new Random();
		Data map_data = new Data();
		
		//server spawning
		
		HashSet<Integer> servers = new HashSet<>();
		for (int s = 0; s < server_quantity; s++) {
			servers.add(s);
			map_data.links.put(s, new HashSet<>());
		}
		
		map_data.infected_servers.put(false, servers);
		
		//linking servers
		
		for (int server_i = 0; server_i < server_quantity; server_i++)
			for (int server_j = server_i + 1; server_j < server_quantity; server_j++)
				if (rand.nextInt() <= link_probability)
					map_data.create_link(server_i, server_j);
				
		
		//patient zero
		for (int _ = 0; _ < infected_quantity; _++) {
			map_data.infect_server(rand.nextInt(map_data.get_uninfected_servers().size()));
		}
		
		//ready!
		return map_data;
	}
	
	public Set<Integer> get_neighbours(final Integer server) {
        return this.links.get(server);
    }
	
	public Integer get_max_infected_server_graph_size() {
        return subgraph_size(this.infected_servers.get(true));
    }
	
	public Integer get_max_uninfected_server_graph_size() {
        return subgraph_size(this.infected_servers.get(false));
    }
	
	public Integer subgraph_size(final Set<Integer> accepted_servers) {
		if (accepted_servers.size() <= 1)
			return accepted_servers.size();
		
		int max_size = 0;
		Set<Integer> visited = new HashSet<>();
		for (Integer starting_server : accepted_servers) {
			
			if (!visited.contains(starting_server)) {
				int count = 0;
				
				Set<Integer> next_servers = new HashSet<>();
				
				next_servers.add(starting_server);
				visited.add(starting_server);
				
				while (!next_servers.isEmpty()) {
					Set<Integer> temp_nxt = new HashSet<>();
					for (Integer server : next_servers) {
						count++;
						
						for (Integer next_server : this.links.get(server)) {
							if (accepted_servers.contains(next_server) & !visited.contains(next_server)) {
								temp_nxt.add(next_server);
								visited.add(server);
							}
						}
						
					}
					next_servers = temp_nxt;
				}
				if (count >= max_size)
					max_size = count;
			}
		}
		return max_size;
		
	}
	
	private void create_link(final Integer s1,final Integer s2) {
		this.links.get(s1).add(s2);
		this.links.get(s2).add(s1);
	}

    public void create_links(final Integer s, Set<Integer> servers) {
        for (Integer s2 : servers)
            create_link(s, s2);
    }

	
	public Set<Integer> get_uninfected_neighbours(final Integer server) {
		return get_uninfected_neighbours(new HashSet<Integer>() {{
			add(server);
		}});
	}
	
	public Set<Integer> get_uninfected_neighbours(final Set<Integer> server) {
		Set<Integer> result = get_neighbours(server);
        result.removeAll(this.infected_servers.get(true));
        return result;
	}
	
	public Set<Integer> get_infected_neighbours(final Set<Integer> server) {
		Set<Integer> result = get_neighbours(server);
        result.removeAll(this.infected_servers.get(false));
        return result;
	}
	
	public Set<Integer> get_infected_neighbours(final Integer server) {
		return get_infected_neighbours(new HashSet<Integer>() {{
			add(server);
		}});
	}
	
	public void cut_links(final Integer server, final Set<Integer> linked_servers) {
		//cut from the other side
		for (Integer s : linked_servers) {
			this.links.get(s).remove(server);
		}
		
		// cut from the server side (in this order because remove All empties linked_servers
		this.links.get(server).removeAll(linked_servers);
		
		
	}
	
	public Map<Integer, HashSet<Integer>> get_all_links() {
		return this.links;
	}
	
	public Set<Integer> get_all_servers() {
		Set<Integer> result = new HashSet<>();
		result.addAll(this.infected_servers.get(true));
		result.addAll(this.infected_servers.get(false));
		return result;
	}
	
	public Integer get_servers_count() {
		return this.links.keySet().size();
	}
	
	public Set<Integer> get_infected_servers() {
        return this.infected_servers.get(true);
    }
	
	public Set<Integer> get_uninfected_servers() {
        return this.infected_servers.get(false);
    }
	
	public Set<Integer> get_neighbours(final Set<Integer> servers) {
		Set<Integer> neighbours = new HashSet<>();
		for (Integer server : servers) {
			neighbours.addAll(get_neighbours(server));
		}
		return neighbours;
	}

}