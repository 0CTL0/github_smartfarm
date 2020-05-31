package com.smartfarm.base.shop.core.manager;

import java.util.List;

import com.smartfarm.base.shop.core.entity.IndexAdvance;
import com.smartfarm.base.shop.core.entity.vo.IndexAdvanceVo;


public interface IndexAdvanceManager {
	
	public List<IndexAdvanceVo> queryAllList(Long farmId);
	
	public void insert(IndexAdvance indexAdvance);
	
	public void updateById(IndexAdvance indexAdvance);
}
