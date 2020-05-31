package com.smartfarm.base.farm.core.manager;	
import java.text.ParseException;
import java.util.List;

import com.smartfarm.base.farm.core.entity.*;
import com.smartfarm.base.farm.core.entity.vo.FarmBazaarOrderVo;
import com.smartfarm.base.farm.core.entity.vo.FarmMemberCropsVo;

/**
 * @author 空谷丶临风
 * @date 创建时间：2019年12月09日 14:58:00
 * @version 1.0
 */
public interface FarmMemberCropsManager {

	/**
	 * 添加我的农作物
	 * @return
	 */
	public int addCrops(FarmDelegateOrder delegateOrder, FarmDelegatePlant delegatePlant);
	
	/**
	 * 根据地块id查询农作物信息
	 * @param landId
	 * @return
	 */
	public FarmMemberCrops queryCropsByLandId(Long landId);
	
	/**
	 * 查询我的农作物
	 * @param landId
	 * @return
	 */
	public List<FarmMemberCropsVo> queryMyCrops(Long landId);
	
	/**
	 * 查询我的已采摘作物
	 * @param landId
	 * @return
	 */
	public List<FarmMemberCropsVo> queryMyMatureCrops(Long landId);
	
	/**
	 * 查询地块的种植情况
	 * @param landId
	 * @return
	 */
	public List<FarmMemberCropsVo> queryLandPlantingSituation(Long landId);
	
	/**
	 * 删除我的作物记录，重新种植
	 * @param id
	 * @return
	 */
	public int deleteMyCropsById(Long id);

	/**
	 * 获取用户地块的全部已收获的农作物
	 * @param memberLandId
	 * @return
	 */
	public List<FarmMemberCropsVo> getAllMemberCrops(long memberLandId);

	/**
	 * 出售农作物
	 * @param farmBazaarProduct
	 * @return
	 */
	public int addFarmBazaarProduct(FarmBazaarProduct farmBazaarProduct);

	/**
	 * 根据id查询我的农作物
	 * @param id
	 * @return
	 */
	FarmMemberCrops getFarmMemberCropsById(Long id);

	/**
	 * 根据id查询作物信息
	 * @param id
	 * @return
	 */
	public FarmMemberCropsVo queryMemberCropsVoById(Long id);

	/**
	 * 查询租地地块信息
	 * @param memberLandId
	 * @return
	 */
	public FarmRentLandInfo queryRentLandByMemberLandId(Long memberLandId);

	/**
	 * 编辑我的农作物
	 * @param farmMemberCrops
	 * @return
	 */
	int editFarmMemberCrops(FarmMemberCrops farmMemberCrops);

	/**
	 * 查询用户土地的全部在售商品
	 * @param memberLandId
	 * @return
	 */
	List<FarmBazaarProduct> getMemberLandProduct(Long memberLandId);

	/**
	 * 下架在售农作物
	 * @param id
	 * @return
	 */
	int removeFarmBazaarProduct(Long id);

	/**
	 * 查询全部的在售商品
	 * @return
	 */
	List<FarmBazaarProduct> getAllBazaarProduct(Long farmId);

	/**
	 * 根据id查询在售的农作物商品
	 * @param id
	 * @return
	 */
	FarmBazaarProduct getBazaarProductById(Long id);

	/**
	 * 生成自由销售订单
	 * @param farmBazaarOrder
	 * @return
	 */
	int addBazaarOrder(FarmBazaarOrder farmBazaarOrder) throws Exception;

	/**
	 * 通过订单号查询订单
	 * @param OderCode
	 * @return
	 */
	FarmBazaarOrder getBazaarOrderByOrderCode(String OderCode);

	/**
	 * 更新订单信息
	 * @param farmBazaarOrder
	 * @return
	 */
	int editBazaarOrder(FarmBazaarOrder farmBazaarOrder);

	/**
	 * 获取用户的全部订单
	 * @param memberId
	 * @return
	 */
	List<FarmBazaarOrderVo> getAllOrder(Long memberId);

	/**
	 * 确认收货
	 * @param farmBazaarOrderId
	 * @return
	 */
	int toReceived(Long farmBazaarOrderId);

	/**
	 * 下架过期的自由销售商品
	 * @return
	 */
	int removeOverdueBazaarProduct() throws ParseException;
	
	/**
     * 获取用户土地的自由销售订单
     * @param memberLandId
     * @return
     */
	List<FarmBazaarOrderVo> getMemberLandOrderList(Long memberLandId);
	
	/**
	 * 取消订单
	 * @param id
	 */
	void orderCancel(Long id);
	
	/**
	 * 超时处理
	 * @param id
	 */
	void orderOutTime(Long id);

	/**
	 * 订单支付成功
	 * @param farmBazaarOrder
	 */
	void orderSuccess(FarmBazaarOrder farmBazaarOrder);
}
