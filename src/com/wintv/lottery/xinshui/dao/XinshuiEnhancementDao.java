package com.wintv.lottery.xinshui.dao;

import java.util.Date;
import com.wintv.framework.dao.BaseDao;
import com.wintv.framework.exception.DaoException;

public interface XinshuiEnhancementDao  extends BaseDao{
	/**
	 * 判断当前用户是否有权限查看该心水 2010-05-06 09:31
	 * 返回结果:
	 *  1:可以查看
	 *  0:没有权限查看
	 */
    public int isAllowLook(Long c2cId,Long userId)throws DaoException;
    public Date loadStartTime(Long againstId)throws DaoException;
    /**
	 * 根据对阵ID加载开赛时间
	 */
    public Date loadStartTimeByC2C(Long c2cId)throws DaoException;
}
