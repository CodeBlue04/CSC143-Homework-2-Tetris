/**
 * Handles events for the Tetris Game.  User events (key strokes) as well as periodic timer
 * events.
 * 
 * @author CSC 143
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//Reacts to events (i.e. keystrokes, timers), DO NOT ADD METHOD TO THIS CLASS, must modify one existing method.
public class EventController extends KeyAdapter implements ActionListener {

	private Game game; // current game: grid and current piece
	private Timer timer;

	private static final double PIECE_MOVE_TIME = 0.7; // wait 0.7 s every time
														// the piece moves down
														// increase to slow it
														// down

	private boolean gameOver;

	/**
	 * Creates an EventController to handle key and timer events.
	 * 
	 * @param game
	 *            the game this is controlling
	 */
	public EventController(Game game) {
		this.game = game;
		gameOver = false;
		double delay = 1000 * PIECE_MOVE_TIME; // in milliseconds
		timer = new Timer((int) delay, this);
		timer.setCoalesce(true); // if multiple events pending, bunch them together
		// 1 event
		timer.start();
	}

	/**
	 * Responds to special keys being pressed.
	 * 
	 * Currently just responds to the space key and the q(uit) key
	 */
	public void keyPressed(KeyEvent e) {
		// if 'Q', quit the game
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			timer.stop();
			((JFrame) e.getSource()).dispose();
		}
		// int remRows = Grid.HEIGHT;
		if (!gameOver) {
			switch (e.getKeyCode()) {
			// special operations
            case KeyEvent.VK_SPACE:
                handleMove(Direction.DROP);
                break;

			// translating left and right
			case KeyEvent.VK_LEFT:
				handleMove(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				handleMove(Direction.RIGHT);
				break;
			// HANDLE other keystrokes here
			case KeyEvent.VK_DOWN:
				handleMove(Direction.ROTATE);
				break;
			}
		}
	}

	/** Updates the game periodically based on a timer event */
	public void actionPerformed(ActionEvent e) {
		handleMove(Direction.DOWN);
	}

	/**
	 * Update the game by moving in the given direction
	 */
	private void handleMove(Direction direction) {
		if (direction == Direction.ROTATE) {
			game.rotate(Direction.ROTATE);
		} else {
			game.movePiece(direction);
			gameOver = game.isGameOver();
			if (gameOver)
				timer.stop();
		}
	}
}