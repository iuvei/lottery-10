package com.wintv.framework.pojo;


/**
 * 足球单场对阵选择
 * @author Administrator
 *
 */
public class FbSingleChoice implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long choiceId;
	private BetOrder TTotalGoal;
	private Long scheduleId;

	 

	 
	public FbSingleChoice() {
	}

 
	public FbSingleChoice(BetOrder TTotalGoal, Long scheduleId) {
		this.TTotalGoal = TTotalGoal;
		this.scheduleId = scheduleId;
	}

 

	public Long getChoiceId() {
		return this.choiceId;
	}

	public void setChoiceId(Long choiceId) {
		this.choiceId = choiceId;
	}

	public BetOrder getTTotalGoal() {
		return this.TTotalGoal;
	}

	public void setTTotalGoal(BetOrder TTotalGoal) {
		this.TTotalGoal = TTotalGoal;
	}

	public Long getScheduleId() {
		return this.scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

}