import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CoordinateCalculator {
    private JFrame frame;
    private CoordinatePanel panel;
    private JLabel koordinatenLabel;
    private Point initialClick;

    private final double PIXELS_PER_CM = 37.8;

    public CoordinateCalculator(int frameWidth, int frameHeight, int buttonWidth, int buttonHeight) {
        frame = new JFrame("Koordinaten Rechner");
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        panel = new CoordinatePanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        koordinatenLabel = new JLabel("Koordinaten: ");
        koordinatenLabel.setBounds(10, 10, 300, 20);
        panel.add(koordinatenLabel);

        JButton button = new JButton("Verschieb mich");
        button.setBounds(50, 50, buttonWidth, buttonHeight);  
        panel.add(button);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                displayCoordinates(button);
            }
        });

        button.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = button.getLocation().x;
                int thisY = button.getLocation().y;
                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                button.setLocation(X, Y);
                displayCoordinates(button);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void displayCoordinates(JButton button) {
        Point location = button.getLocation();
        koordinatenLabel.setText("Koordinaten: x = " + location.x + ", y = " + location.y);
    }

    class CoordinatePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            setBackground(Color.WHITE);

            g.setColor(Color.LIGHT_GRAY);
            int width = getWidth();
            int height = getHeight();

            int pixelPerCm = (int) PIXELS_PER_CM;

            for (int x = 0; x < width; x += pixelPerCm) {
                g.drawLine(x, 0, x, height);
            }

            for (int y = 0; y < height; y += pixelPerCm) {
                g.drawLine(0, y, width, y);
            }
        }
    }

    public static void main(String[] args) {
        String frameWidthInput = JOptionPane.showInputDialog(null, "Geben Sie die Breite des Fensters ein (in Pixeln):");
        String frameHeightInput = JOptionPane.showInputDialog(null, "Geben Sie die Höhe des Fensters ein (in Pixeln):");

        String buttonWidthInput = JOptionPane.showInputDialog(null, "Geben Sie die Breite des Buttons ein (in Pixeln):");
        String buttonHeightInput = JOptionPane.showInputDialog(null, "Geben Sie die Höhe des Buttons ein (in Pixeln):");

        try {
            int frameWidth = Integer.parseInt(frameWidthInput);
            int frameHeight = Integer.parseInt(frameHeightInput);
            int buttonWidth = Integer.parseInt(buttonWidthInput);
            int buttonHeight = Integer.parseInt(buttonHeightInput);

            SwingUtilities.invokeLater(() -> new CoordinateCalculator(frameWidth, frameHeight, buttonWidth, buttonHeight));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ungültige Eingabe. Bitte geben Sie eine Zahl ein.");
        }
    }
}

