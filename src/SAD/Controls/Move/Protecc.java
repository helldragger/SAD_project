package SAD.Controls.Move;

import java.util.HashSet;

public class Protecc extends Move{
	
	final Integer server;
	final HashSet<Integer> links_to_cut;
	
	public Protecc() {
		this(-1, null);
	}
	
	public Protecc(final Integer server, final HashSet<Integer> links){
		this.server = server;
		this.links_to_cut = links;
	}
	
	public Integer get_server() {
		return this.server;
	}
	
	public HashSet<Integer> get_links() {
		return this.links_to_cut;
	}
	
	public boolean is_impossible(){
		return (this.server == -1);
	}
}
