package coma.spring.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import coma.spring.dto.FaqDTO;
import coma.spring.dto.NoticeDTO;
import coma.spring.dto.NoticeFileDTO;
import coma.spring.service.FaqService;

@Controller
@RequestMapping("/faq/")
public class FaqController {

	@Autowired
	private FaqService fservice;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("toWrite")
	public String toWrite() {
		return "/faq/faq_write";
	}
	
	@RequestMapping("writeProc")
	public String writeProc(FaqDTO dto) throws Exception{
		System.out.println(dto.getTitle());
		fservice.insert(dto);
		
		
		return "redirect:/faq/list";
	}
	
	
	
	@RequestMapping("list")
	public String list(HttpServletRequest request) throws Exception{
		
		if(session.getAttribute("fcpage")==null) {
			session.setAttribute("fcpage", 1);
		}
		try { 
			session.setAttribute("fcpage", Integer.parseInt(request.getParameter("fcpage")));
		} catch (Exception e) {}
		int fcpage= (int) session.getAttribute("fcpage");
		List<FaqDTO> dto = fservice.selectByPage(fcpage);
		String navi = fservice.navi(fcpage);
		request.setAttribute("list", dto);
		request.setAttribute("navi", navi);
		return "/faq/faq_list";
	}
	
	@RequestMapping("delete")
	public String delete(String seq) throws Exception{
		fservice.delete(seq);
		return "redirect:list";
	}

	@RequestMapping("modify")
	public String toModify(int seq, HttpServletRequest request) throws Exception {
		FaqDTO dto = fservice.selectBySeq(seq);
		request.setAttribute("contents", dto);
		return "notice/notice_modify";
	}
	
	@RequestMapping("modifyProc")
	public String Modify(FaqDTO dto) throws Exception {
		fservice.update(dto);
		return "redirect:contents?seq="+dto.getSeq();
	}

	
}
