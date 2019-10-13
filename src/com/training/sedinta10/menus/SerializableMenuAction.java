package com.training.sedinta10.menus;

import com.pharmacy.database.Database;
import com.pharmacy.database.Serializer;
import com.pharmacy.menu.Action;

public class SerializableMenuAction extends MenuAction {

	public SerializableMenuAction(String option, String key, Action action) {
		super(option, key, action);
	}

	@Override
	public void doAction() {
		action.run();
		Serializer.save(Database.getInstance());
	}

}
