package bogglish.grid;

/**
 * Tile Data Interface
 *
 * This interface provides the information needed to generate the letters on the
 * face of the tiles and their associated scores.
 * 
 * You should not change this interface.
 * 
 * @author Stephen Cummins
 * @version 1.0 Released 11/10/2005
 */
public interface TileData {

	final static char[] LETTER = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	final static int[] FREQUENCY = { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1,
			2, 1 };
	final static int[] VALUE = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4,
			10 };
}
