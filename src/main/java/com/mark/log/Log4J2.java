package com.mark.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Mark
 * Date  : 15/12/30.
 */
public class Log4J2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Log4J2.class);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Test");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("just a test");
            }
            LOGGER.error("error");
            LOGGER.warn("warn");
            LOGGER.info("info");
            LOGGER.trace("trace");
        }
    }

}
