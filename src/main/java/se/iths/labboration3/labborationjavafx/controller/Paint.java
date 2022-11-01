package se.iths.labboration3.labborationjavafx.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
        //model.getShapes().addListener((ListChangeListener<Shape>) onChange -> drawOnCanvas());
        model.addChangesToUndoList();
    }

    public void onCircleClick() {
        model.setSelectedShape(SelectedShape.CIRCLE);
    }

    public void onRectangleClick() {
        model.setSelectedShape(SelectedShape.RECTANGLE);
    }

    public void onCanvasClick(MouseEvent mouseEvent) {
        var mouseXY = new Point(mouseEvent.getX(), mouseEvent.getY());
        selectOrCreateShape(mouseXY);
        drawOnCanvas();
    }

    private void createAndAddNewShape(Point mouseXY) {
        var newShape = returnNewShape(mouseXY);
        model.addToShapes(newShape);
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
        model.getShapes().forEach(this::draw);
    }

    private void draw(Shape shape) {
        ShapeDrawer.draw(shape, context);
    }

    private void selectOrCreateShape(Point mouseXY) {
        if(selectorOption.get())
            ifSelected(mouseXY);
        else
            createAndAddNewShape(mouseXY);
    }

    private void ifSelected(Point mouseXY) {
        checkIfInsideShape(mouseXY);
    }

    private void checkIfInsideShape(Point mouseXY) {
        for (int i = 0; i < model.getShapes().size(); i++)
            checkIfSelectedIfInside(mouseXY, i);
    }

    private void checkIfSelectedIfInside(Point mouseXY, int i) {
        if(model.getShapes().get(i).isInside(mouseXY))
            model.checkIfSelectedAndAddOrRemove(i);
    }

    public void selection() {
        model.setSelectionMode();
    }

    public void undoLast() {
        model.removeLastChange();
        drawOnCanvas();

    }

    public void changeSize() {
        model.changeSelectedShapes(ChangeOption.SIZE);
        model.addChangesToUndoList();
        drawOnCanvas();
    }

    public void changeColor() {
        model.changeSelectedShapes(ChangeOption.COLOR);
        model.addChangesToUndoList();
        drawOnCanvas();
    }
}