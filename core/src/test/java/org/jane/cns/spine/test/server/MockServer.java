package org.jane.cns.spine.test.server;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class MockServer implements Container {
    private static final int DEFAULT_CODE = 200;
    private static final String DEFAULT_MESSAGE = "{}";
    private int errorCode;
    private String returnMessage;
    private final Connection connection;
    private Request lastRequest;

    public MockServer() throws IOException {
        this.errorCode = DEFAULT_CODE;
        this.returnMessage = DEFAULT_MESSAGE;
        this.connection = new SocketConnection(new ContainerSocketProcessor(this));
    }

    public MockServer(int errorCode, String returnMessage) throws IOException {
        this.errorCode = errorCode;
        this.returnMessage = returnMessage;
        this.connection = new SocketConnection(new ContainerSocketProcessor(this));
    }

    public void setResponse(int code, String message) {
        this.errorCode = code;
        this.returnMessage = message;
    }

    public Request getLastRequest() {
        return lastRequest;
    }

    public int connectServerToPort() throws IOException {
        SocketAddress result = connection.connect(new InetSocketAddress(0)); // Pick any open port
        int port = ((InetSocketAddress) result).getPort();
        System.out.println("Fake server open in port " + port);
        return port;
    }

    public void closeServer() throws IOException {
        connection.close();
    }

    public void handle(Request request, Response response) {
        this.lastRequest = request;
        try {
            response.setCode(errorCode);
            response.setContentType("application/json");
            PrintStream body = response.getPrintStream();
            body.print(returnMessage);
            body.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
