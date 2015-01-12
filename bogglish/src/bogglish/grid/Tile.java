package bogglish.grid;

/**
 * Tile Class
 *
 * This class represents a tile in the game grid.
 * 
 * @author Stephen Cummins
 * @version 1.0 Released 11/10/2005
 */
public class Tile {
	private char letter;
	private int value;
	private boolean active;

	/**
	 * Creates a Tile with a given letter and value.
	 * 
	 * @param letter
	 *            - label for letter tile e.g. 'A'
	 * @param value
	 *            - value e.g. points.
	 */
	public Tile(char letter, int value) {
		this.letter = letter;
		this.value = value;
		active = false;
	}

	/**
	 * Get a letter
	 * 
	 * @return the letter
	 */
	public char letter() {
		return letter;
	}

	/**
	 * Get the value of the tile
	 * 
	 * @return
	 */
	public int value() {
		return value;
	}

	/**
	 * Changes the active state for the tile.
	 * 
	 * @param value
	 *            - new state.
	 */
	public void active(boolean value) {
		active = value;
	}

	/**
	 * Is the current tile active.
	 * 
	 * @return true if active, false if not.
	 */
	public boolean checkActive() {
		return active;
	}

	@Override
	public String toString() {
		return "(" + letter + ", " + value + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + letter;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tile))
			return false;
		Tile other = (Tile) obj;
		if (active != other.active)
			return false;
		if (letter != other.letter)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
}
