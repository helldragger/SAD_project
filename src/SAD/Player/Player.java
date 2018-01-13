package SAD.Player;

import SAD.Controls.Move.Attacc;
import SAD.Controls.Move.Protecc;
import SAD.Data;

public abstract class Player {
	
	public abstract Attacc attacc(Data game);
	public abstract Protecc protecc(Data game);
}
