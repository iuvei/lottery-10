--保存足彩中心期次
procedure   saveLotteryPhase(category in varchar2,result in varchar2)
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


end saveLotteryPhase;

















end PHASE;