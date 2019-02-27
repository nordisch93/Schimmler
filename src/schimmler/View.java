package schimmler;

import java.awt.Color;

import acm.graphics.*;

public class View {
	private int currentBoard[][];
	private GCanvas canvas;
	final double squareSize = 100;
	
	//desktopview
	public View(GCanvas canvas, int[][] initialBoard) {
		currentBoard = initialBoard;
		this.canvas = canvas;
	}
	
	//lighthosue view
	public View() {
		
	}
	
	public void update() {
		Color colors[] = {Color.WHITE, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE};		
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 6; y++) {
				GRect rect = new GRect(squareSize * x, squareSize * y, squareSize, squareSize);
				rect.setFilled(true);				
				rect.setFillColor(colors[currentBoard[x][y]]);
				rect.setColor(colors[currentBoard[x][y]]);
				canvas.add(rect);
			}
		}
		GRect rect = new GRect(0, 0, squareSize * 4, squareSize * 6);
		rect.setColor(Color.BLACK);
		canvas.add(rect);
	}

}
