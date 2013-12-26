package com.jajuka.telnet;

import naga.NIOSocket;

import java.net.InetAddress;
import java.net.InetSocketAddress;
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
    public void write(byte[] data) {
        mSocket.write(data);
    }

    @Override
    public void write(String data) {
        mSocket.write(data.getBytes(Charset.forName(UTF_8)));
    }

    @Override
    public void write(String data, Object... args) {
        mSocket.write(String.format(data, args).getBytes(Charset.forName(UTF_8)));
    }

    @Override
    public void writeln(String data) {
        mSocket.write((data + "\r\n").getBytes(Charset.forName(UTF_8)));
    }

    @Override
    public void writeln(String data, Object... args) {
        mSocket.write(String.format(data + "\r\n", args).getBytes(Charset.forName(UTF_8)));
    }

    @Override
    public void disconnect() {
        mSocket.closeAfterWrite();
        mSocket.write(null);
    }

    @Override
    public InetSocketAddress getAddress() {
        return mSocket.getAddress();
    }
}
