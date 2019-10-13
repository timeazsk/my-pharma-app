package com.pharmacy.configuration;

import java.util.ArrayList;

import com.pharmacy.util.Utils;

public class Configuration {

	private String databaseFileName;
	private int maxLines;
	private int maxColumns;
	private int length;
	private int width;
	private int height;
	private int maxOccupancyPercent;
	private int previousDays;
	private int nextDays;

	private static Configuration instance;

	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
		return instance;
	}

	private Configuration() {
		// aici citesti fisierul de configurare si populezi
		// variabilele membru

		ArrayList<String> xmlValues = MyDomParser.readXMLFile2();

		databaseFileName = xmlValues.get(0);
		maxLines = Utils.getIntRepresentation(xmlValues.get(1));
		maxColumns = Utils.getIntRepresentation(xmlValues.get(2));
		length = Utils.getIntRepresentation(xmlValues.get(3));
		width = Utils.getIntRepresentation(xmlValues.get(4));
		height = Utils.getIntRepresentation(xmlValues.get(5));
		maxOccupancyPercent = Utils.getIntRepresentation(xmlValues.get(6));
		previousDays = Utils.getIntRepresentation(xmlValues.get(7));
		nextDays = Utils.getIntRepresentation(xmlValues.get(8));
	}

	public String getDatabaseFileName() {
		return databaseFileName;
	}

	public int getMaxLines() {
		return maxLines;
	}

	public int getMaxColumns() {
		return maxColumns;
	}

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getMaxOccupancyPercent() {
		return maxOccupancyPercent;
	}

	public int getMacOccupancyVolume() {
		return maxOccupancyPercent * length * width * height / 100;
	}

	public int getPreviousDays() {
		return previousDays;
	}

	public int getNextDays() {
		return nextDays;
	}

}