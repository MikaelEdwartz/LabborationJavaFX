package se.iths.labboration3.labborationjavafx.model;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import se.iths.labboration3.labborationjavafx.model.Enums.SelectedShape;
import se.iths.labboration3.labborationjavafx.model.shapes.Rectangle;

import static org.junit.jupiter.api.Assertions.*;

class PaintModelTest {
    public static PaintModel paintModel = new PaintModel();

    @Test
    void setCircleChangesSelectionModeToCircle(){
        paintModel.setSelectedShape(SelectedShape.CIRCLE);
        assertEquals(paintModel.getSelectedShape(), SelectedShape.CIRCLE);
    }

    @Test
    void setRectangleChangesSelectionModeToCircle(){
        paintModel.setSelectedShape(SelectedShape.RECTANGLE);
        assertEquals(paintModel.getSelectedShape(), SelectedShape.RECTANGLE);

    }

    @Test
    void addToShapesIncreasesShapeListSizeBy1(){
        var oldShapeSize = paintModel.getShapes().size();
        paintModel.getShapes().add(new Rectangle(Color.RED,new Point(1,1), 1, SelectedShape.RECTANGLE));
        var newShapeSize = paintModel.getShapes().size();
        assertEquals(newShapeSize, (oldShapeSize + 1));
    }


}