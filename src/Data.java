import java.util.ArrayList;
import java.util.HashMap;

public class Data {
    // Map of links between servers (server to which server)
    HashMap<Integer, ArrayList<Integer>> links;
    // List of servers per state (infected or not)
    HashMap<Boolean, ArrayList<Integer>> infected_servers;

    public Data(Integer n) {
        // n = Number of servers


    }

    void generate_data(Integer n) {
        // generate the n servers
        this.infected_servers = new HashMap<>();
        
    }

    public void print_server(){
        System.out.println(
                "Les serveurs infectés :\n" +infected_servers.get(true)+ "\n" +
                "Les serveurs non-infectés :\n" +infected_servers.get(false)+ "\n"
        );

        for(int i = 0; i < this.links.size(); i++){
            System.out.println(
                    "\nLe serveur n°" +i+ " est relié aux serveurs " +links.get(i)
            );
        }
    }


}
