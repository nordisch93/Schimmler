package schimmler;

import java.util.ArrayList;

import acm.graphics.*;
import schimmler.Model.Block.Fragment;

public class Model {
	private Board board;

	public int[][] getBoard(){
		return board.getSquares();
	}
	
	public Model() {
		board = new Board();
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

			Block[] blocks = new Block[5];
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
			int z = 0;
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

		public boolean moveBlock(Block block, int direction) {
			int deltaX = 0;
			int deltaY = 0;
			switch (direction) {
			case 0: // up
				deltaY = -1;
				break;
			case 1: // right
				deltaX = 1;
				break;
			case 2: // down
				deltaY = 1;
				break;
			case 3: // left
				deltaX = -1;
				break;
			default:
				break;
			}

			// check if block can be moved
			if (block.getY() > 3 || block.getX() > 1) {
				// can't move block off board
				return false;
			}
			for (Fragment f : block.fragments) {
				int fragmentX = block.getX() + f.x;
				int fragmentY = block.getY() + f.y;

				if (squares[fragmentX + deltaX][fragmentY + deltaY] != 0
						|| squares[fragmentX + deltaX][fragmentY + deltaY] != block.type) {
					// move is blocked by other blocks
					return false;
				}
			}
			// actually move the block
			for (Fragment f : block.fragments) {
				squares[block.getX() + f.x][block.getY()] = 0;
			}

			block.setY(block.getY() + 1);

			for (Fragment f : block.fragments) {
				squares[block.getX() + f.x + deltaX][block.getY() + deltaY] = block.type;
			}
			return true;
		}

	}
}
