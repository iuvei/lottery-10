package com.wintv.lottery.admin.bet.vo;

import java.math.BigDecimal;
import java.util.Date;

public class MyAutoOrderVO {
	    private Long autoOrderId;//主键
		private Long kingId;//关联T_KING_SPONSOR的主键
		private Long userid;//自动跟单定制人用户ID
		private String orderTime;//定制时间
		private String minMoney;//金额最小值
		private String maxMoney;//金额最大值
		private String txMoney;
		private String username;//定制人用户名
		private String type;//投注方式  '1':单式  '2':复式
		private String status;//定制状态  1:正常    2:  取消
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
		public String getOrderTime() {
			return orderTime;
		}
		public void setOrderTime(String orderTime) {
			this.orderTime = orderTime;
		}
		public String getMinMoney() {
			return minMoney;
		}
		public void setMinMoney(String minMoney) {
			this.minMoney = minMoney;
		}
		public String getMaxMoney() {
			return maxMoney;
		}
		public void setMaxMoney(String maxMoney) {
			this.maxMoney = maxMoney;
		}
		public String getTxMoney() {
			return txMoney;
		}
		public void setTxMoney(String txMoney) {
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
}
