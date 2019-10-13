package com.pharmacy.menu;

import static com.pharmacy.util.Utils.getIntRepresentation;
import static com.pharmacy.util.Utils.readInputCancelable;
import static com.pharmacy.util.Utils.readOptionCancelable;
import static com.training.sedinta09.multilanguage.MultilanguageImpl.getInstance;

import com.pharmacy.database.Database;
import com.pharmacy.management.Medicine;
import com.pharmacy.management.MedicineDiv;
import com.pharmacy.management.MedicineIndiv;
import com.pharmacy.util.MedicineType;
import com.pharmacy.util.Option;

public class AddMedicineAction implements Action {

	@Override
	public void run() {
		Medicine medicine = null;
		try {
			String barcode = readInputCancelable(getInstance().getMessage("/inpBarcode"));
			if (Database.getInstance().getMedicineByBarcode(barcode) != null) {
				System.out.println(getInstance().getMessage("/barcodeExist"));
				return;
			}
			String brand = readInputCancelable(getInstance().getMessage("/enterBrand"));
			String details = readInputCancelable(getInstance().getMessage("/enterDetail"));
			int length = Integer.parseInt(readInputCancelable(getInstance().getMessage("/length")));
			int width = Integer.parseInt(readInputCancelable(getInstance().getMessage("/width")));
			int height = Integer.parseInt(readInputCancelable(getInstance().getMessage("/height")));
			String option = readOptionCancelable(getInstance().getMessage("/medType"),
					new String[] { Option.YES.getOptionValue(), Option.NO.getOptionValue() });

			if (Option.NO.getOptionValue().equals(option)) {
				medicine = new MedicineIndiv(barcode, brand, details, MedicineType.INDIV.getType(), length, width, height);
			} else if (Option.YES.getOptionValue().equals(option)) {
				int subDiv = getIntRepresentation(readInputCancelable(getInstance().getMessage("/subDiv")));
				medicine = new MedicineDiv(barcode, brand, details, MedicineType.DIV.getType(), length, width, height, subDiv);
			} else {
				return;
			}
			Database.getInstance().addMedicineToDatabase(medicine);
		} catch (InterruptedException e) {
			System.out.println(getInstance().getMessage("/actCancel"));
		}
	}

}
