package com.wy.design.strategy;

public class StrategyB implements Strategy {

	public void drawText(String text, int lineWidth, int lineCount) {
		System.out.println("StrategyB......drawtext");
	}
}