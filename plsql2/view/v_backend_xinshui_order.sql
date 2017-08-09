create or replace view v_backend_xinshui_order as
select a.ORDER_NO, --������
       a.BUY_USERNAME, --�������û���
       a.buy_user_id,--�������û�Id
       b.XINSHUI_NO, --��ˮ���(�붩��������)
       b.TX_USERNAME, --������(�������û���)
       b.HOST_NAME, --����
       b.GUEST_NAME, --�Ͷ�����
       b.RACE_NAME, --��������(����  ���� ����)
       c.START_TIME, --����ʱ��
       b.ENSURE_MONEY ,--��֤��
       b.PRICE,   --��ˮ�۸�
       decode(b.Type,'1','����','2','��С��') Type,--����
       a.BUY_TIME, --����ʱ��(��������ʱ��)
       decode(a.PAY_STATUS,'1','δ֧��','2','��֧��','3','�ѿۿ�','4','���⸶','5','��ȡ��') PAY_STATUS, --״̬
       --a.PAY_STATUS,--״̬
       c.race_id--����ID
       ,b.c2c_id--��ˮ����
       ,a.xinshui_order_id order_id--��ˮ����ID
       from  T_XINSHUI_ORDER a,T_C2C_PRODUCT b,T_AGAINST c
       where a.PRODUCT_ID=b.C2C_ID  and b.AGAINST_ID=c.AGAINST_ID

