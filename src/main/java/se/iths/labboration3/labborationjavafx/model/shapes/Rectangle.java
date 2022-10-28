package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.Point;

public class Rectangle extends Shape {

    public Rectangle(Color color, Point coordinates, double size) {
        super(color, coordinates, size);
    }




    @Override
    public void draw(GraphicsContext context) {
        double size = getSize();
        double x = getX() - size / 2 * 1.75;
        double y = getY() - size / 2 ;

        context.setFill(getColor());
        context.fillRect(x, y, size * 1.75, size);
    }

    @Override
    public boolean isInside(Point mouseCoordinate) {

        double leftXBorder = getX() - getSize() / 2 * 1.75;
        double rightXBorder = leftXBorder + getSize() * 1.75;
        double topYBorder = getY() - getSize() / 2;
        double bottomYBorder = topYBorder + getSize();

        double mouseX = mouseCoordinate.x();
        double mouseY = mouseCoordinate.y();

        return mouseX >= leftXBorder &&
                mouseX <= rightXBorder &&
                mouseY >= topYBorder &&
                mouseY <= bottomYBorder;



    }



    @Override
    public String toString() {
        return getColor() + super.getClass().toString();
    }


}
