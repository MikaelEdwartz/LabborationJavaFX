package se.iths.labboration3.labborationjavafx;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import se.iths.labboration3.labborationjavafx.model.PaintModel;
import se.iths.labboration3.labborationjavafx.model.shapes.Shape;

import java.io.*;
import java.net.Socket;


public class Server {
    private Socket socket;
    private PaintModel model;
    private PrintWriter writer;
    private BufferedReader reader;
    private final BooleanProperty connected = new SimpleBooleanProperty(false);


    public void connect(PaintModel model) {
        this.model = model;
        if (connected.get()) {
            connected.set(false);
            return;
        }

        try {
            initializeServer();
            readFromNetwork();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private void initializeServer() throws IOException {
        socket = new Socket("127.0.0.1", 8000);
        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
        InputStream input = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));
        connected.set(true);
    }

    private void readFromNetwork() {
        Thread.ofPlatform().start(() -> {
            try {
                while (true) {
                    String line = reader.readLine();
                    System.out.println(line);
                    Platform.runLater(() -> model.addToShapes(line));
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
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
