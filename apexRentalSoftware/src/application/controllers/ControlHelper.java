package application.controllers;


public class ControlHelper {
	
	public void populate(){
	
	}
	
	public void refresh() {
		
		
	}
	
	public static boolean isSpaces(String stringToCheck) {
		if (stringToCheck.replaceAll("\\s", "") == "" || stringToCheck == null) {
			return true;
		} else {
			return false;
		}
	}
	
}
