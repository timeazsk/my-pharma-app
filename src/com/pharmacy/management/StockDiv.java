package com.pharmacy.management;

import static com.training.sedinta09.multilanguage.MultilanguageImpl.getInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.training.sedinta09.multilanguage.MultilanguageImpl;

public class StockDiv extends Stock implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Box> boxes = new ArrayList<>();

	public StockDiv(Medicine medicine, Box box) {
		super(medicine);
		boxes.add(box);
	}

	public StockDiv(Medicine medicine) {
		super(medicine);
	}

	@Override
	public void addToExistingStock(Medicine medicine) {
		if (getWholeBox(medicine) != null) { // daca gasesti o cutie intreaga, adaugi la cantitatea cutiilor
			getWholeBox(medicine).addQuantity();
		} else {
			boxes.add(new Box(1, medicine.getSubDiv()));

		}
	}

	private Box getWholeBox(Medicine medicine) {
		for (Box box : boxes) {
			if (box.getSubDivQuant() == medicine.getSubDiv()) {
				return box;
			}
		}
		return null;
	}

	@Override
	public int getStockVolume() {
		return getBoxQuantity() * medicine.getVolume();
	}

	private int getBoxQuantity() {
		int boxQuant = 0;
		for (Box box : boxes) {
			boxQuant += box.getQuantity();
		}
		return boxQuant;
	}

	@Override
	public void viewStock() {
		System.out.println(getInstance().getMessage("/stock") + ": " + medicine);
		for (Box box : boxes) {
			System.out.println(box.getQuantity() + " " + getInstance().getMessage("/boxesW") + " " + box.getSubDivQuant()
					+ " " + getInstance().getMessage("/divisions"));
		}
	}

	@Override
	public int getMedicineQuantity() {
		int subDivQuant = 0;
		for (Box box : boxes) {
			subDivQuant += box.getQuantity() * box.getSubDivQuant();
		}
		return subDivQuant;

	}

	@Override
	public void addBox(int quantity, int subDivQuant) {
		if (quantity == 0) {
			boxes.add(new Box(1, subDivQuant));
		} else {
			boxes.add(new Box(quantity, subDivQuant));
		}

	}

	private Box getUnOpenedBox() {
		for (Box box : boxes) {
			if (box.getSubDivQuant() == this.medicine.getSubDiv()) {
				return box;
			}
		}
		return null;
	}

	private Box getOpenedBox() {
		for (Box box : boxes) {
			if (box.getSubDivQuant() < this.medicine.getSubDiv()) {
				return box;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Box> getBoxes() {
		return (ArrayList<Box>) this.boxes;
	}

	@Override
	public Stock suggestStockToRemove(int quantity) {
		Stock stockToRemove = new StockDiv(medicine);
		Box box = getOpenedBox(); // ca sa obtii cutia desfacuta prima data
		if (getOpenedBox() == null) {
			box = getUnOpenedBox();
		}
		while (quantity > 0) {
			if (box.getQuantity() * box.getSubDivQuant() >= quantity) {
				if (quantity >= medicine.getSubDiv()) {
					stockToRemove.addBox(quantity / medicine.getSubDiv(), medicine.getSubDiv());
					quantity -= quantity / medicine.getSubDiv() * medicine.getSubDiv();
					box = getUnOpenedBox();
				} else {
					stockToRemove.addBox(quantity / medicine.getSubDiv(), quantity);
					return stockToRemove;
				}
			} else {
				stockToRemove.addBox(box.getQuantity(), box.getSubDivQuant());
				if (getUnOpenedBox() == null) { // daca ai doar o cutie desfacuta
					return stockToRemove;
				}
				if (box.equals(getUnOpenedBox())) { // daca este cutia nedesfacuta, inseamna ca nu mai ai alta cutie
					return stockToRemove;
				} else {
					quantity -= box.getSubDivQuant();
				}
			}
		}
		return stockToRemove;
	}

	@Override
	public Stock getStockByQuantity(int actualQuantity) {
		while (actualQuantity > 0) {
			if (actualQuantity % medicine.getSubDiv() != 0) {
				boxes.add(new Box(actualQuantity / medicine.getSubDiv(), medicine.getSubDiv()));
				actualQuantity -= medicine.getSubDiv();
			}
		}
		return null;
	}

	@Override
	public void removeQuantity(int quantity) {
		// for (Box box : boxes) {
		Box box = getOpenedBox(); // ca sa obtii cutia desfacuta prima data
		if (getOpenedBox() == null) {
			box = getUnOpenedBox();
		}
		if (box.getSubDivQuant() * box.getQuantity() <= quantity) {
			quantity -= box.getSubDivQuant() * box.getQuantity();
			boxes.remove(box);
			// box.setSubDivQuantity(0);
			// box.setQuantity(0);
			if (quantity == 0) {
				return;
			} else {
				removeQuantity(quantity);
			}

		} else if (quantity % medicine.getSubDiv() == 0) {
			box.setQuantity(box.getQuantity() - quantity / medicine.getSubDiv());
			return;
		} else {
			boxes.add(new Box(1, ((box.getQuantity() * box.getSubDivQuant()) - quantity) % medicine.getSubDiv()));
			box.setQuantity((box.getQuantity() * box.getSubDivQuant() - quantity) / medicine.getSubDiv());
			box.setSubDivQuantity(medicine.getSubDiv());
			if (box.getQuantity() == 0) {
				boxes.remove(box);
			}

			return;
		}
	}

	@Override
	public void addToExistingStock(Stock inventoryStock) {
		if (getBoxBySubDivQuantity(inventoryStock.getMedicineQuantity()) != null) {
			getBoxBySubDivQuantity(inventoryStock.getMedicineQuantity()).addQuantity();
		} else {
			boxes.add(new Box(1, inventoryStock.getMedicineQuantity()));
		}
	}

	private Box getBoxBySubDivQuantity(int medicineQuantity) {
		for (Box box : boxes) {
			if (box.getSubDivQuant() == medicineQuantity) {
				return box;
			}
		}
		return null;
	}

	@Override
	public boolean compareTo(Stock stock2) {
		if (this.getMedicineQuantity() == stock2.getMedicineQuantity()) {
			Stock arrangedStock = createStockByQuantity(stock2.getMedicineQuantity());
			for (Box box : boxes) {
				if ((stock2.hasBox(box)) && arrangedStock.hasBox(box)) {
					continue;
				} else {
					System.out.println(MultilanguageImpl.getInstance().getMessage("/sameSubDivWrongArrangem"));
					System.out.println(MultilanguageImpl.getInstance().getMessage("/rearrangeSubDiv"));
					arrangedStock.viewStock();
					return true;
				}
			}
			return true;
		}
		return false;

	}

	@Override
	public Stock createStockByQuantity(int medicineQuantity) {
		Stock stock = new StockDiv(medicine);
		int subDiv = medicine.getSubDiv();
		if (medicineQuantity / subDiv == 0) {
			stock.addBox(1, medicineQuantity);
		} else if (medicineQuantity % subDiv == 0) {
			stock.addBox(medicineQuantity / subDiv, subDiv);
		} else {
			stock.addBox(medicineQuantity / subDiv, subDiv);
			stock.addBox(1, medicineQuantity % subDiv);
		}
		return stock;
	}

	@Override
	public boolean hasBox(Box box2) {
		for (Box box : boxes) {
			if (box.equals(box2)) {
				return true;
			}
		}
		return false;
	}
}