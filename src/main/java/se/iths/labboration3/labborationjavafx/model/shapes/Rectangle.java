package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.Point;

public class Rectangle extends Shape {

    public Rectangle(Color color, Point coordinates, double size) {
        super(color, coordinates, size);
    }

    public Rectangle(Color color, double x, double y, double size) {
        super(color, x, y, size);
    }


    @Override
    public void draw(GraphicsContext context) {
        double size = getSize();
        double x = getX() - (size)/2;
        double y = getY() - size /2;
        context.setFill(getColor());
        context.fillRect(x, y, size * 2, size);
    }

    @Override
    public boolean isInside(Point mouseCoordinate) {

        double leftX = getX() - getSize();
        double topY = getY() - getSize();
        double mouseX = mouseCoordinate.x();
        double mouseY = mouseCoordinate.y();

        return mouseX >= leftX &&
                mouseX <= leftX + 1.5*getSize() &&
                mouseY >= topY &&
                mouseY <= topY + 1.5*getSize();



    }


    @Override
    public String toString() {
        return getColor() + super.getClass().toString();
    }


}
