package se.iths.labboration3.labborationjavafx.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import se.iths.labboration3.labborationjavafx.model.PaintModel;
import se.iths.labboration3.labborationjavafx.shape.ShapeCreator;

public class HelloController {
    public Canvas canvas;
    public GraphicsContext context;
    public ColorPicker colorPicker;
    public TextField brushSize;
    public PaintModel model;
    public ToggleButton eraser;
    public BooleanProperty circle;
    public BooleanProperty rectangle;
    public ShapeCreator shapeCreator;


    public HelloController() {
        this.model = new PaintModel();
        this.circle = new SimpleBooleanProperty();
        this.rectangle = new SimpleBooleanProperty();
        this.shapeCreator = new ShapeCreator();

    }

    public void initialize() {
        context = canvas.getGraphicsContext2D();
        circle.bindBidirectional(model.circleProperty());
        rectangle.bindBidirectional(model.rectangleProperty());
    }

    public void paintCircle(MouseEvent mouseEvent) {
        double size = Double.parseDouble(brushSize.getText());
        double x = mouseEvent.getX() - size / 2;
        double y = mouseEvent.getY() - size / 2;
        context.setFill(colorPicker.getValue());
        context.fillOval(x, y, size, size);
        shapeCreator.drawCircle(colorPicker.getValue(), x, y, size);
    }

    public void paintRectangle(MouseEvent mouseEvent) {
        double size = Double.parseDouble(brushSize.getText());
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

    public void drawShape(MouseEvent mouseEvent) {
        if (circle.get())
            paintCircle(mouseEvent);
        if (rectangle.get())
            paintRectangle(mouseEvent);
    }

}