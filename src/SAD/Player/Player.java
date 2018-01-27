package SAD.Player;

import SAD.Controls.Move.Attacc;
import SAD.Controls.Move.Protecc;
import SAD.Game.Game;

public abstract class Player {
	
	public abstract Attacc attacc(Game game);
	
	public abstract Protecc protecc(Game game);
}
