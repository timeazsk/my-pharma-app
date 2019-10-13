package com.pharmacy.management;

import java.io.Serializable;

import com.pharmacy.database.Database;
import com.training.sedinta09.multilanguage.MultilanguageImpl;

public class MedicineIndiv extends Medicine implements Serializable {

	private static final long serialVersionUID = 1L;

	public MedicineIndiv(String barcode, String brand, String details, String type, int boxLength, int boxWidth,
			int boxHeight) {
		super(barcode, brand, details, type, boxLength, boxWidth, boxHeight);
	}

	@Override
	public Stock addStock(int quantity) {
		return new StockIndiv(this, quantity);
	}

	@Override
	public String toString() {
		return "MedicineIndiv: " + super.toString();
	}

	@Override
	public String getMessage() {
		return MultilanguageImpl.getInstance().getMessage("/box");
	}

	@Override
	public int getSubDiv() {
		return 0;
	}

	@Override
	public int getOrderQuantity(int quantityForNextDays) {
		return quantityForNextDays - Database.getInstance().getTotalMedicineQuantity(this);
	}

	@Override
	public Stock addInventoryStock() {
		return new StockIndiv(this, 1);
	}

}
