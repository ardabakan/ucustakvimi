package com.ucustakvimi.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * An insecure trust manager that accepts all certificates.
 * 
 *
 */
public class InsecureTrustManager implements X509TrustManager {
	@Override
	public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
			throws CertificateException {
		// accept all
	}

	@Override
	public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
			throws CertificateException {
		// accept all
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
