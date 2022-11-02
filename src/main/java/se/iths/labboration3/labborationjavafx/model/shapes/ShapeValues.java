package se.iths.labboration3.labborationjavafx.model.shapes;

import javafx.scene.canvas.GraphicsContext;
import se.iths.labboration3.labborationjavafx.model.Enums.SelectedShape;


public record ShapeValues(GraphicsContext context, double x, double y, double size, SelectedShape shape) {
}
