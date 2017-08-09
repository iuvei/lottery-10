create or replace package body team is
   
   procedure team_manager(
                    p_up_dowm        char,      --上下全场标记  全 f 上半 u下半 d
                    P_latest         char,      --查询最新赛季标记  y  注意：p_Race_id 不能为空
                    p_Race_id        number,
                    p_Race_season_id number,
                    p_ConditionSelect varchar2, --条件查询语句
                    p_Out            out TEAMVOS)
    is
  type refCursorType IS REF CURSOR;
  type teams is table of string(100) index by pls_integer;  
      v_sql  varchar2(1000); 
      against_c refCursorType;
      t_race_name varchar2(100);           
      t_race_season_name varchar2(100);    
      t_host_name varchar2(100);
      t_guest_name varchar2(100);
      t_team_type  char(1); 
      t_score     varchar2(20);
      t_race_result varchar2(10);
      t_host_goal number(4):=0;
      t_guest_goal number(4):=0;
      contains_flag1       int := -1;
      contains_flag2       int := -1;
      against_teams      teams;
      team_vo        TEAMVO;         
   
  begin
    
    team_vo := TEAMVO('','',0,'','',0,0,0,0,'','','',0,0,'','',0);
    p_Out := TEAMVOS();
   
    if p_up_dowm = 'f' then 
        v_sql := 'select t.race_name,t.race_season_name,t.host_name,t.guest_name,t.type,t.score,t.race_result,
                  to_number(substr(t.score,0,instr(t.score,'':'')-1)) as host_goal,
                  to_number(substr(t.score,instr(t.score,'':'')+1,length(t.score))) as  guest_goal                                                    
                     from t_against t  where t.status = 2 ';
    elsif  p_up_dowm = 'u' then 
        v_sql := 'select t.race_name,t.race_season_name,t.host_name,t.guest_name,t.type,t.scorea,t.race_result,
                  to_number(substr(t.scorea,0,instr(t.scorea,'':'')-1)) as host_goal,
                  to_number(substr(t.scorea,instr(t.scorea,'':'')+1,length(t.scorea))) as  guest_goal                                                    
                     from t_against t  where t.status = 2 ';
    else
        v_sql := 'select t.race_name,t.race_season_name,t.host_name,t.guest_name,t.type,t.scoreb,t.race_result,
                  to_number(substr(t.scoreb,0,instr(t.scoreb,'':'')-1)) as host_goal,
                  to_number(substr(t.scoreb,instr(t.scoreb,'':'')+1,length(t.scoreb))) as  guest_goal                                                    
                     from t_against t  where t.status = 2 ';
    end if;
    

    if  P_latest = 'y'  then v_sql := v_sql || ' and t.race_season_name = 
       (select  max (tt.race_season_name) as t_latest_season from t_against tt where tt.status = 2 and tt.race_id = ' || p_Race_id || ' ) ';
    elsif   0 != p_Race_id then v_sql := v_sql || ' and t.race_id = ' || p_Race_id;
    end if;
    
    if 0 != p_Race_season_id then v_sql := v_sql || ' and t.race_season_id = ' || p_Race_season_id;  end if;
    
    if  '1' != p_ConditionSelect then  
         v_sql := v_sql || p_ConditionSelect;
    end if;

    open against_c for v_sql;
    loop 
        fetch against_c into t_race_name,t_race_season_name,t_host_name,t_guest_name,t_team_type,t_score,t_race_result,t_host_goal,t_guest_goal;
              EXIT WHEN against_c%NOTFOUND;
          
           for x in 0..against_teams.count-1 loop
              --已包含球队
              if against_teams(x) = t_host_name then
                  contains_flag1 := 1;
              elsif against_teams(x) = t_guest_name then
                  contains_flag2 := 1;
              end if;
           end loop;
           
           -- 增加主队
           if  contains_flag1 = -1 then 
               against_teams(against_teams.count) := t_host_name;
               p_Out.extend;
               p_Out(against_teams.count) := team_vo;
               p_Out(against_teams.count).team_name := t_host_name;
               if t_team_type ='1' then  p_Out(against_teams.count).team_type := '联赛队'; 
                  elsif t_team_type ='2' then  p_Out(against_teams.count).team_type := '国家队'; 
                  else p_Out(against_teams.count).team_type := '其他队'; 
               end if;
               p_Out(against_teams.count).team_race_name  := t_race_name;
               p_Out(against_teams.count).team_race_season_name := t_race_season_name;
               
           end if;
           
           --增加客队
           if contains_flag2 = -1 then 
               against_teams(against_teams.count) := t_guest_name;
               p_Out.extend;
               p_Out(against_teams.count) := team_vo;
               p_Out(against_teams.count).team_name := t_guest_name;
               if t_team_type ='1' then  p_Out(against_teams.count).team_type := '联赛队'; 
                  elsif t_team_type ='2' then  p_Out(against_teams.count).team_type := '国家队'; 
                  else p_Out(against_teams.count).team_type := '其他队'; 
               end if;
               p_Out(against_teams.count).team_race_name  := t_race_name;
               p_Out(against_teams.count).team_race_season_name := t_race_season_name;
           end if;  
           
           /*
           DBMS_OUTPUT.PUT_LINE('-------------------------------');
            for i in 1..against_teams.count loop
                 DBMS_OUTPUT.PUT_LINE(p_Out(i).team_name);
             end loop;
            DBMS_OUTPUT.PUT_LINE('-------------------------------');
            DBMS_OUTPUT.PUT_LINE('主队：'|| t_host_name ||'客队：'||t_guest_name);
           */
           for i in 1..against_teams.count loop
               --主场
              if p_Out(i).team_name = t_host_name then 
                  p_Out(i).team_showing := p_Out(i).team_showing +1;
                  if t_race_result = '3' then  
                     p_Out(i).win_showing := p_Out(i).win_showing +1;
                  elsif t_race_result = '1' then  
                     p_Out(i).equal_showing := p_Out(i).equal_showing +1;
                  else  
                     p_Out(i).lose_showing := p_Out(i).lose_showing +1;
                  end if;
                  p_Out(i).team_scores :=  p_Out(i).team_scores + t_race_result;
                  p_Out(i).in_goals := p_Out(i).in_goals + t_host_goal;
                  p_Out(i).lose_goals := p_Out(i).lose_goals + t_guest_goal;
                  
               --客场
              elsif p_Out(i).team_name = t_guest_name then 
                  p_Out(i).team_showing := p_Out(i).team_showing +1;
                  if t_race_result = '3' then  
                     p_Out(i).lose_showing := p_Out(i).lose_showing +1;
                  elsif t_race_result = '1' then  
                     p_Out(i).equal_showing := p_Out(i).equal_showing +1;
                     p_Out(i).team_scores :=  p_Out(i).team_scores + 1;
                  else 
                     p_Out(i).win_showing := p_Out(i).win_showing +1;
                     p_Out(i).team_scores :=  p_Out(i).team_scores + 3;
                  end if;
                  p_Out(i).in_goals := p_Out(i).in_goals + t_guest_goal;
                  p_Out(i).lose_goals := p_Out(i).lose_goals + t_host_goal;
             end if;
           end loop; 
           
          contains_flag1 := -1; 
          contains_flag2 := -1; 
          
     end loop;     
        
        for i in 1..against_teams.count loop
          if p_Out(i).team_showing != 0 then
            p_Out(i).win_rate := to_char( floor(p_Out(i).win_showing *10000/ p_Out(i).team_showing+0.5)/100.00);
            p_Out(i).equal_rate := to_char( floor(p_Out(i).equal_showing *10000/ p_Out(i).team_showing+0.5)/100.00);
            p_Out(i).lose_rate := to_char( floor(p_Out(i).lose_showing *10000/ p_Out(i).team_showing+0.5)/100.00);
         
            p_Out(i).in_goals_avg := to_char( floor(p_Out(i).in_goals *100/ p_Out(i).team_showing+0.5)/100);
            p_Out(i).lose_goals_avg := to_char( floor(p_Out(i).lose_goals *100/ p_Out(i).team_showing+0.5)/100); 
          end if;
          p_Out(i).goal_difference := p_Out(i).in_goals - p_Out(i).lose_goals;
        end loop;
        
        DBMS_OUTPUT.PUT_LINE('球队名称'||'--' ||'场次' ||'--' ||'球队类型' 
        ||'--' ||'总进球数'||'--'||'总失球数'||'--'||'胜'||'--'||'平'
        ||'--'||'负' ||'--'||'平均得球'||'--'||'平均失球'||'--'||'总积分'
        ||'--'||'净胜球'  );
       for x in 1..against_teams.count loop
           DBMS_OUTPUT.PUT_LINE(p_Out(x).team_name||'--' ||
           p_Out(x).team_showing ||'--' ||
           p_Out(x).team_type  ||'--' ||
           p_Out(x).in_goals    ||'--' ||
           p_Out(x).lose_goals  ||'--' ||
           p_Out(x).win_rate ||'--' ||
           p_Out(x).equal_rate  ||'--' ||
           p_Out(x).lose_rate  ||'--' ||
             
           p_Out(x).in_goals_avg ||'--' ||
           p_Out(x).lose_goals_avg ||'--' ||
           p_Out(x).team_scores ||'  ' ||
           p_Out(x).goal_difference ||'  ' ||
           p_Out(x).team_scores
           );   
           
       end loop;  
       
    CLOSE against_c;
 end team_manager;
end team;     
