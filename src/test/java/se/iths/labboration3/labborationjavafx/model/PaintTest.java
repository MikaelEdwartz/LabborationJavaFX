package se.iths.labboration3.labborationjavafx.model;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import se.iths.labboration3.labborationjavafx.model.Enums.SelectedShapeToDraw;
import se.iths.labboration3.labborationjavafx.model.shapes.Rectangle;

import static org.junit.jupiter.api.Assertions.*;

class PaintTest {
    public static Paint paintModel = new Paint();

    @Test
    void setCircleChangesSelectionModeToCircle(){
        paintModel.setSelectedShape(SelectedShapeToDraw.CIRCLE);
        assertEquals(paintModel.getSelectedShape(), SelectedShapeToDraw.CIRCLE);
    }

    @Test
    void setRectangleChangesSelectionModeToCircle(){
        paintModel.setSelectedShape(SelectedShapeToDraw.RECTANGLE);
        assertEquals(paintModel.getSelectedShape(), SelectedShapeToDraw.RECTANGLE);

    }

    @Test
    void addToShapesIncreasesShapeListSizeBy1(){
        var oldShapeSize = paintModel.getShapes().size();
        paintModel.getShapes().add(new Rectangle(Color.RED,new Point(1,1), 1));
        var newShapeSize = paintModel.getShapes().size();
        assertEquals(newShapeSize, (oldShapeSize + 1));
    }


}