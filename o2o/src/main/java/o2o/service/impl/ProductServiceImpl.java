/**
 * 
 */
package o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import o2o.dao.ProductDao;
import o2o.dao.ProductImgDao;
import o2o.dto.ImageHolder;
import o2o.dto.ProductExecution;
import o2o.entity.Product;
import o2o.entity.ProductImg;
import o2o.enums.ProductStateEnum;
import o2o.exceptions.ProductOperationException;
import o2o.service.ProductService;
import o2o.util.ImageUtil;
import o2o.util.PageCalculator;
import o2o.util.PathUtil;

/**
 * @author lw243
 *
 */
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductImgDao productImgDao;


	@Override
	public ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		int count = productDao.queryProductCount(productCondition);
		ProductExecution pe = new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}
	
	@Override
	@Transactional
	//1.处理缩略图，获取缩略图相对路径并赋值给product
	//2.往tb_product写入商品信息，获取productId
	//3.结合productId批量处理商品详情图
	//4.将商品详情图列表批量插入tb_product_img中
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationException {
		//空值判断
		if(product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			//给店铺信息赋初始值	
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			product.setEnableStatus(0);
			if (thumbnail != null) {
				addThumbnail(product,thumbnail);
			}
			try {
				//创建商品信息，并自动让product获得id
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品失败");
				}
			}catch(Exception e) {
				throw new ProductOperationException("创建商品失败" + e.toString());
			}
			//若商品详情图不为空则添加
			if (productImgHolderList != null && productImgHolderList.size()>0) {
				addProductImgList(product,productImgHolderList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS,product);
		}else {
			//传参为空就返回错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
	
	/**
	 * 添加缩略图
	 */
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}
	/**
	 * 批量添加图片
	 * @param product
	 * @param productImgHolderList
	 */
	private void addProductImgList(Product product,List<ImageHolder> productImgHolderList) {
		//获取图片存储路径，这里直接存放到相应的店铺文件夹地下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		//遍历图片一次取处理，并添加进productImg实体类里
		for (ImageHolder productImgHolder:productImgHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(productImgHolder,dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		//如果确实是有图片需要添加的，就执行批量添加操作
		if (productImgList.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum < 0) {
					throw new ProductOperationException("创建商品详情图片失败");
				}
			}catch(Exception e) {
				throw new ProductOperationException("创建商品详情图片失败" + e.toString());
			}
		}
	}

	@Override
	public Product getProductById(long productId) {
		return productDao.queryProductById(productId);
	}

	@Override
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productImgHolderList) throws ProductOperationException {
		if (product!=null && product.getShop()!=null && product.getShop().getShopId()!=null) {
			product.setLastEditTime(new Date());
			if (thumbnail!=null) {
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getImgAddr()!=null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product,thumbnail);
				
			}
			if(productImgHolderList!=null && productImgHolderList.size()>0) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product,productImgHolderList);
			}
			try {
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS,product);
			}catch(Exception e) {
				throw new ProductOperationException("更新商品信息失败："+ e.toString());
			}
		}else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}
	
	private void deleteProductImgList(long productId) {
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		for(ProductImg productImg:productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		productImgDao.deleteProductImgByProductId(productId);
	}
	

	

}

