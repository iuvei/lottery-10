package com.wintv.framework.pojo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ScoreChoice implements Serializable {
    
	private Long choiceId;
	private Score score;
	private Long scheduleId;
	public Long getChoiceId() {
		return choiceId;
	}
	public void setChoiceId(Long choiceId) {
		this.choiceId = choiceId;
	}
	public Score getScore() {
		return score;
	}
	public void setScore(Score score) {
		this.score = score;
	}
	public Long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}


}