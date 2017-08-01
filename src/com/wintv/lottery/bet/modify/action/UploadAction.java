package com.wintv.lottery.bet.modify.action;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.acegi.UserCookie;
import com.wintv.framework.common.BaseAction;
import com.wintv.framework.common.Constants;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.bet.service.EnhancementService;
import com.wintv.lottery.bet.utils.CommUtil;
import com.wintv.lottery.bet.vo.BetOrderVO;

/**
 * 方案修改-文件上传
 * 
 * @author Arix04 by 2010-04-19
 * 
 * @version 1.0.0
 */

public class UploadAction extends BaseAction {

	@Autowired
	private EnhancementService enhancementService;
	
	private String upType;//1 = 上传方案、2 = 修改方案
	private Long betId;//方案ID
	private String categoryType;//lotteryType彩种 6,7,8,9
	private BetOrderVO betOrder;//方案信息
	private String phaseId;//期次ID
	private String betCategory;//彩种 1,2,3,5
	
	// 封装上传文件的属性
	private File doc;
	// 封装上传文件名称属性
	private String fileName;
	// 封装上传文件类型属性
	private String contentType;
	// 保存文件路径属性
	private String dir;
	// 保存文件名称属性
	private String targetFileName;
	//上传文件临时文件
	private static String TEMP_UPLOAD_FILE="TEMP_UPLOAD_FILE";
	
	private static final long serialVersionUID = -9156031523533772461L;

	/***
	 * 修改或者上传文件
	 * @return
	 */
	public String upload() {
		try {
			betOrder = enhancementService.loadBetOrder(betId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 方案修改-上传文件/修改文件
	 * 
	 * @return  
	 */
	public String uploadConfirm() {
		try {
			Map params = new HashMap();
			params = putTxtPathToParamsMap(params);
			
			boolean flg = true;
			if("1".equals(upType)) {
				if(request.getSession().getAttribute("userCookie") != null) {
					Long userId = ((UserCookie)request.getSession().getAttribute("userCookie")).getUserId();
					Map m = new HashMap();
					m.put("userId", userId);
					m.put("betId", betId);
					m.put("txtPath", params.get("txtPath"));
					
					flg = enhancementService.uploadTxtPath(m);
				} else {
					flg = false;
				}
			}
			generateResult(1, MSG_SUCCESS, flg);
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 各彩种的上传文件格式验证。
	 * 
	 * @return String
	 */
	public void validator() {
		try {
			session.remove(TEMP_UPLOAD_FILE);
			if(doc == null) {
				generateResult(0, MSG_FAILURE, "上传文件出错，上传文件不存在！");
			}else{
				result=CommUtil.doValidator(doc, fileName, categoryType);
				System.out.println("----"+doc.getAbsolutePath()+"-----");
				if(result.getMessage().equals("success")){
					Map oneFile=new HashMap();
					//将文档写到内存进行缓存
					oneFile.put("doc", FileUtils.readFileToString(doc));
					oneFile.put("fileName",fileName);
					session.put(TEMP_UPLOAD_FILE, oneFile);
				}
			}
		} catch (Exception e) {
			generateResult(0, MSG_FAILURE, "errors");
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		ajaxForAction(obj.toString());//返回验证结果
	}
	
	/**
	 * 单式发起立即上传时,将上传文件路径放到参数map中去
	 * @param params
	 * @return
	 */
	private Map putTxtPathToParamsMap(Map params){
		Map result = params;
		String txtPath = genUploadFilePath();
		System.out.println("-----txtPath="+txtPath+"-------");
		result.put("txtPath", txtPath);
		return result;
	}
	
	/**
	 * 上传文件到服务器同时返回生成上传文件的路径名称
	 */
	private String genUploadFilePath(){
		String txtPath=null;
		if(session.containsKey(TEMP_UPLOAD_FILE)){
			Map oneFile=(Map)session.get(TEMP_UPLOAD_FILE);
			String data=(String)oneFile.get("doc");
			String fileName=(String)oneFile.get("fileName");
			try {
				// 获得upload路径的实际目录
				String realPath = ServletActionContext
						.getServletContext().getInitParameter(
								"uploadPath");
				String targetDirectory = realPath + "\\" + Constants.UPLOAD_PALN_FILE + "\\" + betCategory + "\\" + phaseId;
				
				// 生成保存文件的文件名称
				if("1".equals(upType)) {
					targetFileName = CommUtil.generateFileName(fileName);
				} else {
					targetFileName = enhancementService.loadBetOrder(betId).getTxtPath();
				}
				// 保存文件的路径
				setDir(targetDirectory + targetFileName);
				// 建立一个目标文件
				File target = new File(targetDirectory, targetFileName);
				// 将临时文件复制到目标文件
				//FileUtils.w.copyFile(doc, target);
				// 将临时文件将临时文件从内存中读出写到目标文件
				FileUtils.writeStringToFile(target, data);
				/* 用户上传式投注 */
				txtPath = targetFileName;
				// 设定文件保存路径
			} catch (Exception e) {
				e.printStackTrace();
				throw new LotteryBizException(e.getLocalizedMessage());
			}
		}
		return txtPath;
	}
	
	public Long getBetId() {
		return betId;
	}

	public void setBetId(Long betId) {
		this.betId = betId;
	}

	public String getUpType() {
		return upType;
	}

	public void setUpType(String upType) {
		this.upType = upType;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public String getFileName() {
		return fileName;
	}

	public void setDocFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setDocContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getTargetFileName() {
		return targetFileName;
	}

	public void setTargetFileName(String targetFileName) {
		this.targetFileName = targetFileName;
	}

	public BetOrderVO getBetOrder() {
		return betOrder;
	}

	public void setBetOrder(BetOrderVO betOrder) {
		this.betOrder = betOrder;
	}

	public String getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(String phaseId) {
		this.phaseId = phaseId;
	}

	public String getBetCategory() {
		return betCategory;
	}

	public void setBetCategory(String betCategory) {
		this.betCategory = betCategory;
	}
}
