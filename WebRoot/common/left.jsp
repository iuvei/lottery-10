<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心-列表</title>
<link href="${ctx}/css/left.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/left.js"></script>
</head>
<body>
<div id="body_div">
<table cellpadding="1" cellspacing="1">
<tr>
<td>
<div class="tab" style="border:none;">
    <a target="main" href="${ctx}/mylottery.html" style="text-decoration:none;color:#000000; "><div class="tab_off" onClick="showTab(2, 1, 0)" id="nav1_0">我的天彩</div></a>
    <a target="_top" href="${ctx}/mycenter.html" style="text-decoration:none;color:#000000; "><div class="tab_on" onClick="showTab(2, 1, 1)" id="nav1_1">购彩大厅</div></a>
</div>
<div id="body">
    <div id="showTab1_0" style="display:none;">
        <div id="main1" onclick=expandIt('1') class="menu_left_open">我的彩票</div>
        <div id="sub1">
            <a target="main" href="${ctx}/hall.html">我 要 投 注</a>
            <a target="main" href="${ctx}/bet/mybetrecord.html">投 注 记 录</a>
            <a target="main" href="${ctx}/bet/gendan/mylistGendan.html">我 的 跟 单</a>
            <a target="main" href="${ctx}/attention/index.html">我 的 关 注</a>
            <a target="main" href="${ctx}/b2c/myB2cOrders.html">我 的 内 参</a>
        </div>
        <div id="main2" onclick=expandIt(2) class="menu_left_open">我的心水</div>
        <div id="sub2">
            <a target="main" href="${ctx}/xinshui/buyManage.html">购 买 明 细</a>
            <a target="main" href="${ctx}/xinshui/saleManage.html">销 售 明 细</a>
            <a href="javascript:void(0);" id="pubXinshui">发 布 心 水</a>
            <a target="main" href="${ctx}/xinshui/releaseChoice.html"  style="display:none;"><font id="toPubXinshui"></font></a>
            <a target="main" href="${ctx}/xinshui/releaseManage.html">管 理 心 水</a>
        </div>
        <div id="main3" onclick=expandIt(3) class="menu_left_open">我的资金</div>
        <div id="sub3">
            <a target="main" href="${ctx}/pay/fund_list.html">资 金 明 细</a>
            <a target="main" href="${ctx}/pay/charge.html">我 要 充 值</a>
            <a target="main" href="${ctx}/pay/withdraw.html">我 要 取 款</a>
        </div>
        <div id="main4" onclick=expandIt(4) class="menu_left_open">我的彩金</div>
        <div id="sub4">
            <a target="main" href="${ctx}/pay/caijin_list.html">彩 金 明 细</a>
            <a href="#">彩 金 充 值</a>
            <a href="#">彩 金 说 明</a>
        </div>
        <div id="main5" onclick=expandIt(5) class="menu_left_open">我的积分</div>
        <div id="sub5">
            <a href="#">积 分 明 细</a>
            <a href="#">积 分 兑 换</a>
            <a href="#">兑 换 记 录</a>
            <a href="#">积 分 抽 奖</a>
            <a href="#">抽 奖 记 录</a>
            <a href="#">积 分 规 则</a>
        </div>
        <div id="main6" onclick=expandIt(6) class="menu_left_open">我的信息</div>
        <div id="sub6">
            <a target="main" href="${ctx}/user/preUpdateUserInfo.html">个人信息修改</a>
            <a target="main" href="${ctx}/user/preUpdateLPUserInfo.html">登陆密码管理</a>
            <a target="main" href="${ctx}/user/preUpdateBankUserInfo.html">银行资料管理</a>
        </div>
    </div>
    <div id="showTab1_1">
        <div id="main7" onclick=expandIt(7) class="menu_left_open"><em><a href="#">玩法» </a></em>足球单场</div>
        <div id="sub7">
        <ul>
            <li>
            	<div id="title1" onclick=subMenu(1) class="menu_right_close">让 球 胜 平 负</div>
                <div id="td1" style="display:none;">
                    <a target="main" href="${ctx}/bet/single/fsdgWinDrawLose.html">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/single/togetherBuy.html?betCategory=63">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/single/fshmWinDrawLose.html">发 起 合 买</a>
                </div>
            </li>
            <li>
            	<div id="title2" onclick=subMenu(2) class="menu_right_close">总 进 球 数</div>
                <div id="td2" style="display:none;">
                    <a target="main" href="${ctx}/bet/single/fsdgTotalGoal.html">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/single/togetherBuy.html?betCategory=65">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/single/fshmTotalGoal.html">发 起 合 买</a>
                </div>
            </li>
            <li>
            	<div id="title3" onclick=subMenu(3) class="menu_right_close">上 下 单 双</div>
                <div id="td3" style="display:none;">
                    <a target="main" href="${ctx}/bet/single/fsdgUpDown.html">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/single/togetherBuy.html?betCategory=64">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/single/fshmUpDown.html">发 起 合 买</a>
                </div>
            </li>
            <li>
            	<div id="title4" onclick=subMenu(4) class="menu_right_close">比 分</div>
                <div id="td4" style="display:none;">
                    <a target="main" href="${ctx}/bet/single/fsdgOdds.html">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/single/togetherBuy.html?betCategory=62">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/single/fshmOdds.html">发 起 合 买</a>
                </div>
            </li>
            <li>
            	<div id="title5" onclick=subMenu(5) class="menu_right_close">半 全 场</div>
                <div id="td5" style="display:none;">
                    <a target="main" href="${ctx}/bet/single/fsdgHalfComplete.html">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/single/togetherBuy.html?betCategory=61">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/single/fshmHalfComplete.html">发 起 合 买</a>
                </div>
            </li>
        </div>
        <div id="main8" onclick=expandIt(8) class="menu_left_open"><em><a href="#">玩法» </a></em>胜负14场</div>
        <div id="sub8">
        <ul>
            <li>
                <div id="title6" onclick=subMenu(6) class="menu_right_close">10002期<span>（当前期）</span></div>
                <div id="td6" style="display:none;">
                    <a target="main" href="${ctx}/bet/windraw/winDraw14Display.html?phaseId=588">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/windraw/togetherBuy.html?phaseId=588">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/fqhm/index.html?phaseId=588&type=6">发 起 合 买</a>
                </div>
            </li>
            <li>
                <div id="title7" onclick=subMenu(7) class="menu_right_close">10003期 （预售期）</div>
                <div id="td7" style="display:none;">
                    <a target="main" href="${ctx}/bet/windraw/winDraw14Display.html?phaseId=587">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/windraw/togetherBuy.html?phaseId=587">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/fqhm/index.html?phaseId=587&type=6">发 起 合 买</a>
                </div>
            </li>
        </ul>
        </div>
        <div id="main9" onclick=expandIt(9) class="menu_left_open"><em><a href="#">玩法» </a></em>任选9场</div>
        <div id="sub9">
        <ul>
            <li>
                <div id="title8" onclick=subMenu(8) class="menu_right_close">10002期<span>（当前期）</span></div>
                <div id="td8" style="display:none;">
                    <a target="main" href="${ctx}/bet/optional/duplexShoppingOptiona19Display.html?phaseId=588">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/optional/togetherBuy.html?phaseId=588">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/fqhm/index.html?phaseId=588&type=7">发 起 合 买</a>
                </div>
            </li>
            <li>
                <div id="title9" onclick=subMenu(9) class="menu_right_close">10003期 （预售期）</div>
                <div id="td9" style="display:none;">
                    <a target="main" href="${ctx}/bet/optional/duplexShoppingOptiona19Display.html?phaseId=587">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/optional/togetherBuy.html?phaseId=587">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/fqhm/index.html?phaseId=587&type=7">发 起 合 买</a>
                </div>
            </li>
        </ul>
        </div>
        <div id="main10" onclick=expandIt(10) class="menu_left_open"><em><a href="#">玩法» </a></em>6场半全场</div>
        <div id="sub10">
        <ul>
            <li>
                <div id="title10" onclick=subMenu(10) class="menu_right_close">10002期<span>（当前期）</span></div>
                <div id="td10" style="display:none;">
                    <a target="main" href="${ctx}/bet/halfcomp/duplexShoppingHalfComplete6Display.html?phaseId=643">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/halfcomp/togetherBuy.html?phaseId=643">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/fqhm/index.html?phaseId=643&type=8">发 起 合 买</a>
                </div>
            </li>
            <li>
                <div id="title11" onclick=subMenu(11) class="menu_right_close">10003期 （预售期）</div>
                <div id="td11" style="display:none;">
                    <a target="main" href="${ctx}/bet/halfcomp/duplexShoppingHalfComplete6Display.html?phaseId=644">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/halfcomp/togetherBuy.html?phaseId=644">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/fqhm/index.html?phaseId=644&type=8">发 起 合 买</a>
                </div>
            </li>
        </ul>
        </div>
        <div id="main11" onclick=expandIt(11) class="menu_left_open"><em><a href="#">玩法»</a></em>4场进球</div>
        <div id="sub11">
        <ul>
            <li>
                <div id="title12" onclick=subMenu(12) class="menu_right_close">10002期<span>（当前期）</span></div>
                <div id="td12" style="display:none;">
                    <a target="main" href="${ctx}/bet/goal/duplexShoppingGoal4Display.html?phaseId=761">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/goal/togetherBuy.html?phaseId=761">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/fqhm/index.html?phaseId=761&type=9">发 起 合 买</a>
                </div>
            </li>
            <li>
                <div id="title13" onclick=subMenu(13) class="menu_right_close">10003期 （预售期）</div>
                <div id="td13" style="display:none;">
                    <a target="main" href="${ctx}/bet/goal/duplexShoppingGoal4Display.html?phaseId=802">代 购 投 注</a>
                    <a target="main" href="${ctx}/bet/goal/togetherBuy.html?phaseId=802">参 与 合 买</a>
                    <a target="main" href="${ctx}/bet/fqhm/index.html?phaseId=802&type=9">发 起 合 买</a>
                </div>
            </li>
        </ul>
        </div>
    </div>
</div>
</td>
</tr>
</table>
</div>
</body>
</html>
