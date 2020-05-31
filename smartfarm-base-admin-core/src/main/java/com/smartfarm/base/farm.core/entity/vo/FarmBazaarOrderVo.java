package com.smartfarm.base.farm.core.entity.vo;

import com.smartfarm.base.farm.core.entity.FarmBazaarOrder;
import com.smartfarm.base.farm.core.entity.FarmBazaarProduct;
import com.smartfarm.base.farm.core.entity.FarmDelegateExpress;

public class FarmBazaarOrderVo extends FarmBazaarOrder {
    private String cover;
    private String name;
    private Double price;

    private FarmBazaarProduct farmBazaarProduct;
    private FarmDelegateExpress farmDelegateExpress;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public FarmBazaarProduct getFarmBazaarProduct() {
        return farmBazaarProduct;
    }

    public void setFarmBazaarProduct(FarmBazaarProduct farmBazaarProduct) {
        this.farmBazaarProduct = farmBazaarProduct;
    }

    public FarmDelegateExpress getFarmDelegateExpress() {
        return farmDelegateExpress;
    }

    public void setFarmDelegateExpress(FarmDelegateExpress farmDelegateExpress) {
        this.farmDelegateExpress = farmDelegateExpress;
    }
}
