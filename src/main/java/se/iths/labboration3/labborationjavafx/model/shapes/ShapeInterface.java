package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import se.iths.labboration3.labborationjavafx.model.Point;

public interface ShapeInterface {
    void draw(GraphicsContext context);
    boolean isInside(Point coordinates);
}
