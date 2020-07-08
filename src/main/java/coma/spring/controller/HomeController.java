package coma.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import coma.spring.dto.MemberDTO;
import coma.spring.service.MsgService;


@Controller
public class HomeController {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private MsgService msgservice;
	
	@RequestMapping("/")
	public String home() throws Exception{
		

		
		if(session.getAttribute("loginInfo")==null) {
			return "home";
		}else {
			MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
			int newMsg =msgservice.newmsg(mdto.getId());
			session.setAttribute("newMsg", newMsg);
			return "home";
		}
		
	}
	
	@RequestMapping("error")
	public String error() {
		return "error";
	}
	
}
