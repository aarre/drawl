package com.aarrelaakso.drawl;

import org.jetbrains.annotations.NotNull;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.Boolean.TRUE;

/**
 * Manages logging configuration.
 */
public class LoggingConfig {

    private static final Boolean SIMPLE_LOG_FORMAT = TRUE;

    public LoggingConfig() {
        try {
            if (Boolean.TRUE.equals(SIMPLE_LOG_FORMAT))
            {
                // Programmatic configuration
                System.setProperty("java.util.logging.SimpleFormatter.format",
                        "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s (%2$s) %5$s %6$s%n");
            }
            else
            {
                System.setProperty("java.util.logging.SimpleFormatter.format",
                        "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s [%3$s] (%2$s) %5$s %6$s%n");
            }

            //System.setProperty("java.util.logging.config.file", path);

            @NotNull Level consoleLevel = Level.WARNING;
            @NotNull Level loggerLevel = consoleLevel;

            final @NotNull ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(consoleLevel);
            consoleHandler.setFormatter(new SimpleFormatter());

            final Logger root = Logger.getLogger("");

            root.setLevel(loggerLevel);
            for (@NotNull Handler handler : root.getHandlers()) {
                handler.setLevel(loggerLevel);
            }
            //root.log("Logging level set: " + loggerLevel.getName());

        } catch (Exception e) {
            // The runtime won't show stack traces if the exception is thrown
            e.printStackTrace();
        }
    }
}
