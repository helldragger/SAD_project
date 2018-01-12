import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    // Map of links between servers
    HashMap<Integer, ArrayList<Integer>> links;
    // List of servers states (infected or not)
    ArrayList<Boolean> infected_servers;

    public ArrayList<Boolean> getInfected_servers() {
        return infected_servers;
    }

    public void setInfected_servers(ArrayList<Boolean> infected_servers) {
        this.infected_servers = infected_servers;
    }

    public HashMap<Integer, Integer> getLinks() {
        return links;
    }

    public void setLinks(HashMap<Integer, Integer> links) {
        this.links = links;
    }
}
