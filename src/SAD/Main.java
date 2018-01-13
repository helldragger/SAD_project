package SAD;

import java.util.Random;

public class Main {
    public static void main(String[] args){
    	// Load maps into memory
    	Data.load_maps();
    	
    	// generate a random map to play
        Data map = Data.generate_map();
        
        // show the game
	    map.print_server();
	    
	    
	    
	    //TESTS
	    Random rand = new Random();
	    //isolate a node
		//Integer server = rand.nextInt(map.get_servers_count());
		Integer server = 9;
	    System.out.println("=================== Isolating server no "+server+" ===================");
	    map.cut_links(server, map.get_neighbours(server));
	    
	    map.print_server();
	    
	    //TODO Main loop
	    
	    
	    
    }
}
