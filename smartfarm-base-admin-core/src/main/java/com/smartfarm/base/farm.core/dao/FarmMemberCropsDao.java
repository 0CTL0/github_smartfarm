package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.FarmMemberCrops;
import com.smartfarm.base.farm.core.entity.vo.FarmMemberCropsVo;

public interface FarmMemberCropsDao {
	
	/**
	 * 添加我的农作物
	 * @param crops
	 * @return
	 */
	public int insert(FarmMemberCrops crops);
	
	/**
	 * 根据地块查询作物信息 
	 * @param landId
	 * @return
	 */
	public FarmMemberCrops getCropsByLandId(@Param("landId") Long landId);
	
	/**
	 * 查询（我的）土地在种植的农作物
	 * @param landId
	 * @return
	 */
	public List<FarmMemberCropsVo> getCropsByLandIdAndStatus(@Param("landId") Long landId);
	
	/**
	 * 查询我的已采摘的作物
	 * @param landId
	 * @return
	 */
	public List<FarmMemberCropsVo> getMatureCropsByLandId(@Param("landId") Long landId);
	
	/**
	 * 根据id查找我的作物
	 * @param id
	 * @return
	 */
	public FarmMemberCrops getById(@Param("id") Long id);

	/**
	 * 根据id查询作物信息
	 * @param id
	 * @return
	 */
	public FarmMemberCropsVo getCropById(@Param("id") Long id);
	
	/**
	 * 更新库存
	 * @return
	 */
	public int updateInventory(FarmMemberCrops memberCrops);
	
	/**
	 * 根据我的地块id查找作物状况
	 * @param landId
	 * @return
	 */
	public List<FarmMemberCropsVo> getMyCropsInfoByLandId(@Param("landId") Long landId);
	
	/**
	 * 重新种植
	 * 根据id删除我的作物记录
	 * @param id
	 * @return
	 */
	public int deleteById(@Param("id") Long id);
	
	/**
	 * 采摘，修改status的值
	 * @param memberCrops
	 * @return
	 */
	public int updateStatusById(FarmMemberCrops memberCrops);

	/**
	 * 查询用户地块的全部已收获的农作物
	 * @param memberLandId
	 * @return
	 */
    List<FarmMemberCropsVo> getAllMemberCrops(@Param("memberLandId") Long memberLandId);

	/**
	 * 根据委托单id查找用户作物
	 * @param orderId
	 * @return
	 */
	public FarmMemberCrops getByDelegateOrderId(@Param("orderId") Long orderId);

	/**
	 * 采摘后回填数据
	 * @param memberCrops
	 * @return
	 */
	public int updateAfterPick(FarmMemberCrops memberCrops);

	/**
	 * 拼接采摘委托内容
	 * @param orderId
	 * @return
	 */
	public FarmMemberCropsVo getPickDelegateInfo(@Param("orderId") Long orderId);


    int insert2(FarmMemberCrops record);

    int updateById(FarmMemberCrops record);

    
}