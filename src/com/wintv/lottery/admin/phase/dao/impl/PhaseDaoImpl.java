package com.wintv.lottery.admin.phase.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Map;

import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.Constants;
import com.wintv.framework.common.OracleSqlUtil;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.CSHandleLog;
import com.wintv.framework.pojo.District;
import com.wintv.framework.pojo.LotteryPhase;
import com.wintv.framework.pojo.Race;
import com.wintv.lottery.admin.phase.dao.PhaseDao;
import com.wintv.lottery.admin.phase.vo.AgainstVo;
import com.wintv.lottery.admin.phase.vo.LotteryPhasePO;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

@Repository("phaseDao")
@SuppressWarnings("unchecked")
public class PhaseDaoImpl  extends BaseHibernate<LotteryPhase,Long> implements PhaseDao{
	private static final Log log=LogFactory.getLog(PhaseDaoImpl.class);
	/**根据期次ID查询开奖号码**/
	public String loadKjNo(Long phaseId)throws DaoException{
		String sql="select t.kj_no  from t_lottery_phase t where  t.id=?";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	    try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1,phaseId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				return rs.getString("kj_no");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
		return null;
	}
	//======================================以下是投注部分2010 03-05 11:53==============================================================
	/**
	 * 根据汽车主键 查询该期次已经过期的对阵数量
	 * id:期次主键
	 */
	public int loadDeadAgainstSize(Map params)throws DaoException{
		 Long id=(Long)params.get("id");
		 StringBuilder sql=new StringBuilder("select count(1)  cnt from T_LOTTERY_PHASE a,T_LOTTERY_AGAINST b,T_AGAINST c ");
		 sql.append(" where b.phase_id=a.id and b.against_id=c.against_id ");
         sql.append(" and c.start_time-"+Constants.TIME_INTERVAL+"/24/60<sysdate and a.id=?");
         SQLQuery q=this.getSession().createSQLQuery(sql.toString());
         q.setLong(0, id);
         q.addScalar("cnt", Hibernate.INTEGER);
         return (Integer)q.uniqueResult();
	
	}
	 /**
	 * 查询某彩种的期次号  对阵列表  复式投注截止时间  2010-03-03 13:12
	 * phaseCategory:期次分类  '6':胜负彩期次    '9':进球彩期次 '8':半全场期次 '1':北京单场期次
	 * isCurrent: 足彩期次是否是当前期次 '1':当前期次  '2':预售期次 3:历史期次
	 * phaseId:期次ID,如果有期次ID 则直接安装主键去查询期次信息已经相应的对阵列表
	 * @since 2010 03-4 16:15
	 */

