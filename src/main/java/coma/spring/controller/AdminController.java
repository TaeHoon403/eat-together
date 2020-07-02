package coma.spring.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import coma.spring.dto.MemberDTO;



@Controller
@RequestMapping("/admin/")
public class AdminController {
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("toAdmin")
	public String toAdmin() {

		MemberDTO loginInfo = (MemberDTO)session.getAttribute("loginInfo");
		String adminCheck = loginInfo.getId();
		if(adminCheck.contentEquals("administrator")) {
			return "/admin/admin_main";
		}
		else {
			return "error";
		}
	}
	
}
