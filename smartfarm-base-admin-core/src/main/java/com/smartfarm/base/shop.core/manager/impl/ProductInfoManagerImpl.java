package com.smartfarm.base.shop.core.manager.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.util.DateUtil;
import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.shop.core.dao.ProductInfoDao;
import com.smartfarm.base.shop.core.dao.ProductSkuDao;
import com.smartfarm.base.shop.core.dao.ProductSkuImgDao;
import com.smartfarm.base.shop.core.entity.ProductInfo;
import com.smartfarm.base.shop.core.entity.ProductSkuImg;
import com.smartfarm.base.shop.core.entity.vo.ProductInfoVo;
import com.smartfarm.base.shop.core.manager.ProductInfoManager;

@Service("productInfoManager")
public class ProductInfoManagerImpl implements ProductInfoManager {
	@Resource
	private ProductInfoDao productInfoDao;
	@Resource
	private ProductSkuDao productSkuDao;
	@Resource
	private ProductSkuImgDao productSkuImgDao;
	
	@Override
	public List<ProductInfoVo> selectAllProductInfo(String name,Long categoryId, Integer start,
			Integer limit,Long businessId) {	
		return productInfoDao.selectProductInfoList(name,categoryId, start, limit,businessId);
	}

	@Override
	public int countProductInfoList(String name,Long categoryId, Long businessId) {
		return productInfoDao.countProductInfoList(name,categoryId, businessId);
	}

	
	@Override
	public int insert(ProductInfo productInfo, MultipartFile file,MultipartFile file2,MultipartFile[] mainImgs,
			MultipartFile[] detailImgs)throws IOException {
		
		String path = UploadUtil.uploadFile(file, "/file/product/",  RandomUtil.generateNumber(10));
		String path2 = UploadUtil.uploadFile(file2, "/file/product/",RandomUtil.generateNumber(10));
		String createTime=DateUtil.formatCurrentDateTime();
       // DecimalFormat df = new DecimalFormat("#.00");
       // double price=Double.parseDouble(df.format(productInfo.getShowPrice()));
       // productInfo.setShowPrice(price);
        Short status=ProductInfo.STATUS_DISABLE;
        productInfo.setStatus(status);
		productInfo.setPicUrl(path);
		productInfo.setShareUrl(path2);
		productInfo.setCreateTime(createTime);
		productInfoDao.insert(productInfo);
		
		for(MultipartFile mainImg :mainImgs){		
			String mainImgPath = UploadUtil.uploadFile(mainImg, "/file/product/",RandomUtil.generateNumber(100));					
			ProductSkuImg productSkuImg=new ProductSkuImg();
			productSkuImg.setType(ProductSkuImg.MAINIMG);			
			productSkuImg.setProductId(productInfo.getId());			
			productSkuImg.setCreateTime(createTime);			
			productSkuImg.setPicUrl(mainImgPath);				
			productSkuImgDao.insert(productSkuImg);		
		}
		
		for(MultipartFile detailImg :detailImgs){				
			String detailImgPath = UploadUtil.uploadFile(detailImg, "/file/product/",RandomUtil.generateNumber(100));					
			ProductSkuImg productSkuImg=new ProductSkuImg();
			productSkuImg.setType(ProductSkuImg.DETAILIMG);
			productSkuImg.setProductId(productInfo.getId());
			productSkuImg.setCreateTime(createTime);
			productSkuImg.setPicUrl(detailImgPath);
			productSkuImgDao.insert(productSkuImg);
		}
		
		return 0;
	}

	@Override
	public int delectProductInfo(Long id) {		
		return productInfoDao.delete(id);
	}

	@Override
	public ProductInfo selectById(Long id) {
		return productInfoDao.selectById(id);
	}

	@Override
	public int updateProductInfo(ProductInfo productInfo, MultipartFile file,MultipartFile file2,
			MultipartFile[] mainImgs,MultipartFile[] detailImgs) throws IOException {
		if(file!=null){
			String path = UploadUtil.uploadFile(file, "/file/product/", RandomUtil.generateNumber(10));
			productInfo.setPicUrl(path);
		}
		if(file2!=null){
			String path2 = UploadUtil.uploadFile(file2, "/file/product/",RandomUtil.generateNumber(10));
			productInfo.setShareUrl(path2);			
		}	
		productInfoDao.updateById(productInfo);
		 if(mainImgs.length>0){
			 productSkuImgDao.delectByProductIdAndType(productInfo.getId(), ProductSkuImg.MAINIMG);
			 for(MultipartFile mainImg :mainImgs){					 					 	
					String path = UploadUtil.uploadFile(mainImg, "/file/product/",RandomUtil.generateNumber(100));
					String createTime=DateUtil.formatCurrentDateTime();		
					ProductSkuImg productSkuImg=new ProductSkuImg();
					productSkuImg.setType(ProductSkuImg.MAINIMG);
					productSkuImg.setProductId(productInfo.getId());
					productSkuImg.setCreateTime(createTime);
					productSkuImg.setPicUrl(path);
					productSkuImgDao.insert(productSkuImg);
				}
		 }
		 if(detailImgs.length>0){
			 productSkuImgDao.delectByProductIdAndType(productInfo.getId(), ProductSkuImg.DETAILIMG);
			 for(MultipartFile detailImg :detailImgs){					 					 	
					String path = UploadUtil.uploadFile(detailImg, "/file/product/",RandomUtil.generateNumber(100));
					String createTime=DateUtil.formatCurrentDateTime();		
					ProductSkuImg productSkuImg=new ProductSkuImg();
					productSkuImg.setType(ProductSkuImg.DETAILIMG);
					productSkuImg.setProductId(productInfo.getId());
					productSkuImg.setCreateTime(createTime);
					productSkuImg.setPicUrl(path);
					productSkuImgDao.insert(productSkuImg);
				}
		 }	
		return 0;
	}

