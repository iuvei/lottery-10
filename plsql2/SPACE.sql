create or replace package body SPACE is
--本站热门心水推荐
procedure  findHotXinshui(
  HOT_XINSHUI out List_CUR
)
is
begin
open HOT_XINSHUI for  select
     t2.host_name,t2.guest_name,t1.xinshui_no,t1.price,t2.race_name,to_char(t2.start_time,'mm-dd hh24:mi') start_time,t1.tx_username,t1.ensure_money,
     t1.price,getWinRatio(t1.tx_user_id)  win_ratio,
     (select  count(1) from t_xinshui_order  xo  where xo.sold_user_id=t1.tx_user_id
      and xo.pay_status='2'
     ) sold_cnt
       from  t_c2c_product t1  ,t_against t2 where t1.against_id=t2.against_id and t1.status='1'  and t1.is_recomend=1
       and rownum<=15;
   
     
end;


--计算 最近5场胜率
function  getWinRatio(
p_userId in number
)return varchar2
is
allCnt number(10):=0;
winCnt number(10):=0;
resultValue varchar2(100);
begin
select count(1) into winCnt from(
select a.status  from
 (select b.status from  t_c2c_product b where b.tx_user_id=p_userId
 and b.status in('2','3') order by b.pub_time desc) a where rownum<=5
 ) x where x.status='2';

 select count(1) into allCnt from(
select a.status  from
 (select b.status from  t_c2c_product b where b.tx_user_id=p_userId
 and b.status in('2','3') order by b.pub_time desc) a where rownum<=5
 ) x;
 if allCnt=0 then
   resultValue:='0%';
   return resultValue;
 end if;
 resultValue:=winCnt*100/allCnt;
 resultValue:=resultValue||'%';

 return resultValue;
