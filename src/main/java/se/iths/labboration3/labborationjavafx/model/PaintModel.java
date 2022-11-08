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
    private final List<Shape> changeList;
    private final List<List<Shape>> undoList = new ArrayList<>();
    private final List<List<Shape>> redoList = new ArrayList<>();
    private final StringProperty connectToServer;
    private final Server server;
    private SelectedShape selectedShape;

    public PaintModel() {
        selectorOption = new SimpleBooleanProperty(false);
        colorPicker = new SimpleObjectProperty<>(Color.BLACK);
        size = new SimpleStringProperty("50");
        shapes = FXCollections.observableArrayList(PaintModel::getObservableAttributes);
        changeList = new ArrayList<>();
        selectedShape = SelectedShape.RECTANGLE;
        server = new Server();
        connectToServer = new SimpleStringProperty("Connect To Server");
    }

    private static Observable[] getObservableAttributes(Shape shape) {
        return new Observable[]{
                shape.colorProperty(),
                shape.xProperty(),
                shape.yProperty(),
                shape.sizeProperty(),
                shape.borderColorProperty()
        };
    }

    public void setSelectedShape(SelectedShape selectedShape) {
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

    public void setSelectionMode() {
        selectorOption.set(true);
    }

    public void checkSelectedShape(Shape shape) {
        if (isAlreadySelected(shape))
            removeFromChangeList(shape);
        else
            addToChangeList(shape);
    }

    private boolean isAlreadySelected(Shape shape) {
        return changeList.contains(shape);
    }

    private void removeFromChangeList(Shape shape) {
        shape.setBorderColor(shape.getColor());
        removeClickedShapeFromList(shape);
    }

    private void removeClickedShapeFromList(Shape shape) {
        changeList.remove(shape);
    }

    private void addToChangeList(Shape shape) {
        shape.setBorderColor(Color.VIOLET);
        changeList.add(shape);
    }

    public void checkIfConnectedAndAddToShapes(Shape shape) {
        if (shape == null)
            return;

        if (server.isConnected())
            server.sendToServer(shape);
        else
            shapes.add(shape);
    }

    public void addChangesToUndoList() {
        undoList.add(getTempList());
    }

    private List<Shape> getTempList() {
        List<Shape> tempList = new ArrayList<>();
        getShapes().forEach(shape -> tempList.add(shape.copyOf()));
        return tempList;
    }

    public void addToRedoList() {
        redoList.add(getTempList());
    }

    public void removeLastChange() {
        if (undoList.isEmpty())
            return;
        undo();
    }

    private void undo() {
        shapes.clear();
        removeLastElementFromList(undoList);
        revertToListBeforeChange();
    }

    private void removeLastElementFromList(List<List<Shape>> list) {
        if (list.size() > 0)
            list.remove(list.size() - 1);
    }

    private void revertToListBeforeChange() {
        addShapesToObservableList(undoList);
    }

    private void addShapesToObservableList(List<List<Shape>> list) {
        for (var shape : list.get(list.size() - 1))
            shapes.add(shape.copyOf());
    }

    public void revertLastUndo() {
        if (redoList.isEmpty())
            return;

        redoLast();
    }

    private void redoLast() {
        shapes.clear();
        addShapesToObservableList(redoList);
        removeLastElementFromList(redoList);
    }

    public void changeSelectedShapes(ChangeOption selectedOption) {
        setChange(selectedOption);
        setMatchingBorderColor();
        clearChangeList();
    }

    private void setChange(ChangeOption selectedOption) {
        for (var shape : changeList)
            changeSelectedAttribute(selectedOption, shape);
    }

    private void changeSelectedAttribute(ChangeOption selectedOption, Shape shape) {
        switch (selectedOption) {
            case SIZE -> setNewSize(shape);
            case COLOR -> setNewColor(shape);
        }
    }

    private void setNewSize(Shape shape) {
        shape.setSize(getSizeAsDouble());
        sendChangeToServerIfConnected(shape);
    }

    private void setNewColor(Shape shape) {
        shape.setColor(getColorPicker());
        sendChangeToServerIfConnected(shape);
    }

    private void sendChangeToServerIfConnected(Shape shape) {
        if (server.isConnected())
            server.sendToServer(shape);
    }

    private void setMatchingBorderColor() {
        for (var shape : changeList)
            shape.setBorderColor(shape.getColor());
    }

    public void clearChangeList() {
        changeList.clear();
    }

    public void connectToServer() {
        server.connect(this);
    }

    public void changeConnectString() {
        if (server.isConnected())
            connectToServer.set("Disconnect From Server");
        else
            connectToServer.set("Connect To Server");
    }

    public void readFromServer(String line) {
        if (isNotDrawableShape(line)) return;
        addOrChangeShape(line);
    }

    private boolean isNotDrawableShape(String line) {
        if (isServerMessage(line))
            return true;

        return ifFirstShapeAddToList(line);
    }

    private static boolean isServerMessage(String line) {
        return line == null || line.contains("joined") || line.contains("left");
    }

    private boolean ifFirstShapeAddToList(String line) {
        if (getShapes().size() == 0) {
            shapes.add(ShapeFactory.svgToShape(line));
            return true;
        }
        return false;
    }

    private void addOrChangeShape(String line) {
        if (shapeExistsChangeInstead(line))
            return;
        shapes.add(ShapeFactory.svgToShape(line));
    }

    private boolean shapeExistsChangeInstead(String line) {
        var svgID = getCorrectSvgID(line);
        for (var shape : getShapes()) {
            if (shape.getSvgID().equals(svgID)) {
                changeExistingShapeAttributes(shape, line);
                return true;
            }
        }
        return false;
    }

    private String getCorrectSvgID(String line) {
        String svgID;
        if (line.contains("[you]"))
            svgID = line.substring(17, 53);
        else
            svgID = line.substring(29, 65);
        return svgID;
    }

    private void changeExistingShapeAttributes(Shape shape, String line) {
        var tempShape = ShapeFactory.svgToShape(line);
        shape.setSize(tempShape.getSize())
                .setX(tempShape.getX())
                .setY(tempShape.getY())
                .setColor(tempShape.getColor())
                .setBorderColor(tempShape.getColor());
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

    public void setSelectionMode(boolean option) {
        selectorOption.set(option);
    }

    public BooleanProperty selectorOptionProperty() {
        return selectorOption;
    }

    public StringProperty connectToServerProperty() {
        return connectToServer;
    }
}
