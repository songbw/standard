package com.jiannei.utils;


import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	private static Logger logger = Logger.getLogger(RedisUtil.class);
	public static final int webexpire = Config.getInt("redis.webexpire");
	public static final int appexpire = Config.getInt("redis.appexpire");
	private static String redisIp = Config.getString("redis.ip");
	private static int redisPort = Config.getInt("redis.port");
	private static int active = Config.getInt("redis.maxactive");
	private static int idle = Config.getInt("redis.maxidle");
	private static int wait = Config.getInt("redis.maxwait");
	public static JedisPool pool;
    /**
     * 初始化Redis连接池
     */
    private static JedisPool getJedisPool(){
    	if(pool == null ){
        	JedisPoolConfig config = new JedisPoolConfig();
//            config.setMaxActive(active);
            config.setMaxIdle(idle);
//            config.setMaxWait(wait);
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, redisIp,redisPort);
			logger.info("jedis连接池初始化完成！");
    	}
    	return pool;
    }
    /** 
     * 返还到连接池 
     *  
     * @param pool  
     * @param redis 
     */  
    public static void returnResource(JedisPool pool, Jedis redis) {  
        if (redis != null) {  
            pool.returnResource(redis);  
        }  
    }
    
	public static void putRedis(String type,String key, String token, int expire){
        JedisPool pool = null;  
        Jedis jedis = null;
		try {
			logger.info("key="+type+key+",value="+token);
			pool =getJedisPool();
			jedis = pool.getResource();
			jedis.setex(type+key, expire, token);
			Long ttl = jedis.ttl(type+key);
			logger.info("token缓存成功！剩余时间="+ttl+"秒");
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(pool, jedis);
		}
	}
	
	public static void expire(String openId, int expire){
        JedisPool pool = null;  
        Jedis jedis = null;
		try {
			pool = getJedisPool();
			jedis=pool.getResource();
			jedis.expire(openId, expire);
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(pool, jedis);
		}
	}
	/**
	 *@description	查询key剩余时间
	 *@date 2016-7-4
	 *@return
	 */
	public static long ttl(String key){
        JedisPool pool = null;  
        Jedis jedis = null;
		if("".equals(key) || key==null) return -1;
		try {
			pool = getJedisPool();
			jedis=pool.getResource();
			Long ttl = jedis.ttl(key);
			return ttl;
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(pool, jedis);
		}
	}
	
	/**
	 *@description	判断键是否存在，不存在返回false
	 *@date 2016-7-4
	 *@return
	 */
	  public static boolean exist(String token) {
	        JedisPool pool = null;  
	        Jedis jedis = null;
		    boolean flag = false;
		    if ("".equals(token) || token == null) {
		      return flag;
		    }
		    try {
		    	pool = getJedisPool();
				jedis = pool.getResource();
				flag = jedis.exists(token.getBytes());
				return flag;
			} catch (Exception e) {
				pool.returnBrokenResource(jedis);
				throw e;
			}finally{
				returnResource(pool, jedis);
			}
	}
	  
	  
	  public static String getValue(String token) {
	        JedisPool pool = null;  
	        Jedis jedis = null;
		    String value = "";
		    if ("".equals(token) || token == null) {
		      return value;
		    }
		    try {
		    	pool = getJedisPool();
				jedis = pool.getResource();
				value = jedis.get(token);
				return value;
			} catch (Exception e) {
				pool.returnBrokenResource(jedis);
				throw e;
			}finally{
				returnResource(pool, jedis);
			}
	}

	public static boolean verifyModifyCount(String token) {
		JedisPool pool = null;
		Jedis jedis = null;
		String value = "";
		if (token == null || "".equals(token)) {
			return true;
		}
		int waitTime = Config.getInt("empyeeAndTimeWaitTime");
		try {
			pool = getJedisPool();
			jedis = pool.getResource();
			value = jedis.get(token);
			if (value == null || "".equals(value)) {
				jedis.setex(token,waitTime,"1");
				return true;
			}
			int count = Integer.parseInt(value);
			if (count < Config.getInt("emplyeeAndTimeModifyCount")) {
				count = count + 1 ;
				jedis.setex(token,waitTime,count+"");
				return true ;
			} else {
				return false;
			}
		} catch (Exception e) {
			pool.returnBrokenResource(jedis);
			throw e;
		}finally{
			returnResource(pool, jedis);
		}
	}
	  
}
