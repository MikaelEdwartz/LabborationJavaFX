package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.Point;

public class Circle extends Shape {


    public Circle(Color color, Point coordinates, double size) {
        super(color, coordinates, size);
    }

    @Override
    public void draw(GraphicsContext context) {
        double size = getSize();
        double x = getX() - size / 2;
        double y = getY() - size / 2;
        context.setFill(getBorderColor());
        context.fillOval(x - 2.5, y - 2.5, size + 5, size + 5);
        context.setFill(getColor());
        context.fillOval(x, y, size, size);
    }
    @Override
    public boolean isInside(Point coordinates){
        double distanceX = coordinates.x() - getX();
        double distanceY = coordinates.y() - getY();
        double distance = Math.sqrt((distanceX * distanceX) + distanceY * distanceY);
//namnbyte
        return distance <= getSize()/2;
    }
    @Override
    public String toString() {
        return getColor() + super.getClass().toString();
    }
}

