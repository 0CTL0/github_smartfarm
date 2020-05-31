package com.smartfarm.base.service.shop.service.impl;

import com.smartfarm.base.farm.core.manager.CrowdFundingOrderManager;
import com.smartfarm.base.farm.core.manager.FarmRentLandOrderManager;
import com.smartfarm.base.shop.core.entity.BaseOrder;
import com.smartfarm.base.shop.core.manager.ActivityManager;
import com.smartfarm.base.shop.core.manager.BaseOrderManager;
import com.smartfarm.base.shop.core.manager.OrderInfoManager;
import com.smartfarm.base.shop.core.service.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("orderService")
public class OrderServiceImpl implements OrderService, InitializingBean {
	protected static Logger log = Logger.getLogger(OrderServiceImpl.class);
	/** 分钟 */
	private final int TIME_OUT = 10;
	/** 缓存订单ID信息，与订单信息是一一对应的 */
	private List<Long> orderIdList;
	/** 缓存订单信息 */
	private Map<Long, OrderMessage> orderMessageMap;
	/** 在修改订单状态时，确保不会出现重复处理的情况 */
	private List<Long> savedOrderList;
	
	@Resource
	private BaseOrderManager baseOrderManager;
	@Resource
	private OrderInfoManager orderInfoManager;
	@Resource
	private ActivityManager activityManager;
	@Resource
	private FarmRentLandOrderManager farmRentLandOrderManager;
	@Resource
	private com.smartfarm.base.farm.core.manager.FarmMemberCropsManager FarmMemberCropsManager;
	@Autowired
	private CrowdFundingOrderManager crowdFundingOrderManager;
	//
	// 构造方法
	//
	public OrderServiceImpl() {
		orderMessageMap = new HashMap<Long, OrderMessage>();
		orderIdList = new LinkedList<Long>();
		savedOrderList = new LinkedList<Long>();
		
		new CheckOrderThread().start();
	}

