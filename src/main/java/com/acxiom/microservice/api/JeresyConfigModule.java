package com.acxiom.microservice.api;

import java.util.Map;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Provides;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class JeresyConfigModule extends JerseyServletModule {

    private static final Logger LOG = LoggerFactory.getLogger(JeresyConfigModule.class);
    private final Client client = this.createClient();

    private Client createClient() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        return Client.create(clientConfig);
    }

    @Override
    protected void configureServlets() {
        Map<String, String> initParams = ImmutableMap
                .of(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.toString(true),
                        "com.sun.jersey.config.feature.Trace", Boolean.toString(LOG.isTraceEnabled()));//
        //ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS, CrossOriginResourceSharingFilter.class.getName());

        // Route all requests through GuiceContainer
        serve("/*").with(GuiceContainer.class, initParams);
    }

    @Provides
    @Singleton
    public Client getClient() {
        return this.client;
    }
}
