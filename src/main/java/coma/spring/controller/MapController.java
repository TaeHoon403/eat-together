package coma.spring.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import coma.spring.dto.MapDTO;
import coma.spring.dto.PartyDTO;
import coma.spring.dto.ReviewDTO;
import coma.spring.dto.ReviewFileDTO;
import coma.spring.service.MapService;
import coma.spring.service.PartyService;
import coma.spring.service.ReviewService;

@Controller
@RequestMapping("/map/")
public class MapController {

	private static final String HOST = "https://dapi.kakao.com";

	@Autowired
	private MapService mapservice;
	@Autowired
	private PartyService pservice;
	@Autowired
	private ReviewService rservice;
	@Autowired
	private ServletContext sc;
	@Autowired
	private HttpSession session;

	@ResponseBody
	@RequestMapping("cafeJson")
	public String getCafeJson() throws Exception {
		Gson gson = new Gson();
		String jsonPath = sc.getRealPath("resources/json/KakaoCafe.json");
		Reader reader = new FileReader(jsonPath);
		JsonObject readObj = gson.fromJson(reader, JsonObject.class);
		String respBody = gson.toJson(readObj);
		System.out.println(respBody);
		return respBody;
	}

	@RequestMapping("toMap")
	public String map(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 등록된 맛집 뿌려주기
		List<MapDTO> list = mapservice.selectAll();
		Gson gson = new Gson();
		int cpage = 1;
		try {cpage = Integer.parseInt(request.getParameter("cpage"));}catch(Exception e) {}
		int place_id = 0;
		try{place_id = Integer.parseInt(request.getParameter("place_id"));}catch(Exception e) {}
		String object = gson.toJson(list);
		//request.setAttribute("json", object);
		String jsonPath = sc.getRealPath("resources/json/mapData.json");
		//InputStream jsonStream = new FileInputStream(jsonPath);
		File file = new File(jsonPath);
		FileWriter fw = new FileWriter(file, false);
		JsonArray arr = gson.fromJson(object, JsonArray.class);
		for(JsonElement ele : arr) {
			JsonObject obj = ele.getAsJsonObject();
			int result = mapservice.selectPartyOn(obj.get("place_id").getAsInt());
			obj.addProperty("partyOn", result);
		}
		fw.write(arr.toString());
		fw.flush();
		fw.close();
		response.setHeader("cache-control","no-cache,no-store");
		if(session.getAttribute("loginInfo")==null) {
			int count = pservice.selectAllCount();
			request.setAttribute("partyCount", count);
		}
		return "/map/map";
	}
	@RequestMapping("insert")
	public String insert(MapDTO mapdto, String detail_category) throws Exception {
		// 판단
		if(!mapservice.insertPossible(mapdto.getPlace_url())) {
			return "/map/insertFail";
		}else {
			System.out.println(mapdto.getName() + " : " + 
					mapdto.getAddress() + " : " + 
					mapdto.getRoad_address() + " : " +
					mapdto.getCategory() + " : " + 
					mapdto.getLat() + " : " + 
					mapdto.getLng() + " : " +
					mapdto.getRating_avg() + " : " +
					mapdto.getPhone() + " : " + 
					mapdto.getPlace_url() + " : " + 
					mapdto.getPlace_id());
			mapservice.insert(mapdto);
			return "redirect:/map/toMap";
		}
	}

