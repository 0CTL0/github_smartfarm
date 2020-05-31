package com.smartfarm.base.farm.core.manager;

import java.text.ParseException;
import java.util.List;

import com.smartfarm.base.farm.core.entity.FarmMemberCrops;
import com.smartfarm.base.farm.core.entity.FarmMemberLand;
import com.smartfarm.base.farm.core.entity.WorkingLog;
import com.smartfarm.base.farm.core.entity.vo.FarmMemberLandVo;
import com.smartfarm.base.farm.core.entity.vo.WorkingLogVo;
import org.apache.ibatis.annotations.Param;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月18日 16:50:38
 * @version 1.0
 */
public interface FarmMemberLandManager {

	/**
	 * 根据会员id查询其所有土地
	 * @param memberId
	 * @return
	 */
	public List<FarmMemberLandVo> queryLandsByMemberId(Long memberId);

	/**
	 * 查询用户土地的劳动日志
	 * @return
	 */
	public List<WorkingLogVo> getWorkingLogVoList(Long memberLandId);

	/**
	 * 根据土地租赁id查询土地信息
	 * @param id
	 * @return
	 */
	public FarmMemberLand queryMemberLandById(Long id);

	/**
	 * 根据土地id修改土地别名
	 * @param memberLand
	 * @return
	 */
	public int modifyLandAliasById(FarmMemberLand memberLand);

	/**
	 * 更新到期的租赁土地
	 */
	public void updateOverdueLand() throws ParseException;

	/**
	 * 根据rentlandId查询farmId
	 * @param rentLandId
	 * @return
	 */
	Long selectFarmId(@Param("rentLandId") Long rentLandId);

	/**
	 * 查询用户土地的地块id
	 * @param memberLandId
	 * @return
	 */
	Long selectFarmTunnelId(@Param("memberLandId") Long memberLandId);

	/**
	 * 统计用户土地未完成的委托种植面积
	 * @param memberLandId
	 * @return
	 */
	int getCountDelegatePlantArea(Long memberLandId);

	/**
	 * 用户种植登记
	 * @param farmMemberCrops
	 */
	void plantRegister(FarmMemberCrops farmMemberCrops);
}
