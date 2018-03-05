package SAD.Player;

import SAD.Game.Game;
import SAD.Move.Attacc;
import SAD.Move.Protecc;

public abstract class Player {
	
	public abstract Attacc attacc(Game game);
	
	public abstract Protecc protecc(Game game);
}
