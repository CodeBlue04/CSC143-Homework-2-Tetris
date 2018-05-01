import java.awt.*;

/**
 * One Square on our Tetris Grid or one square in our Tetris game piece
 * 
 * @author Colby Easton, Max Sutters
 */
public class Square {
	//Building block for L-shape and grid. NOT SQUARE PIECE.
	private Grid grid; // the environment where this Square is

	private int row, col; // the grid location of this Square

	private boolean ableToMove; // true if this Square can move

	private Color color; // the color of this Square

	// possible move directions are defined by the Game class

	// dimensions of a Square
	public static final int WIDTH = 20;

	public static final int HEIGHT = 20;

	/**
	 * Creates a square
	 * 
	 * @param g
	 *            the Grid for this Square
	 * @param row
	 *            the row of this Square in the Grid
	 * @param col
	 *            the column of this Square in the Grid
	 * @param c
	 *            the Color of this Square
	 * @param mobile
	 *            true if this Square can move
	 * 
	 * @throws IllegalArgumentException
	 *             if row and col not within the Grid
	 */
	public Square(Grid g, int row, int col, Color c, boolean mobile) {
		if (row < 0 || row > Grid.HEIGHT - 1)
			throw new IllegalArgumentException("Invalid row =" + row);
		if (col < 0 || col > Grid.WIDTH - 1)
			throw new IllegalArgumentException("Invalid column  = " + col);

		// initialize instance variables
		grid = g;
		this.row = row;
		this.col = col;
		color = c;
		ableToMove = mobile;
	}

	/**
	 * Returns the row for this Square
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column for this Square
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Returns true if this Square can move 1 spot in direction d
	 * 
	 * @param direction
	 *            the direction to test for possible move
	 */
	public boolean canMove(Direction direction) {
		if (!ableToMove) {
			return false;
		}
		
		boolean move = true;
		// if the given direction is blocked, we can't move
		// remember to check the edges of the grid
		switch (direction) {
		case DOWN:
			if (row == (Grid.HEIGHT - 1) || grid.isSet(row + 1, col))
				move = false;
			break;
		case UP: // only important for rotation
			if (row == 0 || grid.isSet(row - 1, col)) {
				move = false;
			}

		// currently doesn't support checking LEFT or RIGHT // DONE
		// MODIFY so that it correctly returns if it can move left or right // AND DONE
		case LEFT:
			if (col == 0 || grid.isSet(row, col -1))
				move = false;
			break;
		case RIGHT:
			if (col == Grid.WIDTH - 1 || grid.isSet(row, col +1))
			move = false;
			break;
		}
		return move;
	}

	/**
	 * moves this square in the given direction if possible.
	 * 
	 * The square will not move if the direction is blocked, or if the square is
	 * unable to move.
	 * 
	 * If it attempts to move DOWN and it can't, the square is frozen and cannot
	 * move anymore
	 * 
	 * @param direction
	 *            the direction to move
	 */
	public void move(Direction direction) {
		if (canMove(direction)) {
			switch (direction) {
				case UP:
					row--;
					break;
				case DOWN:
					row++;
					break;
				case LEFT:
					col--;
					break;
				case RIGHT:
					col++;
					break;
				case DROP:
					while (this.canMove(Direction.DOWN)) {
						this.move(Direction.DOWN);
					}
					break;
			}
		}
	}

    /**
     * Determines if the square's rotated position is legal in context of the filled squares on the board
     * TODO: Check to see if blacked-out pieces problem is solved
     */
    public boolean canRotate(int refRow, int refCol, int colVar, int rowVar) {
        int currentCol = this.getCol();
        int currentRow = this.getRow();
        int newRow = (refRow + rowVar);
        int newCol = (refCol + colVar);
        int Col;
        int Row;

        //Border check
        if (newRow < 0 || newRow > grid.HEIGHT - 1 || newCol < 0 || newCol > grid.WIDTH-1) {
            return false;
        }

        // Quadrant 4
        if (rowVar < 0 && colVar < 0) {
            Row = currentRow;
            Col = currentCol;
            for (Col = currentCol; Col > newCol; Col--) {
                Square sQ4C = new Square(grid, Row, Col, Color.WHITE, true);
                if (!sQ4C.canMove(Direction.LEFT)) {
                    return false;
                }
                for (Row = currentRow; Row > newRow; Row--) {
                    Square sQ4R = new Square(grid, Row, newCol, Color.WHITE, true);
                    if (!sQ4R.canMove(Direction.UP)) {
                        return false;
                    }
                }
            }
        }
        // Quadrant 3
        if (rowVar < 0 && colVar > 0) {
            Row = currentRow;
            Col = currentCol;
            for (Row = currentRow; Row > newRow; Row--) {
                Square sQ3R = new Square(grid, Row, Col, Color.WHITE, true);
                if (!sQ3R.canMove(Direction.UP)) {
                    return false;
                }
                for (Col = currentCol; Col < newCol; Col++) {
                    Square sQ3C = new Square(grid, newRow, Col, Color.WHITE, true);
                    if (!sQ3C.canMove(Direction.RIGHT)) {
                        return false;
                    }
                }
            }
        }
        // Quadrant 2
        if (rowVar > 0 && colVar > 0) {
            Row = currentRow;
            Col = currentCol;
            for (Col = currentCol; Col < newCol; Col++) {
                Square sQ2C = new Square(grid, Row, Col, Color.WHITE, true);
                if (!sQ2C.canMove(Direction.RIGHT)) {
                    return false;
                }
                for (Row = currentRow; Row < newRow; Row++) {
                    Square sQ2R = new Square(grid, Row, newCol, Color.WHITE, true);
                    if (!sQ2R.canMove(Direction.DOWN)) {
                        return false;
                    }
                }
            }
        }
        // Quadrant 1
        if (rowVar > 0 && colVar < 0) {
            Row = currentRow;
            Col = currentCol;
            for (Row = currentRow; Row < (refRow + rowVar); Row++) {
                Square sQ1R = new Square(grid, Row, Col, Color.WHITE, true);
                if (!sQ1R.canMove(Direction.DOWN)) {
                    return false;
                }
                for (Col = currentCol; Col < (refCol + colVar); Col--) {
                    Square sQ1C = new Square(grid, newRow, Col, Color.WHITE, true);
                    if (!sQ1C.canMove(Direction.LEFT)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

	/**
	 * Changes the color of this square
	 * 
	 * @param c
	 *            the new color
	 */
	public void setColor(Color c) {
		color = c;
	}

	/**
	 * Gets the color of this square
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Draws this square on the given graphics context
	 */
	public void draw(Graphics g) {

		// calculate the upper left (x,y) coordinate of this square
		int actualX = Grid.LEFT + col * WIDTH;
		int actualY = Grid.TOP + row * HEIGHT;
		g.setColor(color);
		g.fillRect(actualX, actualY, WIDTH, HEIGHT);
		// black border (if not empty)
		if (!color.equals(Grid.EMPTY)) {
			g.setColor(Color.BLACK);
			g.drawRect(actualX, actualY, WIDTH, HEIGHT);
		}
	}
}