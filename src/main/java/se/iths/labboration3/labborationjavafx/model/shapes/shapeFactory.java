package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.Point;
import se.iths.labboration3.labborationjavafx.model.SelectedShapeToDraw;

public class shapeFactory {

    public static Circle circleOf(Color color, Point coordinates, double size){
        return new Circle(color, coordinates, size);
    }
    private static Rectangle rectangleOf(Color color, Point coordinates, double size){
        return new Rectangle(color, coordinates, size);
    }
    public static Shape shapeOf( Color color, Point coordinates, double size, SelectedShapeToDraw option) {
        Shape newShape = null;
        switch (option) {
            case CIRCLE -> newShape = circleOf(color, coordinates, size);
            case RECTANGLE -> newShape =  rectangleOf(color, coordinates, size);
        }
        return newShape;
    }


}
