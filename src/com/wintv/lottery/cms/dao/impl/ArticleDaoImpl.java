package com.wintv.lottery.cms.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.OracleResultSet;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.pojo.Article;
import com.wintv.lottery.cms.dao.ArticleDao;

@Repository("articleDao")
@SuppressWarnings("unchecked")
public class ArticleDaoImpl extends BaseHibernate<Article,Long> implements ArticleDao{
	//http://sorgalla.com/projects/jcarousel/examples/dynamic_flickr_api.html
	//http://mydioxide.com/?p=215
	/**
	 * http://192.168.1.75/CMS-文章列表.shtml
	 * channelId:分类
	 **/
	public Map findArticleList(Map params)throws DaoException{
		    Connection conn=null;
			CallableStatement proc=null;
			ResultSet rs=null;
			int startRow=(Integer)params.get("startRow");
			int pageSize=(Integer)params.get("pageSize");
			Long channelId=(Long)params.get("channelId");//分类ID
			List<Article> resultList=null;
			Map resultMap=null;
		    try{
		      conn =SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		      StringBuilder queryList=new StringBuilder("select t.article_id,t.title,to_char(t.release_date,'yyyy-mm-dd hh24:mi') release_date  from arti_article t where 1=1 ");
		      StringBuilder querySize=new StringBuilder(" select  count(1) cnt from arti_article  b  where  1=1 ");
		      if(channelId!=null){
		    	  queryList.append(" and a.channel_id="+channelId);
		    	  querySize.append(" and b.channel_id="+channelId);
		      }
		      queryList.append(" order by a.release_date desc ");
		      String sql = "{ call bet.sp_Page(?,?,?,?,?,?)}";
		      proc = conn.prepareCall(sql);
		      proc.setInt(1, pageSize);//每页数量
		      proc.setInt(2, startRow);//页码 
		      proc.setString(3, queryList.toString());//取数据的sql
		      proc.setString(4,querySize.toString());//取数据个数的sql
		      proc.registerOutParameter(5, OracleTypes.INTEGER);//输出数据行数
		      proc.registerOutParameter(6, oracle.jdbc.OracleTypes.CURSOR);//输出游标记录集
		      proc.execute();
		      int size = proc.getInt(5);
		      rs = (OracleResultSet)proc.getObject(6);
		      resultList=new ArrayList<Article>();
		      Article vo=null;
		      while (rs.next()) {
		    	 vo=new Article();
		         long articleId=rs.getLong("article_id");
		         vo.setArticleId(articleId);
		         String title=rs.getString("title");
		         vo.setTitle(title);
		         String releaseDate=rs.getString("release_date");
		         vo.setReleaseDate(releaseDate);
		         resultList.add(vo);
		      }
		      resultMap=new HashMap();
		      resultMap.put("totalCount", size);
		      resultMap.put("resultList", resultList);
		    }catch(Exception e){
		    	e.printStackTrace();
		    }finally{
		    	closeAll(rs,proc,conn);
		    }
		    return resultMap;
	}
	

}
