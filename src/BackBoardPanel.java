import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BackBoardPanel extends JPanel {

    private boolean FlipBoard;
    private BufferedImage image;
    private static final int W = 50;
    private static final int D = 30;
    private static final int OFFSET = 5;

    private static final int X1 = W / 2;
    private static final int Y1 = W / 2;
    private Dimension dim;

// Hidden data members.

    private BackBoard board;

// Exported constructors.

    /**
     * Construct a new bacd ck board panel.
     *
     * @param board BackBoard.
     */
    public BackBoardPanel(BackBoard board) {
        super();
        this.board = board;
        System.out.println(W*BackBoard.BOARD_ROWS/2 +" " +(D*BackBoard.BOARD_ROWS/2));
        dim = new Dimension((W * BackBoard.BOARD_ROWS/2)+100, (D * BackBoard.BOARD_ROWS/2) +60);
        setMinimumSize(dim);
        setPreferredSize(dim);
        setMaximumSize(dim);
    }

    // Exported operations.
    public boolean getEnabled(){
        return this.FlipBoard;
    }

    public void setEnabled(boolean enabled) // True to enable, false to disable
    {
        if (this.FlipBoard != enabled)
        {
            this.FlipBoard = enabled;
            repaint();
        }
    }

    /**
     * Determine the row on the BackBoard that was clicked.
     *
     * @param e Mouse event.
     * @return Row index.
     */
    public int clickToRow
    (MouseEvent e) {
        return e.getY() / D;
    }

    /**
     * Determine the column on the BackBoard that was clicked.
     *
     * @param e Mouse event.
     * @return Column index.
     */
    public int clickToColumn
    (MouseEvent e) {
        return e.getX() / W;
    }


    public void clear(){
        board.clearBoard();
    }

// Hidden operations.

    /**
     * Paint this Back board panel in the given graphics context.
     *
     * @param g Graphics context.
     */

    protected void paintComponent
    (Graphics g) {
        super.paintComponent(g);

        // Clone graphics context.
        Graphics2D g2d = (Graphics2D) g.create();

        // Turn on antialiasing.
        g2d.setRenderingHint
                (RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw spots.
        Ellipse2D.Double ellipse = new Ellipse2D.Double();
        ellipse.width = W-10;
        ellipse.height = D-5;

        URL resource = getClass().getResource("small.jpg");
        try {
            image = ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(image, 3, 4, this);

        synchronized (board) {
            for (int r = 0; r < BackBoard.BOARD_ROWS; ++r) {
                MyStack<Piece> location = board.getSpot(r);
                int counter = 0;
                while (!location.isEmpty()) {
                    Piece temp = location.pop();
                    Color color = temp.getColor();
                    if (color != null) {
                        ellipse.x = (r % 12) * W + OFFSET;
                        if (FlipBoard){
                            if (r >= 12) {
                                if (counter >= 6)
                                    ellipse.y = counter % 6 * ellipse.height + ellipse.height / 2;
                                else
                                    ellipse.y = counter * ellipse.height;

                            } else {
                                if (counter >= 6)
                                    ellipse.y = (dim.getHeight() - D) - counter % 6 * ellipse.height - ellipse.height / 2;
                                else
                                    ellipse.y = (dim.getHeight() - D) - counter * ellipse.height;
                            }

                        }
                        else {
                            if (r >= 12) {
                                if (counter >= 6)
                                    ellipse.y = (dim.getHeight() - D) - counter % 6 * ellipse.height - ellipse.height / 2;
                                else
                                    ellipse.y = (dim.getHeight() - D) - counter * ellipse.height;
                            } else {
                                if (counter >= 6)
                                    ellipse.y = counter % 6 * ellipse.height + ellipse.height / 2;
                                else
                                    ellipse.y = counter * ellipse.height;
                            }
                        }
                        g2d.setColor(color);
                        g2d.fill(ellipse);
                        g2d.setColor(Color.BLACK);
                        g2d.draw(ellipse);
                    }
                    counter+=1;
                }

            }
        }
    }
}
