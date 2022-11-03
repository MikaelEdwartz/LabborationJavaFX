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
        if (connected.get()) {
            connected.set(false);
            return true;
        }
        return false;
    }

    private void initializeServer() throws IOException {
        Socket socket = new Socket("127.0.0.1", 8000);
        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
        InputStream input = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));
        connected.set(true);
    }

    private void readFromNetwork() {
        try {
            while (true) {
                sendShapesToServer();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void sendShapesToServer() throws IOException {
        String line = reader.readLine();
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

    public PrintWriter getWriter() {
        return writer;
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public boolean isConnected() {
        return connected.get();
    }

    public BooleanProperty connectedProperty() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected.set(connected);
    }

}
