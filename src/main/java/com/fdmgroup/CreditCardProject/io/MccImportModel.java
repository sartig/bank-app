package com.fdmgroup.CreditCardProject.io;

public class MccImportModel {
	private String id;
	private String description;
	private String category;

	public MccImportModel() {
		super();
	}

	public MccImportModel(String id, String description, String category) {
		this.description = description;
		this.category = category;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
