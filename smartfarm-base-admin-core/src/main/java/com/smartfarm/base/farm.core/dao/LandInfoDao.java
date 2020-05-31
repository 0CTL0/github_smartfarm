package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.LandInfo;
import com.smartfarm.base.farm.core.entity.vo.LandInfoOrderVo;
import com.smartfarm.base.farm.core.entity.vo.LandInfoTypeVo;
import com.smartfarm.base.farm.core.entity.vo.LandInfoVo;

public interface LandInfoDao {
	
	/**
	 * 新增
	 * @param landInfo
	 * @return
	 */
    public int insert(LandInfo landInfo);

    /**
     * 修改
     * @param landInfo
     * @return
     */
    public int updateById(LandInfo landInfo);
    
    /**
     * 查询土地列表
     * @param name
     * @param status
     * @param start
     * @param limit
     * @return
     */
    public List<LandInfo> selectPageList(@Param("name") String name, @Param("status") Short status, @Param("start") Integer start, @Param("limit") Integer limit);
   /**
    * 统计土地列表
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
    public LandInfo selectLandInfoById(@Param("id") Long id);
    /**
     * 查询所有已上架的土地信息
     * @return
     */
    public List<LandInfoVo> selectAllByStatus();   
    
    
    /**
     * 小程序查询土地列表
     * @return
     */
    public List<LandInfo> queryMiNiLandInfoList();
    
    /**
     * 根据土地名称查找土地列表
     * @param name
     * @return
     */
    public List<LandInfo> queryMiNiLandInfoDetailListByName(@Param("name") String name);
    
    /**
     * 根据类型查询土地信息列表
     * @param typeId
     * @return
     */
    public List<LandInfoTypeVo> queryLangInfoWithTypeList(@Param("typeId") Long typeId);
    
    /**
     * 根据土地id查询土地的所有信息
     * @param landId
     * @return
     */
    public LandInfoVo queryLangInfoMsgByLandId(@Param("landId") Long landId);
    
    /**
     * 根据土地id和地块信息查询
     * @param landId
     * @param areaId
     * @return
     */
    public LandInfoOrderVo queryLandInfoWithOrder(@Param("landId") Long landId, @Param("areaId") Long areaId);
    
}