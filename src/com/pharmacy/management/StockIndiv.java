package com.pharmacy.management;

import java.io.Serializable;

import com.training.sedinta09.multilanguage.MultilanguageImpl;

public class StockIndiv extends Stock implements Serializable {

	private int boxQuantity;

	public StockIndiv(Medicine medicine, int quantity) {
		super(medicine);
		this.boxQuantity = quantity;

	}

	private static final long serialVersionUID = 1L;

	@Override
	public int getStockVolume() {
		return boxQuantity * medicine.getVolume();
	}

	@Override
	public void addToExistingStock(Medicine medicine) {
		this.boxQuantity++;
	}

	@Override
	public void viewStock() {
		System.out.println(MultilanguageImpl.getInstance().getMessage("/stock") + ": " + medicine);
		System.out.println(boxQuantity + " " + MultilanguageImpl.getInstance().getMessage("/boxes"));

	}

	@Override
	public int getMedicineQuantity() {
		return boxQuantity;
	}

	@Override
	public Stock suggestStockToRemove(int quantity) {
		if (quantity >= this.boxQuantity) {
			return new StockIndiv(medicine, this.boxQuantity);
		} else {
			return new StockIndiv(medicine, quantity);
		}
	}

	@Override
	public void removeQuantity(int quantity) {
		this.boxQuantity -= quantity;

	}

	@Override
	public void addToExistingStock(Stock inventoryStock) {
		this.boxQuantity++;
		// this.boxQuantity += inventoryStock.getMedicineQuantity();

	}

	@Override
	public boolean compareTo(Stock dStoc) {
		return (this.boxQuantity == dStoc.getMedicineQuantity()) ? true : false;
	}

	@Override
	public Stock createStockByQuantity(int quantity) {
		return new StockIndiv(medicine, quantity);
	}
}
