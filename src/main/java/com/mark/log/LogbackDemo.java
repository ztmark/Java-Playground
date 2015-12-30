package com.mark.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Mark
 * Date  : 15/12/30.
 */
public class LogbackDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogbackDemo.class);

    public static void main(String[] args) {
        while (true) {
            LOGGER.trace("trace");
            LOGGER.info("info");
            LOGGER.warn("warn");
            LOGGER.debug("debug");
            LOGGER.error("error");
        }
    }


}
