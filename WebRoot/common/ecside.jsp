<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ec" uri="http://www.extremecomponents.org" %>
<link rel="stylesheet" type="text/css" href="${ctx}/css/common/jquery.dialog.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/common/ecside_style.css" />
<script language="javaScript" src="${ctx}/js/common/ecside.js" type="text/javascript"></script>
<script language="javaScript" src="${ctx}/js/common/jquery/jquery.eform.js" type="text/javascript"></script>
<script language="javaScript" src="${ctx}/js/common/jquery/jquery.form.js" type="text/javascript"></script>
<script language="javaScript" src="${ctx}/js/common/jquery/jquery.dialog.js" type="text/javascript"></script>
<script type="text/javascript">
	var ECSideMessage={
		ENCODING		: "UTF8",
		WAITTING_MSG	: "请等待...",
		FILTERCLEAR_TEXT: "<fmt:message key='ECSideMessage.FILTERCLEAR_TEXT' />",
		SORTASC_TEXT	: "<fmt:message key='ECSideMessage.SORTASC_TEXT' />",
		SORTDESC_TEXT	: "<fmt:message key='ECSideMessage.SORTDESC_TEXT' />",
		SORTDEFAULT_TEXT: "<fmt:message key='ECSideMessage.SORTDEFAULT_TEXT' />",
		ERR_PAGENO		: "<fmt:message key='ECSideMessage.ERR_PAGENO' />",
		EXPORT_CONFIRM	: "点'确认'导出所有数据,点'取消'导当前页数据！",
		OVER_MAXEXPORT	: "<fmt:message key='ECSideMessage.OVER_MAXEXPORT' />",
		SHADOWROW_FAILED: "<fmt:message key='ECSideMessage.SHADOWROW_FAILED' />",
		NO_RECORD_UPDATE: "<fmt:message key='ECSideMessage.NO_RECORD_UPDATE' />",
		UPDATE_CONFIRM	: "<fmt:message key='ECSideMessage.UPDATE_CONFIRM' />",
		NEARPAGE_TITLE	: ""
	};
</script>