
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
			return new SimpleSpriteUniverse();
		}
		else if (universeCount == 2) {
			return new BlinkySpriteUniverse();
		}
		else if (universeCount == 3) {
			return new AnimatedSpritesUniverse();
		}
		else if (universeCount == 4) {
			return new JumpingSpriteUniverse();
		}
		else if (universeCount == 5) {
			return new SingleTileUniverse();
		}
		else if (universeCount == 6) {
			return new PatternedUniverse();
		}
		else if (universeCount == 7) {
			return new MappedUniverse();
		}
		else if (universeCount == 8) {
			return new ShootingSpriteUniverse();
		}
		else if (universeCount == 9) {
			return new SateliteSpriteUniverse();
		}				
		else {
			return null;
		}

	}
	
}
