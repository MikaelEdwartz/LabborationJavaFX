package se.iths.labboration3.labborationjavafx.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import se.iths.labboration3.labborationjavafx.model.PaintModel;
import se.iths.labboration3.labborationjavafx.model.Point;
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
    public Menu saveOption;
    static int i = 0;


    public PaintController() {
        this.model = new PaintModel();
        this.circle = new SimpleBooleanProperty();
        this.rectangle = new SimpleBooleanProperty();
        this.size = new TextField();
        this.shapeFactory = new shapeFactory();
    }

    public void initialize() {
        context = canvas.getGraphicsContext2D();
        circle.bindBidirectional(model.circleSelectedProperty());
        rectangle.bindBidirectional(model.rectangleSelectedProperty());
        colorPicker.valueProperty().bindBidirectional(model.colorPickerProperty());
        size.textProperty().bindBidirectional(model.sizeProperty());
        model.getShapes().addListener((ListChangeListener<Shape>) onChange -> drawOnCanvas());
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

    private void drawAllSavedShapesOnCanvas() {
        for(var shape : model.getShapes())
            shape.draw(context);


    }

    private void clearCanvas() {
        context.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawShape(MouseEvent mouseEvent) {
        var xy = new Point(mouseEvent.getX(), mouseEvent.getY());

        if(!model.getRectangleSelected() && !model.getCircleSelected()) {
            for(var shapes : model.getShapes())
                if(shapes.isInside(xy))
                    System.out.println(xy + " " +shapes);
        }

        Shape newShape = returnNewShape(xy);
        model.addToShapes(newShape);

    }

    private Shape returnNewShape(Point xy) {
        if (circle.get())
            return circleOf(colorPicker.getValue(), xy, model.getSize());
        if (rectangle.get())
            return rectangleOf(colorPicker.getValue(), xy, model.getSize());

        return null;
    }


    public void print(ActionEvent actionEvent) {
        model.getShapes().get(i++).setColor(colorPicker.getValue());
        context.setFill(Color.RED);
        context.fillRect(model.getShapes().get(0).getX(),model.getShapes().get(0).getY(),model.getShapes().get(0).getSize(),model.getShapes().get(0).getSize());
        for(var m : model.getShapes())
            System.out.println( "X1:" + m.getX() + "  Y1:"
                    + m.getY()+ "  X2:" + m.getSize()*1.75
                    +"   Y2:"+ m.getSize());
    }

    public void savePainting(ActionEvent actionEvent) {
        System.out.println("Saved");
    }



    private void checkifInside() {

    }

    public void selection(ActionEvent actionEvent) {
        model.setSelectionMode();
    }
}