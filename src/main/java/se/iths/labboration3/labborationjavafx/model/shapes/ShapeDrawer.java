package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeDrawer{

    public static void draw(Shape shape, GraphicsContext context){
        switch (shape.getShape()) {
            case CIRCLE ->  drawCircle(shape, context);
            case RECTANGLE -> drawRectangle(shape, context);
        }
    }

    public static void drawCircle(Shape circle, GraphicsContext context){
        var values = new ShapeValues(context, circle.getSize(), getX(circle), getY(circle));
        drawBorder(values, circle.getBorderColor());
        fillInsideBorder(values, circle.getColor());
    }



    private static void drawBorder(ShapeValues values, Color color) {
        values.context().setFill(color);
        values.context().fillOval(values.x() -2.5, values.y() -2.5, values.size() + 5, values.size() +5);
//        context.setFill(color);
//        context.fillOval(x - 2.5, y - 2.5, size + 5, size + 5);
    }

    private static void fillInsideBorder(ShapeValues values, Color color) {
        values.context().setFill(color);
        values.context().fillOval(values.x(), values.y(), values.size(), values.size());


        //context.setFill(color);
        //context.fillOval(x, y, size, size);
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


    private static double getX(Shape shape){
        return switch (shape.getShape()) {
            case CIRCLE -> shape.getX() - shape.getSize() / 2;
            case RECTANGLE -> shape.getX() - shape.getSize() / 2 * 1.75;
        };
    }

    private static double getY(Shape shape){
        return shape.getX() - shape.getSize() / 2;
    }
}

