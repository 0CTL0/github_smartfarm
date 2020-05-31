package com.smartfarm.base.shop.core.pattern;

import com.smartfarm.base.util.NumberUtil;
import com.smartfarm.base.shop.core.dao.OrderProductDao;
import com.smartfarm.base.shop.core.dao.ProductSkuCommissionDao;
import com.smartfarm.base.shop.core.entity.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("commissionSku")
public class CommissionSku extends CommissionSuper {
    @Resource
    private OrderProductDao orderProductDao;
    @Resource
    private ProductSkuCommissionDao productSkuCommissionDao;

    @Override
    public Double getCommission(OrderInfo orderInfo) {
        List<OrderProduct> orderProductList = orderProductDao.queryByOrderId(orderInfo.getId());
        Double commissionAmount = 0.00;
        for (OrderProduct orderProduct : orderProductList) {
            ProductSkuCommission SkuCommission = productSkuCommissionDao.selectSkuCommissionBySkuId(orderProduct.getProductSkuId());
            //sku没有设置分销金额，则不计算该sku佣金
            if (SkuCommission != null) {
                commissionAmount = NumberUtil.add(commissionAmount, SkuCommission.getCommission());
            }
        }
        return commissionAmount;
        }

    }
