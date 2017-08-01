package com.wintv.lottery.admin.team.dao.impl;

import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.lottery.admin.team.dao.TeamRaceDao;
import com.wintv.framework.pojo.TeamRace;
@Repository("teamRaceDao")
public class TeamRaceDaoImpl  extends BaseHibernate<TeamRace,Long> implements TeamRaceDao{

}
