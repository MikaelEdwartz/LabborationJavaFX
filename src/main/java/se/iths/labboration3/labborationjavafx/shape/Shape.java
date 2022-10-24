package se.iths.labboration3.labborationjavafx.shape;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape implements ShapeDrawer {

    private final SimpleDoubleProperty x = new SimpleDoubleProperty();
    private final SimpleDoubleProperty y = new SimpleDoubleProperty();
    private final SimpleDoubleProperty size = new SimpleDoubleProperty();
    private final SimpleObjectProperty<Color> color = new SimpleObjectProperty<>();


    protected Shape(Color color, double x, double y, double size){
        setX(x);
        setY(y);
        setSize(size);
        setColor(color);

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
}
