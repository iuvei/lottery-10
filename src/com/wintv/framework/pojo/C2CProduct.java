package com.wintv.framework.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * C2C产品--民间高手发布的心水包括B2C产品
 */
public class C2CProduct implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long c2cId;// C2C主键
	private Long againstId;// 竞猜赛程ID 关联到表T_XINSHUI_AGAINST
	private Long txUserId;// 民间高手用户ID
	private String txUsername;// 民间高手用户名
	private BigDecimal ensureMoney;// 缴纳的保证金
	private String hostName;
	private String guestName;

	private String type;// '1':亚盘 '2':大小盘
	private String selectType;// 选主, 1：选主 2：选客 3：选大 4：选小
	private String zhDesc;
	private BigDecimal price;// 心水价格
	/**
	 * 心水状态: '1':'发布中','2':'赢','3':'负','4':'走','5':'已关闭'    
	 * 民间高手如果猜的是平手盘  则为'走'    4:'走'  这时不收10%的手续费
	 */
	private String status;
	private String xinshuiNo;// 心水编号(与订单号类似)
	private String raceName;// 赛事名称
	private Long raceId;// 赛事类型
	private Date pubTime;// 发布时间

	
	private String aindex;// 主队或者大球指数
	private String bindex;// 客队或者小球指数
	private String panKouIndex;// 盘口
	private String confidenceIndex;//信心指数
	private String isB2C;//是否为B2C
	private Long click;//点击数
    private Integer isRecommend;//1:推荐在网站首页 0 不推荐在网站首页
    private Date raceStartTime;//比赛开始时间
	public Long getC2cId() {
		return c2cId;
	}

	public void setC2cId(Long id) {
		c2cId = id;
	}

	public Long getAgainstId() {
		return againstId;
	}

	public void setAgainstId(Long againstId) {
		this.againstId = againstId;
	}

	public Long getTxUserId() {
		return txUserId;
	}

	public void setTxUserId(Long txUserId) {
		this.txUserId = txUserId;
	}

	public String getTxUsername() {
		return txUsername;
	}

	public void setTxUsername(String txUsername) {
		this.txUsername = txUsername;
	}

	public BigDecimal getEnsureMoney() {
		return ensureMoney;
	}

	public void setEnsureMoney(BigDecimal ensureMoney) {
		this.ensureMoney = ensureMoney;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	public String getZhDesc() {
		return zhDesc;
	}

	public void setZhDesc(String zhDesc) {
		this.zhDesc = zhDesc;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getXinshuiNo() {
		return xinshuiNo;
	}

	public void setXinshuiNo(String xinshuiNo) {
		this.xinshuiNo = xinshuiNo;
	}

	public String getRaceName() {
		return raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	public Long getRaceId() {
		return raceId;
	}

	public void setRaceId(Long raceId) {
		this.raceId = raceId;
	}

	public Date getPubTime() {
		return pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}



	public String getPanKouIndex() {
		return panKouIndex;
	}

	public void setPanKouIndex(String panKouIndex) {
		this.panKouIndex = panKouIndex;
	}

	public String getAindex() {
		return aindex;
	}

	public void setAindex(String aindex) {
		this.aindex = aindex;
	}

	public String getBindex() {
		return bindex;
	}

	public void setBindex(String bindex) {
		this.bindex = bindex;
	}

	public String getConfidenceIndex() {
		return confidenceIndex;
	}

	public void setConfidenceIndex(String confidenceIndex) {
		this.confidenceIndex = confidenceIndex;
	}

	public String getIsB2C() {
		return isB2C;
	}

	public void setIsB2C(String isB2C) {
		this.isB2C = isB2C;
	}

	public Long getClick() {
		return click;
	}

	public void setClick(Long click) {
		this.click = click;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Date getRaceStartTime() {
		return raceStartTime;
	}

	public void setRaceStartTime(Date raceStartTime) {
		this.raceStartTime = raceStartTime;
	}
}