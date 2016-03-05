# java-reverse-proxy
Reverse proxy using Jetty/Undertow

Provides a simple implementation of reverse proxy using Jetty/Undetow servers.
The application accepts a host name and an optional port number and optional name of server to be used as arguments.
Additional servers can be included by implementing the ProxyService interface.

> -host server.com [-port 8888] [-server jetty]


