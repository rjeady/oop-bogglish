package bogglish;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;

import bogglish.grid.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * Game This is a basic class that can be modified to create a word game.
 * 
 * Hint: Can this class be converted into a singleton?
 * 
 * @author Stephen Cummins
 * @version 1.0 Released 11/10/2005
 */
public class Game extends JPanel {
	private static final long serialVersionUID = 1L;

	private Stack<Tile> currentTiles = new Stack<Tile>();
	private ArrayList<Tile> usedTiles = new ArrayList<Tile>();
	private HashSet<String> dictionary = new HashSet<String>();

	final JFrame frame = new JFrame("Java Word Game");

	GridGUI gridGUI;
	JTextArea totalScore;
	JTextArea wordScores;

	int currentScore = 0;

	/**
	 * Creates an instance of the Game.
	 */
	public Game() {
		loadDictionary();
		buildGUI();
	}

	private void loadDictionary() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("dictionary.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				dictionary.add(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will construct each element of the game's GUI
	 */
	private void buildGUI() {
		buildGrid();

		JPanel scoring = buildScoringPanel();
		JPanel buttons = buildGameButtons();

		frame.setTitle("Java Word Game");

		frame.getContentPane().setLayout(new BorderLayout());
		frame.add(gridGUI, BorderLayout.WEST);
		frame.add(scoring, BorderLayout.EAST);
		frame.add(buttons, BorderLayout.SOUTH);

		frame.pack();
		frame.setResizable(true);
		frame.toFront();

		frame.setBackground(Color.lightGray);
		frame.setVisible(true);
	}

	/**
	 * Build the grid of tiles.
	 */
	private void buildGrid() {
		TileCollection collection = new TileCollection();
		Grid grid = new Grid(6, 6, collection);
		gridGUI = new GridGUI(grid);
		gridGUI.setTileForeground(Color.yellow);
		gridGUI.setTileBackground(Color.blue);

		gridGUI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				Tile source = (Tile) actionEvent.getSource();
				processTileClick(source);
			}
		});
	}

	/**
	 * Processes the clicking of a tile by the user.
	 * @param tile the tile that was clicked.
	 */
	private void processTileClick(Tile tile) {
		// if tile is used, do nothing
		for (Tile t : usedTiles) {
			if (t == tile) {
				return;
			}
		}

		// if tile was previously selected, reset it and all subsequent tiles.
		boolean previouslySelected = false;
		for (Tile t : currentTiles) {
			if (t == tile) {
				previouslySelected = true;
				break;
			}
		}
		if (previouslySelected) {
			boolean moreTilesToReset = true;
			while (moreTilesToReset) {
				Tile t = currentTiles.pop();
				setTileState(t, TileState.INACTIVE);
				// stop once we've reset the clicked tile
				if (t == tile)
					moreTilesToReset = false;
			}
		} else {
			// otherwise, only activate the tile if if it's adjacent to the previous tile (or there is none).
			if (currentTiles.empty()) {
				setTileState(tile, TileState.ACTIVE);
				currentTiles.push(tile);
			} else {
				Tile previous = currentTiles.peek();
				Point sourcePos = gridGUI.getGrid().positionOf(tile);
				Point previousPos = gridGUI.getGrid().positionOf(previous);
				if (Math.abs(sourcePos.getX() - previousPos.getX()) <= 1
						&& Math.abs(sourcePos.getY() - previousPos.getY()) <= 1) {
					setTileState(tile, TileState.ACTIVE);
					currentTiles.push(tile);
				}
			}
		}
	}

	/**
	 * Build the panel holding the game action buttons.
	 * @return
	 */
	private JPanel buildGameButtons() {
		JPanel buttons = new JPanel();

		JButton submit = new JButton("Submit Word");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitWord();
			}
		});

		JButton newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetGame();
			}
		});

		buttons.setLayout(new BorderLayout());
		buttons.add(submit, BorderLayout.WEST);
		buttons.add(newGame, BorderLayout.EAST);
		buttons.setVisible(true);

		return buttons;
	}

	/**
	 * Build the panel showing the user's total score and words scored.
	 * @return
	 */
	private JPanel buildScoringPanel() {
		JPanel scoring = new JPanel();

		totalScore = new JTextArea(1, 10);
		wordScores = new JTextArea();
		ShowDefaultScores();

		scoring.setLayout(new BoxLayout(scoring, BoxLayout.Y_AXIS));
		scoring.add(totalScore);
		scoring.add(wordScores);
		scoring.setVisible(true);

		return scoring;
	}

	/**
	 * Calculate the score for the current word and display it.
	 */
	private void scoreCurrentWord() {
		int score = 0;
		StringBuilder word = new StringBuilder();
		for (Tile t : currentTiles) {
			score += t.value();
			word.append(t.letter());
		}
		score *= currentTiles.size();

		wordScores.append("\n");
		wordScores.append(word.toString() + " (" + score + ")");

		currentScore += score;
		totalScore.setText("Current Score: " + currentScore);
	}


	/**
	 * Check if the user has made a valid word, and if so update the score and the grid.
	 */
	private void submitWord() {
		StringBuilder sb = new StringBuilder();
		// for some reason a stack is enumerated in FIFO order...
		for (Tile t1 : currentTiles) {
			sb.append(t1.letter());
		}
		String word = sb.toString();
		if (dictionary.contains(word.toLowerCase())) {
			scoreCurrentWord();

			while (!currentTiles.empty()) {
				Tile t = currentTiles.pop();
				setTileState(t, TileState.USED);
				usedTiles.add(t);
			}

		} else {
			JOptionPane.showMessageDialog(Game.this, "Sorry, '" + word + "' is not a valid word!");
		}
	}

	/**
	 * Resets the game to play again.
	 */
	private void resetGame() {
		frame.remove(gridGUI);
		buildGrid();
		frame.add(gridGUI, BorderLayout.WEST);

		currentScore = 0;
		currentTiles.clear();
		usedTiles.clear();
		ShowDefaultScores();

		frame.repaint();
	}

	/**
	 * Displays the default scores at the start of a game.
	 */
	private void ShowDefaultScores() {
		totalScore.setText("Current score: 0");
		wordScores.setText("Words scored:");
	}

	/**
	 * Sets the graphical appearance of a tile according to its new state.
	 * @param tile
	 * @param tileState
	 */
	private void setTileState(Tile tile, TileState tileState) {
		switch (tileState) {
			case INACTIVE:
				gridGUI.setTileForeground(tile, Color.yellow);
				gridGUI.setTileBackground(tile, Color.blue);
				tile.active(false);
				break;
			case ACTIVE:
				gridGUI.setTileBackground(tile, Color.red);
				gridGUI.setTileForeground(tile, Color.green);
				tile.active(true);
				break;
			case USED:
				gridGUI.setTileBackground(tile, Color.lightGray);
				gridGUI.setTileForeground(tile, Color.gray);
				tile.active(false);
				break;
			default:
				assert false;
		}
	}
}

enum TileState {
	INACTIVE, ACTIVE, USED
}