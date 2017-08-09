create or replace package body TOOLS is
function xinshuiResult(score in varchar2,pankou in varchar2,xinshuiType char)
  return varchar2
  is
  begin
     if xinshuiType='1' then
        return resultAsiaXinshui(score,pankou);
     elsif xinshuiType='2' then
        return resultBigSmallXinshui(score,pankou);
     end if;
  end xinshuiResult;
--大小盘 根据比分，盘口 心水判断 赢 走负  2010-04-26 11:30
--'3':选大
--'4':选小
  function   resultBigSmallXinshui(score in varchar2,pankou in varchar2)
  return varchar2
  is
  scoreArray tools.table_list:=tools.table_list();
  panKouArray tools.table_list:=tools.table_list();
  hScore number(5);--主队
  gScore number(5);--客队
  hg number(5);
  pankou1 number(5);--盘口第一个值
  pankou2 number(5);--盘口第二个值
  begin
      scoreArray:=fn_split(score,':');
      hScore:=to_number(scoreArray(1));
      gScore:=to_number(scoreArray(2));
      hg:=hScore+gScore;
      panKouArray:=fn_split(pankou,'/');
      if panKouArray.COUNT=1 then--盘口为一个值的情况
          
          pankou1:=to_number(panKouArray(0));
          if hg-pankou1>0 then
              return '3';--大
          end if;
           if hg-pankou1<0 then
              return '4';--小
          end if;
      end if;
      
      if panKouArray.COUNT=2 then--盘口为二个值的情况
           pankou1:=to_number(panKouArray(1));
           pankou2:=to_number(panKouArray(2));
            if hg-pankou2>=0  then
                 return '3';
             end if;
             if hg-pankou2<0 then
               return '4';
             end if;
             if hg-pankou1>0 then
               return '3';
             end if;
      end if;
  end resultBigSmallXinshui;
  --亚洲盘计算方法  根据比分，盘口 心水判断 赢 走负  2010-04-26 10:39
  --'0':双方不赚不赔
  --'1':选主
  --'2':选客
  function   resultAsiaXinshui(score in varchar2,pankou in varchar2)
  return varchar2
  is
  scoreArray tools.table_list:=tools.table_list();
  panKouArray tools.table_list:=tools.table_list();
  hScore number(5);--主队
  gScore number(5);--客队
  hg number(5);
  pankou1 number(5);--盘口第一个值
  pankou2 number(5);--盘口第二个值
  begin
      dbms_output.put_line('比分='||score);
      scoreArray:=fn_split(score,':');
      hScore:=to_number(scoreArray(1));
      gScore:=to_number(scoreArray(2));
      hg:=hScore-gScore;
      panKouArray:=fn_split(pankou,'/');
      if panKouArray.COUNT=1 then--盘口为一个值的情况
      dbms_output.put_line('78行:::::::'||panKouArray(1));
          pankou1:=to_number(panKouArray(1));
          if hg-pankou1=0 then
              return '0';
          end if;
          if hg-pankou1>0 then
              return '1';
          end if;
           if hg-pankou1<0 then
              return '2';
          end if;
      end if;
      
      if panKouArray.COUNT=2 then--盘口为二个值的情况
           pankou1:=to_number(panKouArray(1));
           pankou2:=to_number(panKouArray(2));
           if hg-pankou2>0 or hg-pankou1>0 then
               return '1';
           end if;
           if hg-pankou2<0 or hg-pankou1<0 then
               return '2';
           end if;
           if hg-pankou1=0 then
               return '2';
           end if;
      end if;
     
  end resultAsiaXinshui;


  FUNCTION fn_split (p_str IN VARCHAR2, p_delimiter IN VARCHAR2)
  RETURN table_list
IS
  j INT := 0;
  i INT := 1;
  len INT := 0;
  len1 INT := 0;
  str VARCHAR2 (4000);
  str_split table_list := table_list ();
BEGIN
  len := LENGTH (p_str);
  len1 := LENGTH (p_delimiter);

  WHILE j < len
  LOOP
    j := INSTR (p_str, p_delimiter, i);

    IF j = 0
    THEN
        j := len;
        str := SUBSTR (p_str, i);
        str_split.EXTEND;
        str_split (str_split.COUNT) := str;

        IF i >= len
        THEN
          EXIT;
        END IF;
    ELSE
        str := SUBSTR (p_str, i, j - i);
        i := j + len1;
        str_split.EXTEND;
        str_split (str_split.COUNT) := str;
    END IF;
  END LOOP;

  RETURN str_split;
END fn_split;
end TOOLS;
