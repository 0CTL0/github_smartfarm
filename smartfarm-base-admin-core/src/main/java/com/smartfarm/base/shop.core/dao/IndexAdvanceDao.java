package com.smartfarm.base.shop.core.dao;

import java.util.List;

import com.smartfarm.base.shop.core.entity.IndexAdvance;
import com.smartfarm.base.shop.core.entity.vo.IndexAdvanceVo;

public interface IndexAdvanceDao {

    int insert(IndexAdvance indexAdvance);

    int updateById(IndexAdvance indexAdvance);
    
    List<IndexAdvanceVo> queryAllList(Long farmId);
}