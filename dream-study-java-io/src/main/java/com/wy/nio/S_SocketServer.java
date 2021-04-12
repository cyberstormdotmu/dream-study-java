package com.wy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * NIO服务端
 * 
 * @author ParadiseWY
 * @date 2020-09-29 10:37:32
 */
public class S_SocketServer {

	/**
	 * 多路复用选择器,用于注册通道,对通道进行切换
	 */
	private Selector selector;

	public static void main(String[] args) throws IOException {
		S_SocketServer nio = new S_SocketServer();
		nio.initServer(18888);
		nio.listenSelector();
	}

	private void initServer(int i) throws IOException {
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// 设置为非阻塞
		// 在非阻塞模式下,read方法只传输立即可用的数据,如果没有可用的数据,返回0
		// 对于不能立即写入socket的数据,write也返回0
		serverChannel.configureBlocking(false);
		// 绑定端口
		serverChannel.socket().bind(new InetSocketAddress(i));
		// 初始化通道选择器
		this.selector = Selector.open();
		// server注册到选择器上,第2个参数是事件类型,连接,读,写等,此处监听接收事件
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("服务启动完成");
	}

	private void listenSelector() throws IOException {
		// 轮询监听selector
		while (true) {
			// 等待客户连接
			// selector模型,多路复用,此处会有阻塞,当没有任何链接或读写需要处理时,等待客户端连接
			this.selector.select();
			// 返回选中的通道标记集合,保存的是通道的标记
			Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
			// 轮询处理多个请求
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				// 通道是否有效
				if (key.isValid()) {
					// 处理请求
					handler(key);
				}
				// 从集合中删除
				iterator.remove();
			}
		}
	}

	private void handler(SelectionKey key) throws IOException {
		if (key.isAcceptable()) {
			// 若是连接成功,只有serversocket才会发生可接收事件,客户端不会发生该类型事件
			// 处理客户端连接请求时间,此处可以从selectionkey中获得当前服务端连接,也可以将服务端socket作为全局变量
			ServerSocketChannel channel = (ServerSocketChannel) key.channel();
			SocketChannel accept = channel.accept();
			// 必须设置成非阻塞,否则报错
			accept.configureBlocking(false);
			// 接收客户端发送的信息时,需要给通道设置读的权限
			accept.register(selector, SelectionKey.OP_READ);
		} else if (key.isWritable()) {
			// 若是一个写事件
			SocketChannel channel = (SocketChannel) key.channel();
			Scanner scan = new Scanner(System.in);
			String line = scan.nextLine();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			buffer.put(line.getBytes(StandardCharsets.UTF_8));
			buffer.flip();
			channel.write(buffer);
			channel.register(this.selector, SelectionKey.OP_READ);
			// 同时监听读和写,但最终还是要分开处理,一次只监听一种情况
			// channel.register(this.selector, SelectionKey.OP_READ |
			// SelectionKey.OP_WRITE);
			scan.close();
		} else if (key.isReadable()) {
			// 若是一个读的事件
			// 获取通道
			SocketChannel channel = (SocketChannel) key.channel();
			// 将通道中的数据读取到缓存中,通道中的数据就是客户端发送到服务器的数据
			ByteBuffer buf = ByteBuffer.allocate(1024);
			// flip是nio中控制buffer的操作,Buffer中有个游标,游标操作后不会归零,flip是重置游标的方法
			// buf.flip();
			// buf.remaining();获取buffer中有效数据长度
			// 当流中没有数据的时候,read读到的是0,而不是-1,-1是流已经关闭了
			while (channel.read(buf) != 0) {
				String info = new String(buf.array(), 0, buf.position(), StandardCharsets.UTF_8.displayName());
				System.out.println(info);
				buf.clear();
			}
			// 将要返回的消息写入到流中,不可直接写在读buf的流中,若返回的消息超过缓存buf的长度,无法返回全部消息
			channel.write(ByteBuffer.wrap("我收到你的信息了".getBytes()));
			// 关闭通道
			// key.channel().close();
			// 关闭连接
			// key.cancel();
			// System.out.println("客户端关闭了");
		} else if (key.isConnectable()) {
			// 连接建立后事件
		}
	}
}