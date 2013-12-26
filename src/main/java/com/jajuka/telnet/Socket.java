package com.jajuka.telnet;

import java.net.InetSocketAddress;

/**
 * Created by austinh on 12/25/13.
 */
public interface Socket {
    void write(byte[] data);
    void write(String data);
    void write(String data, Object... args);
    void writeln(String data);
    void writeln(String data, Object... args);
    void disconnect();
    InetSocketAddress getAddress();
}
