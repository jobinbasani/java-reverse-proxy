package com.jb.proxy.services.impl;

import com.jb.proxy.services.ProxyService;
import org.eclipse.jetty.proxy.ProxyServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

/**
 * Created by jobinbasani on 3/3/16.
 */
public class JettyProxy extends ReverseProxy implements ProxyService {

    public JettyProxy(String host, int port) {
        super(host, port);
    }

    public void runProxy() throws Exception {

        Server server = new Server(getPort());
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        contextHandler.setContextPath("/");
        server.setHandler(contextHandler);
        contextHandler.addServlet(new ServletHolder(new ProxyServlet(){
            @Override
            protected URI rewriteURI(HttpServletRequest request) {
                return URI.create(getHost()+request.getRequestURI());
            }
        }), "/*");
        server.start();
        server.join();
    }
}
