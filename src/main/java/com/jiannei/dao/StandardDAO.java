package com.jiannei.dao;

import com.jiannei.bean.StandardBean;
import com.jiannei.bean.StandardSearchRes;
import com.jiannei.bean.StandardTypeParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sbw22 on 2017/9/18.
 */
@Repository
public interface StandardDAO {

    Integer addStandard(StandardBean standardBean) ;

    StandardBean selectTopOne() ;

    int countByParentId(Long parentId) ;

    List<StandardBean> selectAll() ;

    List<StandardSearchRes> selectLike(String key) ;

    List<StandardBean> selectByCode(String code) ;

    int selectLikeCount(String key) ;

    int selectByCodeCount(String code) ;

    int updateImgFlag(StandardBean standardBean) ;

    int updateNameKey(StandardBean standardBean) ;

    List<StandardBean> selectByType(StandardTypeParam standardTypeParam) ;

    int selectByTypeCount(StandardTypeParam standardTypeParam) ;

    StandardBean selectById(long id);
}
