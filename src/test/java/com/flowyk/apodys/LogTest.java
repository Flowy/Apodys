package com.flowyk.apodys;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
    private static final Logger LOG = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void log() {
        LOG.error("error log test");
        LOG.warn("warn log test");
        LOG.info("info log test");
        LOG.debug("debug log test");
        LOG.trace("trace log test");
    }
}
