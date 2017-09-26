package com.jiannei.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

public class IPAddressUtil {
	
	private static final Log logger = LogFactory.getLog(IPAddressUtil.class);

	public static String getClientIP(HttpServletRequest request) { // TODO ：经过代理后，一定检测一下该ip
		String clientIp = request.getHeader("X-Forwarded-For");
		if (logger.isDebugEnabled()) {
			logger.debug("client ip from header 'X-Forwarded-For' is " + clientIp);
		}
		if (clientIp == null || "".equals(clientIp)) {
			clientIp = request.getRemoteAddr();
			if (logger.isDebugEnabled()) {
				logger.debug("client ip from remote add is " + clientIp);
			}
		}
		
		return clientIp;
	}
}