	public LotteryPhasePO loadCurrentPhase(Map params)throws DaoException{
		 Long phaseId=(Long)params.get("phaseId"); 
		 String phaseCategory=(String)params.get("phaseCategory");
		 String isCurrent=(String)params.get("isCurrent"); 
		 StringBuilder sql=null;
		 log.info("isCurrent="+isCurrent+"  phaseCategory="+phaseCategory);
		 if(phaseId!=null){
			 sql=new StringBuilder("select t.ID,to_char(t.MUL_DEADLINE,'yyyy-mm-dd hh24:mi') MUL_DEADLINE,t.PHASE_NO,to_char(S_DEADLINE,'yyyy-mm-dd hh24:mi') from T_LOTTERY_PHASE t where t.ID=?");
		}else if("1".equals(isCurrent)){//当前期次
		   sql=new StringBuilder("select t.ID,to_char(t.MUL_DEADLINE,'yyyy-mm-dd hh24:mi') MUL_DEADLINE,t.PHASE_NO,to_char(S_DEADLINE,'yyyy-mm-dd hh24:mi') from T_LOTTERY_PHASE t where t.CATEGORY=?  and t.STATUS='4'");
		   sql.append(" and t.TKP_DEADLINE > sysdate and  t.SOLD_TIME<=sysdate");
		 }else if("2".equals(isCurrent)){//预售期次
			sql=new StringBuilder("select t.ID,to_char(t.MUL_DEADLINE,'yyyy-mm-dd hh24:mi') MUL_DEADLINE,t.PHASE_NO,to_char(S_DEADLINE,'yyyy-mm-dd hh24:mi') from T_LOTTERY_PHASE t where t.CATEGORY=?  and t.STATUS='4'");
			sql.append(" and t.TKP_DEADLINE > sysdate and  t.SOLD_TIME>sysdate");
		 }else if("3".equals(isCurrent)){
			    sql=new StringBuilder("select t.ID,to_char(t.MUL_DEADLINE,'yyyy-mm-dd hh24:mi') MUL_DEADLINE,t.PHASE_NO,to_char(S_DEADLINE,'yyyy-mm-dd hh24:mi') from T_LOTTERY_PHASE t where t.CATEGORY=?  and t.STATUS='4'");
				sql.append(" and t.TKP_DEADLINE < sysdate and  t.SOLD_TIME<sysdate");
		 }else if("4".equals(isCurrent)){//没有数据时
			 sql=new StringBuilder("select t.ID,to_char(t.MUL_DEADLINE,'yyyy-mm-dd hh24:mi') MUL_DEADLINE,t.PHASE_NO,to_char(S_DEADLINE,'yyyy-mm-dd hh24:mi') from T_LOTTERY_PHASE t where t.CATEGORY=?  and t.STATUS='4'");
			   sql.append(" and t.phase_no=(select max(m.phase_no)  from T_LOTTERY_PHASE m)");
		 }
         List list=null;
        
         if(phaseId!=null){
             list=this.getSession().createSQLQuery(sql.toString()).setLong(0, phaseId).list();
         }else{
        	 list=this.getSession().createSQLQuery(sql.toString()).setString(0, phaseCategory).list();
         }
         LotteryPhasePO po=null;
         //log.info("WJJ="+sql.toString());
         if(list!=null&&list.size()==1){
        	  Object[] o=(Object[])list.get(0);
        	  po=new LotteryPhasePO();
        	  Long id=new Long(o[0].toString());
        	  po.setId(id);
        	  String mulDeadline=o[1]==null?"":o[1].toString();
        	  po.setMulDeadline(mulDeadline);
        	  String phaseNo=o[2]==null?"":o[2].toString();
        	  po.setPhaseNo(phaseNo);
        	  String singleDeadline=o[3]==null?"":o[3].toString();
        	  po.setSingleDeadline(singleDeadline);
         }
         return po;
		 
	}
	/**
	 * 投注模块  根据期次查询对阵列表
	 */
	public List<AgainstVo> findLotteryPhaseAgainstList(Map params)throws DaoException{
		 Long phaseId=(Long)params.get("phaseId");//期次表ID
		 StringBuilder sql=new StringBuilder("select b.RACE_NAME,b.HOST_NAME,b.GUEST_NAME,to_char(b.START_TIME,'yyyy-mm-dd hh24:mi') START_TIME,");
		 sql.append(" b.AGAINST_ID,b.RACE_ID,b.HOST_ID,b.GUEST_ID,b.CONCEDE,b.SCOREA,b.BG_COLOR ");
		 sql.append(" ,case when b.START_TIME-"+Constants.TIME_INTERVAL+"/24/60 <=sysdate then  0  else  1  end  isAvaliable,b.SCOREB,b.Score");
		 sql.append(" ,a.changci from T_LOTTERY_AGAINST a,T_AGAINST b where a.AGAINST_ID=b.AGAINST_ID and  a.PHASE_ID=?");
		 sql.append(" order by a.changci ");
		 log.info(sql.toString());
		 List<Object[]> list =this.getSession().createSQLQuery(sql.toString()).setLong(0, phaseId).list();
		 List<AgainstVo> resultList=new ArrayList<AgainstVo>();
		 AgainstVo po=null;
		 for(Object[] o:list){
			 po=new AgainstVo();
			 String raceName=o[0].toString();
			 po.setRaceName(raceName);
			 String hostName=o[1].toString();
			 po.setHostName(hostName);
			 String guestName=o[2].toString();
			 po.setGuestName(guestName);
			 String startTime=o[3].toString();
			 po.setStartTime(startTime);
			 Long againstId=new Long(o[4].toString());
			 po.setAgainstId(againstId);
			 Long raceId=new Long(o[5].toString());
			 po.setRaceId(raceId);
			 Long hostId=new Long(o[6].toString());
			 po.setHostId(hostId);
			 Long gusetsId=new Long(o[7].toString());
			 po.setGusetsId(gusetsId);
			 String concede=o[8]==null?"0":o[8].toString();//让球
			 po.setConcede(concede);
			 String scoreA=o[9]==null?"":o[9].toString();//比分
			
			 po.setScore(scoreA);
			
			 
			
			 String bgColor=o[10]==null?"":o[10].toString();//联赛的颜色
			 po.setBgColor(bgColor);
			 int isAvaliable=Integer.parseInt(o[11].toString());
			 po.setIsAvaliable(isAvaliable);
			 String scoreB=o[11]==null?"":o[11].toString();
			 po.setScoreB(scoreB);
			 
			 String score=o[12]==null?"":o[12].toString();
			 po.setScore(score);
			 Object changci=o[14];
			 if(changci!=null){
				 po.setChangci(changci.toString());
			 }
			 po.setResultNo("0");
			 resultList.add(po);
		 }
		return resultList;
	}
	
	
	//=====================================================================================================
	/**
	 * 公布赛果??好像比较复杂
	 * @param params
	 */
	public void pubRaceResult(Map params)throws DaoException{
		Connection conn=null;
		CallableStatement cstmt=null;
		String flg=(String)params.get("flg");
		String result=(String)params.get("result");
		try{
		  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
          cstmt = conn.prepareCall("{call PHASE.batchPubRaceResult(?,?)}"); 
		  cstmt.setString(1, flg);
		  cstmt.setString(2, result);
		  cstmt.executeUpdate();
		}catch(Exception e){
			throw new DaoException(e.getLocalizedMessage());
		}finally{
			try{
			  cstmt.close();
			  conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * 【保存期次  对于同一彩种(注意保持当期期次的时候  必须校验 系统中是否存在当期期次)】 2010-02-20 13:52
	 */
	public int savePhase(Map<String,String[]> params)throws DaoException{
		  Connection conn=null;
		  CallableStatement proc=null;
		  boolean bFlag = false;
		   try{
			   conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			   String sql = "{call phase.savePhase(?,?,?,?,?,?,?,?)}"; 
			   proc = conn.prepareCall(sql); 
			   String category=params.get("category")[0];
			   proc.setString(1, params.get("selAgainstId")[0]); 
			   proc.setString(2, category); 
			   proc.setString(3, params.get("phaseNo")[0]); 
			   proc.setString(4, params.get("soldTime")[0]); 
			   proc.setString(5, params.get("tkpDeadline")[0]); 
			   if(Constants.ORDER_CATEGORY_FBSINGLE.equals(category)){//北京单场
				   log.info("WK****="+params.get("viciDeadline").toString());
				   proc.setString(6, params.get("viciDeadline")[0]); //普通过关截止时间(只针对北京单场)
				   proc.setString(7, params.get("comboVici")[0]); //组合与自由过关(只针对北京单场)
			   }else{
			      proc.setString(6, params.get("mulDeadline")[0]); 
			      proc.setString(7, params.get("sDeadline")[0]); 
			   }
			   proc.registerOutParameter(8, java.sql.Types.INTEGER);
			   bFlag = proc.execute(); 
			   int result=proc.getInt(8);
			  return result;
			   
			}catch(Exception e){
				e.printStackTrace();
				//throw new DaoException(e.getLocalizedMessage());
			}finally{
				try{
					  proc.close();
					  conn.close();
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
			return -1;

	}
	public LotteryPhasePO loadLotteryPhase(Long phaseId){
		
		StringBuilder sql=new StringBuilder("select t.CATEGORY,t.PHASE_NO,t.STATUS,to_char(t.ADD_TIME,'yyyy-mm-dd hh24:mi') ADD_TIME,t.USERNAME,to_char(t.SOLD_TIME,'yyyy-mm-dd hh24:mi') SOLD_TIME ");
		sql.append(",case when  t.tkp_deadline>sysdate and  (t.sold_time<=sysdate or t.iscurrent='1') then cast('当前期次' as VARCHAR2(12)) ");
		sql.append("when  t.tkp_deadline>sysdate  and t.sold_time>sysdate then  cast('预售期次'  as VARCHAR2(12))  end lotteryType,to_char(t.TKP_DEADLINE,'yyyy-mm-dd hh24:mi') TKP_DEADLINE ");
		sql.append(" ,to_char(t.MUL_DEADLINE,'yyyy-mm-dd hh24:mi') MUL_DEADLINE,t.S_DEADLINE,t.VICI_DEADLINE,t.COMBO_VICI ");
		sql.append(" from T_LOTTERY_PHASE  t where  t.ID=?");
		SQLQuery q=getSession().createSQLQuery(sql.toString());
		q.setLong(0, phaseId);
	    Object[] obj=(Object[])q.uniqueResult();
	    LotteryPhasePO po=new LotteryPhasePO();
	    String category=obj[0].toString();
	    po.setCategory(category);
	    String phase=(String)obj[1];
	    po.setPhaseNo(phase);
	    String status=obj[2].toString();
	    po.setStatus(status);
	    String addTime=obj[3]!=null?obj[3].toString():"";
	    po.setAddTime(addTime);
	    String username=obj[4]!=null?obj[4].toString():"";
	    po.setUsername(username);
	    String soldTime=obj[5].toString();
	    po.setSoldTime(soldTime);
	    String lotteryType=obj[6]!=null?obj[6].toString():null;
	    po.setPhaseType(lotteryType);
	    po.setTkpDeadline(obj[7].toString());
	    if("1".equals(po.getCategory())){//如果是北京单场
	    	
	    }else{
	    	po.setMulDeadline(obj[8].toString());
	    	po.setSingleDeadline(obj[9].toString());
	    }
	    return po;
	}
	public  List<AgainstVo>  loadAgainstList(Long phaseId)throws DaoException{
		
		SQLQuery q=getSession().createSQLQuery("select b.HOST_NAME,b.GUEST_NAME,to_char(b.START_TIME,'yyyy-mm-dd hh24:mi') START_TIME,b.RACE_NAME,b.SCORE,b.TYPE,b.AGAINST_ID  from T_LOTTERY_AGAINST a,T_AGAINST b where a.AGAINST_ID=b.AGAINST_ID and  a.PHASE_ID=?");
		q.setLong(0, phaseId);
	    List list=q.list();
	    AgainstVo  against=null;
		List againstList=new ArrayList();
		for(Iterator e=list.iterator();e.hasNext();){
		   Object[] obj=(Object[])e.next();
		   against=new AgainstVo();
	       String hostName=(String)obj[0];
		   against.setHostName(hostName);
		   String guestName=(String)obj[1];
		   against.setGuestName(guestName);
		   String startTime=(String)obj[2];
		   against.setStartTime(startTime);
		   String raceName=(String)obj[3];
		   against.setRaceName(raceName);
		   
		   String score=(String)obj[4];
		   if(score==null){
			   score="";
		   }
		   against.setScore(score);
		   
		   String type=obj[5].toString();
		   if("1".equals(type)){
		     against.setType("联赛");
		   }
		   else if("2".equals(type)){
			   against.setType("杯赛");
		   }else{
			   against.setType("其他");
		   }
		   Long againstId=new Long(obj[6].toString());
		   against.setAgainstId(againstId);
		
		   againstList.add(against);
	  }
		
	 return againstList;		
	
	}
	
	/**
	 * 【更新期次状态】对应文档第10页   公布期次   审核期次   停止期次  作废期次
	 * 期次状态(STATUS)  '1':期次未审核、'2':对阵未审核、'3':赛果未审核、'4':已公布、'5':已停止、'6':已作废、'7':已到期、'8':已完成
	 */
    public boolean updateStatus(Map params){
	    Long id=(Long)params.get("id");
	    String status=(String)params.get("status");
		return updateBySql("update  T_LOTTERY_PHASE t where t.ID="+id+" and t.STATUS='"+status+"'");
	}
	
	/**
	 * 【根据条件查询对阵信息，文档第7页】 2010-02-11 09:57
	 * @param params
	 * @return
	 */
	public List findAgainst(Map params){
	    StringBuilder sql=new StringBuilder("select t.HOST_NAME,t.GUEST_NAME,t.AGAINST_ID from  T_AGAINST t where  1=1 ");
		sql.append(" and t.START_TIME > sysdate ");
		String areaId=(String)params.get("areaId");
		if(StringUtils.isNotEmpty(areaId)){
			sql.append(" and t.AREA_ID="+new Long(areaId));
		}                             
		Long countryId=(Long)params.get("countryId");
		if(countryId!=null){
			sql.append(" and t.COUNTRY_ID="+countryId);
		}
		Long raceId=(Long)params.get("raceId");
		if(raceId!=null){
			sql.append("  and  t.RACE_ID="+raceId);
		}
		Long raceSeasonId=(Long)params.get("raceSeasonId");
		if(raceSeasonId!=null){
			sql.append("  and  t.RACE_SEASON_ID="+raceSeasonId);
		}
		Long roundsId=(Long)params.get("roundsId");
		if(roundsId!=null){
			sql.append("  and  t.ROUNDS="+roundsId);
		}
		
		int startRow=(Integer)params.get("startRow");
		int pageSize=(Integer)params.get("pageSize");
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<AgainstVo> resultList=new ArrayList<AgainstVo>();
		try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      sql=OracleSqlUtil.addQueryPageSizeCondition(sql,startRow,pageSize);
		      log.info(sql.toString());
		      pstmt = conn.prepareStatement(sql.toString());
		      rs=pstmt.executeQuery();
		      AgainstVo po=null;
		      while(rs.next()){
			     po=new AgainstVo();
			     String hostName=rs.getString("HOST_NAME");
			     po.setHostName(hostName);
			     String guestName=rs.getString("GUEST_NAME");
			     po.setGuestName(guestName);
			     po.setAgainstId(rs.getLong("AGAINST_ID"));
			     resultList.add(po);
		      }
		  }catch(Exception e){
			    	e.printStackTrace();
		  }finally{
			 closeAll(rs,pstmt,conn);
		  }
	  return resultList;
	}
	/**
	 * 【根据条件查询对阵记录总数 文档第7页】 2010-02-11 09:57
	 * @param params
	 * @return
	 */
	public int findAgainsSize(Map params){
		StringBuilder sql=new StringBuilder("select count(1) cnt from  T_AGAINST t  where 1=1 ");
		sql.append(" and  t.START_TIME >sysdate ");
		String areaId=(String)params.get("areaId");
		if(areaId!=null){
			sql.append(" and t.AREA_ID="+new Long(areaId));
		}
		Long countryId=(Long)params.get("countryId");
		if(countryId!=null){
			sql.append(" and t.COUNTRY_ID="+areaId);
		}
		
		SQLQuery q=getSession().createSQLQuery(sql.toString());
		q.addScalar("cnt", Hibernate.INTEGER);
		return (Integer)q.uniqueResult(); 
	}
	/**
	 * 期次发布时 选择区域，国家
	 */
	public List<District> findDistrict(Long parentId){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List result=null;
		try{
		  StringBuilder sql=new StringBuilder("select distinct b.name,b.id from t_against a,t_district b  where");
		  if(parentId==null){
			sql.append(" a.area_id=b.id");
		  }else{
			sql.append("  a.country_id=b.id  ");
		  }
		  if(parentId!=null){
			sql.append(" and b.parent_id="+parentId);
		  }
		
		  conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		  pstmt = conn.prepareStatement(sql.toString());
	      rs=pstmt.executeQuery();
		  District po=null;
		  result=new ArrayList();
		  while (rs.next()) {
			  po=new District();
			  String name=rs.getString("name");
			  po.setName(name);
			  Long id=rs.getLong("id");
			  po.setId(id);
			result.add(po);
		  }
		return result;
		}catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	closeAll(rs,pstmt,conn);
	    }
		return result;
	}
	/**
	 * 期次发布时 选择区域，国家
	 */
	public List<Race> findRaceList(Map params){
		StringBuilder sql=null;
		String flg=(String)params.get("flg");
		log.info("=================================flg="+flg);
		if("2".equals(flg)){
			Long countId=(Long)params.get("countId");
		    sql=new StringBuilder("select distinct a.race_name,a.race_id  from t_against a,t_district b ");
		    sql.append("where  a.area_id=b.id and a.country_id="+countId);
		}else if("3".equals(flg)){//根据赛事ID 获取相关的赛季
			Long raceId=(Long)params.get("raceId");
			sql=new StringBuilder("select distinct t.race_season_name,t.race_season_id from t_against t  where t.race_id="+raceId);
		}else if("4".equals(flg)){
			Long raceSeasonId=(Long)params.get("raceSeasonId");
			sql=new StringBuilder("select distinct t.rounds_name,t.rounds,t.race_id from t_against t  where t.race_season_id="+raceSeasonId);
		}
		log.info(sql.toString());
        Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Race> result=new ArrayList();
		Race po=null;
		try{
			conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			pstmt=conn.prepareStatement(sql.toString());
			rs=pstmt.executeQuery();
			while(rs.next()){
				po=new Race();
				String name=null;
				Long id=null;
				if("2".equals(flg)){
				   name=rs.getString("race_name");
				   id=rs.getLong("race_id");
				}else if("3".equals(flg)){
					name=rs.getString("race_season_name");
					id=rs.getLong("race_season_id");
				}else if("4".equals(flg)){
					name=rs.getString("rounds_name");
					id=rs.getLong("rounds");
				}
				po.setName(name);
				po.setId(id);
			  result.add(po);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(rs,pstmt,conn);
		}
		log.info(result.size());
		return result;
	}
	//========================================================
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
		String flg=(String)params.get("flg");
		String from=(String)params.get("from");
		String to=(String)params.get("to");
		String category=(String)params.get("category");
		String  lotteryType=(String)params.get("lotteryType");
		String  status=(String)params.get("status");
		
		
		List<LotteryPhasePO> result=new ArrayList<LotteryPhasePO>();
		    //当前期次  状态:'已公布'   强烈建议  对同一彩种不要生成两个当前期次  以免造成混淆
			StringBuilder sql=new StringBuilder("select t.ID,to_char(t.SOLD_TIME,'yy-mm-dd hh24:mi') SOLD_TIME,t.USERNAME,");
			sql.append("decode(t.CATEGORY,'6','胜负彩','9','进球彩','8','半全场','1','北京单场'),");
			sql.append("t.PHASE_NO,to_char(t.MUL_DEADLINE,'yyyy-mm-dd hh24:mi') MUL_DEADLINE,to_char(t.S_DEADLINE,'yyyy-mm-dd hh24:mi') S_DEADLINE,t.STATUS,  ");
			
			//当前期次的状态可能是未发布 所以不需要STATUS='4'
			sql.append("case when  t.tkp_deadline>sysdate and  (t.sold_time<=sysdate or t.iscurrent='1') then cast('当前期次' as VARCHAR2(12)) ");
			sql.append("when  t.tkp_deadline>sysdate  and t.sold_time>sysdate then  cast('预售期次'  as VARCHAR2(12))  end lotteryType");
			//sql.append(" from T_LOTTERY_PHASE  t where 1=1  and t.TKP_DEADLINE > sysdate ");
			sql.append(" from T_LOTTERY_PHASE  t where 1=1 ");//2010-02-25 16:42修改
		    if("1".equals(lotteryType)){//当期期（包括尚未公布的用户是从当期期按钮区新增 以及已经公布的）
		    	sql.append(" (t.ISCURRENT='1' and  t.TKP_DEADLINE > sysdate) or (t.STATUS='4'  and t.TKP_DEADLINE> sysdate and t.SOLD_TIME< sysdate) ");
		    }
		    if("2".equals(lotteryType)){//预售期次
		    	sql.append("  t.TKP_DEADLINE> sysdate and t.SOLD_TIME > sysdate ");
		    }
			if("1".equals(flg)){//添加时间
				if(from!=null){
					sql.append(" and  t.ADD_TIME >= to_date("+from+",'yyyy-mm-dd')");
				}
				if(to!=null){
					sql.append(" and  t.ADD_TIME <=  to_date("+to+",'yyyy-mm-dd')");
				}
			}else if("2".equals(flg)){
				if(from!=null){
					sql.append(" and  t.SOLD_TIME >=to_date("+from+",'yyyy-mm-dd')");
				}
				if(to!=null){
					sql.append(" and  t.SOLD_TIME <=to_date("+to+",'yyyy-mm-dd')");
				}
			}
			if(StringUtils.isNotEmpty(category)){
				sql.append(" and  t.CATEGORY ='"+category+"'");
			}
			if(StringUtils.isNotEmpty(status)){
				sql.append(" and  t.STATUS ='"+status+"'");
			}
			SQLQuery q=getSession().createSQLQuery(sql.toString());
			q.setFirstResult(startRow);
			q.setMaxResults(pageSize);
			List<Object[]> list=q.list();
			LotteryPhasePO po=null;
			for(Object[] arr:list){
				po=new LotteryPhasePO();
				po.setId(new Long(arr[0].toString()));//主键
				po.setSoldTime((String)arr[1]);//开售时间
				String username=(String)arr[2];//添加人
				po.setUsername(username);
				String _category=(String)arr[3];//彩种
				po.setCategory(_category);
				String phase=(String)arr[4];
				po.setPhaseNo(phase);//期次
				String mulDeadline=(String)arr[5];
				po.setMulDeadline(mulDeadline);
				String singleDeadline=(String)arr[6];
				po.setSingleDeadline(singleDeadline);
				//期次是否为当前期次还是 预售期次 应该判断下
				//ISCURRENT='1':当前期次  而且要求 打票时间>当前时间(或者 打票时间>当前时间  且 开售时间<=当前时间  此时  可以为 不公布状态)  
				int statusValue=Integer.parseInt(arr[7].toString());
				String statusLabel=null;
				switch(statusValue){
				  case 1:
					  statusLabel="期次未审核";
					  break;
				  case 2:
					  statusLabel="对阵未审核";
					  break;
				  case 3:
					  statusLabel="赛果未审核";
					  break;
				  case 4:
					  statusLabel="已公布";
					  break;
				  case 5:
					  statusLabel="已停止";
					  break;
				  case 6:
					  statusLabel="已作废";
					  break;
				  case  7:
					  statusLabel="已到期";
					  break;
				  case 8:
					  statusLabel="已完成";
					  break;
				}
				po.setStatus(statusLabel);
				String  _lotteryType=arr[8]!=null?arr[8].toString():"";
				po.setIsCurrent(_lotteryType);//当期期次  还是预售期次
				result.add(po);
			}
			
	
		
		return result;
		
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
		String flg=(String)params.get("flg");
		Date from=(Date)params.get("from");
		Date to=(Date)params.get("to");
		String category=(String)params.get("category");
		String  lotteryType=(String)params.get("lotteryType");
		String  status=(String)params.get("status");
		//当前期次  状态:'已公布'   强烈建议  对同一彩种不要生成两天当前期次  以免造成混淆
			StringBuilder sql=new StringBuilder("select  count(*) cnt ");
			sql.append("  from T_LOTTERY_PHASE  t where 1=1 ");//2010-02-25 16:42修改
		    if("1".equals(lotteryType)){//当期期（包括尚未公布的用户是从当期期按钮区新增 以及已经公布的）
		    	sql.append(" (t.ISCURRENT='1' and  t.TKP_DEADLINE > sysdate) or (t.STATUS='4'  and t.TKP_DEADLINE> sysdate and t.SOLD_TIME< sysdate) ");
		    }
		    if("2".equals(lotteryType)){//预售期次
		    	sql.append("  t.TKP_DEADLINE> sysdate and t.SOLD_TIME > sysdate ");
		    }
			if("1".equals(flg)){//添加时间
				if(from!=null){
					sql.append(" and  t.ADD_TIME >= to_date("+from+",'yyyy-mm-dd')");
				}
				if(to!=null){
					sql.append(" and  t.ADD_TIME <=  to_date("+to+",'yyyy-mm-dd')");
				}
			}else if("2".equals(flg)){
				if(from!=null){
					sql.append(" and  t.SOLD_TIME >=to_date("+from+",'yyyy-mm-dd')");
				}
				if(to!=null){
					sql.append(" and  t.SOLD_TIME <=to_date("+to+",'yyyy-mm-dd')");
				}
			}
			if(StringUtils.isNotEmpty(category)){
				sql.append(" and  t.CATEGORY ='"+category+"'");
			}
			if(StringUtils.isNotEmpty(status)){
				sql.append(" and  t.STATUS ='"+status+"'");
			}
			
		
			SQLQuery q=this.getSession().createSQLQuery(sql.toString());
		       q.addScalar("cnt", Hibernate.INTEGER);
		       Integer size=(Integer)q.uniqueResult();
		     
		       return size.intValue();
	}
	
	public Long saveCSHandleLog(CSHandleLog log){
		return (Long)this.getHibernateTemplate().save(log);
	}

}
