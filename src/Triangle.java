import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * Created by michael on 17/3/2016.
 */
public class Triangle {

    public Triangle() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private TriangleShape triangleShape;
        private Polygon poly;

        public TestPane() {
            triangleShape = new TriangleShape(
                    new Point2D.Double(50, 0),
                    new Point2D.Double(100, 100),
                    new Point2D.Double(0, 100)
            );

            poly = new Polygon(
                    new int[]{50, 100, 0},
                    new int[]{0, 100, 100},
                    3);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.RED);
            g2d.fill(poly);

            g2d.setColor(Color.GREEN);
            g2d.translate(50, 100);
            g2d.fill(triangleShape);
            g2d.dispose();
        }
    }

    public class TriangleShape extends Path2D.Double {

        public TriangleShape(Point2D... points) {
            moveTo(points[0].getX(), points[0].getY());
            lineTo(points[1].getX(), points[1].getY());
            lineTo(points[2].getX(), points[2].getY());
            closePath();
        }

    }

}
