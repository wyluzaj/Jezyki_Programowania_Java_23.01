package V1P;

import java.awt.Color;
import java.awt.Rectangle;

//przechowuje kolor prostokąta
public class ColoredRectangle extends Rectangle {
    private final Color color;

    public ColoredRectangle(int x, int y, int width, int height, Color color) {
        super(x, y, width, height);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}