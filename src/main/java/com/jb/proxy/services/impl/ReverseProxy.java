package com.jb.proxy.services.impl;

/**
 * Created by jobinbasani on 3/3/16.
 */
public abstract class ReverseProxy {
    private String host;
    private int port;
    private static String HTTP = "http://";
    private static int DEFAULT_PORT = 8080;

    public ReverseProxy(String host, int port) {
        setHost(host);
        setPort(port);
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    private void setPort(int port) {
        if (port == 0) {
            if (host.matches(".*:\\d+.?")) {
                String[] hostSplit = host.split(":");
                this.port = Integer.valueOf(hostSplit[hostSplit.length-1].replaceAll("[^0-9]", ""));
            } else {
                this.port = DEFAULT_PORT;
            }
        } else {
            this.port = port;
        }
    }

    private void setHost(String host) {
        if(!host.startsWith(HTTP)){
            this.host = HTTP + host;
        }else{
            this.host = host;
        }
        if (this.host.endsWith("/")) {
            this.host = this.host.replaceFirst("/$","");
        }
    }

    @Override
    public String toString() {
        return String.format("Proxying %s, on port %d, implementation class by %s", getHost(), getPort(), getClass().getSimpleName());
    }
}
