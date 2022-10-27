package se.iths.labboration3.labborationjavafx.model;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.shapes.Shape;


public class PaintModel {
    private final BooleanProperty rectangleSelected;
    private final BooleanProperty circleSelected;
    private final ObservableList<Shape> shapes;
    private final ObjectProperty<Color> colorPicker;
    private final StringProperty size;

    public PaintModel(){
    this.rectangleSelected = new SimpleBooleanProperty(false);
    this.circleSelected = new SimpleBooleanProperty(false);
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
        return Double.parseDouble(getSize1());
    }

    public String getSize1(){
        return size.get();
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

    public boolean getRectangleSelected() {
        return rectangleSelected.get();
    }


    public BooleanProperty rectangleSelectedProperty() {
        return rectangleSelected;
    }

    public void setRectangleSelected(boolean rectangleSelected) {
        this.rectangleSelected.set(rectangleSelected);
    }

    public boolean getCircleSelected() {
        return circleSelected.get();
    }
    public void setCircleShape() {
        circleSelected.set(true);
        rectangleSelected.set(false);
    }
    public void setRectangleShape() {
        circleSelected.set(false);
        rectangleSelected.set(true);
    }
    public void setSelectionMode(){
        circleSelected.set(false);
        rectangleSelected.set(false);
    }

    public BooleanProperty circleSelectedProperty() {
        return circleSelected;
    }

    public void setCircleSelected(boolean circleSelected) {
        this.circleSelected.set(circleSelected);
    }
}
