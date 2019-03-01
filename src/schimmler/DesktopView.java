package schimmler;

import java.awt.Color;

import acm.graphics.*;

public class DesktopView extends View {

	private GCanvas canvas;
	
	final double squareSize = 100;

	final Color normalColors[] = { CustomColor.COLOR_WHITE.getColor(), CustomColor.COLOR_DARKPURPLE.getColor(),
			CustomColor.COLOR_DARKORANGE.getColor(), CustomColor.COLOR_DARKBLUE.getColor(),
			CustomColor.COLOR_DARKGREEN.getColor(), CustomColor.COLOR_DARKRED.getColor(), };
	final Color brightColors[] = { CustomColor.COLOR_WHITE.getColor(), CustomColor.COLOR_LIGHTPURPLE.getColor(),
			CustomColor.COLOR_LIGHTORANGE.getColor(), CustomColor.COLOR_LIGHTBLUE.getColor(),
			CustomColor.COLOR_LIGHTGREEN.getColor(), CustomColor.COLOR_LIGHTRED.getColor(), };

	public DesktopView(Model model, GCanvas canvas) {
		super(model);
		this.canvas = canvas;
		// TODO Auto-generated constructor stub
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

	@Override public void update() {
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

}
