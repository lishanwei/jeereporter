package com.thinkgem.jeesite.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class QiniuUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(QiniuUtils.class);
	
	private static final String ACCESS_KEY="AYEYSSsSHHLutBqJpnrZfOvWjXUhnT6Dscv55qM8"; //Config.getValue("qiniuAccessKey");
	private static final String SECRET_KEY="6Kfki0Nfm63cVVJYy2Su-PuQQrRS4fkd9wbnN5Tm";//Config.getValue("qiniuSecretKey");
	private static final String BUCKET="basic";//Config.getValue("qiniuBucket");
	private static final Auth auth = Auth.create(ACCESS_KEY,SECRET_KEY);
	private static final UploadManager uploadManager = new UploadManager();
	/**
	 * 获取上传凭证key
	 * @return 返回凭证key
	 */
	public static String generateSimpleUploadTicket() {
		try {
			//return auth.uploadToken(BUCKET, null, 36000, new StringMap().put("insertOnly", "1"));
			return auth.uploadToken(BUCKET);
		} catch (Exception e) {
			QiniuUtils.logger.error("获取七牛上传凭证异常：" + e.getMessage(), e);
		}
		return null;
	}
	
	 private String getUpToken(){//return auth.uploadToken(BUCKET);
	     return auth.uploadToken(BUCKET, null, 36000, null);
	 }

	 public void upload(String path) throws QiniuException{
		 String fileName="";
		 //String[] temp=path.sp
		 Response res = uploadManager.put(path, fileName, getUpToken());
		 
	 }
	
	public static String getToken() {   
	    /*Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);  
	    PutPolicy putPolicy = new PutPolicy(BUCKET);  
	    try {  
	        String uptoken = putPolicy.token(mac);  
	        return uptoken;  
	    } catch (Exception e) {  */
	    return null;  
	//}
	}

/*	*//**
	 * 获取下载凭证
	 * @param key 文件key
	 * @param treatMethod 处理管道
	 * @return 下载凭证
	 *//*
	public static String generateDownloadTicket(String key, String treatMethod) {
		//七牛api地址：http://developer.qiniu.com/docs/v6/sdk/java-sdk.html#private-download
		try {
			Auth auth = Auth.create(HmsFileUtils.getSysContentItem(ContentUtils.QINIU_ACCESS_KEY),
					HmsFileUtils.getSysContentItem(ContentUtils.QINIU_SECRET_KEY));
			return auth.privateDownloadUrl(HmsFileUtils.getSysContentItem(ContentUtils.QINIU_DOWNLOAD_ADDRESS) + key
					+ treatMethod, Long.parseLong(HmsFileUtils.getSysContentItem(ContentUtils.QINIU_INVALIDATION_TIME)));
		} catch (Exception e) {
			QiniuUtils.logger.error("获取七牛下载凭证异常：" + e.getMessage(), e);
		}
		return null;
	}*/
}
