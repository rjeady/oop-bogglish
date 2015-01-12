package bogglish.grid;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.NoSuchElementException;

/**
 * GridGUI This class is used to generate the GUI for the game.
 * 
 * You shouldn't need to modify this class to create your game.
 * 
 * @author Stephen Cummins
 * @version 1.0 Released 11/10/2005
 */
public class GridGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private Grid model;
	private ActionListener observer;

	/**
	 * Constructor will create a GUI based on the provided Grid object.
	 * 
	 * @param grid
	 */
	public GridGUI(Grid grid) {
		super(new GridLayout(grid.width(), grid.height()));

		model = grid;
		ActionListener tileSelectionListener = new TileSelectionListener(this);

		for (int y = 0; y < grid.height(); y++) {
			for (int x = 0; x < grid.width(); x++) {

				TileGUI component = new TileGUI(grid.tileAt(new Point(x, y)));
				component.addActionListener(tileSelectionListener);
				this.add(component);
			}
		}
	}

	/**
	 * Add an action Listener
	 * 
	 * @param actionListener
	 */
	public void addActionListener(ActionListener actionListener) {
		this.observer = actionListener;
	}

	/**
	 * Gets the grid.
	 * 
	 * @return the grid object
	 */
	public Grid getGrid() {
		return model;
	}

	/**
	 * Change foreground colour of a tile.
	 * 
	 * @param tile
	 *            - tile to change
	 * @param colour
	 *            - colour to change it to.
	 */
	public void setTileForeground(Tile tile, Color colour) {
		TileGUI tileGUI = locateTileGUI(tile);
		tileGUI.setForeground(colour);
	}

	/**
	 * Change the Tile background colour.
	 * 
	 * @param tile
	 *            - tile to change
	 * @param colour
	 *            - colour to change it to.
	 */
	public void setTileBackground(Tile tile, Color colour) {
		TileGUI tileGUI = locateTileGUI(tile);
		tileGUI.setBackground(colour);
	}

	/**
	 * Change foreground colour of a tile.
	 * 
	 * @param position
	 *            - of the tile.
	 * @param colour
	 *            - colour to change it to.
	 */
	public void setTileForeground(Point position, Color colour) {
		TileGUI tileGUI = locateTileGUI(position);
		tileGUI.setForeground(colour);
	}

	/**
	 * Change the Tile background colour.
	 * 
	 * @param position
	 *            - of the tile
	 * @param colour
	 *            - colour to change it to.
	 */
	public void setTileBackground(Point position, Color colour) {
		TileGUI tileGUI = locateTileGUI(position);
		tileGUI.setBackground(colour);
	}

	/**
	 * Change foreground colour of all tiles.
	 * 
	 * @param color
	 */
	public void setTileForeground(Color color) {
		for (int index = 0; index < this.getComponentCount(); index++) {
			TileGUI tileGUI = (TileGUI) this.getComponent(index);
			tileGUI.setForeground(color);
		}
	}

	/**
	 * Change foreground colour of all tiles.
	 * 
	 * @param color
	 */
	public void setTileBackground(Color color) {
		for (int index = 0; index < this.getComponentCount(); index++) {
			TileGUI tileGUI = (TileGUI) this.getComponent(index);
			tileGUI.setBackground(color);
		}
	}

	private TileGUI locateTileGUI(Tile tile) {
		for (int index = 0; index < this.getComponentCount(); index++) {
			TileGUI tileGUI = (TileGUI) this.getComponent(index);
			if (tileGUI.getTile() == tile) {
				return tileGUI;
			}
		}
		throw new NoSuchElementException("GridGUI:locateTileGUI(" + tile
				+ "): tile not represented by this GUI");
	}

	private TileGUI locateTileGUI(Point position) {
		int index = position.y * ((GridLayout) this.getLayout()).getRows() + position.x;

		try {
			return (TileGUI) this.getComponent(index);
		} catch (ArrayIndexOutOfBoundsException arrayIndexExcep) {
			throw new IllegalArgumentException("GridGUI:locateTileGUI(" + position
					+ "): position out of bounds");
		}
	}

	private class TileSelectionListener implements ActionListener {
		private GridGUI owner;

		public TileSelectionListener(GridGUI gridGUI) {
			this.owner = gridGUI;
		}

		public void actionPerformed(ActionEvent actionEvent) {
			Tile source = ((TileGUI) actionEvent.getSource()).getTile();
			if (owner.hasObserver()) {
				ActionEvent reportedEvent = new ActionEvent(source, 0, model.positionOf(source).toString());
				observer.actionPerformed(reportedEvent);
			}
		}

	}

	private boolean hasObserver() {
		return this.observer != null;
	}
}
