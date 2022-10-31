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

        drawBorder(context, size, x, y);
        fillInsideBorder(context, size, x, y);
    }

    private void fillInsideBorder(GraphicsContext context, double size, double x, double y) {
        context.setFill(getColor());
        context.fillRect(x, y, size * 1.75, size);
    }

    private void drawBorder(GraphicsContext context, double size, double x, double y) {
        context.setFill(getBorderColor());
        context.fillRect(x -2.5, y -2.5, size * 1.75 + 5, size + 5);
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
    public Shape getCopyOfShape() {
        return new Rectangle(getColor(), new Point(getX(),getY()), getSize());
    }


    @Override
    public String toString() {
        return getColor() + super.getClass().toString();
    }


}
