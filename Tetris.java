/**
 * Create and control the game Tetris.
 * 
 * @author Colby Easton, Max Sutters
 *
 */

import javax.swing.*;
import java.awt.*;

public class Tetris extends JPanel {
	// This is the class that handles graphical display, main method, DO NOT MODIFY!
	private Game game;

	/**
	 * Sets up the parts for the Tetris game, display and user control
	 */
	public Tetris() {
		game = new Game(this);
		JFrame f = new JFrame("The Tetris Game");
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // WindowConstants instead of JFrame? Discuss. -MAXS
		f.setSize(400, 550);
		f.setVisible(true);
		EventController ec = new EventController(game);
		f.addKeyListener(ec);
		setBackground(Color.YELLOW);
	}

	/**
	 * Updates the display
	 */
	public void update() {
		repaint();
	}

	/**
	 * Paint the current state of the game
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.draw(g);
		if (game.isGameOver()) {
			g.setFont(new Font("Palatino", Font.BOLD, 41));
			g.setColor(Color.BLACK);
			g.drawString("GAME OVER", 74, 300);

			g.setFont(new Font("Palatino", Font.PLAIN, 40));
			g.setColor(Color.WHITE);
			g.drawString("GAME OVER", 75, 300);

		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Tetris();
			}
		});
	}

}