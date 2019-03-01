package schimmler;

import acm.program.*;
import acm.graphics.*;

import java.awt.event.*;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Game extends GraphicsProgram {

	Controller controller;
	Model model;

	boolean isLighthouseConnected = true;

	/**
	 * Initializes the program by adding eventListeners.
	 */
	public void init() {
		addMouseListeners();
		addKeyListeners();

		add(new JButton("Toggle Lighthouse"), SOUTH);
		add(new JButton("New Game"), SOUTH);
		addActionListeners();
	}

	/**
	 * Initializes instances of a model, a controller and several views.
	 */
	public void run() {

		this.model = new Model();
		this.controller = new Controller(model);

		GCanvas canvas = getGCanvas();
		DesktopView view = new DesktopView(model, canvas);
		model.setDesktopView(view);

		if (isLighthouseConnected) {
			LighthouseView lighthouseView = new LighthouseView(model);
			model.setLighthouseView(lighthouseView);
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

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Toggle Lighthouse")) {
			isLighthouseConnected = !isLighthouseConnected;
			if(isLighthouseConnected) {
				LighthouseView lighthouseView = new LighthouseView(model);
				model.setLighthouseView(lighthouseView);				
			}
			else {
				model.removeLighthouseView();
			}
		}
		else controller.actionPerformed(e);
	}

}
