create or replace package body COOP_BET is
procedure  statPlan4(betCategory in varchar2,phaseId in number,result_list out varchar2)
 is
 againstPlan varchar2(200);
 cnt3 number(20):=0;
 cnt2 number(20):=0;
 cnt1 number(20):=0;
 cnt0 number(20):=0;
 temp varchar2(300);
 addResult number(20);
 stat_inArr  FOUR_GOAL_RECORD:=FOUR_GOAL_RECORD(0,0,0,0,0,0,0,0); 
 my_list FOUR_GOAL_LIST:=FOUR_GOAL_LIST();
 planList TOOLS.table_list:= TOOLS.table_list();
 againstLen number(10):=4;
 sqArray  TOOLS.table_list:= TOOLS.table_list();
 begin
   my_list :=FOUR_GOAL_LIST();
   for  k in 1..4 loop
     stat_inArr :=FOUR_GOAL_RECORD(0,0,0,0,0,0,0,0); 
     my_list.extend(k);
     my_list(k):=stat_inArr;
   end loop;
   for v in(select  *  from  t_bet_order t where t.bet_categoty=betCategory and t.type=4 and t.plan is not null) loop
     againstPlan:=v.plan;
     planList:=tools.fn_split(againstPlan,',');
     for i in 1..planList.count  loop--遍历每一场对阵1,2,3,4..14
         stat_inArr:=my_list(i);
         temp:=planList(i);--对应一场对阵
         sqArray:=tools.fn_split(temp,'-');
         
         if instr(sqArray(1),'3')>0 then
            stat_inArr.a3:= stat_inArr.a3+1;
         end if;
           if instr(sqArray(1),'2')>0 then
            stat_inArr.a2:=stat_inArr.a2+1;
         end if;
         if instr(sqArray(1),'1')>0 then
            stat_inArr.a1:=stat_inArr.a1+1;
         end if;
         if instr(sqArray(1),'0')>0 then
            stat_inArr.a0:=stat_inArr.a0+1;
         end if;
         if instr(sqArray(2),'3')>0 then
            stat_inArr.b3:= stat_inArr.a3+1;
         end if;
         if instr(sqArray(2),'2')>0 then
            stat_inArr.b2:= stat_inArr.a2+1;
         end if;
         if instr(sqArray(2),'1')>0 then
            stat_inArr.b1:=stat_inArr.a1+1;
         end if;
         if instr(sqArray(2),'0')>0 then
            stat_inArr.b0:=stat_inArr.a0+1;
         end if;
         my_list(i):=stat_inArr;
      end loop;
   end loop;
   for m in 1..4 loop
       stat_inArr:=my_list(m);
    
        
       addResult:=stat_inArr.a3+stat_inArr.a1+stat_inArr.a0+stat_inArr.a2;
       result_list:=result_list||';';
       if addResult=0 then
          result_list:=result_list||'3(0%)';
          result_list:=result_list||'2(0%)';
          result_list:=result_list||'1(0%)';
          result_list:=result_list||'0(0%)';
       end if;
       if addResult>0 then
          result_list:=result_list||trunc(100*stat_inArr.a3/addResult,2)||'%';
          result_list:=result_list||trunc(100*stat_inArr.a2/addResult,2)||'%';
          result_list:=result_list||trunc(100*stat_inArr.a1/addResult,2)||'%';
          result_list:=result_list||trunc(100*stat_inArr.a0/addResult,2)||'%';
       end if;
       addResult:=stat_inArr.b3+stat_inArr.b2+stat_inArr.b1+stat_inArr.b0;
       result_list:=result_list||'-';
       if addResult=0 then
          result_list:=result_list||'3(0%)';
          result_list:=result_list||'2(0%)';
          result_list:=result_list||'1(0%)';
          result_list:=result_list||'0(0%)';
       end if;
       if addResult>0 then
          result_list:=result_list||trunc(100*stat_inArr.b3/addResult,2)||'%';
          result_list:=result_list||trunc(100*stat_inArr.b2/addResult,2)||'%';
          result_list:=result_list||trunc(100*stat_inArr.b1/addResult,2)||'%';
          result_list:=result_list||trunc(100*stat_inArr.b0/addResult,2)||'%';
       end if;
    end loop;
end;



