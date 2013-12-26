package com.jajuka.telnet;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Simple logger until I hookup log4j properly
 */
public class Logger {
    private static final class Loader {
        private static final Logger sInstance = new Logger();
    }

    public static Logger getInstance() {
        return Loader.sInstance;
    }

    public void info(Object object) {
        System.out.println(String.format("%s] (I) %s", Calendar.getInstance().getTime().toLocaleString(), object));
    }

    public void warn(Object object) {
        System.out.println(String.format("%s] (W) %s", Calendar.getInstance().getTime().toLocaleString(), object));
    }

    public void error(Object object) {
        System.out.println(String.format("%s] (E) %s", Calendar.getInstance().getTime().toLocaleString(), object));
    }
}
