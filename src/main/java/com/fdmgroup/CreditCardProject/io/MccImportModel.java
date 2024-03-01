package com.fdmgroup.CreditCardProject.io;

public class MccImportModel {
	private String id;
	private String description;

	public MccImportModel() {
		super();
	}

	public MccImportModel(String id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
