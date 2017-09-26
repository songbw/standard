package com.jiannei.bean;

/**
 * Created by song on 2017/9/26.
 */
public class UserQueryBean extends QueryBean {
    private String nickName;
    private String mobile;
    private Integer type =-1;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
