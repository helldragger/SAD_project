package SAD.GUI;

import SAD.Data;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.TreeSet;

public class GUI {
	static Graph graph = new SingleGraph("Game");
	;
	
	static public void load_graph(Data map) {
		graph.clear();
		graph.setStrict(false);
		graph.setAutoCreate(false);
		graph.addAttribute("ui.stylesheet",
				"node{\n" +
						"\n" +
						"    fill-color: darkgreen;\n" +
						"    size : 10px;\n" +
						"    stroke-mode: plain;\n" +
						"    stroke-color: darkgrey;\n" +
						"    stroke-width: 1px;\n" +
						"\n" +
						"}\n" +
						"\n" +
						"node.infected{\n" +
						"    fill-color: darkred;\n" +
						"    size : 10px;\n" +
						"    stroke-mode: plain;\n" +
						"    stroke-color: gold;\n" +
						"    stroke-width: 1px;\n" +
						"}\n" +
						"\n" +
						"edge{\n" +
						"    fill-mode: dyn-plain;\n" +
						"    fill-color: green, red;\n" +
						"}");
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
		graph.display();
		
		//loading nodes
		for (Integer server : map.get_all_servers()) {
			Node n = graph.addNode(String.valueOf(server));
			n.addAttribute("ui.label", n.getId());
		}
		//loading edges
		for (Integer server : map.get_all_servers()) {
			for (Integer neighbour : map.get_neighbours(server)) {
				Edge e = graph.addEdge(String.valueOf(server) + '-' + String.valueOf(neighbour), String.valueOf(server), String.valueOf(neighbour));
				if (e != null) e.addAttribute("ui.color", 0.0);
			}
		}
		//TADAAAA
	}
	
	static public void infect_node(Integer server, TreeSet<Integer> linked_servers) {
		Node n = graph.getNode(String.valueOf(server));
		n.addAttribute("ui.class", "infected");
		for (Edge e : n.getEdgeSet()) {
			Double color = e.getAttribute("ui.color", Double.class);
			e.setAttribute("ui.color", color + 0.5);
		}
	}
	
	static public void cut_links(Integer server, TreeSet<Integer> linked_servers) {
		for (Integer s : linked_servers) {
			graph.removeEdge(String.valueOf(server) + '-' + String.valueOf(s));
			graph.removeEdge(String.valueOf(s) + '-' + String.valueOf(server));
		}
	}
	
}
