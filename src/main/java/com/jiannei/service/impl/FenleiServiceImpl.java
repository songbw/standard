package com.jiannei.service.impl;

import com.jiannei.bean.FenleiBean;
import com.jiannei.bean.ResultBean;
import com.jiannei.dao.FenleiDAO;
import com.jiannei.service.FenleiService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sbw22 on 2017/9/22.
 */
@Service("fenleiService")
public class FenleiServiceImpl implements FenleiService{

    private static Logger log = Logger.getLogger(FenleiServiceImpl.class);

    @Autowired
    private FenleiDAO fenleiDAO;

    @Override
    public ResultBean getAllFenlei() throws Exception {
        ResultBean resultBean = new ResultBean();
        List<FenleiBean> fenleiBeanList = fenleiDAO.selectAll();
        resultBean.setSucResult(fenleiBeanList);
        return resultBean;
    }
}
