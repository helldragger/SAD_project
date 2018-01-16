package SAD;

import SAD.GUI.GUI;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class Data {
    private static ArrayList<Data> maps = new ArrayList<>();
    // Map of links between servers (server to which server)
    private Map<Integer, TreeSet<Integer>> links;
    // List of servers per state (infected or not)
    private Map<Boolean, TreeSet<Integer>> infected_servers;

    public Data() {
		this.links = new HashMap<>();
		this.infected_servers = new HashMap<>();
	    this.infected_servers.put(true, new TreeSet<>());
	    this.infected_servers.put(false, new TreeSet<>());
    }
	
	
	public void print_server() {
        System.out.println(
                "Les serveurs infectés :\n" + infected_servers.get(true) + "\n" +
                        "Les serveurs non-infectés :\n" + infected_servers.get(false) + "\n"
        );

        
	    System.out.println("Maximum of infected interconnected nodes: "
			    + this.get_max_infected_server_graph_size());
	    
	    System.out.println("Maximum of uninfected interconnected nodes: "
			    + this.get_max_uninfected_server_graph_size());
        
        for (int i = 0; i < this.links.size(); i++) {
	        System.out.println(
			        "\nLe serveur n°" + i + " est relié aux serveurs " + links.get(i)
	        );
        }
        
    }
	
	static void load_maps() {
		//TODO load maps
	    // TODO determine the number of maps
	    int map_n = 1;
	    for (int i = 0; i < map_n; i++)
	    {
	    	Data map_data = new Data();
	    	// TODO load from a file or something easily modifiable
	    	if (i == 0){
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
			
			    TreeSet<Integer> servers = new TreeSet<>();
			    for (int j = 0; j < 15; j++)
			    {
			    	servers.add(j);
			    }
			    
		    	map_data.infected_servers.put(false, servers);
			    map_data.links.put(0, new TreeSet<>(singletonList(7)));
			    map_data.links.put(1, new TreeSet<>(singletonList(8)));
			    map_data.links.put(2, new TreeSet<>(asList(3, 9)));
			    map_data.links.put(3, new TreeSet<>(singletonList(2)));
			    map_data.links.put(4, new TreeSet<>(singletonList(5)));
			    map_data.links.put(5, new TreeSet<>(asList(4, 6)));
			    map_data.links.put(6, new TreeSet<>(asList(5, 7)));
			    map_data.links.put(7, new TreeSet<>(asList(0, 6, 8)));
			    map_data.links.put(8, new TreeSet<>(asList(1, 7, 9)));
			    map_data.links.put(9, new TreeSet<>(asList(2, 8, 10, 12)));
			    map_data.links.put(10, new TreeSet<>(singletonList(9)));
			    map_data.links.put(11, new TreeSet<>(singletonList(12)));
			    map_data.links.put(12, new TreeSet<>(asList(9, 11, 13, 14)));
			    map_data.links.put(13, new TreeSet<>(singletonList(12)));
			    map_data.links.put(14, new TreeSet<>(singletonList(12)));
			    // Let's store the map for further testing.
			    maps.add(map_data);
		    }
		    
		    //TODO read serialized data.
		    
	    }
    	
    }
	
	static Data generate_map() {
		Random rand = new Random();
        // Use already planned maps.
        // 1 map au pif parmi celles chargées
	    Data map = maps.get(rand.nextInt(maps.size()));
        // determine the infected server
	    int server_i = rand.nextInt(map.infected_servers.get(false).size());
	    
	    Integer server = (Integer) map.infected_servers.get(false).toArray()[server_i];
		GUI.load_graph(map);
		map.infect_server(server);
     
	    return map;
    }
	
	void infect_server(Integer server) {
		GUI.infect_node(server, this.get_neighbours(server));
		this.infected_servers.get(false).remove(server);
    	this.infected_servers.get(true).add(server);
    }
	
	public TreeSet<Integer> get_neighbours(Integer server) {
		return (TreeSet<Integer>) this.links.get(server).clone();
	}
	
	void cut_links(Integer server, TreeSet<Integer> linked_servers) {
		GUI.cut_links(server, linked_servers);
		//cut from the other side
	    for (Integer s : linked_servers){
	    	this.links.get(s).remove(server);
	    }
	    
    	// cut from the server side (in this order because remove All empties linked_servers
    	this.links.get(server).removeAll(linked_servers);
    	
    	
    }
	
	public TreeSet<Integer> get_uninfected_neighbours(Integer server) {
		return get_uninfected_neighbours(new TreeSet<Integer>() {{
			add(server);
		}});
	}
	
	public TreeSet<Integer> get_uninfected_neighbours(TreeSet<Integer> server) {
		TreeSet<Integer> result = get_neighbours(server);
		result.removeAll((Collection<?>) this.infected_servers.get(true).clone());
		return result;
	}
	
	TreeSet<Integer> get_neighbours(TreeSet<Integer> servers) {
		TreeSet<Integer> neighbours = new TreeSet<>();
		for (Integer server: servers)
	    {
	    	neighbours.addAll(get_neighbours(server));
	    }
    	return neighbours;
    }
	
	public TreeSet<Integer> get_infected_neighbours(Integer server) {
		return get_infected_neighbours(new TreeSet<Integer>() {{
			add(server);
		}});
	}
	
	public TreeSet<Integer> get_infected_neighbours(TreeSet<Integer> server) {
		TreeSet<Integer> result = get_neighbours(server);
		result.removeAll((Collection<?>) this.infected_servers.get(false).clone());
    	return result;
    }
	
	public Map<Integer, TreeSet<Integer>> get_all_links() {
		return this.links;
    }
	
	public TreeSet<Integer> get_all_servers() {
		TreeSet<Integer> result = new TreeSet<>();
		result.addAll(this.infected_servers.get(true));
		result.addAll(this.infected_servers.get(false));
		return result;
	}
	
	public Integer get_servers_count(){
		return this.links.keySet().size();
	}
	
	public Integer get_max_uninfected_server_graph_size(){
		return subgraph_size((TreeSet<Integer>) this.infected_servers.get(false).clone());
	}
	
	public Integer subgraph_size(final TreeSet<Integer> accepted_servers) {
		if (accepted_servers.size() <= 1)
			return accepted_servers.size();
		
		int max_size = 0;
		TreeSet<Integer> visited = new TreeSet<>();
		for (Integer starting_server : accepted_servers) {
			
			if (!visited.contains(starting_server)) {
				int count = 0;
				
				TreeSet<Integer> next_servers = new TreeSet<>();
				
				next_servers.add(starting_server);
				visited.add(starting_server);
				
				while (!next_servers.isEmpty()) {
					TreeSet<Integer> temp_nxt = new TreeSet<>();
					for (Integer server : next_servers) {
						count++;
						
						for (Integer next_server : this.links.get(server))
							if (accepted_servers.contains(next_server) & !visited.contains(next_server)) {
								temp_nxt.add(next_server);
								visited.add(server);
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
	
	public Integer get_max_infected_server_graph_size() {
		return subgraph_size((TreeSet<Integer>) this.infected_servers.get(true).clone());
	}
	
	public TreeSet<Integer> get_infected_servers() {
		return (TreeSet<Integer>) this.infected_servers.get(true).clone();
	}
	
	public TreeSet<Integer> get_uninfected_servers() {
		return (TreeSet<Integer>) this.infected_servers.get(false).clone();
	}
}