package o2o.service;

import java.util.List;

import o2o.dto.ProductCategoryExecution;
import o2o.entity.ProductCategory;
import o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {
	/**
	 * 查询置顶某个店铺下的所有商品类别信息
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategoryList(long shopId);

	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
	throws ProductCategoryOperationException;
	
	/**
	 * 将此类别瞎的商品里的类别id置为空，再删除掉该商品类别
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)
	throws ProductCategoryOperationException;
}
