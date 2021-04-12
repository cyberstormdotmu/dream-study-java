package com.wy.design.proxy;

/**
 * 代理调用类
 *
 * @author ParadiseWY
 * @date 2020-09-26 23:55:08
 */
public class S_ProxyClient implements S_Proxy {

	private S_Proxy proxy;

	/**
	 * 默认代理
	 */
	public S_ProxyClient() {
		proxy = new S_ProxyDefault();
	}

	/**
	 * 指定代理
	 * 
	 * @param proxy 具体的代理实现类
	 */
	public S_ProxyClient(S_Proxy proxy) {
		this.proxy = proxy;
	}

	@Override
	public void cry() {
		proxy.cry();
	}

	@Override
	public void eat() {
		proxy.eat();
	}
}