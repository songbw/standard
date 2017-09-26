package com.jiannei.service;

import com.jiannei.bean.*;

/**
 * Created by song on 2017/9/26.
 */
public interface UserService {
    ResultBean userRegister(RegisterBean registerBean) throws Exception;

    ResultBean userIsExsit(String mobile);

    ResultBean login(LoginBean loginBean)  throws Exception;

    ResultBean complete(User user) throws Exception;

    ResultBean refreshToken(String openId, String refreshTokenOld, int type) throws Exception;

    UserToken addToken(String openId, int type) throws Exception;

    ResultBean modifyPassword(LoginBean loginBean) throws Exception;

    ResultBean myInfo(String openId);

    ResultBean getVerifyCode(String mobile);

    ResultBean verifyCode(String key, String code);

    ResultBean getUserList(UserQueryBean userQueryBean);

    ResultBean getUserById(Long id);
}
