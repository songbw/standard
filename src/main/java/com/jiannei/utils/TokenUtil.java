package com.jiannei.utils;


import org.apache.log4j.Logger;

public class TokenUtil {

	private static Logger logger = Logger.getLogger(TokenUtil.class);

	public TokenUtil() {
	}

	public static boolean verifyToken(String type, String key, String value) {
		boolean b = false;
		if (key == null || value == null || "".equals(value) || "".equals(key)) {
			logger.error("---数据为空！");
			return false;
		}
		try {
			String key0 = new StringBuilder(type).append(key).toString();
			String redisValue = RedisUtil.getValue(key0);
			if (value.equals(redisValue)) {
				b = true;
				logger.info("验证通过！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		return b;
	}
   
/*   public static void putToken(String openId,String token,String clientId){
	   StringBuilder sb = new StringBuilder(openId);
	   if(WEBCLIENT.equals(clientId)){
		   sb.append(0);//表示web登录
		   RedisUtil.putRedis(sb.toString(), token, RedisUtil.webexpire);
		   
	   }else{
		   sb.append(1);//表示移动端登录
		   RedisUtil.putRedis(sb.toString(), token, RedisUtil.appexpire);
		   
	   }
   }*/


}
