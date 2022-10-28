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
        double x = getX() - ((size)/2)*1.75;
        double y = getY() - size/2;
        context.setFill(getColor());
        context.fillRect(x, y, size * 1.75, size);
    }

    @Override
    public boolean isInside(Point mouseCoordinate) {

        double leftX = getX() - (getSize()/2)*1.75;
        double topY = getY() - getSize()/2;
        double mouseX = mouseCoordinate.x();
        double mouseY = mouseCoordinate.y();

        return mouseX >= leftX &&
                mouseX <= leftX + (getSize())*1.75 &&
                mouseY >= topY &&
                mouseY <= topY + getSize();



    }


    @Override
    public String toString() {
        return getColor() + super.getClass().toString();
    }


}
