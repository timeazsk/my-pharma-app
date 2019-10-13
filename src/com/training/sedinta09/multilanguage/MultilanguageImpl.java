package com.training.sedinta09.multilanguage;

import java.io.*;
import java.util.*;

public class MultilanguageImpl implements Multilanguage {
	Language currentLanguage;
	Map<Language, Properties> messages = new HashMap<>();

	// singleton pattern -----------------
	private static Multilanguage instance = new MultilanguageImpl();

	private MultilanguageImpl() {
		setLanguage(Language.RO);
	}

	public static Multilanguage getInstance() {
		return instance;
	}
	// ----------------------------------

	@Override
	public void setLanguage(Language language) {
		currentLanguage = language;
		loadMessagesFile();
	}

	private void loadMessagesFile() {
		Properties properties = messages.get(currentLanguage);
		if (properties == null) {
			properties = new Properties();
			try {
				properties.load(new FileInputStream("messages_" + currentLanguage.name().toLowerCase() + ".txt"));
			} catch (IOException e) {
				System.out.println("Could not load messages file " + e.getMessage());
			}

			messages.put(currentLanguage, properties);
		}
	}

	@Override
	public String getMessage(String key) {
		Properties properties = messages.get(currentLanguage);
		String message = properties.getProperty(key);
		return message != null ? message : "!" + key;
	}

	@Override
	public String getMessage(String key, Object... params) {
		String message = getMessage(key);

		for (int i = 0; i < params.length; i++) {
			message = message.replace("{" + i + "}", params[i].toString());
		}

		return message;
	}

}
