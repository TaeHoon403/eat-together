package coma.spring.controller;

import java.net.URI;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import coma.spring.dto.MapDTO;
import coma.spring.dto.MemberDTO;
import coma.spring.dto.PartyDTO;
import coma.spring.dto.PartySearchListDTO;
import coma.spring.service.ChatService;
import coma.spring.service.MapService;
import coma.spring.service.PartyService;

@Controller
@RequestMapping("/party/")
public class PartyController {

	@Autowired
	private PartyService pservice;

	@Autowired
	private MapService mapservice;

	@Autowired
	private HttpSession session;
	
	// 수지 모임글 작성 페이지 이동
	@RequestMapping("toParty_New")
	public String toPartyNew(HttpServletRequest request) {
		try {
			MemberDTO account = (MemberDTO) session.getAttribute("loginInfo");
			String age = account.getBirth();
			request.setAttribute("age", age);
		}catch(Exception e) {}
		return "/party/party_new";
	}
	// 수지 모임글 작성
	@RequestMapping(value = "party_New_Proc", method = RequestMethod.POST)
	/*public String partyNewProc(HttpServletRequest request) {*/
	public String partyNewProc(PartyDTO dto, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {

		//		String parent_name = dto.getParent_name();
		//		String parent_address = dto.getParent_address();
		//		String title = dto.getTitle();

		String date = dto.getDate();
		String time = dto.getTime();
		String dateAndtime = date + " "+time+":00.0";

		Timestamp meetdate = java.sql.Timestamp.valueOf(dateAndtime);
		dto.setMeetdate(meetdate);
		MemberDTO account = (MemberDTO) session.getAttribute("loginInfo");
		String userid= account.getId();
		dto.setWriter(userid);
		dto.setStatus("1");
		//
		int count = dto.getCount();
		System.out.println("카운트="+count);
		//		
		//		String age = dto.getAge();
		//		String gender = dto.getGender();
		//		
		//		String drinking = dto.getDrinking();
		//		String content = dto.getContent();
		//		
		System.out.println((String)request.getParameter("lat"));
		System.out.println((String)request.getParameter("lng"));
		String place_url = "http://place.map.kakao.com/" + dto.getPlace_id();
		if(mapservice.insertPossible(place_url)) {
			MapDTO mdto = new MapDTO();
			mdto.setName(dto.getParent_name());
			mdto.setAddress((String)request.getParameter("address_name"));
			mdto.setRoad_address(dto.getParent_address());
			mdto.setCategory((String)request.getParameter("category"));
			Double lat = Double.parseDouble((String)request.getParameter("lat")); mdto.setLat(lat);
			Double lng = Double.parseDouble((String)request.getParameter("lng")); mdto.setLng(lng);
			mdto.setPhone((String)request.getParameter("phone"));
			mdto.setPlace_url((String)request.getParameter("place_url"));
			mdto.setPlace_id(dto.getPlace_id());
			mapservice.insert(mdto);
		}

		int myseq = pservice.partyInsert(dto);   // 글번호
		//채팅 insert (생성)  cservice.insert(seq);

		// 모임 등록 작업 수행
		System.out.println(myseq);
		//모임 등록 후 등록된 페이지로 이동 
		//PartyDTO content=pservice.selectBySeq(myseq);

		redirectAttributes.addAttribute("seq", myseq);
		//		request.setAttribute("con", content);
		//		request.setAttribute("seq", myseq);
		System.out.println("파티 이동!!1");
		return "redirect:/party/party_content";
	}
	// 수지 모임 생성 중 상호 검색 창 이동 
	@RequestMapping(value="toSearchStore", method=RequestMethod.GET)
	public String toSearchStore() {
		return "/party/searchStore";
	}
	// 수지 모임 생성 중 상호 검색 
	@ResponseBody
	@RequestMapping(value="searchStoreProc", method=RequestMethod.GET, produces="application/json;charset=utf8")
	public String searchStoreProc(String keyword, String category) throws Exception {
		String HOST = "https://dapi.kakao.com";
		String APIKEY = "KakaoAK 80c29b1bba14c9c568e4ae4f89fc9368";
		System.out.println(keyword);

		Gson gson = new Gson();
		JsonObject result = new JsonObject();
		JsonArray resultadd = new JsonArray();
		boolean breakpoint = false;

		loop: for(int page = 1; page < 46;page++) {

			if(breakpoint) {break loop;}
			RestTemplate restTemplate = new RestTemplate(); 
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("page", "" + page);
			System.out.println("페이지 : " + page);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", APIKEY);
			headers.add("Accept",MediaType.APPLICATION_JSON_UTF8_VALUE);
			headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charser=UTF-8");

			String  query = URLEncoder.encode(keyword,"UTF-8");

			HttpEntity entity = new HttpEntity(params, headers); 

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
			JsonObject obj = gson.fromJson(resp, JsonObject.class);
			JsonArray docs = obj.getAsJsonArray("documents");
			JsonObject meta = obj.getAsJsonObject("meta");
			JsonElement ele = meta.get("is_end");
			for(JsonElement doc : docs) {
				resultadd.add(doc);				
			}
			if(ele.getAsBoolean()) {breakpoint = true;}
		}
		result.add("documents", resultadd);
		String resp = result.toString();
		return resp;
	}
	// 수지 모임 수정 페이지 이동
	@RequestMapping("partymodify")
	public String partymodify(String seq, HttpServletRequest request)  throws Exception {
		PartyDTO content=pservice.selectBySeq(Integer.parseInt(seq));
		request.setAttribute("con",content);
		try {
			MemberDTO account = (MemberDTO) session.getAttribute("loginInfo");
			String age = account.getBirth();
			request.setAttribute("age", age);
		}catch(Exception e) {}
		return "/party/party_modify";
	}
	// 수지 모임 수정
	@RequestMapping("party_modifyProc")
	public String partymodifyProc(PartyDTO dto, HttpServletRequest request) throws Exception{
		String date = dto.getDate();
		String time = dto.getTime();
		String dateAndtime = "";
		if(time.length()==8) {
			dateAndtime = date + " "+time+".0";
		}else {
			dateAndtime = date + " "+time+":00.0";
		}
		System.out.println(date);
		System.out.println(time);
		System.out.println(dateAndtime);

		Timestamp meetdate = java.sql.Timestamp.valueOf(dateAndtime);
		dto.setMeetdate(meetdate);


		System.out.println(dto.getMeetdate());

		pservice.update(dto);
		return "redirect:/party/party_content?seq="+dto.getSeq();
	}
	// 수지 모임 삭제
	@RequestMapping("partydelete")
	public String partydelete(String seq)  throws Exception {
		pservice.delete(seq);
		return "redirect:/map/toMap";
	}
	// 태훈 모임 리스트 출력
//	@RequestMapping("partylist")
//	public String partyList(HttpServletRequest request) throws Exception {
//		
//		List<PartyDTO> partyList = pservice.selectList();
//		System.out.println(partyList.size());
//		
//		List<String> imgList = new ArrayList<>();
//		for(int i=0; i<partyList.size(); i++) {
//					
//			imgList.add(pservice.clew(partyList.get(i).getParent_name()));
//			System.out.println(i +" : "+partyList.get(i).getSeq()+" : "+imgList.get(i));
//		}
//		
//		
//		request.setAttribute("list", partyList);
//		request.setAttribute("imglist", imgList);
//		return "/party/party_list";
//	}
	// 태훈 모임 리스트 네비 포함
	@RequestMapping("partylist")
	public String partyList(HttpServletRequest request) throws Exception {
		
		int cpage=1;
		try {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}catch(Exception e) {

		}
		List<PartyDTO> partyList = pservice.selectList(cpage);
		String navi = pservice.getPageNaviTH(cpage);
		System.out.println(partyList.size());

		List<String> imgList = new ArrayList<>();
		for(int i=0; i<partyList.size(); i++) {

			imgList.add(pservice.clew(partyList.get(i).getParent_name()));
			System.out.println(i +" : "+partyList.get(i).getSeq()+" : "+imgList.get(i));
		}
		
		Map<String,String> param = pservice.partyCountById();
		List<MapDTO> top = mapservice.selectTopStroe(param);
		
		List<String> imgList2 = new ArrayList<>();
		for(int i=0; i<top.size(); i++) {
			imgList2.add(pservice.clew(top.get(i).getName()));
			System.out.println(i + " : " + top.get(i).getSeq() + " : "+imgList2.get(i));
			System.out.println(top.get(i).getName());
		}
		
		request.setAttribute("navi", navi);
		request.setAttribute("list", partyList);
		request.setAttribute("imglist", imgList);
		request.setAttribute("top", top);
		request.setAttribute("imglist2", imgList2);
		return "/party/party_list";
	}
	// 태훈 모임 상세 검색
	@RequestMapping(value="partysearch",  method = RequestMethod.POST)
	public String partySearch(PartySearchListDTO pdto, HttpServletRequest request) throws Exception {

		System.out.println(pdto.getSido());
		System.out.println(pdto.getGugun());
		System.out.println(pdto.getGender());
		System.out.println(pdto.getAge());
		System.out.println(pdto.getDrinking());
		System.out.println(pdto.getText());
		System.out.println(pdto.getSearch());

		List<PartyDTO> partyList = pservice.partySearch(pdto);
		System.out.println(partyList.size());
		List<String> imgList = new ArrayList<>();
		for(int i=0; i<partyList.size(); i++) {

			imgList.add(pservice.clew(partyList.get(i).getParent_name()));
			System.out.println(i +" : "+partyList.get(i).getSeq()+" : "+imgList.get(i));
			System.out.println();
		}

		System.out.println(partyList);
		request.setAttribute("list", partyList);
		request.setAttribute("imglist", imgList);
		return "/party/party_list";
	}
	// 태훈 모임 내용 모달 창
	//@RequestMapping(value="party_content_include2")
	@RequestMapping(value="party_content_include")
	public String party_content_include(HttpServletRequest request) throws Exception {
		PartyDTO content = pservice.selectBySeq(Integer.parseInt(request.getParameter("seq")));
		String img = pservice.clew(content.getParent_name());
		
		request.setAttribute("con",content);
		request.setAttribute("img", img);
		
		return "/include/party_content_include";
		//return "/include/party_content_include2";
	}
	// 예지 모임 글 보기
	@RequestMapping(value="party_content")
	public String party_content(String seq, HttpServletRequest request) throws Exception {
		PartyDTO content=pservice.selectBySeq(Integer.parseInt(seq));
		String img = pservice.clew(content.getParent_name());
		request.setAttribute("img", img);
		request.setAttribute("con",content);
		return "/party/party_content";
	}
	// 예지 잘 모르겠음
	@RequestMapping("stopRecruit")
	public String stopRecruit(String seq) throws Exception {
		pservice.stopRecruit(seq);
		return "redirect:/party/party_content?seq="+seq;
	}
	// 지은 작성자 별 모임 리스트
	@RequestMapping("selectByWriter")
	public ModelAndView selectByWriter(int mcpage) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/mypage_chatlist");

		if(mcpage==0) {
			mcpage=1;
		}

		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		String writer = mdto.getId();
		List<PartyDTO> partyList = pservice.selectByWriterPage(writer, mcpage);
		String navi = pservice.getMyPageNav(mcpage, writer);
		System.out.println("내 모임 개수 : " + partyList.size());
		mav.addObject("partyList", partyList);
		mav.addObject("navi", navi);

		return mav;
	}
	// 이미지 클롤링
	@ResponseBody
	@RequestMapping(value="clewimg", method=RequestMethod.GET, produces="text/plain;charset=utf8")
	public String clewimg(String parent_name)  throws Exception {
		System.out.println("상점이름" + parent_name);
		String imgaddr = pservice.clew(parent_name);
		System.out.println("이미지 주소 " + imgaddr);
		return String.valueOf(imgaddr);
	}
}
