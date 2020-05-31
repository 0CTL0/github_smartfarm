package com.smartfarm.base.shop.core.dao;

import com.smartfarm.base.shop.core.entity.CouponBatchSend;
import com.smartfarm.base.shop.core.entity.vo.CouponBatchSendVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponBatchSendDao {
    int insert(CouponBatchSend record);

    int updateById(CouponBatchSend record);

    /**
     * 查询可用
     * @param businessId
     * @param nowTime
     * @return
     */
    public List<CouponBatchSendVo> queryUseableSend(@Param("businessId") Long businessId, @Param("nowTime") String nowTime);

    /**
     * 库存减一
     * @param id
     * @return
     */
    public int updateRemainNumSend(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public CouponBatchSend querySendById(Long id);

    /**
     * 分页查询
     * @param businessId
     * @return
     */
    public List<CouponBatchSendVo> queryCouponSendPage(@Param("businessId") Long businessId, @Param("start") Integer start,
                                                       @Param("limit") Integer limit);

    /**
     * 分页查询
     * @param businessId
     * @return
     */
    public int countCouponSendPage(Long businessId);

    /**
     * 根据批次id查询
     * @param id
     * @return
     */
    public CouponBatchSend querySendByBatchId(Long id);
}