package com.bigname.search.elasticsearch.client;

import com.envelopes.util.EnvUtil;
import org.apache.ofbiz.base.util.Debug;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by shoab on 5/9/17.
 */
public class SearchClient {
    public static final String module = SearchClient.class.getName();

    //parameters needed for client connections
    private static final String USER = "elastic";
    //private static final String PASSWORD = "jw0d4buwN3P80StPDbibadWw";
    //private static final String CLUSTOR_NAME = "b235c58670b08689d4b0f3b42df95de1";
    private static final String PASSWORD = "jdJk6CImCSHPfFEkd7aNyRCt";
    private static final String CLUSTOR_NAME = "ebb060d251ef4837b4a2fd15fb15192e";
    private static final String REGION = "us-east-1";
    private static final String HOST = CLUSTOR_NAME + "." + REGION + ".aws.found.io";
    private static boolean ip6Enabled = false;
    private static boolean ip4Enabled = true;
    private TransportClient transport = null;

    protected static SearchClient searchClient = null;

    private SearchClient() {
        //create client and execute search, filters, aggregations
        try {
            createClient();
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to create search client.", module);
        }
    }

    /**
     * Create the CLIENT for getting query results
     * @return
     * @throws UnknownHostException
     */
    private void createClient() throws UnknownHostException {
        boolean enableSsl = true;

        Settings settings = Settings.builder()
            .put("client.transport.nodes_sampler_interval", "5s")
            .put("client.transport.sniff", false)
            .put("transport.tcp.compress", true)
            .put("cluster.name", CLUSTOR_NAME)
            .put("xpack.security.transport.ssl.enabled", enableSsl)
            .put("request.headers.X-Found-Cluster", CLUSTOR_NAME)
            .put("xpack.security.user", USER + ":" + PASSWORD)
            .build();

        this.transport = new PreBuiltXPackTransportClient(settings);
        for (InetAddress address : InetAddress.getAllByName(HOST)) {
            if ((ip6Enabled && address instanceof Inet6Address) || (ip4Enabled && address instanceof Inet4Address)) {
                this.transport.addTransportAddress(new TransportAddress(address, 9343));
            }
        }
    }

    /**
     * A client must be closed after use
     */
    public void closeClient() {
        //close client
        try {
            if(this.transport != null) {
                this.transport.close();
            }
        } catch (Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to close search.", module);
        }
    }

    /**
     * Create a static method to get instance.
     */
    public static SearchClient getInstance() {
        if (searchClient == null) {
            synchronized (SearchClient.class) {
                if (searchClient == null) {
                    searchClient = new SearchClient();
                }
            }
        }
        return searchClient;
    }

    public static TransportClient getTransportClient() {
        SearchClient sc = SearchClient.getInstance();
        return sc.transport;
    }
}