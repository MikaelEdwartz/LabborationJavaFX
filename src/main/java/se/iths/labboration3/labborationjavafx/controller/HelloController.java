package se.iths.labboration3.labborationjavafx.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import se.iths.labboration3.labborationjavafx.HelloApplication;
import se.iths.labboration3.labborationjavafx.model.PaintModel;

public class HelloController {
    public Canvas canvas;
    public GraphicsContext context;
    public ColorPicker colorPicker;
    public TextField brushSize;
    public PaintModel model;
    public ToggleButton eraser;
    public BooleanProperty circle;
    public BooleanProperty rectangle;


    public HelloController(){
        this.model = new PaintModel();
        this.circle = new SimpleBooleanProperty();
        this.rectangle = new SimpleBooleanProperty();

    }

    public void initialize() {
        context = canvas.getGraphicsContext2D();
        circle.bindBidirectional(model.circleProperty());
        rectangle.bindBidirectional(model.rectangleProperty());
    }


    private void getBrushSizeAndColor(MouseEvent event) {
    }


    public void paintCircle(MouseEvent mouseEvent) {
        double size = Double.parseDouble(brushSize.getText());
        double x = mouseEvent.getX() - size / 2;
        double y = mouseEvent.getY() - size / 2;
        context.setFill(colorPicker.getValue());
        context.fillOval(x, y, size, size);
     }

    public void paintRectangle(MouseEvent mouseEvent) {

            double size = Double.parseDouble(brushSize.getText());
            double x = mouseEvent.getX() - size / 2;
            double y = mouseEvent.getY() - size / 2;
            context.setFill(colorPicker.getValue());
            context.fillRect(x, y, size *1.75, size);
    }

    public void paintShape(MouseEvent mouseEvent) {

    }

    public void setCircleShape() {
        circle.set(true);
        rectangle.set(false);
    }

    public void setRectangleShape() {
        circle.set(false);
        rectangle.set(true);
    }

    public void drawShape(MouseEvent mouseEvent) {
        if(circle.get())
            paintCircle(mouseEvent);
        if(rectangle.get())
            paintRectangle(mouseEvent);
    }


}