package se.iths.labboration3.labborationjavafx.shape;

import javafx.scene.paint.Color;

public class ShapeCreator {

    public static Rectangle rectangleOf(Color color, double x, double y, double size){
        return new Rectangle(color, x, y, size);
    }
    public static Circle circleOf(Color color, double x, double y, double size){
        return new Circle(color, x, y, size);
    }
}
