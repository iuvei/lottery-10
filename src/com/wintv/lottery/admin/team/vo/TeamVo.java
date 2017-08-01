package com.wintv.lottery.admin.team.vo;

import java.math.BigDecimal;
//import java.util.Comparator;

public class TeamVo implements Comparable<TeamVo>{
	private String hostName;
	private String receName;
	private String raceSeasonName;			//赛季名
	private BigDecimal showing;         		//场次
	private String teamType;               //球队类型
	
	private BigDecimal winShowing;				//胜
	private BigDecimal equalShowing;            //平
	private BigDecimal lostShowing;				//负
	
	private BigDecimal inGoals;                 //进球
	private String inGoals_avg;				//平均进球
	private BigDecimal lostGoals;				//失球
	private String lostGoals_avg;			//平均失球
	
	private BigDecimal winShowingRate;			//胜率
	private BigDecimal equalShowingRate;        //平率
	private BigDecimal lostShowingRate;			//负率
	
	private BigDecimal team_gd;               //净胜球
	private BigDecimal teamScores;					//积分
    private Integer ranking ;               //排名
    private static final BigDecimal zero = new BigDecimal(0);
    
	//排序
	public int compareTo(TeamVo teamVo) {
		if(! this.getTeamScores().subtract(teamVo.getTeamScores()).equals(zero))
			return this.getTeamScores().subtract(teamVo.getTeamScores()).intValue();
		if(! this.getTeam_gd().subtract(teamVo.getTeam_gd()).equals(zero))
			return this.getTeam_gd().subtract(teamVo.getTeam_gd()).intValue();
		return this.getInGoals().subtract(teamVo.getInGoals()).intValue();
		
	}
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getReceName() {
		return receName;
	}
	public void setReceName(String receName) {
		this.receName = receName;
	}
	public String getRaceSeasonName() {
		return raceSeasonName;
	}
	public void setRaceSeasonName(String raceSeasonName) {
		this.raceSeasonName = raceSeasonName;
	}
	public BigDecimal getShowing() {
		return showing;
	}
	public void setShowing(BigDecimal showing) {
		this.showing = showing;
	}
	public String getTeamType() {
		return teamType;
	}
	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}
	public BigDecimal getWinShowing() {
		return winShowing;
	}
	public void setWinShowing(BigDecimal winShowing) {
		this.winShowing = winShowing;
	}
	public BigDecimal getEqualShowing() {
		return equalShowing;
	}
	public void setEqualShowing(BigDecimal equalShowing) {
		this.equalShowing = equalShowing;
	}
	public BigDecimal getLostShowing() {
		return lostShowing;
	}
	public void setLostShowing(BigDecimal lostShowing) {
		this.lostShowing = lostShowing;
	}
	public BigDecimal getInGoals() {
		return inGoals;
	}
	public void setInGoals(BigDecimal inGoals) {
		this.inGoals = inGoals;
	}
	public String getInGoals_avg() {
		return inGoals_avg;
	}
	public void setInGoals_avg(String inGoals_avg) {
		this.inGoals_avg = inGoals_avg;
	}
	public BigDecimal getLostGoals() {
		return lostGoals;
	}
	public void setLostGoals(BigDecimal lostGoals) {
		this.lostGoals = lostGoals;
	}
	public String getLostGoals_avg() {
		return lostGoals_avg;
	}
	public void setLostGoals_avg(String lostGoals_avg) {
		this.lostGoals_avg = lostGoals_avg;
	}
	
	public BigDecimal getWinShowingRate() {
		return winShowingRate;
	}

	public void setWinShowingRate(BigDecimal winShowingRate) {
		this.winShowingRate = winShowingRate;
	}

	public BigDecimal getEqualShowingRate() {
		return equalShowingRate;
	}

	public void setEqualShowingRate(BigDecimal equalShowingRate) {
		this.equalShowingRate = equalShowingRate;
	}

	public BigDecimal getLostShowingRate() {
		return lostShowingRate;
	}

	public void setLostShowingRate(BigDecimal lostShowingRate) {
		this.lostShowingRate = lostShowingRate;
	}

	public BigDecimal getTeam_gd() {
		return team_gd;
	}
	public void setTeam_gd(BigDecimal team_gd) {
		this.team_gd = team_gd;
	}
	public BigDecimal getTeamScores() {
		return teamScores;
	}
	public void setTeamScores(BigDecimal teamScores) {
		this.teamScores = teamScores;
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	
	/*public static class WinShowingRateComparator implements Comparator<TeamVo>{
		@Override
		public int compare(TeamVo v0, TeamVo v1) {
			Double d0 = Double.valueOf(v0.getWinShowingRate().substring(0, v0.getWinShowingRate().length()-1));
			Double d1 = Double.valueOf(v1.getWinShowingRate().substring(0, v1.getWinShowingRate().length()-1));
			return d0 > d1 ? 1 : (d0 ==d1 ? 1:-1); 
		}
	}*/
}



