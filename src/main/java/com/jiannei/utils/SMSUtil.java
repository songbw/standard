package com.jiannei.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by song on 2017/9/26.
 */
public class SMSUtil {

    /* 单条短信发送,智能匹配短信模板
    * @param apikey 成功注册后登录云片官网,进入后台可查看
    * @param text   需要使用已审核通过的模板或者默认模板
    * @param mobile 接收的手机号,仅支持单号码发送
    * @return json格式字符串
    * 请自行使用post方式请求,可使用Apache HttpClient
    */

    private static Logger logger = Logger.getLogger(SMSUtil.class);
//    private final static String APIKEY = "b211794df0ad0f587823337bbb118cdf";
    public static String APIKEY = "b0dc380e41a93480eee73d0b7a701e17";
    public final static String VERIFYCODE = "(#app#手机验证码，请完成验证)，如非本人操作，请忽略本短信";

    public static void main(String[] args) throws Exception {
        SMSUtil.singleSend("13811463960","","123455");
    }

    public static String singleSend(String mobile,String key,String msg) throws Exception {
        logger.info("SMSUtil single send message is mobile : " + mobile +" , key : " + key + " , msg : " + msg);
        String code = ServerUtil.randomCode();
        String time = new Date().getTime() + "";
        String text ="【和骊安（中国）】您的验证码是";
//        String text = "【喜鹊到家】" + code + "(#app#手机验证码，请完成验证)，如非本人操作，请忽略本短信";
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;

        try {
            HttpPost method = new HttpPost("https://sms.yunpian.com/v2/sms/single_send.json");
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            paramList.add(new BasicNameValuePair("apikey",APIKEY));
            paramList.add(new BasicNameValuePair("text",text));
            paramList.add(new BasicNameValuePair("mobile",mobile));
            method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));

            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info(responseText);
        if (responseText.contains("发送成功")) {
            RedisUtil.putRedis(time, mobile, code, 600); //10分钟
            logger.info("other system return's ：" + responseText);
            return time + mobile;
        } else {
            return "400500";
        }
    }
}
