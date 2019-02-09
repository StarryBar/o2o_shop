package dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTest;
import o2o.dao.ProductDao;
import o2o.dao.ProductImgDao;
import o2o.entity.Product;
import o2o.entity.ProductCategory;
import o2o.entity.ProductImg;
import o2o.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;
	
	@Test
	@Ignore
	public void testAInsertShop() throws Exception {
		Shop shop1 = new Shop();
		shop1.setShopId(13L);
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryId(1L);
		Product product1 = new Product();
		product1.setProductName("测试1");
		product1.setProductDesc("测试Desc1");
		product1.setImgAddr("test1");
		product1.setPriority(1);
		product1.setEnableStatus(1);
		product1.setCreateTime(new Date());
		product1.setLastEditTime(new Date());
		product1.setShop(shop1);
		product1.setProductCategory(pc1);
		
		Product product2 = new Product();
		product2.setProductName("测试2");
		product2.setProductDesc("测试Desc2");
		product2.setImgAddr("test2");
		product2.setPriority(2);
		product2.setEnableStatus(0);
		product2.setCreateTime(new Date());
		product2.setLastEditTime(new Date());
		product2.setShop(shop1);
		product2.setProductCategory(pc1);
		
		Product product3 = new Product();
		product3.setProductName("测试3");
		product3.setProductDesc("测试Desc3");
		product3.setImgAddr("test3");
		product3.setPriority(3);
		product3.setEnableStatus(1);
		product3.setCreateTime(new Date());
		product3.setLastEditTime(new Date());
		product3.setShop(shop1);
		product3.setProductCategory(pc1);
		
		int effectedNum = productDao.insertProduct(product1);
		assertEquals(1,effectedNum);
		effectedNum = productDao.insertProduct(product2);
		assertEquals(1,effectedNum);
		effectedNum = productDao.insertProduct(product3);
		assertEquals(1,effectedNum);
	}
	@Test
	@Ignore
	public void testBQueryProductList() throws Exception{
		Product productCondition = new Product();
		List<Product> productList = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(3,productList.size());
		int count = productDao.queryProductCount(productCondition);
		assertEquals(5,count);
		productCondition.setProductName("测试");
		productList = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(3,productList.size());
		count = productDao.queryProductCount(productCondition);
		assertEquals(4,count);
	}
	
	@Test
	@Ignore
	public void testCQueryProductByProductId() throws Exception{
		long productId = 12;
		ProductImg productImg1 = new ProductImg();
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试图片1");
		productImg1.setPriority(1);
		productImg1.setCreateTime(new Date());
		productImg1.setProductId(productId);
		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setImgDesc("测试图片2");
		productImg2.setPriority(1);
		productImg2.setCreateTime(new Date());
		productImg2.setProductId(productId);
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2,effectedNum);
		Product product = productDao.queryProductById(productId);
		assertEquals(2,product.getProductImgList().size());
		effectedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2,effectedNum);
		
	}
	@Test
	@Ignore
	public void testDUpdateProduct() throws Exception{
		Product product = new Product();
		ProductCategory pc = new ProductCategory();
		Shop shop = new Shop();
		shop.setShopId(13L);
		pc.setProductCategoryId(2L);
		product.setProductId(12L);
		product.setShop(shop);
		product.setProductName("第二个产品");
		product.setProductCategory(pc);
		int effectedNum = productDao.updateProduct(product);
		assertEquals(1,effectedNum);
	}
	@Test
	public void testEUpdateProductCategoryToNull() {
		int effectedNum = productDao.updateProductCategoryToNull(2L);
		assertEquals(1,effectedNum);
	}
	
}
