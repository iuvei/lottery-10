package com.wintv.framework.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * 心水订单(B2C订单与c2c订单)
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class XinshuiOrder implements Serializable {
    
	private Long xinshuiOrderId;//主键
	private Long type;//类型   值只能为'1' 或者'2'
	private Long productId;//如果TYPE='1' 关联T_C2C_PRODUCT.B2C_ID  如果 TYPE='2' 关联T_EXPERT.T_EXPERT_ID
	private Long buyUserId;//购买人用户ID   关联T_USER表 
	private String buyUsername;//购买人用户名
	private Long soldUserId;//民间高手或者是特约专家
	private String soldUsername;
	private Date startTime;//生效起始时间(只对b2c有效)
	private Date endTime;//生效结束时间(只对b2c有效)
	private Double price;//价格
	private String status;//状态  1正在进行中   2 正常完成 3流单
	private Date buyTime;//产生订单时间
	/**
	 * 支付状态 支付状态:，1:“未支付”、2:“已支付”、3:“已扣款”、4:“已赔付”   5:“ 已取消”
	 * 如果是b2c订单:     1:"未支付"、2:"已支付"                        5:"已取消"
	 */
	private String payStatus;
	                          
	private String orderNo;//订单号
	private BigDecimal txMoney;//应支付的金额(如status='2',则 心水价格=应支付的金额+应支付的彩金)
	private BigDecimal txCaijin;//应支付的彩金(如status='2',则 心水价格=应支付的金额+应支付的彩金)
	
	private String orderType;//订购类型  '1':包年  '2':包季 '3':包月 '4':包周
	private Date raceStartTime;
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
	public Long getBuyUserId() {
		return buyUserId;
	}
	public void setBuyUserId(Long buyUserId) {
		this.buyUserId = buyUserId;
	}
	public Long getSoldUserId() {
		return soldUserId;
	}
	public void setSoldUserId(Long soldUserId) {
		this.soldUserId = soldUserId;
	}
	public String getSoldUsername() {
		return soldUsername;
	}
	public void setSoldUsername(String soldUsername) {
		this.soldUsername = soldUsername;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getBuyUsername() {
		return buyUsername;
	}
	public void setBuyUsername(String buyUsername) {
		this.buyUsername = buyUsername;
	}
	public BigDecimal getTxMoney() {
		return txMoney;
	}
	public void setTxMoney(BigDecimal txMoney) {
		this.txMoney = txMoney;
	}
	public BigDecimal getTxCaijin() {
		return txCaijin;
	}
	public void setTxCaijin(BigDecimal txCaijin) {
		this.txCaijin = txCaijin;
	}
	public Long getXinshuiOrderId() {
		return xinshuiOrderId;
	}
	public void setXinshuiOrderId(Long xinshuiOrderId) {
		this.xinshuiOrderId = xinshuiOrderId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Date getRaceStartTime() {
		return raceStartTime;
	}
	public void setRaceStartTime(Date raceStartTime) {
		this.raceStartTime = raceStartTime;
	}
	


}