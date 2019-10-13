package com.pharmacy.menu;

import com.pharmacy.database.Database;

public class ViewStockAdjustmentsAction implements Action {

	@Override
	public void run() {
		Database.getInstance().viewStockAdjustments();
	}

}
