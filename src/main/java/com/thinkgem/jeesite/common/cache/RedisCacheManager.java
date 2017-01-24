//package com.thinkgem.jeesite.common.cache;
//
//import java.util.Set;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.CacheManager;
//import org.springframework.stereotype.Service;
//
//import redis.clients.jedis.Jedis;
//
//import com.mk.framework.jedis.MkJedisConnectionFactory;
//import com.mk.framework.utils.SerializeUtil;
//
///**
// * redis CacheManager
// *
// * @author tankai.
// *
// */
//@SuppressWarnings({ "cast" })
//@Service
//public class RedisCacheManager {
//	private static final Logger logger = LoggerFactory
//			.getLogger(RedisCacheManager.class);
//
//	@Resource
//	private volatile CacheManager jedisCacheManager;
//
//	@Autowired
//	private MkJedisConnectionFactory jedisConnectionFactory;
//
//	private Jedis jedis;
//
//	/**
//	 * constructor
//	 */
//	public RedisCacheManager() {
//	}
//
//	/**
//	 * get RedisCacheManager
//	 *
//	 * @return
//	 */
//	public CacheManager getCacheManager() {
//		if (this.jedisCacheManager == null) {
//			RedisCacheManager.logger.error("未注入RedisCacheManager,请检查spring配置.");
//		}
//		return this.jedisCacheManager;
//	}
//
//	/**
//	 * 指定缓存的过期时间
//	 *
//	 * @param cacheName
//	 * @param key
//	 * @param value
//	 * @param seconds
//	 */
//	public void setExpires(String cacheName, String key, String value,
//			int seconds) {
//		// //long time = new Date().getTime();
//		Jedis jedis = this.getJedis();
//		try {
//			String expiresKey = cacheName.concat("~").concat(key);
//			jedis.setex(expiresKey, seconds, value);
//			// //jedis.set(expiresKey, value);
//			// //jedis.expireAt(expiresKey, time + expires);
//		} catch (Exception e) {
//			RedisCacheManager.logger
//					.error("OtsCacheManager setExpires method error:\n"
//							+ e.getMessage());
//		} finally {
//			if (jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	/**
//	 *
//	 * @param cacheName
//	 * @param key
//	 * @param value
//	 * @param seconds
//	 */
//	public void setExpires(String cacheName, String key, Object value,
//			int seconds) {
//		Jedis jedis = this.getJedis();
//		try {
//			String expiresKey = cacheName.concat("~").concat(key);
//			jedis.setex(expiresKey.getBytes(), seconds,
//					SerializeUtil.serialize(value));
//		} catch (Exception e) {
//			RedisCacheManager.logger
//					.error("OtsCacheManager setExpires method error:\n"
//							+ e.getMessage());
//		} finally {
//			if (jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	/**
//	 *
//	 * @param cacheName
//	 * @param key
//	 * @return
//	 */
//	public Object getExpiresObject(String cacheName, String key) {
//		Object result = null;
//		Jedis jedis = this.getJedis();
//		try {
//			String expiresKey = cacheName.concat("~").concat(key);
//			byte[] objBytes = jedis.get(expiresKey.getBytes());
//			result = SerializeUtil.unserialize(objBytes);
//		} catch (Exception e) {
//			result = null;
//		} finally {
//			if (jedis != null) {
//				jedis.close();
//			}
//
//		}
//
//		return result;
//	}
//
//	/**
//	 *
//	 * @param cacheName
//	 * @param key
//	 * @return
//	 */
//	public Object getExpires(String cacheName, String key) {
//		String result = "";
//		Jedis jedis = this.getJedis();
//		try {
//			String expiresKey = cacheName.concat("~").concat(key);
//			result = jedis.get(expiresKey);
//		} catch (Exception e) {
//			result = "";
//		} finally {
//			if (jedis != null) {
//				jedis.close();
//			}
//
//		}
//		return result;
//	}
//
//
//	/**
//	 * 删除指定名称缓存下指定key的值
//	 *
//	 * @param cacheName
//	 *            参数：缓存名称
//	 * @param key
//	 *            参数：key
//	 */
//	public void remove(String cacheName, String key) {
//		Jedis jedis = this.getJedis();
//		try {
//			key = cacheName.concat("~").concat(key);
//			Set<String> set = jedis.keys(key);
//			String[] kk = new String[set.size()];
//			jedis.del(set.toArray(kk));
//
//		} catch (Exception e) {
//			RedisCacheManager.logger
//					.error("OtsCacheManager remove method error:\n"
//							+ e.getMessage());
//		} finally {
//			if (jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	public void del(String key) {
//		Jedis jedis = this.getJedis();
//		try {
//			Set<String> set = jedis.keys(key);
//			String[] kk = new String[set.size()];
//			jedis.del(set.toArray(kk));
//		} catch (Exception e) {
//			RedisCacheManager.logger.error("OtsCacheManager del method error:\n"
//					+ e.getMessage());
//		} finally {
//			jedis.close();
//		}
//	}
//
//	// lpush
//	public void lpush(String queneName, String str) {
//		Jedis jedis = getJedis();
//		try {
//			jedis.lpush(queneName, str);
//		} catch (Exception e) {
//		} finally {
//			if (jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	// rpop
//	public String rpop(String queneName) {
//		Jedis jedis = getJedis();
//		String value = null;
//		try {
//			value = jedis.rpop(queneName);
//		} catch (Exception e) {
//		} finally {
//			if (jedis != null) {
//				jedis.close();
//			}
//		}
//		return value;
//	}
//
//	public boolean isExistKey(String key) {
//		Jedis jedis = getJedis();
//		try {
//			if (jedis.exists(key))
//				return true;
//		} catch (Exception e) {
//		} finally {
//			if (jedis != null) {
//				jedis.close();
//			}
//		}
//		return false;
//	}
//
//
//	public Jedis getJedis() {
//		return this.jedis = this.jedisConnectionFactory.getJedis();
//
//	}
//
//}
