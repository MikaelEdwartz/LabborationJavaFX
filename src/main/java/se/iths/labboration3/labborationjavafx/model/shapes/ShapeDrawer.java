package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeDrawer {

    public static void draw(Shape shape, GraphicsContext context){
        switch (shape.getShape()) {
            case CIRCLE ->   drawCircle(shape, context);
            case RECTANGLE -> drawRectangle(shape, context);
        }
    }

    public static void drawCircle(Shape circle, GraphicsContext context){
        double size = circle.getSize();
        double x = circle.getX() - size / 2;
        double y = circle.getY() - size / 2;

        drawBorder(circle, context, size, x, y);
        fillInsideBorder(circle, context, size, x, y);
    }

    private static void drawBorder(Shape circle, GraphicsContext context, double size, double x, double y) {
        context.setFill(circle.getBorderColor());
        context.fillOval(x - 2.5, y - 2.5, size + 5, size + 5);
    }

    private static void fillInsideBorder(Shape circle, GraphicsContext context, double size, double x, double y) {
        context.setFill(circle.getColor());
        context.fillOval(x, y, size, size);
    }

    public static void drawRectangle(Shape rectangle, GraphicsContext context) {
        double size = rectangle.getSize();
        double x = rectangle.getX() - size / 2 * 1.75;
        double y = rectangle.getY() - size / 2 ;

        drawBorder(context, size, x, y, rectangle.getBorderColor());
        fillInsideBorder(context, size, x, y, rectangle.getColor());
    }

    private static void fillInsideBorder(GraphicsContext context, double size, double x, double y, Color color) {
        context.setFill(color);
        context.fillRect(x, y, size * 1.75, size);
    }

    private static void drawBorder(GraphicsContext context, double size, double x, double y, Color color) {
        context.setFill(color);
        context.fillRect(x -2.5, y -2.5, size * 1.75 + 5, size + 5);
    }
}
