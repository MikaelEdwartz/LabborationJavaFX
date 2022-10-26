package se.iths.labboration3.labborationjavafx.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import se.iths.labboration3.labborationjavafx.model.PaintModel;
import se.iths.labboration3.labborationjavafx.model.shapes.Shape;
import se.iths.labboration3.labborationjavafx.model.shapes.shapeFactory;

import static se.iths.labboration3.labborationjavafx.model.shapes.shapeFactory.*;

public class PaintController {
    public Canvas canvas;
    public GraphicsContext context;
    public PaintModel model;
    public BooleanProperty circle;
    public BooleanProperty rectangle;
    public shapeFactory shapeFactory;
    public ColorPicker colorPicker;
    public TextField size;



    public PaintController() {
        this.model = new PaintModel();
        this.circle = new SimpleBooleanProperty();
        this.rectangle = new SimpleBooleanProperty();
        this.size = new TextField();
        this.shapeFactory = new shapeFactory();

    }

    public void initialize() {
        context = canvas.getGraphicsContext2D();
        circle.bindBidirectional(model.circleProperty());
        rectangle.bindBidirectional(model.rectangleProperty());
        colorPicker.valueProperty().bindBidirectional(model.colorPickerProperty());
        size.textProperty().bindBidirectional(model.sizeProperty());
        model.getShapes().addListener((ListChangeListener<Shape>) onChange -> drawOnCanvas());
    }

    public void paintCircle(MouseEvent mouseEvent) {

    }

    public void paintRectangle(MouseEvent mouseEvent) {
        double size = Double.parseDouble(this.size.getText());
        double x = mouseEvent.getX() - size / 2;
        double y = mouseEvent.getY() - size / 2;
        context.setFill(colorPicker.getValue());
        context.fillRect(x, y, size * 1.75, size);

    }

    public void onCircleClick() {
        model.setCircleShape();
    }

    public void onRectangleClick() {
        model.setRectangleShape();
    }

    public void drawOnCanvas(){
        clearCanvas();
        paintAllSavedShapesOnCanvas();

    }

    private void paintAllSavedShapesOnCanvas() {
        for(var shape : model.getShapes())
            shape.draw(context);
    }

    private void clearCanvas() {
        context.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawShape(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        Shape newShape = null;

        if (circle.get()) {
             newShape = circleOf(colorPicker.getValue(), x, y, model.getSize());
        }

        if (rectangle.get()) {
             newShape = rectangleOf(colorPicker.getValue(), x, y, model.getSize());
        }
        model.addToShapes(newShape);

    }


    public void print(ActionEvent actionEvent) {
        clearCanvas();

        for(var m : model.getShapes())
            System.out.println( "" + m.getX() + "-" + m.getY()+ "-" + m.getColor() +"-"+ m.getClass());
    }
}