package com.jajuka.telnet.example;

import com.jajuka.telnet.Logger;
import com.jajuka.telnet.Socket;
import com.jajuka.telnet.SocketObserver;
import com.jajuka.telnet.TelnetServer;

/**
 * Simple example that uses the Telnet server to echo all responses back
 */
public class EchoServer {
    public static void main(String[] args) {
        EchoServer echoServer = new EchoServer();

        // Create a server
        final TelnetServer server = new TelnetServer("127.0.0.1", 1337, 50);

        // Set the socket observer
        server.setSocketObserver(new SocketObserver() {
            @Override
            public void onConnect(Socket socket) {
                Logger.getInstance().info("--- Socket connected");
            }

            @Override
            public void onDisconnect(Socket socket) {
                Logger.getInstance().info("--- Socket disconnected");
            }

            @Override
            public void onData(Socket socket, String data) {
                Logger.getInstance().info(String.format("--- Socket sent data: %s", data));
                socket.write(data);
            }
        });

        // Start the server
        server.start();

        // Let's wait around indefinitely!
        while (true) ;
    }
}
