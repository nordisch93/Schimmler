package schimmler;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Controller {
	
	private Model model;
	
	public Controller(Model model) {
		this.model = model;
	}
	
	public boolean setSelection(int BlockId) {
		model.setCurrentSelection(BlockId);		
		return true;
	}
	
	public boolean moveBlock(int direction) {
		int currentBlock = model.getCurrentSelection();
		if(currentBlock == 0)
			return false;
		else
			return model.moveBlock(currentBlock, direction);
	}
	
	public void mouseClicked(MouseEvent e) {
		int clickResult = model.getClickedBlock(e.getX(), e.getY());
		switch(clickResult) {
		case -1:
			break;
		case 0:
			setSelection(0);
			break;
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
			setSelection(clickResult);
			break;
		default:
			throw(new RuntimeException("Illegal return value on View.getClickedBlock"));				
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			moveBlock(0);
			break;
		case KeyEvent.VK_S:				
		case KeyEvent.VK_DOWN:
			moveBlock(2);
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			moveBlock(3);
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			moveBlock(1);
			break;
		default:
			return;	
		}
	}
	
	
}
