package schimmler;

import java.awt.Color;
import java.io.IOException;

public class LighthouseView extends View {

	final String username = "stu208059";
	final String token = "API-TOK_V6tl-1kdb-EpFy-6hVn-jnxd";

	final int RowOffset = 1;
	final int ColOffset = 1;
	final int CounterOffsetRow = 3;
	final int CounterOffsetCol = 19;

	final int fragmentHeight = 2;
	final int fragmentWidth = 4;

	final DigitalNumber digits[] = { DigitalNumber.DIGI_NUMBER_ZERO, DigitalNumber.DIGI_NUMBER_ONE,
			DigitalNumber.DIGI_NUMBER_TWO, DigitalNumber.DIGI_NUMBER_THREE, DigitalNumber.DIGI_NUMBER_FOUR,
			DigitalNumber.DIGI_NUMBER_FIVE, DigitalNumber.DIGI_NUMBER_SIX, DigitalNumber.DIGI_NUMBER_SEVEN,
			DigitalNumber.DIGI_NUMBER_EIGHT, DigitalNumber.DIGI_NUMBER_NINE };

	final CustomColor normalColors[] = { CustomColor.COLOR_WHITE, CustomColor.COLOR_DARKPURPLE,
			CustomColor.COLOR_DARKORANGE, CustomColor.COLOR_DARKBLUE, CustomColor.COLOR_DARKGREEN,
			CustomColor.COLOR_DARKRED, };
	final CustomColor brightColors[] = { CustomColor.COLOR_WHITE, CustomColor.COLOR_LIGHTPURPLE,
			CustomColor.COLOR_LIGHTORANGE, CustomColor.COLOR_LIGHTBLUE, CustomColor.COLOR_LIGHTGREEN,
			CustomColor.COLOR_LIGHTRED, };

	public LighthouseView(Model model) {
		super(model);
	}

	private byte[] inflateBoard() {

		// fill data with zeros
		byte[] data = new byte[14 * 28 * 3];
		for (byte b : data) {
			b = 0;
		}
		
		switch(model.getGamestate()) {
		case GAMESTATE_START_SCREEN:
			//Start Screen
			break;
		case GAMESTATE_RUNNING:
			// draw game board
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 6; y++) {
					int value = currentBoard[x][y];
					CustomColor cc;
					if (value == currentSelection) {
						cc = brightColors[value];
					} else {
						cc = normalColors[value];// use dark colors
					}

					for (int row = 0; row < fragmentHeight; row++) {
						for (int column = 0; column < fragmentWidth; column++) {
							data[((RowOffset + row + fragmentHeight * y) * 28 + ColOffset + column + fragmentWidth * x)
									* 3] = (byte) (cc.getR());
							data[((RowOffset + row + fragmentHeight * y) * 28 + ColOffset + column + fragmentWidth * x) * 3
									+ 1] = (byte) cc.getG();
							data[((RowOffset + row + fragmentHeight * y) * 28 + ColOffset + column + fragmentWidth * x) * 3
									+ 2] = (byte) cc.getB();
						}
					}
				}
			}

			// draw moveCounter
			int firstDigitValues[][] = digits[getFirstDigit(model.getMoveCount())].getValues();
			int secondDigitValues[][] = digits[getSecondDigit(model.getMoveCount())].getValues();
			final int digitWidth = 4;

			Color digitColor = calculateDigitColor();	
			
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 5; y++) {
					if (secondDigitValues[x][y] == 1) {
						data[((CounterOffsetRow + y) * 28 + CounterOffsetCol + x) * 3] = (byte) (digitColor.getRed());
						data[((CounterOffsetRow + y) * 28 + CounterOffsetCol + x) * 3 + 1] = (byte) (digitColor.getGreen());
						data[((CounterOffsetRow + y) * 28 + CounterOffsetCol + x) * 3 + 2] = (byte) (digitColor.getBlue());
					}
				}
			}

			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 5; y++) {
					if (firstDigitValues[x][y] == 1) {
						data[((CounterOffsetRow + y) * 28 + CounterOffsetCol + digitWidth + x) * 3] = (byte) (digitColor.getRed());
						data[((CounterOffsetRow + y) * 28 + CounterOffsetCol + digitWidth + x) * 3 + 1] = (byte) (digitColor.getGreen());
						data[((CounterOffsetRow + y) * 28 + CounterOffsetCol + digitWidth + x) * 3 + 2] = (byte) (digitColor.getBlue());
					}
				}
			}
			break;
		case GAMESTATE_END_SCREEN:
			//End Screen
			
			
			break;
		}
		return data;
	}

	private Color calculateDigitColor() {
		final int turnsUntilCounterDarkens = 20;
		
		int moveCount = model.getMoveCount();
		int functionResult = 25*(int)(Math.floor(Math.sqrt(Math.max(0, Math.min(100, moveCount - turnsUntilCounterDarkens)))));
		int r = 255;
		int g = 255 - functionResult;
		int b = 255 - functionResult;
		return new Color(r,g,b);
	}

	private int getFirstDigit(int moveCount) {
		return moveCount % 10;
	}

	private int getSecondDigit(int moveCount) {
		return (moveCount / 10) % 10;
	}

	@Override
	public void update() {

		this.currentBoard = model.getBoard();
		this.currentSelection = model.getCurrentSelection();

		LighthouseDisplay display = new LighthouseDisplay(username, token);

		// Try connecting to the display
		try {
			display.connect();
			// Wait until connection is established
			while (!display.isConnected()) {
				Thread.sleep(100);
			}
		} catch (Exception e) {
			System.out.println("Connection failed: " + e.getMessage());
			e.printStackTrace();
		}

		// Send data to the display
		try {
			// This array contains for every window (14 rows, 28 columns) three
			// bytes that define the red, green, and blue component of the color
			// to be shown in that window. See documentation of LighthouseDisplay's
			// send(...) method.

			byte[] data = inflateBoard();

			display.send(data);
		} catch (IOException e) {
			System.out.println("Connection failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private enum DigitalNumber {
		DIGI_NUMBER_ZERO(new int[][] { { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1 } }),
		DIGI_NUMBER_ONE(new int[][] { { 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0 }, { 1, 1, 1, 1, 1 } }),
		DIGI_NUMBER_TWO(new int[][] { { 1, 0, 1, 1, 1 }, { 1, 0, 1, 0, 1 }, { 1, 1, 1, 0, 1 } }),
		DIGI_NUMBER_THREE(new int[][] { { 1, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 } }),
		DIGI_NUMBER_FOUR(new int[][] { { 1, 1, 1, 0, 0 }, { 0, 0, 1, 0, 0 }, { 0, 1, 1, 1, 1 } }),
		DIGI_NUMBER_FIVE(new int[][] { { 1, 1, 1, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 1, 1, 1 } }),
		DIGI_NUMBER_SIX(new int[][] { { 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 1, 1, 1 } }),
		DIGI_NUMBER_SEVEN(new int[][] { { 1, 1, 0, 0, 0 }, { 1, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1 } }),
		DIGI_NUMBER_EIGHT(new int[][] { { 1, 1, 1, 1, 1 }, { 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 } }),
		DIGI_NUMBER_NINE(new int[][] { { 1, 1, 1, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 1, 1, 1, 1 } });

		private int values[][];

		private DigitalNumber(int values[][]) {
			this.values = values;
		}

		public int[][] getValues() {
			return this.values;
		}
	}

}
