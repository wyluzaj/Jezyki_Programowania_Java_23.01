package V1P;

//Utworzyć aplikacje okienkową (Jframe) zawierającą panel rysowania (JPanel). Na tym obszarze należy za pomocą
// myszy rysować prostokąty. Naciskamy przycisk - jeden róg, przejeżdżamy i puszczamy - drugi róg. Każdy z
//prostokątów ma być sterowany nowym wątkiem. Prostokąty mają jechać w prawo aż dotrą do krawędzi.
//Wtedy mają się pojawiać przy lewej krawędzi i jechać dalej.

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ProstokatyAplikation extends JPanel {
    private final List<ColoredRectangle> rectangles = new ArrayList<>();
    private ColoredRectangle currentRect = null;
    private final ThreadLocal<List<Thread>> threads = ThreadLocal.withInitial(ArrayList::new);
    private Point startPoint;

    public ProstokatyAplikation() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                currentRect = new ColoredRectangle(startPoint.x, startPoint.y, 0, 0, getRandomColor());
                rectangles.add(currentRect);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentRect != null) {
                    Thread thread = createMovingRectangleThread(currentRect);
                    threads.get().add(thread);
                    thread.start();
                }
                currentRect = null;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentRect != null) {
                    Point endPoint = e.getPoint();
                    int x = Math.min(startPoint.x, endPoint.x);
                    int y = Math.min(startPoint.y, endPoint.y);
                    int width = Math.abs(endPoint.x - startPoint.x);
                    int height = Math.abs(endPoint.y - startPoint.y);
                    currentRect.setBounds(x, y, width, height);
                    repaint();
                }
            }
        });
    }

    public void clearRectangles() {
        synchronized (rectangles) {
            rectangles.clear();
        }
        repaint();
    }
   // randomowy kolorrek
    private Color getRandomColor() {
        float r = (float) Math.random();
        float g = (float) Math.random();
        float b = (float) Math.random();
        return new Color(r, g, b);
    }
   //watek
    private Thread createMovingRectangleThread(Rectangle rectangle) {
        return new Thread(() -> {
            try {
                while (true) {
                    synchronized (rectangles) {
                        rectangle.x += 1;
                        if (rectangle.x > getWidth()) {
                            rectangle.x = -rectangle.width;
                        }
                    }
                    repaint();
                    Thread.sleep(25);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    //rendering komponentów
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        synchronized (rectangles) {
            for (ColoredRectangle rect : rectangles) {
                g2d.setColor(rect.getColor());
                g2d.fill(rect);
            }
        }
    }
}