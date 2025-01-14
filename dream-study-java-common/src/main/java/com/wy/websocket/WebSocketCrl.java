package com.wy.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * websocket测试类
 * 
 * @author 飞花梦影
 * @date 2021-01-12 11:15:31
 * @git {@link https://github.com/mygodness100}
 */
@Controller
@RequestMapping("/api/websocket")
public class WebSocketCrl {

	@GetMapping("/index/{userId}")
	public ModelAndView socket(@PathVariable String userId) {
		ModelAndView mav = new ModelAndView("/socket1");
		mav.addObject("userId", userId);
		return mav;
	}

	// 推送数据接口
	@ResponseBody
	@RequestMapping("/socket/push/{cid}")
	public Map<String, Object> pushToWeb(@PathVariable String cid, String message) {
		Map<String, Object> result = new HashMap<>();
		try {
			WebSocketService.sendInfo(message, cid);
			result.put("code", cid);
			result.put("msg", message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}