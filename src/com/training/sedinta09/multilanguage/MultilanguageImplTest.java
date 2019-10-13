//package com.training.sedinta09.multilanguage;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//
//class MultilanguageImplTest {
//
//	@org.junit.Test
//	void testGetMessage() {
//		Multilanguage ml = new MultilanguageImpl();
//		assertEquals("Schimba limba", ml.getMessage("chlang"));
//		assertEquals("!xxx", ml.getMessage("xxx"));
//	}
//
//	@Test
//	void testGetMessageParams() {
//		Multilanguage ml = new MultilanguageImpl();
//		assertEquals("Limba curenta este romana si valoarea enum-ului este RO",
//				ml.getMessage("langchanged", "romana", "RO"));
//		ml.setLanguage(Language.EN);
//		assertEquals("The current language is 1 and the enum value is 2", ml.getMessage("langchanged", "1", "2"));
//	}
//}
