package com.smartfarm.base.shop.core.pattern;

import com.smartfarm.base.util.NumberUtil;
import com.smartfarm.base.farm.core.dao.SettingInfoDao;
import com.smartfarm.base.farm.core.entity.SettingInfo;
import com.smartfarm.base.shop.core.entity.OrderInfo;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service("commissionOrder")
public class CommissionOrder extends CommissionSuper{
    @Resource
    private SettingInfoDao settingInfoDao;



    @Override
    public Double getCommission(OrderInfo orderInfo) {
       SettingInfo settingInfo=settingInfoDao.getInfoByType(orderInfo.getBusinessId(), "commission_order_proportion");
        Double commissionAmount = NumberUtil.mul(orderInfo.getTotalPrice(),Double.parseDouble(settingInfo.getParamValue()));
        return commissionAmount;
    }
}