end;
--本站热门方案推荐
procedure  findHotPlanList(
i_betCategory in char,--玩法
hot_bet_List out List_CUR--本站热门方案推荐
)is
begin
  open hot_bet_List for select a.bet_id,a.is_hot,  decode(a.bet_categoty,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') bet_category
     ,a.phase_no,a.type,a.bet_username,c.bet_military,a.all_money,round((a.already_buy_copys*100)/a.divide_copys,0)  progress,a.bet_id
      from t_bet_order a,t_user c  where a.bet_userid=c.userid and a.sponsor_type='1' and a.is_hot=1 and a.bet_categoty=i_betCategory;
end;
--当前心水
procedure  findCurXinshui(i_UserId  in  number,
CUR_PUB_XINSHUI out List_CUR,--他发起的心水
CUR_BUY_XINSHUI out List_CUR--他购买的心水
)
is
begin
  open CUR_PUB_XINSHUI for select  t2.host_name,t2.guest_name,t1.xinshui_no,t1.price,t2.race_name,to_char(t2.start_time,'mm-dd hh24:mi') start_time,t1.tx_username,t1.ensure_money,
      (select  count(1) from t_xinshui_order  xo  where xo.sold_user_id=t1.tx_user_id) sold_cnt,t1.price
      ,getWinRatio(t1.tx_user_id)  win_ratio,
      (select  count(1) from t_xinshui_order  xo  where xo.sold_user_id=t1.tx_user_id
      and xo.pay_status='2'
     ) sold_cnt
       from  t_c2c_product t1  ,t_against t2 where t1.against_id=t2.against_id and t1.status='1'  and  t1.tx_user_id=i_UserId;

      open CUR_BUY_XINSHUI  for select o.buy_user_id, t2.host_name,t2.guest_name,t1.xinshui_no,t1.price,t2.race_name,to_char(t2.start_time,'mm-dd hh24:mi') start_time,t1.tx_username,t1.ensure_money,
      (select  count(1) from t_xinshui_order  xo  where xo.sold_user_id=t1.tx_user_id and o.pay_status='2'
      ) sold_cnt,t1.price
      ,getWinRatio(t1.tx_user_id)  win_ratio
       from  t_c2c_product t1  ,t_against t2,t_xinshui_order o
       where t1.against_id=t2.against_id  and o.product_id=t1.c2c_id
       and  o.buy_user_id=i_UserId;


end;
--当前投注
procedure  findCurrentBet(i_UserId  in  number,
--i_betCategory in char,--玩法
CUR_SPON_BET out List_CUR,--他发起的投注
CUR_CANYU_BET out List_CUR--他参与的投注

)
is
begin
     open CUR_SPON_BET for select decode(a1.bet_categoty,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') bet_category
     ,a1.phase_no,a1.type,a1.bet_username,c1.bet_military,a1.all_money,round((a1.already_buy_copys*100)/a1.divide_copys,2)  progress,a1.bet_id
      from t_bet_order a1,t_user c1  where a1.bet_userid=c1.userid and a1.sponsor_type='1' and c1.userid=i_UserId;

     open CUR_CANYU_BET for select decode(a2.bet_categoty,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') bet_category
     ,a2.phase_no,a2.type,a2.bet_username,c2.bet_military,a2.all_money,round((a2.already_buy_copys*100)/a2.divide_copys,0)  progress,a2.bet_id
      from t_bet_order a2,t_user c2  where a2.bet_userid=c2.userid and a2.sponsor_type='2' and c2.userid=i_UserId;




end;

procedure  findHomeSpaceData(i_UserId  in  number,
CUR_SPON_BET out List_CUR,--他发起的投注
CUR_CANYU_BET out List_CUR,--他参与的投注
CUR_PUB_XINSHUI out List_CUR,--他发布的心水
CUR_BUY_XINSHUI out List_CUR,--他购买的心水
dyna_info out List_CUR
)
is
begin
     open CUR_SPON_BET for select decode(a1.bet_categoty,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') bet_category
     ,a1.phase_no,a1.type,a1.bet_username,c1.bet_military,a1.all_money,round((a1.already_buy_copys*100)/a1.divide_copys,0)  progress,a1.bet_id
      from t_bet_order a1,t_user c1  where a1.bet_userid=c1.userid and a1.sponsor_type='1' and c1.userid=i_UserId;

      open CUR_CANYU_BET for select decode(a2.bet_categoty,'1','胜负14场','2','任9场','3','4场进球','5','6 场半全场','61','单场半全场', '62','单场比分','63','单场胜平负','64','单场上下单双','65','单场总进球') bet_category
     ,a2.phase_no,a2.type,a2.bet_username,c2.bet_military,a2.all_money,round((a2.already_buy_copys*100)/a2.divide_copys,0)  progress,a2.bet_id
      from t_bet_order a2,t_user c2  where a2.bet_userid=c2.userid and a2.sponsor_type='2' and c2.userid=i_UserId;

       open CUR_PUB_XINSHUI for select  t2.host_name,t2.guest_name,t1.xinshui_no,t1.price,t2.race_name,to_char(t2.start_time,'mmdd hh24:mi') start_time,t1.tx_username,t1.ensure_money,
      (select  count(1) from t_xinshui_order  xo  where xo.sold_user_id=t1.tx_user_id) sold_cnt,t1.price
       from  t_c2c_product t1  ,t_against t2 where t1.against_id=t2.against_id and t1.status='1'  and  t1.tx_user_id=i_UserId;

      open CUR_BUY_XINSHUI  for select o.buy_user_id, t2.host_name,t2.guest_name,t1.xinshui_no,t1.price,t2.race_name,to_char(t2.start_time,'mmdd hh24:mi') start_time,t1.tx_username,t1.ensure_money,
      (select  count(1) from t_xinshui_order  xo  where xo.sold_user_id=t1.tx_user_id) sold_cnt,t1.price
       from  t_c2c_product t1  ,t_against t2,t_xinshui_order o
       where t1.against_id=t2.against_id  and o.product_id=t1.c2c_id
       and  o.buy_user_id=i_UserId;

        open dyna_info  for  select sp.phase_no,sp.bet_userid,bo.bet_username,sp.bet_username  sponsorUsername,

       decode(sp.bet_categoty,'1','胜负彩14场','2','任选9场','3','4场进球','5','6场半全场','61','单场半全场','62','单场比分', '63',
            '单场让球胜平负','64','单场上下单双','65','单场总进球')  bet_categoty,
       sp.all_money,round((sysdate-sp.bet_time)*24,0)  jiange from t_bet_order  bo,t_bet_order sp where  bo.sponsor_bet_id=sp.bet_id
       and bo.sponsor_type='2' and to_char(bo.bet_time,'yyyymmdd')>=to_char(sysdate-7,'yyyymmdd') and bo.bet_userid=i_UserId;--他的动态


end;

--个人中心数据
   procedure  findSpaceData(
   i_UserId  in  number,--1
   regTime out varchar2,--2注册时间
   attentedCnt out number,--3被关注次数
   attentCnt out number,--4他关注别人次数
   username out varchar2,--5他的用户名
   betMilitary out number, --6他的购彩战绩
   xinshuiMilitary out number, --7他的心水战绩
   king_cat_cur out List_CUR,--8如果她是金牌发起人，则返回对应彩种已经定制跟单的人数 格式  胜负彩14场;21
   spCnt out number,--9发起合买数
   spSuccess out varchar2,--10发单成功率
   canYuCnt  out number,--11参与合买次数
   zjCnt out number,--12总中奖次数
   pubXinshui out number,--13发布心水数
   soldXinshuiCnt out number,--14销售心水人数
   last_visit_List out List_CUR,--15最近访客
   userGrade out number,--16用户等级
   bonusList out varchar2,--17获奖记录
   latest_bosnusList out List_CUR,--18
   signature out VARCHAR2--19个人中心的个人签名
  )
  is
  dbException exception;
  sqlV varchar2(200);
  millionBonus  number(20):=0;--百万大奖： 0次
  hundthBonus  number(20):=0;--十万大奖： 0次
  wanBonus number(20):=0;--万元大奖： 0次
  thousandBonus number(20):=0;--千元大奖： 0次

  begin

    select to_char(u.reg_time,'yyyy-mm-dd'),u.username,u.bet_military,u.xinshui_military,u.user_grade,u.signature
    into regTime,username,betMilitary,xinshuiMilitary,userGrade,signature
    from t_user u where  u.userid=i_UserId;

      for v1 in(
       select count(1) cnt1  from t_my_attention ma  where  ma.user_id=i_UserId) loop
         attentCnt:=v1.cnt1;
      end loop;
       --他关注别人次数
      for v2 in(
       select count(1) cnt2  from t_my_attention ma  where  ma.target_userid=i_UserId) loop--被关注次数
           attentedCnt:=v2.cnt2;
      end loop;

       open king_cat_cur for select a.bet_category,(select count(x.category) from  t_my_auto_order x
       where x.status='1' and x.king_id=a.id and x.category=a.bet_category)  gd_cnt
       from t_king_sponsor a
       where a.status='1' and a.userid=i_UserId;

       for v3 in(
       select count(1) cnt3  from t_bet_order h where h.bet_userid=i_UserId) loop--发起合买数
          spCnt:=v3.cnt3;
       end loop;

       for v4 in(
       select count(1) cnt4  from t_bet_order k where k.bet_userid=i_UserId
       and k.already_buy_copys=k.divide_copys) loop--发单成功率
         spSuccess:=v4.cnt4;
       end loop;

       for v5 in(
       select count(1) cnt5  from t_bet_order k where k.bet_userid=i_UserId
       and k.already_buy_copys=k.divide_copys and k.sponsor_type='2') loop--参与合买次数
         canYuCnt:=v5.cnt5;
       end loop;

      for v6 in(
       select count(1)  cnt6  from t_bet_order k where k.bet_userid=i_UserId
       and k.zj_status='2') loop--总中奖次数
        zjCnt:=v6.cnt6;
      end loop;

      for v7 in(
       select count(1)  cnt7  from t_c2c_product  c5 where
       c5.tx_user_id=i_UserId  and (c5.status='2' or c5.status='3')) loop--发布心水数量
         pubXinshui:=v7.cnt7;
      end loop;

     for v8 in(
       select count(1) cnt8 from t_xinshui_order xo2
       where xo2.sold_user_id=i_UserId and xo2.pay_status='3') loop--销售心水数量
         soldXinshuiCnt:=v8.cnt8;
     end loop;

       open king_cat_cur for select a.bet_category,(select count(x.category) from  t_my_auto_order x
       where x.status='1' and x.king_id=a.id and x.category=a.bet_category)  gd_cnt
       from t_king_sponsor a
       where a.status='1' and a.userid=i_UserId;

       open last_visit_List for select u.username,u.user_grade,u.bet_military,u.xinshui_military
       from  T_SPACE_VISITED v,T_USER u where v.user_id=u.userid
       and v.space_userid=i_UserId;

       --bonusList
       for vb in (select bonus.money from T_BONUS bonus  where bonus.user_id=i_UserId) loop
         if vb.money>=1000000 then
            millionBonus:=millionBonus+1;

         elsif vb.money>=100000 then
          hundthBonus:=hundthBonus+1;
         elsif vb.money>=10000 then
          wanBonus:=wanBonus+1;
         elsif vb.money>1000 then
          thousandBonus:=thousandBonus+1;
         end if;
       end loop;
       bonusList:=millionBonus||','||hundthBonus||','||wanBonus||','||thousandBonus;
    open latest_bosnusList for select A.lottery_name,A.phase,A.money  from(
  select x.lottery_name,x.phase,x.money  from  T_BONUS x order by x.kj_time desc
  ) A where rownum<=2;



   --exception
  -- when others  then
   --raise dbException;



  end;
end SPACE;
