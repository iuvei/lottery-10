create or replace package body LOTTERY_COOP_KQ is
--期次分类  '6':胜负彩期次    '9':进球彩期次 '8':半全场期次 '1':北京单场期次
procedure  coopKQ(i_category in VARCHAR2)
is
mulDeadline DATE;
realFloorMoney number(10,2);--发起人到最后一刻需要保底的份数
surplusCopys number(10);--剩余份数
bc  varchar2(1); --彩种
bcZH varchar2(100);--彩种中文
deadLine DATE;--截止时间

begin
    --先为胜负彩14场扣款
    
    for v in(select t.id,t.category,t.mul_deadline from t_lottery_phase t where t.status='4' and t.category=i_category) loop
           if i_category in ('6','9','8') then
              deadLine:=v.mul_deadline;--胜负彩期次,进球彩期次,半全场期次:以复式截止时间为截止时间
           end if;
           if i_category='1' then
               select max(a.start_time) into deadLine  from t_lottery_against la,t_against a where la.against_id=a.against_id and la.phase_id=v.id;
           end if;
           if deadLine<=sysdate then--复式截止时间已经到达
           update t_lottery_phase m set m.status='7' where m.id=v.id;--'7':已到期
           
           for vi in(select BO.BET_CATEGOTY,BO.TYPE,BO.SINGLE_MONEY,bo.divide_copys,bo.floor_copys,bo.already_buy_copys,bo.bet_id,bo.subscribe_money,bo.bet_userid 
                    from t_bet_order bo where bo.sponsor_type='1'  and bo.bet_categoty in ('1','2')) loop--发起人
                     bc:=vi.bet_categoty;
           --已经认购+保底小于90% 则此方案失败
           if  (vi.already_buy_copys+vi.floor_copys)<vi.divide_copys*0.9  then
              --1.1解冻发起人的金额
                 update t_virtual_account va set va.frozen_money=va.frozen_money-(vi.subscribe_money+vi.Floor_Copys*VI.Single_Money) 
                 where va.tx_user_id=vi.bet_userid;
              --1.2
                
                 if bc='1' then
                    bcZH:='发起合买胜负彩14场未满员';
                 elsif bc='2' then
                    bcZH:='发起合买任9场未满员';
                 elsif bc='3' then
                    bcZH:='发起合买4场进球未满员';
                 elsif bc='5' then
                    bcZH:='发起合买6场半全场未满员';
                 else
                    bcZH:='发起合买足球单场未满员';
                 end if;
                 insert into T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
                 values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,vi.bet_userid,vi.subscribe_money+vi.Floor_Copys*VI.Single_Money,sysdate,
                 bcZH,'4',vi.bet_id,'6',to_char(sysdate,'yyyymmdd')||'ZC'||SEQ_ORDER_NO.NEXTVAL||to_char(DBMS_RANDOM.VALUE(100,999),'999')
                 ,'1');
                 
              --2.解冻参与人的金额
                 for vc in(select BO.SINGLE_MONEY,bo.divide_copys,bo.floor_copys,bo.already_buy_copys,bo.bet_id,bo.subscribe_money,bo.bet_userid 
                       from t_bet_order bo where bo.sponsor_bet_id=vi.bet_id) loop--参与人列表
                       --2.1解冻账户
                       update t_virtual_account va set va.frozen_money=va.frozen_money-vi.subscribe_money
                       where va.tx_user_id=vc.bet_userid;
                       --2.2插入解冻日志
                       
                       insert into T_VA_FROZEN_LOG(FROZEN_ID,TX_USER_ID,FROZEN_MONEY,FROZEN_DATE,MEMO,TX_TYPE,ORDER_ID,CATEGORY_TYPE,ORDER_NO,FLG)
                       values(SEQ_VA_FROZEN_LOG_ID.NEXTVAL,vc.bet_userid,vc.subscribe_money,sysdate,
                      bcZH,'4',vc.bet_id,'6',to_char(sysdate,'yyyymmdd')||'ZC'||SEQ_ORDER_NO.NEXTVAL||to_char(DBMS_RANDOM.VALUE(100,999),'999')
                       ,'1');
                 end loop;
           
           
           
           
           end if;
           --满员
           if  vi.already_buy_copys+vi.floor_copys>=vi.divide_copys  then
                  --实际保底金额
                  realFloorMoney:=(vi.divide_copys-vi.already_buy_copys)*vi.single_money;
                  --3.1 发起人解冻(金额与保底金额)
                  update t_virtual_account va3  set va3.frozen_money=va3.frozen_money-vi.subscribe_money-vi.floor_copys*vi.single_money
                  where va3.tx_user_id=vi.bet_userid;
                  --3.2 发起人扣款
                  update t_virtual_account tva  set  tva.all_money=tva.all_money-realFloorMoney-vi.subscribe_money
                  where tva.tx_user_id=vi.bet_userid;
                  --插入发起人扣款交易日志
                  if bc='1' then
                    bcZH:='合买胜负彩14场';
                  elsif bc='2' then
                    bcZH:='合买任9场';
                   elsif bc='3' then
                    bcZH:='合买4场进球';
                  elsif bc='5' then
                    bcZH:='合买6场半全场';
                  else
                    bcZH:='合买足球单场';
                  end if;
                  insert into T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_DATE,TX_TYPE,ORDER_ID,FLG,ORDER_NO,TX_TYPE_NAME,TARGET_USERID)
                  values(SEQ_VA_TRANSACTION_LOG_ID.NEXTVAL,vi.bet_userid,realFloorMoney+vi.subscribe_money,sysdate,'4',vi.bet_id,'2',
                  to_char(sysdate,'yyyymmdd')||'ZC'||SEQ_ORDER_NO.NEXTVAL||to_char(DBMS_RANDOM.VALUE(100,999),'999'),
                  bcZH,vi.bet_userid);
                  for vj in(select bo.sponsor_bet_id,bo.subscribe_money,bo.bet_userid from t_bet_order bo where bo.sponsor_bet_id=vi.bet_id) loop--参与人
                       --第一步解冻
                       update t_virtual_account va4  set va4.frozen_money=va4.frozen_money-vj.subscribe_money
                       where va4.tx_user_id=vj.bet_userid;
                      --第二部扣款
                       update t_virtual_account t4  set  t4.all_money=t4.all_money-vj.subscribe_money
                       where t4.tx_user_id=vj.bet_userid;
                     --产生扣款交易日志
                       insert into T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_DATE,TX_TYPE,ORDER_ID,FLG,ORDER_NO,TX_TYPE_NAME,TARGET_USERID)
                       values(SEQ_VA_TRANSACTION_LOG_ID.NEXTVAL,vj.bet_userid,realFloorMoney+vj.subscribe_money,sysdate,'4',vi.bet_id,'2',
                       to_char(sysdate,'yyyymmdd')||'ZC'||SEQ_ORDER_NO.NEXTVAL||to_char(DBMS_RANDOM.VALUE(100,999),'999'),
                       bcZH,vi.bet_userid);
                  end loop;
           
           end if;
           
           --需要天盛公司去认购剩余的金额
           if  (vi.already_buy_copys+vi.floor_copys)>=vi.divide_copys*0.9 and  (vi.already_buy_copys+vi.floor_copys)<vi.divide_copys then
              --1.1解冻发起人的金额
                 update t_virtual_account va set va.frozen_money=va.frozen_money-(vi.subscribe_money+vi.Floor_Copys*VI.Single_Money) 
                 where va.tx_user_id=vi.bet_userid;
             --1.2 发起人扣款
                 update t_virtual_account va set va.frozen_money=va.all_money-(vi.subscribe_money+vi.Floor_Copys*VI.Single_Money) 
                 where va.tx_user_id=vi.bet_userid;
                  if bc='1' then
                    bcZH:='合买胜负彩14场';
                  elsif bc='2' then
                    bcZH:='合买任9场';
                    elsif bc='3' then
                    bcZH:='合买4场进球';
                  elsif bc='5' then
                    bcZH:='合买6场半全场';
                  else
                    bcZH:='合买足球单场';
                  end if;
                  insert into T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_DATE,TX_TYPE,ORDER_ID,FLG,ORDER_NO,TX_TYPE_NAME,TARGET_USERID)
                  values(SEQ_VA_TRANSACTION_LOG_ID.NEXTVAL,vi.bet_userid,vi.subscribe_money+vi.Floor_Copys*VI.Single_Money,sysdate,'4',vi.bet_id,'2',
                  to_char(sysdate,'yyyymmdd')||'ZC'||SEQ_ORDER_NO.NEXTVAL||to_char(DBMS_RANDOM.VALUE(100,999),'999'),
                  bcZH,vi.bet_userid);
              --1.3 天盛公司(WINTV_USERID)认购剩余份数
                  surplusCopys:=vi.divide_copys-vi.already_buy_copys-vi.floor_copys;
                 insert into t_bet_order(bet_id,bet_time,bet_userid,bet_username,type,subscribe_copys,subscribe_money)values
                 (SEQ_BET_ORDER_ID.NEXTVAL,sysdate,WINTV_USERID,WINTV_USERNAME,vi.type,surplusCopys,surplusCopys*vi.single_money);
           
                 
              --2.解冻参与人的金额
                 for vc in(select BO.SINGLE_MONEY,bo.divide_copys,bo.floor_copys,bo.already_buy_copys,bo.bet_id,bo.subscribe_money,bo.bet_userid 
                       from t_bet_order bo where bo.sponsor_bet_id=vi.bet_id) loop--参与人列表
                       --2.1解冻账户
                       update t_virtual_account va set va.frozen_money=va.frozen_money-vi.subscribe_money
                       where va.tx_user_id=vc.bet_userid;
                       --2.2扣款
                       update t_virtual_account vm set vm.all_money=vm.all_money-vc.subscribe_money
                       where vm.tx_user_id=vc.bet_userid;
                       --2.3插入扣款日志
                        if bc='1' then
                          bcZH:='合买胜负彩14场';
                        elsif bc='2' then
                          bcZH:='合买任9场';
                         elsif bc='3' then
                          bcZH:='合买4场进球';
                         elsif bc='5' then
                          bcZH:='合买6场半全场';
                         else
                           bcZH:='合买足球单场';
                         end if;
                       insert into T_VA_TRANSACTION_LOG(VAT_ID,TX_USER_ID,TX_MONEY,TX_DATE,TX_TYPE,ORDER_ID,FLG,ORDER_NO,TX_TYPE_NAME,TARGET_USERID)
                       values(SEQ_VA_TRANSACTION_LOG_ID.NEXTVAL,vc.bet_userid,vc.subscribe_money,sysdate,'4',vi.bet_id,'2',
                       to_char(sysdate,'yyyymmdd')||'ZC'||SEQ_ORDER_NO.NEXTVAL||to_char(DBMS_RANDOM.VALUE(100,999),'999'),
                       bcZH,vi.bet_userid);
                 end loop;
           
           
           
           
           end if;
           
           
           
           
           --------
            
           
          
          end loop;
          end if;
      end loop;
      
      
      
   end;
end LOTTERY_COOP_KQ;
