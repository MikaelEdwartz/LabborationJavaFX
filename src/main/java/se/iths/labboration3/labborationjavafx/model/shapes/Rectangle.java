package se.iths.labboration3.labborationjavafx.model.shapes;


import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.Enums.SelectedShape;
import se.iths.labboration3.labborationjavafx.model.Point;


public class Rectangle extends Shape {


    public Rectangle(Color color, Point coordinates, double size, SelectedShape shape) {
        super(color, coordinates, size, shape);
    }

    public Rectangle(Color color, Point coordinates, double size, SelectedShape shape, String id) {
        super(color, coordinates, size, shape);
        super.setSvgID(id);
    }

    @Override
    public Shape copyOf() {
        return new Rectangle(getColor(), new Point(getX(), getY()), getSize(), getShape());
    }

    @Override
    public boolean insideShape(Point mouseCoordinate) {
        return insideXAxis(mouseCoordinate.x()) && insideYAxis(mouseCoordinate.y());

    }

    private boolean insideYAxis(double mouseY) {
        double topYBorder = getY() - getSize() / 2;
        double bottomYBorder = topYBorder + getSize();
        return mouseY >= topYBorder && mouseY <= bottomYBorder;

    }

    private boolean insideXAxis(double mouseX) {
        double leftXBorder = getX() - getSize() / 2 * 1.75;
        double rightXBorder = leftXBorder + getSize() * 1.75;
        return mouseX >= leftXBorder && mouseX <= rightXBorder;
    }

    @Override
    public String getAsSVG() {

        return "<rect \"id=\"" + getSvgID() + "\"" +
                "x=\"" + (getX() - getSize() / 2) + "\" " +
                "y=\"" + (getY() - getSize() / 2) + "\" " +
                "width=\"" + getSize() * 1.75 + "\" " +
                "height=\"" + getSize() + "\" " +
                "fill=\"#" + getColor().toString().substring(2, 10) + "\" />";
    }


}