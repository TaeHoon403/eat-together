package coma.spring.controller;

import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import coma.spring.service.ChatService;
import coma.spring.service.MemberService;
import coma.spring.statics.ChatStatics;

@Controller
@RequestMapping("chat")
public class ChatController {
	@Autowired
	private HttpSession session;

	@Autowired
	private ChatService cservice;
	
	@RequestMapping("chatroom")
	public String chat(int roomNum) {
		// 방번호를 받음 : 웹소켓에서 삭제됨
		this.session.setAttribute("roomNum", roomNum);
		// 방번호의 저장된 채팅이 있는지 검색 
		boolean ChatroomExist = true;
		for(int i : ChatStatics.savedChats.keySet()) {
			if(i == roomNum) {ChatroomExist = false;}
		}
		// 없으면 cservice.savedChat를 통해 가져옴 (채팅 리스트 ,채팅수)
		if(ChatroomExist) {
			cservice.savedChat(roomNum);
		}
		return "/chat/chatroom";
	}	
}
