/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.jetty.server;

import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.spdy.server.NPNServerConnectionFactory;
import org.eclipse.jetty.spdy.server.http.HTTPSPDYServerConnectionFactory;
import org.eclipse.jetty.spdy.server.http.ReferrerPushStrategy;
import org.eclipse.jetty.util.ssl.SslContextFactory;

/**
 *
 * @author hope
 */
public class ManyConnectors {

    public ManyConnectors() {
    }

    ///////many connectors./////////////
    // HTTP Configuration
    public HttpConfiguration getHttpConfig() {
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(8443);
        httpConfig.setOutputBufferSize(32768);
        return httpConfig;
    }

    // SSL Context factory 
    public SslContextFactory getSslContextFactory() {
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath("src/main/resources/keystore");
        sslContextFactory.setKeyStorePassword("rising");
        //sslContextFactory.setKeyManagerPassword("rising111");
        sslContextFactory.setProtocol("TLSv1");
        //sslContextFactory.addExcludeCipherSuites(".*RC4.*");
       // sslContextFactory.addExcludeCipherSuites("TLS_DHE_RSA.*");
        return sslContextFactory;
    }

    // HTTPS Configuration
    public HttpConfiguration getHttpsConfig() {
        HttpConfiguration config = new HttpConfiguration(getHttpConfig());
        config.addCustomizer(new SecureRequestCustomizer());
        return config;
    }

    // SPDY or HTTP Configuration
    public HttpConfiguration getHTTPSPDYConfig() {
        HttpConfiguration config = getHttpConfig();
        config.setSecureScheme("https");
        config.setSecurePort(8443);
        config.setOutputBufferSize(32768);
        config.setRequestHeaderSize(8192);
        config.setResponseHeaderSize(8192);
        return config;
    }

    // TLS Configuration
    public HttpConfiguration gettlsHttpConfig() {
        HttpConfiguration config = new HttpConfiguration(getHTTPSPDYConfig());
        config.addCustomizer(new SecureRequestCustomizer());
        return config;
    }

    // Push Strategy
    public ReferrerPushStrategy getPushStrategy() {
        ReferrerPushStrategy pushStrategy = new ReferrerPushStrategy();
        /* Uncomment to blacklist browsers for this push strategy. If one of the blacklisted Strings occurs in the
         user-agent header sent by the client, push will be disabled for this browser. This is case insensitive" -->
         */
        /* List<String> blacklist = new ArrayList();
         blacklist.add(".*(?i)firefox/14.*");
         blacklist.add(".*(?i)firefox/15.*");
         blacklist.add(".*(?i)firefox/16.*");
         pushStrategy.setUserAgentBlacklist(blacklist);
         */
        // Uncomment to override default file extensions to push (pls check the escape character)
       /* List<String> regexpsList = new ArrayList();
         regexpsList.add(".*\\.css");
         regexpsList.add(".*\\.js");
         regexpsList.add(".*\\.png");
         regexpsList.add(".*\\.jpg");
         regexpsList.add(".*\\.gif");
         pushStrategy.setPushRegexps(regexpsList);
         */
        pushStrategy.setReferrerPushPeriod(5000);
        pushStrategy.setMaxAssociatedResources(32);
        return pushStrategy;
    }

    // SSL Connection factory with NPN as next protocol 
    public SslConnectionFactory getSslFactory() {
        SslConnectionFactory factory = new SslConnectionFactory(getSslContextFactory(), "npn");
        return factory;
    }

    // NPN Connection factory with HTTP as default protocol 
    public NPNServerConnectionFactory getNPNFactory() {
        String[] protocols = {"spdy/3", "spdy/2", "http/1.1"};
        NPNServerConnectionFactory factory = new NPNServerConnectionFactory(protocols);
        factory.setDefaultProtocol("http/1.1");
        return factory;
    }

    // SPDY/3 Connection factory
    public HTTPSPDYServerConnectionFactory getSPDY3Factory() {
        HTTPSPDYServerConnectionFactory factory = new HTTPSPDYServerConnectionFactory(3, gettlsHttpConfig(), getPushStrategy());
        return factory;
    }

    // SPDY/2 Connection factory 
    public HTTPSPDYServerConnectionFactory getSPDY2Factory() {
        HTTPSPDYServerConnectionFactory factory = new HTTPSPDYServerConnectionFactory(2, gettlsHttpConfig());
        return factory;
    }

    // HTTP Connection factory 
    public HttpConnectionFactory getHTTPSFactory() {
        HttpConnectionFactory factory = new HttpConnectionFactory(gettlsHttpConfig());
        return factory;
    }

    // All Connection factories 
    public ConnectionFactory[] getFactories() {
        ConnectionFactory[] factories = {
            getSslFactory(),
            getNPNFactory(),
            getSPDY2Factory(),
            getSPDY3Factory(),
            getHTTPSFactory()
        };
        return factories;
    }

    // SPDY Connector
    public ServerConnector spdyConnector(Server server) {
        ServerConnector connector = new ServerConnector(server, getFactories());
        connector.setPort(8443);
        connector.setIdleTimeout(200000);//TODO 30000
        return connector;
    }

    // HTTP connector
    public Connector getHttpConnector(Server server) {
        ServerConnector http = new ServerConnector(server,
                new HttpConnectionFactory(getHttpConfig()));
        http.setPort(8080);
        http.setIdleTimeout(200000);//TODO 30000
        return http;
    }

    // All connectors
    public Connector[] getConnectors(Server server) {

        Connector[] connectors = new Connector[]{getHttpConnector(server), spdyConnector(server)
        };
        return connectors;
    }
}
