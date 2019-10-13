package com.training.sedinta09.multilanguage;

import java.io.*;

public class MainApp {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		MainApp app = new MainApp();
		app.run();
	}

	private void run() {
		while (true) {
			showMenu();
			String option = readString();

			switch (option) {
			case "1":
				runLanguageMenu();
				break;
			case "0":
				return;
			}
		}
	}

	private String readString() {
		try {
			return br.readLine();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private void runLanguageMenu() {
		while (true) {
			showLanguageMenu();
			String option = readString();

			switch (option) {
			case "1":
				// System.out.println(ml.getMessage("chlang"));
				break;
			case "2":
				// System.out.println("Changing language to EN");
				break;
			case "0":
				return;
			}
		}
	}

	private void showLanguageMenu() {
		System.out.println("1. Romanian");
		System.out.println("2. English");
		System.out.println("0. Back");
	}

	private void showMenu() {
		System.out.println("1. " + "ch lang");
		System.out.println("0. Exit");
	}
}
