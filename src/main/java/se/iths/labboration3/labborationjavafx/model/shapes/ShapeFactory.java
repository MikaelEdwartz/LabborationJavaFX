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

    private static Circle circleOf(Color color, Point coordinates, double size) {
        return new Circle(color, coordinates, size, SelectedShape.CIRCLE);
    }

    private static Rectangle rectangleOf(Color color, Point coordinates, double size) {
        return new Rectangle(color, coordinates, size, SelectedShape.RECTANGLE);
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
        double size = Double.parseDouble(svgString[4].substring(1, 5)) / 2;
        double x = Double.parseDouble(svgString[2].substring(1, 5)) + size / 2;
        double y = Double.parseDouble(svgString[3].substring(1, 5)) + size / 2;
        Color color = Color.valueOf(svgString[6].substring(1, 8));
        return shapeOf(color, new Point(x, y), size, SelectedShape.RECTANGLE);
    }

    private static Shape getCircle(String[] svgString) {
        double size = Double.parseDouble(svgString[3].substring(1, 5)) * 2;
        double x = Double.parseDouble(svgString[1].substring(1, 5));
        double y = Double.parseDouble(svgString[2].substring(1, 5));
        Color color = Color.valueOf(svgString[4].substring(1, 8));
        return shapeOf(color, new Point(x, y), size, SelectedShape.CIRCLE);
    }

    private static double getValueFromString(String[] svgString, int arrayIndex, int startIndex, int stopIndex) {
        return Double.parseDouble(svgString[arrayIndex].substring(startIndex, stopIndex));
    }
}
