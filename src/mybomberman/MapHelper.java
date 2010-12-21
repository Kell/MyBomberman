package mybomberman;

import mybomberman.states.GamePlayState;


public class MapHelper {

	/**
	 * 
	 * @param px - value in pixel
	 * @return number of Tile
	 */
	public static int getTileNumber(int px) {
		return (int) px / 64;
	}

	/**
	 * 
	 * @param x - x coordinate of a tile
	 * @param y - y coordinate of a tile
	 * @return boolean value - true if tile is blocked
	 */
	public static boolean isTileBlocked(int x, int y) {
		int xTile = getTileNumber(x);
		int yTile = getTileNumber(y);

		
		if (xTile >= GamePlayState.map.getWidth() || xTile < 0 || yTile >= GamePlayState.map.getHeight() || yTile < 0)
			return true;
			
		if (GamePlayState.blocked[xTile][yTile]) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return boolean value - true if tile is breakable
	 */
	public static boolean isTileBreakable(int x, int y) {
		int xTile = getTileNumber(x);
		int yTile = getTileNumber(y);

		int tileID = GamePlayState.map.getTileId(xTile, yTile, 0);
		String value = GamePlayState.map.getTileProperty(tileID, "breakable", "false");

		if (value.equals("1")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param dir - int value (1-4) - position from explosion 1 - upper tile 2 -
	 *            right tile 3 - lower tile 4 - left tile
	 */
	public static boolean changeBackgroundTile(int x, int y, int dir, int tileID) {
		boolean breakable = isTileBreakable(x, y);
		int xTile = getTileNumber(x);
		int yTile = getTileNumber(y);

		if (breakable) {
			GamePlayState.map.setTileId(xTile, yTile, 0, tileID);
			return true;
		}
		return false;
	}
}
