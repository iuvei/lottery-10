create or replace package body BET_ADV_SEARCH is
procedure  advSearch4(betCategory varchar2,phaseId in number,advSearch in varchar2,result_list out CLOB)
is
db_plan varchar2(300);
db_planList TOOLS.table_list:= TOOLS.table_list();
tjList TOOLS.table_list:= TOOLS.table_list();
tempList TOOLS.table_list:= TOOLS.table_list();
tj varchar2(20);
idx number(10);
idv  varchar2(20);
boolValue number(10);--结果
sqArr TOOLS.table_list:= TOOLS.table_list();
db_planTemp varchar2(20);
db_planTempArr TOOLS.table_list:= TOOLS.table_list();
db_ARRAY TOOLS.table_list:= TOOLS.table_list();
db_e varchar2(10);
m_Array TOOLS.table_list:= TOOLS.table_list();
m_e varchar2(10);
begin
tjList:=tools.fn_split(advSearch,';');--[1,0][3,12]
result_list:='';
for v in(select  *  from  t_bet_order t where t.bet_categoty=betCategory and t.phase_id=phaseId  and t.plan is not null) loop
   db_plan:=v.plan;
   db_planList:=tools.fn_split(db_plan,',');
   boolValue:=1;
   for k in tjList.first..tjList.last loop
      tj:=tjList(k);
      if tj is not null and length(tj)>=2 then
        tempList:=tools.fn_split(tj,','); --[场次][进球数]
        if tempList.count=2 then
          idx:=to_number(tempList(1));--条件场次 2
          idv:=tempList(2);--条件值  0123
          dbms_output.put_line('IDV='||idv);
          db_planTemp:=db_planList(idx);--数据库里面的值
        
          --dbms_output.put_line(db_planTemp||'---'||idv);
          ----AAAA
         for k in 1..length(idv) loop 
            m_e:=substr(idv,k,1);
            dbms_output.put_line(db_planTemp||'---'||idv);
            if  instr(db_planTemp,m_e)=0 then
                boolValue:=0;
            end if;
           
          end loop;

          ----AAA结尾
   
    end if;
    end if;
end loop;
if boolValue=1 then
      result_list:=result_list||','||v.bet_id;
   end if;
end loop;
result_list:=result_list||',';
end;
----------------
procedure  advSearch6(betCategory varchar2,phaseId in number,advSearch in varchar2,result_list out CLOB)
is
db_plan varchar2(300);
db_planList TOOLS.table_list:= TOOLS.table_list();
tjList TOOLS.table_list:= TOOLS.table_list();
tempList TOOLS.table_list:= TOOLS.table_list();
temp varchar2(20);
idx number(10);
idv  varchar2(20);
boolValue number(10);--结果
sqArr TOOLS.table_list:= TOOLS.table_list();
db_planTemp varchar2(20);
db_planTempArr TOOLS.table_list:= TOOLS.table_list();
db_ARRAY TOOLS.table_list:= TOOLS.table_list();
db_e varchar2(10);
m_Array TOOLS.table_list:= TOOLS.table_list();
m_e varchar2(10);
begin
tjList:=tools.fn_split(advSearch,';');--[2,310-0][3,-1][4,3-]
result_list:='';
for v in(select  *  from  t_bet_order t where t.bet_categoty=betCategory and t.phase_id=phaseId  and t.plan is not null) loop
   db_plan:=v.plan;
   db_planList:=tools.fn_split(db_plan,',');
   boolValue:=1;
   for k in tjList.first..tjList.last loop
      temp:=tjList(k);
      if temp is not null and length(temp)>=2 then
        tempList:=tools.fn_split(temp,',');
        --dbms_output.put_line('tempList'||tempList.count);
        if tempList.count=2 then
          --dbms_output.put_line('33 line..');
          idx:=to_number(tempList(1));--条件场次 2
          idv:=tempList(2);--条件值  310-0  31-
          sqArr:=tools.fn_split(idv,'-'); --条件数组[310][0]
          dbms_output.put_line('IDV='||idv);
          db_planTemp:=db_planList(idx);--  310-1
          db_planTempArr:=tools.fn_split(db_planTemp,'-');
          
        
         
         
         
          ----AAAA
          --if sqArr(1) is not null and length(sqArr(1))>0 then
          for k in sqArr.first..sqArr.last loop 
            m_e:=sqArr(k);--[310] [1]
            if length(m_e)>0 then
                    db_e:=db_planTempArr(k);
                    if  length(db_e)<length(m_e) then   boolValue:=0;
                    else
                          if m_e='30'  then 
                               if  db_e='310' or db_e='30' then  boolValue:=1; else  boolValue:=0;  end if;
                          else
                            if instr(db_e,m_e)=0  then  boolValue:=0;  end if;
                          end if;
                    end if;
                       
                    
            
             
            end if;
           
          end loop;

          ----AAA结尾
   
    end if;
    end if;
end loop;
if boolValue=1 then
      result_list:=result_list||','||v.bet_id;
   end if;
end loop;
result_list:=result_list||',';
end;
end BET_ADV_SEARCH;
