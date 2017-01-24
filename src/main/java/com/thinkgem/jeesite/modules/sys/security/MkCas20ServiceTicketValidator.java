//package com.thinkgem.jeesite.modules.sys.security;
//
//import org.jasig.cas.client.ssl.HttpURLConnectionFactory;
//import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
//
///**
// * 调整认证方式，对自签名证书进行强制认证.
// *
// * @author zhaoshb
// * @since v1.0
// */
//public class MkCas20ServiceTicketValidator extends Cas20ServiceTicketValidator {
//
//	/**
//	 * cas主机地址<br>
//	 * 由于是自签名证书，需要通过sun认证.
//	 */
//	private String casServerHost = null;
//
//	private HttpURLConnectionFactory urlConnectionFactory = null;
//
//	public MkCas20ServiceTicketValidator(String casServerUrlPrefix) {
//		super(casServerUrlPrefix);
//	}
//
//	@Override
//	protected HttpURLConnectionFactory getURLConnectionFactory() {
//		if (this.urlConnectionFactory == null) {
//			this.urlConnectionFactory = new MkHttpURLConnectionFactory();
//		}
//		return this.urlConnectionFactory;
//	}
//
//	public String getCasServerHost() {
//		return this.casServerHost;
//	}
//
//	public void setCasServerHost(String casServerHost) {
//		this.casServerHost = casServerHost;
//	}
//}
