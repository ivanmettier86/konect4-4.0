package com.konect4.utilities;

import khandroid.ext.apache.http.conn.scheme.Scheme;
import khandroid.ext.apache.http.conn.ssl.SSLSocketFactory;
import khandroid.ext.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Muhammad Zeeshan on 6/13/2017.
 */

public class CustomHttpClient extends DefaultHttpClient {

    public CustomHttpClient() {
        super();
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier(new CustomHostnameVerifier());
        Scheme scheme = (new Scheme("https", 443, socketFactory));  //; Scheme("https", socketFactory, 443));
        getConnectionManager().getSchemeRegistry().register(scheme);
    }


}
