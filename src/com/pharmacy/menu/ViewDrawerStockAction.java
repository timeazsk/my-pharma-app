package com.pharmacy.menu;

import static com.pharmacy.util.Utils.readInputCancelable;

import com.pharmacy.database.Database;
import com.pharmacy.management.Drawer;
import com.training.sedinta09.multilanguage.MultilanguageImpl;;

public class ViewDrawerStockAction implements Action {

	@Override
	public void run() {
		try {
			String id = readInputCancelable(MultilanguageImpl.getInstance().getMessage("/inpID"));
			if (Database.getInstance().getDrawerByID(id.toUpperCase()) != null) {
				Drawer drawer = Database.getInstance().getDrawerByID(id.toUpperCase());
				drawer.viewStock();
				if (drawer.isEmpty()) {
					System.out.println(MultilanguageImpl.getInstance().getMessage("/emptyDrw"));
				}
			} else {
				System.out.println(MultilanguageImpl.getInstance().getMessage("/invalidID"));
			}
		} catch (InterruptedException e) {
			System.out.println(MultilanguageImpl.getInstance().getMessage("/actCancel"));
		}

	}

}
