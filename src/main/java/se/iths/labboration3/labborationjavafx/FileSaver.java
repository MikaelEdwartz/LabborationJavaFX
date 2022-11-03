package se.iths.labboration3.labborationjavafx;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.iths.labboration3.labborationjavafx.model.PaintModel;
import se.iths.labboration3.labborationjavafx.model.shapes.Shape;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileSaver {

    FileChooser fileChooser = new FileChooser();

    public void save(PaintModel model, Stage stage) {
        setUpSaveWindow();
        Path path = fileChooser.showSaveDialog(stage.getOwner()).toPath();
        writeSVGToPath(model, path);
    }

    private void setUpSaveWindow() {
        fileChooser.setInitialFileName("Painting");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SVG", "*.svg"));
    }

    private void writeSVGToPath(PaintModel model, Path path) {
        try {
            Files.write(path, getSVGAsStrings(model));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<String> getSVGAsStrings(PaintModel model) {
        List<String> svg = new ArrayList<>();
        getCompleteSVGString(model, svg);
        return svg;
    }

    private void getCompleteSVGString(PaintModel model, List<String> svg) {
        svg.add("<svg width=\"1540.0\" height=\"740.0\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">");
        addAllShapes(svg, model);
        svg.add("</svg>");
    }

    private void addAllShapes(List<String> svg, PaintModel model) {
        model.getShapes().forEach(shape -> addShapeAsSVG(svg, shape));
    }

    private void addShapeAsSVG(List<String> svg, Shape shape) {
        svg.add(shape.getAsSVG());
    }
}
