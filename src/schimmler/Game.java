package schimmler;

import acm.program.*;
import acm.graphics.*;

import java.awt.event.*;

@SuppressWarnings("serial")
public class Game extends GraphicsProgram {

	Controller controller;
	Model model;

	/**
	 * Initializes the program by adding eventListeners.
	 */
	public void init() {
		addMouseListeners();
		addKeyListeners();
	}

	/**
	 * Initializes instances of a model, a controller and several views.
	 */
	public void run() {

		boolean isLighthouseConnected = false;
		this.model = new Model();
		this.controller = new Controller(model);

		GCanvas canvas = getGCanvas();
		DesktopView view = new DesktopView(model, canvas);
		model.setMainView(view);

		if (isLighthouseConnected) {
			LighthouseView lighthouseView = new LighthouseView(model);
			model.addView(lighthouseView);
		}
	}

	/**
	 * Catches Key inputs and delegates them to the controller.
	 * 
	 * @param e The KeyEvent that triggered the function
	 * 
	 */
	public void keyReleased(KeyEvent e) {
		controller.keyReleased(e);
	}

	/**
	 * Catches mouse clicks and delegates them to the controller.
	 * 
	 * @param e The MouseEvent that triggered the function
	 * 
	 */
	public void mouseClicked(MouseEvent e) {
		controller.mouseClicked(e);
	}

}
