package com.stratego;

//Main class that opens an instance of GameDriver and creates a view:
public class Stratego {
	static GameDriver strategoDriver;
	
	public static void main(String[] args) {
		strategoDriver = GameDriver.getInstance();
		strategoDriver.openView();
	}
}