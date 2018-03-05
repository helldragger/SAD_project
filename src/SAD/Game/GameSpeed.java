package SAD.Game;

import java.util.NoSuchElementException;

public enum GameSpeed {
	INSTANT(0),
	FASTER(150),
	FAST(300),
	NORMAL(500),
	SLOW(750),
	SLOWER(1000);
	
	int step_duration;

    GameSpeed(int value) {
		this.step_duration = value;
	}
	
	public static GameSpeed from_index(int index) {
		switch (index) {
			case 1:
				return SLOWER;
			case 2:
				return SLOW;
			case 3:
				return NORMAL;
			case 4:
				return FAST;
			case 5:
				return FASTER;
			case 6:
				return INSTANT;
			default:
				throw new NoSuchElementException();
		}
	}
	
	public int get_index() {
		switch (this) {
			case SLOWER:
				return 1;
			case SLOW:
				return 2;
			case NORMAL:
				return 3;
			case FAST:
				return 4;
			case FASTER:
				return 5;
			case INSTANT:
				return 6;
			default:
				throw new NoSuchElementException();
		}
	}
}
