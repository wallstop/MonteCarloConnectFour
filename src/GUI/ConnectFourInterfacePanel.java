package GUI;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ConnectFourInterfacePanel extends JPanel implements Runnable
{

    // Drawing variables
    private final BufferedImage background, top, overlays;

    private final Color darkBlue = new Color(48, 92, 130);
    private final Color lightBlue = new Color(144, 177, 204);
    private final Color lightGreen = new Color(179, 255, 215);
    private final Color darkGreen = new Color(56, 182, 115);
    private final Color lightYellow = new Color(200, 200, 100);
    private final Color red = new Color(200, 64, 64);
    private final Color gray = new Color(78, 61, 61);
    private final Color lightGray = new Color(150, 150, 150);

    // Grid variables
    public final int width, height, columns, columnWidth;

    // Overloaded constructor
    public ConnectFourInterfacePanel(int panelWidth, int panelHeight, int columns)
    {
        // Init Java2D layer
        // Create the canvas in the JPanel
        int windowWidth = panelWidth;
        int windowHeight = panelHeight;
        // Background
        this.background = new BufferedImage(windowWidth, windowHeight, BufferedImage.OPAQUE);
        // Game tiles
        this.top = new BufferedImage(windowWidth, windowHeight, BufferedImage.TRANSLUCENT);
        // User overlays
        this.overlays = new BufferedImage(windowWidth, windowHeight, BufferedImage.TRANSLUCENT);

        this.width = panelWidth;
        this.height = panelHeight;
        this.columns = columns;
        this.columnWidth = width / columns;
        initBoard();
    }

    // Draw board for user to click on
    private void initBoard()
    {
        Graphics2D g2d = top.createGraphics();

        g2d.setColor(lightGray);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(lightBlue);
        BasicStroke b = new BasicStroke(3f);
        g2d.setStroke(b);
        for(int i = 0; i < columns; i++)
        {
            g2d.drawLine(width / columns * (i + 1), 0, width / columns * (i + 1), height);
        }
    }

    // Thread running - not certain what this method should contain
    @Override
    public void run()
    {
        repaint();
    }

    // JPanel/Canvas command that draws being overwritten here
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, null);
        g.drawImage(top, 0, 0, null);
        g.drawImage(overlays, 0, 0, null);

        g.dispose();
    }

    // Redraw all graphics
    public void resetBoard()
    {
        initBoard();
        repaint();
    }

    // Erase any overlays on the board
    public void eraseOverlays()
    {
        Graphics2D g2d = overlays.createGraphics();
        Composite comp = g2d.getComposite();
        // Set the composite to clear, in order to 'erase'
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, width, height);
        g2d.setComposite(comp);
    }

    // Highlight a column the user is mousing over
    public void highlightColumn(int column)
    {
        Graphics2D g2d = overlays.createGraphics();
        BasicStroke s = new BasicStroke(1f);
        g2d.setStroke(s);
        g2d.setColor(lightYellow);

        int x = width / columns * column - (width / columns / 2);
        g2d.drawLine(x, 10, x, height / 6);

        repaint();
    }

    // Fill a rectangle, incremented slightly SE and cropped to fit
    public void paintMove(int column, int row, boolean isPlayer)
    {
        Color c = isPlayer ? Color.GREEN : Color.RED;
        Graphics2D g2d = top.createGraphics();
        g2d.setColor(c);

        int d = 91;
        int space = 5;
        int x = columnWidth * column + 5;
        int y = getHeight() - ((d + space) * row) - space;
        System.out.printf("Drawing move @%d, %d a request for %d, %d.\n", x, y, column, row);
        g2d.fillOval(x, y, d, d);
        repaint();
    }
}
