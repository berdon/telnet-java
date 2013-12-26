package com.jajuka.telnet;

import naga.NIOSocket;

import java.nio.charset.Charset;

/**
 * Default socket implementation. Mostly a pass through to hide the NIOSocket layer.
 */
class DefaultSocket implements Socket {
    private static final String UTF_8 = "UTF-8";
    private NIOSocket mSocket;

    DefaultSocket(NIOSocket socket) {
        mSocket = socket;
    }

    @Override
    public void send(byte[] data) {
        mSocket.write(data);
    }

    @Override
    public void write(String data) {
        mSocket.write(data.getBytes(Charset.forName(UTF_8)));
    }

    @Override
    public void disconnect() {
        mSocket.closeAfterWrite();
        mSocket.write(null);
    }
}
