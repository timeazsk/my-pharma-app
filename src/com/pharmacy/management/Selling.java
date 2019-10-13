package com.pharmacy.management;

import java.io.Serializable;

import com.pharmacy.configuration.Configuration;
import com.pharmacy.util.Utils;

public class Selling implements Serializable {
	private static final long serialVersionUID = 1L;
	private long time;
	private Medicine medicine;
	private int quantity; // poate fi box quant sau subDiv quant in fucntie de tipul medicamentului

	public Selling(Medicine medicine, int quantity) {
		super();
		this.time = Utils.getActualTime();
		this.medicine = medicine;
		this.quantity = quantity;
	}

	public long getTime() {
		return time;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getQuantityForNextDays() {
		return quantity * Configuration.getInstance().getNextDays();
	}

	public void addQuantity(int quantity2) {
		this.quantity += quantity2;

	}

}
