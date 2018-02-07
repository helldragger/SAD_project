package SAD.Game;

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
}
