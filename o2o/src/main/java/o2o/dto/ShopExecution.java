package o2o.dto;

import java.util.List;

import o2o.entity.Shop;
import o2o.enums.ShopStateEnum;

public class ShopExecution {

	//	结果状态
	private int state;
	
	// 状态标志
	private String stateInfo;
	
	//操作的shop（增删该查店铺使用）
	private Shop shop;
	
	//shop列表（查询店铺列表的时候使用)
	private List<Shop> shopList;
	
	private int count;
	
	public ShopExecution() {
		
	}
	//操作失败的函数构造器
	public ShopExecution(ShopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	//操作成功的函数构造器
	public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}
	//操作成功的函数构造器
	public ShopExecution(ShopStateEnum stateEnum, List<Shop> shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shop;
	}
	public int getState() {
		return state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public Shop getShop() {
		return shop;
	}
	public List<Shop> getShopList() {
		return shopList;
	}
	public int getCount() {
		return count;
	}
	public void setState(int state) {
		this.state = state;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
