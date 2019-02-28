package schimmler;

import acm.program.*;
import acm.graphics.*;

import java.awt.event.*;

@SuppressWarnings("serial")
public class Game extends GraphicsProgram {

	View view;
	Controller controller;
	Model model;
	
	public void init() {
		// initialize eventListeners
		addMouseListeners();
		addKeyListeners();
	}

	public void run() {
		
		boolean desktopView = true;
		this.model = new Model();
		this.controller = new Controller(model);

		if (desktopView) {
			GCanvas canvas = getGCanvas();
			this.view = new View(canvas, model.getBoard());
			model.addView(view);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		controller.keyReleased(e);
	}
	
	public void mouseClicked(MouseEvent e) {
		controller.mouseClicked(e);
	}

}
