package schimmler;

import java.awt.Color;

import acm.graphics.*;

public class View {
	private int currentBoard[][];
	private int currentSelection;
	private GCanvas canvas;
	private Model model;

	final double squareSize = 100;
	final Color normalColors[] = { CustomColor.COLOR_WHITE.getValue(), CustomColor.COLOR_DARKPURPLE.getValue(),
			CustomColor.COLOR_DARKORANGE.getValue(), CustomColor.COLOR_DARKBLUE.getValue(),
			CustomColor.COLOR_DARKGREEN.getValue(), CustomColor.COLOR_DARKRED.getValue(), };
	final Color brightColors[] = { CustomColor.COLOR_WHITE.getValue(), CustomColor.COLOR_LIGHTPURPLE.getValue(),
			CustomColor.COLOR_LIGHTORANGE.getValue(), CustomColor.COLOR_LIGHTBLUE.getValue(),
			CustomColor.COLOR_LIGHTGREEN.getValue(), CustomColor.COLOR_LIGHTRED.getValue(), };

	public View(GCanvas canvas, Model model) {
		this.model = model;
		currentBoard = model.getBoard();
		this.canvas = canvas;
		this.currentSelection = model.getCurrentSelection();
	}

	// lighthosue view
	public View() {

	}

	/**
	 * Returns the id of the Block that was clicked.
	 * 
	 * @param x x position of mouse
	 * @param y y position of mouse
	 * @return -1 if click was outside of board 0 if empty field was clicked Block
	 *         id else
	 */
	public int getClickedBlock(int x, int y) {
		x = Math.floorDiv(x, (int) squareSize);
		y = Math.floorDiv(y, (int) squareSize);
		if ((x > 3 || y > 5) || (x < 0 || y < 0)) {
			return -1;
		} else {
			return currentBoard[x][y];
		}
	}

	public void update() {
		this.currentBoard = model.getBoard();
		this.currentSelection = model.getCurrentSelection();
		// clear canvas
		canvas.removeAll();
		if (model.isPuzzleSolved()) {
			// TODO: GAMEFINISHED
		} else {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 6; y++) {
					GRect rect = new GRect(squareSize * x, squareSize * y, squareSize, squareSize);
					rect.setFilled(true);
					Color c;
					if (currentBoard[x][y] == currentSelection) {
						c = brightColors[currentBoard[x][y]];
					} else {
						c = normalColors[currentBoard[x][y]];
					}
					rect.setFillColor(c);
					rect.setColor(c);
					canvas.add(rect);
				}
			}
			GRect rect = new GRect(0, 0, squareSize * 4, squareSize * 6);
			rect.setColor(Color.BLACK);
			canvas.add(rect);
		}
	}

	private enum CustomColor {
		COLOR_DARKRED(230, 0, 0), COLOR_DARKGREEN(0, 153, 0), COLOR_DARKORANGE(255, 102, 0),
		COLOR_DARKPURPLE(204, 0, 153), COLOR_DARKBLUE(41, 41, 163), COLOR_WHITE(255, 255, 255),

		COLOR_LIGHTRED(255, 102, 102), COLOR_LIGHTGREEN(26, 255, 26), COLOR_LIGHTORANGE(255, 179, 128),
		COLOR_LIGHTPURPLE(255, 77, 210), COLOR_LIGHTBLUE(114, 114, 218);

		private Color value;

		private CustomColor(int r, int g, int b) {
			this.value = new Color(r, g, b);
		}

		public Color getValue() {
			return value;
		}
	}

}
