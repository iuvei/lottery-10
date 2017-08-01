package com.wintv.framework.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.ecside.table.limit.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.dao.DistrictDao;
import com.wintv.framework.page.Page;
import com.wintv.framework.pojo.District;
import com.wintv.lottery.admin.team.vo.DistrictVo;

@Repository("districtDao")
public class DistrictDaoImpl extends BaseHibernate<District,Long> implements DistrictDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Page findAllDistrict(Limit limit,Map<String,String> param) {
		String sql;
		if(null == param)
			sql = "select d from District d where d.parentId = 0 ";
		else
			sql = "select d from District d where d.parentId =  " + param.get("district_id");
		List<String> arg = new ArrayList<String>();
		return pagedQuery(sql,arg.toArray(),limit.getPage(),limit.getCurrentRowsDisplayed());
	}

	@Override
	public List<DistrictVo> findAllDistrictCount() {
		StringBuilder sb = new StringBuilder();
		sb.append(" select dis.id,dis.name,tt.count_dis from t_district dis ,  " +
				" (select count(*) as count_dis,t.parent_id  from t_district t " +
				" where t.parent_id<>0  group by t.parent_id) tt " +
				" where dis.parent_id =0 and dis.id = tt.parent_id(+) ");
		final List<DistrictVo> list_r = new ArrayList<DistrictVo>();
		List<String> list = new ArrayList<String>();
		jdbcTemplate.query(sb.toString(), list.toArray(), new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				DistrictVo d = new DistrictVo();
				d.setId(rs.getLong("id"));
				d.setName(rs.getString("name"));
				d.setCount(rs.getLong("count_dis"));
				list_r.add(d);
			}
		});
		list = null;
		sb = null;
		return list_r;
	}

	@Override
	public District findUniqueDistrict(Map<String, String> param) {
		StringBuilder sql = new StringBuilder("");
		if(StringUtils.isBlank(param.get("recordKey"))){
			sql.append("select d from District d where d.name = ? and d.parentId = 0 ");
			return (District) getSession().createQuery(sql.toString())
				.setString(0, param.get("name")).uniqueResult();
		}
		sql.append("select d from District d where d.name = ? and d.parentId = ? ");
		return (District) getSession().createQuery(sql.toString())
			.setString(0, param.get("name"))
			.setLong(1, Long.parseLong(param.get("recordKey"))).uniqueResult();
	}
	
	
}
