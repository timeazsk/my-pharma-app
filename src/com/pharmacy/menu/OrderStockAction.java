package com.pharmacy.menu;

import java.io.FileOutputStream;
import java.util.HashMap;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pharmacy.configuration.Configuration;
import com.pharmacy.database.Database;
import com.pharmacy.management.Medicine;
import com.pharmacy.management.Selling;
import com.training.sedinta09.multilanguage.MultilanguageImpl;

public class OrderStockAction implements Action {

	@Override
	public void run() {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("StockOrder.pdf"));
			document.open();
			document.add(new Paragraph(MultilanguageImpl.getInstance().getMessage("/orderPDF")
					+ Configuration.getInstance().getNextDays() + MultilanguageImpl.getInstance().getMessage("/days")));
			document.add(new Paragraph("   "));
			PdfPTable table = new PdfPTable(5);
			table.addCell(" ");
			table.addCell("Barcode");
			table.addCell("Brand");
			table.addCell(MultilanguageImpl.getInstance().getMessage("/type"));
			table.addCell(MultilanguageImpl.getInstance().getMessage("/quantity"));
			int index = 1;
			HashMap<Medicine, Integer> medicineOrderQuantity = getMedicineOrderQuantity();
			for (Medicine medicine : medicineOrderQuantity.keySet()) {
				table.addCell(String.valueOf(index));
				index++;
				table.addCell(medicine.getBarcode());
				table.addCell(medicine.getBrand());
				table.addCell(medicine.getType());
				table.addCell(String.valueOf(medicineOrderQuantity.get(medicine)));
			}
			document.add(table);
			document.close();
			// writer.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(MultilanguageImpl.getInstance().getMessage("/createPDF"));
	}

	private HashMap<Medicine, Integer> getMedicineOrderQuantity() {
		HashMap<Medicine, Integer> medicineOrderQuantity = new HashMap<>();
		for (Selling sale : Database.getInstance().getPreviousSales()) {
			Medicine medicine = sale.getMedicine();
			int orderQuantity = medicine.getOrderQuantity(sale.getQuantityForNextDays());
			if (orderQuantity > 0) {
				medicineOrderQuantity.put(medicine, orderQuantity);
			} else {
				continue;
			}
		}
		return medicineOrderQuantity;
	}

}
