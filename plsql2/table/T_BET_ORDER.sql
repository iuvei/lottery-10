-- Create table
create table T_BET_ORDER
(
  BET_ID            NUMBER(20) not null,
  IS_FLOOR          CHAR(1),
  VICI_WAY          VARCHAR2(100),
  BET_NUMBER        NUMBER(10),
  PHASE_NO          VARCHAR2(10),
  BET_TIME          DATE default sysdate,
  BET_USERID        NUMBER(20) not null,
  BET_MULTI         NUMBER(10),
  PLAN              VARCHAR2(200),
  TYPE              VARCHAR2(2) not null,
  FORMAT_STR        VARCHAR2(100),
  SINGLE_MONEY      NUMBER(20,2),
  ALL_MONEY         NUMBER(20,2),
  DIVIDE_COPYS      NUMBER(10) default 1,
  FLOOR_COPYS       NUMBER(20) default 0,
  RECRUIT_USERS     VARCHAR2(500),
  PLAN_CODE         VARCHAR2(100),
  ORDER_NO          VARCHAR2(50) not null,
  BET_CATEGOTY      VARCHAR2(2) not null,
  ORDER_STATUS      CHAR(1) default '1' not null,
  ZJ_STATUS         CHAR(1) default '1',
  BET_USERNAME      VARCHAR2(300) not null,
  SPONSOR_TYPE      CHAR(1) default '0',
  SPONSOR_BET_ID    NUMBER(20),
  SUBSCRIBE_COPYS   NUMBER(10) default 1 not null,
  SUBSCRIBE_MONEY   NUMBER(20,2) not null,
  BONUS             NUMBER(20,2) default 0.00,
  TC_RATE           NUMBER(3),
  WICI_TYPE         CHAR(1),
  PHASE_ID          NUMBER(20),
  KING_ID           NUMBER(20),
  ALREADY_BUY_COPYS NUMBER(20) default 0,
  END_TIME          DATE,
  TXT_PATH          VARCHAR2(400),
  SECURITY_TYPE     NUMBER(5),
  PLAN_TITLE        VARCHAR2(180),
  PLAN_DESC         VARCHAR2(1000),
  BET_SEL_INFO      VARCHAR2(180),
  CORRECT_CHANGCI   NUMBER(5),
  IS_TOP            NUMBER(1) default 0,
  IS_HOT            NUMBER(1) default 0
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
comment on table T_BET_ORDER
  is '用户投注结果';
-- Add comments to the columns 
comment on column T_BET_ORDER.BET_ID
  is '状态';
comment on column T_BET_ORDER.IS_FLOOR
  is '是否保底';
comment on column T_BET_ORDER.VICI_WAY
  is '过关方式 ';
comment on column T_BET_ORDER.BET_NUMBER
  is '注数';
comment on column T_BET_ORDER.PHASE_NO
  is '足彩期次';
comment on column T_BET_ORDER.BET_TIME
  is '投注时间';
comment on column T_BET_ORDER.BET_USERID
  is '投注人用户ID';
comment on column T_BET_ORDER.BET_MULTI
  is '倍数';
comment on column T_BET_ORDER.PLAN
  is '方案';
comment on column T_BET_ORDER.TYPE
  is '类型 :''1''单代 ''2'' 单合  ''3 ''复代 ''4''复合';
comment on column T_BET_ORDER.FORMAT_STR
  is '格式转换字符';
comment on column T_BET_ORDER.SINGLE_MONEY
  is '单倍金额(合买时  即每一份多少钱)';
comment on column T_BET_ORDER.ALL_MONEY
  is '总金额   代购时:用户应该付的总投注金额  合买时：所有用户的投注额';
comment on column T_BET_ORDER.DIVIDE_COPYS
  is '分成份数(即总份数)';
comment on column T_BET_ORDER.FLOOR_COPYS
  is '保底份数';
comment on column T_BET_ORDER.RECRUIT_USERS
  is '招股对象';
comment on column T_BET_ORDER.PLAN_CODE
  is '方案编号';
comment on column T_BET_ORDER.ORDER_NO
  is '订单号';
comment on column T_BET_ORDER.BET_CATEGOTY
  is '投注彩种    1: 胜负14场      2:任9场  3:4场进球  5:6 场半全场     61:单场半全场  62:单场比分 63:单场让球胜平负  64:单场上下单双 65:单场总进球';
comment on column T_BET_ORDER.ORDER_STATUS
  is '订单状态:''1''  :未支付、''2'':待出票、''3'':已出票、''4'':已取消、''5'':已过期';
comment on column T_BET_ORDER.ZJ_STATUS
  is '中奖状态;   ''1'':未开奖 ''2'':已返奖  ''3'':未中奖';
comment on column T_BET_ORDER.BET_USERNAME
  is '投注人用户名';
comment on column T_BET_ORDER.SPONSOR_TYPE
  is '发起类型   ''1'':发起人   ''2''  参与合买    ''0''代购';
comment on column T_BET_ORDER.SPONSOR_BET_ID
  is '参与合买时的发起人投注ID  关联BET_ID  如果SPONSOR_BET_ID 为空  且TYPE=''2''  OR TYPE=''4'' 即为发起合买';
comment on column T_BET_ORDER.SUBSCRIBE_COPYS
  is '认购份数(合买时)';
comment on column T_BET_ORDER.SUBSCRIBE_MONEY
  is '认购金额(合买时)    如果是代购  则表示用户已经付的金额   可能少于ALL_MONEY   ';
comment on column T_BET_ORDER.BONUS
  is '奖金';
comment on column T_BET_ORDER.TC_RATE
  is '提成比例';
comment on column T_BET_ORDER.WICI_TYPE
  is '过关类型    ''1'':普通过关  ''2'': 组合过关   ''3'':自由过关 ';
comment on column T_BET_ORDER.PHASE_ID
  is '期次ID';
comment on column T_BET_ORDER.KING_ID
  is '用于自动跟单使用  关联T_KING_SPONSOR表的主键';
comment on column T_BET_ORDER.ALREADY_BUY_COPYS
  is '合买时已经合买的份数';
comment on column T_BET_ORDER.END_TIME
  is '方案截止时间';
comment on column T_BET_ORDER.TXT_PATH
  is 'TXT文件路径';
comment on column T_BET_ORDER.SECURITY_TYPE
  is '保密类型:  1:完全公开  2:截止后公开   3:仅对跟单用户公
开';
comment on column T_BET_ORDER.PLAN_TITLE
  is '方案标题(仅对发起方案使用)';
comment on column T_BET_ORDER.PLAN_DESC
  is '方案描述(仅对发起方案使用)';
comment on column T_BET_ORDER.BET_SEL_INFO
  is '投注选择信息  4-2, 3-1,2-2,1-11';
comment on column T_BET_ORDER.CORRECT_CHANGCI
  is '猜对的场次';
comment on column T_BET_ORDER.IS_TOP
  is '是否置顶';
comment on column T_BET_ORDER.IS_HOT
  is '热门合买推荐方案';
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_BET_ORDER
  add constraint BET_ORDER_PK_ID primary key (BET_ID)
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
