package com.pharmacy.util;

public enum Sign {
	PLUS("+"), MINUS("-");

	private String sign;

	private Sign(String sign) {
		this.sign = sign;
	}

	public String getOptionValue() {
		return sign;
	}
}
