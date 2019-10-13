package com.pharmacy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class Utils {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static int getIntRepresentation(String s) {
		return Integer.parseInt(s);
	}

	public static String readInput(String message) {
		System.out.print(message);
		try {
			return br.readLine();
		} catch (IOException e) {
			System.out.println("Wrong format");
			e.printStackTrace();
		}
		return null;
	}

	public static String readInputCancelable(String message) throws InterruptedException {
		System.out.print(message);
		try {
			String input = br.readLine();
			if (input.length() == 0) {
				throw new InterruptedException();
			}
			return input;
		} catch (IOException e) {
			System.out.println("Wrong format");
			e.printStackTrace();
		}
		return null;
	}

	public static String readOption(String message, String[] options) {
		System.out.print(message);

		System.out.print("(");
		boolean first = true;
		for (String s : options) {
			if (!first) {
				System.out.print("/");
			}
			first = false;
			System.out.print(s);
		}
		System.out.print(")");

		try {
			String input = br.readLine();
			// if (input.length() == 0) {
			// throw new InterruptedException();
			// }

			String result = getOptionForInput(input, options);

			return result;
		} catch (IOException e) {
			System.out.println("Wrong format");
			e.printStackTrace();
		}
		return null;
	}

	public static String readOptionCancelable(String message, String[] options) throws InterruptedException {
		System.out.print(message);

		System.out.print("(");
		boolean first = true;
		for (String s : options) {
			if (!first) {
				System.out.print("/");
			}
			first = false;
			System.out.print(s);
		}
		System.out.print(")");

		try {
			String input = br.readLine();
			if (input.length() == 0) {
				throw new InterruptedException();
			}

			String result = getOptionForInput(input, options);

			return result;
		} catch (IOException e) {
			System.out.println("Wrong format");
			e.printStackTrace();
		}
		return null;
	}

	private static String getOptionForInput(String input, String[] options) {

		for (String s : options) {
			if (s.toLowerCase().startsWith(input.toLowerCase())) {
				return s;
			}
		}

		return null;
	}

	public static long getActualTime() {
		Date date = new Date();
		return date.getTime();
	}

	public static Date getDate() {
		Date date = new Date();
		return date;
	}

}
