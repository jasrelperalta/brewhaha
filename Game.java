import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame {

    private final int WIDTH = 600;
    private final int HEIGHT = 800;

    public Game() {
        setTitle("2D Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Add game panel
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Game();
        });
    }
}

class GamePanel extends JPanel implements ActionListener {

    private Timer timer;

    public GamePanel() {
        setFocusable(true);
        setBackground(Color.WHITE);

        // Initialize game objects and variables here

        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw game objects and graphics here

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update game logic here

        repaint();
    }
}