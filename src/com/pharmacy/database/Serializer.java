package com.pharmacy.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.pharmacy.configuration.Configuration;
import com.training.sedinta09.multilanguage.MultilanguageImpl;

public class Serializer implements Serializable {

	private static final long serialVersionUID = 1L;

	public static void save(Database db) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(Configuration.getInstance().getDatabaseFileName()));
			oos.writeObject(db);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Database load() {
		Database data;
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(Configuration.getInstance().getDatabaseFileName()));
			data = (Database) ois.readObject();
			ois.close();
			return data;
		} catch (FileNotFoundException e) {
			System.out.println(MultilanguageImpl.getInstance().getMessage("/initialFile"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
