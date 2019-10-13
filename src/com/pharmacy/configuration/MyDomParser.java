package com.pharmacy.configuration;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MyDomParser {

	private static ArrayList<String> configuration;

	public static ArrayList<String> readXMLFile2() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		configuration = new ArrayList<>();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("config.xml");

			saveByTagName(doc.getElementsByTagName("datafile"));
			NodeList maxLinesList = doc.getElementsByTagName("maxLines");
			saveByTagName(maxLinesList);
			NodeList maxColumnsList = doc.getElementsByTagName("maxColumns");
			saveByTagName(maxColumnsList);
			NodeList lenghtList = doc.getElementsByTagName("length");
			saveByTagName(lenghtList);
			NodeList widthList = doc.getElementsByTagName("width");
			saveByTagName(widthList);
			NodeList heightList = doc.getElementsByTagName("height");
			saveByTagName(heightList);
			NodeList maxOccupancyPercentList = doc.getElementsByTagName("maxOccupancyPercent");
			saveByTagName(maxOccupancyPercentList);
			NodeList previousDaysList = doc.getElementsByTagName("previousDays");
			saveByTagName(previousDaysList);
			NodeList nextDaysList = doc.getElementsByTagName("nextDays");
			saveByTagName(nextDaysList);
			return configuration;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static void saveByTagName(NodeList datafileList) {
		for (int i = 0; i < datafileList.getLength(); i++) {
			Node df = datafileList.item(i);
			if (df.getNodeType() == Node.ELEMENT_NODE) {
				Element datafile = (Element) df;
				configuration.add(datafile.getTextContent());

			}
		}
	}

	@Override
	public String toString() {
		return "MyDomParser2 []";
	}

}
