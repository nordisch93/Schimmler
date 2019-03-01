package schimmler;

import java.io.IOException;

public class LighthouseView extends View {
	
	final String username = "stu208059";
	final String token = "API-TOK_V6tl-1kdb-EpFy-6hVn-jnxd";
	
	
	final int RowOffset = 1;
	final int ColOffset = 6;
	
	final int fragmentHeight = 2;
	final int fragmentWidth = 4;
	
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
				
				for(int row = 0; row < fragmentHeight; row ++) {
					for(int column = 0; column < fragmentWidth; column++) {
						data[((RowOffset+row+fragmentHeight*y)*28 + ColOffset + column+fragmentWidth*x)*3] = (byte)(cc.getR());
						data[((RowOffset+row+fragmentHeight*y)*28 + ColOffset + column+fragmentWidth*x)*3+1] = (byte)cc.getG();
						data[((RowOffset+row+fragmentHeight*y)*28 + ColOffset + column+fragmentWidth*x)*3+2] = (byte)cc.getB();						
					}
				}
			}
		}
		
		
		return data;
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
