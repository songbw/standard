package com.jiannei.dao;

import com.jiannei.bean.User;
import com.jiannei.bean.UserToken;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


/**
 * Created by song on 2017/9/26.
 */
@Repository
public interface UserOptionDAO {

    Integer userRegister(User user);

    User userIsExsit(String userName);

    Integer updateUser(User user);

    User getUserByOpenId(String openId);

    Integer addToken(UserToken userToken);

    UserToken getTokenByOpenId(HashMap map);

    Integer updateToken(UserToken userToken);

    /*User getUserInfoByMobile(String userName);*/

    Integer  resetUserAddressStatusByOpenId(String openId);

    Integer delUserAddressById(long l);

    User getUserByWeiXinOpenId(String weixinOpenId);

    List<User> getUserLimit(HashMap map);

    Integer getUserCount(HashMap map);

    User getUserById(Long id);

    Integer getSleepUserCount(HashMap map);
}
