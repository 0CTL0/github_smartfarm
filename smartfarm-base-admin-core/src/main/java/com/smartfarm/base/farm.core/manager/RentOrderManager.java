package com.smartfarm.base.farm.core.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.farm.core.entity.ProgressInfo;
import com.smartfarm.base.farm.core.entity.RentOrder;
import com.smartfarm.base.farm.core.entity.SeedDetail;
import com.smartfarm.base.farm.core.entity.ShippingInfo;
import com.smartfarm.base.farm.core.entity.vo.RentOrderDetailVo;
import com.smartfarm.base.farm.core.entity.vo.RentOrderVo;


public interface RentOrderManager {
	/**
	 * 查询种植订单列表
	 * @param name
	 * @param status
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<RentOrderVo> getPageList(String orderNo, Short status, Integer start, Integer limit);
	/**
	 * 统计种植订单列表
	 * @param name
	 * @param status
	 * @return
	 */
	public int countPageList(String orderNo, Short status);
	/**
	 * 根据Id获得订单
	 * @param rentOrder
	 * @return
	 */
	public RentOrderVo getRentOrder(RentOrder rentOrder);
	/**
	 * 根据订单id获取种子信息
	 * @param seedDatail
	 * @return
	 */
	public List<SeedDetail> getSeedDatailByOrderId(SeedDetail seedDatail);
	/**
	 * 根据订单id获取配送信息
	 * @param shippingInfo
	 * @return
	 */
	public List<ShippingInfo> getShippingByOrderId(ShippingInfo shippingInfo);
	/**
	 * 编辑订单信息
	 * @param renderOrder
	 * @return
	 */
	public int editRentOrder(RentOrder rentOrder);
	/**
	 * 编辑种子信息
	 * @param seedDetail
	 * @return
	 */
	public int editSeedDetail(SeedDetail seedDetail);
	/**
	 * 根据id查询订单进展
	 * @param id
	 * @return
	 */
	public List<ProgressInfo> getProgressInfoList(ProgressInfo progressInfo);
	/**
	 * 新增状态信息
	 * @param progressInfo
	 * @param files
	 * @return
	 * @throws IOException
	 */
	public int insert(ProgressInfo progressInfo, MultipartFile[] files) throws IOException;
	/**
	 * 确认发货,若配送序号等于配送次数，则将订单状态更改为已完成
	 * @param shippingInfo
	 * @return
	 */
	public int received(ShippingInfo shippingInfo);
	/**
	 * 立即发货，若配送序号为1，即第一次配送则生成所有配送的时间
	 * @param shippingInfo
	 * @return
	 */
	public int shippingNow(ShippingInfo shippingInfo);
	
	/**
	 * 保存订单
	 * @param rentOrder
	 * @param seedDetails
	 * @return
	 */
	public int insertRentOrder(HttpServletRequest request, RentOrder rentOrder, String seedDetails);
	
	/**
     * 查询未支付种植的订单
     * @param payStatus
     * @return
     */
    public List<RentOrderVo> queryRentOrderUnPayList(short payStatus, HttpServletRequest request);
    
    /**
     * 查询种植状态查询种植列表
     * @param plantStatus
     * @param request
     * @return
     */
    public List<RentOrderVo> queryRentOrderPlantList(short plantStatus, HttpServletRequest request);
    
    /**
     * 根据订单id得到订单详细
     * @param orderId
     * @return
     */
    public RentOrderDetailVo queryRentOrderStatusById(Long orderId);
    
    /**
     * 取消订单
     * @param rentOrder
     * @return
     */
	public int cancelRentOrder(RentOrder rentOrder);
		
}
