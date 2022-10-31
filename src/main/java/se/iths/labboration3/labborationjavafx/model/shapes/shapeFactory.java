package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.Point;
import se.iths.labboration3.labborationjavafx.model.Enums.SelectedShapeToDraw;

public class shapeFactory {

    public static Shape shapeOf( Color color, Point coordinates, double size, SelectedShapeToDraw option) {
        return switch (option) {
            case CIRCLE -> circleOf(color, coordinates, size);
            case RECTANGLE -> rectangleOf(color, coordinates, size);
        };
    }

    public static Circle circleOf(Color color, Point coordinates, double size){
        return new Circle(color, coordinates, size);
    }

    private static Rectangle rectangleOf(Color color, Point coordinates, double size){
        return new Rectangle(color, coordinates, size);
    }
}
