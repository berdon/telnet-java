package com.jajuka.telnet;

/**
 * Telnet option codes.
 */
enum Option implements Instruction {
    /**
     * The original Telnet implementation defaulted to half duplex operation. This means that data traffic could only go
     * in one direction at a time and specific action is required to indicate the end of traffic in one direction and
     * that traffic may now start in the other direction. [This similar to the use of "roger" and "over" by amateur and
     * CB radio operators.] The specific action is the inclusion of a GA character in the data stream.<br /><br />
     * Modern links normally allow bi-directional operation and the "suppress go ahead" option is enabled.
     */
    SUPRESS_GO_AHEAD(0x03),
    /**
     * The echo option is enabled, usually by the server, to indicate that the server echos every character it receives.
     * A combination of "suppress go ahead" and "echo" is called character-at-a-time mode meaning that each character is
     * separately transmitted and echoed.<br /><br />
     * There is an understanding known as kludge-line mode, which means that if either "suppress go ahead" or "echo" is
     * enabled but not both, then Telnet operates in line-at-a-time mode meaning that complete lines are assembled at
     * each end and transmitted in one "go".
     */
    ECHO(0x01),
    /**
     *
     */
    STATUS(0x05),
    TIMING_MARK(0x06),
    TERMINAL_TYPE(0x18),
    WINDOW_SIZE(0x01F),
    TERMINAL_SPEED(0x20),
    REMOTE_FLOW_CONTROL(0x21),
    /**
     * This option replaces and supersedes the line mode kludge.
     */
    LINEMODE(0x22),
    ENVIRONMENT_VARIABLES(0x24);

    private byte mValue;

    public byte value() {
        return mValue;
    }

    Option(int value) {
        mValue = (byte) value;
    }
}
