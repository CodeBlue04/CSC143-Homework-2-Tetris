import java.awt.*;

public interface Piece {

    /**
     * Draws a piece on the given Graphics context
     */
    public void draw(Graphics g);

    /**
     * Moves a piece if possible. Freeze a piece if it cannot move down
     * anymore
     *
     * @param direction
     *            the direction to move
     */
    public void move(Direction direction);

    /**
     * Rotates a piece if possible.
     */
    public void rotate(int refRow, int refCol);

    /**
     * Returns the (row,col) grid coordinates occupied by a Piece
     *
     * @return an Array of (row,col) Points
     */
    public Point[] getLocations();

    /**
     * Return the color of a piece
     */
    public Color getColor();

    /**
     * Returns if this piece can move in the given direction
     *
     */
    public boolean canMove(Direction direction);

}
