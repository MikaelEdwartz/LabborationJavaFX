package se.iths.labboration3.labborationjavafx.model;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.shapes.Shape;


public class PaintModel {
    private final BooleanProperty selectorOption;
    private final ObservableList<Shape> shapes;
    private final ObjectProperty<Color> colorPicker;
    private final StringProperty size;
    private SelectedShapeToDraw selectedShape;

    public PaintModel(){
    this.selectorOption = new SimpleBooleanProperty(false);
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

    public SelectedShapeToDraw getSelectedShape() {
        return selectedShape;
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

    public boolean getSelectorOption() {
        return selectorOption.get();
    }

    public void setCircleShape() {
        selectedShape = SelectedShapeToDraw.CIRCLE;
        setSelectionMode(false);
    }
    public void setRectangleShape() {
        selectedShape = SelectedShapeToDraw.RECTANGLE;
        setSelectionMode(false);
    }
    public void setSelectionMode(){
        this.selectorOption.set(true);

    }
    public void setSelectionMode(boolean option){
        this.selectorOption.set(option);

    }
    public BooleanProperty selectorOptionProperty() {
        return selectorOption;
    }

}
