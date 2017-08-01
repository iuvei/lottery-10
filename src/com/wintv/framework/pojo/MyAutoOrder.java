package com.wintv.framework.pojo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
/**
 * 彩票自动跟单定制  2010-03-11 11:35
 * 
 *
 */
@SuppressWarnings("serial")
public class MyAutoOrder implements Serializable {

    private Long autoOrderId;//主键
	private Long kingId;//关联T_KING_SPONSOR的主键
	private Long userid;//自动跟单定制人用户ID
	private Date orderTime;//定制时间
	private BigDecimal minMoney;//金额最小值
	private BigDecimal maxMoney;//金额最大值
	private BigDecimal txMoney;
	private String username;//定制人用户名
	private String type;//投注方式  '1':单式  '2':复式
	private String status;//定制状态  1:正常    2:  取消
	private Long orderNum;//定制人序号(金牌发起人发起合买后，按照定制人序号依次自动跟单购买)
	private String  isLackOrder;//认购金额不足是否认购
	private Long kingUserId;//金牌发起人用户ID
	/**
	 * 投注彩种    1: 胜负14场      2:任9场  3:4场进球  5:6 场半全场     
	 *61:单场半全场  62:单场比分 63:单场让球胜平负  64:单场上下单双 65:单场总进球
	 */
	private String category;
	public Long getAutoOrderId() {
		return autoOrderId;
	}
	public void setAutoOrderId(Long autoOrderId) {
		this.autoOrderId = autoOrderId;
	}
	public Long getKingId() {
		return kingId;
	}
	public void setKingId(Long kingId) {
		this.kingId = kingId;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public BigDecimal getMinMoney() {
		return minMoney;
	}
	public void setMinMoney(BigDecimal minMoney) {
		this.minMoney = minMoney;
	}
	public BigDecimal getMaxMoney() {
		return maxMoney;
	}
	public void setMaxMoney(BigDecimal maxMoney) {
		this.maxMoney = maxMoney;
	}
	public BigDecimal getTxMoney() {
		return txMoney;
	}
	public void setTxMoney(BigDecimal txMoney) {
		this.txMoney = txMoney;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	public String getIsLackOrder() {
		return isLackOrder;
	}
	public void setIsLackOrder(String isLackOrder) {
		this.isLackOrder = isLackOrder;
	}
	public Long getKingUserId() {
		return kingUserId;
	}
	public void setKingUserId(Long kingUserId) {
		this.kingUserId = kingUserId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}