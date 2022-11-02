package se.iths.labboration3.labborationjavafx.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
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
        model.changeSelectedShapes(ChangeOption.COLOR);
        model.addChangesToUndoList();
    }
}
/*public static void draw(Shape shape, GraphicsContext context){
        switch (shape.getShape()) {
            case CIRCLE ->   drawCircle(shape, context);
            case RECTANGLE -> drawRectangle(shape, context);
        }
    }

    public static void drawCircle(Shape circle, GraphicsContext context){
        double size = circle.getSize();
        double x = circle.getX() - size / 2;
        double y = circle.getY() - size / 2;

        drawBorder(circle, context, size, x, y);
        fillInsideBorder(circle, context, size, x, y);
    }

    private static void drawBorder(Shape circle, GraphicsContext context, double size, double x, double y) {
        context.setFill(circle.getBorderColor());
        context.fillOval(x - 2.5, y - 2.5, size + 5, size + 5);
    }

    private static void fillInsideBorder(Shape circle, GraphicsContext context, double size, double x, double y) {
        context.setFill(circle.getColor());
        context.fillOval(x, y, size, size);
    }

    public static void drawRectangle(Shape rectangle, GraphicsContext context) {
        double size = rectangle.getSize();
        double x = rectangle.getX() - size / 2 * 1.75;
        double y = rectangle.getY() - size / 2 ;

        drawBorder(context, size, x, y, rectangle.getBorderColor());
        fillInsideBorder(context, size, x, y, rectangle.getColor());
    }

    private static void fillInsideBorder(GraphicsContext context, double size, double x, double y, Color color) {
        context.setFill(color);
        context.fillRect(x, y, size * 1.75, size);
    }

    private static void drawBorder(GraphicsContext context, double size, double x, double y, Color color) {
        context.setFill(color);
        context.fillRect(x -2.5, y -2.5, size * 1.75 + 5, size + 5);
    }
* */