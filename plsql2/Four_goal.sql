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
     stat_inArr :=FOUR_GOAL_LIST(0,0,0,0,0,0,0,0); 
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
