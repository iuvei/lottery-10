package com.wintv.framework.pojo;

import java.lang.Long;
import java.util.Date;
import java.io.Serializable;
/**
 * 四场进球赛场表
 * @author 王金阶
 * @since 1.0
 */
@SuppressWarnings("serial")
public class FourSceneGoalSchedule implements Serializable {

	

	private Long fsgsId;//赛程ID
	private String hostTeam;//主队
	private Date startTime;//开赛时间
	private String avgIndex;//平均指数
	private String guestTeam;//客队
	private String phase;//第几期
	private String hostGoal;//主队进球数，比赛结束后填写
	private String guestGoal;//客队进球数，比赛结束后填写
	
	public FourSceneGoalSchedule() {
	
	}
	public FourSceneGoalSchedule(Long fsgsId, String hostTeam, Date startTime,
			String avgIndex, String guestTeam, String phase, String hostGoal,
			String guestGoal) {
		
		this.fsgsId = fsgsId;
		this.hostTeam = hostTeam;
		this.startTime = startTime;
		this.avgIndex = avgIndex;
		this.guestTeam = guestTeam;
		this.phase = phase;
		this.hostGoal = hostGoal;
		this.guestGoal = guestGoal;
	}
	public Long getFsgsId() {
		return fsgsId;
	}
	public void setFsgsId(Long fsgsId) {
		this.fsgsId = fsgsId;
	}
	public String getHostTeam() {
		return hostTeam;
	}
	public void setHostTeam(String hostTeam) {
		this.hostTeam = hostTeam;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getAvgIndex() {
		return avgIndex;
	}
	public void setAvgIndex(String avgIndex) {
		this.avgIndex = avgIndex;
	}
	public String getGuestTeam() {
		return guestTeam;
	}
	public void setGuestTeam(String guestTeam) {
		this.guestTeam = guestTeam;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getHostGoal() {
		return hostGoal;
	}
	public void setHostGoal(String hostGoal) {
		this.hostGoal = hostGoal;
	}
	public String getGuestGoal() {
		return guestGoal;
	}
	public void setGuestGoal(String guestGoal) {
		this.guestGoal = guestGoal;
	}


}