package coma.spring.controller;

import java.net.URI;
import java.net.URLEncoder;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import coma.spring.dto.MemberDTO;
import coma.spring.dto.PartyDTO;
import coma.spring.service.PartyService;

@Controller
@RequestMapping("/party/")
public class PartyController {
	

	@Autowired
	private PartyService pservice;
	@Autowired
	private HttpSession session;
	
	@RequestMapping("toParty_New")
	public String toPartyNew() {
		
		return "/party/party_new";
	}

	@RequestMapping(value = "party_New_Proc", method = RequestMethod.POST)
	/*public String partyNewProc(HttpServletRequest request) {*/
	public String partyNewProc(PartyDTO dto, HttpServletRequest request) throws Exception {
		
//		String parent_name = dto.getParent_name();
//		String parent_address = dto.getParent_address();
//		String title = dto.getTitle();
		
		String date = dto.getDate();
		String time = dto.getTime();
		String dateAndtime = date + " "+time+":00.0";

		Timestamp meetdate = java.sql.Timestamp.valueOf(dateAndtime);
		dto.setMeetdate(meetdate);
		MemberDTO account = (MemberDTO) session.getAttribute("loginInfo");
		String id= account.getId();
		dto.setWriter(id);
		dto.setStatus("1");
//
//		int count = dto.getCount();
//		
//		String age = dto.getAge();
//		String gender = dto.getGender();
//		
//		String drinking = dto.getDrinking();
//		String content = dto.getContent();
//		
		int myseq = pservice.partyInsert(dto); 
		// 모임 등록 작업 수행
		System.out.println(myseq);
		//모임 등록 후 등록된 페이지로 이동 
		PartyDTO content=pservice.selectBySeq(myseq);
		
		System.out.println(content.getTime());
		request.setAttribute("con",content);
		
		
		return "/party/party_content";
	}
	
	//모임 글보기
	@RequestMapping(value="party_content")
	public String party_content(String seq, HttpServletRequest request) throws Exception {
		PartyDTO content=pservice.selectBySeq(Integer.parseInt(seq));
		request.setAttribute("con",content);
		return "/party/party_content";
	}
	
	
	
	@RequestMapping(value="toSearchStore", method=RequestMethod.GET)
	public String toSearchStore() {
		return "/party/searchStore";
	}
	
	@ResponseBody
	@RequestMapping(value="searchStoreProc", method=RequestMethod.GET, produces="application/json;charset=utf8")
	public String searchStoreProc(String keyword, String category, String page) throws Exception {
		String HOST = "https://dapi.kakao.com";
		String APIKEY = "KakaoAK 80c29b1bba14c9c568e4ae4f89fc9368";
		System.out.println(keyword);
		
		RestTemplate restTemplate = new RestTemplate(); 
		
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", APIKEY);
	    headers.add("Accept",MediaType.APPLICATION_JSON_UTF8_VALUE);
	    headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charser=UTF-8");
	  
	    String  query = URLEncoder.encode(keyword,"UTF-8");

		HttpEntity entity = new HttpEntity("parameters", headers); 
		URI foodurl=URI.create("https://dapi.kakao.com/v2/local/search/keyword.json?query="+query+"&"+"category_group_code=FD6&page="+page); 
		URI cafeurl=URI.create("https://dapi.kakao.com/v2/local/search/keyword.json?query="+query+"&"+"category_group_code=CE7&page="+page);
		
		String resp;
		
		RequestEntity<String> rq = new RequestEntity<>(headers, HttpMethod.GET, foodurl);
		RequestEntity<String> rq1 = new RequestEntity<>(headers, HttpMethod.GET, cafeurl);
		if(category.contentEquals("f")) {
		resp = restTemplate.postForObject(foodurl, rq, String.class);
		System.out.println("f");
		}else {
		resp = restTemplate.postForObject(cafeurl, rq1, String.class);
		System.out.println("c");
		}
		/*
		 * Map<String,String> respBody = new HashMap<String, String>();
		 * respBody.put("food",resp); respBody.put("cafe",resp2);
		 */
		System.out.println(page);
		
        System.out.println(resp);
		return resp;
	}
	
	@RequestMapping("partymodify")
	public String partymodify(String seq, HttpServletRequest request)  throws Exception {
		PartyDTO content=pservice.selectBySeq(Integer.parseInt(seq));
		request.setAttribute("con",content);
		
		return "/party/party_modify";
	}
	
	@RequestMapping("party_modifyProc")
	public String partymodifyProc(PartyDTO dto, HttpServletRequest request) throws Exception{
		
		pservice.update(dto);
		return "redirect:/party/party_content?seq="+dto.getSeq();
	}
	
	@RequestMapping("partydelete")
	public String partydelete(String seq)  throws Exception {
		pservice.delete(seq);
		return "redirect:/map/toMap";
	}
	
	@RequestMapping("partylist")
	public String partyList() throws Exception {
		return "/party/party_list";
	}
}
