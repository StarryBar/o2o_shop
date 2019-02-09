package service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import base.BaseTest;
import o2o.dto.ImageHolder;
import o2o.dto.ProductExecution;
import o2o.entity.Product;
import o2o.entity.ProductCategory;
import o2o.entity.Shop;
import o2o.enums.ProductStateEnum;
import o2o.exceptions.ProductOperationException;
import o2o.exceptions.ShopOperationException;
import o2o.service.ProductService;

public class ProductServiceTest extends BaseTest {

	@Autowired
	private ProductService productService;
	
	@Test
	@Ignore
	public void testAddProduct() throws ProductOperationException,FileNotFoundException {
		
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(13L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		//创建缩略图文件流
		File thumbnailFile = new File("E:\\python3\\cv\\dog.png");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(),is);
		//创建两个商品详情图文件流并将它们添加到详情图列表中
		File productImg1 = new File("E:\\python3\\cv\\dog.png");
		InputStream is1 = new FileInputStream(productImg1);
		File productImg2 = new File("E:\\python3\\cv\\raw.jpg");
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(),is1));
		productImgList.add(new ImageHolder(productImg2.getName(),is2));	
		//添加商品并验证
		ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
	}
	
	@Test
	public void testModifyProduct() throws ShopOperationException,FileNotFoundException{
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(13L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(2L);
		product.setProductId(12L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("正式的商品");
		product.setProductDesc("正式的商品");
		
		File thumbnailFile = new File("E:\\python3\\cv\\dog.png");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(),is);
		File productImg1 = new File("E:\\python3\\cv\\raw.jpg");
		InputStream is1 = new FileInputStream(productImg1);
		File productImg2 = new File("E:\\python3\\cv\\target1.jpg");
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(),is1));
		productImgList.add(new ImageHolder(productImg2.getName(),is2));
		
		ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
	}
	
}
