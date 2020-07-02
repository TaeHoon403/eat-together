package coma.spring.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

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
import coma.spring.service.MapService;

@Controller
@RequestMapping("/map/")
public class MapController {

	private static final String HOST = "https://dapi.kakao.com";

	@Autowired
	private MapService mservice;

	@Autowired
	private ServletContext sc;

	@RequestMapping("toMap")
	public String map(HttpServletRequest request) throws Exception{
		// 등록된 맛집 뿌려주기
		List<MapDTO> list = mservice.selectAll();
		Gson gson = new Gson();
		String object = gson.toJson(list);
		request.setAttribute("json", object);
		String jsonPath = sc.getRealPath("resources/json/mapData.json");
		//		InputStream jsonStream = new FileInputStream(jsonPath);
		File file = new File(jsonPath);
		FileWriter fw = new FileWriter(file, false);

		fw.write(object);
		fw.flush();
		fw.close();
		return "/map/map";
	}
	@RequestMapping("insert")
	public String insert(MapDTO mdto, String detail_category) throws Exception {
		// 판단
		if(!mservice.insertPossible(mdto.getPlace_url())) {
			return "/map/insertFail";
		}else {
			System.out.println(mdto.getName() + " : " + 
					mdto.getAddress() + " : " + 
					mdto.getRoad_address() + " : " +
					mdto.getCategory() + " : " + 
					mdto.getLat() + " : " + 
					mdto.getLng() + " : " +
					mdto.getRating_avg() + " : " +
					mdto.getPhone() + " : " + 
					mdto.getPlace_url() + " : " + 
					mdto.getPlace_id());
			mservice.insert(mdto);
			return "redirect:/map/toMap";
		}
	}

	@ResponseBody
	@RequestMapping(value="food",produces="application/json;charset=utf8",method=RequestMethod.GET)
	public String getFood(String lng, String lat) throws RestClientException, URISyntaxException{
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("x", lng);
		params.add("y", lat);
		params.add("category_group_code", "FD6");
		params.add("radius", "20000");
		params.add("page", "1");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + "e156322dd35cfd9dc276f1365621ae9a");
		headers.add("Accept",MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charser=UTF-8");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		String respBody = restTemplate.postForObject(new URI(HOST + "/v2/local/search/category.json"), request, String.class);
		System.out.println(respBody);
		return respBody;
	}
	@ResponseBody
	@RequestMapping(value="cafe",produces="application/json;charset=utf8",method=RequestMethod.GET)
	public String getCafe(String lng, String lat) throws RestClientException, URISyntaxException{
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("x", lng);
		params.add("y", lat);
		params.add("category_group_code", "CE7");
		params.add("radius", "20000");
		params.add("page", "1");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + "e156322dd35cfd9dc276f1365621ae9a");
		headers.add("Accept",MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charser=UTF-8");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
		String respBody = restTemplate.postForObject(new URI(HOST + "/v2/local/search/category.json"), request, String.class);
		System.out.println(respBody);
		return respBody;
	}

	@RequestMapping(value="search",method=RequestMethod.GET)
	public String searchByKeyword(String keyword, HttpServletRequest req) throws Exception{
//		RestTemplate restTemplate = new RestTemplate();
//		List<MapDTO> search_list = new ArrayList<>();
//
//		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
//		params.add("query", keyword);
//		params.add("page", 1);
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Authorization", "KakaoAK " + "e156322dd35cfd9dc276f1365621ae9a");
//		headers.add("Accept",MediaType.APPLICATION_JSON_UTF8_VALUE);
//		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charser=UTF-8");		
//
//		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
//		String respBody = restTemplate.postForObject(new URI(HOST + "/v2/local/search/keyword.json"), request, String.class);
//
//		Gson gson = new Gson();
//		JsonObject obj = gson.fromJson(respBody, JsonObject.class);
//		JsonArray docs = (JsonArray)obj.get("documents");
//
//		for(JsonElement doc : docs) {
//			JsonObject docobj = doc.getAsJsonObject(); 
//			int place_id = docobj.get("id").getAsInt();
//			List<MapDTO> list = mservice.searchByKeyword(place_id);
//			if(list != null) {
//				for(MapDTO search_result : list) {
//					search_list.add(search_result);
//					System.out.println(search_result.getName());
//				}
//			}
//		}
//		req.setAttribute("search_list", search_list);
		return "redirect:/map/toMap";
	}

	@ResponseBody
	@RequestMapping(value="cafeInsert",produces="application/json;charset=utf8",method=RequestMethod.GET)
	public String insertKakaoCafeData(String lng, String lat) throws Exception {
		return insertJson("resources/json/KakaoCafe.json","CE7",lng,lat);
	}

	@ResponseBody
	@RequestMapping(value="foodInsert",produces="application/json;charset=utf8",method=RequestMethod.GET)
	public String insertKakaoFoodData(String lng, String lat) throws Exception {
		return insertJson("resources/json/KakaoFood.json","FD6",lng,lat);
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
				System.out.println("읽어 들인 음식점 : " + readObj);
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


}
