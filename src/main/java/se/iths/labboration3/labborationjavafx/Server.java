package se.iths.labboration3.labborationjavafx;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import se.iths.labboration3.labborationjavafx.model.PaintModel;
import se.iths.labboration3.labborationjavafx.model.shapes.Shape;

import java.io.*;
import java.net.Socket;


public class Server {
    private PaintModel model;
    private PrintWriter writer;
    private BufferedReader reader;
    private final BooleanProperty connected = new SimpleBooleanProperty(false);

    public void connect(PaintModel model) {
        this.model = model;
        if (connectedDisconnect())
            return;
        runServer();
    }

    private void runServer() {
        try {
            initializeServer();
            Thread.ofPlatform().start(this::readFromNetwork);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean connectedDisconnect() {
        if (isConnected()) {
            connected.set(false);
            return true;
        }
        return isConnected();
    }

    private void initializeServer() throws IOException {
        Socket socket = new Socket("localhost", 8000);
        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
        InputStream input = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));
        connected.set(true);
    }

    private void readFromNetwork() {
        try {
            while (true) {
                saveShapesFromNetworkToList();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void saveShapesFromNetworkToList() throws IOException {
        String line = reader.readLine();
        if (line == null || line.contains("joined") || line.contains("left"))
            return;

        Platform.runLater(() -> model.addToShapes(line));
    }

    public void sendToServer(Shape shape) {
        writer.println(shape.getAsSVG());
    }

    public PaintModel getModel() {
        return model;
    }

    public void setModel(PaintModel model) {
        this.model = model;
    }

    public boolean isConnected() {
        return connected.get();
    }
}
