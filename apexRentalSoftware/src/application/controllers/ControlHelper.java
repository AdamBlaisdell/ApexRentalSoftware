package application.controllers;


public class ControlHelper {
	
	public static boolean isSpaces(String stringToCheck) {
		if (stringToCheck.replaceAll("\\s", "") == "" || stringToCheck == null) {
			return true;
		} else {
			return false;
		}
	}
	
}
