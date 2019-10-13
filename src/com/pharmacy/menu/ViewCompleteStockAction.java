package com.pharmacy.menu;

import java.util.Collections;

import com.pharmacy.configuration.Configuration;
import com.pharmacy.database.Database;

public class ViewCompleteStockAction implements Action {

	@Override
	public void run() {
		int length = Database.getInstance().getLongestMedicineNameLength() + 2; // +2 pt 2 spatii
		String line = String.join("", Collections.nCopies((length * (Configuration.getInstance().getMaxColumns() + 1))
				+ (Configuration.getInstance().getMaxColumns() + 2), "-"));
		String space = String.join("", Collections.nCopies(length - 1, " "));
		System.out.println(line);
		System.out.print(String.format("|%" + length + "s|", " "));
		for (int i = 0; i < Configuration.getInstance().getMaxColumns(); i++) {
			System.out.print(String.format("%" + length + "s|", i + 1 + space));
		}
		System.out.println();
		System.out.println(line);

		Database.getInstance().printCompleteStock(length, line, space);
	}

}
