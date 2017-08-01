package com.wintv.lottery.admin.user.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.wintv.framework.common.BaseAction;
import com.wintv.lottery.admin.user.service.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 根据选择的用户ID，导出相关用户名到文件
 * 
 * @author Hikin Yao
 * 
 * @version 1.0.0
 */
public class UserExportAdminAction extends BaseAction {
	private static final long serialVersionUID = -1270125300278228193L;
	private String checkedUserIds = "83,105,86";
	private String downloadFileName = "users.txt";
	@Autowired
	private UserAdminService userAdminService;

	public InputStream getInputStream() throws Exception {
		String exportUserNames="";
		if(null!=checkedUserIds&&!checkedUserIds.equals("")){			
			exportUserNames = userAdminService
				.getUserListByUserIds(checkedUserIds);
		}
		ByteArrayInputStream stream = new ByteArrayInputStream(exportUserNames
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
