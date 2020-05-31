package com.smartfarm.base.shop.core.pattern;

import com.smartfarm.base.shop.core.entity.OrderInfo;


public class CommissionContext {
    private CommissionSuper commissionSuper;

    public CommissionContext(CommissionSuper commissionSuper){
        this.commissionSuper=commissionSuper;
    }
    public Double getCommission(OrderInfo orderInfo){
        return commissionSuper.getCommission(orderInfo);
    }
}
