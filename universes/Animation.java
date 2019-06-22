
public class Animation {

	private static int universeCount = 0;
	
	public static int getUniverseCount() {
		return universeCount;
	}

	public static void setUniverseCount(int count) {
		Animation.universeCount = count;
	}

	public static Universe getNextUniverse() {

		universeCount++;
		
		if (universeCount == 1) {
			return new GameUniverse();
		}
		else if (universeCount == 2) {
			return new EndScreen();
		}
		else {
			universeCount = 0;
			return getNextUniverse();
		}
		
		
	}
	
}
