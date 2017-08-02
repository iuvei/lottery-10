














         --查询所有当前期 
          select  *  from  T_LOTTERY_SCHEDULE  t  where  t.STATUS='4'  and t.TKP_DEADLINE >sysdate  and  SOLD_TIME <=sysdate
         --查询所有预售期
          select  *  from  T_LOTTERY_SCHEDULE  t  where  t.STATUS='4'  and t.TKP_DEADLINE >sysdate  and  SOLD_TIME >sysdate

          
          