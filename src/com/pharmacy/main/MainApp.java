package com.pharmacy.main;

import static com.training.sedinta09.multilanguage.MultilanguageImpl.getInstance;

import com.pharmacy.database.Database;
import com.pharmacy.menu.MainAppMenu;
import com.pharmacy.util.Option;
import com.pharmacy.util.Utils;
import com.training.sedinta09.multilanguage.MultilanguageImpl;

public class MainApp {

	MainAppMenu mainAppMenu = new MainAppMenu();

	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		try {
			mainApp.init();
			mainApp.run();
		} catch (InterruptedException e) {
			System.out.println(MultilanguageImpl.getInstance().getMessage("/actCancel"));
		}

	}

	private void run() {
		mainAppMenu.showMenu();
	}

	private void init() throws InterruptedException {
		if (Database.getInstance().dbDoesNotExist()) {
			String option = Utils.readOption(getInstance().getMessage("/newDb"),
					new String[] { Option.YES.getOptionValue(), Option.NO.getOptionValue() });
			if (Option.YES.getOptionValue().equals(option)) {
				Database.getInstance().createEmptyDrawers();
			} else {
				throw new InterruptedException();

			}
		}
	}
}
