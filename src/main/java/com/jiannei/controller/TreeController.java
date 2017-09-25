package com.jiannei.controller;

import com.jiannei.bean.ResultBean;
import com.jiannei.bean.SystemStatus;
import com.jiannei.service.StandardService;
import com.jiannei.service.TreeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sbw22 on 2017/9/19.
 */
@RequestMapping(value = "/tree")
@Controller
public class TreeController {

    private static Logger logger = Logger.getLogger(TreeController.class);

    @Autowired
    private TreeService treeService;

    @RequestMapping(value="/parent",method= RequestMethod.GET)
    public @ResponseBody
    ResultBean getParentList(long id) {
        ResultBean resultBean = new ResultBean();
        try {
            resultBean = treeService.findByParentId(id) ;
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean ;
    }
}
