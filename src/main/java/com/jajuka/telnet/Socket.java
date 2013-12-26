package com.jajuka.telnet;

/**
 * Created by austinh on 12/25/13.
 */
public interface Socket {
    void send(byte[] data);
    void write(String data);
    void disconnect();
}
