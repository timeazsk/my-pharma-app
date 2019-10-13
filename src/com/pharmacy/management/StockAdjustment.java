package com.pharmacy.management;

import java.io.Serializable;
import java.util.Date;

import com.pharmacy.util.Utils;

public class StockAdjustment implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date date;
	private String ID;
	private String sign;
	private String medicine;
	private int quantity;

	public StockAdjustment(String iD, String sign, String medicine, int quantity) {
		super();
		this.date = Utils.getDate();
		this.ID = iD;
		this.sign = sign;
		this.medicine = medicine;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "StockAdjustment [date=" + date + ", ID=" + ID + ", sign=" + sign + ", medicine=" + medicine + ", quantity="
				+ quantity + "]";
	}

	public void viewAdjustments(int length) {
		System.out.print(String.format("|%20s| ", this.date + " "));
		System.out.print(String.format("%4s| ", this.ID + " "));
		System.out.print(String.format("%3s| ", this.sign + " "));
		System.out.print(String.format("%" + length + "s| ", this.medicine + " "));
		System.out.print(String.format("%6s| ", this.quantity + " "));

	}

}
