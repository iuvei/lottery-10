


select a.ORDER_NO, --订单号
       a.BUY_USERNAME, --购买人用户名
       b.XINSHUI_NO, --心水编号(与订单号类似)
       b.TX_USERNAME, --发布人(民间高手用户名)
       b.HOST_NAME, --主队
       b.GUEST_NAME, --客队名称
       b.RACE_NAME, --赛事名称(包括  联赛 杯赛)
       c.START_TIME, --开赛时间
       b.ENSURE_MONEY, --保证金
       b.PRICE,   --心水价格
       a.BUY_TIME, --订购时间(产生订单时间)
       decode(a.PAY_STATUS,'1','未支付','2','已支付','3','已扣款','4','已赔付','5','已取消') PAY_STATUS  --状态
       from  T_XINSHUI_LOG  a,T_C2C_PRODUCT b,T_AGAINST c
       where a.PRODUCT_ID=b.C2C_ID  and b.AGAINST_ID=c.AGAINST_ID
