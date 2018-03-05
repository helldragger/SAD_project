package SAD.Move;

public class Attacc extends Move {
	Integer target;
	
	public Attacc() {
		this(-1);
	}
	
	public Attacc(Integer cible) {
		this.target = cible;
	}
	
	public Integer get_target() {
		return this.target;
	}
	
	public boolean is_empty() {
		return (this.target == -1);
	}
	
}
