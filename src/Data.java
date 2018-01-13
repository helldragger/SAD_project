import java.lang.reflect.Array;
import java.util.*;

public class Data {
    //TODO determiner une classe pour les structures de données
    static List<Data> maps = new ArrayList<>();
    // Map of links between servers (server to which server)
    Map<Integer, List<Integer>> links;
    // List of servers per state (infected or not)
    Map<Boolean, List<Integer>> infected_servers;

    public Data() {
		this.links = new HashMap<>();
		this.infected_servers = new HashMap<>();
		this.infected_servers.put(true, new ArrayList<>());
		this.infected_servers.put(false, new ArrayList<>());
    }

    public void print_server() {
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
			
			    List< Integer > servers = new ArrayList<>();
			    for (int j = 0; j < 15; j++)
			    {
			    	servers.add(j);
			    }
			    
		    	map_data.infected_servers.put(false, servers);
			    
			    map_data.links.put(0, Arrays.asList(new Integer[]{7}));
			    map_data.links.put(1, Arrays.asList(new Integer[]{8}));
			    map_data.links.put(2, Arrays.asList(new Integer[]{3,9}));
			    map_data.links.put(3, Arrays.asList(new Integer[]{2}));
			    map_data.links.put(4, Arrays.asList(new Integer[]{5}));
			    map_data.links.put(5, Arrays.asList(new Integer[]{4,6}));
			    map_data.links.put(6, Arrays.asList(new Integer[]{5,7}));
			    map_data.links.put(7, Arrays.asList(new Integer[]{0,6,8}));
			    map_data.links.put(8, Arrays.asList(new Integer[]{1,7,9}));
			    map_data.links.put(9, Arrays.asList(new Integer[]{2,8,10,12}));
			    map_data.links.put(10, Arrays.asList(new Integer[]{9}));
			    map_data.links.put(11, Arrays.asList(new Integer[]{12}));
			    map_data.links.put(12, Arrays.asList(new Integer[]{9,11,13,14}));
			    map_data.links.put(13, Arrays.asList(new Integer[]{12}));
			    map_data.links.put(14, Arrays.asList(new Integer[]{12}));
			    // Let's store the map for further testing.
			    maps.add(map_data);
		    }
		    else{
	    		//TODO read serialized data.
		    }
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
	    infect_server(map, server);
     
	    return map;
    }
	
    static void infect_server(Data data, Integer server){
    	data.infected_servers.get(false).remove(server);
    	data.infected_servers.get(true).add(server);
    }
    // TODO recuperer des maps

    // TODO

}