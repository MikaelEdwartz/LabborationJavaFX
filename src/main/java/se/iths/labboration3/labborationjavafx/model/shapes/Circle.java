package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.Point;

public class Circle extends Shape {


    protected Circle(Color color, Point coordinates, double size) {
        super(color, coordinates, size);
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

