package com.jiannei.service.impl;

import com.jiannei.bean.*;
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
        List<StandardSearchRes> standardBeanList = standardDAO.selectLike("%"+key+"%");
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

    @Override
    public ResultBean searchStandardByType(String type,int currentPage,int pageSize,String key,long parentId) throws Exception {
        ResultBean resultBean = new ResultBean();
        StandardTypeParam standardTypeParam = new StandardTypeParam();
        standardTypeParam.setType(type);
        standardTypeParam.setPageSize(pageSize);
        standardTypeParam.setParentId(parentId);
        if (key !=null && !"".equals(key)) {
            standardTypeParam.setKey("%"+key+"%");
        }
        standardTypeParam.setCurrentPage(currentPage * pageSize);

        List<StandardBean> standardBeanList = standardDAO.selectByType(standardTypeParam);
        int total = standardDAO.selectByTypeCount(standardTypeParam);
        PageBean pageBean = new PageBean();
        pageBean.setPageNo(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setList(standardBeanList);
        pageBean.setTotal(total);
        pageBean.setPages(PageBean.getPages(total,pageSize));
        resultBean.setSucResult(pageBean);
        return resultBean;
    }

    @Override
    public ResultBean findById(long id) throws Exception {
        ResultBean resultBean = new ResultBean();
        StandardBean standardBean = standardDAO.selectById(id);
        resultBean.setSucResult(standardBean);
        return resultBean;
    }
}
