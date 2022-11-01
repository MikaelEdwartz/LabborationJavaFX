package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.Point;
import se.iths.labboration3.labborationjavafx.model.Enums.SelectedShape;

public class ShapeFactory {

    public static Shape shapeOf(Color color, Point coordinates, double size, SelectedShape option) {
        return switch (option) {
            case CIRCLE -> circleOf(color, coordinates, size);
            case RECTANGLE -> rectangleOf(color, coordinates, size);
        };
    }

    public static Circle circleOf(Color color, Point coordinates, double size){
        return new Circle(color, coordinates, size, SelectedShape.CIRCLE);
    }

    private static Rectangle rectangleOf(Color color, Point coordinates, double size){
        return new Rectangle(color, coordinates, size, SelectedShape.RECTANGLE);
    }
}
