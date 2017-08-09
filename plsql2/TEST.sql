-- Created on 2010-4-14 by ADMINISTRATOR 
declare 
  regTime  varchar2(100);--2注册时间
   attentedCnt  number;--3被关注次数
   attentCnt  number;--4他关注别人次数
   username  varchar2(100);--5他的用户名
   betMilitary  number; --6他的购彩战绩
   xinshuiMilitary  number; --7他的心水战绩
   king_cat_cur  space.List_CUR;--8如果她是金牌发起人，则返回对应彩种已经定制跟单的人数 格式  胜负彩14场;21
   spCnt  number;--9发起合买数
   spSuccess  varchar2(200);--10发单成功率
   canYuCnt   number;--11参与合买次数
   zjCnt  number;--12总中奖次数 
   pubXinshui  number;--13发布心水数 
   soldXinshuiCnt  number;--14销售心水人数
   last_visit_List  space.List_CUR;--15最近访客
   
begin
   space.findSpaceData(162,regTime,--2注册时间
   attentedCnt,--3被关注次数
   attentCnt,--4他关注别人次数
   username,--5他的用户名
   betMilitary, --6他的购彩战绩
   xinshuiMilitary, --7他的心水战绩
   king_cat_cur,--8如果她是金牌发起人，则返回对应彩种已经定制跟单的人数 格式  胜负彩14场;21
   spCnt,--9发起合买数
   spSuccess,--10发单成功率
   canYuCnt,--11参与合买次数
   zjCnt,--12总中奖次数 
   pubXinshui,--13发布心水数 
   soldXinshuiCnt,--14销售心水人数
   last_visit_List--15最近访客
  );
  
end;