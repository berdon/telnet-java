package com.jajuka.telnet;

import naga.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Java telnet server implementation with listener pattern for connection/data interaction.
 */
public class TelnetServer {
    private static final String UTF_8 = "UTF-8";

    /**
     * Default hostname.
     */
    private static final String DEFAULT_HOSTNAME = "localhost";
    /**
     * Default port. 0 tells ServerSocket to select one.
     */
    private static final int DEFAULT_PORT = 0;
    /**
     * Default socket backlog of 50 - same as ServerSocket.
     */
    private static final int DEFAULT_BACKLOG = 50;

    private static Logger sLogger = Logger.getInstance();
    private NIOService mNIOService;
    private NIOServerSocket mNIOServerSocket;
    private String mHostname;
    private int mPort;
    private int mBacklog;
    private ExecutorService mExecutor;

    private SocketObserver mSocketObserver;
    private Map<NIOSocket, Socket> mSocketMap = new HashMap<NIOSocket, Socket>();

    public TelnetServer(String hostname, int port, int backlog) {
        mHostname = hostname;
        mPort = port;
        mBacklog = backlog;
    }

    public void start() {
        sLogger.info("Starting TelnetServer");
        mExecutor = Executors.newSingleThreadExecutor();
        mExecutor.submit(new ServerRunnable());
    }

    public void stop() {
        sLogger.info("Killing TelnetServer");
        mExecutor.shutdown();
        mExecutor = null;
        mSocketMap.clear();
    }

    public String getHostname() {
        return mHostname;
    }

    public int getPort() {
        return mPort;
    }

    public int getBacklog() {
        return mBacklog;
    }

    public SocketObserver getSocketObserver() {
        return mSocketObserver;
    }

    public void setSocketObserver(SocketObserver socketObserver) {
        mSocketObserver = socketObserver;
    }

    private class ServerRunnable implements Runnable {
        @Override
        public void run() {
            // Create the socket server
            sLogger.info("*** Creating server socket");
            try {
                mNIOService = new NIOService();
                mNIOServerSocket = mNIOService.openServerSocket(new InetSocketAddress(mHostname, mPort), mBacklog);
            } catch (IOException e) {
                throw new RuntimeException("Unable to resolve hostname");
            }

            // Bind to the requested hostname:port
            sLogger.info(String.format("*** Binding to %s:%d", mHostname, mPort));
            try {
                mNIOServerSocket.setConnectionAcceptor(ConnectionAcceptor.ALLOW);
                mNIOServerSocket.listen(mNIOServerObserver);
                if (mNIOServerSocket.isOpen() == false) {
                    throw new RuntimeException(String.format("Unable to listen to %s:%d", mHostname, mPort));
                }
                while (true) {
                    mNIOService.selectBlocking();
                }
            } catch (Exception e) {
                sLogger.error(String.format("*** Unable to bind to %s:%d", mHostname, mPort));
            }
        }
    }

    private ServerSocketObserver mNIOServerObserver = new ServerSocketObserver() {
        @Override
        public void acceptFailed(IOException e) {
            sLogger.warn("*** Failed to accept socket");
        }

        @Override
        public void serverSocketDied(Exception e) {
            sLogger.warn("*** Server died!");
        }

        @Override
        public void newConnection(NIOSocket nioSocket) {
            sLogger.info("*** Socket connection established");
            nioSocket.listen(mNIOSocketObserver);
        }
    };

    private naga.SocketObserver mNIOSocketObserver = new naga.SocketObserver() {
        @Override
        public void connectionOpened(NIOSocket nioSocket) {
            Socket socket = mSocketMap.get(nioSocket);
            if (socket == null) {
                socket = new DefaultSocket(nioSocket);
                mSocketMap.put(nioSocket, socket);
            }

            // Setup the initialization state for terminal negotiation
            // TODO : austinh : read the Telnet RFCs to get a handle on commands/options

            if (mSocketObserver != null) {
                mSocketObserver.onConnect(socket);
            }
        }

        @Override
        public void connectionBroken(NIOSocket nioSocket, Exception e) {
            mSocketMap.remove(nioSocket);
        }

        @Override
        public void packetReceived(NIOSocket nioSocket, byte[] bytes) {
            Socket socket = mSocketMap.get(nioSocket);
            if (socket != null) {
                if (bytes[0] == Command.IAC.value()) {
                    // Ignore for right now
                    sLogger.info("Client issued telnet instruction!");
                } else if (mSocketObserver != null) {
                    try {
                        mSocketObserver.onData(socket, new String(bytes, UTF_8));
                    } catch (UnsupportedEncodingException e) {
                        sLogger.warn("Invalid UTF-8 data");
                    }
                }
            } else {
                sLogger.warn("Incoming data from unknown socket!");
            }
        }

        @Override
        public void packetSent(NIOSocket nioSocket, Object o) {

        }
    };
}
