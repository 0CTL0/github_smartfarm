package com.smartfarm.base.shop.core.manager;

import com.smartfarm.base.shop.core.entity.ProductSku;
import com.smartfarm.base.shop.core.entity.ProductSkuCommission;
import com.smartfarm.base.shop.core.entity.vo.MemberOrderCommissionVo;
import com.smartfarm.base.shop.core.entity.vo.ProductSkuCommissionVo;
import com.smartfarm.base.shop.core.entity.vo.ProductSkuVo;

import java.util.List;


public interface ProductSkuCommissionManager {

    /**
     * 查询用户的全部分销订单
     * @param memberId
     * @return
     */
    List<MemberOrderCommissionVo> getMemberOrderCommissions(Long memberId);

    /**
     * 查询sku佣金列表
     * @param farmId
     * @param name
     * @param start
     * @param limit
     * @return
     */
    List<ProductSkuVo> getSkuCommissionList(Long farmId, String name, Integer start, Integer limit);

    /**
     * 统计sku佣金列表
     * @param farmId
     * @param name
     * @return
     */
    int getSkuCommissionTotal(Long farmId, String name);
    List<ProductSkuCommissionVo> getPageList(String name, Integer start, Integer limit);
    int getPageTotal(String name);
    int editProductSkuCommission(ProductSkuCommission productSkuCommission);
}
