package com.training.sedinta10.menus;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.training.sedinta09.multilanguage.MultilanguageImpl;

public abstract class MenuItem {
	String option;
	String key;

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public MenuItem(String option, String key) {
		super();
		this.option = option;
		this.key = key;
	}

	public MenuItem(String option) {
		super();
		this.option = option;
	}

	public abstract void doAction();

	@Override
	public String toString() {
		return option + ". " + MultilanguageImpl.getInstance().getMessage(key);
	}

}
