public class Main {
    public static void main(String[] args){
    	// Load maps into memory
    	Data.load_maps();
    	
    	// generate a random map to play
        Data map = Data.generate_map();
        
        // show the game
	    map.print_server();
    }
}
