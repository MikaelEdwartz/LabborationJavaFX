package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.Enums.SelectedShape;
import se.iths.labboration3.labborationjavafx.model.Point;

public class Circle extends Shape {

    public Circle(Color color, Point coordinates, double size, SelectedShape shape) {
        super(color, coordinates, size, shape);
    }

    public Circle(Color color, Point coordinates, double size, SelectedShape shape, String svgID) {
        super(color, coordinates, size, shape);
        super.setSvgID(svgID);
    }

    @Override
    public Shape copyOf() {
        return new Circle(getColor(), new Point(getX(), getY()), getSize(), getShape());
    }

    @Override
    public boolean insideShape(Point coordinates) {
        double distance = Math.sqrt(distanceX(coordinates) + distanceY(coordinates));
        return distance <= getSize() / 2;
    }

    private double distanceY(Point coordinates) {
        double distanceY = coordinates.y() - getY();
        return distanceY * distanceY;
    }

    private double distanceX(Point coordinates) {
        double distanceX = coordinates.x() - getX();
        return distanceX * distanceX;
    }

    @Override
    public String getAsSVG() {
        return "<circle id=" + getSvgID() + "\" "
                + "cx=\"" + getX() + "\" "
                + "cy=\"" + getY() + "\" "
                + "r=\"" + getSize() / 2 + "\" "
                + "fill=\"#" + getColor().toString().substring(2, 10) + "\"/>";
    }

}

