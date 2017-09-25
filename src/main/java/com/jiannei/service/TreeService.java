package com.jiannei.service;

import com.jiannei.bean.ResultBean;

/**
 * Created by sbw22 on 2017/9/19.
 */
public interface TreeService {

    ResultBean findByParentId(long id) throws Exception;

}
