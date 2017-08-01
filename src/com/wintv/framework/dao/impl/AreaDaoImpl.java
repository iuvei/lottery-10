package com.wintv.framework.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.dao.AreaDao;
import com.wintv.framework.pojo.Code;
import com.wintv.lottery.area.vo.AreaVo;

@Repository("areaDao")
@SuppressWarnings("unchecked")
public class AreaDaoImpl  extends BaseHibernate<Code,Long> implements AreaDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public List<Code> findAllProvince() {
		// TODO Auto-generated method stub
		return  this.getSession().createQuery("from Code c where c.parentId = 1 order by c.id  ").list();
	}

	@Override
	public Code findCityById(Long cityId) {
		// TODO Auto-generated method stub
		return (Code) this.getSession().createQuery("from Code c where c.id = ? ").setLong(0, cityId).uniqueResult();
	}

	@Override
	public List<Code> findCityByProvID(Long provId) {
		return this.getSession().createQuery("from Code c where c.parentId = ? ").setLong(0, provId).list();
	}

	@Override
	public Code findProvinceById(Long provId) {
		// TODO Auto-generated method stub
		return (Code) this.getSession().createQuery("from Code c where c.id = ? ").setLong(0, provId).uniqueResult();
	}
	/**
	 * 根据父级code获取该父级所有下属地区列表
	 * 
	 * @author hikin yao
	 */
	@Override
	public List<AreaVo> getAreaByParentCode(String parentCode){
		StringBuilder sqlStr = new StringBuilder();
		sqlStr.append(" select t.id,t.name from t_code t where t.parent_id="+parentCode);
		sqlStr.append(" order by t.id asc");
		final List resultList = new ArrayList();
		jdbcTemplate.query(sqlStr.toString(), resultList.toArray(),
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						AreaVo AreaVo = new AreaVo();
						AreaVo.setAreaCode(rs.getString("id"));
						AreaVo.setAreaName(rs.getString("name"));
						resultList.add(AreaVo);
					}
		});
		return resultList;
	}
}
