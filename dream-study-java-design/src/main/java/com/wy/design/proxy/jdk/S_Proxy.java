package com.wy.design.proxy.jdk;

/**
 * JDK动态代理,被代理类必须要实现某个接口,利用的是asm,底层直接修改字节码文件<br>
 * cglib动态代理,不需要实现接口,详见paradise-study-java-common项目
 *
 * @author ParadiseWY
 * @date 2020-09-26 23:48:57
 */
public interface S_Proxy {

	void print(String string);
}