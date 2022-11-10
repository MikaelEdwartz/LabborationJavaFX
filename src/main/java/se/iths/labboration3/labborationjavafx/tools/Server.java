package se.iths.labboration3.labborationjavafx.tools;

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
        if (ifConnectedThenDisconnect())
            return;
        runServer();
    }

    private boolean ifConnectedThenDisconnect() {
        if (isConnected()) {
            connected.set(false);
            return true;
        }
        return false;
    }

    public boolean isConnected() {
        return connected.get();
    }

    private void runServer() {
        try {
            initializeServer();
            Thread.ofPlatform().start(this::readFromNetwork);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initializeServer() throws IOException {
        var socket = new Socket("localhost", 8000);
        var output = socket.getOutputStream();
        var input = socket.getInputStream();
        writer = new PrintWriter(output, true);
        reader = new BufferedReader(new InputStreamReader(input));
        connected.set(true);
    }

    private void readFromNetwork() {
        try {
            while (true)
                saveShapesFromNetworkToList();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void saveShapesFromNetworkToList() throws IOException {
        var line = reader.readLine();
        if (line == null || line.contains("joined") || line.contains("left"))
            return;

        Platform.runLater(() -> model.readFromServer(line));
    }

    public void sendToServer(Shape shape) {
        writer.println(shape.getAsSVG());
    }

    public void sendToServer(String string) {
        writer.println(string);
    }

    public PaintModel getModel() {
        return model;
    }

    public void setModel(PaintModel model) {
        this.model = model;
    }

}
