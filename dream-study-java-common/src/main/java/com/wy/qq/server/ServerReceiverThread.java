package com.wy.qq.server;

import java.net.Socket;

import com.wy.qq.MessageFactory;

/**
 * 服务器接受者线程
 */
public class ServerReceiverThread extends Thread {
	
	//通客户端通信的socket对象
	private Socket sock ;
	
	public ServerReceiverThread(Socket sock){
		this.sock = sock ;
	}
	
	public void run() {
		try {
			while(true){
				MessageFactory.parseClientMessageAndGenServerMsg(sock);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}