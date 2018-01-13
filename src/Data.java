import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

class Data {
    //TODO determiner une classe pour les structures de données
    private static ArrayList<Data> maps = new ArrayList<>();
    // Map of links between servers (server to which server)
    private Map<Integer, ArrayList<Integer>> links;
    // List of servers per state (infected or not)
    private Map<Boolean, ArrayList<Integer>> infected_servers;

    Data() {
		this.links = new HashMap<>();
		this.infected_servers = new HashMap<>();
		this.infected_servers.put(true, new ArrayList<>());
		this.infected_servers.put(false, new ArrayList<>());
    }

    void print_server() {
        System.out.println(
                "Les serveurs infectés :\n" + infected_servers.get(true) + "\n" +
                        "Les serveurs non-infectés :\n" + infected_servers.get(false) + "\n"
        );

        for (int i = 0; i < this.links.size(); i++) {
            System.out.println(
                    "\nLe serveur n°" + i + " est relié aux serveurs " + links.get(i)
            );
        }
    }

    static void load_maps(){
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
			
			    ArrayList< Integer > servers = new ArrayList<>();
			    for (int j = 0; j < 15; j++)
			    {
			    	servers.add(j);
			    }
			    
		    	map_data.infected_servers.put(false, servers);
			    map_data.links.put(0, new ArrayList<>(singletonList(7)));
			    map_data.links.put(1, new ArrayList<>(singletonList(8)));
			    map_data.links.put(2, new ArrayList<>(asList(3,9)));
			    map_data.links.put(3, new ArrayList<>(singletonList(2)));
			    map_data.links.put(4, new ArrayList<>(singletonList(5)));
			    map_data.links.put(5, new ArrayList<>(asList(4,6)));
			    map_data.links.put(6, new ArrayList<>(asList(5,7)));
			    map_data.links.put(7, new ArrayList<>(asList(0,6,8)));
			    map_data.links.put(8, new ArrayList<>(asList(1,7,9)));
			    map_data.links.put(9, new ArrayList<>(asList(2,8,10,12)));
			    map_data.links.put(10, new ArrayList<>(singletonList(9)));
			    map_data.links.put(11, new ArrayList<>(singletonList(12)));
			    map_data.links.put(12, new ArrayList<>(asList(9,11,13,14)));
			    map_data.links.put(13, new ArrayList<>(singletonList(12)));
			    map_data.links.put(14, new ArrayList<>(singletonList(12)));
			    // Let's store the map for further testing.
			    maps.add(map_data);
		    }
		    
		    //TODO read serialized data.
		    
	    }
    	
    }
    
    static Data generate_map(){
        Random rand = new Random();
        // Use already planned maps.
        // 1 map au pif parmi celles chargées
	    Data map = maps.get(rand.nextInt(maps.size()));
        // determine the infected server
	    int server_i = rand.nextInt(map.infected_servers.get(false).size());
	    Integer server = map.infected_servers.get(false).get(server_i);
	    map.infect_server(server);
     
	    return map;
    }
	
    void infect_server(Integer server){
    	this.infected_servers.get(false).remove(server);
    	this.infected_servers.get(true).add(server);
    }
	
    void cut_links(Integer server, ArrayList<Integer> linked_servers){
    	
    	//cut from the other side
	    for (Integer s : linked_servers){
	    	this.links.get(s).remove(server);
	    }
	    
    	// cut from the server side (in this order because remove All empties linked_servers
    	this.links.get(server).removeAll(linked_servers);
    	
    	
    }
    
    ArrayList<Integer> get_neighbours(Integer server){
    	return this.links.get(server);
    }
    
    Map<Integer, ArrayList<Integer>> get_all_links(){
    	return this.links;
    }
    
	Map<Boolean, ArrayList<Integer>> get_all_servers(){
    	return this.infected_servers;
	}
	
	Integer get_servers_count(){
		return this.links.keySet().size();
	}
	
}