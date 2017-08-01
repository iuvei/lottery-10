package com.wintv.lottery.wdlose.service;

import java.util.List;

import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.wdlose.vo.KjVO;

public interface WdloseService {
	/**
	 * 4场进球彩主队与客队 2010-05-07 13:20
	 *参数:
	 * phaseNo:其次号
	 * 
	 */
	public List<String>  find4Against(String phaseNo)throws DaoException;
	public String[] bulletin14(String phaseNo)throws DaoException;
	public KjVO bulletin6(String phaseNo)throws DaoException;
	/**开奖期次列表 2010-04-28 17:58
	 *参数：
	 *category:
	 *  1: 胜负14场      2:任9场  3:4场进球  5:6 场半全场     
	 *  61:单场半全场  62:单场比分 63:单场让球胜平负  64:单场上下单双 65:单场总进球
	 **/
	public List<String> findPhaseList(String category)throws DaoException;
	 /* 
	 *参数：
	 * phaseNo:其次号
	 * betCategory：'6':胜负彩期次  '8':半全场期次 '1':北京单场期次
	 */
	public List<String>  findHostList(String phaseNo,String  betCategory)throws DaoException;
	public KjVO bulletin4(String phaseNo)throws DaoException;
}
