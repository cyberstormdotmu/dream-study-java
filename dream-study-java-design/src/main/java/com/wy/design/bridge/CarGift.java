package com.wy.design.bridge;

/**
 * 车类,需要传递一个礼物的实现类
 * 
 * @author ParadiseWY
 * @date 2020-09-27 23:29:36
 */
public class CarGift extends Gift {

	public CarGift(GiftImpl gift) {
		this.impl = gift;
	}
}