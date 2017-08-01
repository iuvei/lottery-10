 package com.wintv.lottery.xinshui.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.wintv.framework.pojo.XinshuiOrder;

public class XinshuiVo implements java.io.Serializable {

	private Long xinshuiId;
    /**
     * 类型   值只能为'1' 或者'2'
     */
	private Long type;
	/**
	 * 产品ID  如果TYPE='1' 关联T_B2C_PRODUCT.B2C_ID  
	 * 如果 TYPE='2' 关联T_C2C_PRODUCT.C2C_ID
	 */
	private Long productId;
	/**
	 * 卖家用户ID   关联T_USER表 
	 */
	private Long sellerId;
	/**
	 * 买家用户ID   关联T_USER表 
	 */
	private Long buyerId;
	/**
	 * 产品生效起始时间
	 */
	private Date startTime;
	/**
	 * 产品生效结束时间
	 */
	private Date endTime;
	/**
	 * 价格
	 */
	private java.math.BigDecimal price;
	/**
	 * 产品名称
	 */
	private String productName;
	
	/**
	 * 卖家名称
	 */
	private String sellerName;
	
	/**
	 * 买家名称
	 */
	private String buyerName;
	
	private String phase;//第几期

	public XinshuiVo() {};

	public XinshuiVo(Long xinshuiId, Long type, Long productId, Long sellerId,
			Long buyerId, Date startTime, Date endTime, BigDecimal price,
			String productName, String sellerName, String buyerName,
			String phase) {
		super();
		this.xinshuiId = xinshuiId;
		this.type = type;
		this.productId = productId;
		this.sellerId = sellerId;
		this.buyerId = buyerId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
		this.productName = productName;
		this.sellerName = sellerName;
		this.buyerName = buyerName;
		this.phase = phase;
	}

	public Long getXinshuiId() {
		return xinshuiId;
	}

	public void setXinshuiId(Long xinshuiId) {
		this.xinshuiId = xinshuiId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

}
