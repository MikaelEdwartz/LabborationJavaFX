package se.iths.labboration3.labborationjavafx.model;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.Server;
import se.iths.labboration3.labborationjavafx.model.Enums.ChangeOption;
import se.iths.labboration3.labborationjavafx.model.Enums.SelectedShape;
import se.iths.labboration3.labborationjavafx.model.shapes.Shape;
import se.iths.labboration3.labborationjavafx.model.shapes.ShapeFactory;

import java.util.ArrayList;
import java.util.List;


public class PaintModel {
    private final BooleanProperty selectorOption;
    private final ObservableList<Shape> shapes;
    private final ObjectProperty<Color> colorPicker;
    private final StringProperty size;
    private SelectedShape selectedShape;
    private final List<Shape> changeList;
    private final List<List<Shape>> undoList = new ArrayList<>();
    private final Server server;
    private final StringProperty connectToServer;


    public PaintModel() {
        this.selectorOption = new SimpleBooleanProperty(false);
        this.colorPicker = new SimpleObjectProperty<>(Color.BLACK);
        this.size = new SimpleStringProperty("50");
        this.shapes = FXCollections.observableArrayList(PaintModel::getShapeAttribute);
        this.changeList = new ArrayList<>();
        this.selectedShape = SelectedShape.RECTANGLE;
        server = new Server();
        connectToServer = new SimpleStringProperty("Connect To Server");
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

    public void checkIfConnectedAndAddToShapes(Shape shape) {
        if (shape == null)
            return;

        if (server.isConnected())
            server.sendToServer(shape);
        else
            this.shapes.add(shape);
    }

    public void addToShapes(String line) {
        if (line == null || line.contains("joined") || line.contains("left"))
            return;

        this.shapes.add(ShapeFactory.svgToShape(line));
    }

    public SelectedShape getSelectedShape() {
        return selectedShape;
    }

    public double getSizeAsDouble() {
        return Double.parseDouble(getSize());
    }

    public String getSize() {
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

    public void setSelectedShape(SelectedShape selectedShape){
        switch (selectedShape) {
            case CIRCLE -> setCircleShape();
            case RECTANGLE -> setRectangleShape();
        }
    }

    public void setCircleShape() {
        selectedShape = SelectedShape.CIRCLE;
        setSelectionMode(false);
    }

    public void setRectangleShape() {
        selectedShape = SelectedShape.RECTANGLE;
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
        if(undoList.isEmpty())
            return;
        undoChange();
    }

    private void undoChange() {
        shapes.clear();
        removeLastElementFromUndoList();
        revertToListBeforeChange();
    }

    private void revertToListBeforeChange() {
        for (var shape : undoList.get(undoList.size() - 1))
            shapes.add(shape.copyOf());
    }

    private void removeLastElementFromUndoList() {
        if(undoList.size() > 0)
            undoList.remove(undoList.size() - 1);


    }

    public void addChangesToUndoList() {
        List<Shape> tempList = new ArrayList<>();
        copyShapesToTempList(tempList);
        undoList.add(tempList);
    }

    public void copyShapesToTempList(List<Shape> tempList) {
        getShapes().forEach(shape -> tempList.add(shape.copyOf()));
    }

    public void checkIfSelectedAndAddOrRemove(int i) {
        if (alreadySelected(i))
            removeFromChangeList(i);
        else
            addToChangeList(i);
    }

    public void checkIfSelectedAndAddOrRemove(Shape shape) {
        if (alreadySelected(shape))
            removeFromChangeList(shape);
        else
            addToChangeList(shape);
    }

    private boolean alreadySelected(int i) {
        return this.changeList.contains(i);
    }

    private boolean alreadySelected(Shape shape) {
        return this.changeList.contains(shape);
    }

    private void removeFromChangeList(int i) {
        this.shapes.get(i).setBorderColor(this.shapes.get(i).getColor());
        removeClickedShapeFromList(i);
    }

    private void removeFromChangeList(Shape shape) {
        shape.setBorderColor(shape.getColor());
        removeClickedShapeFromList(shape);
    }

    private void removeClickedShapeFromList(int i) {
        List<Integer> tempList = getListWithoutClickedShape(i);
        this.changeList.clear();
        // this.changeList.addAll(tempList);
    }

    private void removeClickedShapeFromList(Shape shape) {
        this.changeList.remove(shape);
    }

    private List<Integer> getListWithoutClickedShape(int i) {
        return this.changeList
                .stream()
                .filter(s -> sameNumber(i, s))
                .toList();
    }

    private static boolean sameNumber(int i, Integer s) {
        return !(s == i);
    }

    private void addToChangeList(int i) {
        this.shapes.get(i).setBorderColor(Color.VIOLET);
        this.changeList.add(i);
    }

    private void addToChangeList(Shape shape) {
        shape.setBorderColor(Color.VIOLET);
        this.changeList.add(shape);
    }

    public void clearChangeList() {
        this.changeList.clear();
    }

    public void changeSelectedShapes(ChangeOption selectedOption) {
        setChange(selectedOption);
        setMatchingBorderColor();
        clearChangeList();
    }

    private void setChangea(ChangeOption selectedOption) {
        for (var index : this.changeList)
            changeSelectedAttribute(selectedOption, index);
    }

    private void setChange(ChangeOption selectedOption) {
        for (var shape : this.changeList)
            changeSelectedAttribute(selectedOption, shape);
    }

    private void changeSelectedAttributea(ChangeOption selectedOption, Integer index) {
        switch (selectedOption) {
            case SIZE -> setNewSize(index);
            case COLOR -> setNewColor(index);
        }
    }

    private void changeSelectedAttribute(ChangeOption selectedOption, Shape shape) {
        switch (selectedOption) {
            case SIZE -> setNewSize(shape);
            case COLOR -> setNewColor(shape);
        }
    }

    private void setNewColor(Shape shape) {
        shape.setColor(getColorPicker());
        // sendChangeToServerIfConnected(index);
    }

    private void sendChangeToServerIfConnected(Integer index) {
        if (server.isConnected())
            server.sendToServer(this.shapes.get(index));
    }

    private void setNewSize(Shape shape) {
        shape.setSize(getSizeAsDouble());
        //sendChangeToServerIfConnected(index);
    }

    private void setMatchingBorderColora() {
        for (Integer integer : this.changeList)
            this.shapes.get(integer).setBorderColor(this.shapes.get(integer).getColor());
    }

    private void setMatchingBorderColor() {
        for (var shape : this.changeList)
            shape.setBorderColor(shape.getColor());
    }

    public Server getServer() {
        return server;
    }

    public void connectToServer() {
        server.connect(this);
    }

    public String getConnectToServer() {
        return connectToServer.get();
    }

    public StringProperty connectToServerProperty() {
        return connectToServer;
    }

    public void setConnectToServer(String connectToServer) {
        this.connectToServer.set(connectToServer);
    }

    public void changeConnectionString() {
        if (server.isConnected())
            connectToServer.set("Disconnect From Server");
        else
            connectToServer.set("Connect To Server");
    }
}
