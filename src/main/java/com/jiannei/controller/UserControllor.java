package com.jiannei.controller;

import com.jiannei.bean.*;
import com.jiannei.service.UserService;
import com.jiannei.utils.Config;
import com.jiannei.utils.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 用户个人信息相关接口
 * Created by song on 2017/9/26.
 */

@Controller
@RequestMapping("/user")
public class UserControllor {

    private static Logger logger = Logger.getLogger(UserControllor.class);

    @Autowired
    private UserService userService;


    /**
     * 获取手机短信验证码 47
     * @return
     */
    @RequestMapping(value="/getverifycode",method= RequestMethod.GET)
    public @ResponseBody
    ResultBean getVerifyCode(String mobile){
        ResultBean resultBean = new ResultBean();
        try {
            resultBean = userService.getVerifyCode(mobile);
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean;
    }

    /**
     * 验证手机短信验证码 48
     * @return
     */
    @RequestMapping(value="/verifycode",method= RequestMethod.GET)
    public @ResponseBody
    ResultBean verifyCode(String key ,String code){
        ResultBean resultBean = new ResultBean();
        try {
            resultBean = userService.verifyCode(key,code);
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean;
    }

    /**
     * 查询手机是否可用 46
     * @return
     */
    @RequestMapping(value="/mobilecanbeuse",method= RequestMethod.GET)
    public @ResponseBody
    ResultBean userIsExsit(String mobile){
        ResultBean resultBean = new ResultBean();
        try {
            resultBean = userService.userIsExsit(mobile);
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean;
    }

    /**
     * 用户注册 49
     * @return
     */
    @RequestMapping(value="/register",method= RequestMethod.POST)
    public @ResponseBody
    ResultBean userRegister(@RequestBody RegisterBean registerBean){
        ResultBean resultBean = new ResultBean();
        if(registerBean.getMobile()==null||"".equals(registerBean.getMobile())){
            logger.error("user register userRegister  error Exception：type is null." + resultBean.getRequestId());
            resultBean.setFailMsg(400101,"registerBean.getMobile is null");
            return resultBean;
        }
        if(registerBean.getPassword()==null||"".equals(registerBean.getPassword())){
            logger.error("user register userRegister  error Exception：type is null." + resultBean.getRequestId());
            resultBean.setFailMsg(400102,"registerBean.getPassword is null");
            return resultBean;
        }

        try {
            resultBean = userService.userRegister(registerBean);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean;
    }

    /**
     * 用户信息补全 55
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/complete",method= RequestMethod.POST)
    public ResultBean complete (@RequestHeader("token") String token, @RequestHeader("openId") String openId, @RequestBody User user){
        ResultBean resultBean = new ResultBean();
        if (!TokenUtil.verifyToken(Config.getString("token4user"), openId,token)) {
            resultBean.setFailMsg(SystemStatus.TOKEN_TIME_OUT);
            return resultBean;
        }
        if(openId.contains("_H5")){
            openId = openId.split("_")[0];
        }
        if(user.getOpenId()==null||"".equals(user.getOpenId())){
            logger.error("user login  error Exception：user.OpenId is null." + resultBean.getRequestId());
            resultBean.setFailMsg(400103,"user.OpenId is null");
            return resultBean;
        }
        try {
            resultBean = userService.complete(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean;
    }

    /**
     * 用户登录 50
     * @return
     */
    @RequestMapping(value="/login",method= RequestMethod.POST)
    public @ResponseBody
    ResultBean login (@RequestBody LoginBean loginBean){
        ResultBean resultBean = new ResultBean();
        if(loginBean.getMobile()==null||"".equals(loginBean.getMobile())){
            logger.error("user login  error Exception：type is null." + resultBean.getRequestId());
            resultBean.setFailMsg(400104,"registerBean.getMobile is null");
            return resultBean;
        }
        if(loginBean.getPassword()==null||"".equals(loginBean.getPassword())){
            logger.error("user login  error Exception：type is null." + resultBean.getRequestId());
            resultBean.setFailMsg(400105,"registerBean.getPassword is null");
            return resultBean;
        }
        if(loginBean.getClientId()==null||"".equals(loginBean.getClientId())){
            logger.error("user login  error Exception：type is null." + resultBean.getRequestId());
            resultBean.setFailMsg(400106,"registerBean.getClientId is null");
            return resultBean;
        }
        try {
            resultBean = userService.login(loginBean);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean;
    }


    /**
     * 用户获取个人信息 53
     * @return
     */
    @RequestMapping(value="/myinfo",method= RequestMethod.GET)
    public @ResponseBody
    ResultBean myInfo (@RequestHeader("token") String token, @RequestHeader("openId") String openId){
        ResultBean resultBean = new ResultBean();
       /* if (!TokenUtil.verifyToken(Config.getString("token4user"), openId,token)) {
            resultBean.setFailMsg(SystemStatus.TOKEN_TIME_OUT);
            return resultBean;
        }*/
        if(openId.contains("_H5")){
            openId = openId.split("_")[0];
        }
        try {
            resultBean = userService.myInfo(openId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean;
    }

    /**
     * refreshToken  54
     * type 1 用戶端
     * @return
     */
    @RequestMapping(value="/refreshtoken",method= RequestMethod.GET)
    public @ResponseBody
    ResultBean refreshToken(String openId,String refreshToken,int type){
        ResultBean resultBean = new ResultBean();
        try {
            resultBean = userService.refreshToken(openId,refreshToken,type);
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean;
    }


    /**
     * 修改密码  56
     * @return
     */
    @RequestMapping(value="/modifypassword",method= RequestMethod.POST)
    public @ResponseBody
    ResultBean modifyPassword(@RequestHeader("token") String token, @RequestHeader("openId") String openId, @RequestBody LoginBean loginBean){
        ResultBean resultBean = new ResultBean();
      /*  if (!TokenUtil.verifyToken(Config.getString("token4user"), openId,token)) {
            resultBean.setFailMsg(SystemStatus.TOKEN_TIME_OUT);
            return resultBean;
        }*/
        if(openId.contains("_H5")){
            openId = openId.split("_")[0];
        }
        if(loginBean.getMobile()==null||"".equals(loginBean.getMobile())){
            logger.error("user login  error Exception：type is null." + resultBean.getRequestId());
            resultBean.setFailMsg(400104,"registerBean.getMobile is null");
            return resultBean;
        }
        if(loginBean.getPassword()==null||"".equals(loginBean.getPassword())){
            logger.error("user login  error Exception：type is null." + resultBean.getRequestId());
            resultBean.setFailMsg(400105,"registerBean.getPassword is null");
            return resultBean;
        }
        try {
            resultBean = userService.modifyPassword(loginBean);
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean;
    }

    /**
     * 忘记密码  57
     */
    @RequestMapping(value="/resetpassword",method= RequestMethod.POST)
    public @ResponseBody
    ResultBean resetPassword(@RequestBody LoginBean loginBean){
        ResultBean resultBean = new ResultBean();
        if(loginBean.getMobile()==null||"".equals(loginBean.getMobile())){
            logger.error("user login  error Exception：type is null." + resultBean.getRequestId());
            resultBean.setFailMsg(400104,"registerBean.getMobile is null");
            return resultBean;
        }
        if(loginBean.getPassword()==null||"".equals(loginBean.getPassword())){
            logger.error("user login  error Exception：type is null." + resultBean.getRequestId());
            resultBean.setFailMsg(400105,"registerBean.getPassword is null");
            return resultBean;
        }
        try {
            resultBean = userService.modifyPassword(loginBean);
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean;
    }




    /**
     * 客户管理  118
     */
    @RequestMapping(value="/getuserlist",method= RequestMethod.POST)
    public @ResponseBody
    ResultBean getUserList(@RequestBody UserQueryBean userQueryBean){
        ResultBean resultBean = new ResultBean();
        try {
            resultBean = userService.getUserList(userQueryBean);
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean;
    }

    /**
     * 管理端获取客户详情  119
     */
    @RequestMapping(value="/getuserbyid",method= RequestMethod.GET)
    public @ResponseBody
    ResultBean getUserById(Long id){
        ResultBean resultBean = new ResultBean();
        try {
            resultBean = userService.getUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean;
    }

}