procedure  statPlan6(betCategory in varchar2,phaseId in number,result_list out varchar2)
 is
 againstPlan varchar2(200);
 cnt3 number(20):=0;
 cnt1 number(20):=0;
 cnt0 number(20):=0;
 temp varchar2(300);
 addResult number(20);
 stat_inArr  six_record:=six_record(0,0,0,0,0,0); 
 my_list SIX_LIST:=SIX_LIST();
 planList TOOLS.table_list:= TOOLS.table_list();
 againstLen number(10):=6;
 sqArray  TOOLS.table_list:= TOOLS.table_list();
 begin
   my_list :=SIX_LIST();
   for  k in 1..6 loop
     stat_inArr :=six_record(0,0,0,0,0,0); 
     my_list.extend(k);
     my_list(k):=stat_inArr;
   end loop;
   for v in(select  *  from  t_bet_order t where t.bet_categoty=betCategory and t.type='4' and  t.plan is not null) loop
     againstPlan:=v.plan;
     planList:=tools.fn_split(againstPlan,',');
     for i in 1..planList.count  loop--遍历每一场对阵1,2,3,4..14
         stat_inArr:=my_list(i);
         temp:=planList(i);--对应一场对阵
         sqArray:=tools.fn_split(temp,'-');
         
         if instr(sqArray(1),'3')>0 then
            stat_inArr.a3:= stat_inArr.a3+1;
         end if;
         if instr(sqArray(1),'1')>0 then
            stat_inArr.a1:=stat_inArr.a1+1;
         end if;
         if instr(sqArray(1),'0')>0 then
            stat_inArr.a0:=stat_inArr.a0+1;
         end if;
          if instr(sqArray(2),'3')>0 then
            stat_inArr.b3:= stat_inArr.a3+1;
         end if;
         if instr(sqArray(2),'1')>0 then
            stat_inArr.b1:=stat_inArr.a1+1;
         end if;
         if instr(sqArray(2),'0')>0 then
            stat_inArr.b0:=stat_inArr.a0+1;
         end if;
         my_list(i):=stat_inArr;
      end loop;
   end loop;
   for m in 1..6 loop
       stat_inArr:=my_list(m);
       dbms_output.put_line(stat_inArr.a3||'   '||stat_inArr.a1||'   '||stat_inArr.a0);
       dbms_output.put_line(stat_inArr.b3||'   '||stat_inArr.b1||'   '||stat_inArr.b0);
        
       addResult:=stat_inArr.a3+stat_inArr.a1+stat_inArr.a0;
       result_list:=result_list||';';
       if addResult=0 then
          result_list:=result_list||'3(0%)';
          result_list:=result_list||'1(0%)';
          result_list:=result_list||'0(0%)';
       end if;
       if addResult>0 then
          result_list:=result_list||trunc(100*stat_inArr.a3/addResult,2)||'%';
          result_list:=result_list||trunc(100*stat_inArr.a1/addResult,2)||'%';
          result_list:=result_list||trunc(100*stat_inArr.a0/addResult,2)||'%';
       end if;
       addResult:=stat_inArr.b3+stat_inArr.b1+stat_inArr.b0;
       result_list:=result_list||'-';
       if addResult=0 then
          result_list:=result_list||'3(0%)';
          result_list:=result_list||'1(0%)';
          result_list:=result_list||'0(0%)';
       end if;
       if addResult>0 then
          result_list:=result_list||trunc(100*stat_inArr.b3/addResult,2)||'%';
          result_list:=result_list||trunc(100*stat_inArr.b1/addResult,2)||'%';
          result_list:=result_list||trunc(100*stat_inArr.b0/addResult,2)||'%';
       end if;
    end loop;
end;

procedure  statPlanRen(betCategory in varchar2,phaseId in number,result_list out varchar2)
 is
 againstPlan varchar2(500);
 cnt3 number(20):=0;
 cnt1 number(20):=0;
 cnt0 number(20):=0;
 temp varchar2(300);
 addResult number(20);
 stat_inArr  num_record:=num_record(0,0,0); 
 my_list temp_list:=temp_list();
 planList TOOLS.table_list:= TOOLS.table_list();
 againstLen number(10):=14;
 idx number(10);
 betId number(20);
 begin
   my_list :=temp_list();
   for  k in 1..14 loop
     stat_inArr :=num_record(0,0,0); 
     my_list.extend(k);
     my_list(k):=stat_inArr;
   end loop;
   for v in(select  *  from  t_bet_order t where t.bet_categoty='2' and t.type='4') loop
     betId:=v.bet_id;
     for in_v in (select *  from  t_bet_order_choice  o  where  o.bet_order_id=betId order by o.changci) loop
         temp:=in_v.bet_plan;
         idx:=in_v.changci;
         stat_inArr:=my_list(idx);
         if instr(temp,'3')>0 then
            stat_inArr.cnt3:= stat_inArr.cnt3+1;
         end if;
         if instr(temp,'1')>0 then
            stat_inArr.cnt1:=stat_inArr.cnt1+1;
         end if;
         if instr(temp,'0')>0 then
            stat_inArr.cnt0:=stat_inArr.cnt0+1;
         end if;
         my_list(idx):=stat_inArr;
      end loop;
   end loop;
   
   for m in 1..14 loop
       stat_inArr:=my_list(m);
      
        addResult:=stat_inArr.cnt3+stat_inArr.cnt1+stat_inArr.cnt0;
       result_list:=result_list||';';
       if addResult=0 then
        result_list:=result_list||'0%';
        result_list:=result_list||'0%';
        result_list:=result_list||'0%';
       end if;
       if addResult>0 then
        result_list:=result_list||trunc(100*stat_inArr.cnt3/addResult,2)||'%';
        result_list:=result_list||trunc(100*stat_inArr.cnt1/addResult,2)||'%';
        result_list:=result_list||trunc(100*stat_inArr.cnt0/addResult,2)||'%';
       end if;
   end loop;
    
   
 end;

