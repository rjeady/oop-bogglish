package bogglish.grid;

import java.awt.Point;
import java.util.NoSuchElementException;

/**
 * Grid Class This class represents a grid of tiles.
 * 
 * @author Stephen Cummins
 * @version 1.0 Released 11/10/2005
 */
public class Grid {
	private Tile[][] gridRep;

	/**
	 * Constructor will generate a grid of a specified width and height and
	 * populate it with a collection of tiles.
	 * 
	 * @param width
	 * @param height
	 * @param tiles
	 */
	public Grid(int width, int height, TileCollection tiles) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("Grid:constructor(" + width + ", " + height + ", " + tiles
					+ "): grid must be at least 1x1 in size");
		} else if (width * height > tiles.size()) {
			throw new IllegalArgumentException("Grid:constructor(" + width + ", " + height + ", " + tiles
					+ "): not enough tiles to cover grid");
		} else {
			gridRep = new Tile[width][height];
			for (int y = 0; y < height; y++) {

				for (int x = 0; x < width; x++) {

					gridRep[x][y] = tiles.removeOne();
				}
			}
		}
	}

	/**
	 * TileAt This method will return the tile at a given position within the
	 * grid.
	 * 
	 * @param position
	 * @return a Tile
	 */
	public Tile tileAt(Point position) {
		if (position.x > this.width() || position.x < 0 || position.y > this.height() || position.y < 0) {
			throw new IllegalArgumentException("Grid:at(" + position + "): point out of bounds");
		} else {
			return gridRep[position.x][position.y];
		}

	}

	/**
	 * PositionOf This method will search for a given tile and tell you what the
	 * position of it is within the grid
	 * 
	 * @param tile
	 * @return a Point
	 */
	public Point positionOf(Tile tile) {
		for (int y = 0; y < this.height(); y++) {
			for (int x = 0; x < this.width(); x++) {
				if (gridRep[x][y] == tile) {
					return new Point(x, y);
				}
			}
		}
		throw new NoSuchElementException("Grid:of(" + tile + "): tile not contained in this grid");
	}

	/**
	 * Width This method will get the current width of the grid
	 * 
	 * @return int width
	 */
	public int width() {
		return gridRep.length;
	}

	/**
	 * Height This method will get the current height of the grid
	 * 
	 * @return int height
	 */
	public int height() {
		return gridRep[0].length;
	}
}
