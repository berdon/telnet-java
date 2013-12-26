package com.jajuka.telnet;

/**
 * Created by austinh on 12/25/13.
 */
public abstract class LineDelimittedSocketObserver implements SocketObserver {
    @Override
    public void onData(Socket socket, String data) {
        onLine(socket, data.replaceAll("(\r)?(\n)?$", ""));
    }

    public abstract void onLine(Socket socket, String data);
}
