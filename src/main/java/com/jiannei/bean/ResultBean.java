package com.jiannei.bean;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;


public class ResultBean {


 public String requestId =UUID.randomUUID().toString().replace("-", "");
 public String httpCode = "";
  public int code;
  public String message;
  public Object result ;

 /*   public ResultBean(){
    }

    public ResultBean(String requestId) {
        if(requestId!=null&&!"".equals(requestId))
        this.requestId = requestId;
    }*/

    public int getCode() {
    return code;
  }
  private void setCode(int code) {
    this.code = code;
  }
  public String getMessage() {
    return message;
  }
  private void setMessage(String message) {
    this.message = message;
  }
  public Object getResult() {
    return result;
  }
  private void setResult(Object result) {
    this.result = result;
  }

  public String getRequestId() {
    return requestId;
  }
  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }
  public String getHttpCode() {
    return httpCode;
  }
  public void setHttpCode(String httpCode) {
    this.httpCode = httpCode;
  }
  /**
   * 
   * @param status  异常返回枚举类型
   */
  public void setFailMsg(SystemStatus status) {
    setCode(status.getCode());
    setMessage(status.getStr());
  }

  /**
   *
   */
  public void setFailMsg(int code,String str) {
    setCode(code);
    setMessage(str);
  }
  
  /**
   * 
   * @param result  成功返回数据
   */
  public void setSucResult(Object result) {
    setCode(SystemStatus.OK.getCode());
    setMessage(SystemStatus.OK.getStr());
    setResult(result);
  }


    public static HashMap setTimeInMap(HashMap map , Date startTime,Date endTime){
        if(startTime!=null){
            map.put("startTime", startTime);
            map.put("endTime", new Date());
        }
        if(endTime!=null){
            if(startTime==null){
                try {
                    map.put("startTime",  new SimpleDateFormat("yy/MM/dd HH:mm:ss").parse("1970/1/1 00:00:00"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            map.put("endTime", endTime);
        }
        if(endTime==null&&startTime==null){
            try {
                map.put("startTime",  new SimpleDateFormat("yy/MM/dd HH:mm:ss").parse("1970/1/1 00:00:00"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            map.put("endTime", new Date());
        }
        return map;
    }

}
