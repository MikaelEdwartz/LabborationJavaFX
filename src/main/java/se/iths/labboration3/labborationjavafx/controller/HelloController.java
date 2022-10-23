package se.iths.labboration3.labborationjavafx.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class HelloController {
    public MenuButton loadSave;
    public MenuItem circle;
    public Button lineButton;
    public Button fill;
    public ColorPicker colorButton;
    public Canvas canvas;
    public GraphicsContext context;
    public ColorPicker colorPicker;
    public TextField brushSize;

    public void initialize(){
    context = canvas.getGraphicsContext2D();
    }

    @FXML
    protected void onHelloButtonClick() {
        canvas.setOnMouseDragged(event -> {
            double size = Double.parseDouble(brushSize.getText());
            double x = event.getX() - size / 2;
            double y = event.getY() - size / 2;

            context.setFill(colorPicker.getValue());
            context.fillOval(x,y, size, size);

        });
    }

    public void paintBrush() {
    }
}