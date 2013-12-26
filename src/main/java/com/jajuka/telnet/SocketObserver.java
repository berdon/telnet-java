package com.jajuka.telnet;

/**
 * Created by austinh on 12/25/13.
 */
public interface SocketObserver {
    void onConnect(Socket socket);
    void onDisconnect(Socket socket);
    void onData(Socket socket, String data);
}
