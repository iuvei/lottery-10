-- Create table
create table T_4_SCENE_GOAL
(
  SG_ID                NUMBER(20) not null,
  NO                   VARCHAR2(80),
  MULTIPLE             NUMBER(5),
  COPYS                NUMBER(5),
  RECRUIT_USERS        VARCHAR2(500),
  PRIVATE_TYPE         CHAR(1),
  AUTO_SUBSCRIBE       NUMBER(5),
  IS_FLOOR             CHAR(1),
  SPONSOR_USERID       NUMBER(20),
  USER_ID              NUMBER(20),
  BET_TIME             DATE,
  PHASE                VARCHAR2(10),
  PARENT_ID            NUMBER(20),
  TYPE                 CHAR(2),
  ALL_MONEY            NUMBER(20,2),
  AUTO_SUBSCRIBE_MONEY NUMBER(10,2),
  BONUS                NUMBER(20,2),
  PLAN_CODE            VARCHAR2(50)
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
comment on table T_4_SCENE_GOAL
  is '四场进球';
-- Add comments to the columns 
comment on column T_4_SCENE_GOAL.SG_ID
  is '主键';
comment on column T_4_SCENE_GOAL.NO
  is '号码(使用逗号隔开每一场结果) 与方案同一意思';
comment on column T_4_SCENE_GOAL.MULTIPLE
  is '倍数';
comment on column T_4_SCENE_GOAL.COPYS
  is '份数';
comment on column T_4_SCENE_GOAL.RECRUIT_USERS
  is '招股对象';
comment on column T_4_SCENE_GOAL.PRIVATE_TYPE
  is '保密类型';
comment on column T_4_SCENE_GOAL.AUTO_SUBSCRIBE
  is '认购份数';
comment on column T_4_SCENE_GOAL.IS_FLOOR
  is '是否保底';
comment on column T_4_SCENE_GOAL.SPONSOR_USERID
  is '发起人用户ID';
comment on column T_4_SCENE_GOAL.USER_ID
  is '投注人ID';
comment on column T_4_SCENE_GOAL.BET_TIME
  is '投注时间';
comment on column T_4_SCENE_GOAL.PHASE
  is '第几期';
comment on column T_4_SCENE_GOAL.PARENT_ID
  is '发起方案ID(当参与合买时关联本表主键否则为空)';
comment on column T_4_SCENE_GOAL.TYPE
  is '类型 91单代 92 单合  93 复代 94复合';
comment on column T_4_SCENE_GOAL.ALL_MONEY
  is '总金额';
comment on column T_4_SCENE_GOAL.AUTO_SUBSCRIBE_MONEY
  is '自认金额';
comment on column T_4_SCENE_GOAL.BONUS
  is '奖金';
comment on column T_4_SCENE_GOAL.PLAN_CODE
  is '方案编号';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_4_SCENE_GOAL
  add constraint PK_T_4_SCENE_GOAL primary key (SG_ID)
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
  
  
  -- Create table
create table T_4_SCENE_GOAL_SCHEDULE
(
  FSGS_ID    NUMBER(20),
  HOST_TEAM  VARCHAR2(100),
  START_TIME DATE,
  AVG_INDEX  VARCHAR2(100),
  GUEST_TEAM VARCHAR2(100),
  PHASE      VARCHAR2(10),
  HOST_GOAL  NUMBER(3),
  GUEST_GOAL NUMBER(3)
)


tablespace LOTTERY
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
comment on table T_4_SCENE_GOAL_SCHEDULE
  is '四场进球赛场表';
-- Add comments to the columns 
comment on column T_4_SCENE_GOAL_SCHEDULE.FSGS_ID
  is '赛程ID';
comment on column T_4_SCENE_GOAL_SCHEDULE.HOST_TEAM
  is '主队';
comment on column T_4_SCENE_GOAL_SCHEDULE.START_TIME
  is '开赛时间';
comment on column T_4_SCENE_GOAL_SCHEDULE.AVG_INDEX
  is '平均指数';
comment on column T_4_SCENE_GOAL_SCHEDULE.GUEST_TEAM
  is '客队';
comment on column T_4_SCENE_GOAL_SCHEDULE.PHASE
  is '第几期';
comment on column T_4_SCENE_GOAL_SCHEDULE.HOST_GOAL
  is '主队进球数';
comment on column T_4_SCENE_GOAL_SCHEDULE.GUEST_GOAL
  is '客队进球数';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_4_SCENE_GOAL_SCHEDULE
  add constraint FSGS_PK_ID primary key (FSGS_ID)
  disable;
  
  
  -- Create table
create table T_6_HALF_COMPLETE
(
  SIX_HC_ID            NUMBER(20) not null,
  THREE                NUMBER(3),
  TWO                  NUMBER(3),
  ONE                  NUMBER(3),
  BET_MULTIPLE         NUMBER(10),
  DIVIED_COPYS         NUMBER(10),
  RECRUIT_USERS        VARCHAR2(500),
  PRIVATE_TYPE         CHAR(1),
  AUTO_SUBSCRIBE       NUMBER(5),
  IS_FLOOR             CHAR(1),
  FLOOR_COPYS          NUMBER(20),
  TYPE                 CHAR(2),
  PLAN                 VARCHAR2(200),
  ALL_MONEY            NUMBER(10,2),
  AUTO_SUBSCRIBE_MONEY NUMBER(10,2),
  BET_USER_ID          NUMBER(20),
  SPONSOR_USER_ID      NUMBER(20),
  PHASE                VARCHAR2(10),
  BET_DATE             DATE
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
comment on table T_6_HALF_COMPLETE
  is '六场半全场';
-- Add comments to the columns 
comment on column T_6_HALF_COMPLETE.SIX_HC_ID
  is '主键';
comment on column T_6_HALF_COMPLETE.THREE
  is '三选个数';
comment on column T_6_HALF_COMPLETE.TWO
  is '双选个数';
comment on column T_6_HALF_COMPLETE.ONE
  is '单选个数';
comment on column T_6_HALF_COMPLETE.BET_MULTIPLE
  is '投注倍数';
comment on column T_6_HALF_COMPLETE.DIVIED_COPYS
  is '分成';
comment on column T_6_HALF_COMPLETE.RECRUIT_USERS
  is '招股对象';
comment on column T_6_HALF_COMPLETE.PRIVATE_TYPE
  is '保密类型';
comment on column T_6_HALF_COMPLETE.AUTO_SUBSCRIBE
  is '自己认购份数';
comment on column T_6_HALF_COMPLETE.IS_FLOOR
  is '是否保底';
comment on column T_6_HALF_COMPLETE.FLOOR_COPYS
  is '保底份数';
comment on column T_6_HALF_COMPLETE.TYPE
  is '类型 81单代 82 单合  83 复代 84复合';
comment on column T_6_HALF_COMPLETE.PLAN
  is '方案';
comment on column T_6_HALF_COMPLETE.ALL_MONEY
  is '总金额';
comment on column T_6_HALF_COMPLETE.AUTO_SUBSCRIBE_MONEY
  is '自己购买的金额';
comment on column T_6_HALF_COMPLETE.BET_USER_ID
  is '投注人ID';
comment on column T_6_HALF_COMPLETE.SPONSOR_USER_ID
  is '发起投注的用户ID';
comment on column T_6_HALF_COMPLETE.PHASE
  is '期数';
comment on column T_6_HALF_COMPLETE.BET_DATE
  is '投注时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_6_HALF_COMPLETE
  add constraint PK_T_6_HALF_COMPLETE primary key (SIX_HC_ID)
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
  
  
  -- Create table
create table T_6_HALF_COMPLETE_SCHEDULE
(
  SIX_HCS_ID NUMBER(20) not null,
  RACE_TYPE  VARCHAR2(100),
  START_TIME DATE,
  END_TIME   DATE,
  HOST_TEAM  VARCHAR2(100),
  GUEST_TEAM VARCHAR2(100),
  SCORE      VARCHAR2(100),
  DATA       VARCHAR2(100)
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
comment on table T_6_HALF_COMPLETE_SCHEDULE
  is '6场半全场';
-- Add comments to the columns 
comment on column T_6_HALF_COMPLETE_SCHEDULE.SIX_HCS_ID
  is '主键';
comment on column T_6_HALF_COMPLETE_SCHEDULE.RACE_TYPE
  is '赛事类型';
comment on column T_6_HALF_COMPLETE_SCHEDULE.START_TIME
  is '开赛时间';
comment on column T_6_HALF_COMPLETE_SCHEDULE.END_TIME
  is '结束时间';
comment on column T_6_HALF_COMPLETE_SCHEDULE.HOST_TEAM
  is '主队';
comment on column T_6_HALF_COMPLETE_SCHEDULE.GUEST_TEAM
  is '客队';
comment on column T_6_HALF_COMPLETE_SCHEDULE.SCORE
  is '比分';
comment on column T_6_HALF_COMPLETE_SCHEDULE.DATA
  is '数据';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_6_HALF_COMPLETE_SCHEDULE
  add constraint SIX_HCS_ID primary key (SIX_HCS_ID)
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
  
  
  -- Create table
create table T_ADV
(
  ADV_ID  NUMBER(20) not null,
  TITLE   VARCHAR2(100),
  CONTENT CLOB,
  ATTACH  VARCHAR2(200)
)


tablespace LOTTERY
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
comment on table T_ADV
  is '广告';
-- Add comments to the columns 
comment on column T_ADV.ADV_ID
  is '主键';
comment on column T_ADV.TITLE
  is '标题';
comment on column T_ADV.CONTENT
  is '主要内容';
comment on column T_ADV.ATTACH
  is '附件';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_ADV
  add constraint T_ADV_PK_ID primary key (ADV_ID)
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
  
  
  -- Create table
create table T_ARBITRARY_9
(
  A9_ID           NUMBER(20) not null,
  VICI_WAY        VARCHAR2(100),
  FORMAT_STR      VARCHAR2(100),
  SINGLE_MONEY    NUMBER(20,2),
  MULTIPLE        NUMBER(5),
  PLAN            VARCHAR2(200),
  DIVIDED_COPYS   NUMBER(8),
  DEDUCT          NUMBER(2,2),
  SUBSCRIBE_COPYS NUMBER(10),
  IS_FLOOR        CHAR(1),
  PRIVATE_TYPE    CHAR(1),
  RECRUIT_USERS   VARCHAR2(500),
  TYPE            CHAR(2),
  BET_USER_ID     NUMBER(20),
  BET_DATE        DATE,
  PARENT_ID       NUMBER(20),
  ALL_MONEY       NUMBER(20,2),
  PLAN_CODE       VARCHAR2(50)
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
comment on table T_ARBITRARY_9
  is '任选9场';
-- Add comments to the columns 
comment on column T_ARBITRARY_9.A9_ID
  is '主键';
comment on column T_ARBITRARY_9.DIVIDED_COPYS
  is '我要分成几份';
comment on column T_ARBITRARY_9.SUBSCRIBE_COPYS
  is '认购几份';
comment on column T_ARBITRARY_9.IS_FLOOR
  is '是否保底';
comment on column T_ARBITRARY_9.PRIVATE_TYPE
  is '保密类型';
comment on column T_ARBITRARY_9.RECRUIT_USERS
  is '招股对象';
comment on column T_ARBITRARY_9.TYPE
  is '类型    71:单式代购  72:单式合买   73:复式代购  74:复式合买';
comment on column T_ARBITRARY_9.PLAN_CODE
  is '方案编号';
  
  
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


tablespace LOTTERY
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
  
  
  -- Create table
create table T_ARENA_RESULT
(
  AR_ID      NUMBER(20) not null,
  BET_USERID NUMBER(20)
)


tablespace LOTTERY
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
comment on table T_ARENA_RESULT
  is '擂台赛投注结果';
-- Add comments to the columns 
comment on column T_ARENA_RESULT.AR_ID
  is '主键';
comment on column T_ARENA_RESULT.BET_USERID
  is '投注人用户ID';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_ARENA_RESULT
  add constraint AR_PK_ID primary key (AR_ID)
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
  
  
  -- Create table
create table T_ARTICLE
(
  ARTICLE_ID  NUMBER(20) not null,
  TITLE       VARCHAR2(100),
  CONTENT     CLOB,
  PUB_TIME    CHAR(10),
  PUB_USER_ID CHAR(10),
  CATEGORY_ID NUMBER(20),
  STATUS      CHAR(1),
  IS_TOP      CHAR(1),
  ORDER       NUMBER(5),
  TAG         VARCHAR2(200)
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
comment on table T_ARTICLE
  is 'CMS文章';
-- Add comments to the columns 
comment on column T_ARTICLE.ARTICLE_ID
  is '文章主键';
comment on column T_ARTICLE.TITLE
  is '标题';
comment on column T_ARTICLE.CONTENT
  is '主要内容';
comment on column T_ARTICLE.PUB_TIME
  is '发布时间';
comment on column T_ARTICLE.PUB_USER_ID
  is '发布人';
comment on column T_ARTICLE.CATEGORY_ID
  is '分类ID';
comment on column T_ARTICLE.STATUS
  is '状态';
comment on column T_ARTICLE.IS_TOP
  is '是否置顶';
comment on column T_ARTICLE.ORDER
  is '在置顶部分的顺序';
comment on column T_ARTICLE.TAG
  is 'TAG';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_ARTICLE
  add constraint PK_T_ARTICLE primary key (ARTICLE_ID)
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
  
  
  
  -- Create table
create table T_AUTO_DOC
(
  AD_ID           NUMBER(20) not null,
  SPONSOR_USER_ID NUMBER(20),
  PHASE           VARCHAR2(10),
  LOTTERY_TYPE    VARCHAR2(2),
  MILITARY_SCORE  NUMBER(20),
  AUTO_MONEY      NUMBER(20,2),
  ALL_MONEY       NUMBER(20,2),
  DOC_USER_ID     NUMBER(20)
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
comment on table T_AUTO_DOC
  is '自动跟单';
-- Add comments to the columns 
comment on column T_AUTO_DOC.AD_ID
  is '主键';
comment on column T_AUTO_DOC.SPONSOR_USER_ID
  is '发起人用户ID';
comment on column T_AUTO_DOC.PHASE
  is '第几期';
comment on column T_AUTO_DOC.LOTTERY_TYPE
  is '彩种';
comment on column T_AUTO_DOC.MILITARY_SCORE
  is '发起人战绩';
comment on column T_AUTO_DOC.AUTO_MONEY
  is '跟单人购买金额';
comment on column T_AUTO_DOC.ALL_MONEY
  is '跟单总金额';
comment on column T_AUTO_DOC.DOC_USER_ID
  is '跟单人用户ID';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_AUTO_DOC
  add constraint AD_PK_ID primary key (AD_ID)
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
  
  
  -- Create table
create table T_B2C_PRODUCT
(
  B2C_ID        NUMBER(20) not null,
  PRODUCT_NAME  VARCHAR2(200) not null,
  EXPERT_ID     NUMBER(20) not null,
  YEAR_PACK     NUMBER(8),
  MONTH_PACK    NUMBER(5),
  SEAON_PACKAGE NUMBER(5),
  SCENE_PACKAGE NUMBER(5),
  WEEK_PACKAGE  NUMBER(5)
)


tablespace LOTTERY
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
comment on table T_B2C_PRODUCT
  is 'B2C产品';
-- Add comments to the columns 
comment on column T_B2C_PRODUCT.B2C_ID
  is 'B2C主键';
comment on column T_B2C_PRODUCT.PRODUCT_NAME
  is '产品名称';
comment on column T_B2C_PRODUCT.EXPERT_ID
  is '专家ID';
comment on column T_B2C_PRODUCT.YEAR_PACK
  is '包年';
comment on column T_B2C_PRODUCT.MONTH_PACK
  is '包月';
comment on column T_B2C_PRODUCT.SEAON_PACKAGE
  is '包季';
comment on column T_B2C_PRODUCT.SCENE_PACKAGE
  is '包单轮';
comment on column T_B2C_PRODUCT.WEEK_PACKAGE
  is '包周';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_B2C_PRODUCT
  add constraint B2C_PRODUCT_PK_ID primary key (B2C_ID)
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
  
  
  -- Create table
create table T_BONUS
(
  ID           NUMBER(20) not null,
  PHASE        VARCHAR2(20),
  USERNAME     VARCHAR2(50),
  USER_ID      NUMBER(20),
  LOTTERY_TYPE VARCHAR2(3),
  LOTTERY_NAME VARCHAR2(100),
  MONEY        NUMBER(20,2)
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
  
  
  -- Create table
create table T_C2C_PRODUCT
(
  C2C_ID       NUMBER(20) not null,
  SCHEDULE_ID  NUMBER(20) not null,
  TX_USER_ID   NUMBER(20),
  ENSURE_MONEY NUMBER(10),
  PRODUCT_NAME VARCHAR2(200),
  CONTENT      VARCHAR2(900),
  TYPE         CHAR(1),
  HOST_TEAM    VARCHAR2(50),
  GUEST_TEAM   VARCHAR2(50),
  INDEX_VALUE  VARCHAR2(50),
  BIG_BALL     VARCHAR2(50),
  SMALL_BALL   VARCHAR2(50),
  BIG_SMALL    VARCHAR2(50),
  ZH_DESC      VARCHAR2(600) not null,
  STATUS       CHAR(1),
  XINSHUI_NO   VARCHAR2(50),
  PRICE        NUMBER(10,2),
  RACE_NAME    VARCHAR2(60),
  RACE_TYPE    VARCHAR2(5),
  PUB_TIME     DATE default sysdate,
  WIN_RATIO    NUMBER(4,2),
  ANSWER       CHAR(1),
  TX_USERNAME  VARCHAR2(90)
)


tablespace LOTTERY
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
comment on table T_C2C_PRODUCT
  is 'C2C产品--民间高手发布的心水';
-- Add comments to the columns 
comment on column T_C2C_PRODUCT.C2C_ID
  is 'C2C主键';
comment on column T_C2C_PRODUCT.SCHEDULE_ID
  is '竞猜赛程ID 关联到表T_XINSHUI_SCHEDULE';
comment on column T_C2C_PRODUCT.TX_USER_ID
  is '民间高手用户ID';
comment on column T_C2C_PRODUCT.ENSURE_MONEY
  is '保证金';
comment on column T_C2C_PRODUCT.PRODUCT_NAME
  is 'c2c产品名称';
comment on column T_C2C_PRODUCT.CONTENT
  is '民间高手竞猜内容';
comment on column T_C2C_PRODUCT.TYPE
  is '''1'':亚盘   ''2'':大小盘';
comment on column T_C2C_PRODUCT.HOST_TEAM
  is '选主';
comment on column T_C2C_PRODUCT.GUEST_TEAM
  is '选客';
comment on column T_C2C_PRODUCT.INDEX_VALUE
  is '指数';
comment on column T_C2C_PRODUCT.BIG_BALL
  is '选大';
comment on column T_C2C_PRODUCT.SMALL_BALL
  is '大\小';
comment on column T_C2C_PRODUCT.BIG_SMALL
  is '选小';
comment on column T_C2C_PRODUCT.ZH_DESC
  is '心水说明';
comment on column T_C2C_PRODUCT.STATUS
  is '心水状态:    ''1'':发布中   ''2'':赢       ''3'':负  ''4'':走    ''5'':已关闭';
comment on column T_C2C_PRODUCT.XINSHUI_NO
  is '心水编号(与订单号类似)';
comment on column T_C2C_PRODUCT.PRICE
  is '心水价格';
comment on column T_C2C_PRODUCT.RACE_NAME
  is '赛事名称(包括  联赛 杯赛)';
comment on column T_C2C_PRODUCT.RACE_TYPE
  is '赛事类型';
comment on column T_C2C_PRODUCT.PUB_TIME
  is '发布时间';
comment on column T_C2C_PRODUCT.ANSWER
  is '本次是否猜对    ''1'':猜对     ''2'':猜错  后台会发布赛果的同时  更改此字段';
comment on column T_C2C_PRODUCT.TX_USERNAME
  is '民间高手用户名';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_C2C_PRODUCT
  add constraint C2C_PRODUCT_PK_ID primary key (C2C_ID)
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
  
  
  -- Create table
create table T_CAIJIN_DONATE
(
  ID              NUMBER(20) not null,
  APPLY_TIME      DATE default sysdate,
  APPLY_USER_ID   NUMBER(20),
  REASON          VARCHAR2(100),
  MONEY           NUMBER(10,2),
  ALL_MONEY       NUMBER(10,2),
  AUDIT_USER_ID   NUMBER(20),
  AUDIT_TIME      DATE,
  STATUS          CHAR(1),
  ORDER_NO        VARCHAR2(50),
  APPLY_USER      VARCHAR2(100),
  AUDIT_USER      VARCHAR2(100),
  DONATE_NUM      NUMBER(20),
  CONCRETE_REASON VARCHAR2(300),
  AUDIT_REASON    VARCHAR2(300),
  DEPT_CODE       VARCHAR2(20),
  CATEGORY_TYPE   VARCHAR2(10)
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
comment on table T_CAIJIN_DONATE
  is '彩金赠送记录';
-- Add comments to the columns 
comment on column T_CAIJIN_DONATE.APPLY_TIME
  is '申请时间';
comment on column T_CAIJIN_DONATE.APPLY_USER_ID
  is '申请人用户ID';
comment on column T_CAIJIN_DONATE.REASON
  is '简短理由';
comment on column T_CAIJIN_DONATE.MONEY
  is '赠送金额';
comment on column T_CAIJIN_DONATE.ALL_MONEY
  is '合计金额';
comment on column T_CAIJIN_DONATE.AUDIT_USER_ID
  is '审核人用户ID';
comment on column T_CAIJIN_DONATE.AUDIT_TIME
  is '审核时间';
comment on column T_CAIJIN_DONATE.STATUS
  is '订单状态';
comment on column T_CAIJIN_DONATE.ORDER_NO
  is '订单号';
comment on column T_CAIJIN_DONATE.APPLY_USER
  is '申请人姓名';
comment on column T_CAIJIN_DONATE.AUDIT_USER
  is '审核人姓名';
comment on column T_CAIJIN_DONATE.DONATE_NUM
  is '赠送人数';
comment on column T_CAIJIN_DONATE.CONCRETE_REASON
  is '详细理由';
comment on column T_CAIJIN_DONATE.AUDIT_REASON
  is '审核理由';
comment on column T_CAIJIN_DONATE.DEPT_CODE
  is '部门';
comment on column T_CAIJIN_DONATE.CATEGORY_TYPE
  is '关联哪张订单表';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CAIJIN_DONATE
  add constraint CD_PK_ID primary key (ID)
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
  
  
  -- Create table
create table T_CHARGE_LOG
(
  CHARGE_ID      NUMBER(20) not null,
  FROM_BANK      VARCHAR2(50) not null,
  MONEY          NUMBER(20,2) not null,
  USER_ID        NUMBER(20) not null,
  CHARGE_TIME    DATE not null,
  MEMO           VARCHAR2(1000),
  IP             VARCHAR2(16),
  ORDER_NO       VARCHAR2(50),
  STATUS         CHAR(1),
  FROM_BANK_CODE VARCHAR2(50),
  ALL_MONEY      NUMBER(20,2),
  PAY_WAY        VARCHAR2(50),
  CATEGORY_TYPE  VARCHAR2(10) default '12'
)


tablespace LOTTERY
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
comment on table T_CHARGE_LOG
  is '充值日志 ';
-- Add comments to the columns 
comment on column T_CHARGE_LOG.CHARGE_ID
  is '主键';
comment on column T_CHARGE_LOG.FROM_BANK
  is '银行或第三方系统中文名称';
comment on column T_CHARGE_LOG.MONEY
  is '金额';
comment on column T_CHARGE_LOG.USER_ID
  is '用户ID';
comment on column T_CHARGE_LOG.CHARGE_TIME
  is '日期';
comment on column T_CHARGE_LOG.MEMO
  is '备注';
comment on column T_CHARGE_LOG.IP
  is '充值IP';
comment on column T_CHARGE_LOG.ORDER_NO
  is '订单号';
comment on column T_CHARGE_LOG.STATUS
  is '充值状态';
comment on column T_CHARGE_LOG.FROM_BANK_CODE
  is '银行代码';
comment on column T_CHARGE_LOG.ALL_MONEY
  is '账户余额';
comment on column T_CHARGE_LOG.PAY_WAY
  is '充值方式:  ''1'':支付宝  ''2'':网银充值 ''3'':银行转账 ''4'':手机充值   ''5'':财付通  ''6'':快钱  ''7'':其他';
comment on column T_CHARGE_LOG.CATEGORY_TYPE
  is '12:充值';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CHARGE_LOG
  add constraint CHARGE_LOG_PK_ID primary key (CHARGE_ID)
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
  
  
  -- Create table
create table T_CODE
(
  NAME      VARCHAR2(120),
  ROOT      CHAR(1),
  ZONE      NUMBER(5),
  ID        NUMBER(20) not null,
  PARENT_ID NUMBER(20),
  TYPE      CHAR(1)
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
comment on table T_CODE
  is '省市数据表';
-- Add comments to the columns 
comment on column T_CODE.NAME
  is '中文名称';
comment on column T_CODE.ID
  is '主键';
comment on column T_CODE.PARENT_ID
  is '父节点ID';
comment on column T_CODE.TYPE
  is '类型''1'':洲 ''2'':国家';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CODE
  add constraint AREA_PK_ID primary key (ID)
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
  
  
  -- Create table
create table T_CONCEDE_WINNING_LOSING
(
  TG_ID          NUMBER(20) not null,
  WICI_TYPE      CHAR(10),
  IS_DINGDAN     CHAR(1),
  VICI_WAY       VARCHAR2(100),
  BET_MULTIPLE   NUMBER(10),
  PHASE          VARCHAR2(10),
  BET_TIME       DATE,
  BET_USERID     NUMBER(20),
  PLAN           VARCHAR2(200),
  TYPE           CHAR(2),
  SPONSOR_USERID NUMBER(20),
  IS_FLOOR       CHAR(1),
  FORMAT_STR     VARCHAR2(100),
  SINGLE_MONEY   NUMBER(20,2),
  ALL_MONEY      NUMBER(20,2),
  DIVIDE_COPYS   NUMBER(10),
  FLOOR_COPYS    NUMBER(20,2),
  PRIVATE_TYPE   CHAR(1),
  RECRUIT_USERS  VARCHAR2(500)
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
comment on table T_CONCEDE_WINNING_LOSING
  is '让球胜平负';
-- Add comments to the columns 
comment on column T_CONCEDE_WINNING_LOSING.TG_ID
  is '主键';
comment on column T_CONCEDE_WINNING_LOSING.WICI_TYPE
  is '过关类型';
comment on column T_CONCEDE_WINNING_LOSING.IS_DINGDAN
  is '是否定胆';
comment on column T_CONCEDE_WINNING_LOSING.VICI_WAY
  is '过关方式';
comment on column T_CONCEDE_WINNING_LOSING.BET_MULTIPLE
  is '投注倍数';
comment on column T_CONCEDE_WINNING_LOSING.PHASE
  is '第几期';
comment on column T_CONCEDE_WINNING_LOSING.BET_TIME
  is '投注时间';
comment on column T_CONCEDE_WINNING_LOSING.BET_USERID
  is '投注人用户ID';
comment on column T_CONCEDE_WINNING_LOSING.PLAN
  is '方案';
comment on column T_CONCEDE_WINNING_LOSING.TYPE
  is '类型';
comment on column T_CONCEDE_WINNING_LOSING.SPONSOR_USERID
  is '发起人用户ID';
comment on column T_CONCEDE_WINNING_LOSING.IS_FLOOR
  is '是否保底';
comment on column T_CONCEDE_WINNING_LOSING.FORMAT_STR
  is '格式化字符';
comment on column T_CONCEDE_WINNING_LOSING.SINGLE_MONEY
  is '单倍金额';
comment on column T_CONCEDE_WINNING_LOSING.ALL_MONEY
  is '总金额';
comment on column T_CONCEDE_WINNING_LOSING.DIVIDE_COPYS
  is '分成几份';
comment on column T_CONCEDE_WINNING_LOSING.FLOOR_COPYS
  is '保底份数';
comment on column T_CONCEDE_WINNING_LOSING.PRIVATE_TYPE
  is '保密类型';
comment on column T_CONCEDE_WINNING_LOSING.RECRUIT_USERS
  is '招股对象';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CONCEDE_WINNING_LOSING
  add constraint CWL_PK_ID primary key (TG_ID)
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
  
  
  -- Create table
create table T_CONCEDE_WL_CHOICE
(
  CHOICE_ID   NUMBER(20) not null,
  SCHEDULE_ID NUMBER(20),
  CWL_ID      NUMBER(20)
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
comment on table T_CONCEDE_WL_CHOICE
  is '让球胜平负场次选择';
-- Add comments to the columns 
comment on column T_CONCEDE_WL_CHOICE.CHOICE_ID
  is '主键';
comment on column T_CONCEDE_WL_CHOICE.SCHEDULE_ID
  is '赛程ID';
comment on column T_CONCEDE_WL_CHOICE.CWL_ID
  is '外键 (关联表T_CONCEDE_WINNING_LOSING的主键)';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CONCEDE_WL_CHOICE
  add constraint CWLC_PK_ID primary key (CHOICE_ID)
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
  
  
  -- Create table
create table T_CS_HANDLE_LOG
(
  ID          NUMBER(20) not null,
  CS_USER_ID  NUMBER(20),
  HANDLE_TIME DATE,
  CS_NAME     VARCHAR2(50),
  MEMO        VARCHAR2(300),
  TYPE        CHAR(1),
  LOG_ID      NUMBER(20),
  OP_TYPE     VARCHAR2(50),
  IP          VARCHAR2(16)
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
comment on table T_CS_HANDLE_LOG
  is '客服处理日志,万一出了什么事情 可以立即找到当事人';
-- Add comments to the columns 
comment on column T_CS_HANDLE_LOG.ID
  is '主键';
comment on column T_CS_HANDLE_LOG.CS_USER_ID
  is '客服用户ID';
comment on column T_CS_HANDLE_LOG.HANDLE_TIME
  is '处理时间';
comment on column T_CS_HANDLE_LOG.CS_NAME
  is '客服姓名';
comment on column T_CS_HANDLE_LOG.MEMO
  is '客服处理结果';
comment on column T_CS_HANDLE_LOG.TYPE
  is '''1'':T_WITHDRAW_LOG        ''2'':T_VA_FROZEN_LOG';
comment on column T_CS_HANDLE_LOG.LOG_ID
  is '外键 关联日志表(T_WITHDRAW_LOG或T_VA_FROZEN_LOG)';
comment on column T_CS_HANDLE_LOG.OP_TYPE
  is '''1'':受理取款 ''2'':转账 3:撤销转账';
comment on column T_CS_HANDLE_LOG.IP
  is '客服机器的IP';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_CS_HANDLE_LOG
  add constraint LOG_PK_ID primary key (ID)
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
  
  
  -- Create table
create table T_DICTIONARY
(
  DIC_ID  NUMBER(10) not null,
  CODE    VARCHAR2(30),
  VALUE   VARCHAR2(550),
  ZH_DESC VARCHAR2(500),
  TYPE    VARCHAR2(100)
)


tablespace LOTTERY
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
comment on table T_DICTIONARY
  is '数据字典 主要用于保存一些系统的配置信息  因为这些信息可能以后会根据网站运营情况需要做适当修改  若写死在程序里  则需要重写部署程序 太麻烦
所以如需要修改只要修改后台的维护界面即可

1 B2C价格的指定  在此处设置';
-- Add comments to the columns 
comment on column T_DICTIONARY.DIC_ID
  is '数据字典主键';
comment on column T_DICTIONARY.CODE
  is '代码';
comment on column T_DICTIONARY.VALUE
  is '值';
comment on column T_DICTIONARY.ZH_DESC
  is '中文描述';
comment on column T_DICTIONARY.TYPE
  is '类型';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_DICTIONARY
  add constraint DIC_PK_ID primary key (DIC_ID)
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
  
  
  -- Create table
create table T_EXPERT
(
  EXPERT_ID    NUMBER(20),
  USER_ID      NUMBER(20),
  INTRODUCTION CLOB,
  JOB          VARCHAR2(100),
  WIN_RATIO    NUMBER(4,2) default 0.00,
  TYPE         CHAR(1)
)


tablespace LOTTERY
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
comment on table T_EXPERT
  is 'B2签约专家补充资料   民间高手相关信息也放在这里';
-- Add comments to the columns 
comment on column T_EXPERT.EXPERT_ID
  is '主键';
comment on column T_EXPERT.USER_ID
  is '关联T_USER表USERID';
comment on column T_EXPERT.INTRODUCTION
  is '专家介绍 或民间高手介绍';
comment on column T_EXPERT.JOB
  is '职务';
comment on column T_EXPERT.WIN_RATIO
  is '胜率';
comment on column T_EXPERT.TYPE
  is '''1'':特约专家   ''2'':民间高手';
  
  
  -- Create table
create table T_FB_REALTIME_SCORE
(
  ID          NUMBER(20) not null,
  Rounds      VARCHAR2(50) not null,
  STATUS      CHAR(1),
  HOST_TEAM   VARCHAR2(100),
  SCORE       VARCHAR2(20),
  GUEST_TEAM  VARCHAR2(100),
  HALF_SCENE  VARCHAR2(100),
  DATA        VARCHAR2(100),
  RACING_TYPE VARCHAR2(5),
  RACING_NAME VARCHAR2(100),
  TIME        DATE,
  PHASE       VARCHAR2(10)
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
comment on table T_FB_REALTIME_SCORE
  is '足彩比分';
-- Add comments to the columns 
comment on column T_FB_REALTIME_SCORE.ID
  is '主键';
comment on column T_FB_REALTIME_SCORE.Rounds
  is '轮次';
comment on column T_FB_REALTIME_SCORE.STATUS
  is '状态';
comment on column T_FB_REALTIME_SCORE.HOST_TEAM
  is '主队';
comment on column T_FB_REALTIME_SCORE.SCORE
  is '比分';
comment on column T_FB_REALTIME_SCORE.GUEST_TEAM
  is '客队';
comment on column T_FB_REALTIME_SCORE.HALF_SCENE
  is '半场';
comment on column T_FB_REALTIME_SCORE.DATA
  is '数据';
comment on column T_FB_REALTIME_SCORE.RACING_TYPE
  is '赛事类型  关联表T_RACING_TYPE的主键';
comment on column T_FB_REALTIME_SCORE.RACING_NAME
  is '赛事名称';
comment on column T_FB_REALTIME_SCORE.TIME
  is '开赛时间';
comment on column T_FB_REALTIME_SCORE.PHASE
  is '第几期';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_FB_REALTIME_SCORE
  add constraint FB_REALTIME_SCORE_PK primary key (ID)
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
  
  
  -- Create table
create table T_FB_SINGLE
(
  TG_ID          NUMBER(20) not null,
  WICI_TYPE      CHAR(10),
  IS_DINGDAN     CHAR(1),
  VICI_WAY       VARCHAR2(100),
  BET_MULTIPLE   NUMBER(10),
  PHASE          VARCHAR2(10),
  BET_TIME       DATE,
  BET_USERID     NUMBER(20),
  BET_MULTI      NUMBER(10),
  PLAN           VARCHAR2(200),
  TYPE           CHAR(2),
  SPONSOR_USERID NUMBER(20),
  IS_FLOOR       CHAR(1),
  FORMAT_STR     VARCHAR2(100),
  SINGLE_MONEY   NUMBER(20,2),
  ALL_MONEY      NUMBER(20,2),
  DIVIDE_COPYS   NUMBER(10),
  FLOOR_COPYS    NUMBER(20,2),
  IS_OPEN        CHAR(1),
  RECRUIT_USERS  VARCHAR2(500),
  PLAN_CODE      VARCHAR2(100)
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
comment on table T_FB_SINGLE
  is '足球单场投注结果';
-- Add comments to the columns 
comment on column T_FB_SINGLE.TG_ID
  is '主键';
comment on column T_FB_SINGLE.WICI_TYPE
  is '过关类型';
comment on column T_FB_SINGLE.IS_DINGDAN
  is '是否定胆';
comment on column T_FB_SINGLE.VICI_WAY
  is '过关方式';
comment on column T_FB_SINGLE.BET_MULTIPLE
  is '投注倍数';
comment on column T_FB_SINGLE.PHASE
  is '第几期';
comment on column T_FB_SINGLE.BET_TIME
  is '投注时间';
comment on column T_FB_SINGLE.BET_USERID
  is '投注人用户ID';
comment on column T_FB_SINGLE.BET_MULTI
  is '倍数';
comment on column T_FB_SINGLE.PLAN
  is '方案(胜胜 33 ..其他类似)';
comment on column T_FB_SINGLE.TYPE
  is '类型 21单代 2 单合  23 复代 24复合';
comment on column T_FB_SINGLE.SPONSOR_USERID
  is '发起人用户ID';
comment on column T_FB_SINGLE.IS_FLOOR
  is '是否保底';
comment on column T_FB_SINGLE.FORMAT_STR
  is '格式转换字符';
comment on column T_FB_SINGLE.SINGLE_MONEY
  is '单倍金额';
comment on column T_FB_SINGLE.ALL_MONEY
  is '总金额';
comment on column T_FB_SINGLE.DIVIDE_COPYS
  is '分成份数';
comment on column T_FB_SINGLE.FLOOR_COPYS
  is '保底份数';
comment on column T_FB_SINGLE.IS_OPEN
  is '是否公开';
comment on column T_FB_SINGLE.RECRUIT_USERS
  is '招股对象';
comment on column T_FB_SINGLE.PLAN_CODE
  is '方案编号';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_FB_SINGLE
  add constraint PK_T_TOTAL_GOAL primary key (TG_ID)
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
  
  
  -- Create table
create table T_FB_SINGLE_CHOICE
(
  CHOICE_ID   NUMBER(20) not null,
  SCHEDULE_ID NUMBER(20),
  TG_ID       NUMBER(20)
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
comment on table T_FB_SINGLE_CHOICE
  is '足球单场对阵选择';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_FB_SINGLE_CHOICE
  add constraint TGC_PK_ID primary key (CHOICE_ID)
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
alter table T_FB_SINGLE_CHOICE
  add constraint TG_FK_ID foreign key (TG_ID)
  references T_FB_SINGLE (TG_ID);
  
  
  -- Create table
create table T_FOOTBALL_S_SCHEDULE
(
  SCHEDULE_ID NUMBER(20) not null,
  START_TIME  DATE,
  HOST_TEAM   VARCHAR2(100),
  GUEST_TEAM  VARCHAR2(100),
  HOST_GOAL   NUMBER(5),
  GUEST_GOAL  NUMBER(5),
  AVG_INDEX   NUMBER(10,2)
)


tablespace LOTTERY
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
comment on table T_FOOTBALL_S_SCHEDULE
  is '足球单场总赛程表';
-- Add comments to the columns 
comment on column T_FOOTBALL_S_SCHEDULE.SCHEDULE_ID
  is '主键';
comment on column T_FOOTBALL_S_SCHEDULE.START_TIME
  is '开赛时间';
comment on column T_FOOTBALL_S_SCHEDULE.HOST_TEAM
  is '主队';
comment on column T_FOOTBALL_S_SCHEDULE.GUEST_TEAM
  is '客队';
comment on column T_FOOTBALL_S_SCHEDULE.HOST_GOAL
  is '主队进球数';
comment on column T_FOOTBALL_S_SCHEDULE.GUEST_GOAL
  is '客队进球数';
comment on column T_FOOTBALL_S_SCHEDULE.AVG_INDEX
  is '平均指数';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_FOOTBALL_S_SCHEDULE
  add constraint SCHEDULE_PK_ID primary key (SCHEDULE_ID)
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
  
  
  -- Create table
create table T_HALF_COMPLETE
(
  HC_ID           NUMBER(20) not null,
  VICI_WAY        VARCHAR2(100),
  FORMAT_STR      VARCHAR2(100),
  SINGLE_MONEY    NUMBER(20,2),
  MULTIPLE        NUMBER(5),
  PLAN            VARCHAR2(200),
  DIVIDED_COPYS   NUMBER(8),
  DEDUCT          NUMBER(2,2),
  SUBSCRIBE_COPYS NUMBER(10),
  IS_FLOOR        CHAR(1),
  IS_OPEN         CHAR(1),
  RECRUIT_USERS   VARCHAR2(500),
  TYPE            CHAR(2),
  BET_USER_ID     NUMBER(20),
  BET_DATE        DATE,
  PARENT_ID       NUMBER(20),
  ALL_MONEY       NUMBER(20,2)
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
comment on table T_HALF_COMPLETE
  is '半全场';
-- Add comments to the columns 
comment on column T_HALF_COMPLETE.HC_ID
  is '主键';
comment on column T_HALF_COMPLETE.VICI_WAY
  is '过关方式';
comment on column T_HALF_COMPLETE.FORMAT_STR
  is '格式转换字符';
comment on column T_HALF_COMPLETE.SINGLE_MONEY
  is '单倍金额';
comment on column T_HALF_COMPLETE.MULTIPLE
  is '倍数';
comment on column T_HALF_COMPLETE.PLAN
  is '方案(胜胜 33 ..其他类似)';
comment on column T_HALF_COMPLETE.DIVIDED_COPYS
  is '分成几份';
comment on column T_HALF_COMPLETE.DEDUCT
  is '提成比例';
comment on column T_HALF_COMPLETE.SUBSCRIBE_COPYS
  is '认购份数';
comment on column T_HALF_COMPLETE.IS_FLOOR
  is '是否保底';
comment on column T_HALF_COMPLETE.IS_OPEN
  is '是否公开';
comment on column T_HALF_COMPLETE.RECRUIT_USERS
  is '招募对象';
comment on column T_HALF_COMPLETE.TYPE
  is '类型(2)   51:单代  52 单合  53  复代   54 复合';
comment on column T_HALF_COMPLETE.BET_USER_ID
  is '投注人ID';
comment on column T_HALF_COMPLETE.BET_DATE
  is '投注时间';
comment on column T_HALF_COMPLETE.PARENT_ID
  is '发起方案ID(当参与合买时关联本表主键HC_ID)';
comment on column T_HALF_COMPLETE.ALL_MONEY
  is '总金额';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_HALF_COMPLETE
  add constraint PK_T_HALF_COMPLETE primary key (HC_ID)
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
  
  
  -- Create table
create table T_HALF_COMPLETE_CHOICE
(
  HCC_ID           NUMBER(20) not null,
  HALF_COMPLETE_ID NUMBER(20),
  HCC_SCHEDULE_ID  NUMBER(20),
  PLAN             VARCHAR2(200),
  IS_DINGDAN       CHAR(1),
  VICI_TYPE        VARCHAR2(100),
  VICI_WAY         VARCHAR2(100),
  BET_MULTIPLE     NUMBER(10),
  BET_MONEY        NUMBER(10,2),
  betNum           NUMBER(8)
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
comment on table T_HALF_COMPLETE_CHOICE
  is '半全场场次选择';
-- Add comments to the columns 
comment on column T_HALF_COMPLETE_CHOICE.HCC_ID
  is '主键';
comment on column T_HALF_COMPLETE_CHOICE.HALF_COMPLETE_ID
  is '半全场ID';
comment on column T_HALF_COMPLETE_CHOICE.HCC_SCHEDULE_ID
  is '赛程ID';
comment on column T_HALF_COMPLETE_CHOICE.PLAN
  is '方案(胜胜 33 ..其他类似)';
comment on column T_HALF_COMPLETE_CHOICE.IS_DINGDAN
  is '是否定胆';
comment on column T_HALF_COMPLETE_CHOICE.VICI_TYPE
  is '过关类型';
comment on column T_HALF_COMPLETE_CHOICE.VICI_WAY
  is '过关方式';
comment on column T_HALF_COMPLETE_CHOICE.BET_MULTIPLE
  is '投注倍数';
comment on column T_HALF_COMPLETE_CHOICE.BET_MONEY
  is '投注金额';
comment on column T_HALF_COMPLETE_CHOICE.betNum
  is '注数';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_HALF_COMPLETE_CHOICE
  add constraint PK_T_HALF_COMPLETE_CHOICE primary key (HCC_ID)
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
  
  -- Create table
create table T_HALF_COMPLETE_CHOICE
(
  HCC_ID           NUMBER(20) not null,
  HALF_COMPLETE_ID NUMBER(20),
  HCC_SCHEDULE_ID  NUMBER(20),
  PLAN             VARCHAR2(200),
  IS_DINGDAN       CHAR(1),
  VICI_TYPE        VARCHAR2(100),
  VICI_WAY         VARCHAR2(100),
  BET_MULTIPLE     NUMBER(10),
  BET_MONEY        NUMBER(10,2),
  betNum           NUMBER(8)
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
comment on table T_HALF_COMPLETE_CHOICE
  is '半全场场次选择';
-- Add comments to the columns 
comment on column T_HALF_COMPLETE_CHOICE.HCC_ID
  is '主键';
comment on column T_HALF_COMPLETE_CHOICE.HALF_COMPLETE_ID
  is '半全场ID';
comment on column T_HALF_COMPLETE_CHOICE.HCC_SCHEDULE_ID
  is '赛程ID';
comment on column T_HALF_COMPLETE_CHOICE.PLAN
  is '方案(胜胜 33 ..其他类似)';
comment on column T_HALF_COMPLETE_CHOICE.IS_DINGDAN
  is '是否定胆';
comment on column T_HALF_COMPLETE_CHOICE.VICI_TYPE
  is '过关类型';
comment on column T_HALF_COMPLETE_CHOICE.VICI_WAY
  is '过关方式';
comment on column T_HALF_COMPLETE_CHOICE.BET_MULTIPLE
  is '投注倍数';
comment on column T_HALF_COMPLETE_CHOICE.BET_MONEY
  is '投注金额';
comment on column T_HALF_COMPLETE_CHOICE.betNum
  is '注数';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_HALF_COMPLETE_CHOICE
  add constraint PK_T_HALF_COMPLETE_CHOICE primary key (HCC_ID)
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
  
  
  -- Create table
create table T_HALF_COMPLETE_SCHEDULE
(
  HCS_ID     NUMBER(20) not null,
  RACE_TYPE  VARCHAR2(100),
  START_TIME DATE,
  END_TIME   DATE,
  HOST_TEAM  VARCHAR2(100),
  GUEST_TEAM VARCHAR2(100),
  SCORE      VARCHAR2(100),
  DATA       VARCHAR2(100)
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
comment on table T_HALF_COMPLETE_SCHEDULE
  is '半全场赛程';
-- Add comments to the columns 
comment on column T_HALF_COMPLETE_SCHEDULE.HCS_ID
  is '主键';
comment on column T_HALF_COMPLETE_SCHEDULE.RACE_TYPE
  is '赛事类型';
comment on column T_HALF_COMPLETE_SCHEDULE.START_TIME
  is '开赛时间';
comment on column T_HALF_COMPLETE_SCHEDULE.END_TIME
  is '截止时间';
comment on column T_HALF_COMPLETE_SCHEDULE.HOST_TEAM
  is '主队';
comment on column T_HALF_COMPLETE_SCHEDULE.GUEST_TEAM
  is '客队';
comment on column T_HALF_COMPLETE_SCHEDULE.SCORE
  is '比分';
comment on column T_HALF_COMPLETE_SCHEDULE.DATA
  is '数据';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_HALF_COMPLETE_SCHEDULE
  add constraint PK_T_HALF_COMPLETE_SCHEDULE primary key (HCS_ID)
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
  
  
  -- Create table
create table T_HISTORY_RACE
(
  HR_ID             NUMBER(20) not null,
  RACE_TYPE         CHAR(1),
  START_TIME        DATE not null,
  HOST_TEAM         NUMBER(10) not null,
  GUEST_TEAM        NUMBER(10) not null,
  POINT             NUMBER(20) not null,
  HALF_SCORE        VARCHAR2(20),
  CONCEDE_POINT     VARCHAR2(100),
  ROAD_BED          CHAR(1),
  BIG_OR_SMALL_BALL CHAR(1),
  RACE_NAME         VARCHAR2(100) not null
)


tablespace LOTTERY
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
comment on table T_HISTORY_RACE
  is '球队以往比赛  盘路(ROAD_BED)  1:赢 2:走 3:输   大小球(BIG_OR_SMALL_BALL)  1:小  2:大';
-- Add comments to the columns 
comment on column T_HISTORY_RACE.HR_ID
  is '主键';
comment on column T_HISTORY_RACE.RACE_TYPE
  is '''1'' :联赛    ''2'':杯赛     ''3'':其他';
comment on column T_HISTORY_RACE.START_TIME
  is '比赛时间';
comment on column T_HISTORY_RACE.HOST_TEAM
  is '主队';
comment on column T_HISTORY_RACE.GUEST_TEAM
  is '客队';
comment on column T_HISTORY_RACE.POINT
  is '积分';
comment on column T_HISTORY_RACE.HALF_SCORE
  is '半场比分';
comment on column T_HISTORY_RACE.CONCEDE_POINT
  is '让球';
comment on column T_HISTORY_RACE.ROAD_BED
  is '盘路';
comment on column T_HISTORY_RACE.BIG_OR_SMALL_BALL
  is '大小球';
comment on column T_HISTORY_RACE.RACE_NAME
  is '比赛名称';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_HISTORY_RACE
  add constraint HR_ID_PK_ID primary key (HR_ID)
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
  
  
  -- Create table
create table T_LOTTERY_CONFIG
(
  LC_ID      NUMBER(20) not null,
  CAI_TYPE   VARCHAR2(100),
  CUR_PHASE  VARCHAR2(10),
  LAST_PHASE VARCHAR2(10),
  NEXT_PHASE VARCHAR2(10),
  STATUS     CHAR(1)
)


tablespace LOTTERY
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
comment on table T_LOTTERY_CONFIG
  is '彩票管理 指定哪一期是当前期   指定结束销售 等必须在此设置';
-- Add comments to the columns 
comment on column T_LOTTERY_CONFIG.LC_ID
  is '主键';
comment on column T_LOTTERY_CONFIG.CAI_TYPE
  is '彩种';
comment on column T_LOTTERY_CONFIG.CUR_PHASE
  is '当前期';
comment on column T_LOTTERY_CONFIG.LAST_PHASE
  is '上一期';
comment on column T_LOTTERY_CONFIG.NEXT_PHASE
  is '下一期';
comment on column T_LOTTERY_CONFIG.STATUS
  is '状态:    1.正在销售  2 .暂停销售 ';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_LOTTERY_CONFIG
  add constraint CM_PK_ID primary key (LC_ID)
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
  
  
  -- Create table
create table T_MARGIN_TRANSACTION
(
  MT_ID       NUMBER(20) not null,
  TX_USER_ID  NUMBER(20) not null,
  TX_MONEY    NUMBER(10) not null,
  IS_THAW     CHAR(1) not null,
  FROZEN_TIME DATE not null,
  PURPOSE     VARCHAR2(200)
)


tablespace LOTTERY
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
comment on table T_MARGIN_TRANSACTION
  is '纳保证金缴纳历史记录 废弃  改用T_VA_FROZEN_LOG表代替';
-- Add comments to the columns 
comment on column T_MARGIN_TRANSACTION.MT_ID
  is '主键';
comment on column T_MARGIN_TRANSACTION.TX_USER_ID
  is '用户ID';
comment on column T_MARGIN_TRANSACTION.TX_MONEY
  is '金额';
comment on column T_MARGIN_TRANSACTION.IS_THAW
  is '是否解冻';
comment on column T_MARGIN_TRANSACTION.FROZEN_TIME
  is '冻结时间';
comment on column T_MARGIN_TRANSACTION.PURPOSE
  is '原因';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_MARGIN_TRANSACTION
  add constraint MT_PK_ID primary key (MT_ID)
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
  
  
  -- Create table
create table T_PLAYER
(
  PLAYER_ID  NUMBER(20) not null,
  NAME       VARCHAR2(100),
  AGE        NUMBER(3),
  HEIGHT     NUMBER(4),
  COUNTRY_ID NUMBER(8)
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
comment on table T_PLAYER
  is '球员信息';
-- Add comments to the columns 
comment on column T_PLAYER.PLAYER_ID
  is '球员表主键';
comment on column T_PLAYER.NAME
  is '姓名';
comment on column T_PLAYER.AGE
  is '年龄';
comment on column T_PLAYER.HEIGHT
  is '身高';
comment on column T_PLAYER.COUNTRY_ID
  is '国籍  关联T_COUNTRY表的主键';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_PLAYER
  add constraint PLAYER_PK_ID primary key (PLAYER_ID)
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
  
  
  -- Create table
create table T_PLAYER_TEAM
(
  PT_ID      NUMBER(20) not null,
  PLAYER_ID  NUMBER(20),
  TEAM_ID    NUMBER(20),
  START_TIME DATE,
  END_TIME   DATE
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
comment on table T_PLAYER_TEAM
  is '球员_球队';
-- Add comments to the columns 
comment on column T_PLAYER_TEAM.PT_ID
  is '主键';
comment on column T_PLAYER_TEAM.PLAYER_ID
  is '球员表主键';
comment on column T_PLAYER_TEAM.TEAM_ID
  is '球队表主键';
comment on column T_PLAYER_TEAM.START_TIME
  is '入队起始时间';
comment on column T_PLAYER_TEAM.END_TIME
  is '入队结束时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_PLAYER_TEAM
  add constraint PT_PK_ID primary key (PT_ID)
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
  
  
  -- Create table
create table T_POINT_TRANSACTION_LOG
(
  LOG_ID     NUMBER(20) not null,
  TX_TYPE    NUMBER(3) not null,
  TX_VALUE   NUMBER(5) not null,
  TX_TIME    DATE not null,
  TX_USER_ID NUMBER(20) not null,
  ALL_POINT  NUMBER(20)
)


tablespace LOTTERY
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
comment on table T_POINT_TRANSACTION_LOG
  is '积分日志     日志类型 1.注册、2.充值、3.投注、4.合买、5.擂台   具体值应该  读取数据字典';
-- Add comments to the columns 
comment on column T_POINT_TRANSACTION_LOG.LOG_ID
  is '主键';
comment on column T_POINT_TRANSACTION_LOG.TX_TYPE
  is '日志类型';
comment on column T_POINT_TRANSACTION_LOG.TX_VALUE
  is '交易积分数量';
comment on column T_POINT_TRANSACTION_LOG.TX_TIME
  is '交易时间';
comment on column T_POINT_TRANSACTION_LOG.TX_USER_ID
  is '用户ID';
comment on column T_POINT_TRANSACTION_LOG.ALL_POINT
  is '剩余积分';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_POINT_TRANSACTION_LOG
  add constraint PK_T_POINT_TRANSACTION_LOG primary key (LOG_ID)
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
  
  
  -- Create table
create table T_RACE
(
  ID          NUMBER(20) not null,
  NAME        VARCHAR2(100),
  PARENT_ID   NUMBER(20),
  TYPE        VARCHAR2(2),
  PREFIX      CHAR(1),
  SCHEDULE_ID NUMBER(20)
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
comment on table T_RACE
  is '联赛或杯赛球队表';
-- Add comments to the columns 
comment on column T_RACE.ID
  is '主键';
comment on column T_RACE.NAME
  is '赛季名称或轮次(联赛与杯赛等)';
comment on column T_RACE.PARENT_ID
  is '父节点';
comment on column T_RACE.TYPE
  is '类型 ''1'':联赛   ''2'':''杯赛''  3:''其他''';
comment on column T_RACE.PREFIX
  is '前缀     ''A'' ,''B''等等';
comment on column T_RACE.SCHEDULE_ID
  is '关联对阵表主键(T_SCHEDULE)';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RACE
  add constraint RACE_ID primary key (ID)
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
  
  
  -- Create table
create table T_RACE_TYPE
(
  RACE_ID NUMBER(20) not null,
  NAME    VARCHAR2(50),
  TYPE    CHAR(1)
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
comment on table T_RACE_TYPE
  is '赛事类型';
-- Add comments to the columns 
comment on column T_RACE_TYPE.RACE_ID
  is '主键';
comment on column T_RACE_TYPE.NAME
  is '赛事名称';
comment on column T_RACE_TYPE.TYPE
  is '''1'':联赛   ''2'':杯赛 ''3'':其他';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_RACE_TYPE
  add constraint PK_RACE_TYPE primary key (RACE_ID)
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
  
  
  -- Create table
create table T_SCENE_REALTIME_SCORE
(
  SRC_ID      NUMBER(20) not null,
  MATCH       NUMBER(10),
  RACING      VARCHAR2(100),
  START_TIME  DATE,
  STATUS      CHAR(1),
  HOST_TEAM   VARCHAR2(100),
  GUEST_TEAM  VARCHAR2(100),
  SCORE       VARCHAR2(20),
  HALF_SECENE VARCHAR2(100),
  CONCEDED    VARCHAR2(50),
  CAI_GUO     VARCHAR2(20)
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
comment on table T_SCENE_REALTIME_SCORE
  is '单场即时比分';
-- Add comments to the columns 
comment on column T_SCENE_REALTIME_SCORE.SRC_ID
  is '主键';
comment on column T_SCENE_REALTIME_SCORE.MATCH
  is '场次';
comment on column T_SCENE_REALTIME_SCORE.RACING
  is '赛事';
comment on column T_SCENE_REALTIME_SCORE.START_TIME
  is '开赛时间';
comment on column T_SCENE_REALTIME_SCORE.STATUS
  is '状态';
comment on column T_SCENE_REALTIME_SCORE.HOST_TEAM
  is '主队';
comment on column T_SCENE_REALTIME_SCORE.GUEST_TEAM
  is '客队';
comment on column T_SCENE_REALTIME_SCORE.SCORE
  is '比分';
comment on column T_SCENE_REALTIME_SCORE.HALF_SECENE
  is '半场';
comment on column T_SCENE_REALTIME_SCORE.CONCEDED
  is '让球';
comment on column T_SCENE_REALTIME_SCORE.CAI_GUO
  is '彩果';
  
  
  -- Create table
create table T_SCHEDULE
(
  SCHEDULE_ID NUMBER(20) not null,
  HOST_NAME   VARCHAR2(60),
  GUEST_NAME  VARCHAR2(60),
  START_TIME  DATE,
  RACE_NAME   VARCHAR2(100),
  RACE_SEASON VARCHAR2(60),
  ROUNDS      VARCHAR2(60),
  STATUS      CHAR(1),
  ASIA_PEI    VARCHAR2(60),
  BIG_SMALL   VARCHAR2(60),
  TYPE        CHAR(1),
  RACE_ID     NUMBER(20),
  HOST_ID     NUMBER(20),
  GUEST_ID    NUMBER(20)
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
comment on table T_SCHEDULE
  is '对阵表(对阵信息数据量超过体彩中心的期次对阵数据量)';
-- Add comments to the columns 
comment on column T_SCHEDULE.SCHEDULE_ID
  is '主键';
comment on column T_SCHEDULE.HOST_NAME
  is '主队';
comment on column T_SCHEDULE.GUEST_NAME
  is '客队';
comment on column T_SCHEDULE.START_TIME
  is '开赛时间';
comment on column T_SCHEDULE.RACE_NAME
  is '赛事名称';
comment on column T_SCHEDULE.RACE_SEASON
  is '赛季(或届)';
comment on column T_SCHEDULE.ROUNDS
  is '轮次';
comment on column T_SCHEDULE.STATUS
  is '状态:''1'':   未选择   ''2'':已选择     ''3'':已过期';
comment on column T_SCHEDULE.ASIA_PEI
  is '亚盘';
comment on column T_SCHEDULE.BIG_SMALL
  is '大小盘';
comment on column T_SCHEDULE.TYPE
  is '赛事类型''1'':联赛   ''2'':杯赛   ''3'':其他';
comment on column T_SCHEDULE.RACE_ID
  is '赛事ID';
comment on column T_SCHEDULE.HOST_ID
  is '主队ID';
comment on column T_SCHEDULE.GUEST_ID
  is '客队ID';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_SCHEDULE
  add constraint T_SCHEDULE_PK_ID primary key (SCHEDULE_ID)
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
  
  
  -- Create table
create table T_SCORE
(
  SCORE_ID       NUMBER(20) not null,
  WICI_TYPE      CHAR(10),
  IS_DINGDAN     CHAR(1),
  VICI_WAY       VARCHAR2(100),
  BET_MULTIPLE   NUMBER(10),
  PHASE          VARCHAR2(10),
  BET_TIME       DATE,
  BET_USERID     NUMBER(20),
  BET_MULTI      NUMBER(10),
  PLAN           VARCHAR2(200),
  TYPE           CHAR(2),
  SPONSOR_USERID NUMBER(20),
  IS_FLOOR       CHAR(1),
  FORMAT_STR     VARCHAR2(100),
  SINGLE_MONEY   NUMBER(20,2),
  ALL_MONEY      NUMBER(20,2),
  DIVIDE_COPYS   NUMBER(10),
  FLOOR_COPYS    NUMBER(20,2),
  IS_OPEN        CHAR(1),
  RECRUIT_USERS  VARCHAR2(500),
  PLAN_CODE      VARCHAR2(100)
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
comment on table T_SCORE
  is '比分投注结果';
-- Add comments to the columns 
comment on column T_SCORE.SCORE_ID
  is '主键';
comment on column T_SCORE.WICI_TYPE
  is '过关类型';
comment on column T_SCORE.IS_DINGDAN
  is '是否定胆';
comment on column T_SCORE.VICI_WAY
  is '过关方式';
comment on column T_SCORE.BET_MULTIPLE
  is '投注倍数';
comment on column T_SCORE.PHASE
  is '第几期';
comment on column T_SCORE.BET_TIME
  is '投注时间';
comment on column T_SCORE.BET_USERID
  is '投注人用户ID';
comment on column T_SCORE.BET_MULTI
  is '倍数';
comment on column T_SCORE.PLAN
  is '方案(胜胜 33 ..其他类似)';
comment on column T_SCORE.TYPE
  is '类型 21单代 2 单合  23 复代 24复合';
comment on column T_SCORE.SPONSOR_USERID
  is '发起人用户ID';
comment on column T_SCORE.IS_FLOOR
  is '是否保底';
comment on column T_SCORE.FORMAT_STR
  is '格式转换字符';
comment on column T_SCORE.SINGLE_MONEY
  is '单倍金额';
comment on column T_SCORE.ALL_MONEY
  is '总金额';
comment on column T_SCORE.DIVIDE_COPYS
  is '分成份数';
comment on column T_SCORE.FLOOR_COPYS
  is '保底份数';
comment on column T_SCORE.IS_OPEN
  is '是否公开';
comment on column T_SCORE.RECRUIT_USERS
  is '招股对象';
comment on column T_SCORE.PLAN_CODE
  is '方案编号';
  
  
  -- Create table
create table T_SCORE_CHOICE
(
  CHOICE_ID   NUMBER(20) not null,
  SCHEDULE_ID NUMBER(20),
  SCORE_FK_ID NUMBER(20)
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
comment on table T_SCORE_CHOICE
  is '比分场次选择';
-- Add comments to the columns 
comment on column T_SCORE_CHOICE.CHOICE_ID
  is '主键';
comment on column T_SCORE_CHOICE.SCHEDULE_ID
  is '赛程ID';
comment on column T_SCORE_CHOICE.SCORE_FK_ID
  is '比分投注表ID';
  
  
  -- Create table
create table T_SECURITY_IP
(
  SECURITY_ID NUMBER(20) not null,
  USER_ID     NUMBER(20),
  IP          VARCHAR2(16) not null,
  TX_TIME     DATE default sysdate,
  CNT         NUMBER(20) not null
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
comment on table T_SECURITY_IP
  is '用户注册 登录时记录 日志';
-- Add comments to the columns 
comment on column T_SECURITY_IP.SECURITY_ID
  is '主键';
comment on column T_SECURITY_IP.USER_ID
  is '废弃';
comment on column T_SECURITY_IP.IP
  is 'IP';
comment on column T_SECURITY_IP.TX_TIME
  is '废弃';
comment on column T_SECURITY_IP.CNT
  is '注册次数';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_SECURITY_IP
  add constraint SECURITY_PK_ID primary key (SECURITY_ID)
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
  
  
  -- Create table
create table T_SITE_MESSAGE
(
  MESSAGE_ID NUMBER(20) not null,
  TITLE      VARCHAR2(100),
  CONTENT    CLOB
)


tablespace LOTTERY
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
comment on table T_SITE_MESSAGE
  is '站内消息';
-- Add comments to the columns 
comment on column T_SITE_MESSAGE.MESSAGE_ID
  is '主键';
comment on column T_SITE_MESSAGE.TITLE
  is '标题';
comment on column T_SITE_MESSAGE.CONTENT
  is '主要内容';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_SITE_MESSAGE
  add constraint MESSAGE_FK_ID primary key (MESSAGE_ID)
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
  
  
  -- Create table
create table T_TEAM
(
  ID             NUMBER(10) not null,
  NAME           VARCHAR2(50) not null,
  COUNTRY        VARCHAR2(30),
  FOUND_DATE     DATE,
  MAIN_SCENE     VARCHAR2(30),
  BOSS           VARCHAR2(30),
  CITY           VARCHAR2(100),
  WEB_SITE       VARCHAR2(300),
  CONTACT_ADDR   VARCHAR2(300) not null,
  STADIUM        VARCHAR2(300),
  STADIUM_VOLUME NUMBER(20,2),
  TYPE           CHAR(1),
  EMAIL          VARCHAR2(50),
  FULL_NAME      VARCHAR2(150),
  INTRODUCTION   VARCHAR2(4000),
  HONOR          VARCHAR2(4000),
  SPECIALTY      VARCHAR2(2000),
  WIN            NUMBER(10),
  PING           NUMBER(10),
  LOSING         NUMBER(10),
  CHANGCI        NUMBER(10),
  GOAL           NUMBER(10),
  POINT          NUMBER(10),
  COUNTRY_ID     NUMBER(10),
  AREA_ID        NUMBER(10)
)


tablespace LOTTERY
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
comment on table T_TEAM
  is '足球球队的基本信息：国家、城市、成立时间、主场、老板、网址';
-- Add comments to the columns 
comment on column T_TEAM.ID
  is '主键';
comment on column T_TEAM.NAME
  is '球队名称';
comment on column T_TEAM.COUNTRY
  is '国家';
comment on column T_TEAM.FOUND_DATE
  is '成立时间';
comment on column T_TEAM.BOSS
  is '老板';
comment on column T_TEAM.CITY
  is '所在城市';
comment on column T_TEAM.WEB_SITE
  is '官方网站';
comment on column T_TEAM.CONTACT_ADDR
  is '联系地址';
comment on column T_TEAM.STADIUM
  is '球场';
comment on column T_TEAM.STADIUM_VOLUME
  is '球场容量';
comment on column T_TEAM.TYPE
  is '''1'':联赛队  ''2'':国家队  ''3'':其他队';
comment on column T_TEAM.EMAIL
  is '球队EMAIL';
comment on column T_TEAM.FULL_NAME
  is '球队全称';
comment on column T_TEAM.INTRODUCTION
  is '球队简介';
comment on column T_TEAM.HONOR
  is '球队荣誉';
comment on column T_TEAM.SPECIALTY
  is '球队之最';
comment on column T_TEAM.WIN
  is '胜';
comment on column T_TEAM.PING
  is '平';
comment on column T_TEAM.LOSING
  is '负';
comment on column T_TEAM.CHANGCI
  is '场次';
comment on column T_TEAM.GOAL
  is '进球';
comment on column T_TEAM.POINT
  is '积分';
comment on column T_TEAM.COUNTRY_ID
  is '国家主键  对应表T_CODE';
comment on column T_TEAM.AREA_ID
  is '区域(即  洲)';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_TEAM
  add constraint PK_T_TEAM primary key (ID)
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
  
  
  -- Create table
create table T_TEAM_RACE
(
  ID        NUMBER(20) not null,
  TEAM_ID   NUMBER(20),
  RACE_ID   NUMBER(20),
  RACE_TIME DATE
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
comment on table T_TEAM_RACE
  is '球队与赛事中间表(联赛  杯赛等)';
-- Add comments to the columns 
comment on column T_TEAM_RACE.TEAM_ID
  is '球队主键';
comment on column T_TEAM_RACE.RACE_ID
  is '赛事主键(包括联赛  杯赛 等)';
comment on column T_TEAM_RACE.RACE_TIME
  is '开赛时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_TEAM_RACE
  add constraint TR_ID primary key (ID)
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
  
  
  -- Create table
create table T_UPDOWN_SINGLEDOUBLE
(
  TG_ID          NUMBER(20) not null,
  WICI_TYPE      CHAR(10),
  IS_DINGDAN     CHAR(1),
  VICI_WAY       VARCHAR2(100),
  BET_MULTIPLE   NUMBER(10),
  PHASE          VARCHAR2(10),
  BET_TIME       DATE,
  BET_USERID     NUMBER(20),
  BET_MULTI      NUMBER(10),
  PLAN           VARCHAR2(200),
  TYPE           CHAR(2),
  SPONSOR_USERID NUMBER(20),
  IS_FLOOR       CHAR(1),
  FORMAT_STR     VARCHAR2(100),
  SINGLE_MONEY   NUMBER(20,2),
  ALL_MONEY      NUMBER(20,2),
  DIVIDE_COPYS   NUMBER(10),
  FLOOR_COPYS    NUMBER(20,2),
  IS_OPEN        CHAR(1),
  RECRUIT_USERS  VARCHAR2(500)
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
  
  
  -- Create table
create table T_USER
(
  USERID          NUMBER(20) not null,
  USERNAME        VARCHAR2(50) not null,
  EMAIL           VARCHAR2(50) not null,
  LOGIN_PASSWORD  VARCHAR2(32) not null,
  USER_GRADE      CHAR(1) default '1' not null,
  WITHDRAW_PWD    VARCHAR2(32),
  NAME            VARCHAR2(50),
  PASSWORD_TIP    CHAR(1),
  PASSWORD_ANSWER VARCHAR2(150),
  MP              VARCHAR2(11),
  MILITARY_SCORE  NUMBER(10),
  STATUS          CHAR(1) default '1',
  IDCARD          VARCHAR2(30),
  SEX             CHAR(1),
  TEL             VARCHAR2(20),
  PROVINCE        VARCHAR2(50),
  CITY            VARCHAR2(100),
  REG_IP          VARCHAR2(16),
  LOGIN_CNT       NUMBER(20),
  QQ              VARCHAR2(20),
  TITLE           VARCHAR2(50),
  REG_TIME        DATE,
  IS_EMAIL_BIND   CHAR(1),
  BIRTHDAY        DATE,
  IS_ONLINE       CHAR(1)
)


tablespace LOTTERY
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
comment on table T_USER
  is '网站的用户会员信息';
-- Add comments to the columns 
comment on column T_USER.USERID
  is '用户ID';
comment on column T_USER.USERNAME
  is '用户名';
comment on column T_USER.EMAIL
  is '邮件';
comment on column T_USER.LOGIN_PASSWORD
  is '登录密码';
comment on column T_USER.USER_GRADE
  is '用户等级';
comment on column T_USER.WITHDRAW_PWD
  is '取款密码';
comment on column T_USER.NAME
  is '用户真实姓名';
comment on column T_USER.PASSWORD_TIP
  is '密码提示';
comment on column T_USER.PASSWORD_ANSWER
  is '密码答案';
comment on column T_USER.MP
  is '手机号码';
comment on column T_USER.MILITARY_SCORE
  is '心水战绩';
comment on column T_USER.STATUS
  is '状态 1：正常  2:锁定';
comment on column T_USER.IDCARD
  is '身份证';
comment on column T_USER.SEX
  is '性别   1：男    2：女';
comment on column T_USER.TEL
  is '电话';
comment on column T_USER.PROVINCE
  is '省市自治区直辖市';
comment on column T_USER.CITY
  is '地级市';
comment on column T_USER.REG_IP
  is '注册IP';
comment on column T_USER.LOGIN_CNT
  is '登录次数';
comment on column T_USER.QQ
  is 'QQ';
comment on column T_USER.TITLE
  is '头衔';
comment on column T_USER.REG_TIME
  is '注册时间';
comment on column T_USER.IS_EMAIL_BIND
  is '邮箱是否绑定      ''1'' 绑定     ''0'' 尚未绑定';
comment on column T_USER.BIRTHDAY
  is '出生日期';
comment on column T_USER.IS_ONLINE
  is '用户是否在线   ‘1’ 在线     ''0'' 不在线';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_USER
  add constraint PK_T_USER primary key (USERID)
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
alter table T_USER
  add constraint UNI_EMAIL unique (EMAIL);
alter table T_USER
  add constraint UNI_NAME unique (USERNAME)
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
-- Create/Recreate indexes 
create unique index UNIQUE_EMAIL on T_USER (EMAIL)
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
  
  -- Create table
create table T_USER_DONATE
(
  UD_ID      NUMBER(20) not null,
  DONATE_ID  NUMBER(20),
  USER_ID    NUMBER(20),
  ALL_CAIJIN NUMBER(20,2)
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
comment on table T_USER_DONATE
  is '用户-彩金赠送 中间表';
-- Add comments to the columns 
comment on column T_USER_DONATE.UD_ID
  is '主键';
comment on column T_USER_DONATE.DONATE_ID
  is '赠送表ID';
comment on column T_USER_DONATE.USER_ID
  is '被赠送人用户ID';
comment on column T_USER_DONATE.ALL_CAIJIN
  is '余额';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_USER_DONATE
  add constraint UD_PK_ID primary key (UD_ID)
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
  
 -- Create table
create table T_VA_FROZEN_LOG
(
  FROZEN_ID               NUMBER(20) not null,
  TX_USER_ID              NUMBER(20) not null,
  FROZEN_MONEY            NUMBER(10,2) not null,
  FROZEN_DATE             DATE not null,
  MEMO                    VARCHAR2(1000),
  TX_TYPE                 VARCHAR2(3) not null,
  ALL_MONEY               NUMBER(20,2),
  MOSAIC_GOLDMONEY        NUMBER(20,2),
  FROZEN_MOSAIC_GOLDMONEY NUMBER(20,2),
  ORDER_ID                NUMBER(20) not null,
  CATEGORY_TYPE           VARCHAR2(5),
  ORDER_NO                VARCHAR2(50),
  FLG                     CHAR(1)
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
comment on table T_VA_FROZEN_LOG
  is '冻结日志';
-- Add comments to the columns 
comment on column T_VA_FROZEN_LOG.FROZEN_ID
  is '主键';
comment on column T_VA_FROZEN_LOG.TX_USER_ID
  is '交易人用户ID';
comment on column T_VA_FROZEN_LOG.FROZEN_MONEY
  is '冻结金额';
comment on column T_VA_FROZEN_LOG.FROZEN_DATE
  is '冻结日期';
comment on column T_VA_FROZEN_LOG.MEMO
  is '备注';
comment on column T_VA_FROZEN_LOG.TX_TYPE
  is '详细请见  T_DICTIONARY表TX_TYPE字段所对应的几条记录';
comment on column T_VA_FROZEN_LOG.ALL_MONEY
  is '剩余金额';
comment on column T_VA_FROZEN_LOG.MOSAIC_GOLDMONEY
  is '剩余彩金';
comment on column T_VA_FROZEN_LOG.FROZEN_MOSAIC_GOLDMONEY
  is '冻结彩金';
comment on column T_VA_FROZEN_LOG.ORDER_ID
  is '外键（关联投注表或者是心水购买表）';
comment on column T_VA_FROZEN_LOG.CATEGORY_TYPE
  is '购买彩票(1:足球单场,6:胜负彩,7:任九,8:6场半全场,9:4场进球) |购买b2c(10:) |购买c2c(11) 12:充值 13:取款   14缴纳保证金';
comment on column T_VA_FROZEN_LOG.ORDER_NO
  is '订单号';
comment on column T_VA_FROZEN_LOG.FLG
  is '''1'':冻结   ''2'':解冻';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_VA_FROZEN_LOG
  add constraint VAF_PK_ID primary key (FROZEN_ID)
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

-- Create table
create table T_VA_TRANSACTION_LOG
(
  VAT_ID              NUMBER(20) not null,
  TX_USER_ID          NUMBER(20) not null,
  TX_MONEY            NUMBER(10,2) default 0.00,
  TX_DATE             DATE not null,
  MEMO                VARCHAR2(1000),
  TX_TYPE             VARCHAR2(5) not null,
  ALL_MONEY           NUMBER(20,2) default 0.00,
  MOSAIC_GOLDMONEY    NUMBER(20,2) default 0.00,
  TX_MOSAIC_GOLDMONEY NUMBER(20,2) default 0.00,
  ORDER_ID            NUMBER(20),
  CATEGORY_TYPE       VARCHAR2(10),
  FLG                 CHAR(1) default '2',
  ORDER_NO            VARCHAR2(50),
  TX_TYPE_NAME        VARCHAR2(90)
)


tablespace LOTTERY
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
comment on table T_VA_TRANSACTION_LOG
  is '虚拟币交易日志  日志类型  1.购买彩票  2  退款  3.中奖 4 购买心水(B2C C2C产品)   5.彩金奖励';
-- Add comments to the columns 
comment on column T_VA_TRANSACTION_LOG.VAT_ID
  is '主键';
comment on column T_VA_TRANSACTION_LOG.TX_USER_ID
  is '用户ID';
comment on column T_VA_TRANSACTION_LOG.TX_MONEY
  is '金额(参与交易的金额)';
comment on column T_VA_TRANSACTION_LOG.TX_DATE
  is '交易时间';
comment on column T_VA_TRANSACTION_LOG.MEMO
  is '备注';
comment on column T_VA_TRANSACTION_LOG.TX_TYPE
  is '详细请见  T_DICTIONARY表TX_TYPE字段所对应的几条记录';
comment on column T_VA_TRANSACTION_LOG.ALL_MONEY
  is '总余额';
comment on column T_VA_TRANSACTION_LOG.MOSAIC_GOLDMONEY
  is '彩金总余额';
comment on column T_VA_TRANSACTION_LOG.TX_MOSAIC_GOLDMONEY
  is '交易彩金';
comment on column T_VA_TRANSACTION_LOG.ORDER_ID
  is '关联的表对应的ID(投注表   心水购买表)';
comment on column T_VA_TRANSACTION_LOG.CATEGORY_TYPE
  is '购买彩票(1:足球单场,6:胜负彩,7:任九,8:6场半全场,9:4场进球) |购买b2c(10:) |购买c2c(11) 12:充值 13:取款   14缴纳保证金';
comment on column T_VA_TRANSACTION_LOG.FLG
  is '''1'':收入  ''2'':支出';
comment on column T_VA_TRANSACTION_LOG.ORDER_NO
  is '订单号   yyMMddCJXXXXXXXXXXXXXXXX';
comment on column T_VA_TRANSACTION_LOG.TX_TYPE_NAME
  is '交易类型中文名称';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_VA_TRANSACTION_LOG
  add constraint PK_T_VA_TRANSACTION_LOG primary key (VAT_ID)
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

-- Create table
create table T_VIRTUAL_ACCOUNT
(
  VA_ID              NUMBER(20) not null,
  TX_USER_ID         NUMBER(20),
  STATUS             CHAR(1) default '1',
  ALL_MONEY          NUMBER(20,2) default 0.00,
  FROZEN_MONEY       NUMBER(20,2) default 0.00,
  MOSAIC_GOLD        NUMBER(10,2) default 0.00,
  POINT              NUMBER(20),
  BANK_CODE          VARCHAR2(10),
  CARD_NUM           VARCHAR2(50),
  PROVINCE           VARCHAR2(20),
  CITY               VARCHAR2(120),
  FROZEN_MOSAIC_GOLD NUMBER(10,2) default 0.00,
  BRANCH             VARCHAR2(120),
  BANK_NAME          VARCHAR2(120)
)


tablespace LOTTERY
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
comment on table T_VIRTUAL_ACCOUNT
  is '用户的金额虚拟账号 ';
-- Add comments to the columns 
comment on column T_VIRTUAL_ACCOUNT.VA_ID
  is '主键';
comment on column T_VIRTUAL_ACCOUNT.TX_USER_ID
  is '用户ID';
comment on column T_VIRTUAL_ACCOUNT.STATUS
  is '状态   1:正常      2  冻结';
comment on column T_VIRTUAL_ACCOUNT.ALL_MONEY
  is '总余额(包括冻结金额)';
comment on column T_VIRTUAL_ACCOUNT.FROZEN_MONEY
  is '冻结金额';
comment on column T_VIRTUAL_ACCOUNT.MOSAIC_GOLD
  is '彩金(包括已经冻结的彩金)';
comment on column T_VIRTUAL_ACCOUNT.POINT
  is '积分';
comment on column T_VIRTUAL_ACCOUNT.BANK_CODE
  is '银行代码   具体在T_DICTIONARY表';
comment on column T_VIRTUAL_ACCOUNT.CARD_NUM
  is '银行账户';
comment on column T_VIRTUAL_ACCOUNT.PROVINCE
  is '银行所在省份';
comment on column T_VIRTUAL_ACCOUNT.CITY
  is '银行所在地级市';
comment on column T_VIRTUAL_ACCOUNT.FROZEN_MOSAIC_GOLD
  is '冻结彩金';
comment on column T_VIRTUAL_ACCOUNT.BRANCH
  is '支行';
comment on column T_VIRTUAL_ACCOUNT.BANK_NAME
  is '银行名称';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_VIRTUAL_ACCOUNT
  add constraint PK_T_VIRTUAL_ACCOUNT primary key (VA_ID)
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

-- Create table
create table T_WINNING_LOSING
(
  WL_ID           NUMBER(20) not null,
  USERID          NUMBER(20),
  SPONSOR_USERID  NUMBER(20),
  BET_MULTIPLE    NUMBER(10),
  DIVIDE_COPYS    NUMBER(10,2),
  RECRUIT_USERS   VARCHAR2(500),
  IS_PRIVATE      CHAR(1),
  IS_FLOOR        CHAR(1),
  SUBSCRIBE_COPYS NUMBER(10),
  BET_NUM         NUMBER(10),
  PLAN            VARCHAR2(200),
  BET_DATE        DATE,
  PARENT_ID       NUMBER(20),
  FLOOR_COPYS     NUMBER(20,2),
  AUTO_MONEY      NUMBER(20,2),
  ALL_MONEY       NUMBER(20,2),
  TYPE            CHAR(2),
  PLAN_CODE       VARCHAR2(20),
  PHASE           VARCHAR2(10)
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
comment on table T_WINNING_LOSING
  is '胜负彩    1.招募对象(RECRUIT_USERS)  , 如果为空  就表示招募所用的用户  否则只能指定的用户吗？
      2.  ( 方案，注数)与选择号码 是互斥的
     3.发起人用户ID (SPONSOR_USERID)不为空  表示此记录是发起合买   否则表示参与合买';
-- Add comments to the columns 
comment on column T_WINNING_LOSING.WL_ID
  is '主键';
comment on column T_WINNING_LOSING.USERID
  is '用户ID';
comment on column T_WINNING_LOSING.SPONSOR_USERID
  is '发起人用户ID';
comment on column T_WINNING_LOSING.BET_MULTIPLE
  is '投注倍数';
comment on column T_WINNING_LOSING.DIVIDE_COPYS
  is '分成份数';
comment on column T_WINNING_LOSING.RECRUIT_USERS
  is '招股对象';
comment on column T_WINNING_LOSING.IS_PRIVATE
  is '是否保密';
comment on column T_WINNING_LOSING.IS_FLOOR
  is '是否保底';
comment on column T_WINNING_LOSING.SUBSCRIBE_COPYS
  is '认购份数';
comment on column T_WINNING_LOSING.BET_NUM
  is '注数';
comment on column T_WINNING_LOSING.PLAN
  is '方案(胜胜 33 ..其他类似)';
comment on column T_WINNING_LOSING.BET_DATE
  is '投注时间';
comment on column T_WINNING_LOSING.PARENT_ID
  is '发起方案ID(当参与合买时关联本表主键否则为空)';
comment on column T_WINNING_LOSING.FLOOR_COPYS
  is '保底份数';
comment on column T_WINNING_LOSING.AUTO_MONEY
  is '本人购买金额';
comment on column T_WINNING_LOSING.ALL_MONEY
  is '总金额';
comment on column T_WINNING_LOSING.TYPE
  is '类型 61单代 62 单合  63 复代 64复合';
comment on column T_WINNING_LOSING.PLAN_CODE
  is '方案编号';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_WINNING_LOSING
  add constraint WL_PK_ID primary key (WL_ID)
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
-- Create/Recreate indexes 
create index Relationship_4_FK on T_WINNING_LOSING (USERID)
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

-- Create table
create table T_WINNING_LOSING_SCHEDULE
(
  WLS_ID     NUMBER(20) not null,
  START_TIME DATE,
  HOST_TEAM  VARCHAR2(100),
  GUEST_TEAM VARCHAR2(100),
  AVG_INDEX  VARCHAR2(100)
)


tablespace LOTTERY
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
comment on table T_WINNING_LOSING_SCHEDULE
  is '每期胜负彩赛程表';
-- Add comments to the columns 
comment on column T_WINNING_LOSING_SCHEDULE.WLS_ID
  is '主键';
comment on column T_WINNING_LOSING_SCHEDULE.START_TIME
  is '开赛时间';
comment on column T_WINNING_LOSING_SCHEDULE.HOST_TEAM
  is '主队';
comment on column T_WINNING_LOSING_SCHEDULE.GUEST_TEAM
  is '客队';
comment on column T_WINNING_LOSING_SCHEDULE.AVG_INDEX
  is '平均指数';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_WINNING_LOSING_SCHEDULE
  add constraint WEL_SCHEDULE_PK_ID primary key (WLS_ID)
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

-- Create table
create table T_WITHDRAW_LOG
(
  DRAW_ID       NUMBER(20) not null,
  BANK          VARCHAR2(150),
  TX_MONEY      NUMBER(10,2),
  TX_USER_ID    NUMBER(20),
  DRAW_TIME     DATE,
  BANK_PROVINCE VARCHAR2(20),
  BANK_CITY     VARCHAR2(20),
  STATUS        CHAR(1),
  MEMO          VARCHAR2(1000),
  FEE           NUMBER(5,2),
  BANK_CODE     VARCHAR2(50),
  ORDER_NO      VARCHAR2(50),
  WITHDRAW_IP   VARCHAR2(16),
  CATEGORY_TYPE VARCHAR2(10) default '13',
  ALL_MONEY     NUMBER(20,2),
  CARD_NUM      VARCHAR2(30),
  BRANCH        VARCHAR2(90)
)


tablespace LOTTERY
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
comment on table T_WITHDRAW_LOG
  is '取款日志  用于记录每次提现的清单   提现流水号就是  前缀+主键  组成 前缀可以是字母TX  ';
-- Add comments to the columns 
comment on column T_WITHDRAW_LOG.DRAW_ID
  is '主键';
comment on column T_WITHDRAW_LOG.BANK
  is '银行名称';
comment on column T_WITHDRAW_LOG.TX_MONEY
  is '金额';
comment on column T_WITHDRAW_LOG.TX_USER_ID
  is '用户ID';
comment on column T_WITHDRAW_LOG.DRAW_TIME
  is '取款日期';
comment on column T_WITHDRAW_LOG.BANK_PROVINCE
  is '银行所在省份';
comment on column T_WITHDRAW_LOG.BANK_CITY
  is '银行所在城市';
comment on column T_WITHDRAW_LOG.STATUS
  is '状态     1:未受理、2:已受理、3:已转账';
comment on column T_WITHDRAW_LOG.MEMO
  is '备用字段';
comment on column T_WITHDRAW_LOG.FEE
  is '手续费';
comment on column T_WITHDRAW_LOG.BANK_CODE
  is '银行代码';
comment on column T_WITHDRAW_LOG.ORDER_NO
  is '取款订单号';
comment on column T_WITHDRAW_LOG.WITHDRAW_IP
  is '取款IP';
comment on column T_WITHDRAW_LOG.CARD_NUM
  is '银行账户';
comment on column T_WITHDRAW_LOG.BRANCH
  is '支行';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_WITHDRAW_LOG
  add constraint DRAW_PK_ID primary key (DRAW_ID)
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

-- Create table
create table T_XINSHUI_LOG
(
  XINSHUI_ID   NUMBER(20) not null,
  TYPE         NUMBER(2),
  PRODUCT_ID   NUMBER(20),
  BUY_USER_ID  NUMBER(20),
  START_TIME   DATE default sysdate,
  END_TIME     DATE default sysdate,
  STATUS       CHAR(1),
  PHASE        VARCHAR2(10),
  BUY_TIME     DATE,
  PAY_STATUS   CHAR(1),
  ORDER_NO     VARCHAR2(50),
  SOLD_USER_ID NUMBER(20)
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
comment on table T_XINSHUI_LOG
  is '购买心水记录';
-- Add comments to the columns 
comment on column T_XINSHUI_LOG.XINSHUI_ID
  is '主键';
comment on column T_XINSHUI_LOG.TYPE
  is '类型   值只能为''1'' 或者''2''';
comment on column T_XINSHUI_LOG.PRODUCT_ID
  is '产品ID    如果TYPE=''1'' 关联T_B2C_PRODUCT.B2C_ID  如果 TYPE=''2'' 关联T_C2C_PRODUCT.C2C_ID';
comment on column T_XINSHUI_LOG.BUY_USER_ID
  is '购买人用户ID   关联T_USER表 ';
comment on column T_XINSHUI_LOG.START_TIME
  is '生效起始时间(只对b2c有效)';
comment on column T_XINSHUI_LOG.END_TIME
  is '生效结束时间(只对b2c有效)';
comment on column T_XINSHUI_LOG.STATUS
  is '状态  1正在进行中   2 正常完成 3流单';
comment on column T_XINSHUI_LOG.PHASE
  is '第几期';
comment on column T_XINSHUI_LOG.BUY_TIME
  is '产生订单时间';
comment on column T_XINSHUI_LOG.PAY_STATUS
  is '支付状态:，1:“未支
付”、2:“已支付”、3:“已扣款”、
4:“已赔付”   5:“ 已取消”';
comment on column T_XINSHUI_LOG.ORDER_NO
  is '订单号';
comment on column T_XINSHUI_LOG.SOLD_USER_ID
  is '销售人用户ID(民间高手用户ID   特约专家用户ID)';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_XINSHUI_LOG
  add constraint XINSHUI_PK_ID primary key (XINSHUI_ID)
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

-- Create table
create table T_XINSHUI_SCHEDULE
(
  ID          NUMBER(20) not null,
  DEADLINE    DATE,
  SCHEDULE_ID NUMBER(20)
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
comment on table T_XINSHUI_SCHEDULE
  is '后台客服通过筛选出热点赛事,提供给民间高手发布心水的赛程表';
-- Add comments to the columns 
comment on column T_XINSHUI_SCHEDULE.ID
  is '主键';
comment on column T_XINSHUI_SCHEDULE.DEADLINE
  is '心水发布截至时间';
comment on column T_XINSHUI_SCHEDULE.SCHEDULE_ID
  is '关联到总的对阵表 关联到 T_SCHEDULE';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_XINSHUI_SCHEDULE
  add constraint XS_PK_ID primary key (ID)
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

  
  
  
  
