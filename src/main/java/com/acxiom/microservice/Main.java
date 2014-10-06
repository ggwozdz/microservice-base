package com.acxiom.microservice;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acxiom.microservice.api.JeresyConfigModule;
import com.acxiom.microservice.example.ExampleModule;
import com.acxiom.microservice.exceptions.ExceptionHandlerModule;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);
	private static final int HTTP_PORT = 8080;

	public static void main(String[] args) throws Exception {
		try {

			String appURL = getAppURL();
			Application application = Application.newBuilder()
				.setAppUrl(appURL)
				.addModule(new ExceptionHandlerModule())
				.addModule(new JeresyConfigModule())
				.addModule(new ExampleModule())
				.setHttpPort(HTTP_PORT)
				.build();

			application.start();
		} catch (Exception e) {
			LOG.error("Cannot start the application", e);
			throw e;
		}
	}

	private static String getAppURL() {
		URL url = Main.class.getClassLoader().getResource("public");
		return url.toExternalForm();
	}

}
