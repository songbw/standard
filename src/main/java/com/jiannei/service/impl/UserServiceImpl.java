package com.jiannei.service.impl;

import com.jiannei.bean.*;
import com.jiannei.dao.UserOptionDAO;
import com.jiannei.service.UserService;
import com.jiannei.utils.Config;
import com.jiannei.utils.RedisUtil;
import com.jiannei.utils.SMSUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

/**
 * Created by song on 2017/9/26.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);
    int expire = RedisUtil.appexpire;

    @Autowired
    private UserOptionDAO userOptionDAO;

    @Override
    public ResultBean getVerifyCode(String mobile) {
        ResultBean resultBean = new ResultBean();
        try{
       String result =  SMSUtil.singleSend(mobile,SMSUtil.VERIFYCODE,"");
            if(result.equals("400500")){
                resultBean.setFailMsg(400500,"短信发送失败！");
            }else{
               resultBean.setSucResult(result);
            }
        }
        catch (Exception e){
            e.printStackTrace();
          resultBean.setFailMsg(500555,"短信发送失败！");
        }
        return resultBean;
    }

    @Override
    public ResultBean verifyCode(String key,String code) {
        ResultBean resultBean = new ResultBean();
        boolean b = false;
        String redisValue = RedisUtil.getValue(key);
        if (code.equals(redisValue)){
            b = true;
            logger.info("验证码验证通过！");
        }
        resultBean.setSucResult(b);
        return resultBean;
    }

    @Override
    public ResultBean getUserList(UserQueryBean userQueryBean) {
        ResultBean resultBean = new ResultBean();
        List<User> userList = new ArrayList<User>();
        int offset = PageBean.getOffset(userQueryBean.getPageNo(),userQueryBean.getPageSize());
        HashMap map = new HashMap();
        map.put("pageNo", offset);
        map.put("pageSize", userQueryBean.getPageSize());
        if(userQueryBean.getNickName()!=null&&!"".equals(userQueryBean.getNickName())){
            map.put("nickName", new StringBuilder("%").append(userQueryBean.getNickName()).append("%").toString());
        }
        if(userQueryBean.getMobile()!=null&&!"".equals(userQueryBean.getMobile())){
            map.put("mobile", new StringBuilder("%").append(userQueryBean.getMobile()).append("%").toString());
        }
        map = ResultBean.setTimeInMap(map,userQueryBean.getStartTime(),userQueryBean.getEndTime());
        int total = 0;
        userList =  userOptionDAO.getUserLimit(map);
        total = userOptionDAO.getUserCount(map);
        PageBean pageBean = new PageBean();
        pageBean.setList(userList);
        pageBean.setTotal(total);
        pageBean.setPages(PageBean.getPages(total,userQueryBean.getPageSize()));
        pageBean.setPageNo(userQueryBean.getPageNo());
        pageBean.setPageSize(userQueryBean.getPageSize());
        resultBean.setSucResult(pageBean);
        return resultBean;
    }

    @Override
    public ResultBean getUserById(Long id) {
        ResultBean resultBean = new ResultBean();
        User user = userOptionDAO.getUserById(id);
        resultBean.setSucResult(user);
        return resultBean;
    }



    @Transactional
    @Override
    public ResultBean userRegister(RegisterBean registerBean) throws Exception {
        ResultBean resultBean = new ResultBean();
        if (!(boolean) userIsExsit(registerBean.getMobile()).getResult()) {
            resultBean.setFailMsg(500101, "mobile already exists");
            return resultBean;
        }
        User user = new User();
        String openId = UUID.randomUUID().toString();
        user.setUserName(registerBean.getMobile());
        user.setMobile(registerBean.getMobile());
        user.setOpenId(openId);
        user.setPassword(registerBean.getPassword());
        user.setCreateDate(new Date());
        user.setLastLoginDate(new Date());
        try {
            Integer id = userOptionDAO.userRegister(user);
            addToken(openId,1);//生成token
            resultBean.setSucResult(id);
        } catch (Exception e) {
            throw e;
        }
        return resultBean;
    }

    @Override
    public ResultBean userIsExsit(String mobile) {
        ResultBean resultBean = new ResultBean();
        User user = userOptionDAO.userIsExsit(mobile);
        boolean b = true;
        if (user != null) {
            b = false;
        }
        resultBean.setSucResult(b);
        return resultBean;
    }

    @Transactional
    @Override
    public ResultBean login(LoginBean loginBean) throws Exception{
        ResultBean resultBean = new ResultBean();
        User user = userOptionDAO.userIsExsit(loginBean.getMobile());
        if ((user == null) || (!loginBean.getPassword().equals(user.getPassword()))) {
            resultBean.setFailMsg(500102, "用户名或者密码不正确");
        }else {
            try {
                UserToken userToken = addToken(user.getOpenId(),1);
                user.setLastLoginDate(new Date());
                userOptionDAO.updateUser(user);
                User userNew = userOptionDAO.userIsExsit(loginBean.getMobile());
                logger.info(userNew.getOpenId() + "_____" + userToken.getToken() + "____" + expire);
                RedisUtil.putRedis(Config.getString("token4user"), userNew.getOpenId(), userToken.getToken(), expire);
                LoginRefUserBean loginRefUserBean = new LoginRefUserBean();
                loginRefUserBean.setExpire(expire);
                loginRefUserBean.setOpenId(userNew.getOpenId());
                loginRefUserBean.setToken(userToken.getToken());
                loginRefUserBean.setRefreshToken(userToken.getRefreshToken());
                loginRefUserBean.setUser(userNew);
                resultBean.setSucResult(loginRefUserBean);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return resultBean;
    }


    @Override
    public ResultBean myInfo(String openId) {
        ResultBean resultBean = new ResultBean();
        User userNew = userOptionDAO.getUserByOpenId(openId);
        resultBean.setSucResult(userNew);
        return resultBean;
    }


    @Transactional
    @Override
    public UserToken addToken(String openId,int type) throws Exception {
        String token = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();
        UserToken userToken = new UserToken();
        userToken.setOpenId(openId);
        userToken.setToken(token);
        userToken.setRefreshToken(refreshToken);
        userToken.setType(type);
        userToken.setUpdateTime(new Date());
        try {
            HashMap map = new HashMap();
            map.put("openId",openId);
            map.put("type",type);
            UserToken userTokenOld = userOptionDAO.getTokenByOpenId(map);
            if(userTokenOld!=null){
                userOptionDAO.updateToken(userToken);
            }else {
                userOptionDAO.addToken(userToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return userToken;
    }

    @Transactional
    @Override
    public ResultBean modifyPassword(LoginBean loginBean) throws Exception{
        ResultBean resultBean = new ResultBean();
        User user = userOptionDAO.userIsExsit(loginBean.getMobile());
        user.setPassword(loginBean.getPassword());
        try {
            userOptionDAO.updateUser(user);
            resultBean.setSucResult(1);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        return resultBean;
    }


    @Transactional
    @Override
    public ResultBean refreshToken(String openId, String refreshTokenOld,int type) throws Exception {
        ResultBean resultBean = new ResultBean();
        String token = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();
        UserToken userToken = new UserToken();
        userToken.setOpenId(openId);
        userToken.setToken(token);
        userToken.setType(type);
        userToken.setRefreshToken(refreshToken);
        userToken.setUpdateTime(new Date());
        String key="";
        try {
            HashMap map = new HashMap();
            map.put("openId",openId);
            map.put("type",type);
            UserToken userTokenOld = userOptionDAO.getTokenByOpenId(map);
            if (userTokenOld != null && refreshTokenOld.equals(userTokenOld.getRefreshToken())) {
                userOptionDAO.updateToken(userToken);
                switch (type) {
                    case 1:
                        key = Config.getString("token4user");
                        break;
                    case 2:
                        key = Config.getString("token4employee");
                        break;
                    case 3:
                        key = Config.getString("token4user");
                        openId=openId+"_H5";
                        break;
                }
                RedisUtil.putRedis(key,openId, token, expire);
                LoginRefUserBean loginRefUserBean = new LoginRefUserBean();
                loginRefUserBean.setOpenId(openId);
                loginRefUserBean.setToken(token);
                loginRefUserBean.setRefreshToken(refreshToken);
                loginRefUserBean.setExpire(expire);
                resultBean.setSucResult(loginRefUserBean);
            } else {
                resultBean.setFailMsg(SystemStatus.TOKEN_TIME_OUT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return resultBean;
    }

    @Transactional
    @Override
    public ResultBean complete(User user)  throws Exception {
        ResultBean resultBean = new ResultBean();
        User oldUser = userOptionDAO.getUserByOpenId(user.getOpenId());
        if (oldUser != null) {
            if (user.getBirthday() != null && !"".equals(user.getBirthday())) {
                oldUser.setBirthday(user.getBirthday());
            }
            if (user.getEmail() != null && !"".equals(user.getEmail())) {
                oldUser.setEmail(user.getEmail());
            }
            if (user.getHeadPic() != null && !"".equals(user.getHeadPic())) {
                oldUser.setHeadPic(user.getHeadPic());
            }
            if (user.getIdType() !=-1) {
                oldUser.setIdType(user.getIdType());
            }
            if (user.getIdCard() != null && !"".equals(user.getIdCard())) {
                oldUser.setIdCard(user.getIdCard());
            }
            if (user.getSex()!=-1) {
                oldUser.setSex(user.getSex());
            }
            if (user.getNickName() != null && !"".equals(user.getNickName())) {
                oldUser.setNickName(user.getNickName());
            }
            if (user.getRealName() != null && !"".equals(user.getRealName())) {
                oldUser.setRealName(user.getRealName());
            }
            logger.info(user.getIdCard()+"_"+user.getNickName() +user.getIdType());
            try {
                userOptionDAO.updateUser(oldUser);
                resultBean.setSucResult(1);
            }catch(Exception e){
                e.printStackTrace();
                throw e;
            }
        } else {
            resultBean.setFailMsg(500103, "user is not exist");
        }
        return resultBean;
    }
}
