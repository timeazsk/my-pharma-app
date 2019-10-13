package com.pharmacy.management;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Stock implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Medicine medicine;

	public Stock(Medicine medicine) {
		this.medicine = medicine;
	}

	public Medicine getMedicine() {
		return this.medicine;
	}

	public abstract int getStockVolume();
	// return boxes.size() * medicine.getVolume();

	public abstract void addToExistingStock(Medicine medicine);
	// boxes.add(medicine.addToExistingStock());

	@Override
	public String toString() {
		return "Stock: " + medicine;
	}

	public abstract void viewStock();

	public abstract int getMedicineQuantity();

	public abstract Stock suggestStockToRemove(int quantity);

	public void addBox(int quantity, int subDivQuant) {

	}

	public ArrayList<Box> getBoxes() {
		return null;
	}

	public Stock getStockByQuantity(int actualQuantity) {
		return null;
	}

	public abstract void removeQuantity(int quantity);

	public abstract void addToExistingStock(Stock inventoryStock);

	public abstract boolean compareTo(Stock dStoc);

	public boolean hasBox(Box box) {
		return false;
	}

	public abstract Stock createStockByQuantity(int quantity);

}