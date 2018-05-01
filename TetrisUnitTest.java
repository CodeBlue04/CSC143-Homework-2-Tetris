import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

/**
 * Test cases for different elements of the Tetris game
 */
public class TetrisUnitTest {

    /**
     * This test ensures that the Left, Right, and Drop directions function as
     * intended.
     */
    @Test
    public void motionTest() {
        Grid grid = new Grid();
        Color c = Color.MAGENTA;
        // Left test
        Square lefty = new Square(grid, 3, 5, c, true);
        if (lefty.canMove(Direction.LEFT)) {
            lefty.move(Direction.LEFT);
            assertTrue(lefty.getRow() == 3 && lefty.getCol() == 4);
        }
        // Right test
        Square righty = new Square(grid, 3, 5, c, true);
        if (righty.canMove(Direction.RIGHT)) {
            righty.move(Direction.RIGHT);
            assertTrue(righty.getRow() == 3 && righty.getCol() == 6);
        }
        // Drop test
        Square dropsy = new Square(grid, 3, 5, c, true);
        if (dropsy.canMove(Direction.DROP)) {
            dropsy.move(Direction.DROP);
            assertTrue(dropsy.getRow() == 19 && dropsy.getCol() == 5);
        }
    }

    /**
     * This test method ensures that when rows are filled, they are deleted and all
     * other rows are translated down.
     */
    @Test
    public void checkRowsTest() {
        Grid Grid = new Grid();
        Color fullColor = Color.MAGENTA;
        Color emptyColor = Color.WHITE;

        for (int col = 0; col < Grid.WIDTH; col++) {
            Grid.set(19, col, fullColor);
        }
        for (int col = 0; col < Grid.WIDTH; col++) {
            assertTrue(Grid.isSet(19, col));
        }
        Grid.checkRows();
        for (int col = 0; col < Grid.WIDTH; col++) {
            assertFalse(Grid.isSet(19, col));
        }
        // Instantiates "H"-shaped squares layout, removes the large block above and
        // large block below, then
        // checks to make sure the bridge of the H has properly translated down to the
        // bottom of the grid after
        // checkRows
        Grid GridB = new Grid();
        for (int row = 0; row < GridB.HEIGHT; row++) {
            for (int col = 0; col < GridB.WIDTH; col++) {
                GridB.set(row, col, fullColor);
            }
        }
        for (int col = 0; col < GridB.WIDTH; col++) {
            if (col != 4 && col != 5) {
                GridB.set(9, col, emptyColor);
            }
            assertTrue(GridB.isSet(19, 4));
        }
    }

    /**
     * This method checks to make sure that if the piece can't rotate, the canRotate
     * method returns false.
     */
    @Test
    public void checkCanRotate() {
        Grid Grid = new Grid();

        Square sVA = new Square(Grid, 2, 0, Color.MAGENTA, true);
        // Border test to ensure vertical motion along border is accepted
        assertTrue(sVA.canRotate(3, 0, 1, 1));

        Square sVD = new Square(Grid, 2, 0, Color.MAGENTA, true);
        // Border test to show that vertical motion beyond border isn't allowed
        assertFalse(sVD.canRotate(3, 0, 0, -4));

        Square sHA = new Square(Grid, 2, 0, Color.MAGENTA, true);
        // Border test to ensure horizontal motion along border is accepted
        assertTrue(sHA.canRotate(3, 0, 1, 1));

        Square sHD = new Square(Grid, 4, 0, Color.MAGENTA, true);
        // Border test to show that horizontal motion beyond border isn't allowed
        assertFalse(sHD.canRotate(5, 0, -1, 0));

        //Test to check if  piece will rotate into occupied space
        Grid.set(5, 6, Color.MAGENTA);
        Square sFS = new Square(Grid, 4, 5, Color.BLUE, true);
        assertFalse(sFS.canRotate(5, 5,  1, 1));
    }


    /**
     * This method creates a piece, measures its location using assertTrue,
     * then rotates the piece and measures it again to ensure rotation
     * follows intended path.
     */
    @Test
    public void checkRotate() {
        Grid Grid = new Grid();
        Point[] points = new Point[4];

        Square s = new Square(Grid, 5, 6, Color.WHITE, false);
        LShape L = new LShape(5, 5, Grid);
        Point[] p = L.getLocations();

        //Grid positions before rotation
        assertTrue((p[0].getY() == 5) && p[0].getX() == 4);
        assertTrue((p[1].getY() == 5) && p[1].getX() == 5);
        assertTrue((p[2].getY() == 5) && p[2].getX() == 6);
        assertTrue((p[3].getY() == 6) && p[3].getX() == 6);

        L.rotate(5, 5);
        p = L.getLocations();
        //Grid positions after rotation
        assertTrue((p[0].getY() == 6) && p[0].getX() == 5);
        assertTrue((p[1].getY() == 5) && p[1].getX() == 5);
        assertTrue((p[2].getY() == 4) && p[2].getX() == 5);
        assertTrue((p[3].getY() == 4) && p[3].getX() == 6);

    }
}
