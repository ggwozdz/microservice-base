package com.acxiom.microservice;

import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.component.LifeCycle.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLifecycleListener implements Listener {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultLifecycleListener.class);

    @Override
    public void lifeCycleStarted(LifeCycle arg0) {
        LOG.info("Server has started");
    }

    @Override
    public void lifeCycleStopped(LifeCycle arg0) {
        LOG.info("Server has stopped");
    }

    @Override
    public void lifeCycleFailure(LifeCycle arg0, Throwable arg1) {
    }

    @Override
    public void lifeCycleStarting(LifeCycle arg0) {
    }

    @Override
    public void lifeCycleStopping(LifeCycle arg0) {
    }
}
