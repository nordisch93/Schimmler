package schimmler;

import java.awt.Color;

import acm.graphics.*;

public class View {
	private int currentBoard[][];
	private int currentSelection;
	private GCanvas canvas;
	final double squareSize = 100;
	final Color normalColors[] = {Color.WHITE, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE};
	final Color brightColors[] = {Color.WHITE, Color.CYAN, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE};
	//desktopview
	public View(GCanvas canvas, int[][] initialBoard) {
		currentBoard = initialBoard;
		this.canvas = canvas;
		this.currentSelection = 0;
	}
	
	//lighthosue view
	public View() {
		
	}
	
	/**
	 * Returns the id of the Block that was clicked. 
	 * 
	 * @param x		x position of mouse
	 * @param y		y position of mouse
	 * @return		-1 				if click was outside of board
	 * 				0				if empty field was clicked
	 * 				Block id		else
	 */
	public int getClickedBlock(int x, int y) {
		if (x > 4 * squareSize || y > 6* squareSize) {
			return -1;
		}
		else {
		x = Math.floorDiv(x, (int)squareSize);
		y = Math.floorDiv(y, (int)squareSize);
			return currentBoard[x][y]; 
		}		
	}
	
	public void update(int[][]currentBoard, int currentSelection) {
		this.currentBoard = currentBoard;
		this.currentSelection = currentSelection;
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 6; y++) {
				GRect rect = new GRect(squareSize * x, squareSize * y, squareSize, squareSize);
				rect.setFilled(true);
				Color c;
				if(currentBoard[x][y] == currentSelection) {
					c = brightColors[currentBoard[x][y]];
					}
				else {
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
