package se.iths.labboration3.labborationjavafx.model;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.shapes.Shape;

import java.util.ArrayList;
import java.util.List;


public class PaintModel {
    private final BooleanProperty selectorOption;
    private final ObservableList<Shape> shapes;
    private final ObjectProperty<Color> colorPicker;
    private final StringProperty size;
    private SelectedShapeToDraw selectedShape;
    private final List<Shape> undoChanges;
    private final List<Integer> changeList;


    public PaintModel(){
    this.selectorOption = new SimpleBooleanProperty(false);
    this.colorPicker = new SimpleObjectProperty<>(Color.BLACK);
    this.size = new SimpleStringProperty("50");
    this.shapes = FXCollections.observableArrayList(PaintModel::getShapeAttribute);
    this.undoChanges = new ArrayList<>();
    this.changeList = new ArrayList<>();
    }
    private static Observable[] getShapeAttribute(Shape shape) {
        return new Observable[]{
                shape.colorProperty(),
                shape.xProperty(),
                shape.yProperty(),
                shape.sizeProperty(),
                shape.borderColorProperty()
        };
    }

    public void addToShapes(Shape shape){
        clearChangeList();
        if(!(shape == null))
            this.shapes.add(shape);
    }

    public SelectedShapeToDraw getSelectedShape() {
        return selectedShape;
    }

    public double getSizeAsDouble(){
        return Double.parseDouble(getSize());
    }

    public String getSize(){
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

    public void removeLastChange() {
        shapes.remove(shapes.size()-1);
    }

    public void checkIfSelectedAndAddOrRemove(int i){
        if(alreadySelected(i))
            removeFromChangeList(i);
        else
            addToChangeList(i);
    }

    private boolean alreadySelected(int i) {
        return this.changeList.contains(i);
    }

    private void removeFromChangeList(int i) {
        this.shapes.get(i).setBorderColor(this.shapes.get(i).getColor());
        this.changeList.remove(i);
    }

    private void addToChangeList(int i) {
        this.shapes.get(i).setBorderColor(Color.VIOLET);
        this.changeList.add(i);
    }

    public void clearChangeList(){
        this.changeList.clear();
    }

    public void changeSelectedShapes(ChangeOption selectedOption){
        setChange(selectedOption);
        setMatchingBorderColor();
        clearChangeList();
    }

    private void setChange(ChangeOption selectedOption) {
        for (var index : this.changeList)
            changeSelectedAttribute(selectedOption, index);
    }

    private void changeSelectedAttribute(ChangeOption selectedOption, Integer index) {
        switch (selectedOption) {
            case SIZE -> this.shapes.get(index).setSize(getSizeAsDouble());
            case COLOR -> this.shapes.get(index).setColor(getColorPicker());
        }
    }

    private void setMatchingBorderColor() {
        for (Integer integer : this.changeList)
            this.shapes.get(integer).setBorderColor(this.shapes.get(integer).getColor());
    }
}
