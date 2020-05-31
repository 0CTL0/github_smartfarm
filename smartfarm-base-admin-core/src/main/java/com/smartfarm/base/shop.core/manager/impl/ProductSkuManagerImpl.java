package com.smartfarm.base.shop.core.manager.impl;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.shop.core.dao.ProductSkuAttributeDao;
import com.smartfarm.base.shop.core.dao.ProductSkuDao;
import com.smartfarm.base.shop.core.dao.ProductSkuImgDao;
import com.smartfarm.base.shop.core.entity.ProductSku;
import com.smartfarm.base.shop.core.entity.vo.ProductSkuVo;
import com.smartfarm.base.shop.core.manager.ProductSkuManager;

@Service("productSkuManager")
public class ProductSkuManagerImpl implements ProductSkuManager {
	@Resource
	private ProductSkuDao productSkuDao;
	@Resource
	private ProductSkuImgDao productSkuImgDao;
	@Resource
	private ProductSkuAttributeDao productSkuAttributeDao;

	@Override
	public Long insert(ProductSku productSku) {	
		String createTime=DateUtil.formatCurrentDateTime();
        if(productSku.getPrice()!=null){
        	 DecimalFormat df = new DecimalFormat("#.00");
             double price=Double.parseDouble(df.format(productSku.getPrice()));
             productSku.setPrice(price);
        }      
        productSku.setCreateTime(createTime);
        productSku.setStatus(ProductSku.TYPE_INTEGRAL);
        productSkuDao.insert(productSku);
		return productSku.getId();
	}

	@Override
	public int updateProductSku(ProductSku productSku) {	
		return productSkuDao.updateById(productSku);
	}

	@Override
	public List<ProductSkuVo> selectAllProductSku(Long Id, Integer start,
			Integer limit) {
		return productSkuDao.selectSkuList(Id, start, limit);
	}

	@Override
	public int countProductSkuList(Long id) {
		return productSkuDao.countSkuList(id);
	}

	@Override
	public  ProductSku selectById(Long id) {
		return productSkuDao.selectById(id);
	}

	
	@Override
	public int changeStatus(Long id) {	
		ProductSku productSku=productSkuDao.selectById(id);
		short status=productSku.getStatus();	
		if (status==ProductSku.STATUS_ENABLE) {		
			status=ProductSku.STATUS_DISABLE;
		}else {
			status=ProductSku.STATUS_ENABLE;			
		}
		productSku.setStatus(status);
		return productSkuDao.updateById(productSku);
	}

	@Override
	public List<ProductSkuVo> querySkuListByProductId(Long productId) {
		List<ProductSkuVo> list = productSkuDao.querySkuByProductId(productId);
		for(ProductSkuVo vo:list) {
			//product_sku_img改为关联到productinfo，因此查询sku的img改为用productId来查询
			vo.setImgList(productSkuImgDao.queryByProductId(productId));
			vo.setAttributeList(productSkuAttributeDao.queryByProductSkuId(vo.getId()));
		}
		return list;
	}

	@Override
	public List<ProductSkuVo> querySkuByProductIdAndType(Long productId,
			Short type) {
		List<ProductSkuVo> list = productSkuDao.querySkuByProductIdAndType(productId, type);
		for(ProductSkuVo vo:list) {
			vo.setImgList(productSkuImgDao.queryByProductId(productId));
			vo.setAttributeList(productSkuAttributeDao.queryByProductSkuId(vo.getId()));
		}
		return list;
	}

	@Override
	public List<ProductSku> querySkuDetailsById(Long id) {
		return productSkuDao.querySkuDetailsById(id);
	}

	@Override
	public List<ProductSkuVo> queryProductSkuById(Long id) {
		return productSkuDao.queryProductSkuById(id);
	}

	@Override
	public Long addProductSku(ProductSku productSku) {
		String createTime=DateUtil.formatCurrentDateTime();
		productSku.setCreateTime(createTime);
		productSku.setStatus(ProductSku.STATUS_DISABLE);
		productSkuDao.insert(productSku);
		return null;
	}
		

	@Override
	public int editProductSku(ProductSku productSku) {
		 productSkuDao.updateById(productSku);	 
		return 0;
	}

	@Override
	public List<ProductSku> queryAllProductSku() {
		return productSkuDao.queryAllProductSku();
	}

}
