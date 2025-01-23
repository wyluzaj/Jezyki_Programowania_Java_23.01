package V1P;

import javax.swing.*;
import java.awt.BorderLayout;

public class Aplikation {
    private final JFrame frame;

    //konstruktor
    Aplikation(String[] args) {
        frame = new JFrame("Moving rectangles");
    }

    // Show
    public void show() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //zamykanie aplikacji po kliknięciu "X"
        frame.setSize(800, 600); // rozmiar okna
        frame.setLocationRelativeTo(null); // centrum

        ProstokatyAplikation drawing = new ProstokatyAplikation();
        frame.add(drawing, BorderLayout.CENTER);

        JButton clearButton = new JButton("Wyczyść");
        clearButton.addActionListener(e -> drawing.clearRectangles());
        frame.add(clearButton, BorderLayout.SOUTH);

        //pokaż okno
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> new Aplikation(args).show());
    }
}