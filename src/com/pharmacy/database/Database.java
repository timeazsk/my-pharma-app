package com.pharmacy.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.pharmacy.configuration.Configuration;
import com.pharmacy.management.Drawer;
import com.pharmacy.management.Inventory;
import com.pharmacy.management.Medicine;
import com.pharmacy.management.Selling;
import com.pharmacy.management.Stock;
import com.pharmacy.management.StockAdjustment;
import com.pharmacy.util.Sign;
import com.pharmacy.util.Utils;

public class Database implements Serializable {

	transient private static final long serialVersionUID = 1L;
	private List<Drawer> drawers;
	private List<Medicine> medicine = new ArrayList<Medicine>();
	private List<Selling> sales = new ArrayList<Selling>();
	// transient private MyDomParser parser = new MyDomParser();
	private List<StockAdjustment> adjustment = new ArrayList<>();

	// -------SINGLETON--------
	private static Database instance = createDatabase();

	private Database() {
	}

	private static Database createDatabase() {
		if (Serializer.load() == null) {
			return new Database();
		} else {
			return Serializer.load();
		}
	}

	public static Database getInstance() {
		return instance;
	}

	// -------SINGLETON--------

	public boolean dbDoesNotExist() {
		return (Serializer.load() == null);

	}

	public void createEmptyDrawers() {
		drawers = new ArrayList<Drawer>();
		int counter = 0;
		for (char ch = 'A'; ch < 'Z'; ch++) {
			counter++;
			if (counter <= Configuration.getInstance().getMaxLines()) {
				for (int j = 0; j < Configuration.getInstance().getMaxColumns(); j++) {
					drawers.add(new Drawer(String.valueOf(ch), String.valueOf(j + 1)));
				}
			}
		}
		System.out.println(drawers.toString());
		Serializer.save(Database.getInstance());
	}

	public Medicine getMedicineByBarcode(String barcode) {
		for (Medicine med : medicine) {
			if (med.hasBarcode(barcode)) {
				return med;
			} else {
				continue;
			}
		}
		return null;
	}

	public void addMedicineToDatabase(Medicine medicine2) {
		medicine.add(medicine2);
	}

	public Drawer suggestStockLocation(Medicine medicine) {
		if (getDrawerByMedicineBrand(medicine) != null) {
			return getDrawerByMedicineBrand(medicine);
		} else if (getEmptyDrawer(medicine) != null) {
			return getEmptyDrawer(medicine);
		} else if (getDrawerWithFewerBrands(medicine) != null) {
			return getDrawerWithFewerBrands(medicine);
		}
		return null;
	}

	private Drawer getEmptyDrawer(Medicine medicine2) {
		for (Drawer drawer : drawers) {
			if (drawer.isEmpty()) {
				return drawer;
			}
		}
		return null;
	}

	private Drawer getDrawerByMedicineBrand(Medicine medicine2) {
		for (Drawer drawer : drawers) {
			if ((drawer.hasBrand(medicine2)) && (drawer.hasEnoughSpace(medicine2))) {
				return drawer;
			}
		}
		return null;
	}

	private Drawer getDrawerWithFewerBrands(Medicine medicine) { // fewer brands si care are si loc
		Drawer minDrawer = getMinDrawer(medicine);
		for (Drawer drawer : drawers) {
			if (drawer.hasEnoughSpace(medicine)) {
				if (drawer.countBrands() < minDrawer.countBrands()) {
					minDrawer = drawer;
				}
			}
		}
		return minDrawer;
	}

	private Drawer getMinDrawer(Medicine medicine2) {
		for (Drawer drawer : drawers) {
			if (drawer.hasEnoughSpace(medicine2)) {
				return drawer;
			}
		}
		return null;
	}

	public ArrayList<Medicine> getMedicine() {
		return new ArrayList<Medicine>(medicine);
	}

	public Drawer getDrawerByID(String suggestedStockLocation) {
		for (Drawer drawer : drawers) {
			if (drawer.getDrawerID().equals(suggestedStockLocation)) {
				return drawer;
			}
		}
		return null;
	}

