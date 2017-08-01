package com.wintv.framework.pojo;

import java.io.Serializable;

/**金牌发起人选择的彩种**/
@SuppressWarnings("serial")
public class KingSponsorCategory implements Serializable {
    private Long id;//主键
	private String betCategory;//彩种    1: 胜负14场      2:任9场  3:4场进球  4:4半全场  5:6 场半全场     61:单场半全场  62:单场比分 63:单场胜平负  64:单场上下单双 65:单场总进球
	private Long kingId;//金牌发起人主键
	private Integer status;//彩种审核状态  0:未审核  1:审核通过     2:审核未通过
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBetCategory() {
		return betCategory;
	}
	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}
	public Long getKingId() {
		return kingId;
	}
	public void setKingId(Long kingId) {
		this.kingId = kingId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}


}