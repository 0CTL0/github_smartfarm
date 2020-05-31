package com.smartfarm.base.shop.core.pattern;

import com.smartfarm.base.shop.core.entity.OrderInfo;

public abstract class CommissionSuper {
    public abstract Double getCommission(OrderInfo orderInfo);
}
