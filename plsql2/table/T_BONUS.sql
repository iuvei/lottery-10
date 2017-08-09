-- Create table
create table T_BONUS
(
  ID           NUMBER(20) not null,
  PHASE        VARCHAR2(20),
  USERNAME     VARCHAR2(50),
  USER_ID      NUMBER(20),
  LOTTERY_TYPE VARCHAR2(3),
  LOTTERY_NAME VARCHAR2(100),
  MONEY        NUMBER(20,2),
  BONUS_CLASS  NUMBER(3),
  KJ_TIME      DATE default sysdate,
  FLG          NUMBER(1)
)
tablespace LOTTERY
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table T_BONUS
  is '中奖表';
-- Add comments to the columns 
comment on column T_BONUS.ID
  is '主键';
comment on column T_BONUS.PHASE
  is '期数';
comment on column T_BONUS.USERNAME
  is '用户名';
comment on column T_BONUS.USER_ID
  is '用户ID';
comment on column T_BONUS.LOTTERY_TYPE
  is '彩票种类';
comment on column T_BONUS.LOTTERY_NAME
  is '彩票玩法';
comment on column T_BONUS.MONEY
  is '奖金';
comment on column T_BONUS.BONUS_CLASS
  is '1:一等奖   2  二等奖   3   三等奖';
comment on column T_BONUS.KJ_TIME
  is '开奖时间';
comment on column T_BONUS.FLG
  is '1 代购   2 发起合买  3 参与合买';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_BONUS
  add constraint BONUS_PK_ID primary key (ID)
  using index 
  tablespace LOTTERY
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
