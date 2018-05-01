import java.awt.*;
import java.util.Random;

/**
 * Manages the game Tetris. Keeps track of the current piece and the grid.
 * Updates the display whenever the state of the game has changed.
 * 
 * @author CSC 143
 */
public class Game {
//Contains code to track pieces on board. contains public method to play the game.
	private Grid grid; // the grid that makes up the Tetris board

	private Tetris display; // the visual for the Tetris game

	private AbstractPiece piece; // the current piece that is dropping // TODO: Don't act like an abstract piece can be instantiated

	private boolean isOver; // has the game finished?

	/**
	 * Creates a Tetris game
	 * 
	 * @param display Tetris // MAX
	 *            the display for Tetris
	 */
	public Game(Tetris display) {
		grid = new Grid();
		this.display = display;
		piece = new ZShape(1, Grid.WIDTH / 2 - 1, grid);
		isOver = false;
	}

	/**
	 * Draws the current state of the game
	 * 
	 * @param g
	 *            the Graphics context on which to draw
	 */
	public void draw(Graphics g) {
		grid.draw(g);
		if (piece != null) {
			piece.draw(g);
		}
	}

	/**
	 * Moves the piece in the given direction
	 * 
	 * @param direction the
	 *            direction to move // MAX
	 */
	public void movePiece(Direction direction) {
		if (piece != null) {
			if (direction == Direction.DROP) {
				while (piece.canMove(Direction.DOWN)) {
					piece.move(Direction.DOWN);
				}
//				updatePiece();
//
			} else {
				piece.move(direction);
			}
		}
		updatePiece();
		display.update();
		grid.checkRows();
	}

	public void rotate(Direction direction) {
		int refRow = piece.square[1].getRow();
		int refCol = piece.square[1].getCol();
		if (direction != Direction.ROTATE) {
			isOver = true;
		} else {
			piece.rotate(refRow, refCol);
		}
	}

	/**
	 * Returns true if the game is over
	 */
	public boolean isGameOver() {
		// game is over if the piece occupies the same space as some non-empty
		// part of the grid. Usually happens when a new piece is made
		if (piece == null) {
			return false;
		}

		// check if game is already over
		if (isOver) {
			return true;
		}

		// check every part of the piece
		Point[] p = piece.getLocations();
		for (int i = 0; i < p.length; i++) {
			if (grid.isSet((int) p[i].getX(), (int) p[i].getY())) {
				isOver = true;
				return true;
			}
		}
		return false;
	}

	/** Updates the piece */
	private void updatePiece() {

		if (piece == null) {
			piece = (AbstractPiece) randomPiece(); // TODO: Maybe remove casting, idk
		}

		// set Grid positions corresponding to frozen piece
		// and then release the piece
		else if (!piece.canMove(Direction.DOWN)) {
			// modifying to allow sliding into place
            if (!piece.canMove(Direction.LEFT) && !piece.canMove(Direction.RIGHT)) {
                Point[] p = piece.getLocations();
                Color c = piece.getColor();
                for (int i = 0; i < p.length; i++) {
                    grid.set((int) p[i].getX(), (int) p[i].getY(), c);
                }
                piece = null;
                updatePiece();
            }
		}
	}

    /** Returns random Piece object from possible shapes
     *
     */
    private Object randomPiece() {
        Random randy = new Random();
        int caseNumber = randy.nextInt(7) + 1; 
        // starting column. Starting row is 1
        int startCol = Grid.WIDTH / 2 - 1;
        switch (caseNumber) {
            case 1: return new ZShape(1, startCol, grid);
            case 2: return new SquareShape(1, startCol, grid);
            case 3: return new JShape(1, startCol, grid);
            case 4: return new TShape(1, startCol, grid);
            case 5: return new SShape(1, startCol, grid);
            case 6: return new BarShape(1, startCol, grid);
            case 7: return new LShape(1, startCol, grid);
        }
        return new Object(); // LShape(1, Grid.WIDTH / 2 - 1, grid);
    }

}