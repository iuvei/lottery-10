package com.wintv.lottery.bet.action;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.pojo.Against;
import com.wintv.lottery.bet.service.BetService;

@SuppressWarnings("serial")
public class DshmAction   extends BaseAction {

	public void submit(){
		UserCookie userCookie = (UserCookie)request.getSession().getAttribute("userCookie");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("betUserid", userCookie.getUserId());
		param.put("betUsername", userCookie.getUsername());
		param.put("from", from);
		param.put("phaseNo", phaseNo);
		param.put("phaseId", phaseId);
		param.put("type", "2");
		param.put("betCategory", dsdg_type);
		param.put("against_ids", against_ids);
		param.put("file", file);
		param.put("against_count", against_count);
		param.put("fileFileName", fileFileName);
		param.put("fileContentType", fileContentType);
		param.put("savePath", savePath);
		param.put("upload_input", upload_input);
		param.put("pass_fashion", pass_fashion);
		param.put("wiciWay", pass_fashion);
		param.put("betMulti", multiple);
		param.put("endTime", endTime);
		param.put("useCaijin", useCaijin);
		
		param.put("subscribeCopys", subscribeCopys);
		param.put("subscribeMoney", subscribeMoney);
		param.put("floorCopys", floorCopys);
		param.put("securityType", securityType);
		param.put("singleMoney", singleMoney);
		param.put("isFloor", isFloor);
		param.put("divideCopys", divideCopys);
		param.put("tcRate", tcRate);
		
		param = betService.dshm_submit(param);
		JSONObject obj = null;
		if(null != param.get("error")){
			obj = new JSONObject();
			obj.put("error", param.get("error"));
			ajaxForAction(obj.toString());  
			return ;
		}
		all_money = (BigDecimal)param.get("allMoney")+"";
		obj = new JSONObject();
		obj.put("all_money", all_money);
		obj.put("count", param.get("count"));
		obj.put("flag", param.get("flag"));
        ajaxForAction(obj.toString()); 
	}
	
	
	@Autowired
	private BetService betService;
	private String from;             //页面来源
	private String phaseNo;          //期次
	private String phaseId;
	private String dsdg_type; 			//彩种
	private int against_count;       //比赛场次
	private String against_ids;       //对阵IDS
	//private String index_array;       //页面对阵索引(用于上传错误后回显)
	private File  file;
	private String fileFileName;
	private String fileContentType;
    private String savePath;
    
    private String upload_input;     //上传格式拼装
    private String pass_fashion;	 //过关方式
    private String multiple;         //倍投
    private String endTime;          //截止时间
    private String betNum;           //注数
    private String all_money;        //总金额
    private List<Against> againstList;  //对阵列表
    private String useCaijin;            //使用彩金
    
    private String subscribeCopys;   //发起人认购份数
    private String subscribeMoney;   //认购金额
    private String floorCopys;     //保底份数
    private String securityType;    //保密类型:  1:完全公开  2:截止后公开   3:仅对跟单用户公开
    private String divideCopys;     //分成多少份
    private String singleMoney;    //每一份金额
    private String isFloor;        //是否保底
    private String tcRate ;         //提成比例
    
   
    public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getPhaseNo() {
		return phaseNo;
	}
	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}
	public String getPhaseId() {
		return phaseId;
	}
	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}
	public String getDsdg_type() {
		return dsdg_type;
	}
	public void setDsdg_type(String dsdg_type) {
		this.dsdg_type = dsdg_type;
	}
	public int getAgainst_count() {
		return against_count;
	}
	public void setAgainst_count(int against_count) {
		this.against_count = against_count;
	}
	public String getAgainst_ids() {
		return against_ids;
	}
	public void setAgainst_ids(String against_ids) {
		this.against_ids = against_ids;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getUpload_input() {
		return upload_input;
	}
	public void setUpload_input(String upload_input) {
		this.upload_input = upload_input;
	}
	public String getPass_fashion() {
		return pass_fashion;
	}
	public void setPass_fashion(String pass_fashion) {
		this.pass_fashion = pass_fashion;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getBetNum() {
		return betNum;
	}
	public void setBetNum(String betNum) {
		this.betNum = betNum;
	}
	public String getAll_money() {
		return all_money;
	}
	public void setAll_money(String all_money) {
		this.all_money = all_money;
	}
	public List<Against> getAgainstList() {
		return againstList;
	}
	public void setAgainstList(List<Against> againstList) {
		this.againstList = againstList;
	}
	public String getUseCaijin() {
		return useCaijin;
	}
	public void setUseCaijin(String useCaijin) {
		this.useCaijin = useCaijin;
	}
	public String getSubscribeCopys() {
		return subscribeCopys;
	}
	public void setSubscribeCopys(String subscribeCopys) {
		this.subscribeCopys = subscribeCopys;
	}
	public String getSubscribeMoney() {
		return subscribeMoney;
	}
	public void setSubscribeMoney(String subscribeMoney) {
		this.subscribeMoney = subscribeMoney;
	}
	public String getFloorCopys() {
		return floorCopys;
	}
	public void setFloorCopys(String floorCopys) {
		this.floorCopys = floorCopys;
	}
	public String getSecurityType() {
		return securityType;
	}
	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}
	public String getDivideCopys() {
		return divideCopys;
	}
	public void setDivideCopys(String divideCopys) {
		this.divideCopys = divideCopys;
	}
	public String getSingleMoney() {
		return singleMoney;
	}
	public void setSingleMoney(String singleMoney) {
		this.singleMoney = singleMoney;
	}
	public String getIsFloor() {
		return isFloor;
	}
	public void setIsFloor(String isFloor) {
		this.isFloor = isFloor;
	}
	public String getTcRate() {
		return tcRate;
	}
	public void setTcRate(String tcRate) {
		this.tcRate = tcRate;
	}
}
