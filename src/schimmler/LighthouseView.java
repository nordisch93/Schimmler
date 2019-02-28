package schimmler;

import java.io.IOException;

public class LighthouseView extends View {
	
	final CustomColor normalColors[] = { CustomColor.COLOR_WHITE, CustomColor.COLOR_DARKPURPLE,
			CustomColor.COLOR_DARKORANGE, CustomColor.COLOR_DARKBLUE,
			CustomColor.COLOR_DARKGREEN, CustomColor.COLOR_DARKRED, };
	final CustomColor brightColors[] = { CustomColor.COLOR_WHITE, CustomColor.COLOR_LIGHTPURPLE,
			CustomColor.COLOR_LIGHTORANGE, CustomColor.COLOR_LIGHTBLUE,
			CustomColor.COLOR_LIGHTGREEN, CustomColor.COLOR_LIGHTRED, };

	public LighthouseView(Model model) {
		super(model);
	}
	
	private byte[] inflateBoard() {
		byte[] data = new byte[14 * 28 * 3];
		for(byte b : data) {
			b = 0;
		}
		
		
		final int RowOffset = 5;
		final int ColOffset = 1;
		
		for(int x = 0; x < 4; x++) {
			for(int y = 0; y < 6; y++) {
				int value = currentBoard[x][y];
				CustomColor cc;
				if(value == currentSelection)
				{
					cc = brightColors[value];
				}
				else
				{
					cc = normalColors[value];//use dark colors
				}
				
				for(int column = 0; column < 3; column ++) {
					for(int row = 0; row < 3; row++) {
						data[((RowOffset+row)*14 + ColOffset + column)*3] = (byte)(cc.getR());
						data[((RowOffset+row)*14 + ColOffset + column)*3+1] = (byte)cc.getG();
						data[((RowOffset+row)*14 + ColOffset + column)*3+2] = (byte)cc.getB();						
					}
				}
			}
		}
		
		
		return data;
	}	
	
	private enum CustomColor {
		COLOR_DARKRED(230, 0, 0), COLOR_DARKGREEN(0, 153, 0), COLOR_DARKORANGE(255, 102, 0),
		COLOR_DARKPURPLE(204, 0, 153), COLOR_DARKBLUE(41, 41, 163), COLOR_WHITE(255, 255, 255),

		COLOR_LIGHTRED(255, 102, 102), COLOR_LIGHTGREEN(26, 255, 26), COLOR_LIGHTORANGE(255, 179, 128),
		COLOR_LIGHTPURPLE(255, 77, 210), COLOR_LIGHTBLUE(114, 114, 218);

		private int r;
		private int g;
		private int b;

		private CustomColor(int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}

		public int getR() { return r; }
		public int getG() { return g; }
		public int getB() { return b; }
	}

	@Override
	public void update() {
		
		this.currentBoard = model.getBoard();
		this.currentSelection = model.getCurrentSelection();

		LighthouseDisplay display = new LighthouseDisplay("MyUsername", "MyToken");
		
		// Try connecting to the display
		try {
		    display.connect();
		    // Wait until connection is established
		    while(!display.isConnected()) {
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


}
