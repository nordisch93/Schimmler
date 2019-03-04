package schimmler;

import java.util.ArrayList;
import java.util.Arrays;


//import acm.graphics.*;
import schimmler.Model.Block.Fragment;

public class Model {
	private GameState gamestate = GameState.GAMESTATE_START_SCREEN;

	private Board board;
	private int currentSelection = 0;
	private DesktopView desktopView;
	private LighthouseView lighthouseView;
	private final int solvedBoard[][] = {{2,2,5,5,0,0},{2,0,0,5,1,1},{3,0,0,4,1,1},{3,3,4,4,0,0}};
	private int moveCount = 0;

	/**
	 * Standard constructor, creates a news board.
	 * 
	 */
	public Model() {
		this.board = new Board();
	}
	
	/**
	 * Returns an int array with the current state of the model's board.
	 * 
	 * @return The state of the model's board.
	 */
	public int[][] getBoardState(){
		return board.getSquares();
	}

	/**
	 * Returns the Id of the currently selected block.
	 * 
	 * @return Id of the currently slected block
	 */
	public int getCurrentSelection() {
		return currentSelection;
	}

	/**
	 * Set the current selection to the ID of a block.
	 * 
	 * @param currentSelection	Id of the block that is to be selected.
	 */
	public void setCurrentSelection(int currentSelection) {
		this.currentSelection = currentSelection;
		updateViews();	
	}	
	
	/**
	 * Returns the current gamestate.
	 * @return	{@code GAMESTATE_START_SCREEN}	if the program was just started
	 * 			{@code GAMESTATE_RUNNING}		if a game is running
	 * 			{@code GAMESTATE_END_SCREEN}	if a game was finished
	 */
	public GameState getGamestate() {
		return gamestate;
	}

	/**
	 * Tries to move a block on the board and checks whether the move solved the puzzle.
	 * 
	 * If the move was valid and ended the game, the endGame routine is called and the views are updated.
	 * 
	 * @param blockId		The Id of the block that is to be moved, must be element of {1,2,3,4,5}
	 * @param direction		The direction in which the block is to be moved
	 * 
	 * @throws IllegalArgumentException if blockId was invalid (< 1 or > 5)  
	 */
	public void moveBlock(int blockId, Direction direction) {
		if(blockId < 1 || blockId > 5)
			throw(new IllegalArgumentException("called moveBlock with invalid BlockId" + Integer.toString(blockId)));
		boolean success = board.moveBlock(blockId, direction);
		if (success) {
			moveCount++;
			if(Arrays.deepEquals(board.getSquares(), solvedBoard)){
				endGame();
			}
			updateViews();		
		}
		return;
	}

	/**
	 * Retrieves the Id of the block that was clicked from the Desktop view.
	 * 
	 * @param x		The x-coordinate of the mouseClickEvent
	 * @param y		The y-coordinate of the mouseClickEvent
	 * 
	 * @return -1					 			if click was outside of board
	 * 			0								if an empty field was clicked
	 * 			BlockId of the clicked Block	else
	 *		
	 */
	public int getClickedBlock(int x, int y) {
		return desktopView.getClickedBlock(x,y);
	}
	
	/**
	 * Sets the LighthouseView of the model and updates it.
	 * 
	 * @param view		The new LighthouseView
	 */
	public void setLighthouseView(LighthouseView view) {
		this.lighthouseView = view;
		lighthouseView.update();
	}
	
	/**
	 * Removes the current lighthouseView from the model.
	 */
	public void removeLighthouseView() {
		this.lighthouseView = null;
	}

	/**
	 * returns the desktopView of the model.
	 * 
	 * @return	the DesktopView of the model
	 */
	public DesktopView getMainView() {
		return desktopView;
	}

	/**
	 * Sets the desktopViiew of the model and updates it.
	 * 
	 * @param view the new desktopView
	 */
	public void setDesktopView(DesktopView view) {
		this.desktopView = view;
		desktopView.update();
	}

	/**
	 * The class represents one of the playing blocks that are moved around on the board.
	 * 
	 * It consists of 3 or 4 fragments, which each represent a single square on the playing board, 
	 * which implement their own collision checks and move methods.
	 * 
	 * @author Thomas Fuhrt, Thomas Schubert
	 *
	 */
	class Block {
		/**
		 * The Id of the block, which specifies both the shape of the block and gives it a unique identifier.
		 */
		int blockId;
		/**
		 * The position coordinates on the playing board, using a coordinate system with its origin at the top right
		 * corner of the board. We have (0 <= x <= 2) and (0 <= y <= 4).
		 */
		int x;
		int y;
		/**
		 * The list of fragments that the block consists of. A block has 4 fragments if its Id is 1 (the square block)
		 * or 3 fragments else.
		 */
		ArrayList<Fragment> fragments;

