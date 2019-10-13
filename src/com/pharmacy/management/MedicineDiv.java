package com.pharmacy.management;

import java.io.Serializable;

import com.pharmacy.database.Database;
import com.pharmacy.util.Utils;
import com.training.sedinta09.multilanguage.MultilanguageImpl;

public class MedicineDiv extends Medicine implements Serializable {

	private static final long serialVersionUID = 1L;
	private int subDiv;

	public MedicineDiv(String barcode, String brand, String details, String type, int boxLength, int boxWidth,
			int boxHeight, int subDiv) {
		super(barcode, brand, details, type, boxLength, boxWidth, boxHeight);
		this.subDiv = subDiv;
	}

	@Override
	public Stock addStock(int quantity) {
		return new StockDiv(this, new Box(quantity, subDiv));
	}

	@Override
	public String toString() {
		return "MedicineDiv: " + super.toString() + " SubDiv" + subDiv;
	}

	@Override
	public String getMessage() {
		return MultilanguageImpl.getInstance().getMessage("/div");
	}

	@Override
	public int getSubDiv() {
		return this.subDiv;
	}

	@Override
	public int getOrderQuantity(int quantityForNextDays) {
		if (quantityForNextDays > Database.getInstance().getTotalMedicineQuantity(this)) {
			int orderQuantity = (quantityForNextDays - Database.getInstance().getTotalMedicineQuantity(this));
			if (orderQuantity % this.getSubDiv() != 0) {
				return orderQuantity / this.getSubDiv() + 1;
			} else {
				return orderQuantity / this.getSubDiv();
			}
		}
		return 0;
	}

	@Override
	public Stock addInventoryStock() {
		int subDivQuantity = Utils
				.getIntRepresentation((Utils.readInput("Introduceti numarul subdiviziunilor din cutie: ")));
		return new StockDiv(this, new Box(1, subDivQuantity));
	}

}
