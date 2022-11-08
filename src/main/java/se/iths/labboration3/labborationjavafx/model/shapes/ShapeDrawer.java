package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeDrawer {

    public static void draw(Shape shape, GraphicsContext context) {
        var values = new ShapeValues(context, getX(shape), getY(shape), shape.getSize(), shape.getShape());
        switch (shape.getShape()) {
            case CIRCLE -> drawCircle(shape, values);
            case RECTANGLE -> drawRectangle(shape, values);
        }
    }

    public static void drawCircle(Shape circle, ShapeValues values) {
        drawBorder(values, circle.getBorderColor());
        fillInsideBorder(values, circle.getColor());
    }

    public static void drawRectangle(Shape rectangle, ShapeValues values) {
        drawBorder(values, rectangle.getBorderColor());
        fillInsideBorder(values, rectangle.getColor());
    }

    private static void drawBorder(ShapeValues values, Color color) {
        setContextColor(values, color);
        switch (values.shape()) {
            case CIRCLE ->
                    values.context().fillOval(values.x() - 2.5, values.y() - 2.5, values.size() + 5, values.size() + 5);
            case RECTANGLE ->
                    values.context().fillRect(values.x() - 2.5, values.y() - 2.5, values.size() * 1.75 + 5, values.size() + 5);
        }
    }

    private static void fillInsideBorder(ShapeValues values, Color color) {
        setContextColor(values, color);
        switch (values.shape()) {
            case CIRCLE -> values.context().fillOval(values.x(), values.y(), values.size(), values.size());
            case RECTANGLE -> values.context().fillRect(values.x(), values.y(), values.size() * 1.75, values.size());

        }
    }

    private static double getX(Shape shape) {
        return switch (shape.getShape()) {
            case CIRCLE -> shape.getX() - shape.getSize() / 2;
            case RECTANGLE -> shape.getX() - shape.getSize() / 2 * 1.75;
        };
    }

    private static double getY(Shape shape) {
        return shape.getY() - shape.getSize() / 2;
    }

    private static void setContextColor(ShapeValues values, Color color) {
        values.context().setFill(color);
    }
}

