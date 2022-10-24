package se.iths.labboration3.labborationjavafx.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.function.Supplier;

public class PaintModel {
    private final BooleanProperty rectangle;
    private final BooleanProperty circle;


    public PaintModel(){
    this.rectangle  = new SimpleBooleanProperty(false);
    this.circle = new SimpleBooleanProperty(false);

    }

    public boolean isRectangle() {
        return rectangle.get();
    }


    public BooleanProperty rectangleProperty() {
        return rectangle;
    }

    public void setRectangle(boolean rectangle) {
        this.rectangle.set(rectangle);
    }

    public boolean isCircle() {
        return circle.get();
    }

    public BooleanProperty circleProperty() {
        return circle;
    }

    public void setCircle(boolean circle) {
        this.circle.set(circle);
    }
}
