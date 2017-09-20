package com.jiannei.dao;

import com.jiannei.bean.StandardBean;
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

    List<StandardBean> selectLike(String key) ;

    List<StandardBean> selectByCode(String code) ;

    int selectLikeCount(String key) ;

    int selectByCodeCount(String code) ;

    int updateImgFlag(StandardBean standardBean) ;

    int updateNameKey(StandardBean standardBean) ;
}
