-- Create table
create table T_ARENA
(
  ARENA_ID   NUMBER(20) not null,
  HOST_TEAM  VARCHAR2(100) not null,
  GUEST_TEAM VARCHAR2(100) not null,
  RACE_TIME  DATE not null,
  AVG_INDEX  VARCHAR2(100),
  PHASE      VARCHAR2(10) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table T_ARENA
  is '擂台赛赛程   指定哪一期是本期  哪一期是下期  在彩票管理表里指定';
-- Add comments to the columns 
comment on column T_ARENA.ARENA_ID
  is '主键';
comment on column T_ARENA.HOST_TEAM
  is '主队';
comment on column T_ARENA.GUEST_TEAM
  is '客队';
comment on column T_ARENA.RACE_TIME
  is '开赛时间';
comment on column T_ARENA.AVG_INDEX
  is '平均指数';
comment on column T_ARENA.PHASE
  is '第几期';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_ARENA
  add constraint ARENA_PK_ID primary key (ARENA_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
