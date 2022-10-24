package se.iths.labboration3.labborationjavafx.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Shape{


    protected Circle(Color color, double x, double y, double size) {
        super(color, x, y, size);
    }


    @Override
    public void draw(GraphicsContext context) {
        double size = getSize();
        double x = getX() - size / 2;
        double y = getY() - size / 2;
        context.setFill(getColor());
        context.fillOval(x, y, size, size);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

