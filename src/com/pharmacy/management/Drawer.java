package com.pharmacy.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pharmacy.configuration.Configuration;
import com.pharmacy.database.Database;
import com.pharmacy.database.Serializer;

public class Drawer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String line;
	private String column;
	private List<Stock> stock = new ArrayList<Stock>();

	public Drawer(String line, String column) {
		super();
		this.line = line;
		this.column = column;

	}

	public Drawer(Drawer drawer) {
		this.line = drawer.getLine();
		this.column = drawer.getColumn();
		this.stock = drawer.getStock();
	}

	public String getLine() {
		return line;
	}

	public String getColumn() {
		return column;
	}

	public List<Stock> getStock() {
		return stock;
	}

	@Override
	public String toString() {
		return "Drawer [line=" + line + ", column=" + column + ", stock=" + stock + "]";
	}

	public String getDrawerID() {
		return this.line + this.column;
	}

	public boolean hasBrand(Medicine medicine) {
		for (Stock stoc : stock) {
			if (stoc.getMedicine().getBrand().contains(medicine.getBrand())) {
				return true;
			}
		}
		return false;
	}

	public boolean hasEnoughSpace(Medicine medicine) {
		int availableVolume = Configuration.getInstance().getMacOccupancyVolume();
		for (Stock stoc : stock) {
			availableVolume -= stoc.getStockVolume();
		}
		if (availableVolume >= medicine.getVolume()) {
			return true;
		}

		return false;
	}

	public boolean isEmpty() {
		return stock.isEmpty();
	}

	public int countBrands() {
		return stock.size();
	}

	public void addStockToDrawer(Medicine medicine) {
		if (getMedicineStock(medicine) != null) {
			getMedicineStock(medicine).addToExistingStock(medicine); // adaugi o cutie de tipul medicamentului la stoc
		} else {
			stock.add(medicine.addStock(1)); // adaugi un stoc nou la stoculsertarului
		}

	}

	public Stock getMedicineStock(Medicine medicine) {
		for (Stock stoc : stock) {
			if (stoc.getMedicine().equals(medicine)) {
				return stoc;
			}
		}
		return null;
	}

	public void viewStock() {
		for (Stock stoc : stock) {
			stoc.viewStock();
		}

	}

	public Stock getStockToRemoveByQuantity(Medicine medicine, int quantity) {
		Stock drawerStock = null;
		for (Stock stoc : stock) {
			if (stoc.getMedicine().equals(medicine)) {
				drawerStock = stoc.suggestStockToRemove(quantity);
			}
		}
		return drawerStock;
	}

	public void removeStockByQuantity(Stock stock2, int quantity) {
		for (Stock stoc : stock) {
			if (stoc.getMedicine().equals(stock2.getMedicine())) {
				stoc.removeQuantity(quantity);
				if (stoc.getMedicineQuantity() == 0) {
					stock.remove(stoc);
					return;
				}
			}
		}
	}

	public int getLongestMedicineNameLength() {
		int length = 0;
		for (Stock stoc : stock) {
			if (stoc.getMedicine().getBrand().length() > length) {
				length = stoc.getMedicine().getBrand().length();
			}
		}
		return length;
	}

	public int getStockQuantity() {
		return stock.size();
	}

	public Stock getStockByIndex(int index) {
		if (stock.size() == 0) {
			return null;
		} else if (stock.size() < index + 1) {
			return stock.get(index - 1);
		} else if (stock.size() == index + 1) {
			return stock.get(index);
		} else if (stock.size() > index + 1) {
			return stock.get(index);
		}
		return null;
	}

	public int getStockSize() {
		return stock.size();
	}

	public Stock getStock(Stock stoc2) {
		for (Stock stoc : stock) {
			if (stoc.getMedicine().equals(stoc2.getMedicine())) {
				return stoc;
			}
		}
		return null;
	}

	public boolean compareStock(List<Stock> inventoryStock) {
		if (this.stock.size() == inventoryStock.size()) {
			for (Stock inventStoc : inventoryStock) {
				Stock drawerStock = getStock(inventStoc);
				if (drawerStock != null) {
					if (inventStoc.compareTo(drawerStock)) {
						continue;
					}
				}
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public void setStock(List<Stock> newStock) {
		this.stock.clear();
		this.stock = newStock;
		Serializer.save(Database.getInstance());
	}

}
