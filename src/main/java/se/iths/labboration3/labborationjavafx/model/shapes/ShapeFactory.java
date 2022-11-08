package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.paint.Color;

import se.iths.labboration3.labborationjavafx.model.Point;
import se.iths.labboration3.labborationjavafx.model.Enums.SelectedShape;

import java.util.regex.Pattern;

public class ShapeFactory {

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

    public static Circle circleOf(Color color, Point coordinates, double size, String id) {
        return new Circle(color, coordinates, size, SelectedShape.CIRCLE, id);
    }

    private static Rectangle rectangleOf(Color color, Point coordinates, double size) {
        return new Rectangle(color, coordinates, size, SelectedShape.RECTANGLE);
    }

    private static Rectangle rectangleOf(Color color, Point coordinates, double size, String id) {
        return new Rectangle(color, coordinates, size, SelectedShape.RECTANGLE, id);
    }

    public static Shape svgToShape(String line) {
        try {
            String[] svgString = getSvgString(line);
            if (line.contains("circle"))
                return getCircle(svgString);
            else
                return getRectangle(svgString);

        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public static String[] getSvgString(String line) {
        var pattern = Pattern.compile("=");
        return pattern.split(line);
    }

    private static Shape getRectangle(String[] svgString) {
        var svgID = svgString[1].substring(0, 36);
        var size = Double.parseDouble(svgString[4].substring(1, 5)) / 2;
        var x = Double.parseDouble(svgString[2].substring(1, 5)) + size / 2;
        var y = Double.parseDouble(svgString[3].substring(1, 5)) + size / 2;
        var color = Color.valueOf(svgString[6].substring(1, 8));

        return shapeOf(color, new Point(x, y), size, SelectedShape.RECTANGLE, svgID);
    }

    private static Shape getCircle(String[] svgString) {
        var svgID = svgString[1].substring(0, 36);
        var size = Double.parseDouble(svgString[4].substring(1, 5)) * 2;
        var x = Double.parseDouble(svgString[2].substring(1, 5));
        var y = Double.parseDouble(svgString[3].substring(1, 5));
        var color = Color.valueOf(svgString[5].substring(1, 8));

        return shapeOf(color, new Point(x, y), size, SelectedShape.CIRCLE, svgID);
    }
}
