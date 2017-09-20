package com.jiannei.service.impl;

import com.jiannei.bean.PageBean;
import com.jiannei.bean.ResultBean;
import com.jiannei.bean.StandardBean;
import com.jiannei.dao.StandardDAO;
import com.jiannei.service.StandardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sbw22 on 2017/9/19.
 */
@Service("standardService")
public class StandardServiceImpl implements StandardService {

    private static Logger log = Logger.getLogger(StandardServiceImpl.class);

    @Autowired
    private StandardDAO standardDAO;

    @Override
    public ResultBean searchStandard(String key) throws Exception {
        ResultBean resultBean = new ResultBean();
        List<StandardBean> standardBeanList = standardDAO.selectLike("%"+key+"%");
        int total = standardDAO.selectLikeCount("%"+key+"%");
        PageBean pageBean = new PageBean();
        pageBean.setList(standardBeanList);
        pageBean.setTotal(total);
        resultBean.setSucResult(pageBean);
        return resultBean;
    }

    @Override
    public ResultBean searchStandardByCode(String code) throws Exception {
        ResultBean resultBean = new ResultBean();
        List<StandardBean> standardBeanList = standardDAO.selectByCode(code);
        int total = standardDAO.selectByCodeCount(code);
        PageBean pageBean = new PageBean();
        pageBean.setList(standardBeanList);
        pageBean.setTotal(total);
        resultBean.setSucResult(pageBean);
        return resultBean;
    }
}
