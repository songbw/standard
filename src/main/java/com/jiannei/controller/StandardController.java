package com.jiannei.controller;

import com.jiannei.bean.ResultBean;
import com.jiannei.bean.SystemStatus;
import com.jiannei.service.StandardService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sbw22 on 2017/9/19.
 */
@RequestMapping(value = "/standard")
@Controller
public class StandardController {

    private static Logger logger = Logger.getLogger(StandardController.class);

    @Autowired
    private StandardService standardService;

    @RequestMapping(value="/search",method= RequestMethod.GET)
    public @ResponseBody ResultBean serStandard(String key) {
        ResultBean resultBean = new ResultBean();
        try {
            resultBean = standardService.searchStandard(key) ;
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean ;
    }

    @RequestMapping(value="",method= RequestMethod.GET)
    public @ResponseBody ResultBean codeStandard(String code) {
        ResultBean resultBean = new ResultBean();
        try {
            resultBean = standardService.searchStandardByCode(code) ;
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean ;
    }
}