	@Override
	public Map<String, Object> makeOrder(Long id) throws Exception {
		log.info(new StringBuilder("[OrderServiceImpl:makeOrder]").append("生成订单.").toString());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			int timeout = TIME_OUT;
			synchronized (orderIdList) {
				orderIdList.add(id);
				orderMessageMap.put(id, new OrderMessage(timeout));
			}
			map.put("timeOut", timeout);
			log.info(new StringBuilder("[OrderServiceImpl:makeOrder]").append("生成订单完成.").toString());
		} catch (Exception e) {
			log.error("[OrderServiceImpl:makeOrder]生成订单时出现其他错误!",e);
			throw new Exception("生成订单错误");
		}
		return map;
	}
	
	@Override
	public void paySuccess(Long id) {
		synchronized (savedOrderList) {
			if (savedOrderList.contains(id)) {
				// 有一个相同的订单正在进行处理，则直接返回
				return;
			}
			savedOrderList.add(id);
		}
		log.info(new StringBuilder("[OrderServiceImpl:paySuccess]").append("完成订单成功.").toString());
		BaseOrder baseOrder = baseOrderManager.queryById(id);
		try {
			if (baseOrder.getStatus() != BaseOrder.BASE_ORDER_STATUS_INIT) {
				return;
			}
			baseOrder.setStatus(BaseOrder.BASE_ORDER_STATUS_UNLOCK);
			baseOrderManager.updateById(baseOrder);
			clearCacheOrder(baseOrder.getId());
			log.info(new StringBuilder("[OrderServiceImpl:paySuccess]{id:" + id + ",type:" + baseOrder.getType() + "}").append("订单支付完成.").toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(new StringBuilder("[OrderServiceImpl:paySuccess]{id:" + id + ",type:" + baseOrder.getType() + "}").append("订单支付完成处理失败.").toString(), e);
		} finally {
			// 删除保存的订单号
			synchronized (savedOrderList) {
				savedOrderList.remove(id);
			}
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		List<Long> ids = baseOrderManager.queryOrderUnPay();
		if (null != ids) {
			System.out.println("初始化缓存订单:");
			for (Long id : ids) {
				int timeout = TIME_OUT;

				synchronized (orderIdList) {
					orderIdList.add(id);
					orderMessageMap.put(id, new OrderMessage(timeout));
				}
			}
			System.out.println("初始化完成");
		}
		
	}

	@Override
	public void cleanOrder(Long id) {
		log.info(new StringBuilder("[OrderServiceImpl:cleanOrder]{id:" + id + "}").append("主动取消订单.").toString());
		try {
			BaseOrder baseOrder = baseOrderManager.queryById(id);
			if (baseOrder.getStatus() == BaseOrder.BASE_ORDER_STATUS_INIT) {
				if(baseOrder.getType() == BaseOrder.BASE_ORDER_TYPE_ORDER) {//商城订单
					orderInfoManager.orderCancel(baseOrder.getRefId());
				}else if(baseOrder.getType() == BaseOrder.BASE_ORDER_TYPE_ACTIVITY) {//活动订单
					activityManager.orderCancel(baseOrder.getRefId());
				}else if(baseOrder.getType() == BaseOrder.BASE_ORDER_TYPE_RENT) {//租地
					farmRentLandOrderManager.orderCancel(baseOrder.getRefId());
				}else if(baseOrder.getType() == BaseOrder.BASE_ORDER_TYPE_FARMBAZAAR) {//自由贸易
					FarmMemberCropsManager.orderCancel(baseOrder.getRefId());
				} else if (baseOrder.getType() == BaseOrder.BASE_ORDER_TYPE_CROW) { //众筹订单
					crowdFundingOrderManager.payTimeOut(baseOrder.getId());
				}
				
				baseOrder.setStatus(BaseOrder.BASE_ORDER_STATUS_UNLOCK);
				baseOrderManager.updateById(baseOrder);
			}
			clearCacheOrder(id);
			log.info(new StringBuilder("[OrderServiceImpl:cleanOrder]{id:" + id + "}").append("主动取消处理完成.").toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(new StringBuilder("[OrderServiceImpl:cleanOrder]{id:" + id + "}").append("主动取消处理失败.").toString(), e);
		}
	}
	
	
	
	//
	// 内部类
	//
	class OrderMessage {
		/** 记录时的时间戳 */
		private long timestamp;
		/** 订单的超时时间 */
		private int timeout;

		public OrderMessage(int timeout) {
			this.timeout = timeout * 60 * 1000; // 设置的超时时间以分钟为单位
			this.timestamp = System.currentTimeMillis();
		}

		/** 检查是否该订单已经超时 */
		public boolean checkTimeout() {
			return System.currentTimeMillis() - timestamp >= timeout;
		}

		/** 取得订单超时的剩余时间 */
		public long getTimeoutRemain() {
			if (checkTimeout()) {
				return 0;
			} else {
				return (timeout - (System.currentTimeMillis() - timestamp)) / 1000;
			}
		}

		public String toString() {
			return "t:" + timeout / 1000 + ",r:" + getTimeoutRemain();
		}
	}
	
	//
	// 私有方法
	//
	private boolean clearCacheOrder(Long orderId) {
		synchronized (orderIdList) {
			int index = orderIdList.indexOf(orderId);
			if (index == -1) {
				log.warn(new StringBuilder("[result][orderId:").append(orderId).append("] not in order cache").toString());
				return false;
			}

			orderIdList.remove(index);
			orderMessageMap.remove(orderId);
		}
		return true;
	}
	
	
	class CheckOrderThread extends Thread {
		public void run() {
			while (true) {
				try {
					synchronized (this) {
						wait(5000); // 5秒钟激活一次
					}
				} catch (Exception ex) {
				}

				synchronized (orderIdList) {
					for (Iterator<Long> iter = orderIdList.iterator(); iter.hasNext();) {
						Long orderId = iter.next();
						OrderMessage orderMessage = orderMessageMap.get(orderId);
						if (orderMessage.checkTimeout()) {

							StringBuilder slog = new StringBuilder();
							slog.append("[checkTimeout][orderId:").append(orderId).append("]");

							// 修改订单为超时的状态
							try {
								BaseOrder baseOrder = baseOrderManager.queryById(orderId);
								//对订单进行回滚操作
								//释放库存
								if (baseOrder.getStatus() == BaseOrder.BASE_ORDER_STATUS_INIT) {//商城订单
									if(baseOrder.getType() == BaseOrder.BASE_ORDER_TYPE_ORDER) {
										orderInfoManager.orderOutTime(baseOrder.getRefId());
									}else if(baseOrder.getType() == BaseOrder.BASE_ORDER_TYPE_ACTIVITY) {//活动订单
										activityManager.orderOutTime(baseOrder.getRefId());
									}else if(baseOrder.getType() == BaseOrder.BASE_ORDER_TYPE_RENT) {//租地
										farmRentLandOrderManager.orderOutTime(baseOrder.getRefId());
									}else if(baseOrder.getType() == BaseOrder.BASE_ORDER_TYPE_FARMBAZAAR) {//自由贸易
										FarmMemberCropsManager.orderOutTime(baseOrder.getRefId());
									}else if(baseOrder.getType() == BaseOrder.BASE_ORDER_TYPE_CROW){//众筹订单
										crowdFundingOrderManager.payTimeOut(baseOrder.getId());
									}
									
									
									baseOrder.setStatus(BaseOrder.BASE_ORDER_STATUS_UNLOCK);
									baseOrderManager.updateById(baseOrder);
								}

								iter.remove();
								orderMessageMap.remove(orderId);
								log.info(slog.append("订单超时清除!").toString());
							} catch (Exception e) {
								log.error(slog.append("订单超时处理异常!").toString(), e);
							}
						}
					}
				}
			}
		}
	}


}
