package o2o.service;

import java.util.List;

import o2o.dto.ImageHolder;
import o2o.dto.ProductExecution;
import o2o.entity.Product;
import o2o.exceptions.ProductOperationException;

public interface ProductService {

	ProductExecution addProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImageList) 
			throws ProductOperationException;
	
	ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);
	
	Product getProductById(long productId);
	
	ProductExecution modifyProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImgHolderList)
			throws ProductOperationException;

}
