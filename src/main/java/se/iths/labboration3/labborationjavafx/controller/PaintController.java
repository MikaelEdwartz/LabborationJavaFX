package se.iths.labboration3.labborationjavafx.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import se.iths.labboration3.labborationjavafx.model.ChangeOption;
import se.iths.labboration3.labborationjavafx.model.PaintModel;
import se.iths.labboration3.labborationjavafx.model.Point;
import se.iths.labboration3.labborationjavafx.model.shapes.Shape;
import se.iths.labboration3.labborationjavafx.model.shapes.shapeFactory;

import static se.iths.labboration3.labborationjavafx.model.shapes.shapeFactory.*;

public class PaintController {
    public Canvas canvas;
    public GraphicsContext context;
    public PaintModel model;
    public BooleanProperty selectorOption;
    public shapeFactory shapeFactory;
    public ColorPicker colorPicker;
    public TextField size;
    public Menu saveOption;
    static int i = 0;
    public Button undoButton;
    public Button changeSize;


    public PaintController() {
        this.model = new PaintModel();
        this.selectorOption = new SimpleBooleanProperty();
        this.size = new TextField();
        this.shapeFactory = new shapeFactory();
    }

    public void initialize() {
        context = canvas.getGraphicsContext2D();
        selectorOption.bindBidirectional(model.selectorOptionProperty());
        colorPicker.valueProperty().bindBidirectional(model.colorPickerProperty());
        size.textProperty().bindBidirectional(model.sizeProperty());
        model.getShapes().addListener((ListChangeListener<Shape>) onChange -> drawOnCanvas());
        model.getShapes().addListener((ListChangeListener<Shape>) onChange -> addToUndoList(onChange));
    }

    private void addToUndoList(ListChangeListener.Change<? extends Shape> shape) {

        model.addToUndoListChanges(shape);
    }


    public void onCircleClick() {
        model.setCircleShape();
    }

    public void onRectangleClick() {
        model.setRectangleShape();
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
        shape.draw(context);
    }



    public void onCanvasClick(MouseEvent mouseEvent) {
        var mouseXY = new Point(mouseEvent.getX(), mouseEvent.getY());

        if(selectorOption.get())
            checkifInsideCircle(mouseXY);
        else
            model.addToShapes(returnNewShape(mouseXY));
    }

    private void checkifInsideCircle(Point mouseXY) {
        for (int i = 0; i < model.getShapes().size(); i++)
            if(model.getShapes().get(i).isInside(mouseXY))
                model.checkIfSelectedAndAddOrRemove(i);
    }

    private Shape returnNewShape(Point xy) {
        return shapeOf(colorPicker.getValue(), xy, model.getSize(), model.getSelectedShape());
    }



    public void savePainting(ActionEvent actionEvent) {
        System.out.println("Saved");
    }



    private void checkifInside() {

    }

    public void selection() {
        model.setSelectionMode();
    }


    public void undoLast(ActionEvent actionEvent) {
        model.removeLastChange();
    }

    public void changeSize() {
        model.changeSelectedShapes(ChangeOption.SIZE);
    }

    public void changeColor() {
        model.changeSelectedShapes(ChangeOption.COLOR);
    }
}