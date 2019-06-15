package com.ucustakvimi.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * An insecure host name verifier that accepts everything
 */
public class InsecureHostnameVerifier implements HostnameVerifier {
	@Override
	public boolean verify(String s, SSLSession sslSession) {
		return true;
	}
}
