-- Create table
create table T_BACKEND_USER
(
  USERID    NUMBER(20) not null,
  EMAIL     VARCHAR2(50) not null,
  PWD       VARCHAR2(32) not null,
  ROLE      CHAR(1) not null,
  NAME      VARCHAR2(50),
  MP        VARCHAR2(11),
  STATUS    CHAR(1),
  QQ        VARCHAR2(20),
  REG_TIME  DATE,
  DEPT_NAME VARCHAR2(60),
  DEPT_CODE VARCHAR2(60)
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
comment on table T_BACKEND_USER
  is '废弃表';
-- Add comments to the columns 
comment on column T_BACKEND_USER.USERID
  is '用户ID';
comment on column T_BACKEND_USER.EMAIL
  is '邮件';
comment on column T_BACKEND_USER.PWD
  is '密码';
comment on column T_BACKEND_USER.ROLE
  is '角色';
comment on column T_BACKEND_USER.NAME
  is '姓名';
comment on column T_BACKEND_USER.MP
  is '手机';
comment on column T_BACKEND_USER.STATUS
  is '状态';
comment on column T_BACKEND_USER.QQ
  is 'QQ';
comment on column T_BACKEND_USER.REG_TIME
  is '管理员时间';
comment on column T_BACKEND_USER.DEPT_NAME
  is '部门名称';
comment on column T_BACKEND_USER.DEPT_CODE
  is '部门CODE关联T_DICTIONARY';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_BACKEND_USER
  add constraint BACKEND_USERID primary key (USERID)
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
