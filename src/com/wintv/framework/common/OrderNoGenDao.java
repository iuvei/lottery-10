package com.wintv.framework.common;

import com.wintv.framework.exception.DaoException;

public interface OrderNoGenDao {
	public String gen(String flg)throws DaoException;
}
