package com.pharmacy.menu;

import static com.pharmacy.util.Utils.getIntRepresentation;
import static com.pharmacy.util.Utils.readInputCancelable;
import static com.pharmacy.util.Utils.readOption;
import static com.training.sedinta09.multilanguage.MultilanguageImpl.getInstance;

import java.util.ArrayList;
import java.util.HashMap;

import com.pharmacy.database.Database;
import com.pharmacy.database.Serializer;
import com.pharmacy.management.Drawer;
import com.pharmacy.management.Medicine;
import com.pharmacy.management.Stock;
import com.pharmacy.util.Option;;

public class SellMedicineAction implements Action {

	@Override
	public void run() {
		try {
			String brand = readInputCancelable(getInstance().getMessage("/enterBrand"));
			ArrayList<Medicine> medicineOptions = Database.getInstance().suggestMedicine(brand);
			for (Medicine med : medicineOptions) {
				System.out.println((medicineOptions.indexOf(med) + 1) + ". " + med.toString());
			}
			String option = readInputCancelable(getInstance().getMessage("/chooseMed"));
			Medicine medicine = medicineOptions.get(getIntRepresentation(option) - 1);
			sellMedicine(medicine);
		} catch (InterruptedException e) {
			System.out.println(getInstance().getMessage("/actCancel"));
		}
	}

	private void sellMedicine(Medicine medicine) {
		try {
			int quantity = getIntRepresentation(readInputCancelable(
					getInstance().getMessage("/enterNumber") + medicine.getMessage() + getInstance().getMessage("/quit")));

			if (Database.getInstance().getTotalMedicineQuantity(medicine) >= quantity) {
				HashMap<Drawer, Stock> drawerAndQuantity = getDrawersWithStock(medicine, quantity);
				for (Drawer drawer : drawerAndQuantity.keySet()) {
					if (drawerAndQuantity.get(drawer).getMedicineQuantity() != 0) {
						System.out.print(drawer.getDrawerID() + ": ");
						drawerAndQuantity.get(drawer).viewStock();
					}
				}
				String option2 = readOption(getInstance().getMessage("/cofirmSale"),
						new String[] { Option.YES.getOptionValue(), Option.NO.getOptionValue() });
				if (option2.equals(Option.YES.getOptionValue())) {
					for (Drawer drawer : drawerAndQuantity.keySet()) {
						Database.getInstance().recordSelling(medicine, drawerAndQuantity.get(drawer).getMedicineQuantity());
						if (drawerAndQuantity.get(drawer).getMedicineQuantity() != 0) {
							Database.getInstance().removeStock(drawer, drawerAndQuantity.get(drawer),
									drawerAndQuantity.get(drawer).getMedicineQuantity());
						}
						Serializer.save(Database.getInstance());
					}
				} else if (option2.equals(Option.NO.getOptionValue())) {
					return;
				}
			} else {
				System.out.println(getInstance().getMessage("/noStock"));
				return;
			}
		} catch (

		InterruptedException e) {
			System.out.println(getInstance().getMessage("/actCancel"));
		}
	}

	private HashMap<Drawer, Stock> getDrawersWithStock(Medicine medicine, int quantity) {
		HashMap<Drawer, Stock> drawersWithStocks = new HashMap<>();
		for (Drawer drawer : Database.getInstance().getMedicineLocations(medicine)) {
			Stock drawerStock = drawer.getStockToRemoveByQuantity(medicine, quantity);
			if (drawerStock != null) {
				drawersWithStocks.put(drawer, drawerStock);
				quantity -= drawerStock.getMedicineQuantity();
			}
		}
		return drawersWithStocks;

	}

}
