package SAD.Controls.Move;

import java.util.TreeSet;

public class Protecc extends Move{
	
	final Integer server;
	final TreeSet<Integer> links_to_cut;
	
	public Protecc() {
		this(-1, null);
	}
	
	public Protecc(final Integer server, final TreeSet<Integer> links) {
		this.server = server;
		this.links_to_cut = links;
	}
	
	public Integer get_server() {
		return this.server;
	}
	
	public TreeSet<Integer> get_links() {
		return this.links_to_cut;
	}
	
	public boolean is_impossible(){
		return (this.server == -1);
	}
}
