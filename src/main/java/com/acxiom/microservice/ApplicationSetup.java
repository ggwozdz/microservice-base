package com.acxiom.microservice;

import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;

public class ApplicationSetup extends GuiceServletContextListener {

    private final List<? extends Module> modules;

    public ApplicationSetup(List<? extends Module> modules) {
        this.modules = modules;
    }

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(modules);
    }
}
