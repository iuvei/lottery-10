<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv=Pragma content=no-cache>
<meta http-equiv=Cache-Control content=no-cache>
<meta http-equiv=Expires content=0>
<title>天彩网</title>
<script type='text/javascript'>
	var rootPath ="${ctx}/";
</script>
<link type="text/css" rel="stylesheet" href="${ctx}/css/common/public.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/common/default.css" />
<script type="text/javascript">
function showTab(total, tabid, id)
{
	for (var i = 0; i < total; i++)
	{
		var nav = document.getElementById('nav'+tabid+'_'+i);
		var showTab = document.getElementById('showTab'+tabid+'_'+i);
		if (i == id)
		{
			nav.className = 'tab_on';
			showTab.style.display = "";
		}
		else
		{
			nav.className = 'tab_off';
			showTab.style.display = "none";
		}
	}
}
</script>
</head>
<body>
<%@ include file="/common/header.jsp"%>
<!--Start Body-->
<div id="body">
	<div class="w_232 fl">
    	<div class="left_top m_5">
        	<div>
            	<a href="">免费注册</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
                <a href="">账户充值</a>
            </div>
            <ul>
            	<li><a href="#">如何注册</a> | <a href="#">如何充值</a> | <a href="#">如何购彩</a></li>
            	<li style="border-bottom:none;"><a href="#">如何合买</a> | <a href="#">如何派奖</a> | <a href="#">如何提现</a></li>
            </ul>
        </div>
        <h2>足彩开售中</h2>
        <table cellpadding="0" cellspacing="0" class="table_1 m_5">
        <tr class="h1"><td>彩种</td><td>期号</td><td>截止时间</td><td>购买</td></tr>
        <tr><td>胜负彩</td><td>091207</td><td>12-10</td><td><span>买</span></td></tr>
        <tr><td>胜负彩</td><td>091207</td><td>12-10</td><td><span>买</span></td></tr>
        <tr><td>胜负彩</td><td>091207</td><td>12-10</td><td><span>买</span></td></tr>
        <tr><td>胜负彩</td><td>091207</td><td>12-10</td><td><span>买</span></td></tr>
        <tr><td>胜负彩</td><td>091207</td><td>12-10</td><td><span>买</span></td></tr>
        <tr><td>胜负彩</td><td>091207</td><td>12-10</td><td><span>买</span></td></tr>
        <tr><td>胜负彩</td><td>091207</td><td>12-10</td><td><span>买</span></td></tr>
        <tr><td>胜负彩</td><td>091207</td><td>12-10</td><td><span>买</span></td></tr>
        <tr><td>胜负彩</td><td>091207</td><td>12-10</td><td><span>买</span></td></tr>
        <tr><td>胜负彩</td><td>091207</td><td>12-10</td><td><span>买</span></td></tr>
        </table>
        <h2>最新开奖结果</h2>
        <div class="left_table pt5 m_5">
        <dl>
        	<dt><span>09105期 <a href="#">玩法介绍</a></span><b>胜负彩</b></dt>
            <dd><span>0033331100130</span></dd>
            <dd>一等奖：0注 0元</dd>
            <dd>二等奖：10注 10000元</dd>
            <dd>三等奖：1注 20元</dd>
        </dl>
        <dl>
        	<dt><span>09105期 <a href="#">玩法介绍</a></span><b>4场进球彩</b></dt>
            <dd><span>0033331100130</span></dd>
            <dd>一等奖：0注 0元</dd>
            <dd>二等奖：10注 10000元</dd>
            <dd>三等奖：1注 20元</dd>
        </dl>
        <dl>
        	<dt><span>09105期 <a href="#">玩法介绍</a></span><b>6场半全场</b></dt>
            <dd><span>0033331100130</span></dd>
            <dd>一等奖：0注 0元</dd>
            <dd>二等奖：10注 10000元</dd>
            <dd>三等奖：1注 20元</dd>
        </dl>
        </div>
        <h2>本站最新中奖</h2>
        <table cellpadding="0" cellspacing="0" class="table_2 m_5">
        <tr class="h1"><td>用户名</td><td>彩种</td><td>旗号</td><td>中奖金额</td></tr>
        <tr class="bg"><td>bear</td><td>11选5</td><td>12312</td><td>20元</td></tr>
        <tr><td>bear</td><td>11选5</td><td>12312</td><td>20元</td></tr>
        <tr class="bg"><td>bear</td><td>11选5</td><td>12312</td><td>20元</td></tr>
        <tr><td>bear</td><td>11选5</td><td>12312</td><td>20元</td></tr>
        <tr class="bg"><td>bear</td><td>11选5</td><td>12312</td><td>20元</td></tr>
        <tr><td>bear</td><td>11选5</td><td>12312</td><td>20元</td></tr>
        <tr class="bg"><td>bear</td><td>11选5</td><td>12312</td><td>20元</td></tr>
        <tr><td>bear</td><td>11选5</td><td>12312</td><td>20元</td></tr>
        <tr class="bg"><td>bear</td><td>11选5</td><td>12312</td><td>20元</td></tr>
        <tr><td>bear</td><td>11选5</td><td>12312</td><td>20元</td></tr>
        </table>
        
        <!--start 总排行榜-->
        <h3>总排行榜</h3>
        <div class="tab">
            <div class="tab_on" onClick="showTab(3, 1, 0)" id="nav1_0"><div><p>胜负14场</p></div></div>
            <div class="tab_off" onClick="showTab(3, 1, 1)" id="nav1_1"><div><p>任选9</p></div></div>
            <div class="tab_off" onClick="showTab(3, 1, 2)" id="nav1_2"><div><p>单场</p></div></div>
        </div>
        <div id="showTab1_0">
            <!--start 胜负14场-->
            <div class="num">
            <table cellpadding="1" cellspacing="1" class="table_3 m_5">
            <tr><td>排名</td><td class="bg">发起人</td><td class="bg">人气</td><td class="bg">当前方案</td><td class="bg">跟单</td></tr>
            <tr><td class="nor">1</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">2</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">3</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">4</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">5</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">6</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">7</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">8</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">9</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">10</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            </table>
            </div>
            <!--end 胜负14场-->
        </div>
        <div id="showTab1_1" style="display:none;">
            <!--start 任选9-->
            <div class="num">
            <table cellpadding="1" cellspacing="1" class="table_3 m_5">
            <tr><td>排名</td><td class="bg">发起人</td><td class="bg">人气</td><td class="bg">当前方案</td><td class="bg">跟单</td></tr>
            <tr><td class="nor">1</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">2</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">3</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">4</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">5</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">6</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">7</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">8</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">9</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">10</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            </table>
            </div>
            <!--end 任选9-->
        </div>
        <div id="showTab1_2" style="display:none;">
            <!--start 单场-->
            <div class="num">
            <table cellpadding="1" cellspacing="1" class="table_3 m_5">
            <tr><td>排名</td><td class="bg">发起人</td><td class="bg">人气</td><td class="bg">当前方案</td><td class="bg">跟单</td></tr>
            <tr><td class="nor">1</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">2</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">3</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">4</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">5</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">6</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">7</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">8</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">9</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">10</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            </table>
            </div>
            <!--end 单场-->
        </div>
        <!--end 总排行榜-->
        
        <!--start 合买排行榜-->
        <h3>合买排行榜</h3>
        <div class="tab">
            <div class="tab_on" onClick="showTab(3, 2, 0)" id="nav2_0"><div><p>胜负14场</p></div></div>
            <div class="tab_off" onClick="showTab(3, 2, 1)" id="nav2_1"><div><p>任选9</p></div></div>
            <div class="tab_off" onClick="showTab(3, 2, 2)" id="nav2_2"><div><p>单场</p></div></div>
        </div>
        <div id="showTab2_0">
            <!--start 胜负14场-->
            <div class="num">
            <table cellpadding="1" cellspacing="1" class="table_3 m_5">
            <tr><td>排名</td><td class="bg">发起人</td><td class="bg">人气</td><td class="bg">当前方案</td><td class="bg">跟单</td></tr>
            <tr><td class="nor">1</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">2</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">3</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">4</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">5</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">6</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">7</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">8</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">9</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">10</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            </table>
            </div>
            <!--end 胜负14场-->
        </div>
        <div id="showTab2_1" style="display:none;">
            <!--start 任选9-->
            <div class="num">
            <table cellpadding="1" cellspacing="1" class="table_3 m_5">
            <tr><td>排名</td><td class="bg">发起人</td><td class="bg">人气</td><td class="bg">当前方案</td><td class="bg">跟单</td></tr>
            <tr><td class="nor">1</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">2</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">3</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">4</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">5</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">6</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">7</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">8</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">9</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">10</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            </table>
            </div>
            <!--end 任选9-->
        </div>
        <div id="showTab2_2" style="display:none;">
            <!--start 单场-->
            <div class="num">
            <table cellpadding="1" cellspacing="1" class="table_3 m_5">
            <tr><td>排名</td><td class="bg">发起人</td><td class="bg">人气</td><td class="bg">当前方案</td><td class="bg">跟单</td></tr>
            <tr><td class="nor">1</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">2</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">3</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">4</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">5</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">6</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">7</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">8</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            <tr><td class="nor">9</td><td>bear</td><td>1777</td><td>查看</td><td class="nor"><span>定制</span></td></tr>
            <tr><td class="nor">10</td><td class="bg">bear</td><td class="bg">1777</td><td class="bg">查看</td><td class="bg nor"><span>定制</span></td></tr>
            </table>
            </div>
            <!--end 单场-->
        </div>
        <!--end 合买排行榜-->
        
        <!--start 打擂排行-->
        <h3>打擂排行</h3>
        <div class="tab">
            <div class="tab_on" onClick="showTab(3, 3, 0)" id="nav3_0"><div><p>胜负14场</p></div></div>
            <div class="tab_off" onClick="showTab(3, 3, 1)" id="nav3_1"><div><p>任选9</p></div></div>
            <div class="tab_off" onClick="showTab(3, 3, 2)" id="nav3_2"><div><p>单场</p></div></div>
        </div>
        <div id="showTab3_0">
            <!--start 胜负14场-->
            <div class="num">
            <table cellpadding="1" cellspacing="1" class="table_3 m_5">
            <tr><td>排名</td><td class="bg">用户名</td><td class="bg">上期胜率</td><td class="bg">本期方案</td></tr>
            <tr><td class="nor">1</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">2</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            <tr><td class="nor">3</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">4</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            <tr><td class="nor">5</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">6</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            <tr><td class="nor">7</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">8</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            <tr><td class="nor">9</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">10</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            </table>
            </div>
            <!--end 胜负14场-->
        </div>
        <div id="showTab3_1" style="display:none;">
            <!--start 任选9-->
            <div class="num">
            <table cellpadding="1" cellspacing="1" class="table_3 m_5">
            <tr><td>排名</td><td class="bg">用户名</td><td class="bg">上期胜率</td><td class="bg">本期方案</td></tr>
            <tr><td class="nor">1</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">2</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            <tr><td class="nor">3</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">4</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            <tr><td class="nor">5</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">6</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            <tr><td class="nor">7</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">8</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            <tr><td class="nor">9</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">10</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            </table>
            </div>
            <!--end 任选9-->
        </div>
        <div id="showTab3_2" style="display:none;">
            <!--start 单场-->
            <div class="num">
            <table cellpadding="1" cellspacing="1" class="table_3 m_5">
            <tr><td>排名</td><td class="bg">用户名</td><td class="bg">上期胜率</td><td class="bg">本期方案</td></tr>
            <tr><td class="nor">1</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">2</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            <tr><td class="nor">3</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">4</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            <tr><td class="nor">5</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">6</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            <tr><td class="nor">7</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">8</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            <tr><td class="nor">9</td><td>天盛</td><td>98%</td><td class="nor">查看</td></tr>
            <tr><td class="nor">10</td><td class="bg">天盛</td><td class="bg">98%</td><td class="bg nor">查看</td></tr>
            </table>
            </div>
            <!--end 单场-->
        </div>
        <!--end 打擂排行-->
        
        <!--start 商城积分兑换-->
        <h2>商城积分兑换</h2>
        <table cellpadding="0" cellspacing="0" class="table_4 m_5">
        <tr><td>兑换商品</td><td>所需积分</td><td>操作</td></tr>
        <tr><td><img class="img" src="${ctx}/images/default/del_1.gif" /></td><td>123123</td><td><img src="${ctx}/images/default/shopping_1.gif" alt="兑换" /></tr>
        <tr><td><img class="img" src="${ctx}/images/default/del_1.gif" /></td><td>123123</td><td><img src="${ctx}/images/default/shopping_1.gif" alt="兑换" /></tr>
        <tr><td><img class="img" src="${ctx}/images/default/del_1.gif" /></td><td>123123</td><td><img src="${ctx}/images/default/shopping_1.gif" alt="兑换" /></tr>
        <tr><td><img class="img" src="${ctx}/images/default/del_1.gif" /></td><td>123123</td><td><img src="${ctx}/images/default/shopping_1.gif" alt="兑换" /></tr>
        <tr><td><img class="img" src="${ctx}/images/default/del_1.gif" /></td><td>123123</td><td><img src="${ctx}/images/default/shopping_1.gif" alt="兑换" /></tr>
        </table>
        <!--end 商城积分兑换-->
    </div>
    
    <div class="w_743 fr">
        <div class="slide"><img src="${ctx}/images/del/slide.gif" /></div>
        <div class="top_news">
            <h2><div>冠军杯周中开赛是  米兰双雄开场艰难</div></h2>
            <ul>
                <li><a href=""><strong>[合买足球]</strong>  冠军杯开中开赛  米兰双雄开场艰难</a></li>
                <li><a href=""><strong>[合买足球]</strong>  冠军杯开中开赛  米兰双雄开场艰难</a></li>
                <li><a href=""><strong>[合买足球]</strong>  冠军杯开中开赛  米兰双雄开场艰难</a></li>
            </ul>
            <h2><div>冠军杯周中开赛是  米兰双雄开场艰难</div></h2>
            <ul>
                <li><a href=""><strong>[合买足球]</strong>  冠军杯开中开赛  米兰双雄开场艰难</a></li>
                <li><a href=""><strong>[合买足球]</strong>  冠军杯开中开赛  米兰双雄开场艰难</a></li>
                <li><a href=""><strong>[合买足球]</strong>  冠军杯开中开赛  米兰双雄开场艰难</a></li>
            </ul>
        </div>
    </div>
    <div class="clearfix"></div>
        
    <div class="w_488 fl ml5">
    	<div class="index">
        </div>
    	<div class="mid_2 m_5">
        	<h2>合买方案</h2>
            <table cellpadding="0" cellspacing="0">
            <tr>
            	<td><select><option>选择彩种</option></select></td>
            	<td><select><option>方案金额</option></select></td>
            	<td><select><option>进度排行</option></select></td>
            	<td class="bg"><input type="text" class="text" /><input type="button" value=" " class="search" /></td>
            </tr>
            </table>
            <div>热门：<a href="#">巴菲特玩足彩</a> | <a href="#">莱万特</a> | <a href="#">巴菲特玩足彩</a> | <a href="#">莱万特</a> | <a href="#">巴菲特玩足彩</a> | <a href="#">莱万特</a></div>
        </div>
    	
    	<div class="mid_1">
        	<div class="h1">精选方案</div>
            <div class="tab">
                <div class="tab_on" onClick="showTab(3, 4, 0)" id="nav4_0"><div><p>胜负彩</p></div></div>
                <div class="tab_off" onClick="showTab(3, 4, 1)" id="nav4_1"><div><p>任选9场</p></div></div>
                <div class="tab_off" onClick="showTab(3, 4, 2)" id="nav4_2"><div><p>足球单场</p></div></div>
            </div>
        </div>
        <div id="showTab4_0">
    	<table cellpadding="1" cellspacing="1" class="table_5">
        <tr><td>发起人</td><td>战绩</td><td>总金额</td><td>剩余份数</td><td>进度</td><td>参与</td></tr>
        <tr class="bg">
        	<td>天盛</td>
            <td><img src="${ctx}/images/default/p-x_1.gif" /><img src="${ctx}/images/default/p-x_2.gif" /><img src="${ctx}/images/default/p-z_1.gif" /><img src="${ctx}/images/default/p-z_2.gif" /></td>
            <td>￥1888</td><td>11</td><td>95%</td><td></td>
        </tr>
        <tr>
        	<td>天盛</td>
            <td><img src="${ctx}/images/default/p-x_1.gif" /><img src="${ctx}/images/default/p-x_2.gif" /><img src="${ctx}/images/default/p-z_1.gif" /><img src="${ctx}/images/default/p-z_2.gif" /></td>
            <td>￥1888</td><td>11</td><td>95%</td><td></td>
        </tr>
        <tr class="bg">
        	<td>天盛</td>
            <td><img src="${ctx}/images/default/p-x_1.gif" /><img src="${ctx}/images/default/p-x_2.gif" /><img src="${ctx}/images/default/p-z_1.gif" /><img src="${ctx}/images/default/p-z_2.gif" /></td>
            <td>￥1888</td><td>11</td><td>95%</td><td></td>
        </tr>
        <tr>
        	<td>天盛</td>
            <td><img src="${ctx}/images/default/p-x_1.gif" /><img src="${ctx}/images/default/p-x_2.gif" /><img src="${ctx}/images/default/p-z_1.gif" /><img src="${ctx}/images/default/p-z_2.gif" /></td>
            <td>￥1888</td><td>11</td><td>95%</td><td></td>
        </tr>
        <tr class="bg">
        	<td>天盛</td>
            <td><img src="${ctx}/images/default/p-x_1.gif" /><img src="${ctx}/images/default/p-x_2.gif" /><img src="${ctx}/images/default/p-z_1.gif" /><img src="${ctx}/images/default/p-z_2.gif" /></td>
            <td>￥1888</td><td>11</td><td>95%</td><td></td>
        </tr>
        <tr>
        	<td>天盛</td>
            <td><img src="${ctx}/images/default/p-x_1.gif" /><img src="${ctx}/images/default/p-x_2.gif" /><img src="${ctx}/images/default/p-z_1.gif" /><img src="${ctx}/images/default/p-z_2.gif" /></td>
            <td>￥1888</td><td>11</td><td>95%</td><td></td>
        </tr>
        <tr class="bg">
        	<td>天盛</td>
            <td><img src="${ctx}/images/default/p-x_1.gif" /><img src="${ctx}/images/default/p-x_2.gif" /><img src="${ctx}/images/default/p-z_1.gif" /><img src="${ctx}/images/default/p-z_2.gif" /></td>
            <td>￥1888</td><td>11</td><td>95%</td><td></td>
        </tr>
        <tr>
        	<td>天盛</td>
            <td><img src="${ctx}/images/default/p-x_1.gif" /><img src="${ctx}/images/default/p-x_2.gif" /><img src="${ctx}/images/default/p-z_1.gif" /><img src="${ctx}/images/default/p-z_2.gif" /></td>
            <td>￥1888</td><td>11</td><td>95%</td><td></td>
        </tr>
        <tr class="bg">
        	<td>天盛</td>
            <td><img src="${ctx}/images/default/p-x_1.gif" /><img src="${ctx}/images/default/p-x_2.gif" /><img src="${ctx}/images/default/p-z_1.gif" /><img src="${ctx}/images/default/p-z_2.gif" /></td>
            <td>￥1888</td><td>11</td><td>95%</td><td></td>
        </tr>
        <tr>
        	<td>天盛</td>
            <td><img src="${ctx}/images/default/p-x_1.gif" /><img src="${ctx}/images/default/p-x_2.gif" /><img src="${ctx}/images/default/p-z_1.gif" /><img src="${ctx}/images/default/p-z_2.gif" /></td>
            <td>￥1888</td><td>11</td><td>95%</td><td></td>
        </tr>
        </table>
        </div>
        <div id="showTab4_1" style="display:none;">
    	<table cellpadding="1" cellspacing="1" class="table_5">
        <tr><td>发起人</td><td>战绩</td><td>总金额</td><td>剩余份数</td><td>进度</td><td>参与</td></tr>
        <tr class="bg">
        	<td>天盛</td>
            <td><img src="${ctx}/images/default/p-x_1.gif" /><img src="${ctx}/images/default/p-x_2.gif" /><img src="${ctx}/images/default/p-z_1.gif" /><img src="${ctx}/images/default/p-z_2.gif" /></td>
            <td>￥1888</td><td>11</td><td>95%</td><td></td>
        </tr>
        </table>
        </div>
        <div id="showTab4_2" style="display:none;">
    	<table cellpadding="1" cellspacing="1" class="table_5">
        <tr><td>发起人</td><td>战绩</td><td>总金额</td><td>剩余份数</td><td>进度</td><td>参与</td></tr>
        <tr class="bg">
        	<td>天盛</td>
            <td><img src="${ctx}/images/default/p-x_1.gif" /><img src="${ctx}/images/default/p-x_2.gif" /><img src="${ctx}/images/default/p-z_1.gif" /><img src="${ctx}/images/default/p-z_2.gif" /></td>
            <td>￥1888</td><td>11</td><td>95%</td><td></td>
        </tr>
        </table>
        </div>
        
    </div>
    
    <div class="w_250 fr">
    	<h2>足彩玩法及名词解释</h2>
        <div class="border pt5 m_5">
        <ul class="td_1">
        	<li>足球单场 | 胜负彩 | 进球彩 | 任九</li>
            <li>半全场 | 单赛事 | 历史数据</li>
            <li>中奖查询 | 代购 | 合买</li>
        </ul>
        </div>
        <h2><span><a href="#">更多>></a></span>足彩资讯</h2>
        <div class="border" style="border-bottom:none;">
            <div class="tab" style="border:none;">
                <div class="tab_on" onClick="showTab(2, 6, 0)" id="nav6_0"><div><p>专家预测</p></div></div>
                <div class="tab_off" onClick="showTab(2, 6, 1)" id="nav6_1"><div><p>足彩新闻</p></div></div>
            </div>
            <div id="showTab6_0">
                <div class="list_img">
                <ul>
                    <li><a href="#"><img src="${ctx}/images/default/del_4.gif" /></a><div><a href="#">专家预测银球</a></div></li>
                    <li><a href="#"><img src="${ctx}/images/default/del_4.gif" /></a><div><a href="#">专家预测银球</a></div></li>
                </ul><div class="clear"></div>
                </div>
                <div class="list_news">
                <ul>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                </ul>
                </div>
            </div>
            <div id="showTab6_1" style="display:none;">
                <div class="list_img">
                <ul>
                    <li><a href="#"><img src="${ctx}/images/default/del_4.gif" /></a><div><a href="#">专家预测银球</a></div></li>
                    <li><a href="#"><img src="${ctx}/images/default/del_4.gif" /></a><div><a href="#">专家预测银球</a></div></li>
                </ul><div class="clear"></div>
                </div>
                <div class="list_news">
                <ul>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                    <li><span>10-12</span><a href="#">阿道夫哈尽快落实党风哈士大</a></li>
                </ul>
                </div>
            </div>
        </div>
        <table cellpadding="1" cellspacing="1" class="table_8 m_5">
        <tr><td><a href="#">胜负14场预测</a></td><td><a href="#">任选9场预测</a></td><td><a href="#">任选9场预测</a></td></tr>
        </table>
    </div>
    <div class="clearfix"></div>
    
    <div class="w_488 fl ml5">
    	<div class="expert">
        	<div class="expert_title">
                <h2><a href="#">更多>></a>名家足球推荐</h2>
                <div class="expert_list">
                <ul>
                    <li>
                        <img src="${ctx}/images/del/img_3.gif" /><samp>[唐克]</samp><br />云达不莱梅、波仔、德黑 民间高手：黑白无间、签、德黑手：黑白无间<br />
                        <span><a href=""><img src="${ctx}/images/default/button_goumai.gif" /></a></span>
                    </li>
                    <li>
                        <img src="${ctx}/images/del/img_3.gif" /><samp>[唐克]</samp><br />云达不莱梅、波仔、德黑 民间高手：黑白无间、签、德黑手：黑白无间<br />
                        <span><a href=""><img src="${ctx}/images/default/button_goumai.gif" /></a></span>
                    </li>
                    <li>
                        <img src="${ctx}/images/del/img_3.gif" /><samp>[唐克]</samp><br />云达不莱梅、波仔、德黑 民间高手：黑白无间、签、德黑手：黑白无间<br />
                        <span><a href=""><img src="${ctx}/images/default/button_goumai.gif" /></a></span>
                    </li>
                    <li>
                        <img src="${ctx}/images/del/img_3.gif" /><samp>[唐克]</samp><br />云达不莱梅、波仔、德黑 民间高手：黑白无间、签、德黑手：黑白无间<br />
                        <span><a href=""><img src="${ctx}/images/default/button_goumai.gif" /></a></span>
                    </li>
                    <li>
                        <img src="${ctx}/images/del/img_3.gif" /><samp>[唐克]</samp><br />云达不莱梅、波仔、德黑 民间高手：黑白无间、签、德黑手：黑白无间<br />
                        <span><a href=""><img src="${ctx}/images/default/button_goumai.gif" /></a></span>
                    </li>
                    <li>
                        <img src="${ctx}/images/del/img_3.gif" /><samp>[唐克]</samp><br />云达不莱梅、波仔、德黑 民间高手：黑白无间、签、德黑手：黑白无间<br />
                        <span><a href=""><img src="${ctx}/images/default/button_goumai.gif" /></a></span>
                    </li>
                    <li>
                        <img src="${ctx}/images/del/img_3.gif" /><samp>[唐克]</samp><br />云达不莱梅、波仔、德黑 民间高手：黑白无间、签、德黑手：黑白无间<br />
                        <span><a href=""><img src="${ctx}/images/default/button_goumai.gif" /></a></span>
                    </li>
                    <li>
                        <img src="${ctx}/images/del/img_3.gif" /><samp>[唐克]</samp><br />云达不莱梅、波仔、德黑 民间高手：黑白无间、签、德黑手：黑白无间<br />
                        <span><a href=""><img src="${ctx}/images/default/button_goumai.gif" /></a></span>
                    </li>
                </ul>
                </div>
           	</div>
        </div>
    	<div class="mid_3"><a href="#">更多>></a>民间高手推荐</div>
        <table cellpadding="1" cellspacing="1" class="table_7 m_5">
        <tr class="h1"><td>联赛</td><td>开赛时间<br />主客对阵</td><td>推荐人<br />级别</td><td>最近5场 胜率</td><td>保证金</td><td>价格</td><td>操作</td></tr>
        <tr>
        	<td>英超</td>
            <td>2009-12-22 19:00:00<br />嘉辛塔 vs 施华师U20 </td>
            <td>黑白无间<br /><img src="${ctx}/images/default/rank.gif" alt="推荐等级" /></td>
            <td>【82.3%】</td>
            <td>¥12000</td>
            <td>¥10</td>
            <td><a href=""><img src="${ctx}/images/default/shopping_1.gif" alt="购买" /></a></td>
        </tr>
        <tr>
        	<td>英超</td>
            <td>2009-12-22 19:00:00<br />嘉辛塔 vs 施华师U20 </td>
            <td>黑白无间<br /><img src="${ctx}/images/default/rank.gif" alt="推荐等级" /></td>
            <td>【82.3%】</td>
            <td>¥12000</td>
            <td>¥10</td>
            <td><a href=""><img src="${ctx}/images/default/shopping_1.gif" alt="购买" /></a></td>
        </tr>
        <tr>
        	<td>英超</td>
            <td>2009-12-22 19:00:00<br />嘉辛塔 vs 施华师U20 </td>
            <td>黑白无间<br /><img src="${ctx}/images/default/rank.gif" alt="推荐等级" /></td>
            <td>【82.3%】</td>
            <td>¥12000</td>
            <td>¥10</td>
            <td><a href=""><img src="${ctx}/images/default/shopping_1.gif" alt="购买" /></a></td>
        </tr>
        <tr>
        	<td>英超</td>
            <td>2009-12-22 19:00:00<br />嘉辛塔 vs 施华师U20 </td>
            <td>黑白无间<br /><img src="${ctx}/images/default/rank.gif" alt="推荐等级" /></td>
            <td>【82.3%】</td>
            <td>¥12000</td>
            <td>¥10</td>
            <td><a href=""><img src="${ctx}/images/default/shopping_1.gif" alt="购买" /></a></td>
        </tr>
        <tr>
        	<td>英超</td>
            <td>2009-12-22 19:00:00<br />嘉辛塔 vs 施华师U20 </td>
            <td>黑白无间<br /><img src="${ctx}/images/default/rank.gif" alt="推荐等级" /></td>
            <td>【82.3%】</td>
            <td>¥12000</td>
            <td>¥10</td>
            <td><a href=""><img src="${ctx}/images/default/shopping_1.gif" alt="购买" /></a></td>
        </tr>
        </table>
    </div>
    
    <div class="w_250 fr">
    	<h2><span><a href="#">更多>></a></span>联赛资料</h2>
        <div class="border m_5">
            <div class="logo_list">
            <ul>
                <li><img src="${ctx}/images/default/del_3.gif" /><div>英超</div></li>
                <li><img src="${ctx}/images/default/del_3.gif" /><div>英超</div></li>
                <li><img src="${ctx}/images/default/del_3.gif" /><div>英超</div></li>
                <li><img src="${ctx}/images/default/del_3.gif" /><div>英超</div></li>
                <li><img src="${ctx}/images/default/del_3.gif" /><div>英超</div></li>
                <li><img src="${ctx}/images/default/del_3.gif" /><div>英超</div></li>
                <li><img src="${ctx}/images/default/del_3.gif" /><div>英超</div></li>
                <li><img src="${ctx}/images/default/del_3.gif" /><div>英超</div></li>
                <li><img src="${ctx}/images/default/del_3.gif" /><div>英超</div></li>
            </ul><div class="clear"></div>
            </div>
            <table cellpadding="0" cellspacing="0" width="100%">
            <tr>
            	<td align="center" height="42"><select><option>&nbsp;&nbsp;区 域&nbsp;&nbsp;</option></select></td>
            	<td align="center" height="42"><select><option>&nbsp;&nbsp;国 家&nbsp;&nbsp;</option></select></td>
            </tr>
            <tr>
            	<td align="center" height="42"><select><option>&nbsp;&nbsp;联 赛&nbsp;&nbsp;</option></select></td>
            	<td align="center" height="42"><select><option>&nbsp;&nbsp;赛 季&nbsp;&nbsp;</option></select></td>
            </tr>
            </table>
        </div>
    	<h2>明间高手排行(月)</h2>
        <div class="border m_5" style="padding-bottom:9px;">
        	<table cellpadding="0" cellspacing="0" class="table_6">
            <tr><td>排名</td><td>用户名</td><td>场次</td><td>胜率</td><td>操作</td></tr>
            <tr><td>&nbsp;</td><td>bear</td><td>11</td><td>80%</td><td><img src="${ctx}/images/default/gou.gif" alt="购买" /></td></tr>
            <tr><td>&nbsp;</td><td>bear</td><td>11</td><td>80%</td><td><img src="${ctx}/images/default/gou.gif" alt="购买" /></td></tr>
            <tr><td>&nbsp;</td><td>bear</td><td>11</td><td>80%</td><td><img src="${ctx}/images/default/gou.gif" alt="购买" /></td></tr>
            <tr><td>&nbsp;</td><td>bear</td><td>11</td><td>80%</td><td><img src="${ctx}/images/default/gou.gif" alt="购买" /></td></tr>
            <tr><td>&nbsp;</td><td>bear</td><td>11</td><td>80%</td><td><img src="${ctx}/images/default/gou.gif" alt="购买" /></td></tr>
            <tr><td>&nbsp;</td><td>bear</td><td>11</td><td>80%</td><td><img src="${ctx}/images/default/gou.gif" alt="购买" /></td></tr>
            <tr><td>&nbsp;</td><td>bear</td><td>11</td><td>80%</td><td><img src="${ctx}/images/default/gou.gif" alt="购买" /></td></tr>
            <tr><td>&nbsp;</td><td>bear</td><td>11</td><td>80%</td><td><img src="${ctx}/images/default/gou.gif" alt="购买" /></td></tr>
            <tr><td>&nbsp;</td><td>bear</td><td>11</td><td>80%</td><td><img src="${ctx}/images/default/gou.gif" alt="购买" /></td></tr>
            <tr><td>&nbsp;</td><td>bear</td><td>11</td><td>80%</td><td><img src="${ctx}/images/default/gou.gif" alt="购买" /></td></tr>
            </table>
        </div>
    </div>
    <div class="clearfix"></div>
    
    <div class="w_743 fr">
    	<div class="title_743">
        	<h2>足彩对阵</h2>
            <div class="tab">
                <div class="tab_on" onClick="showTab(4, 5, 0)" id="nav5_0"><div><p>胜负14场</p></div></div>
                <div class="tab_off" onClick="showTab(4, 5, 1)" id="nav5_1"><div><p>任选9场</p></div></div>
                <div class="tab_off" onClick="showTab(4, 5, 2)" id="nav5_2"><div><p>6场半全场</p></div></div>
                <div class="tab_off" onClick="showTab(4, 5, 3)" id="nav5_3"><div><p>进球彩</p></div></div>
            </div>
        </div>
       	<div id="showTab5_0">
        <!--start 胜负14场-->
        <div class="next_743">
            <div class="a"><a href="#">本月对阵</a></div>
            <div class="next">下一期</div>
            <div class="show">1210008期</div>
            <div class="pre">上一期</div>
        </div>
        <div class="td_743">
        	<table cellpadding="1" cellspacing="1" class="table_743_1">
            <tr class="h1"><td width="40"></td><td>对阵</td><td>开赛时间</td><td>数据分析</td><td>平均指数</td><td>代购合买</td></tr>
            <tr>
            	<td>[1]</td><td>巴塞 <span>1：0</span> 里斯本</td><td>12-17 02:00</td><td>分析 欧指 亚指</td><td>1.99 3.16 3.65</td>
            	<td>
					<em onclick="this.style.backgroundColor='#74b6ff'">3</em>
                    <em onclick="this.style.backgroundColor='#74b6ff'">1</em>
                    <em onclick="this.style.backgroundColor='#74b6ff'">0</em> [全]
            	</td>
            </tr>
            <tr>
            	<td>[2]</td><td class="bg">巴塞 <span>1：0</span> 里斯本</td><td class="bg">12-17 02:00</td><td class="bg">分析 欧指 亚指</td><td class="bg">1.99 3.16 3.65</td>
            	<td>
					<em onclick="this.style.backgroundColor='#74b6ff'">3</em>
                    <em onclick="this.style.backgroundColor='#74b6ff'">1</em>
                    <em onclick="this.style.backgroundColor='#74b6ff'">0</em> [全]
            	</td>
            </tr>
            <tr>
            	<td>[3]</td><td>巴塞 <span>1：0</span> 里斯本</td><td>12-17 02:00</td><td>分析 欧指 亚指</td><td>1.99 3.16 3.65</td>
            	<td>
					<em onclick="this.style.backgroundColor='#74b6ff'">3</em>
                    <em onclick="this.style.backgroundColor='#74b6ff'">1</em>
                    <em onclick="this.style.backgroundColor='#74b6ff'">0</em> [全]
            	</td>
            </tr>
            <tr>
            	<td>[4]</td><td class="bg">巴塞 <span>1：0</span> 里斯本</td><td class="bg">12-17 02:00</td><td class="bg">分析 欧指 亚指</td><td class="bg">1.99 3.16 3.65</td>
            	<td>
					<em onclick="this.style.backgroundColor='#74b6ff'">3</em>
                    <em onclick="this.style.backgroundColor='#74b6ff'">1</em>
                    <em onclick="this.style.backgroundColor='#74b6ff'">0</em> [全]
            	</td>
            </tr>
            <tr>
            	<td class="bg p_5" colspan="5">复式合买截止：<span>2009-12-16  22：50：00</span> 您选择了<span>0</span>注 ￥<span>0.00</span>元</td>
                <td class="bg p_5">
                	<img src="${ctx}/images/default/button_qingkong.gif" alt="清空" />
                	<img src="${ctx}/images/default/button_goumai.gif" alt="购买" />
                </td>
            </tr>
            </table>
        </div>
        <!--end 胜负14场-->
        </div>
        <div id="showTab5_1" style="display:none;">
        <!--start 任选9场-->
        <div class="next_743">
            <div class="a"><a href="#">本月对阵</a></div>
            <div class="next">下一期</div>
            <div class="show">1210008期</div>
            <div class="pre">上一期</div>
        </div>
        <div class="td_743">
        	<table cellpadding="1" cellspacing="1" class="table_743_1">
            <tr class="h1"><td width="40"></td><td>对阵</td><td>开赛时间</td><td>数据分析</td><td>平均指数</td><td>代购合买</td></tr>
            <tr>
            	<td>[1]</td><td>巴塞 <span>1：0</span> 里斯本</td><td>12-17 02:00</td><td>分析 欧指 亚指</td><td>1.99 3.16 3.65</td>
            	<td>
					<em onclick="this.style.backgroundColor='#74b6ff'">3</em>
                    <em onclick="this.style.backgroundColor='#74b6ff'">1</em>
                    <em onclick="this.style.backgroundColor='#74b6ff'">0</em> [全]
            	</td>
            </tr>
            <tr>
            	<td>[2]</td><td class="bg">巴塞 <span>1：0</span> 里斯本</td><td class="bg">12-17 02:00</td><td class="bg">分析 欧指 亚指</td><td class="bg">1.99 3.16 3.65</td>
            	<td>
					<em onclick="this.style.backgroundColor='#74b6ff'">3</em>
                    <em onclick="this.style.backgroundColor='#74b6ff'">1</em>
                    <em onclick="this.style.backgroundColor='#74b6ff'">0</em> [全]
            	</td>
            </tr>
            <tr>
            	<td class="bg p_5" colspan="5">复式合买截止：<span>2009-12-16  22：50：00</span> 您选择了<span>0</span>注 ￥<span>0.00</span>元</td>
                <td class="bg p_5">
                	<img src="${ctx}/images/default/button_qingkong.gif" alt="清空" />
                	<img src="${ctx}/images/default/button_goumai.gif" alt="购买" />
                </td>
            </tr>
            </table>
        </div>
        <!--end 任选9场-->
        </div>
        <div id="showTab5_2" style="display:none;">
        <!--start 6场半全场-->
        <div class="next_743">
            <div class="a"><a href="#">本月对阵</a></div>
            <div class="next">下一期</div>
            <div class="show">1210008期</div>
            <div class="pre">上一期</div>
        </div>
        <div class="td_743">
        	<table cellpadding="1" cellspacing="1" class="table_743_1">
            <tr class="h1"><td>场</td><td>赛事</td><td>对阵</td><td>开赛时间</td><td>平均赔率</td><td>数据分析</td><td>选号</td></tr>
            <tr>
                <td>[1]</td>
                <td>英超</td>
                <td>巴塞 <span>1：0</span> 里斯本</td>
                <td>12-17 10:00</td>
                <td>1.99 3.16 3.65</td>
                <td>分析 欧指 亚指</td>
                <td>
                    半场：<em onclick="this.style.backgroundColor='#74b6ff'">3</em><em onclick="this.style.backgroundColor='#74b6ff'">1</em><em onclick="this.style.backgroundColor='#74b6ff'">0</em> [全]<br />
                    全场：<em onclick="this.style.backgroundColor='#74b6ff'">3</em><em onclick="this.style.backgroundColor='#74b6ff'">1</em><em onclick="this.style.backgroundColor='#74b6ff'">0</em> [全]
                </td>
            </tr>
            <tr>
                <td>[2]</td>
                <td class="bg">英超</td>
                <td class="bg">巴塞 <span>1：0</span> 里斯本</td>
                <td class="bg">12-17 10:00</td>
                <td class="bg">1.99 3.16 3.65</td>
                <td class="bg">分析 欧指 亚指</td>
                <td>
                    半场：<em onclick="this.style.backgroundColor='#74b6ff'">3</em><em onclick="this.style.backgroundColor='#74b6ff'">1</em><em onclick="this.style.backgroundColor='#74b6ff'">0</em> [全]<br />
                    全场：<em onclick="this.style.backgroundColor='#74b6ff'">3</em><em onclick="this.style.backgroundColor='#74b6ff'">1</em><em onclick="this.style.backgroundColor='#74b6ff'">0</em> [全]
                </td>
            </tr>
            <tr>
            	<td class="bg p_5" colspan="6">复式合买截止：<span>2009-12-16  22：50：00</span> 您选择了<span>0</span>注 ￥<span>0.00</span>元</td>
                <td class="bg p_5">
                	<img src="${ctx}/images/default/button_qingkong.gif" alt="清空" />
                	<img src="${ctx}/images/default/button_goumai.gif" alt="购买" />
                </td>
            </tr>
            </table>
        </div>
        <!--end 6场半全场-->
        </div>
        <div id="showTab5_3" style="display:none;">
        <!--start 进球彩-->
        <div class="next_743">
            <div class="a"><a href="#">本月对阵</a></div>
            <div class="next">下一期</div>
            <div class="show">1210008期</div>
            <div class="pre">上一期</div>
        </div>
        <div class="td_743">
        	<table cellpadding="1" cellspacing="1" class="table_743_1">
            <tr class="h1"><td>场</td><td>赛事</td><td>对阵</td><td>开赛时间</td><td>平均赔率</td><td>数据分析</td><td>选号区</td></tr>
            <tr>
                <td>[1]</td>
                <td>英超</td>
                <td>巴塞 <span>1：0</span> 里斯本</td>
                <td>12-17 10:00</td>
                <td>1.99 3.16 3.65</td>
                <td>分析 欧指 亚指</td>
                <td><em onclick="this.style.backgroundColor='#74b6ff'">0</em><em onclick="this.style.backgroundColor='#74b6ff'">1</em><em onclick="this.style.backgroundColor='#74b6ff'">2</em><em onclick="this.style.backgroundColor='#74b6ff'">3</em> [全]</td>
            </tr>
            <tr>
                <td>[2]</td>
                <td class="bg">英超</td>
                <td class="bg">巴塞 <span>1：0</span> 里斯本</td>
                <td class="bg">12-17 10:00</td>
                <td class="bg">1.99 3.16 3.65</td>
                <td class="bg">分析 欧指 亚指</td>
                <td><em onclick="this.style.backgroundColor='#74b6ff'">0</em><em onclick="this.style.backgroundColor='#74b6ff'">1</em><em onclick="this.style.backgroundColor='#74b6ff'">2</em><em onclick="this.style.backgroundColor='#74b6ff'">3</em> [全]</td>
            </tr>
            <tr>
            	<td class="bg p_5" colspan="6">复式合买截止：<span>2009-12-16  22：50：00</span> 您选择了<span>0</span>注 ￥<span>0.00</span>元</td>
                <td class="bg p_5">
                	<img src="${ctx}/images/default/button_qingkong.gif" alt="清空" />
                	<img src="${ctx}/images/default/button_goumai.gif" alt="购买" />
                </td>
            </tr>
            </table>
        </div>
        <!--end 进球彩-->
        </div>
    </div>
    
    <div class="clear"></div>
    <div class="guide">
    	<div>
    	<ul>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        </ul>
    	<ul>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        </ul>
    	<ul>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        </ul>
    	<ul>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        </ul>
    	<ul>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        </ul>
    	<ul>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        </ul>
    	<ul>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        </ul>
    	<ul>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        	<li><a href="#" target="_blank">天彩首页</a></li>
        </ul>
        </div>
    </div>
    <div class="link">
    <ul>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    	<li><p><a href="#" target="_blank"><img src="${ctx}/images/default/del_2.gif" alt="" /></a></p><a href="#" target="_blank">天彩</a></li>
    </ul><div class="clear"></div>
    </div>
</div>
<!--End Body-->
<%@ include file="/common/footer.jsp"%>
</body>
</html>