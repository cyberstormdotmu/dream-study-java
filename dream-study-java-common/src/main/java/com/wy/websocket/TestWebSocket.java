package com.wy.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * WebSocket推送消息到前端
 * 
 * 全双工即双向通讯,本质上是一个额外的TCP连接,建立和关闭时握手使用http协议,其他数据传输不使用http协议,<br>
 * 更加复杂一些,适用于需要进行复杂双向数据通讯的场景,支持大部分主流浏览器
 * 
 * 开发成本较高,适用性较好适用于聊天功能等
 * 
 * WebSocket是HTML5开始提供的一种在单个 TCP 连接上进行全双工通讯的协议
 * 
 * 
 * SSE与WebSocket有相似功能,都是用来建立浏览器与服务器之间的通信渠道,两者的区别在于:
 * 
 * WebSocket是全双工通道,可以双向通信,功能更强;SSE是单向通道,只能服务器向浏览器端发送<br>
 * WebSocket是一个新的协议,需要服务器端支持;SSE则是部署在 HTTP协议之上的,现有的服务器软件都支持<br>
 * SSE是一个轻量级协议,相对简单;WebSocket是一种较重的协议,相对复杂<br>
 * SSE默认支持断线重连,WebSocket则需要额外部署<br>
 * SSE支持自定义发送的数据类型<br>
 * SSE不支持CORS,参数url就是服务器网址,必须与当前网页的网址在同一个网域,而且协议和端口都必须相同
 * 
 * @author ParadiseWY
 * @date 2020-11-13 17:32:16
 * @git {@link https://github.com/mygodness100}
 */
@ServerEndpoint("/websocket")
public class TestWebSocket {

	// 客户端代码
	//
	// var websocket = null;
	// //判断当前浏览器是否支持WebSocket
	// if ('WebSocket' in window) {
	// websocket = new WebSocket("ws://localhost:8080/WebSocketTest/websocket");
	// }
	// else {
	// alert('当前浏览器 Not support websocket')
	// }
	//
	// //连接发生错误的回调方法
	// websocket.onerror = function () {
	// setMessageInnerHTML("WebSocket连接发生错误");
	// };
	//
	// //连接成功建立的回调方法
	// websocket.onopen = function () {
	// setMessageInnerHTML("WebSocket连接成功");
	// }
	//
	// //接收到消息的回调方法
	// websocket.onmessage = function (event) {
	// setMessageInnerHTML(event.data);
	// }
	//
	// //连接关闭的回调方法
	// websocket.onclose = function () {
	// setMessageInnerHTML("WebSocket连接关闭");
	// }
	//
	// //监听窗口关闭事件,当窗口关闭时,主动去关闭websocket连接,防止连接还没断开就关闭窗口,server端会抛异常。
	// window.onbeforeunload = function () {
	// closeWebSocket();
	// }
	//
	// //将消息显示在网页上
	// function setMessageInnerHTML(innerHTML) {
	// document.getElementById('message').innerHTML += innerHTML + '<br/>';
	// }
	//
	// //关闭WebSocket连接
	// function closeWebSocket() {
	// websocket.close();
	// }
	//
	// //发送消息
	// function send() {
	// var message = document.getElementById('text').value;
	// websocket.send(message);
	// }

	private static int onlineCount = 0;// 记录当前在线人数

	// 确保线程安全
	private static CopyOnWriteArraySet<TestWebSocket> webSocketSet = new CopyOnWriteArraySet<TestWebSocket>();

	private Session session;

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this);
		addOnlineCount();
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	@OnClose
	public void OnClose() {
		webSocketSet.remove(this); // 从set中删除
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	@OnMessage
	public void OnMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);
		// 群发消息
		for (TestWebSocket item : webSocketSet) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	@OnError
	public void OnError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
		// this.session.getAsyncRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		TestWebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		TestWebSocket.onlineCount--;
	}
}