package com.wintv.lottery.admin.phase.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wintv.framework.common.Constants;
import com.wintv.framework.exception.DaoException;

import com.wintv.framework.pojo.Against;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.District;
import com.wintv.framework.pojo.Race;

import com.wintv.framework.pojo.LotteryPhase;
import com.wintv.lottery.admin.phase.dao.PhaseDao;
import com.wintv.lottery.admin.phase.service.PhaseService;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.admin.phase.vo.LotteryPhasePO;
import com.wintv.lottery.admin.team.dao.AgainstDao;


@Service("phaseService")
public class PhaseServiceImpl  implements PhaseService{
	private static final Log log=LogFactory.getLog(PhaseServiceImpl.class);
	
	
	//=====================================================================================================
	/**
	 * 【3.2.2.2足彩期次录入后台列表】对应文档第10页
	 * 参数:
	 *   flg="1":添加时间
	 *   flg="2":开售时间
	 *   from:起始时间
	 *   to:结束时间
	 *   startRow:起始记录数
	 *   pageSize:每页最大记录数
	 * @return
	 * @throws DaoException
	 */
	public List<LotteryPhasePO> findPhaseList(Map params,int startRow,int pageSize )throws DaoException{
		return this.phaseDao.findPhaseList(params, startRow, pageSize);
	}
	/**
	 * 【3.2.2.2足彩期次录入后台列表】 分页记录数统计对应文档第10页
	 * 参数:
	 *   flg="1":添加时间
	 *   flg="2":开售时间
	 *   from:起始时间
	 *   to:结束时间
	 *   startRow:起始记录数
	 *   pageSize:每页最大记录数
	 * @return
	 * @throws DaoException
	 */
	public int findPhaseSize(Map params)throws DaoException{
		return this.phaseDao.findPhaseSize(params);
	}
	/**
	 * 根据主键查询对阵表
	 * @param id
	 * @return
	 */
	public AgainstVo loadAgainstById(String category,Long id)throws DaoException{
		return this.againstDao.loadAgainstById(category,id);
	}
	/**
	 * 期次发布时 选择区域，国家
	 */
	public List<District> findDistrict(Long parentId){
		
		return this.phaseDao.findDistrict(parentId);
	}
	/**
	 * 期次发布时 选择区域，国家
	 */
	public List<Race> findRaceList(Map params){
		return this.phaseDao.findRaceList(params);
	}
	/**
	 * 根据条件查询对阵信息
	 * @param params
	 * @return
	 */
	public List findAgainst(Map params){
		return this.phaseDao.findAgainst(params);
	}
	/**
	 * 【根据条件查询对阵记录总数 文档第7页】 2010-02-11 09:57
	 * @param params
	 * @return
	 */
	public int findAgainsSize(Map params){
		return this.phaseDao.findAgainsSize(params);
	}
	/**
	 * 查询所有联赛或杯赛
	 * @return
	 * @throws DaoException
	 */
	public List findRaceList()throws DaoException{
		return null;
	}
	/**
	 * 修改期次
	 * 参数:
	 * flg='1':审核
	 * 
	 * 
	 * flg='3':修改期次
	 * flg='4':公布期次
	 * flg='5':停止期次
	 * flg='6':作废期次
	 * 
	 * flg='8':公布赛果
	 * 
	 * CSHandleLog:客服日志类
	 * memo:  客服记录内容
	 * ip:    客服处理时机器的IP地址
	 */
	@Transactional(rollbackFor = DaoException.class)
	public boolean updatePhase(Map params)throws DaoException{
		String flg=(String)params.get("flg");
		if("20".equals(flg)){//更新对阵比分
			String score=(String)params.get("score");
			String againstId=(String)params.get("againstId");
			return this.phaseDao.updateBySql("update T_AGAINST  t  set  t.score='"+score+"' where  t.AGAINST_ID=TO_NUMBER('"+againstId+"')");
		}
		CSHandleLog log=(CSHandleLog)params.get("log");
		Long id=(Long)params.get("id");
		if(log!=null){
			  this.phaseDao.saveObject(log);//保存客服的相关信息
		}
		if("8".equals(flg)){//公布赛果
			 this.phaseDao.pubRaceResult(params);
			 return true;
		}else if("4".equals(flg)||"5".equals(flg)||"6".equals(flg)){//公布期次 停止期次 作废期次
			//公布期次
			return this.phaseDao.updateBySql("update  T_LOTTERY_PHASE t  set t.STATUS='"+flg+"'  where  t.ID="+id);
		}else if("1".equals(flg)){//审核期次
			//1.更改期次状态为已经发布('4')
			return this.phaseDao.updateBySql("update  T_LOTTERY_PHASE t  set t.STATUS='"+Constants.PHASE_ALREADY_PUBLISHED+"'  where t.ID="+id);
		}
		return false;
		
	   
	}
	/**
	 * 保存期次
	 * @param po
	 * @return
	 */
	@Transactional(rollbackFor = DaoException.class)
	public int savePhase(Map<String,String[]> params)throws DaoException{
		 return phaseDao.savePhase(params);
	}
	public  LotteryPhase loadLotterySchedule(Long id){
		return  phaseDao.read(id);
	}
	public boolean updateStatus(Map params){
		return phaseDao.updateStatus(params);
	}
	
	@Transactional(rollbackFor = DaoException.class)
	public Map  loadLotteryPhase(Long id)throws DaoException{
		Map result=new HashMap();
		LotteryPhasePO po=this.phaseDao.loadLotteryPhase(id);
		List againstList=this.phaseDao.loadAgainstList(id);
		result.put("po", po);
		result.put("againstList", againstList);
	  return result;
	}
	@Transactional(rollbackFor = DaoException.class)
	public Long saveCSHandleLog(CSHandleLog log){
		return this.phaseDao.saveCSHandleLog(log);
	}
	@Autowired
	private PhaseDao phaseDao;
	@Autowired
	private AgainstDao againstDao;

}
