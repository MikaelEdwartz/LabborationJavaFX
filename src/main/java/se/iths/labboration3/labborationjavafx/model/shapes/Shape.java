package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.Enums.SelectedShape;
import se.iths.labboration3.labborationjavafx.model.Point;

public abstract class Shape implements ShapeInterface {

    private final SimpleDoubleProperty x = new SimpleDoubleProperty();
    private final SimpleDoubleProperty y = new SimpleDoubleProperty();
    private final SimpleDoubleProperty size = new SimpleDoubleProperty();
    private final SimpleObjectProperty<Color> color = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Color> borderColor = new SimpleObjectProperty<>();
    private final SelectedShape shape;

    public SelectedShape getShape() {
        return shape;
    }

    protected Shape(Color color, Point coordinates, double size, SelectedShape shape){
        setColor(color);
        setX(coordinates.x());
        setY(coordinates.y());
        setSize(size);
        setBorderColor(color);
        this.shape = shape;
    }


    public Color getBorderColor() {
        return borderColor.get();
    }

    public SimpleObjectProperty<Color> borderColorProperty() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor.set(borderColor);
    }

    public double getX() {
        return x.get();
    }

    public SimpleDoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public SimpleDoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public double getSize() {
        return size.get();
    }

    public SimpleDoubleProperty sizeProperty() {
        return size;
    }

    public void setSize(double size) {
        this.size.set(size);
    }

    public Color getColor() {
        return (Color) color.get();
    }

    public SimpleObjectProperty colorProperty() {
        return color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void draw(GraphicsContext context) {
    }

    public abstract boolean isInside(Point coordinates);


    public abstract Shape getCopyOfShape();
}
