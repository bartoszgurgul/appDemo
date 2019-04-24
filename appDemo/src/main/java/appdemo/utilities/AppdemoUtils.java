package appdemo.utilities;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppdemoUtils {
	
	private AppdemoUtils() {
		throw new IllegalStateException("Utility class");
	}
	public static boolean checkEmailOrPassword(String pattern, String pStr) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(pStr);
		return m.matches();
	}
	
	public static String randomStringGenerator() {
		StringBuilder randomString = new StringBuilder();
		
		String signs = "abcdefghijklmnopqrstuvwxyz"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "12345678990"
				+ "!@#$%^&*()-+";
		
		Random rnd = new Random();
		for(int i = 0;i< 32;i++) {
			int liczba = rnd.nextInt(signs.length());
	
			randomString.append(signs.substring(liczba, liczba + 1));
		}
		
		
		return randomString.toString();
	}
}
