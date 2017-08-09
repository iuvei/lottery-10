-- Create table
create table T_BET_ORDER_CHOICE
(
  CHOICE_ID    NUMBER(20) not null,
  AGAINST_ID   NUMBER(20),
  BET_ORDER_ID NUMBER(20),
  BET_PLAN     VARCHAR2(50),
  DAN_CODE     VARCHAR2(50),
  CHANGCI      NUMBER(10)
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
comment on table T_BET_ORDER_CHOICE
  is '对阵选择';
-- Add comments to the columns 
comment on column T_BET_ORDER_CHOICE.CHOICE_ID
  is '主键';
comment on column T_BET_ORDER_CHOICE.AGAINST_ID
  is '对阵ID';
comment on column T_BET_ORDER_CHOICE.BET_ORDER_ID
  is '投注订单ID    关联T_BET_ORDER表';
comment on column T_BET_ORDER_CHOICE.BET_PLAN
  is '用户投注结果';
comment on column T_BET_ORDER_CHOICE.DAN_CODE
  is '胆码';
comment on column T_BET_ORDER_CHOICE.CHANGCI
  is '场次 由体彩中心提供(无效)';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_BET_ORDER_CHOICE
  add constraint CHOICE_PK_ID primary key (CHOICE_ID)
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
