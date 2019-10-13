package com.training.sedinta09.multilanguage;

public interface Multilanguage {
	void setLanguage(Language language);

	String getMessage(String key);

	String getMessage(String key, Object... params);
}
