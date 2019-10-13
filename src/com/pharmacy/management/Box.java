package com.pharmacy.management;

import java.io.Serializable;

public class Box implements Serializable {

	private static final long serialVersionUID = 1L;

	private int quantity;
	private int subDivQuant;

	public Box(int quantity, int subDivQuant) {
		this.quantity = quantity;
		this.subDivQuant = subDivQuant;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getSubDivQuant() {
		return subDivQuant;
	}

	public void addQuantity() {
		this.quantity++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + quantity;
		result = prime * result + subDivQuant;
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
		Box other = (Box) obj;
		if (quantity != other.quantity)
			return false;
		if (subDivQuant != other.subDivQuant)
			return false;
		return true;
	}

	public void setSubDivQuantity(int i) {
		this.subDivQuant = i;
	}

	public void setQuantity(int i) {
		this.quantity = i;
	}

}
