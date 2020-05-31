package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.SeedInfo;
import com.smartfarm.base.farm.core.entity.vo.SeedInfoVo;

public interface SeedInfoDao {
	
	/**
	 * 新增
	 * @param seedInfo
	 * @return
	 */
    public int insert(SeedInfo seedInfo);

    /**
     * 根据id修改
     * @param seedInfo
     * @return
     */
    public int updateById(SeedInfo seedInfo);
    /**
     * 查询页面列表
     * @param name
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<SeedInfo> selectPageList(@Param("name") String name, @Param("status") Short status, @Param("start") Integer start, @Param("limit") Integer limit);
   /**
    * 统计页面列表
    * @param name
    * @param status
    * @return
    */
    public int selectPageTotal(@Param("name") String name, @Param("status") Short status);
    /**
     * 根据Id查找
     * @param seedInfo
     * @return
     */
    public SeedInfo selectSeedInfoById(@Param("id") Long id);
    /**
     * 查询全部种子
     * @return
     */
    public List<SeedInfoVo> selectAll();
    
    /**
     * 根据土地id查询种子信息
     * @param landId
     * @return
     */
    public List<SeedInfo> querySeedInfoByLandId(@Param("landId") Long landId);
    
    /**
     * 查询所有种子信息
     * @return
     */
    public List<SeedInfo> getAllSeedInfo();
}