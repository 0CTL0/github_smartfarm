package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.ProgressInfo;
import com.smartfarm.base.farm.core.entity.vo.ProgressInfoVo;

public interface ProgressInfoDao {
	
	/**
	 * 新增
	 * @param progressInfo
	 * @return
	 */
    public int insert(ProgressInfo progressInfo);

    /**
     * 根据id修改
     * @param progressInfo
     * @return
     */
    public int updateById(ProgressInfo progressInfo);
    /**
     * 根据id查看订单进展
     * @param id
     * @return
     */
    public List<ProgressInfo> queryAllOrderProgress(@Param("id") Long id);
    /**
     * 根据id删除
     * @param id
     * @return
     */
    public int deleteProgressInfoById(@Param("id") Long id);
    /**
     * 查询订单进展
     * @param progressInfo
     * @return
     */
    public List<ProgressInfo> selectProgressList(ProgressInfo progressInfo);
    
    /**
     * 根据项目id得到项目进展信息(小程序端)
     * @param id
     * @return
     */
    public List<ProgressInfoVo> getCrowdfundingProgressInfoList(@Param("id") Long id);
}