create or replace package body bet is
procedure sp_Page(  pageSize       int, 
                    startRow        int,
                    p_SqlSelect      varchar2, --查询语句,含排序部分
                    p_SqlCount       varchar2, --获取记录总数的查询语句
                    p_OutRecordCount out int, --返回总记录数
                    p_OutCursor      out refCursorType) is
    v_sql       varchar2(10000);
    v_count     int;
    v_heiRownum int;
    v_lowRownum int;
  begin
    ----取记录总数
    execute immediate p_SqlCount
      into v_count;
    p_OutRecordCount := v_count;
    ----执行分页查询
    v_heiRownum := startRow+pageSize;
    v_lowRownum := startRow+1;
    v_sql := 'SELECT * FROM (SELECT A.*, rownum rn 
    FROM (' || p_SqlSelect || ') A
    WHERE rownum <= ' || to_char(v_heiRownum) || ') B
    WHERE rn >= ' || to_char(v_lowRownum);
    --注意对rownum别名的使用,第一次直接用rownum,第二次一定要用别名rn
    OPEN p_OutCursor FOR v_sql;
  end sp_Page;
  procedure sp_Page2(p_PageSize       int, --每页记录数
                    p_PageNo         int, --当前页码,从 1 开始
                    p_SqlSelect      varchar2, --查询语句,含排序部分
                    p_SqlCount       varchar2, --获取记录总数的查询语句
                    p_OutRecordCount out int, --返回总记录数
                    p_OutCursor      out refCursorType,
                    p_SqlSelect2      varchar2, --SQL
                    p_OutCursor2      out refCursorType--返回游标
                    ) is
    v_sql       varchar2(10000);
    v_count     int;
    v_heiRownum int;
    v_lowRownum int;
  begin
    ----取记录总数
    execute immediate p_SqlCount  into v_count;
    p_OutRecordCount := v_count;
    ----执行分页查询
    v_heiRownum := p_PageNo * p_PageSize;
    v_lowRownum := v_heiRownum - p_PageSize + 1;
    v_sql := 'SELECT * FROM (SELECT M.*, rownum rn 
    FROM (' || p_SqlSelect || ') M
    WHERE rownum <= ' || to_char(v_heiRownum) || ') N
    WHERE rn >= ' || to_char(v_lowRownum);
    --注意对rownum别名的使用,第一次直接用rownum,第二次一定要用别名rn
    OPEN p_OutCursor FOR v_sql;
    OPEN p_OutCursor2 for p_SqlSelect2;
 end sp_Page2;
  --发起合买
  --allMoney 合买订单总金额 
  --userId发起人用户ID
  procedure  saveCoopBuy(allMoney in number,userId in  NUMBER,subscribeCopys in NUMBER,subscribeMoney in NUMBER,useCaijin in CHAR ,betCategory in varchar2)
  is
  betOrderId number(20);--投注订单ID
  payResult number(2);
  txMoney number(20,2);
  sponsorBetId number(20);
  categoryType  varchar2(5);
  begin
     --select SEQ_BET_ORDER_ID.NEXTVAL into  betOrderId  from dual;
     --LOTTERY_DAIGOU_PAY.coop_pay(betOrderId,userId,subscribeMoney,useCaijin,payResult);
     IF betCategory in('61','62','63','64','65')  THEN
        categoryType:='1';
     ELSIF betCategory='1' THEN
        categoryType:='6';
     ELSIF betCategory='3' THEN
        categoryType:='9';
     ELSIF betCategory='5' THEN
        categoryType:='8';
     END IF;
     LOTTERY_DAIGOU_PAY.coop_pay(betOrderId,userId ,txMoney ,useCaijin,payResult,categoryType);
     
     if payResult=1 then--冻结成功
      insert into t_bet_order(BET_ID,Bet_Userid,All_Money,subscribe_copys,subscribe_money) 
      values(betOrderId,userId,allMoney,subscribeCopys,subscribeMoney);
      sponsorBetId:=betOrderId;
      for  m in(select mao.tx_money  from t_my_auto_order mao ) loop
           txMoney:=m.tx_money;
           select SEQ_BET_ORDER_ID.NEXTVAL into  betOrderId  from dual;
           LOTTERY_DAIGOU_PAY.coop_pay(betOrderId,userId,subscribeMoney,'0',payResult,categoryType);
           if payResult=1 then
               insert into t_bet_order(BET_ID,Bet_Userid,All_Money,subscribe_copys,subscribe_money) 
               values(betOrderId,userId,allMoney,subscribeCopys,subscribeMoney);
           end if;
           
           
      end loop;
     end if;
  end;
  --执行自动跟单
  procedure  autoGendan(sponsorBetOrderId in number,sponsorUserId in number,pSingleMoney in number,pType  in number,pCategoryType in varchar2)
  is
    txMoney number(20,2);--我每一次跟单金额
    alreadyBuyCopys  number(20);
    allCopys number(20);
    copy number(20);--我购买的份数
    allMoney number(20);
    frozenMoney  number(20,2);
    availiableMoney number(20,2);
    betUsername varchar2(300);
    buyUserId number(20);--定制人用户ID
  begin
    for v in (select  my.USERID,my.TX_MONEY  from t_My_Auto_Order my,t_king_sponsor  k where my.king_id=k.id  and k.userid=sponsorUserId
                order by my.order_time)  loop
      txMoney:=v.tx_money;
      buyUserId:=v.userid;
      if buyUserId <> null  then
       select va.all_money,va.frozen_money,va.user_name into  allMoney,frozenMoney,betUsername  from t_virtual_account  va  
       where va.tx_user_id=buyUserId  and va.status='1';
       availiableMoney:=allMoney-frozenMoney;
       if txMoney>=pSingleMoney then--只有跟单金额大于一份的金额时才能跟单
       
        copy:=floor(txMoney/pSingleMoney);
        txMoney:=pSingleMoney*copy;
        if availiableMoney>=txMoney then
           select  a.already_buy_copys,a.divide_copys  into  alreadyBuyCopys,allCopys  from T_BET_ORDER  a;
          if copy+alreadyBuyCopys<=allCopys then
              --新增订单
              insert into  T_BET_ORDER(BET_ID,BET_USERID,SUBSCRIBE_MONEY,BET_TIME,SPONSOR_TYPE,SPONSOR_BET_ID,TYPE,BET_USERNAME,Order_Status)
              values(SEQ_TOTAL_GOAL_ID.NEXTVAL,v.userid,txMoney,sysdate,'2',sponsorBetOrderId,pType,betUsername,'2');
              --更新跟单总量
              update t_bet_order b  set b.already_buy_copys=b.already_buy_copys+copy;
              --冻结账户  产生冻结日志
              update t_virtual_account va2  set va2.frozen_money=va2.frozen_money+txMoney  
              where  va2.tx_user_id=v.userid;
              insert into t_va_frozen_log(FROZEN_ID,FROZEN_MONEY,Tx_User_Id,Flg,Category_Type)
              values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,txMoney,v.userid,'1',pCategoryType);
              
              
          end if;
        end if;
           
       end if;
     end if;
    end loop;
  end;
  




end bet;
