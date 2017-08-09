create or replace package body LOTTERY_KJ is
procedure  caipiao_kj(phaseNo in  varchar2,betCategory in varchar2,kjNo in varchar2)
is 
db_plan varchar2(100);
db_e varchar2(10);
resultValue number(5);
m_e varchar2(10);
bonusClass number(3):=0;
db_planArray TOOLS.table_list:=TOOLS.table_list();
hgArray  TOOLS.table_list:=TOOLS.table_list();
betId number(20);
changci number(10);
begin
   if betCategory='1' then--胜负彩14场
      for v in (select t.bet_id, t.plan,t.bet_username,t.bet_userid,t.type,t.bet_categoty,t.phase_no from t_bet_order t  where  t.bet_categoty=betCategory  and  t.phase_no=phaseNo) loop
        db_plan:=v.plan;
        resultValue:=0;
        db_planArray:=TOOLS.fn_split(db_plan,',');
        for k in 1..length(kjNo)  loop
            m_e:=substr(kjNo,k,1);
            for j in db_planArray.first..db_planArray.last  loop
                 db_e:=db_planArray(j);
                 if instr(db_e,m_e)>0 then
                   resultValue:=resultValue+1;
                 end if;
            end loop;
        end loop;
        if resultValue=14 then
           bonusClass:=1;--中了一等奖
        end if;
        if resultValue=13 then
         bonusClass:=2;--中了二等奖
        end if;
       if  bonusClass>0 then
         insert into T_BONUS(ID,PHASE,USERNAME,USER_ID,LOTTERY_TYPE,LOTTERY_NAME,MONEY,BONUS_CLASS) 
         values(SEQ_BONUS_ID.NEXTVAL,v.phase_no,v.bet_username,v.bet_userid,v.type,v.bet_categoty,0,1);
         update t_bet_order  bo  set bo.zj_status='2',bo.bonus=0,bo.correct_changci=resultValue  where  bo.bet_id=v.bet_id;
       else
         update t_bet_order  bo  set bo.zj_status='3',bo.bonus=0,bo.correct_changci=resultValue  where  bo.bet_id=v.bet_id;
       end if;
     end loop;
   end if;
   if  betCategory='2' then--任9场
      for v in (select t.bet_id, t.plan,t.bet_username,t.bet_userid,t.type,t.bet_categoty,t.phase_no from t_bet_order t  
         where  t.bet_categoty=betCategory  and  t.phase_no=phaseNo) loop
         betId:=v.bet_id;
         resultValue:=0;
         for v_choice in(select boc.bet_plan,boc.changci from t_bet_order_choice  boc  where boc.bet_order_id=betId)  loop
            db_plan:=v_choice.bet_plan;
            changci:=v_choice.changci;
            m_e:=substr(kjNo,changci,1);
            if instr(db_plan,m_e)>0 then
              resultValue:=resultValue+1;
            end if;
            
         end loop;
         if resultValue=9 then
           insert into T_BONUS(ID,PHASE,USERNAME,USER_ID,LOTTERY_TYPE,LOTTERY_NAME,MONEY,BONUS_CLASS) 
         values(SEQ_BONUS_ID.NEXTVAL,v.phase_no,v.bet_username,v.bet_userid,v.type,v.bet_categoty,0,1);
         update t_bet_order  bo  set bo.zj_status='2',bo.bonus=0,bo.correct_changci=resultValue  where  bo.bet_id=v.bet_id;
         
         end if;
      end loop;
   end if;
   
   ------------ KjNO:3102    6场半全场
    if  betCategory='3' then
      for v in (select t.bet_id, t.plan,t.bet_username,t.bet_userid,t.type,t.bet_categoty,t.phase_no from t_bet_order t  
         where  t.bet_categoty=betCategory  and  t.phase_no=phaseNo) loop
         betId:=v.bet_id;
         resultValue:=0;
         db_plan:=v.plan;
         db_planArray:=TOOLS.fn_split(db_plan,',');
         for k in 1..length(kjNo)  loop
            m_e:=substr(kjNo,k,1);--开奖号码3
            db_e:=db_planArray(k/2);
            hgArray:=tools.fn_split(db_e,'-');
            if mod(k,2)=1 then
               if instr(hgArray(0),m_e)>0 then
                 resultValue:=resultValue+1;
               end if;
            else
               if instr(hgArray(1),m_e)>0 then
                 resultValue:=resultValue+1;
               end if;
            end if;
        end loop;
        --
        if resultValue=8 then
            insert into T_BONUS(ID,PHASE,USERNAME,USER_ID,LOTTERY_TYPE,LOTTERY_NAME,MONEY,BONUS_CLASS) 
         values(SEQ_BONUS_ID.NEXTVAL,v.phase_no,v.bet_username,v.bet_userid,v.type,v.bet_categoty,0,1);
         update t_bet_order  bo  set bo.zj_status='2',bo.bonus=0,bo.correct_changci=resultValue  where  bo.bet_id=v.bet_id;
        else
          update t_bet_order  bo  set bo.zj_status='3',bo.bonus=0,bo.correct_changci=resultValue  where  bo.bet_id=v.bet_id;
        end if;
        --
   
      end loop;
   end if;
   
end;
end LOTTERY_KJ;
