package com.smartfarm.base.shop.core.manager.impl;

import com.smartfarm.base.shop.core.dao.*;
import com.smartfarm.base.shop.core.entity.MemberOrderCommission;
import com.smartfarm.base.shop.core.entity.ProductSku;
import com.smartfarm.base.shop.core.entity.ProductSkuCommission;
import com.smartfarm.base.shop.core.entity.vo.MemberOrderCommissionVo;
import com.smartfarm.base.shop.core.entity.vo.ProductSkuCommissionVo;
import com.smartfarm.base.shop.core.entity.vo.ProductSkuVo;
import com.smartfarm.base.shop.core.manager.ProductSkuCommissionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("productSkuCommissionManager")
public class ProductSkuCommissionManagerImpl implements ProductSkuCommissionManager {
    @Resource
    private ProductSkuCommissionDao productSkuCommissionDao;
    @Resource
    private ProductSkuDao productSkuDao;
    @Resource
    private MemberOrderCommissionDao memberOrderCommissionDao;
    @Resource
    private OrderInfoDao orderInfoDao;
    @Resource
    private MemberInfoDao memberInfoDao;

    @Override
    public List<MemberOrderCommissionVo> getMemberOrderCommissions(Long memberId) {
        List<MemberOrderCommissionVo> commissionVos=memberOrderCommissionDao.selectMemberOrderCommissions(memberId);
        if(null!=commissionVos){
            for(MemberOrderCommissionVo memberOrderCommissionVo:commissionVos){
                memberOrderCommissionVo.setOrderInfo(orderInfoDao.queryById(memberOrderCommissionVo.getOrderId()));
                memberOrderCommissionVo.setOrderMember(memberInfoDao.queryById(memberOrderCommissionVo.getOrderMemberId()));
            }
        }
        return commissionVos;
    }

    @Override
    public List<ProductSkuVo> getSkuCommissionList(Long farmId, String name, Integer start, Integer limit) {
        List<ProductSkuVo> list=productSkuDao.selectSkuCommissionList(farmId,name,start,limit);
        if(null!=list){
            for(ProductSkuVo productSkuVo:list){
                Long skuId=productSkuVo.getId();
                ProductSkuCommission productSkuCommission=productSkuCommissionDao.selectSkuCommissionBySkuId(skuId);
                if(productSkuCommission==null){
                    ProductSkuCommission productSkuCommissionAdd=new ProductSkuCommission();
                    productSkuCommissionAdd.setProductSkuId(skuId);
                    Double commission=0.00;
                    productSkuCommissionAdd.setCommission(commission);
                    productSkuCommissionDao.insertSelective(productSkuCommissionAdd);
                    productSkuVo.setProductSkuCommission(productSkuCommissionDao.selectSkuCommissionBySkuId(skuId));
                }
                else {
                    productSkuVo.setProductSkuCommission(productSkuCommission);
                }
            }
        }
        return list;
    }

    @Override
    public int getSkuCommissionTotal(Long farmId, String name) {
        return productSkuDao.selectSkuCommissionTotal(farmId,name);
    }

    public List<ProductSkuCommissionVo> getPageList(String name, Integer start, Integer limit) {
        return productSkuCommissionDao.selectPageList(name,start,limit);
    }

    public int getPageTotal(String name) {
        return productSkuCommissionDao.selectPageTotal(name);
    }

    public int editProductSkuCommission(ProductSkuCommission productSkuCommission) {
        return productSkuCommissionDao.updateByPrimaryKeySelective(productSkuCommission);
    }


}
