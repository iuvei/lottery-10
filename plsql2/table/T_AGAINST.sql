-- Create table
create table T_AGAINST
(
  AGAINST_ID       NUMBER(10) not null,
  HOST_NAME        VARCHAR2(60),
  GUEST_NAME       VARCHAR2(60),
  START_TIME       DATE,
  RACE_NAME        VARCHAR2(100),
  RACE_SEASON_ID   NUMBER(10),
  ROUNDS           NUMBER(10),
  STATUS           CHAR(1) default '1',
  ASIA_PEI         VARCHAR2(60),
  BIG_SMALL        VARCHAR2(60),
  TYPE             CHAR(1),
  RACE_ID          NUMBER(20),
  HOST_ID          NUMBER(20),
  GUEST_ID         NUMBER(20),
  AREA_ID          NUMBER(20),
  COUNTRY_ID       NUMBER(20),
  SCOREA           VARCHAR2(20),
  RACE_RESULT      CHAR(1),
  CONCEDE          VARCHAR2(10),
  RACE_SEASON_NAME VARCHAR2(100),
  ROUNDS_NAME      VARCHAR2(100),
  BG_COLOR         VARCHAR2(100),
  CONCEDE_RESULT   VARCHAR2(100),
  SCOREB           VARCHAR2(20),
  SCORE            VARCHAR2(20)
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
comment on table T_AGAINST
  is '对阵表(对阵信息数据量超过体彩中心的期次对阵数据量)';
-- Add comments to the columns 
comment on column T_AGAINST.AGAINST_ID
  is ' 主键';
comment on column T_AGAINST.HOST_NAME
  is '主队名称';
comment on column T_AGAINST.GUEST_NAME
  is '客队名称';
comment on column T_AGAINST.START_TIME
  is '开赛时间';
comment on column T_AGAINST.RACE_NAME
  is '赛事名称';
comment on column T_AGAINST.RACE_SEASON_ID
  is '赛季(或届)  关联T_RACE表ID';
comment on column T_AGAINST.ROUNDS
  is '轮次,关联T_RACE表ID';
comment on column T_AGAINST.STATUS
  is '状态  1.“未完赛”、2.“已完赛”、3.
“已取消”';
comment on column T_AGAINST.ASIA_PEI
  is '亚盘';
comment on column T_AGAINST.BIG_SMALL
  is '大小盘';
comment on column T_AGAINST.TYPE
  is '赛事类型''1'':联赛   ''2'':杯赛   ''3'':其他';
comment on column T_AGAINST.RACE_ID
  is '赛事ID';
comment on column T_AGAINST.HOST_ID
  is '主队ID';
comment on column T_AGAINST.GUEST_ID
  is '客队ID';
comment on column T_AGAINST.AREA_ID
  is '区域ID';
comment on column T_AGAINST.COUNTRY_ID
  is '国家ID';
comment on column T_AGAINST.SCOREA
  is '上半场比分 ';
comment on column T_AGAINST.RACE_RESULT
  is '赛果';
comment on column T_AGAINST.CONCEDE
  is '让球';
comment on column T_AGAINST.RACE_SEASON_NAME
  is '赛季(或届)  的名称';
comment on column T_AGAINST.ROUNDS_NAME
  is '轮次名称';
comment on column T_AGAINST.BG_COLOR
  is '联赛颜色';
comment on column T_AGAINST.CONCEDE_RESULT
  is '让球赛果';
comment on column T_AGAINST.SCOREB
  is '下半场比分';
comment on column T_AGAINST.SCORE
  is '全部比分';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_AGAINST
  add constraint T_SCHEDULE_PK_ID primary key (AGAINST_ID)
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
