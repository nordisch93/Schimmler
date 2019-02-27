package schimmler;

import acm.program.*;
import acm.graphics.*;

public class Game extends GraphicsProgram{

	public void run() {
		boolean desktopView = true;
		
		Model model = new Model();
		
		if(desktopView) {
			GCanvas canvas = getGCanvas();
			View view = new View(canvas, model.getBoard());
			
			
			view.update();
		}
		else {
			View view = new View();
		}		
		
		//view.update();
	}

}
