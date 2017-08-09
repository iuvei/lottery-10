-- Create table
create table T_BANK
(
  ID              NUMBER(5) not null,
  NAME            VARCHAR2(60),
  IMG             VARCHAR2(100),
  URL             VARCHAR2(100),
  CHARGE_STATUS   CHAR(1),
  CODE            VARCHAR2(16) not null,
  WITHDRAW_STATUS CHAR(1)
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
-- Add comments to the columns 
comment on column T_BANK.ID
  is '主键';
comment on column T_BANK.NAME
  is '中文名称';
comment on column T_BANK.IMG
  is '图片的地址';
comment on column T_BANK.URL
  is '网银地址';
comment on column T_BANK.CHARGE_STATUS
  is '是否可用    ''1'':可用  ''2'':不可用';
comment on column T_BANK.CODE
  is '代码';
comment on column T_BANK.WITHDRAW_STATUS
  is '是否可用    ''1'':可用  ''2'':不可用';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_BANK
  add constraint BANL_PK_ID primary key (ID)
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