		/**
		 * Constructs a new block of with a given Id at a given position.
		 * 
		 * @param blockId		The Id of the block. (must be 1 <= blockId <= 5=
		 * @param x				The x-coordinate (must be 0 <= x <= 2)
		 * @param y				The y-coordinate (must be 0 <= y <= 4)
		 */
		public Block(int blockId, int x, int y) {
			if(blockId < 1 || blockId > 5) {
				throw(new IllegalArgumentException(""));
			}
			
			this.blockId = blockId;
			this.x = x;
			this.y = y;
			fragments = new ArrayList<Fragment>();
			switch (blockId) {
			case 1:		//square block
				fragments.add(new Fragment(0, 0));
				fragments.add(new Fragment(1, 0));
				fragments.add(new Fragment(0, 1));
				fragments.add(new Fragment(1, 1));
				break;
			case 2://upper left
				fragments.add(new Fragment(0, 0));
				fragments.add(new Fragment(1, 0));
				fragments.add(new Fragment(0, 1));				
				break;
			case 3://upper right
				fragments.add(new Fragment(0, 0));
				fragments.add(new Fragment(1, 0));
				fragments.add(new Fragment(1, 1));
				break;
			case 4://lower right
				fragments.add(new Fragment(1, 0));
				fragments.add(new Fragment(0, 1));
				fragments.add(new Fragment(1, 1));
				break;
			case 5://lower left
				fragments.add(new Fragment(0, 0));
				fragments.add(new Fragment(0, 1));
				fragments.add(new Fragment(1, 1));
				break;
			}
			
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getType() {
			return blockId;
		}

		class Fragment {
			int x;
			int y;

			public Fragment(int x, int y) {
				this.x = x;
				this.y = y;
			}
		}
	}

	class Board {
		private int[][] squares;
		private Block blocks[];

		public Board() {
			squares = new int[4][6];
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 6; y++) {
					squares[x][y] = 0;
				}
			}

			this.blocks = new Block[5];
			//square block
			blocks[0] = new Block(1, 1, 1);
			addBlock(blocks[0]);
			//upper left
			blocks[1] = new Block(2, 0, 0);
			addBlock(blocks[1]);
			//upper right
			blocks[2] = new Block(3, 2, 0);
			addBlock(blocks[2]);
			//lower right
			blocks[3] = new Block(4, 2, 2);
			addBlock(blocks[3]);
			//lower left
			blocks[4] = new Block(5, 0, 2);
			addBlock(blocks[4]);
		}

		public int getSquare(int x, int y) {
			return squares[x][y];
		}

		public void setSquare(int x, int y, int value) {
			squares[x][y] = value;
		}

		private void addBlock(Block block) {
			for (Fragment f : block.fragments) {
				squares[block.getX() + f.x][block.getY() + f.y] = block.blockId;
			}
		}
		
		public int[][] getSquares(){
			return squares;
		}
		
		public boolean moveBlock(int blockId, Direction direction) {
			int deltaX = 0;
			int deltaY = 0;
			switch (direction) {
			case DIRECTION_UP: // up
				deltaY = -1;
				break;
			case DIRECTION_RIGHT: // right
				deltaX = 1;
				break;
			case DIRECTION_DOWN: // down
				deltaY = 1;
				break;
			case DIRECTION_LEFT: // left
				deltaX = -1;
				break;
			default:
				break;
			}

			// check if block can be moved
			if ((blocks[blockId-1].getY() + deltaY > 4 || blocks[blockId-1].getX()+deltaX > 2)||
					(blocks[blockId-1].getY() + deltaY < 0 || blocks[blockId-1].getX()+deltaX < 0))
			{
				// can't move block off board
				return false;
			}
			for (Fragment f : blocks[blockId-1].fragments) {
				int fragmentX = blocks[blockId-1].getX() + f.x;
				int fragmentY = blocks[blockId-1].getY() + f.y;

				if (squares[fragmentX + deltaX][fragmentY + deltaY] != 0
						&& squares[fragmentX + deltaX][fragmentY + deltaY] != blocks[blockId-1].blockId) {
					// move is blocked by other blocks
					return false;
				}
			}
			// actually move the block
			for (Fragment f : blocks[blockId-1].fragments) {
				int fragmentX = blocks[blockId-1].getX() + f.x;
				int fragmentY = blocks[blockId-1].getY() + f.y;
				squares[fragmentX][fragmentY] = 0;
			}

			blocks[blockId-1].setX(blocks[blockId-1].getX() + deltaX);
			blocks[blockId-1].setY(blocks[blockId-1].getY() + deltaY);			

			for (Fragment f : blocks[blockId-1].fragments) {
				squares[blocks[blockId-1].getX() + f.x][blocks[blockId-1].getY() + f.y] = blocks[blockId-1].blockId;
			}
			return true;
		}

		public void reset() {
			squares = new int[4][6];
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 6; y++) {
					squares[x][y] = 0;
				}
			}

			//square block
			blocks[0] = new Block(1, 1, 1);
			addBlock(blocks[0]);
			//upper left
			blocks[1] = new Block(2, 0, 0);
			addBlock(blocks[1]);
			//upper right
			blocks[2] = new Block(3, 2, 0);
			addBlock(blocks[2]);
			//lower right
			blocks[3] = new Block(4, 2, 2);
			addBlock(blocks[3]);
			//lower left
			blocks[4] = new Block(5, 0, 2);
			addBlock(blocks[4]);		
			
			moveCount = 0;
		}

	}

	public void startGame() {
		board.reset();
		currentSelection = 0;
		gamestate = GameState.GAMESTATE_RUNNING;
		updateViews();
	}
	
	private void endGame() {
		gamestate = GameState.GAMESTATE_END_SCREEN;
	}


	private void updateViews() {
		desktopView.update();
		if(lighthouseView != null)
			lighthouseView.update();
	}


	public int getMoveCount() {
		return moveCount;
	}
		
}
