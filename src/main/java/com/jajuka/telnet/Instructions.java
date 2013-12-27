package com.jajuka.telnet;

/**
 * Helper class for dealing with {@link com.jajuka.telnet.Instruction}s.
 */
public final class Instructions {
    private Instructions() { }

    public static byte[] asArray(Instruction... instruction) {
        byte[] data = new byte[instruction.length];
        for (int i = 0; i < instruction.length; i++) {
            data[i] = instruction[i].value();
        }

        return data;
    }
}
