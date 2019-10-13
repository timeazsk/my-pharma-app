package com.pharmacy.management;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

	List<Stock> stock = new ArrayList<>();

	public Stock getMedicineStock(Medicine medicine) {
		for (Stock stoc : stock) {
			if (stoc.getMedicine().equals(medicine)) {
				return stoc;
			}
		}
		return null;
	}

	public boolean compareStocks(Drawer drawer) {
		if (drawer.compareStock(this.stock)) {
			return true;
		}
		return false;

		// for (Stock stoc : stock) {
		// if(drawer.getStock(stoc)!=null)
		// Stock drawerStock=drawer.getStock(stoc); // nu recunoaste "stoc" ?!?!?!?!
	}

	public void showStock() {
		for (Stock stoc : stock) {
			stoc.viewStock();
		}

	}

	public void addStock(Stock inventoryStock) {
		if (getMedicineStock(inventoryStock.getMedicine()) != null) {
			getMedicineStock(inventoryStock.getMedicine()).addToExistingStock(inventoryStock);
		} else {
			stock.add(inventoryStock);
		}

	}

	public boolean equals(Inventory inventory) {
		if (this.stock.size() == inventory.getStock().size()) {
			for (Stock stoc : stock) {
				Stock stock2 = inventory.getStock(stoc);
				if (stock2 != null) {
					if (stoc.compareTo(stock2)) {
						continue;
					}
				}
				return false;
			}
			return true;
		}
		return false;

	}

	private Stock getStock(Stock stoc2) {
		for (Stock stoc : stock) {
			if (stoc.getMedicine().equals(stoc2.getMedicine())) {
				return stoc;
			}
		}
		return null;
	}

	public List<Stock> getStock() {
		return this.stock;
	}

	public List<Stock> getArrangedStock() {
		List<Stock> newStock = new ArrayList<>();
		for (Stock stoc : stock) {
			newStock.add(stoc.createStockByQuantity(stoc.getMedicineQuantity()));
		}
		return newStock;
	}

	public void setStock(List<Stock> arrangedStock) {
		this.stock.clear();
		this.stock = arrangedStock;

	}

	public int getStocksize() {
		return this.stock.size();
	}
}
