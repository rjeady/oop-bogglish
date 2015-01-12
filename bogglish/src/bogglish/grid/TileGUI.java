package bogglish.grid;

import javax.swing.JButton;
import java.awt.Color;

/**
 * TileGUI
 *
 * This class generates a GUI element that represents a tile in the game grid.
 * 
 * @author Stephen Cummins
 * @version 1.0 Released 11/10/2005
 */
public class TileGUI extends JButton {

	private static final long serialVersionUID = 1L;
	private Tile model;

	/**
	 * The constructor accepts a tile and generates the button for the grid.
	 * 
	 * @param tile
	 */
	public TileGUI(Tile tile) {
		super();
		model = tile;
		setText(makeHTML(super.getForeground(), tile));
		this.setContentAreaFilled(true);
	}

	private String setTwoDigits(String string) {
		if (string.length() == 1) {
			return "0" + string;
		} else if (string.length() == 2) {
			return string;
		} else {
			throw new IllegalArgumentException("TileGUI:setTwoDigits(" + string + "): more than two digits");
		}
	}

	/**
	 * This will allow you to change the foreground colour of the tile.
	 */
	@Override
	public void setForeground(Color colour) {
		super.setForeground(colour);
		if (model != null) {
			this.setText(makeHTML(colour, model));
		}
	}

	/**
	 * This method will get you the tile object which is being represented by
	 * this GUI element
	 * 
	 * @return a Tile
	 */
	public Tile getTile() {
		return model;
	}
	
	private String makeHTML(Color colour, Tile tile) {
		String redString = Integer.toHexString(colour.getRed());
		String greenString = Integer.toHexString(colour.getGreen());
		String blueString = Integer.toHexString(colour.getBlue());
		redString = setTwoDigits(redString);
		greenString = setTwoDigits(greenString);
		blueString = setTwoDigits(blueString);

		StringBuffer result = new StringBuffer();
		result.append("<html><font color=\"#");
		result.append(redString);
		result.append(greenString);
		result.append(blueString);
		result.append("\" size=\"+5\">" + tile.letter() + "</font><sub>" + tile.value() + "</sub>");
		return result.toString();
	}
}
