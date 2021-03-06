package com.jiannei.service;

import com.jiannei.bean.ResultBean;

/**
 * Created by sbw22 on 2017/9/19.
 */
public interface StandardService {

    ResultBean searchStandard(String key) throws Exception;

    ResultBean searchStandardByCode(String code) throws Exception;

    ResultBean searchStandardByType(String type,int currentPage,int pageSize,String key,long parentId) throws Exception;

    ResultBean findById(long id) throws Exception;

}
