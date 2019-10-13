package com.training.sedinta10.menus;

import com.pharmacy.menu.Action;

public class MenuAction extends MenuItem {

	protected Action action;

	public MenuAction(String option, String key, Action action) {
		super(option, key);
		this.action = action;
	}

	@Override
	public void doAction() {
		action.run();
	}

}