	@ResponseBody
	@RequestMapping(value="food",produces="application/json;charset=utf8",method=RequestMethod.GET)
	public String getFood(String lng, String lat) throws RestClientException, URISyntaxException{
		Gson gson = new Gson();
		JsonObject result = new JsonObject();
		JsonArray resultadd = new JsonArray();
		boolean breakpoint = false;
		loop: for(int page = 1; page < 46;page++) {
			if(breakpoint) {break loop;}
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("x", lng);
			params.add("y", lat);
			params.add("category_group_code", "FD6");
			params.add("radius", "20000");
			params.add("page", "" + page);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "KakaoAK " + "e156322dd35cfd9dc276f1365621ae9a");
			headers.add("Accept",MediaType.APPLICATION_JSON_UTF8_VALUE);
			headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charser=UTF-8");

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
			String respBody = restTemplate.postForObject(new URI(HOST + "/v2/local/search/category.json"), request, String.class);
			System.out.println(respBody);
			JsonObject obj = gson.fromJson(respBody, JsonObject.class);
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
	@ResponseBody
	@RequestMapping(value="cafe",produces="application/json;charset=utf8",method=RequestMethod.GET)
	public String getCafe(String lng, String lat) throws RestClientException, URISyntaxException{
		Gson gson = new Gson();
		JsonObject result = new JsonObject();
		JsonArray resultadd = new JsonArray();
		loop: for(int page = 1; page < 46;page++) {
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("x", lng);
			params.add("y", lat);
			params.add("category_group_code", "CE7");
			params.add("radius", "20000");
			params.add("page", "" + page);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "KakaoAK " + "e156322dd35cfd9dc276f1365621ae9a");
			headers.add("Accept",MediaType.APPLICATION_JSON_UTF8_VALUE);
			headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charser=UTF-8");

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
			String respBody = restTemplate.postForObject(new URI(HOST + "/v2/local/search/category.json"), request, String.class);
			System.out.println(respBody);
			JsonObject obj = gson.fromJson(respBody, JsonObject.class);
			JsonArray docs = obj.getAsJsonArray("documents");
			JsonObject meta = obj.getAsJsonObject("meta");
			JsonElement ele = meta.get("is_end");
			for(JsonElement doc : docs) {
				resultadd.add(doc);				
			}
			if(!ele.getAsBoolean()) {continue loop;}
			else{break loop;}
		}
		result.add("documents", resultadd);
		String resp = result.toString();
		return resp;
	}

	@ResponseBody
	@RequestMapping(value="search",produces="application/json;charset=utf8",method=RequestMethod.GET)
	public String searchByKeyword(String keyword, HttpServletRequest req) throws Exception{
		// 검색도 ajax 말고 페이지 이동으로?
		// 검색 기능 ? keyword 로 Json 파일에서 검색 ! -> map 테이블에 있는 내용이 최상단
		// 카페 정보
		String cafePath = sc.getRealPath("resources/json/cafe.json");
		Gson gson = new Gson();
		JsonArray cafeArr = new JsonArray();
		JsonObject respObj = new JsonObject();
		List<MapDTO> list = mapservice.searchByKeyword(keyword);
		JsonArray maplist = gson.fromJson(gson.toJson(list), JsonArray.class);
		for(JsonElement ele : maplist) {
			JsonObject obj = ele.getAsJsonObject();
			int result = mapservice.selectPartyOn(obj.get("place_id").getAsInt());
			obj.addProperty("partyOn", result);
		}
		respObj.add("map_list", maplist);
		File cafeFile = new File(cafePath);
		if(cafeFile.exists()) {
			Reader reader = new FileReader(cafePath);
			JsonObject readObj = gson.fromJson(reader, JsonObject.class);
			JsonArray arr = (JsonArray)readObj.get("cafe_list");
			boolean addPossible = false;
			for(JsonElement ele : arr) { // cafe_list 에서 cafe 정보 빼오기
				JsonObject cafeObj = ele.getAsJsonObject();
				JsonObject cafe = cafeObj.get("cafe").getAsJsonObject();
				String place_name = cafe.get("place_name").getAsString();
				if(place_name.contains(keyword)) { 
					for(JsonElement map : maplist) { // maplist 에서 map 꺼내기
						JsonObject mapObj = map.getAsJsonObject();
						String id = cafe.get("id").getAsString(); // id 비교 ~~ 
						String map_id = mapObj.get("place_id").getAsString();
						if(!id.equals(map_id)) {
							addPossible = true;
						}
					}
					if(addPossible) cafeArr.add(cafe);
				}
			}
			respObj.add("cafe_list", cafeArr);
		}

		String foodPath = sc.getRealPath("resources/json/food.json");
		JsonArray foodArr = new JsonArray();
		File foodFile = new File(foodPath);
		if(foodFile.exists()) {
			Reader reader = new FileReader(foodPath);
			JsonObject readObj = gson.fromJson(reader, JsonObject.class);
			JsonArray arr = (JsonArray)readObj.get("food_list");
			boolean addPossible = false;
			for(JsonElement ele : arr) {
				JsonObject foodObj = ele.getAsJsonObject();
				JsonObject food = foodObj.get("food").getAsJsonObject();
				String place_name = food.get("place_name").getAsString();
				if(place_name.contains(keyword)) { 
					for(JsonElement map : maplist) {
						JsonObject mapObj = map.getAsJsonObject();
						String id = food.get("id").getAsString();
						String map_id = mapObj.get("place_id").getAsString();
						if(!id.equals(map_id)) {
							addPossible = true;
						}
					}
					if(addPossible) foodArr.add(food);
				}
			}
			respObj.add("food_list", foodArr);
		}
		String respBody =  gson.toJson(respObj);
		System.out.println(respBody);
		return respBody;
	}

