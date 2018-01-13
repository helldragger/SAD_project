package SAD.Controls.Move;

import java.util.ArrayList;
import java.util.HashSet;

public class Protecc {
	
	Integer server;
	HashSet<Integer> links_to_cut;
	
	public Protecc() {
		this(-1, null);
	}
	
	public Protecc(Integer server, HashSet<Integer> links){
		this.server = server;
		this.links_to_cut = links;
	}
	
}
