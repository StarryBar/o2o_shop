package o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import o2o.entity.Product;

public interface ProductDao {
	/**
	 * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id，商品类别
	 */
	List<Product> queryProductList(@Param("productCondition")Product productCondition,@Param("rowIndex")int rowIndex,
			@Param("pageSize")int pageSize);
	/**
	 * 查询对应的商品总数
	 * @param product
	 * @return
	 */
	int queryProductCount(@Param("productCondition")Product productCondition);
	
	int insertProduct(Product product);
	
	Product queryProductById(long productId);
	
	int updateProduct(Product product);
	
	int updateProductCategoryToNull(long productCategoryId);
	
}