	@ResponseBody
	@RequestMapping(value="searchCafeBtn",produces="application/json;charset=utf8",method=RequestMethod.GET)
	public String searchByCafe(String category, HttpServletRequest req) throws Exception{
		String cafePath = sc.getRealPath("resources/json/cafe.json");
		Gson gson = new Gson();
		JsonArray cafeArr = new JsonArray();
		JsonObject respObj = new JsonObject();

		List<MapDTO> list = mapservice.searchByCategory("카페");
		JsonArray maplist = gson.fromJson(gson.toJson(list), JsonArray.class);
		for(JsonElement ele : maplist) {
			JsonObject obj = ele.getAsJsonObject();
			int result = mapservice.selectPartyOn(obj.get("place_id").getAsInt());
			obj.addProperty("partyOn", result);
		}
		respObj.add("map_list", maplist);
		
		File cafeFile = new File(cafePath);
		if(cafeFile.exists()) {
			Reader reader = new FileReader(cafePath);
			JsonObject readObj = gson.fromJson(reader, JsonObject.class);
			JsonArray arr = (JsonArray)readObj.get("cafe_list");
			boolean addPossible = false;
			for(JsonElement ele : arr) {
				JsonObject cafeObj = ele.getAsJsonObject();
				JsonObject cafe = cafeObj.get("cafe").getAsJsonObject();
				for(JsonElement map : maplist) {
					JsonObject mapObj = map.getAsJsonObject();
					String id = cafe.get("id").getAsString();
					String map_id = mapObj.get("place_id").getAsString();
					if(!id.equals(map_id)) {
						addPossible = true;
					}
				}
				if(addPossible) cafeArr.add(cafe);
			}
			respObj.add("cafe_list", cafeArr);
		}
		
		String respBody =  gson.toJson(respObj);
		return respBody;
	}
	@ResponseBody
	@RequestMapping(value="searchFoodBtn",produces="application/json;charset=utf8",method=RequestMethod.GET)
	public String searchByFood(HttpServletRequest req) throws Exception{
		Gson gson = new Gson();
		JsonObject respObj = new JsonObject();
		List<MapDTO> list = mapservice.searchByCategory("음식점");
		JsonArray maplist = gson.fromJson(gson.toJson(list), JsonArray.class);
		for(JsonElement ele : maplist) {
			JsonObject obj = ele.getAsJsonObject();
			int result = mapservice.selectPartyOn(obj.get("place_id").getAsInt());
			obj.addProperty("partyOn", result);
		}
		respObj.add("map_list", maplist);
		
		String foodPath = sc.getRealPath("resources/json/food.json");
		JsonArray foodArr = new JsonArray();
		
		File foodFile = new File(foodPath);
		if(foodFile.exists()) {
			Reader reader = new FileReader(foodPath);
			JsonObject readObj = gson.fromJson(reader, JsonObject.class);
			JsonArray arr = (JsonArray)readObj.get("food_list");
			for(JsonElement ele : arr) {
				JsonObject foodObj = ele.getAsJsonObject();
				JsonObject food = foodObj.get("food").getAsJsonObject();
				boolean addPossible = false;
				for(JsonElement map : maplist) {
					JsonObject mapObj = map.getAsJsonObject();
					String id = food.get("id").getAsString();
					String map_id = mapObj.get("place_id").getAsString();
					if(!id.equals(map_id)) {
						addPossible = true;
					}
				}
				if(addPossible) foodArr.add(food);
			}
			respObj.add("food_list", foodArr);
		}
		String respBody =  gson.toJson(respObj);
		return respBody;
	}

