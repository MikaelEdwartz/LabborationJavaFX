package se.iths.labboration3.labborationjavafx.model;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.shape.Shape;

public class PaintModel {
    private final BooleanProperty rectangle;
    private final BooleanProperty circle;
    private final ObservableList<Shape> shapes;
    private final ObjectProperty<Color> colorPicker;
    private final StringProperty size;

    public PaintModel(){
    this.rectangle  = new SimpleBooleanProperty(false);
    this.circle = new SimpleBooleanProperty(false);
    this.colorPicker = new SimpleObjectProperty<>(Color.BLACK);
    this.size = new SimpleStringProperty("50");
    this.shapes = FXCollections.observableArrayList(PaintModel::getShapeAttribute);

    }
    private static Observable[] getShapeAttribute(Shape shape) {
        return new Observable[]{
                shape.colorProperty(),
                shape.xProperty(),
                shape.yProperty(),
                shape.sizeProperty()
        };
    }

    public void addToShapes(Shape shape){
        if(!(shape == null))
            this.shapes.add(shape);

    }

    public double getSize(){
        return Double.parseDouble(sizeProperty().get());
    }

    public StringProperty sizeProperty() {
        return size;
    }

    public Color getColorPicker() {
        return colorPicker.get();
    }

    public ObjectProperty<Color> colorPickerProperty() {
        return colorPicker;
    }

    public ObservableList<Shape> getShapes() {
        return shapes;
    }

    public boolean isRectangle() {
        return rectangle.get();
    }


    public BooleanProperty rectangleProperty() {
        return rectangle;
    }

    public void setRectangle(boolean rectangle) {
        this.rectangle.set(rectangle);
    }

    public boolean isCircle() {
        return circle.get();
    }
    public void setCircleShape() {
        circle.set(true);
        rectangle.set(false);
    }
    public void setRectangleShape() {
        circle.set(false);
        rectangle.set(true);
    }

    public BooleanProperty circleProperty() {
        return circle;
    }

    public void setCircle(boolean circle) {
        this.circle.set(circle);
    }
}
