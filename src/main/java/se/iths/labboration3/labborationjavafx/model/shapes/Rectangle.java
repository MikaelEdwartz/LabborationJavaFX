package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.Point;

public class Rectangle extends Shape {

    protected Rectangle(Color color, Point coordinates, double size) {
        super(color, coordinates, size);
    }

    @Override
    public void draw(GraphicsContext context) {
        double size = getSize();
        double x = getX() - size / 2;
        double y = getY() - size / 2;
        context.setFill(getColor());
        context.fillRect(x, y, size * 1.75, size);
    }


    @Override
    public String toString() {
        return super.toString();
    }


}
