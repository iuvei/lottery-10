-- Create table
create table T_BET_HOT_SEARCH
(
  HOT_ID       NUMBER(20),
  USERID       NUMBER(20),
  USERNAME     VARCHAR2(300),
  BET_ID       NUMBER(20),
  BET_CATEGORY VARCHAR2(2),
  PHASE_NO     VARCHAR2(16),
  PHASE_ID     NUMBER(20)
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
comment on table T_BET_HOT_SEARCH
  is '热门发起订单(合买)';
-- Add comments to the columns 
comment on column T_BET_HOT_SEARCH.HOT_ID
  is '主键';
comment on column T_BET_HOT_SEARCH.USERID
  is '热门发起人用户ID';
comment on column T_BET_HOT_SEARCH.USERNAME
  is '热门发起人用户名';
comment on column T_BET_HOT_SEARCH.BET_ID
  is '订单ID';
comment on column T_BET_HOT_SEARCH.BET_CATEGORY
  is '彩种';
comment on column T_BET_HOT_SEARCH.PHASE_NO
  is '期次号';
comment on column T_BET_HOT_SEARCH.PHASE_ID
  is '期次主键';
