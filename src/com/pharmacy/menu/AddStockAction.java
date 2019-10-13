package com.pharmacy.menu;

import static com.pharmacy.util.Utils.readInputCancelable;
import static com.pharmacy.util.Utils.readOption;
import static com.training.sedinta09.multilanguage.MultilanguageImpl.getInstance;

import com.pharmacy.database.Database;
import com.pharmacy.management.Drawer;
import com.pharmacy.management.Medicine;
import com.pharmacy.util.Option;

public class AddStockAction implements Action {

	@Override
	public void run() {
		while (true) {
			String barcode;
			try {
				barcode = readInputCancelable(getInstance().getMessage("/inpBarcode"));
				Medicine medicine = Database.getInstance().getMedicineByBarcode(barcode);
				if (Database.getInstance().medicineDoesNotExist(medicine)) {
					if (readOption(getInstance().getMessage("/medTypeDNExist"),
							new String[] { Option.YES.getOptionValue(), Option.NO.getOptionValue() })
									.equals(Option.YES.getOptionValue())) {
						new AddMedicineAction().run();
					} else {
						continue;
					}
				} else if (Database.getInstance().suggestStockLocation(medicine) != null) {
					System.out.println(getInstance().getMessage("/suggestLoc")
							+ Database.getInstance().suggestStockLocation(medicine).getDrawerID());
					if (readOption(getInstance().getMessage("/confirmLoc"),
							new String[] { Option.YES.getOptionValue(), Option.NO.getOptionValue() })
									.equals(Option.YES.getOptionValue())) {
						Drawer selectedDrawer = Database.getInstance().suggestStockLocation(medicine);
						selectedDrawer.addStockToDrawer(medicine);
						System.out.println(getInstance().getMessage("/confirmedLoc"));
					} else {
						continue;
					}
				} else {
					System.out.println(getInstance().getMessage("/noLoc"));
					continue;
				}
			} catch (InterruptedException e) {
				System.out.println(getInstance().getMessage("/actCancel"));
				return;
			}

		}

	}

}
