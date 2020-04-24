package com.aarrelaakso.drawl;


import java.util.logging.*;

public class LoggingConfig {

    public LoggingConfig() {
        try {
            // Programmatic configuration
            System.setProperty("java.util.logging.SimpleFormatter.format",
                    "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s [%3$s] (%2$s) %5$s %6$s%n");

            //System.setProperty("java.util.logging.config.file", path);

            Level consoleLevel = Level.INFO;

            final ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(consoleLevel);
            consoleHandler.setFormatter(new SimpleFormatter());

            Logger root = Logger.getLogger("");
            Level loggerLevel = Level.INFO;
            root.setLevel(loggerLevel);
            for (Handler handler : root.getHandlers()) {
                handler.setLevel(loggerLevel);
            }
            System.out.println("level set: " + loggerLevel.getName());

        } catch (Exception e) {
            // The runtime won't show stack traces if the exception is thrown
            e.printStackTrace();
        }
    }
}
