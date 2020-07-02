package coma.spring.controller;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat/")
public class ChatController {
	@Autowired
	private HttpSession session;

	@Autowired
	private SqlSessionTemplate mybatis;
	
	@RequestMapping("/")
	public String chat(int roomNum) {
		
		return "chat";
	}	
}
