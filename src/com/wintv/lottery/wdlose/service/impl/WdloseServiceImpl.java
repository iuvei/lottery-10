package com.wintv.lottery.wdlose.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wintv.framework.exception.DaoException;
import com.wintv.lottery.wdlose.dao.WdloseDao;
import com.wintv.lottery.wdlose.service.WdloseService;
import com.wintv.lottery.wdlose.vo.KjVO;
@Service("wdloseService")
public class WdloseServiceImpl implements WdloseService{
	/**
	 * 4场进球彩主队与客队 2010-05-07 13:20
	 *参数:
	 * phaseNo:其次号
	 * 
	 */
	public List<String>  find4Against(String phaseNo)throws DaoException{
		return this.wdloseDao.find4Against(phaseNo);
	}
	/***4场进球  2010-04-29 13:23*/
	public KjVO bulletin4(String phaseNo)throws DaoException{
		return this.wdloseDao.bulletin4(phaseNo);
	}
	/**
	 * 任9场  胜负彩14场
	 */
	public String[] bulletin14(String phaseNo)throws DaoException{
		return this.wdloseDao.bulletin14(phaseNo);
	}
	public KjVO bulletin6(String phaseNo)throws DaoException{
		return this.wdloseDao.bulletin6(phaseNo);
	}
	/**开奖期次列表 2010-04-28 17:58
	 *参数：
	 *category:
	 *  1: 胜负14场      2:任9场  3:4场进球  5:6 场半全场     
	 *  61:单场半全场  62:单场比分 63:单场让球胜平负  64:单场上下单双 65:单场总进球
	 **/
	public List<String> findPhaseList(String category)throws DaoException{
		return this.wdloseDao.findPhaseList(category);
	}
	 /* 
	 *参数：
	 * phaseNo:其次号
	 * betCategory：'6':胜负彩期次 '8':半全场期次 '1':北京单场期次
	 */
	public List<String>  findHostList(String phaseNo,String  betCategory)throws DaoException{
		return this.wdloseDao.findAgainst(phaseNo, betCategory);
	}
	
	@Resource
	private WdloseDao wdloseDao;
}
