package com.whjt.core;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

public class HttpClientUtil {

    // ��ȡ��ʱ
    private final static int SOCKET_TIMEOUT = 30000;
    // ���ӳ�ʱ
    private final static int CONNECTION_TIMEOUT = 30000;
    // ÿ��HOST�������������
    private final static int MAX_CONN_PRE_HOST = 20;
    // ���ӳص����������
    private final static int MAX_CONN = 1000;
    // ���ӳ�
    private final static HttpConnectionManager httpConnectionManager;

    static {
        httpConnectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = httpConnectionManager.getParams();
        params.setConnectionTimeout(CONNECTION_TIMEOUT);
        params.setSoTimeout(SOCKET_TIMEOUT);
        params.setDefaultMaxConnectionsPerHost(MAX_CONN_PRE_HOST);
        params.setMaxTotalConnections(MAX_CONN);
    }

    public static HttpClient getClient() {
        return new HttpClient(httpConnectionManager);
    }
}