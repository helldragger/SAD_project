
import java.util.*;

public class Data {
    //TODO determiner une classe pour les structures de données
    List<Object> maps;

    // Map of links between servers (server to which server)
    Map<Integer, List<Integer>> links;
    // List of servers per state (infected or not)
    Map<Boolean, List<Integer>> infected_servers;

    public Data() {
        // n = Number of servers


    }

    void generate_data() {
        Random rand = new Random();
        // Use already planned maps.
        // 3 maps de base
        int map = rand.nextInt(3);

        // TODO plannifier des maps a tirer aleatoires

        // generate the n uninfected servers
        this.infected_servers = new HashMap<>();

        // Server list generation
        List<Integer> server_list = new ArrayList<>();
        for (int i = 0; i < n; i++)
            server_list.add(i);

        // Servers links generation

        Map<Integer, List<Integer>> links = new HashMap<>();


        this.infected_servers.put(true, new ArrayList<>());
        this.infected_servers.put(false, new ArrayList<>());

    }

    // TODO recuperer des maps

    // TODO

}
