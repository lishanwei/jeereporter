//package com.thinkgem.jeesite.modules.sys.security;
//
//import org.apache.http.ssl.SSLContexts;
//import org.apache.http.ssl.TrustStrategy;
//import org.jasig.cas.client.ssl.AnyHostnameVerifier;
//import org.jasig.cas.client.ssl.HttpURLConnectionFactory;
//import org.jasig.cas.client.ssl.RegexHostnameVerifier;
//import org.jasig.cas.client.ssl.WhitelistHostnameVerifier;
//
//import javax.net.ssl.*;
//import java.net.HttpURLConnection;
//import java.net.URLConnection;
//import java.security.KeyManagementException;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//
///**
// * 连接工厂
// * <p>
// * 对自签证书主机进行强制认证.
// *
// * @author zhaoshb
// * @since v1.0
// */
//public class MkHttpURLConnectionFactory implements HttpURLConnectionFactory {
//
//	/**
//	 * Hostname verifier used when making an SSL request to the CAS server.
//	 * Defaults to {@link HttpsURLConnection#getDefaultHostnameVerifier()}
//	 */
//	private HostnameVerifier hostnameVerifier = new TrustAllHostnameVerifier();
//
//	public MkHttpURLConnectionFactory() {
//	}
//
//	/**
//	 * Set the host name verifier for the https connection received.
//	 *
//	 * @see AnyHostnameVerifier
//	 * @see RegexHostnameVerifier
//	 * @see WhitelistHostnameVerifier
//	 */
//	public final void setHostnameVerifier(final HostnameVerifier verifier) {
//		this.hostnameVerifier = verifier;
//	}
//
//	@Override
//	public HttpURLConnection buildHttpURLConnection(final URLConnection url) {
//		return this.configureHttpsConnectionIfNeeded(url);
//	}
//
//	/**
//	 * Configures the connection with specific settings for secure http
//	 * connections If the connection instance is not a
//	 * {@link HttpsURLConnection}, no additional changes will be made and the
//	 * connection itself is simply returned.
//	 *
//	 * @param conn
//	 *            the http connection
//	 */
//	private HttpURLConnection configureHttpsConnectionIfNeeded(final URLConnection conn) {
//		if (conn instanceof HttpsURLConnection) {
//			final HttpsURLConnection httpsConnection = (HttpsURLConnection) conn;
//			final SSLSocketFactory socketFactory = this.createSSLSocketFactory();
//			if (socketFactory != null) {
//				httpsConnection.setSSLSocketFactory(socketFactory);
//			}
//
//			if (this.hostnameVerifier != null) {
//				httpsConnection.setHostnameVerifier(this.hostnameVerifier);
//			}
//		}
//		return (HttpURLConnection) conn;
//	}
//
//	/**
//	 * Creates a {@link SSLSocketFactory} based on the configuration specified
//	 * <p>
//	 * Sample properties file:
//	 *
//	 * <pre>
//	 * protocol=TLS
//	 * keyStoreType=JKS
//	 * keyStorePath=/var/secure/location/.keystore
//	 * keyStorePass=changeit
//	 * certificatePassword=aGoodPass
//	 * </pre>
//	 *
//	 * @return the {@link SSLSocketFactory}
//	 */
//	private SSLSocketFactory createSSLSocketFactory() {
//		try {
//			SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
//				@Override
//				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//					return true;
//				}
//			}).build();
//			return sslcontext.getSocketFactory();
//		} catch (KeyManagementException e) {
//			throw new RuntimeException("初始化SSLContext失败");
//		} catch (NoSuchAlgorithmException e) {
//			throw new RuntimeException("初始化SSLContext失败");
//		} catch (KeyStoreException e) {
//			throw new RuntimeException("初始化SSLContext失败");
//		}
//
//	}
//
//	private class TrustAllHostnameVerifier implements HostnameVerifier {
//
//		@Override
//		public boolean verify(String arg0, SSLSession arg1) {
//			return true;
//		}
//
//	}
//}