	public int getTotalMedicineQuantity(Medicine medicine) { // cantitatea medicamentului din toate sertarele
		int quantity = 0;
		for (Drawer drawer : drawers) {
			if (drawer.getMedicineStock(medicine) != null) {
				quantity += drawer.getMedicineStock(medicine).getMedicineQuantity();
			}
		}
		return quantity;
	}

	public void recordSelling(Medicine medicine2, int quantity) {
		if (getSellingByMedicine(medicine2) != null) {
			getSellingByMedicine(medicine2).addQuantity(quantity);
		} else {
			sales.add(new Selling(medicine2, quantity));
		}

	}

	private Selling getSellingByMedicine(Medicine medicine2) {
		for (Selling sale : sales) {
			if (medicine2.equals(sale.getMedicine())) {
				return sale;
			}
		}
		return null;
	}

	public boolean medicineDoesNotExist(Medicine medicine2) {
		for (Medicine med : medicine) { // te uiti in stocul din db al tipurilor de medicamente
			if (med.equals(medicine2)) {
				return false;
			}
		}
		return true;
	}

	public void searchForMedicineInDatabase(String search) {
		showDetails(suggestMedicine(search));

	}

	public ArrayList<Medicine> suggestMedicine(String search) {
		ArrayList<Medicine> searchedMedicine = new ArrayList<>();
		for (Medicine med : medicine) {
			if ((med.getBrand().contains(search)) || (med.getBarcode().contains(search))) {
				searchedMedicine.add(med);
			}
		}
		if (searchedMedicine.size() == 0) {
			System.out.println("Nu s-a gasit nici un medicament care sa contina aceste detalii");
		}
		return searchedMedicine;

	}

	private void showDetails(ArrayList<Medicine> suggestMedicine) {
		for (Medicine med : suggestMedicine) {
			ArrayList<String> drawerIDs = new ArrayList<>();
			System.out.println(med.toString());
			for (Drawer drawer : drawers) {
				if (drawer.getMedicineStock(med) != null) {
					drawerIDs.add(drawer.getDrawerID());
				}
			}
			System.out.println("Sertare: " + drawerIDs.toString());

		}

	}

	public ArrayList<Drawer> getMedicineLocations(Medicine medicine2) {
		ArrayList<Drawer> suggestedDrawers = new ArrayList<>();
		for (Drawer drawer : drawers) {
			if (drawer.getMedicineStock(medicine2) != null) {
				suggestedDrawers.add(drawer);
			}
		}
		return suggestedDrawers;

	}

	public void removeStock(Drawer drawer, Stock stock, int quantity) {
		drawer.removeStockByQuantity(stock, quantity);
	}

	public ArrayList<Selling> getPreviousSales() {
		ArrayList<Selling> previousSales = new ArrayList<>();
		long previousDayMiliSeconds = Configuration.getInstance().getPreviousDays() * 24 * 60 * 60 * 1000;
		for (Selling sale : sales) {
			if (sale.getTime() >= Utils.getActualTime() - previousDayMiliSeconds) {
				previousSales.add(sale);
			}
		}
		return previousSales;
	}

	public int getLongestMedicineNameLength() {
		int length = 0;
		for (Drawer drawer : drawers) {
			if (drawer.getLongestMedicineNameLength() > length) {
				length = drawer.getLongestMedicineNameLength();
			}
		}
		return length;
	}

	public void printCompleteStock(int length, String line, String space) {
		List<Drawer> drawersPerLine = new ArrayList<>();
		int counter = 0;
		for (char ch = 'A'; ch < 'Z'; ch++) {
			counter++;
			if (counter <= Configuration.getInstance().getMaxLines()) {
				System.out.print(String.format("|%" + length + "s|", ch + space));
				for (int j = 0; j < Configuration.getInstance().getMaxColumns(); j++) {
					Drawer drawer = getDrawerByID((ch + Integer.toString(j + 1)).toUpperCase());
					drawersPerLine.add(new Drawer(drawer));
				}
				printStockPerLine(drawersPerLine, length);
				drawersPerLine.clear();
				System.out.println();
				System.out.println(line);
			}
		}
	}

