package com.bedrock.origin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketController {

	@Autowired
	private SimpMessagingTemplate messageingTemplate;
	
	@GetMapping("websocket.html")
	public String websocket(ModelMap map)
	{
		return "websocket";
	}
	
	@GetMapping("websocket2.html")
	public String websocket2(ModelMap map,HttpServletRequest request)
	{
		request.getSession().setAttribute("username", "admin");
		return "websocket2";
	}
	@GetMapping("websocket3.html")
	public String websocket3(ModelMap map,HttpServletRequest request)
	{
		request.getSession().setAttribute("guest", "guest");
		return "websocket3";
	}
	
    /**
     * 当浏览器向服务端发送请求时，通过@MessageMapping映射/welcome这个地址，类似于@RequestMapping
     * 当服务端有消息时，会对订阅了@SendTo中的路径的浏览器发送消息
     * @param msg
     * @return
     */
    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public String say(String msg){
    	/*messageingTemplate.convertAndSend("/topic/getResponse", "msg");*/
    	System.out.println(msg+"111111111111111111");
        return msg;
    }
    
    @MessageMapping("/chat")
    public void handleChat(String msg){
    	
    	// user是要发送的对象
    	///quequ/notificatinons 订阅地址                                                   
    	messageingTemplate.convertAndSendToUser("admin","/quequ/notifications",msg); 
    }
    
}