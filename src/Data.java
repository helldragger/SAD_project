import java.util.*;

public class Data {
    //TODO determiner une classe pour les structures de données
    List<Object> maps;
    // Map of links between servers (server to which server)
    HashMap<Integer, ArrayList<Integer>> links;
    // List of servers per state (infected or not)
    HashMap<Boolean, ArrayList<Integer>> infected_servers;

    public Data(Integer n) {
        // n = Number of servers


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