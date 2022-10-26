package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.Point;

public class shapeFactory {

    public static Rectangle rectangleOf(Color color, Point coordinates, double size){
        return new Rectangle(color, coordinates, size);
    }
    public static Circle circleOf(Color color, Point coordinates, double size){
        return new Circle(color, coordinates, size);
    }
}
