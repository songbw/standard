package com.jiannei.filter;

import com.jiannei.utils.IPAddressUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * Tomcat GET方式表单提交乱码解决。
 * 
 * @author BeanSoft
 * 
 */
public class MyFilter implements Filter {

  private static Logger log = Logger.getLogger(MyFilter.class);

  public void destroy() {
    // TODO Auto-generated method stub
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
          ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    req.setCharacterEncoding("utf-8");
    log.info("client ip is :"+IPAddressUtil.getClientIP(req));
    log.info("请求地址:" + req.getRequestURL() + "   请求参数：" + req.getQueryString() + "  涉及到的方法为：" + req.getMethod()+"请求头信息: token: "+req.getHeader("token") + " openId: "+req.getHeader("openId"));
    int length = req.getContentLength();
    if (length > 0) {
      BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(req, length);
      InputStream is = bufferedRequest.getInputStream();
      byte[] content = new byte[length];
      int pad = 0;
      while (pad < length) {
        pad += is.read(content, pad, length);
      }
      log.info("涉及到的内容为:" + new String(content, "utf-8"));
      request = bufferedRequest;
    }
    long bef = System.currentTimeMillis();
    chain.doFilter(request, response);
    long aft = System.currentTimeMillis();

    log.info(" Request to " + req.getRequestURI() + "  use: " + (aft - bef) + " ms");

  }

  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("创建filter");
  }

}