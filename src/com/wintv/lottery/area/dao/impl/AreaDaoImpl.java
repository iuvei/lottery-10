package com.wintv.lottery.area.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.lottery.area.dao.AreaDao;
import com.wintv.lottery.area.vo.AreaVo;
//@Repository("areaDao")
@SuppressWarnings("unchecked")
public class AreaDaoImpl extends BaseHibernate<Object, Long>  implements AreaDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	//根据父级code获取该父级所有下属地区列表
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