	@ResponseBody
	@RequestMapping(value="cafeInsert",produces="application/json;charset=utf8",method=RequestMethod.GET)
	public String insertKakaoCafeData(String lng, String lat, HttpServletResponse response) throws Exception {
		response.setHeader("cache-control","no-cache,no-store");
		return insertJson("resources/json/cafe.json","CE7",lng,lat);
	}

	@ResponseBody
	@RequestMapping(value="foodInsert",produces="application/json;charset=utf8",method=RequestMethod.GET)
	public String insertKakaoFoodData(String lng, String lat, HttpServletResponse response) throws Exception {
		response.setHeader("cache-control","no-cache,no-store");
		return insertJson("resources/json/food.json","FD6",lng,lat);
	}

	public String insertJson(String path, String category_code, String lng, String lat) throws Exception {

		// 초기 설정
		String list_name = null;
		String category_name = null;
		if(category_code.equals("FD6")) {
			list_name = "food_list";
			category_name = "food";
		}else if(category_code.equals("CE7")) {
			list_name = "cafe_list";
			category_name = "cafe";
		}

		String jsonPath = sc.getRealPath(path);
		System.out.println(jsonPath);
		// 다음 지도 API 에서 값 json 파일로 저장하는 native 코드
		// 중심 좌표를 기준으로 카테고리별 데이터 넣는 로직
		Gson gson = new Gson();
		JsonObject respObj = new JsonObject();
		JsonArray respArr = new JsonArray();
		String respBody = null;

		File file = new File(jsonPath);
		if(!file.exists()) {
			file.getParentFile().mkdirs(); // Will create parent directories if not exists
			file.createNewFile();
			FileOutputStream s = new FileOutputStream(file,false);
		}
		// if문 : json 파일 read - 일치하는 값은 제외하고 입력!
		Reader reader = new FileReader(jsonPath);
		JsonObject readObj = gson.fromJson(reader, JsonObject.class);
		// 페이지별 값 가져오기
		loop: for(int page = 1; page < 46;page++) {
			// 페이지마다 카테고리별 검색 진행 
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("x", lng);
			params.add("y", lat);
			params.add("category_group_code", category_code);
			params.add("radius", "20000");
			params.add("page", "" + page);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "KakaoAK " + "e156322dd35cfd9dc276f1365621ae9a");
			headers.add("Accept",MediaType.APPLICATION_JSON_UTF8_VALUE);
			headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charser=UTF-8");

			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
			respBody = restTemplate.postForObject(new URI(HOST + "/v2/local/search/category.json"), request, String.class);

			if(readObj == null) { // json 파일이 비어있을 때 
				// 입력 진행
				JsonObject obj = gson.fromJson(respBody, JsonObject.class);
				JsonArray docs = obj.getAsJsonArray("documents");
				for(JsonElement doc: docs) { 
					JsonObject place_info = doc.getAsJsonObject();
					JsonObject place_obj = new JsonObject();
					place_obj.add(category_name, place_info);
					respArr.add(place_obj);
					respObj.add(list_name, respArr);
				}
				String resp = gson.toJson(respObj);
				FileWriter fw = new FileWriter(file, true);
				// 이어서 입력되도록 하기 ( read 해서 추가되도록 ) 
				fw.write(resp);
				fw.flush();
				fw.close();
				break;
			}else { // json 파일이 비어있지 않을 때
				JsonArray readArr = (JsonArray) readObj.get(list_name);
				List<String> existIds = new ArrayList<>();
				for(JsonElement readEle : readArr) { // 첫 번째 for문 (파일에 있는 맛집 반복해서 꺼냄) 
					JsonObject read_obj = (JsonObject) readEle.getAsJsonObject().get(category_name);
					String id = read_obj.get("id").toString(); // 이미 파일에 있는 아이디
					existIds.add(id);
				}
				JsonObject obj = gson.fromJson(respBody, JsonObject.class);
				JsonArray docs = obj.getAsJsonArray("documents");
				for(JsonElement doc: docs) { // 두 번째 for문 (입력할 맛집 반복해서 꺼냄)
					String inputId = doc.getAsJsonObject().get("id").toString();
					boolean insertable = true;
					for(String id : existIds) { // 이미 파일에 있는 아이디들과 비교
						if(id.equals(inputId)) {
							insertable = false; // 하나라도 있으면 입력 불가능
						}
					}
					if(insertable) {
						// 입력 진행
						System.out.println("입력함");
						JsonObject place_info = doc.getAsJsonObject();
						JsonObject insert_obj = new JsonObject();
						insert_obj.add(category_name, place_info);
						readArr.add(insert_obj); // 기존 배열에 추가
					}
				}
				// 마지막 페이지인지 판단
				JsonObject meta = obj.getAsJsonObject("meta");
				JsonElement ele = meta.get("is_end");
				readObj.add(list_name, readArr);
				String resp = gson.toJson(readObj); 
				FileWriter fw = new FileWriter(file, false); // false : 새로 입력
				fw.write(resp);
				fw.flush();
				fw.close();
				if(!ele.getAsBoolean()) {continue loop;}
				else{break loop;}
			}
		}
		return respBody;
	}

	@RequestMapping(value="selectMarkerInfo",method=RequestMethod.GET)
	public String selectMarkerInfo(int place_id, HttpServletRequest request) throws Exception{
		int cpage = 1;
		try {cpage = Integer.parseInt(request.getParameter("cpage"));}catch(Exception e) {}
		MapDTO mapdto = mapservice.selectOne(place_id);
		// 진행중인 모임이 있다면 모임도 같이 보내준다.
		int pcount = mapservice.selectPartyOn(place_id);
		List<PartyDTO> plist = pservice.selectByPageNo(cpage, place_id);
		String navi = pservice.getPageNavi(cpage, place_id);
		// request
		request.setAttribute("mapdto", mapdto);
		request.setAttribute("partyCount", pcount);
		request.setAttribute("partyList", plist);
		request.setAttribute("navi", navi);
		String img = pservice.clew(mapdto.getName());
		request.setAttribute("img", img);
		request.setAttribute("markerlat", mapdto.getLat());
		request.setAttribute("markerlng", mapdto.getLng());
		request.setAttribute("parent_seq", mapdto.getSeq());
		Map<ReviewDTO,ReviewFileDTO> rmap = new LinkedHashMap<>();
		// Map 은 순서없이 저장??
		List<ReviewDTO> rlist = rservice.selectByPseq(mapdto.getSeq());
		for(ReviewDTO rdto : rlist) {
			System.out.println(rdto.getSdate());
			ReviewFileDTO rf = rservice.selectFileByPseq(rdto.getSeq());
			rmap.put(rdto, rf);
		}
		request.setAttribute("reviewMap", rmap);
		// 리뷰 사진
		
		return "map/map";
	}

	@ResponseBody
	@RequestMapping("getPartyInfo")
	public PartyDTO getPartyInfo(int seq) throws Exception{
		PartyDTO pdto = pservice.selectBySeq(seq);
		return pdto;
	}


}
