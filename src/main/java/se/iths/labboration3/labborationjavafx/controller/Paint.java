package se.iths.labboration3.labborationjavafx.controller;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import se.iths.labboration3.labborationjavafx.FileSaver;
import se.iths.labboration3.labborationjavafx.model.Enums.ChangeOption;
import se.iths.labboration3.labborationjavafx.model.Enums.SelectedShape;
import se.iths.labboration3.labborationjavafx.model.PaintModel;
import se.iths.labboration3.labborationjavafx.model.Point;
import se.iths.labboration3.labborationjavafx.model.shapes.Shape;
import se.iths.labboration3.labborationjavafx.model.shapes.ShapeDrawer;

import static se.iths.labboration3.labborationjavafx.model.shapes.ShapeFactory.*;

public class Paint {
    public Canvas canvas;
    public GraphicsContext context;
    public PaintModel model;
    public BooleanProperty selectorOption;
    public ColorPicker colorPicker;
    public TextField size;
    public Button undoButton;
    private Stage stage;



    public Paint() {
        this.model = new PaintModel();
        this.selectorOption = new SimpleBooleanProperty();
        this.size = new TextField();
    }

    public void initialize() {
        context = canvas.getGraphicsContext2D();
        selectorOption.bindBidirectional(model.selectorOptionProperty());
        colorPicker.valueProperty().bindBidirectional(model.colorPickerProperty());
        size.textProperty().bindBidirectional(model.sizeProperty());
        model.getShapes().addListener((ListChangeListener<Shape>) onChange -> drawOnCanvas());
        model.addChangesToUndoList();
    }

    public void onCircleClick() {
        model.setSelectedShape(SelectedShape.CIRCLE);
    }

    public void onRectangleClick() {
        model.setSelectedShape(SelectedShape.RECTANGLE);
    }
    public void onSelectionClick() {
        model.setSelectionMode();
    }
    public void onCanvasClick(MouseEvent mouseEvent) {
        var mouseXY = new Point(mouseEvent.getX(), mouseEvent.getY());
        selectOrCreateShape(mouseXY);
        drawOnCanvas();

    }
    private void selectOrCreateShape(Point mouseXY) {
        if(selectorOption.get())
            checkIfInsideShape(mouseXY);
        else
            createAndAddNewShape(mouseXY);
    }

    private void createAndAddNewShape(Point mouseXY) {
        var newShape = returnNewShape(mouseXY);
        model.checkIfConnectedAndAddToShapes(newShape);
        model.addChangesToUndoList();
    }

    private Shape returnNewShape(Point xy) {
        return shapeOf(colorPicker.getValue(), xy, model.getSizeAsDouble(), model.getSelectedShape());
    }

    public void drawOnCanvas(){
        clearCanvas();
        drawAllSavedShapesOnCanvas();
    }

    private void clearCanvas() {
        context.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawAllSavedShapesOnCanvas() {
        model.getShapes().forEach(this::drawShapeOnCanvas);
    }

    private void drawShapeOnCanvas(Shape shape) {
        ShapeDrawer.draw(shape, context);
    }

    private void checkIfInsideShape(Point mouseXY) {
        for (int i = 0; i < model.getShapes().size(); i++)
            checkIfSelectedIsInside(mouseXY, i);
    }

    private void checkIfSelectedIsInside(Point mouseXY, int i) {
        if(model.getShapes().get(i).insideShape(mouseXY))
            model.checkIfSelectedAndAddOrRemove(i);
    }

    public void undoLast() {
        model.removeLastChange();
    }

    public void changeSize() {
        model.changeSelectedShapes(ChangeOption.SIZE);
        model.addChangesToUndoList();
    }

    public void changeColor() {
        model.connectToServer();
        //model.changeSelectedShapes(ChangeOption.COLOR);
        //model.addChangesToUndoList();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void save() {
        new FileSaver().save(model, stage);
    }

    public void connectToServer() {
        Thread.ofPlatform().start(() -> {
            Platform.runLater(model.connectToServer());
        });
    }

}