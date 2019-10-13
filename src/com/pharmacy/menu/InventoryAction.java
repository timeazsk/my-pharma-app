package com.pharmacy.menu;

import static com.training.sedinta09.multilanguage.MultilanguageImpl.getInstance;

import java.util.List;

import com.pharmacy.database.Database;
import com.pharmacy.management.Drawer;
import com.pharmacy.management.Inventory;
import com.pharmacy.management.Medicine;
import com.pharmacy.management.Stock;
import com.pharmacy.util.Option;
import com.pharmacy.util.Utils;;

public class InventoryAction implements Action {

	@Override
	public void run() {
		String id;
		try {
			id = Utils.readInputCancelable(getInstance().getMessage("/inpID"));
			if (Database.getInstance().getDrawerByID(id.toUpperCase()) != null) {
				Drawer drawer = Database.getInstance().getDrawerByID(id.toUpperCase());
				Inventory firstInventory = createInventory();
				if (firstInventory.compareStocks(drawer)) {
					System.out.println(getInstance().getMessage("/goodInventory"));
				} else {
					System.out.println(getInstance().getMessage("/redoInventory"));
					Inventory secondInventory = createInventory();
					if (firstInventory.equals(secondInventory)) {
						System.out.println(getInstance().getMessage("/sameQuantities"));
						String option = Utils.readOption(getInstance().getMessage("/updateStock"),
								new String[] { Option.YES.getOptionValue(), Option.NO.getOptionValue() });
						if (option.equals(Option.YES.getOptionValue())) {
							Database.getInstance().saveStockAdjustments(drawer, firstInventory);
							List<Stock> newStock = firstInventory.getArrangedStock();
							drawer.setStock(newStock);
						} else {
							return;
						}
					} else {
						System.out.println(getInstance().getMessage("/diffQuantities"));
						new InventoryAction();
					}
				}
			} else {
				System.out.println(getInstance().getMessage("/invalidID"));
			}
		} catch (InterruptedException e) {
			System.out.println(getInstance().getMessage("/actCancel"));
		}

	}

	private Inventory createInventory() {
		String barcode;
		Inventory inventory = new Inventory();
		try {
			while (true) {
				barcode = Utils.readInputCancelable(getInstance().getMessage("/inpBarcodeOrEnterForEmptyDrw"));
				Medicine medicine = Database.getInstance().getMedicineByBarcode(barcode);
				Stock inventoryStock = medicine.addInventoryStock();
				inventory.addStock(inventoryStock);
			}
		} catch (InterruptedException e) {
		}
		System.out.println(getInstance().getMessage("/scannedInventory"));
		inventory.showStock();
		return inventory;

	}

}
