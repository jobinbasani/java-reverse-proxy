package com.jb.proxy.services.impl;

import com.jb.proxy.services.ProxyService;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.ResponseCodeHandler;
import io.undertow.server.handlers.proxy.ProxyClient;
import io.undertow.server.handlers.proxy.ProxyHandler;
import io.undertow.server.handlers.proxy.SimpleProxyClientProvider;

import java.net.URI;

/**
 * Created by jobinbasani on 3/3/16.
 */
public class UndertowProxy extends ReverseProxy implements ProxyService {

    public UndertowProxy(String host, int port) {
        super(host, port);
    }

    @Override
    public void runProxy() throws Exception {
        ProxyClient proxyClientProvider = new SimpleProxyClientProvider(URI.create(getHost()));
        final HttpHandler handler = new ProxyHandler(proxyClientProvider, 30000, ResponseCodeHandler.HANDLE_404);
        Undertow server = Undertow.builder()
                .addHttpListener(getPort(),"localhost")
                .setHandler(new HttpHandler() {
                    @Override
                    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
                        httpServerExchange.setRelativePath(httpServerExchange.getRequestPath());
                        handler.handleRequest(httpServerExchange);
                    }
                }).build();
        server.start();
    }
}
