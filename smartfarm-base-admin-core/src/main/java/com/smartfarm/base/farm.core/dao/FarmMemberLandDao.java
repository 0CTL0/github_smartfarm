package com.smartfarm.base.farm.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.smartfarm.base.farm.core.entity.FarmMemberLand;
import com.smartfarm.base.farm.core.entity.vo.FarmMemberLandVo;

public interface FarmMemberLandDao {
	
	/**
	 * 根据会员id查询其所有土地
	 * @param memberId
	 * @return
	 */
	public List<FarmMemberLandVo> getLandsByMemberId(@Param("memberId") Long memberId);
	
	/**
	 * 根据id查询土地信息
	 * @param id
	 * @return
	 */
	public FarmMemberLand getById(@Param("id") Long id);
	
	/**
	 * 根据id修改土地别名
	 * @param memberLand
	 * @return
	 */
	public int updateById(FarmMemberLand memberLand);
	
	
	
	
	
    int deleteByPrimaryKey(Long id);

    int insert2(FarmMemberLand record);

    int insert(FarmMemberLand record);

    
    /**
	 * 查询所有租赁中的用户土地
	 * @return
	 */
	List<FarmMemberLand> selectAllMemberLand();
    

    int updateByPrimaryKey(FarmMemberLand record);

	/**
	 * 查询用户土地的地块id
	 * @param memberLandId
	 * @return
	 */
	Long selectFarmTunnelId(@Param("memberLandId") Long memberLandId);

	/**
	 * 统计用户土地未完成的种植委托的面积
	 * @param memberLandId
	 * @return
	 */
	int countDelegatePlantArea(@Param("memberLandId") Long memberLandId);
}