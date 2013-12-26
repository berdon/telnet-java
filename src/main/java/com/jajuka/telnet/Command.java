package com.jajuka.telnet;

import java.util.EnumSet;

/**
 * Telnet option command codes
 */
public enum Command implements Instruction{
    /**
     * SEND
     */
    SEND(0x01),
    /**
     * End of subnegotiation parameters
     */
    SE(0xF0),
    /**
     * No operation
     */
    NOP(0xF1),
    /**
     * Data mark
     */
    DM(0xF2),
    /**
     * Break
     */
    BRK(0xF3),
    /**
     * Suspend
     */
    IP(0xF4),
    /**
     * This is used to suppress the transmission of remote process output. An AO (238) character is included in the data
     * stream.
     */
    ABORT_OUTPUT(0xF5),
    /**
     * Are you there
     */
    AYT(0xF6),
    /**
     * Erace character
     */
    EC(0xF7),
    /**
     * Erase line
     */
    EL(0xF8),
    /**
     * Go ahead
     */
    GA(0xF9),
    /**
     * Subnegotiation
     */
    SB(0xFA),
    /**
     * Will
     */
    WILL(0xFB),
    /**
     * Won't
     */
    WONT(0xFC),
    /**
     * Do
     */
    DO(0xFD),
    /**
     * Don't
     */
    DONT(0xFE),
    /**
     * Interpret as command
     */
    IAC(0xFF);

    private byte mValue;

    public byte value() {
        return mValue;
    }

    Command(int value) {
        mValue = (byte) value;
    }
}
