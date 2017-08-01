package com.wintv.lottery.bet.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.io.File;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import com.wintv.framework.common.BaseAction;
import com.wintv.lottery.admin.user.service.UserAdminService;
import com.wintv.lottery.bet.service.BetOrderRecordService;


public class DownloadTxtAction extends BaseAction {

	private static final long serialVersionUID = -1270125300278228193L;
	private String checkedUserIds = "83,105,86";
	private String downloadFileName = "users.txt";
	@Autowired
    private BetOrderRecordService betOrderRecordService;

	public InputStream getInputStream() throws Exception {
		String id=request.getParameter("id");
		//System.out.println("***"+id);
		//
	    String dir = request.getRealPath(File.separator+"WEB-INF"+File.separator+"upload");
	    String fileName=betOrderRecordService.getTxtFile(new Long(id));
		
		String fullFilePath =dir+File.separator+ fileName;
		//System.out.println(fullFilePath);
		BufferedReader br = null;   
	     String ret = null;   
	     try {   
	         br =  new BufferedReader(new FileReader(fullFilePath));   
	         //System.out.println(br!=null);
	         String line = null;   
	         StringBuffer sb = new StringBuffer((int)fullFilePath.length());   
	         while( (line = br.readLine() ) != null ) {   
	             sb.append(line);
	         }   
	         ret = sb.toString();   
	         //System.out.println("王"+ret);
	     } finally {   
	         if(br!=null) {try{br.close();} catch(Exception e){} }   
	     }   
		
		
		//
		ByteArrayInputStream stream = new ByteArrayInputStream(ret
				.getBytes());
		return stream;
	}

	public String execute() throws Exception {
		return SUCCESS;
	}
	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public String getCheckedUserIds() {
		return checkedUserIds;
	}

	public void setCheckedUserIds(String checkedUserIds) {
		this.checkedUserIds = checkedUserIds;
	}


}
