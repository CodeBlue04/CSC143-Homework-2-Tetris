import java.awt.*;
/**
 * This abstract class controls the behavior of the game pieces, with the exception
 * of SquareShape's rotate method.
 * 
 * @author Colby Easton, Max Sutters
 *
 */

public class AbstractPiece implements Piece {
    //One game piece. 4 squares, L shape of square array values.
    protected boolean ableToMove; // can this piece move

    protected Square[] square; // the squares that make up this piece

    // Made up of PIECE_COUNT squares
    protected Grid grid; // the board this piece is on

    // number of squares in one Tetris game piece // TODO: Add PIECE_COUNT getter
    protected static final int PIECE_COUNT = 4;

    /**
     * Draws a piece on the given Graphics context
     */
    public void draw(Graphics g) {
        for (int i = 0; i < PIECE_COUNT; i++) {
            square[i].draw(g);
        }
    }

    /**
     * Moves a piece if possible. Freeze a piece if it cannot move down
     * anymore
     *
     * @param direction
     *            the direction to move
     */
    public void move(Direction direction) {
        if (canMove(direction)) {
            for (int i = 0; i < PIECE_COUNT; i++)
                square[i].move(direction);
        }
        // if the piece couldn't move, see if because b/c at the bottom
        else if (direction == Direction.DOWN) {
            ableToMove = false;
        }
    }

    /**
     * Rotates a piece if possible.
     */
    public void rotate(int refRow, int refCol) {
        //First loop sends to Square.canRotate method
        for (int i = 0; i < 4; i++) {
            int curCol = square[i].getCol();
            int curRow = square[i].getRow();
            int newCol = refRow + refCol - curRow;
            int newRow = refRow - refCol + curCol;
            int colVar = curCol - newCol;
            int rowVar = curRow - newRow;
            if(newCol > grid.WIDTH-1 || newCol < 0 ) {
                return;
            }
            Color c = square[i].getColor();
            if (!square[i].canRotate(refRow, refCol, colVar, rowVar)) {

                return;
            }
        }

        //Second loop rotates pieces
        for (int k = 0; k < 4; k++) {
            int curCol = square[k].getCol();
            int curRow = square[k].getRow();
            int newCol = refRow + refCol - curRow;
            int newRow = refRow - refCol + curCol;
            Color c = square[k].getColor();
            square[k] = new Square(grid, newRow, newCol, c, true);
        }
    }

    /**
     * Returns the (row,col) grid coordinates occupied by a Piece
     *
     * @return an Array of (row,col) Points
     */
    public Point[] getLocations() {
        Point[] points = new Point[PIECE_COUNT];
        for (int i = 0; i < PIECE_COUNT; i++) {
            points[i] = new Point(square[i].getRow(), square[i].getCol());
        }
        return points;
    }

    /**
     * Return the color of a piece
     */
    public Color getColor() {
        // all squares of this piece have the same color
        return square[0].getColor();
    }

    /**
     * Returns if a piece can move in the given direction TODO: project a false square to canRotate to check
     *
     */
    public boolean canMove(Direction direction) {
        if (!ableToMove) {
            return false;
        }

        // Each square must be able to move in that direction
        boolean answer = true;
        for (int i = 0; i < PIECE_COUNT && answer; i++) {
            answer = square[i].canMove(direction); // && answer
        }

        return answer;
    }
}
