package com.pharmacy.management;

import java.io.Serializable;

public abstract class Medicine implements Serializable {
	private static final long serialVersionUID = 1L;
	private String barcode;
	private String brand;
	private String details;
	private String type;
	private int boxLength;
	private int boxWidth;
	private int boxHeight;

	public Medicine(String barcode, String brand, String details, String type, int boxLength, int boxWidth,
			int boxHeight) {
		super();
		this.barcode = barcode;
		this.brand = brand;
		this.details = details;
		this.type = type;
		this.boxLength = boxLength;
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
	}

	public abstract Stock addStock(int quantity);

	public boolean hasBarcode(String barcode2) {
		return barcode2.equals(this.barcode);
	}

	public String getBrand() {
		return this.brand;
	}

	public int getVolume() {
		return boxLength * boxWidth * boxHeight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medicine other = (Medicine) obj;
		if (barcode == null) {
			if (other.barcode != null)
				return false;
		} else if (!barcode.equals(other.barcode))
			return false;
		return true;
	}

	public String getBarcode() {
		return this.barcode;
	}

	@Override
	public String toString() {
		return " Barcode:" + barcode + ", Brand:" + brand + ", Details:" + details + ", Type:" + type + " BoxLength:"
				+ boxLength + ", BoxWidth:" + boxWidth + ", BoxHeight:" + boxHeight;
	}

	public String getType() {
		return this.type;
	}

	public abstract String getMessage();

	public abstract int getSubDiv();

	public abstract int getOrderQuantity(int quantityForNextDays);

	public abstract Stock addInventoryStock();

}
