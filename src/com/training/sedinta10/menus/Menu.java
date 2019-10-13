package com.training.sedinta10.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.training.sedinta09.multilanguage.MultilanguageImpl;

public class Menu extends MenuItem {
	public Menu(String option, String text) {
		super(option, text);
	}

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	ArrayList<MenuItem> items = new ArrayList<>();
	MenuItem backAction;

	private String readString() {
		try {
			return br.readLine();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public void doAction() {
		while (true) {
			showMenu();
			String option = readString();
			MenuItem item = getMenuItemByOption(option);

			if (item == null) {
				System.out.println(MultilanguageImpl.getInstance().getMessage("/invalidoption"));
				continue;
			}

			if (item == backAction) {
				return;
			}
			item.doAction();
		}
	}

	private MenuItem getMenuItemByOption(String option) {
		for (MenuItem item : items) {
			if (item.option.equals(option)) {
				return item;
			}
		}
		return null;
	}

	private void showMenu() {
		for (MenuItem item : items) {
			System.out.println(item.toString());
		}
		System.out.print(MultilanguageImpl.getInstance().getMessage("/option"));
	}

	public void addSubMenu(MenuItem item) {
		items.add(item);
	}

	public void setBackAction(MenuItem item) {
		this.backAction = item;
	}
}
