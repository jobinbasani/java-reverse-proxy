package com.jb.proxy;

import com.jb.proxy.services.ProxyService;
import com.jb.proxy.services.impl.JettyProxy;
import com.jb.proxy.services.impl.UndertowProxy;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class ProxyServer {

    @Option(name = "-host", usage = "Remote host to be proxied", required = true)
    private String remoteHost;

    @Option(name = "-port", usage = "Port on which proxy is to be run,optional parameter. Can be provided as part of host address or default value of 8080 is used")
    private int port = 0;

    @Option(name = "-server", usage = "Server implementation to be used eg:- jetty, undertow")
    private String serverImpl = "jetty";

    public static void main(String[] args) throws Exception {
        new ProxyServer().runMain(args);
    }

    private void runMain(String[] args) throws Exception {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            getProxyService().runProxy();
        } catch (CmdLineException e) {
            System.err.println("Incorrect usage of arguments");
            System.err.println("Example of arguments: -host server.com [-port 8888] [-server jetty]");
        }

    }

    private ProxyService getProxyService() {

        ProxyService proxyService;
        switch (serverImpl) {
            case "jetty":
                proxyService = new JettyProxy(remoteHost, port);
                break;
            case "undertow":
                proxyService = new UndertowProxy(remoteHost, port);
                break;
            default:
                proxyService = new JettyProxy(remoteHost, port);
        }
        System.out.println(proxyService);
        return proxyService;
    }
}
