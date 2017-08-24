<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ec" uri="http://www.extremecomponents.org" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="basePath" value="${pageContext.request.scheme}+'://'+${pageContext.request.serverName}+
							':'+${pageContext.request.serverPort}+${pageContext.request.contextPath}+'/'"/>
							
<link   href="${ctx}/css/common/common.css" rel="stylesheet" type="text/css" />							
<script language="javaScript" type="text/javaScript" src="${ctx}/js/common/common.js"></script>
<script language="javaScript" type="text/javaScript" src="${ctx}/js/common/Dialog.js"></script>
<script language="javaScript" type="text/javaScript" src="${ctx}/js/common/jquery/jquery-1.2.1.js"></script>
<script language="javaScript" type="text/javaScript" src="${ctx}/js/common/jquery/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
if(!window.path) window.path = "${pageContext.request.contextPath}";
var rootPath ="${ctx}/";
String.prototype.Trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}
</script>
<div id="count_for_str_num" style="display:none;color:green;width:180px;height:15px;position:absolute;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=60);"></div>