create or replace package body PHASE is
--保存期次  2010-02-10 10:08

procedure  savePhase(againstList in VARCHAR2,category in VARCHAR2,phaseNo in VARCHAR2,
     soldTime in VARCHAR2,tkpDeadline in VARCHAR2,mulDeadline in VARCHAR2,sDeadline in VARCHAR2,result OUT NUMBER)
is 
against_id NUMBER(20);--对阵ID
lotteryPhaseID NUMBER(20);
againstArray TOOLS.table_list:= TOOLS.table_list();
dbException exception;
cnt NUMBER(2);
againstCnt NUMBER(2):=0;
begin
  select count(1) into cnt from  T_LOTTERY_PHASE m where  m.phase_no=phaseNo;
  IF cnt>0 THEN--期次已经存在
      result:=2;
      return;
  END IF;
  againstArray:=TOOLS.fn_split(againstList,';');
  --第0步
  select SEQ_LOTTERY_PHASE_ID.NEXTVAL  into  lotteryPhaseID from dual;
  --第一步 插入期次信息
  if category='1' then--如果是北京单场
     insert into T_LOTTERY_PHASE(ID,Category,Phase_No,SOLD_TIME,TKP_DEADLINE,VICI_DEADLINE,COMBO_VICI,STATUS)
     values(lotteryPhaseID,category,phaseNo,to_date(soldTime,'yyyy-mm-dd hh24:mi'),to_date(tkpDeadline,'yyyy-mm-dd hh24:mi'),to_number(mulDeadline),to_number(sDeadline),'1');
  else
    insert into T_LOTTERY_PHASE(ID,Category,Phase_No,SOLD_TIME,TKP_DEADLINE,MUL_DEADLINE,S_DEADLINE,STATUS)
    values(lotteryPhaseID,category,phaseNo,to_date(soldTime,'yyyy-mm-dd hh24:mi'),to_date(tkpDeadline,'yyyy-mm-dd hh24:mi'),to_date(mulDeadline,'yyyy-mm-dd hh24:mi'),to_date(sDeadline,'yyyy-mm-dd hh24:mi'),'1');
  end if;
  --第二步 插入期次-对阵表
  for v in againstArray.FIRST..againstArray.LAST loop
    against_id:=to_number(againstArray(v));
    insert into T_LOTTERY_AGAINST(ID,AGAINST_ID,PHASE_ID) values(SEQ_LOTTERY_AGAINST_ID.NEXTVAL,against_id,lotteryPhaseID);
     
  
    
  end loop;
  result:=1;
  exception
  when others then
  rollback;
  result:=-1;
   raise dbException;
  

end savePhase;





--公布赛果  
--category 期次分类  '1':胜负彩   '2':进球彩期次 '3':半全场期次 '4':北京单场期次 '5':竞彩单场
procedure   batchPubRaceResult(category in varchar2,result in varchar2)
is 
resultList TOOLS.table_list:= TOOLS.table_list();
against VARCHAR2(200);--每一场对阵 变量
againstList TOOLS.table_list:=Tools.table_list();
begin
     resultList:=TOOLS.fn_split(result,';');-- 分割每一场1,3:0,3;2,2:2,1 其中1,3:0,3代表一场  1为T_AGAINST主键  3:0为比分  3为赛果
     FOR v IN resultList.FIRST..resultList.LAST  LOOP--遍历每一场对阵
         against:=resultList(v);--对应一场对阵
         againstList:=TOOLS.fn_split(against,',');
        
         --dbms_output.put_line(againstList(1));
         --dbms_output.put_line(againstList(2));
         --dbms_output.put_line(againstList(3));
         update t_against  t set t.score=againstList(2),t.race_result=againstList(3)  where  t.against_id=againstList(1);
        
          
          
     END LOOP;


end batchPubRaceResult;
end PHASE;
