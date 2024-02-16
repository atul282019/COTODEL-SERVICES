package com.cotodel.hrms.auth.server.util;
/**
 * @author Vinay Kushwaha
 * @Email  vinaykushwaha85@gmail.com

 */
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NhaSSSLUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(NhaSSSLUtil.class);
	
	public static void setDefaultSSL() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new
				X509TrustManager() {
			public java.security.cert.X509Certificate[]
					getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
			}
		} };
		// Install the all-trusting trust manager
		SSLContext sc = null;
		try {
			sc = SSLContext.getInstance("SSL");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			logger.error("NoSuchAlgorithmException in PersonnelService: " + e1);
			e1.printStackTrace();
		}
		try {
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
		} catch (KeyManagementException e1) {
			// TODO Auto-generated catch block
			logger.error("KeyManagementException in PersonnelService e:" + e1);
			e1.printStackTrace();
		}
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {

			public boolean verify(String hostname, SSLSession session) {
				return true;
			}

		};

		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}






}
