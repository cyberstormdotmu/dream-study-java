package com.wy.server;

/**
 * @apiNote nginx学习使用,安装使用见文档.
 * @apiNote nginx在处理静态页面的效率和高并发上要比tomcat好,
 *          所以一般是nginx+tomcat来运行前后端程序
 * @instruction 实现负载均衡:可以再配置文件中添加upstream,多server,多日志配置,shell对日志分割
 * @author ParadiseWY
 * @date 2019年5月29日 下午5:05:49
 */
public class S_Nginx {

	/**
	 * nginx命令:start nginx.exe,nginx.exe -s stop,nginx.exe -s reload
	 * 正向代理:用户->代理->浏览器.浏览器可以很明确的知道访问他的用户地址
	 * 反响代理:用户->nginx->tomcat.tomcat不知道请求的真正来源,只知道是从nginx发送的请求
	 */
}