package com.wintv.lottery.index.service;

import java.util.List;
import java.util.Map;

import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.index.vo.BetOrderVO;
import com.wintv.lottery.index.vo.ExpertVO;

public interface IndexService {
	/**对应bet/wdlose.jsp的 【合买推荐】   2010-04-27 14:10
	 *  List<BetOrderVO>:list合买方案列表
	 *  String:deadline:截止时间
	 **/
	public Map  findHemaiRecommendedPlanList(Long phaseId,String betcategory)throws DaoException;
	//网站首页:左边部分 2010-04-23 10:33
	public Map leftData()throws DaoException;
	/**
	 * 热门关键词 2010-04-23 09:26
	 *返回:
	 * List<String>
	 */
	public List<String>  findHotKeys()throws DaoException;
	/**
	 * 精选方案  2010-04-21 09:17
	 *
	 */
	public List<BetOrderVO> findJingXuanPlanList(Map params)throws DaoException;
	//网站首页:名家足球推荐 2010-04-21 10:32
	public List<ExpertVO> findFamousExpertList()throws DaoException;
}
