package o2o.service;

import java.io.InputStream;

import o2o.dto.ImageHolder;
import o2o.dto.ShopExecution;
import o2o.entity.Shop;
import o2o.exceptions.ShopOperationException;

public interface ShopService {
	/**
	 * 根据shopCondition分页返回相应列表
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	/**
	 * 
	 * @param shopId
	 * @return
	 */
	
	Shop getByShopId(long shopId);
	
	/**
	 * 更新店铺信息
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
	
	
	ShopExecution addShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;
	
}
