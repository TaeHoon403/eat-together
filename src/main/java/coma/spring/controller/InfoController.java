package coma.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info/")
public class InfoController {
	@RequestMapping("toIntroduction")
	public String toIntroduction() {
		return "/info/introduction";
	}
	
	@RequestMapping("aboutUs")
	public String toAboutUs() {
		return "/info/aboutus";
	}

}
