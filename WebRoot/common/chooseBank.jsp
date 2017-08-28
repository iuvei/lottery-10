<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<s:if test="virtualAccount.bankCode == null">
	<div class="zucetext ts-20-5-2"><input type="radio" name="bank_code" value="ICBC" id="radiobutton" /></div>
    <div class="ts-20-5-3"><img src="${ctx}/images/gonghanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-4"><input type="radio" name="bank_code" value="CMBC" id="radiobutton" /></div>
    <div class="ts-20-5-5"><img src="${ctx}/images/zhaohanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-6"><input type="radio" name="bank_code" value="CCB" id="radiobutton" /></div>
    <div class="ts-20-5-7"><img src="${ctx}/images/jianhanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-8"><input type="radio" name="bank_code" value="ABC" id="radiobutton" /></div>
    <div class="ts-20-5-9"><img src="${ctx}/images/longhanglogo-huiyuan2.gif" width="110" height="32" /></div>
</s:if>
<s:elseif test="virtualAccount.bankCode == 'ICBC'">
	<div class="zucetext ts-20-5-2"><input type="radio" name="bank_code" value="ICBC" id="radiobutton" checked="checked"/></div>
    <div class="ts-20-5-3"><img src="${ctx}/images/gonghanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-4"><input type="radio" name="bank_code" value="CMBC" id="radiobutton" /></div>
    <div class="ts-20-5-5"><img src="${ctx}/images/zhaohanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-6"><input type="radio" name="bank_code" value="CCB" id="radiobutton" /></div>
    <div class="ts-20-5-7"><img src="${ctx}/images/jianhanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-8"><input type="radio" name="bank_code" value="ABC" id="radiobutton" /></div>
    <div class="ts-20-5-9"><img src="${ctx}/images/longhanglogo-huiyuan2.gif" width="110" height="32" /></div>
</s:elseif>
<s:elseif test="virtualAccount.bankCode == 'CMBC'">
	<div class="zucetext ts-20-5-2"><input type="radio" name="bank_code" value="ICBC" id="radiobutton" /></div>
    <div class="ts-20-5-3"><img src="${ctx}/images/gonghanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-4"><input type="radio" name="bank_code" value="CMBC" id="radiobutton" checked="checked"/></div>
    <div class="ts-20-5-5"><img src="${ctx}/images/zhaohanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-6"><input type="radio" name="bank_code" value="CCB" id="radiobutton" /></div>
    <div class="ts-20-5-7"><img src="${ctx}/images/jianhanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-8"><input type="radio" name="bank_code" value="ABC" id="radiobutton" /></div>
    <div class="ts-20-5-9"><img src="${ctx}/images/longhanglogo-huiyuan2.gif" width="110" height="32" /></div>
</s:elseif>
<s:elseif test="virtualAccount.bankCode == 'ABC'">
	<div class="zucetext ts-20-5-2"><input type="radio" name="bank_code" value="ICBC" id="radiobutton" /></div>
    <div class="ts-20-5-3"><img src="${ctx}/images/gonghanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-4"><input type="radio" name="bank_code" value="CMBC" id="radiobutton" /></div>
    <div class="ts-20-5-5"><img src="${ctx}/images/zhaohanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-6"><input type="radio" name="bank_code" value="CCB" id="radiobutton" /></div>
    <div class="ts-20-5-7"><img src="${ctx}/images/jianhanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-8"><input type="radio" name="bank_code" value="ABC" id="radiobutton" checked="checked"/></div>
    <div class="ts-20-5-9"><img src="${ctx}/images/longhanglogo-huiyuan2.gif" width="110" height="32" /></div>
</s:elseif>
<s:elseif test="virtualAccount.bankCode == 'CCB'">
	<div class="zucetext ts-20-5-2"><input type="radio" name="bank_code" value="ICBC" id="radiobutton" /></div>
    <div class="ts-20-5-3"><img src="${ctx}/images/gonghanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-4"><input type="radio" name="bank_code" value="CMBC" id="radiobutton" /></div>
    <div class="ts-20-5-5"><img src="${ctx}/images/zhaohanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-6"><input type="radio" name="bank_code" value="CCB" id="radiobutton" checked="checked"/></div>
    <div class="ts-20-5-7"><img src="${ctx}/images/jianhanglogo-huiyuan2.gif" width="110" height="32" /></div>
    <div class="zucetext ts-20-5-8"><input type="radio" name="bank_code" value="ABC" id="radiobutton" /></div>
    <div class="ts-20-5-9"><img src="${ctx}/images/longhanglogo-huiyuan2.gif" width="110" height="32" /></div>
</s:elseif>
<s:else>
</s:else>  
</body>
</html>