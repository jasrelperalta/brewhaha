import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Game extends JFrame{

    private final int WIDTH = 1000;
    private final int HEIGHT = 600;

    public Game() {
        setTitle("BREW-HAHA!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Add game panel
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        // Display the main menu component



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
        ImageIcon img = new ImageIcon("img/bg.png");
        g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        ImageIcon title = new ImageIcon("img/title.png");
        g.drawImage(title.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        ImageIcon play = new ImageIcon("img/play.png");
        g.drawImage(play.getImage(), 10, 0, this.getWidth(), this.getHeight(), null);
        ImageIcon multi = new ImageIcon("img/multiplayer.png");
        g.drawImage(multi.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        ImageIcon quit = new ImageIcon("img/quit.png");
        g.drawImage(quit.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);



        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update game logic here

        repaint();
    }
}