package com.acxiom.microservice;

import java.util.List;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.LifeCycle.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceFilter;

public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    final List<Module> modules;
    final Listener listener;
    final String appUrl;
    final int httpPort;

    final Server server;

    private Application(Builder builder) {
        this(builder.modules, builder.listener, builder.appUrl, builder.httpPort);
    }

    public Application(List<Module> modules, Listener listener, String appUrl, int httpPort) {
        this.modules = ImmutableList.copyOf(modules);
        this.listener = listener;
        this.appUrl = appUrl;
        this.httpPort = httpPort;

        this.server = this.createServer();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void start() throws Exception {
        LOG.info("Application is starting");
        server.start();
        server.join();
    }

    public void stop() throws Exception {
        LOG.info("Application is stopping");
        server.stop();
    }

    protected Server createServer() {
        LOG.debug("Binding server to port {}", httpPort);
        LOG.debug(appUrl);

        Server server = new Server(httpPort);
        server.setStopAtShutdown(true);
        server.addLifeCycleListener(listener);

        HandlerList handlers = this.createHandlerList(appUrl);
        server.setHandler(handlers);

        return server;
    }

    protected HandlerList createHandlerList(String appURL) {
        HandlerList handlers = new HandlerList();
        ServletContextHandler apiHandler = this.createApiHandler();
        if (appURL != null) {
            ResourceHandler resourceHandler = this.createResourceHandler(appURL);
            handlers.setHandlers(new Handler[]{resourceHandler, apiHandler, new DefaultHandler()});
        } else {
            handlers.setHandlers(new Handler[]{apiHandler, new DefaultHandler()});
        }

        return handlers;
    }

    protected ResourceHandler createResourceHandler(String appURL) {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase(appURL);

        return resourceHandler;
    }

    protected ServletContextHandler createApiHandler() {
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/api/*");
        handler.addEventListener(new ApplicationSetup(modules));
        handler.addFilter(GuiceFilter.class, "/*", null);
        return handler;
    }

    public static final class Builder {

        private static final int DEFAULT_HTTP_PORT = 9000;

        private List<Module> modules = Lists.newArrayList();
        private Listener listener = new DefaultLifecycleListener();
        private int httpPort = DEFAULT_HTTP_PORT;
        private String appUrl;

        public Builder setModules(List<? extends Module> modules) {
            this.modules = Lists.newArrayList(modules);
            return this;
        }

        public Builder addModule(Module module) {
            this.modules.add(module);
            return this;
        }

        public Builder setListener(Listener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setAppUrl(String appUrl) {
            this.appUrl = appUrl;
            return this;
        }

        public Builder setHttpPort(int httpPort) {
            this.httpPort = httpPort;
            return this;
        }

        public Application build() {
            return new Application(this);
        }
    }

}
