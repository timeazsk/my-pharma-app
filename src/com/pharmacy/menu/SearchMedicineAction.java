package com.pharmacy.menu;

import static com.pharmacy.util.Utils.readInputCancelable;

import com.pharmacy.database.Database;
import com.training.sedinta09.multilanguage.MultilanguageImpl;;

public class SearchMedicineAction implements Action {

	@Override
	public void run() {
		try {
			String search = readInputCancelable(MultilanguageImpl.getInstance().getMessage("/inpBarcodeOrBrand"));
			Database.getInstance().searchForMedicineInDatabase(search.toLowerCase());
		} catch (InterruptedException e) {
			System.out.println(MultilanguageImpl.getInstance().getMessage("/actCancel"));
		}
	}

}
