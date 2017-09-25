package com.jiannei.service.impl;

import com.jiannei.bean.BZJTreeBean;
import com.jiannei.bean.ResultBean;
import com.jiannei.dao.BZJTreeDAO;
import com.jiannei.service.TreeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sbw22 on 2017/9/19.
 */
@Service("treeService")
public class TreeServiceImpl implements TreeService {

    private static Logger log = Logger.getLogger(TreeServiceImpl.class);

    @Autowired
    private BZJTreeDAO bzjTreeDAO;

    @Override
    public ResultBean findByParentId(long id) throws Exception {
        ResultBean resultBean = new ResultBean();
        List<BZJTreeBean> bzjTreeBeanList = bzjTreeDAO.selectByParentId(id);
        resultBean.setSucResult(bzjTreeBeanList);
        return resultBean;
    }
}
