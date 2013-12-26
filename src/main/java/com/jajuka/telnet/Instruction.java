package com.jajuka.telnet;

/**
 * Interface to relate {@link com.jajuka.telnet.Command} and {@link com.jajuka.telnet.Option} while keeping them
 * separate for contextual reasons.
 */
public interface Instruction {
    byte value();
}
