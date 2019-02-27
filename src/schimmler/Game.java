package schimmler;

import acm.program.*;
import acm.graphics.*;

import java.awt.event.*;

@SuppressWarnings("serial")
public class Game extends GraphicsProgram {

	View view;
	Controller controller;
	Model model;

	public void run() {

		// initialize eventListeners
		addMouseListeners();
		addKeyListeners();

		boolean desktopView = true;

		this.model = new Model();
		this.controller = new Controller(model);
		

		if (desktopView) {
			GCanvas canvas = getGCanvas();
			this.view = new View(canvas, model.getBoard());

			while(true) {
				
			}

		} else {
			view = new View();
		}

	}
	
	public void keyTyped(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			controller.moveBlock(0);
			break;
		case KeyEvent.VK_S:				
		case KeyEvent.VK_DOWN:
			controller.moveBlock(2);
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			controller.moveBlock(3);
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			controller.moveBlock(1);
			break;
		default:
			return;	
		}
		view.update(model.getBoard(), model.getCurrentSelection());
	}
	
	public void mouseClicked(MouseEvent e) {
		int clickResult = view.getClickedBlock(e.getX(), e.getY());
		switch(clickResult) {
		case -1:
			break;
		case 0:
			controller.setSelection(0);
			view.update(model.getBoard(), model.getCurrentSelection());
			break;
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			controller.setSelection(clickResult);
			view.update(model.getBoard(), model.getCurrentSelection());
			break;
		default:
			throw(new RuntimeException("Illegal return value on View.getClickedBlock"));				
		}
	}

}
