package o2o.service;

import java.util.List;

import o2o.entity.ShopCategory;

public interface ShopCategoryService {
	/**
	 * 
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
	
}