	@Override
	public int changeNew(Long id) {
		ProductInfo productInfo = productInfoDao.selectById(id);
		boolean n=productInfo.getIsNew();
		
		if (n) {
			productInfo.setIsNew(false);
		}else {
			productInfo.setIsNew(true);
		}
		return productInfoDao.updateById(productInfo);
	}

	@Override
	public int changeHot(Long id) {
		ProductInfo productInfo = productInfoDao.selectById(id);
		boolean h=productInfo.getIsHot();
		
		if (h) {
			productInfo.setIsHot(false);
		}else {
			productInfo.setIsHot(true);
		}
		return productInfoDao.updateById(productInfo);
	}

	@Override
	public int changeStatus(Long id) {
		ProductInfo productInfo=productInfoDao.selectById(id);
		short status=productInfo.getStatus();	
		if (status==ProductInfo.STATUS_ENABLE) {		
			status=ProductInfo.STATUS_DISABLE;
		}else {
			status=ProductInfo.STATUS_ENABLE;
		}
		productInfo.setStatus(status);

		return productInfoDao.updateById(productInfo);
	}

	@Override
	public Object queryAllProductBusinessList() {
		return productInfoDao.queryAllProductBusinessList();
	}

	@Override
	public List<ProductInfoVo> queryNewProductList(Long businessId) {
		List<ProductInfoVo> list = productInfoDao.queryNewProductList(businessId);
		for(ProductInfoVo productInfoVo:list) {
			productInfoVo.setProductSkuList(productSkuDao.querySkuByProductId(productInfoVo.getId()));
		}
		return list;
	}

	@Override
	public List<ProductInfoVo> queryHotProductList(Long businessId) {
		List<ProductInfoVo> list = productInfoDao.queryHotProductList(businessId);
		for(ProductInfoVo productInfoVo:list) {
			productInfoVo.setProductSkuList(productSkuDao.querySkuByProductId(productInfoVo.getId()));
		}
		return list;
	}

	@Override
	public List<ProductInfoVo> queryProductListByCategoryId(Long categoryId) {
		List<ProductInfoVo> list = productInfoDao.queryProductListByCategoryId(categoryId);
		for(ProductInfoVo productInfoVo:list) {
			productInfoVo.setProductSkuList(productSkuDao.querySkuByProductId(productInfoVo.getId()));
		}
		return list;
	}

	@Override
	public List<ProductInfoVo> queryNewProductList(Short type,Long businessId) {
		List<ProductInfoVo> list = productInfoDao.queryNewProductListByType(type,businessId);
		for(ProductInfoVo productInfoVo:list) {
			productInfoVo.setProductSkuList(productSkuDao.querySkuByProductId(productInfoVo.getId()));
		}
		return list;
	}

	@Override
	public List<ProductInfoVo> queryHotProductList(Short type,Long businessId) {
		List<ProductInfoVo> list = productInfoDao.queryHotProductListByType(type,businessId);
		for(ProductInfoVo productInfoVo:list) {
			productInfoVo.setProductSkuList(productSkuDao.querySkuByProductId(productInfoVo.getId()));
		}
		return list;
	}

	@Override
	public List<ProductInfoVo> queryIntegralProductList(Long businessId) {
		List<ProductInfoVo> list = productInfoDao.queryIntegralProductList(businessId);
		for(ProductInfoVo productInfoVo:list) {
			productInfoVo.setProductSkuList(productSkuDao.querySkuByProductId(productInfoVo.getId()));
		}
		return list;
	}
	
	@Override
	public List<ProductInfoVo> queryProductListByCategoryId(Long categoryId,
			Short type) {
		List<ProductInfoVo> list = productInfoDao.queryProductListByCategory(categoryId, type);
		for(ProductInfoVo productInfoVo:list) {
			productInfoVo.setProductSkuList(productSkuDao.querySkuByProductId(productInfoVo.getId()));
		}
		return list;
	}

	@Override
	public List<ProductInfoVo> queryIndexProduct(Long farmId) {
		List<ProductInfoVo> list = productInfoDao.queryProductForAdvance(farmId);
		for(ProductInfoVo productInfoVo:list) {
			productInfoVo.setProductSkuList(productSkuDao.querySkuByProductId(productInfoVo.getId()));
		}
		return list;
	}

	@Override
	public List<ProductInfoVo> queryProductForAdvanceAdd(Long businessId) {
		List<ProductInfoVo> list = productInfoDao.queryProductForAdvanceAdd(businessId);
		for(ProductInfoVo productInfoVo:list) {
			productInfoVo.setProductSkuList(productSkuDao.querySkuByProductId(productInfoVo.getId()));
		}
		return list;
	}

	@Override
	public ProductInfoVo selectVoById(Long id) {
		ProductInfoVo productInfoVo=productInfoDao.selectVoByProductId(id);
		productInfoVo.setMainPicList(productSkuImgDao.queryImgList(id,(short)1));
		productInfoVo.setDetailPicList(productSkuImgDao.queryImgList(id,(short)2));
		return productInfoVo;
	}
}


	
	
