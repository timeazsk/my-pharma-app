package com.pharmacy.util;

import com.training.sedinta09.multilanguage.MultilanguageImpl;

public enum Option {
	YES("/yes"), NO("/no");
	private String option;

	private Option(String option) {
		this.option = MultilanguageImpl.getInstance().getMessage(option);
	}

	public String getOptionValue() {
		return option;
	}
}
