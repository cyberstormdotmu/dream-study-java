package com.wy.strategy;

public class StrategyA implements Strategy {

	public void drawText(String text, int lineWidth, int lineCount) {
		System.out.println("StrategyA......drawtext");
	}
}