procedure  advSearch14(betCategory varchar2,phaseId in number,advSearch in varchar2,result_list out varchar2)
is
plan varchar2(300);
planList TOOLS.table_list:= TOOLS.table_list();
pList TOOLS.table_list:= TOOLS.table_list();
tempList TOOLS.table_list:= TOOLS.table_list();
temp varchar2(20);
idx number(10);
idv  varchar2(20);
boolValue number(10);--结果
m_e  varchar2(100);
begin
pList:=tools.fn_split(advSearch,';');
result_list:='';
for v in(select  *  from  t_bet_order t where t.bet_categoty=betCategory and t.phase_id=phaseId  and t.plan is not null) loop
   plan:=v.plan;
   planList:=tools.fn_split(plan,',');
   boolValue:=1;
   for k in pList.first..pList.last loop
      temp:=pList(k);
      if temp is not null and length(temp)>=2 then
        tempList:=tools.fn_split(temp,',');
        if tempList.count=2 then
          idx:=to_number(tempList(1));--场次
          idv:=tempList(2);--选择条件值
          for u in planList.first..planList.last loop
             dbms_output.put_line('planList(u)='||planList(u));
          end loop;
          if planList(idx) is  not null and  idv is  not null then
          --BUG修改   2010-04-10  15:36
          for k in 1..length(idv) loop 
            m_e:=substr(idv,k,1);
            if  instr(planList(idx),m_e)=0 then
                boolValue:=0;
            end if;
           
          end loop;
          
          -----
              --boolValue:=0;
          end if;
        end if;
      end if;
      
   end loop;
   
   if boolValue=1 then
      result_list:=result_list||','||v.bet_id;
   end if;
end loop;
result_list:=result_list||',';
end;



procedure  statPlan(betCategory in varchar2,phaseId in number,result_list out varchar2)
 is
 againstPlan varchar2(200);
 cnt3 number(20):=0;
 cnt1 number(20):=0;
 cnt0 number(20):=0;
 temp varchar2(300);
 addResult number(20);
 stat_inArr  num_record:=num_record(0,0,0); 
 my_list temp_list:=temp_list();
 planList TOOLS.table_list:= TOOLS.table_list();
 againstLen number(10):=14;
 begin
   my_list :=temp_list();
   for  k in 1..14 loop
     stat_inArr :=num_record(0,0,0); 
     my_list.extend(k);
     my_list(k):=stat_inArr;
   end loop;
   for v in(select  *  from  t_bet_order t where t.bet_categoty=betCategory and t.plan is not null) loop
     againstPlan:=v.plan;
     planList:=tools.fn_split(againstPlan,',');
     for i in 1..planList.count  loop--遍历每一场对阵1,2,3,4..14
         stat_inArr:=my_list(i);
         temp:=planList(i);--对应一场对阵
         if instr(temp,'3')>0 then
            stat_inArr.cnt3:= stat_inArr.cnt3+1;
         end if;
         if instr(temp,'1')>0 then
            stat_inArr.cnt1:=stat_inArr.cnt1+1;
         end if;
         if instr(temp,'0')>0 then
            stat_inArr.cnt0:=stat_inArr.cnt0+1;
         end if;
         my_list(i):=stat_inArr;
      
     end loop;
   end loop;
   
   for m in 1..14 loop
       stat_inArr:=my_list(m);
       dbms_output.put_line(stat_inArr.cnt3||'   '||stat_inArr.cnt1||'   '||stat_inArr.cnt0);
        
       addResult:=stat_inArr.cnt3+stat_inArr.cnt1+stat_inArr.cnt0;
       dbms_output.put_line('**'||addResult);
       result_list:=result_list||';';
       if addResult=0 then
          result_list:=result_list||'3(0%)';
          result_list:=result_list||'1(0%)';
          result_list:=result_list||'0(0%)';
       end if;
       if addResult>0 then
          result_list:=result_list||trunc(100*stat_inArr.cnt3/addResult,2)||'%';
          result_list:=result_list||trunc(100*stat_inArr.cnt1/addResult,2)||'%';
          result_list:=result_list||trunc(100*stat_inArr.cnt0/addResult,2)||'%';
       end if;
   end loop;
    
   
 end;
end COOP_BET;
