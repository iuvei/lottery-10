--2010-03-01 09:12
procedure    executeXinshuiResult(resultValue OUT  CHAR)
is 
select_type CHAR(1);
xinshui_result CHAR(2);
c2cId NUMBER(20);
dbException  exception;
begin
   for v in(select b.c2c_id,a.xinshui_result,b.select_type from T_XINSHUI_AGAINST a,T_C2C_PRODUCT b  where a.AGAINST_ID=b.AGAINST_ID  
   and a.XINSHUI_RESULT is not null)  loop
        select_type:=v.select_type;
        xinshui_result:=v.xinshui_result;
        c2cId:=v.c2c_id;
        if instr(xinshui_result,select_type)>0  then
            update  t_c2c_product  cp  set cp.answer='1' where  cp.c2c_id=c2cId;
        else
            update  t_c2c_product  cp  set cp.answer='0' where  cp.c2c_id=c2cId;
        end if;
   
   end loop;
   resultValue:='1';
exception
when others then
   resultValue:='-1';
   rollback;
   raise dbException;


end executeXinshuiResult;