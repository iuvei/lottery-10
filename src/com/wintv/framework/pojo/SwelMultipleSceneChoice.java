package com.wintv.framework.pojo;

import java.io.Serializable;



public class SwelMultipleSceneChoice implements Serializable {

	// Fields

	private Long swelMultipleSceneChoiceId;
	private Long scene;
	private String race;
	private String detResult;
	private Long TSwelMultipleId;
	private String isDingdan;

	// Constructors

	/** default constructor */
	public SwelMultipleSceneChoice() {
	}

	/** full constructor */
	public SwelMultipleSceneChoice(Long scene, String race,
			String detResult, Long TSwelMultipleId, String isDingdan) {
		this.scene = scene;
		this.race = race;
		this.detResult = detResult;
		this.TSwelMultipleId = TSwelMultipleId;
		this.isDingdan = isDingdan;
	}

	// Property accessors

	public Long getSwelMultipleSceneChoiceId() {
		return this.swelMultipleSceneChoiceId;
	}

	public void setSwelMultipleSceneChoiceId(
			Long swelMultipleSceneChoiceId) {
		this.swelMultipleSceneChoiceId = swelMultipleSceneChoiceId;
	}

	public Long getScene() {
		return this.scene;
	}

	public void setScene(Long scene) {
		this.scene = scene;
	}

	public String getRace() {
		return this.race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getDetResult() {
		return this.detResult;
	}

	public void setDetResult(String detResult) {
		this.detResult = detResult;
	}

	public Long getTSwelMultipleId() {
		return this.TSwelMultipleId;
	}

	public void setTSwelMultipleId(Long TSwelMultipleId) {
		this.TSwelMultipleId = TSwelMultipleId;
	}

	public String getIsDingdan() {
		return this.isDingdan;
	}

	public void setIsDingdan(String isDingdan) {
		this.isDingdan = isDingdan;
	}

}