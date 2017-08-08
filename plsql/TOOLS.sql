create or replace package body TOOLS is

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
