package com.jiannei.dao;


import com.jiannei.bean.BZJTreeBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by song on 2017/9/16.
 */
@Repository
public interface BZJTreeDAO {
    List<BZJTreeBean> selectByUrl();

    List<BZJTreeBean> selectByParentId(long id);
}
