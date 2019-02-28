package schimmler;

import java.util.ArrayList;

//import acm.graphics.*;
import schimmler.Model.Block.Fragment;

public class Model {
	private Board board;
	private int currentSelection = 0;
	private ArrayList<View> views;

	public Model() {
		this.board = new Board();
		this.views = new ArrayList<View>();
	}
	
	
	public int[][] getBoard(){
		return board.getSquares();
	}

	public int getCurrentSelection() {
		return currentSelection;
	}

	public void setCurrentSelection(int currentSelection) {
		this.currentSelection = currentSelection;
		for(View v : views) {
			v.update(board.getSquares(), currentSelection);
		}
	}

	public void moveBlock(int blockId, Direction direction) {
		boolean success = board.moveBlock(blockId, direction);
		if (success) {
			for (View v : views) {
				v.update(board.getSquares(), currentSelection);
			}
		}
		return;
	}
	

	public int getClickedBlock(int x, int y) {
		return views.get(0).getClickedBlock(x,y);
	}
	
	public void addView(View view) {
		views.add(view);
		for (View v : views) {
			v.update(board.getSquares(), currentSelection);
		}
	}
	
	class Block {
		int type;
		int x;
		int y;
		ArrayList<Fragment> fragments;

		public Block(int type, int x, int y) {
			this.type = type;
			this.x = x;
			this.y = y;
			fragments = new ArrayList<Fragment>();
			switch (type) {
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
			return type;
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
				squares[block.getX() + f.x][block.getY() + f.y] = block.type;
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
						&& squares[fragmentX + deltaX][fragmentY + deltaY] != blocks[blockId-1].type) {
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
				squares[blocks[blockId-1].getX() + f.x][blocks[blockId-1].getY() + f.y] = blocks[blockId-1].type;
			}
			return true;
		}

	}
}