	private void printStockPerLine(List<Drawer> drawersPerLine, int length) {
		int maxStockNumber = getMaxStockNumber(drawersPerLine);
		if (maxStockNumber == 0) {
			System.out.print(String.format("%" + length + "s|%" + length + "s|%" + length + "s|", " ", " ", " "));
		}
		for (int i = 0; i < maxStockNumber; i++) {
			for (Drawer drawer : drawersPerLine) {
				if (drawer.getStockSize() > i) {
					System.out.print(String.format("%" + length + "s|", drawer.getStockByIndex(i).getMedicine().getBrand()));
				} else {
					System.out.print(String.format("%" + length + "s|", ""));
				}
			}
			if (i == maxStockNumber - 1) {
				break;
			}
			System.out.println();
			System.out.print(String.format("|%" + length + "s|", " "));
		}
	}

	private int getMaxStockNumber(List<Drawer> drawersPerLine) {
		int max = 0;
		for (Drawer drawer : drawersPerLine) {
			if (drawer.getStockQuantity() > max) {
				max = drawer.getStockQuantity();
			}
		}
		return max;
	}

	public void addStockAdjustment(StockAdjustment stockAdjust) {
		this.adjustment.add(stockAdjust);
	}

	public void saveStockAdjustments(Drawer drawer, Inventory inventory) {
		inventory.setStock(inventory.getArrangedStock());
		if (drawer.getStockSize() >= inventory.getStocksize()) {
			for (Stock stoc : drawer.getStock()) {
				Stock invStock = inventory.getMedicineStock(stoc.getMedicine());
				if (invStock != null) {
					if (stoc.getMedicineQuantity() == invStock.getMedicineQuantity()) {
						continue;
					} else {
						int difference = stoc.getMedicineQuantity() - invStock.getMedicineQuantity();
						addStockAdjustment(new StockAdjustment(drawer.getDrawerID(), getSign(difference),
								stoc.getMedicine().getBrand(), Math.abs(difference)));
					}
				} else {
					addStockAdjustment(new StockAdjustment(drawer.getDrawerID(), Sign.MINUS.getOptionValue(),
							stoc.getMedicine().getBrand(), stoc.getMedicineQuantity()));
				}
			}
		} else {
			for (Stock stoc : inventory.getStock()) {
				Stock drawStock = drawer.getMedicineStock(stoc.getMedicine());
				if (drawStock != null) {
					if (stoc.getMedicineQuantity() == drawStock.getMedicineQuantity()) {
						continue;
					} else {
						int difference = drawStock.getMedicineQuantity() - stoc.getMedicineQuantity();
						addStockAdjustment(new StockAdjustment(drawer.getDrawerID(), getSign(difference),
								stoc.getMedicine().getBrand(), difference));
					}
				} else {
					addStockAdjustment(new StockAdjustment(drawer.getDrawerID(), Sign.PLUS.getOptionValue(),
							stoc.getMedicine().getBrand(), stoc.getMedicineQuantity()));
				}
			}
		}
	}

	// private void saveStockAdjustments(List<Stock> stock, Object obj, Drawer
	// drawer) {
	// for (Stock stoc : stock) {
	// if(stoc.getMedicine().equals(obj.getClass().
	// ))
	// if (lStock.getMedicine().equals(sStock.getMedicine())) {
	// if (lStock.getMedicineQuantity() == sStock.getMedicineQuantity()) {
	// break;
	// } else {
	// int difference = lStock.getMedicineQuantity() - sStock.getMedicineQuantity();
	// addStockAdjustment(new StockAdjustment(drawer.getDrawerID(),
	// getSign(difference),
	// lStock.getMedicine().getBrand(), difference));
	// }
	// }
	// }
	// }
	//
	// }

	private String getSign(int difference) {
		if (difference < 0) {
			return Sign.PLUS.getOptionValue();
		} else {
			return Sign.MINUS.getOptionValue();
		}
	}

	public void viewStockAdjustments() {
		int length = getLongestMedicineNameLength();
		for (StockAdjustment adj : adjustment) {
			adj.viewAdjustments(length);
			System.out.println();
			// System.out.print(String.format("|%" + length + "s| ", " "));
			// System.out.println(adj.toString());
		}

	}

}