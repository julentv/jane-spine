package org.jane.cns.spine.service;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class EfferentManagementServiceStarter {
    private static final Logger LOGGER = Logger.getLogger(EfferentManagementServiceStarter.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("Starting cns server");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);


        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.packages",
                "com.jersey.jaxb, com.fasterxml.jackson.jaxrs.json");

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                EfferentManagementService.class.getCanonicalName());

        jerseyServlet.setInitParameter(
                "org.jane.cns.spine.store.path",
                args[0]);

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            //jettyServer.destroy();
        }
    }
}
