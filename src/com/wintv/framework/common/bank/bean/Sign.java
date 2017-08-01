package com.wintv.framework.common.bank.bean;

public class Sign {
	
	// 商户号，长度�?15个字节的数字串，由ChinaPay或清算银行分配�??
	private String MerId = "808080052502123";
	// 订单号，长度�?16个字节的数字串，由商户系统生成，失败的订单号允许重复支付�?
	private String OrdId;
	// 交易金额，长度为12个字节的数字串，例如：数字串"000000001234"表示12.34元�??
	private String TransAmt;
	// 货币代码, 长度�?3个字节的数字串，目前只支持人民币，取值为"156" �?
	private String CuryId = "156";
	// 交易日期，长度为8个字节的数字串，表示格式为：YYYYMMDD�?
	private String TransDate;
	// 交易类型，长度为4个字节的数字串，取�?�范围为�?"0001"�?"0002"�? 其中"0001"表示消费交易
	private String TransType = "0001";
	// 接口版本�?
	private String Version = "20040916";
	// 后台交易接收URL，长度不要超�?80个字�?
	private String BgRetUrl;
	// 页面交易接收URL，长度不要超�?80个字�?
	private String PageRetUrl;
	// 支付网关号，可�??
	private String GateId = "0001";
	// 商户私有域，可�?�，长度不要超过60个字�?
	private String Priv1;
	// 256字节长的ASCII�?
	private String ChkValue;

	public Sign() {};
	
	public Sign(String MerId, String OrdId, String TransAmt, String CuryId, String TransDate, String TransType, String Version, String BgRetUrl, String PageRetUrl, String GateId, String Priv1, String ChkValue) {
		this.MerId = MerId;
		this.OrdId = OrdId;
		this.TransAmt = TransAmt;
		this.CuryId = CuryId;
		this.TransDate = TransDate;
		this.TransType = TransType;
		this.Version = Version;
		this.BgRetUrl = BgRetUrl;
		this.PageRetUrl = PageRetUrl;
		this.GateId = GateId;
		this.Priv1 = Priv1;
		this.ChkValue = ChkValue;
	}
	
	public String getMerId() {
		return MerId;
	}

	public void setMerId(String merId) {
		MerId = merId;
	}

	public String getOrdId() {
		return OrdId;
	}

	public void setOrdId(String ordId) {
		OrdId = ordId;
	}

	public String getTransAmt() {
		return TransAmt;
	}

	public void setTransAmt(String transAmt) {
		TransAmt = transAmt;
	}

	public String getCuryId() {
		return CuryId;
	}

	public void setCuryId(String curyId) {
		CuryId = curyId;
	}

	public String getTransDate() {
		return TransDate;
	}

	public void setTransDate(String transDate) {
		TransDate = transDate;
	}

	public String getTransType() {
		return TransType;
	}

	public void setTransType(String transType) {
		TransType = transType;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getBgRetUrl() {
		return BgRetUrl;
	}

	public void setBgRetUrl(String bgRetUrl) {
		BgRetUrl = bgRetUrl;
	}

	public String getPageRetUrl() {
		return PageRetUrl;
	}

	public void setPageRetUrl(String pageRetUrl) {
		PageRetUrl = pageRetUrl;
	}

	public String getGateId() {
		return GateId;
	}

	public void setGateId(String gateId) {
		GateId = gateId;
	}

	public String getPriv1() {
		return Priv1;
	}

	public void setPriv1(String priv1) {
		Priv1 = priv1;
	}

	public String getChkValue() {
		return ChkValue;
	}

	public void setChkValue(String chkValue) {
		ChkValue = chkValue;
	}

}
