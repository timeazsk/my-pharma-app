package com.pharmacy.menu;

import com.training.sedinta09.multilanguage.Language;
import com.training.sedinta09.multilanguage.MultilanguageImpl;
import com.training.sedinta10.menus.Menu;
import com.training.sedinta10.menus.MenuAction;
import com.training.sedinta10.menus.MenuItem;
import com.training.sedinta10.menus.SerializableMenuAction;

public class MainAppMenu {

	public void showMenu() {
		Menu mainMenu = new Menu("", "");
		Menu back = new Menu("0", "/back");

		// 1.addMed------------------------------------------------------------------------------

		MenuAction addMed = new SerializableMenuAction("1", "/addmed", new AddMedicineAction());

		// 2.addStock---------------------------------------------------------------------------

		MenuAction addStock = new SerializableMenuAction("2", "/addstoc", new AddStockAction());

		// 3.search--------------------------------------------------------------

		MenuAction search = new MenuAction("3", "/searchMed", new SearchMedicineAction());

		// 4.sell------------------------------------------------------------------

		MenuAction sell = new SerializableMenuAction("4", "/sell", new SellMedicineAction());

		// 5.inventory--------------------------------------------------------------

		MenuAction inventory = new SerializableMenuAction("5", "/inventory", new InventoryAction());

		// 6.orderstock-------------------------------------------------------

		MenuAction orderStock = new SerializableMenuAction("6", "/order", new OrderStockAction());

		// 7.stockhistory------------------------------------------------------

		MenuAction stockHistory = new MenuAction("7", "/history", new ViewStockAdjustmentsAction());

		// 8.viewstock----------------------------------------------------------

		Menu viewStock = new Menu("8", "/view");

		MenuAction complete = new MenuAction("1", "/complete", new ViewCompleteStockAction());

		MenuAction drawer = new MenuAction("2", "/drawer", new ViewDrawerStockAction());

		viewStock.addSubMenu(complete);
		viewStock.addSubMenu(drawer);
		viewStock.addSubMenu(back);
		viewStock.setBackAction(back);

		// 9.settings--------------------------------------------------------

		Menu settings = new Menu("9", "/settings");

		Menu changeLang = new Menu("1", "/chlang");
		settings.addSubMenu(changeLang);
		settings.addSubMenu(back);
		settings.setBackAction(back);

		MenuItem en = new MenuItem("1", "/en") {
			@Override
			public void doAction() {
				MultilanguageImpl.getInstance().setLanguage(Language.EN);
			}
		};

		MenuItem ro = new MenuItem("2", "/ro") {
			@Override
			public void doAction() {
				MultilanguageImpl.getInstance().setLanguage(Language.RO);
			}
		};

		changeLang.addSubMenu(en);
		changeLang.addSubMenu(ro);
		changeLang.addSubMenu(back);
		changeLang.setBackAction(back);

		// ---------------------------------------------------------------------
		Menu exit = new Menu("0", "/exit");

		mainMenu.addSubMenu(addMed);
		mainMenu.addSubMenu(addStock);
		mainMenu.addSubMenu(search);
		mainMenu.addSubMenu(sell);
		mainMenu.addSubMenu(inventory);
		mainMenu.addSubMenu(orderStock);
		mainMenu.addSubMenu(stockHistory);
		mainMenu.addSubMenu(viewStock);
		mainMenu.addSubMenu(settings);
		mainMenu.addSubMenu(exit);
		mainMenu.setBackAction(exit);

		mainMenu.doAction();
	}

}
