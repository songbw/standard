package com.jiannei.controller;

import com.jiannei.bean.ResultBean;
import com.jiannei.bean.SystemStatus;
import com.jiannei.service.FenleiService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sbw22 on 2017/9/22.
 */
@RequestMapping(value = "/fenlei")
@Controller
public class FenleiController {

    private static Logger logger = Logger.getLogger(FenleiController.class);

    @Autowired
    private FenleiService fenleiService;

    @RequestMapping(value="",method= RequestMethod.GET)
    public @ResponseBody
    ResultBean list() {
        ResultBean resultBean = new ResultBean();
        try {
            resultBean = fenleiService.getAllFenlei() ;
        } catch (Exception e) {
            e.printStackTrace();
            resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
        }
        return resultBean ;
    }

}
