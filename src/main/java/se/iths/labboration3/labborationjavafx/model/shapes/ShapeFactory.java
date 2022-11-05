package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.PaintModel;
import se.iths.labboration3.labborationjavafx.model.Point;
import se.iths.labboration3.labborationjavafx.model.Enums.SelectedShape;

import java.util.regex.Pattern;

public class ShapeFactory {
    private static final PaintModel model = new PaintModel();


    public static Shape shapeOf(Color color, Point coordinates, double size, SelectedShape option) {
        return switch (option) {
            case CIRCLE -> circleOf(color, coordinates, size);
            case RECTANGLE -> rectangleOf(color, coordinates, size);
        };
    }

    public static Shape shapeOf(Color color, Point coordinates, double size, SelectedShape option, String svgID) {
        return switch (option) {
            case CIRCLE -> circleOf(color, coordinates, size, svgID);
            case RECTANGLE -> rectangleOf(color, coordinates, size, svgID);
        };
    }

    public static Circle circleOf(Color color, Point coordinates, double size) {
        return new Circle(color, coordinates, size, SelectedShape.CIRCLE);
    }

    private static Rectangle rectangleOf(Color color, Point coordinates, double size) {
        return new Rectangle(color, coordinates, size, SelectedShape.RECTANGLE);
    }

    public static Circle circleOf(Color color, Point coordinates, double size, String id) {
        return new Circle(color, coordinates, size, SelectedShape.CIRCLE, id);
    }

    private static Rectangle rectangleOf(Color color, Point coordinates, double size, String id) {
        return new Rectangle(color, coordinates, size, SelectedShape.RECTANGLE, id);
    }

    public static Shape svgToShape(String line) {

        try {
            Pattern pattern = Pattern.compile("=");

            String[] svgString = pattern.split(line);

            if (line.contains("circle"))
                return getCircle(svgString);
            else
                return getRectangle(svgString);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private static Shape getRectangle(String[] svgString) {
        String svgID = svgString[1].substring(0, 32);
        double size = Double.parseDouble(svgString[4].substring(1, 5)) / 2;
        double x = Double.parseDouble(svgString[2].substring(1, 5)) + size / 2;
        double y = Double.parseDouble(svgString[3].substring(1, 5)) + size / 2;
        Color color = Color.valueOf(svgString[6].substring(1, 8));
        return shapeOf(color, new Point(x, y), size, SelectedShape.RECTANGLE, svgID);
    }

    private static Shape getCircle(String[] svgString) {
        String svgID = svgString[1].substring(0, 32);
        double size = Double.parseDouble(svgString[4].substring(1, 5));
        double x = Double.parseDouble(svgString[2].substring(1, 5));
        double y = Double.parseDouble(svgString[3].substring(1, 5));
        Color color = Color.valueOf(svgString[5].substring(1, 8));

        return shapeOf(color, new Point(x, y), size, SelectedShape.CIRCLE, svgID);
    }

}
