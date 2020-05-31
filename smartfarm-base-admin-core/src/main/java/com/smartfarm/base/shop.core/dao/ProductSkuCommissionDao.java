package com.smartfarm.base.shop.core.dao;

import com.smartfarm.base.shop.core.entity.ProductSkuCommission;
import com.smartfarm.base.shop.core.entity.vo.ProductSkuCommissionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSkuCommissionDao {
    int deleteByPrimaryKey(Long id);


    int insertSelective(ProductSkuCommission record);

    ProductSkuCommission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSkuCommission record);

    List<ProductSkuCommissionVo> selectPageList(@Param("name") String name, @Param("start") Integer start,
                                                @Param("limit") Integer limit);

    int selectPageTotal(@Param("name") String name);

    ProductSkuCommission selectSkuCommissionBySkuId(@Param("skuId") Long skuId);
}