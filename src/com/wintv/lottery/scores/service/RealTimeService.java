package com.wintv.lottery.scores.service;

import com.wintv.framework.exception.DaoException;
import java.util.List;
public interface RealTimeService {
	/**
	 * 查询数据库最新的足彩即时比分列表
	 * 
	 */
	   public List findFBRealTimeScoreList()throws DaoException;
	   
	   /**
		 * 查询数据库最新的单场即时比分列表
		 * 
		 */
	   public List findSceneRealTimeScoreList()throws DaoException;
}
