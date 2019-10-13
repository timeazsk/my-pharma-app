package com.pharmacy.util;

import com.training.sedinta09.multilanguage.MultilanguageImpl;

public enum MedicineType {
	DIV("/divisible"), INDIV("/indivisible");

	private String type;

	private MedicineType(String type) {
		this.type = MultilanguageImpl.getInstance().getMessage(type);
	}

	public String getType() {
		return type;
	}
}
