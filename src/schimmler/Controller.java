package schimmler;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * The Controller class handles the users inputs provided by Game
 * and passes them on to the model via the according methods.
 *
 */
public class Controller {

	// Controller needs access to model (MVC pattern)
	private Model model;
	
	/**
	 * Constructs a controller object and provides it with an instance of model.
	 * 
	 * @param model an object instance of model
	 */
	public Controller(Model model) {
		this.model = model;
	}
	
	/**
	 * Sets the model's selected block.
	 * 
	 * @param BlockId the Id of the selected block
	 */
	public void setSelection(int BlockId) {
		model.setCurrentSelection(BlockId);
		return;
	}

	/**
	 * Determines which block is selected an moves it according to the 
	 * chosen direction.
	 * 
	 * @param direction the direction provided by the user's key inputs
	 */
	public void moveBlock(Direction direction) {
		int currentBlock = model.getCurrentSelection();
		if (currentBlock == 0)
			// no block selected
			return;
		else
			// block selected -> move
			model.moveBlock(currentBlock, direction);
	}
	
	/**
	 * Processes the mouse input and determines the block selection.
	 * 
	 * @param e the users mouse click input
	 */
	public void mouseClicked(MouseEvent e) {
		if (model.getGamestate() != GameState.GAMESTATE_RUNNING) {
			// game hasn't started yet
		} else {
			int clickResult = model.getClickedBlock(e.getX(), e.getY());
			switch (clickResult) {
			case -1:
				// irrelevant click
				break;
			case 0:
				// no block selected
				setSelection(0);
				
				break;
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				// block selected
				setSelection(clickResult);
				break;
			default:
				throw (new RuntimeException("Illegal return value on View.getClickedBlock"));
			}
		}
	}

	/**
	 * Moves blocks according to key input. Both WASD and arrow keys work.
	 * 
	 * @param e Key input
	 * 
	 */
	public void keyReleased(KeyEvent e) {
		if (model.getGamestate() != GameState.GAMESTATE_RUNNING) {
			// game isn't running
		} else {
			// game is running
			switch (e.getKeyCode()) {
			// up
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				moveBlock(Direction.DIRECTION_UP);
				break;
			// down
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				moveBlock(Direction.DIRECTION_DOWN);
				break;
			// left
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				moveBlock(Direction.DIRECTION_LEFT);
				break;
			// right
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				moveBlock(Direction.DIRECTION_RIGHT);
				break;
			default:
				// pressed key is irrelevant 
				return;
			}
		}
	}

	/**
	 * Starts new game / resets board.
	 * 
	 * @param e the action event of the pressed JButton
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("New Game")) {
			model.startGame();
		}
	}
}
