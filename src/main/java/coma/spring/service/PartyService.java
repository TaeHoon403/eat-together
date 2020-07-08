package coma.spring.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coma.spring.dao.PartyDAO;
import coma.spring.dto.PartyDTO;
import coma.spring.dto.PartySearchListDTO;
import coma.spring.statics.Configuration;
import coma.spring.statics.PartyConfiguration;


@Service
public class PartyService {
	
	@Autowired
	private PartyDAO pdao;
	
	// 수지 모임 생성
	public int partyInsert(PartyDTO dto) throws Exception  {
		int seq = pdao.getNextVal();
		String imgaddr = this.clew(dto.getParent_name());
		dto.setSeq(seq);
		dto.setImgaddr(imgaddr);
		pdao.insert(dto);
		return seq;
	}
	// 수지 모임 글 보기 
	public PartyDTO selectBySeq(int seq) throws Exception {
		PartyDTO dto = pdao.selectBySeq(seq); // 읽어오기
		return dto;
	}
	// 수지 모임 삭제
	public int delete(String seq) throws Exception{
		int result=pdao.delete(seq);
		return result;
	}
	// 수지 모임 수정
	public int update(PartyDTO dto) throws Exception{
		int result= pdao.update(dto);
		return result;
	}
	// 태훈 그냥 모임 글 보기
//	public List<PartyDTO> selectList() throws Exception {
//		List<PartyDTO> list = pdao.selectList();
//		return list;
//	}
	// 태훈 모임 리스트
	public List<PartyDTO> selectList(int cpage) throws Exception {
		List<PartyDTO> list = pdao.selectList(cpage);
		return list;
	}
	// 태훈 페이지 네비
	public String getPageNaviTH(int currentPage) throws Exception{
		int recordTotalCount = pdao.getListCount(); 
		int pageTotalCount = 0; 
		if(recordTotalCount % PartyConfiguration.SEARCH_COUNT_PER_PAGE > 0) {
			pageTotalCount = recordTotalCount / PartyConfiguration.SEARCH_COUNT_PER_PAGE + 1;			
		}else {
			pageTotalCount = recordTotalCount / PartyConfiguration.SEARCH_COUNT_PER_PAGE;
		}
		if(currentPage < 1) {
			currentPage = 1;
		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		int startNavi = (currentPage - 1) / PartyConfiguration.NAVI_COUNT_PER_PAGE * PartyConfiguration.NAVI_COUNT_PER_PAGE + 1;
		int endNavi = startNavi + PartyConfiguration.NAVI_COUNT_PER_PAGE - 1;
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		boolean needPrev = true; // <
		boolean needNext = true; // >
		StringBuilder sb = new StringBuilder();
		if(startNavi == 1) {needPrev = false;}
		if(endNavi == pageTotalCount) {needNext = false;}

		if(needPrev) {
			sb.append("<li class='page-item'><a class='page-link' href='partylistBy?cpage="+(startNavi-1)+" aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span> </a></li>");
		}
		else {
			sb.append("<li class='page-item disabled'><a class='page-link' aria-label=\"Previous\"> <span aria-hidden=\"true\">&laquo;</span> </a></li>");
		}
		for(int i = startNavi;i <= endNavi;i++) {
			if(currentPage == i) {
				sb.append("<li class='page-item active' aria-current=\"page\"><span class=\"page-link\">" + i +"<span class=\"sr-only\">(current)</span></span></li>");
			}
			else {
				sb.append("<li class='page-item'><a class='page-link' href=\"partylist?cpage="+i+"\">" + i + "</a></li>");
			}	
		}
		if(needNext) {
			sb.append("<li class='page-item'><a class='page-link' href='partylistBy?cpage="+(endNavi-1)+" aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span> </a></li>");
		}
		else {
			sb.append("<li class='page-item disabled'><a class='page-link' aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span> </a></li>");
		}
		
		return sb.toString();
	}
	// 태훈 모임 글 상세 검색
	public List<PartyDTO> partySearch(PartySearchListDTO pdto) throws Exception{
		List<PartyDTO> list = pdao.partySearch(this.searchKey(pdto));
		return list;
	}
	// 태훈 검색 키워드 가공
	public Map<String, Object> searchKey(PartySearchListDTO pdto) throws Exception{

		Map<String, Object> param = new HashMap<>();

		// 지역 정보
		if(pdto.getSido().equals("시/도 선택")) {
			param.put("address", "");
		}
		else {
			param.put("address",pdto.getSido() + " " + pdto.getGugun());
		}
		// 성별 정보
		param.put("gender",pdto.getGender());
		// 나이 정보
		List<String> ageList = new ArrayList<String>();
		if(pdto.getAge() != null) {
			for(int i=0; i<pdto.getAge().size();i++) {
				ageList.add(Integer.toString(pdto.getAge().get(i)));
			}
		}
		param.put("ageList.size", ageList.size());
		param.put("ageList", ageList);
		// 음주 정보
		param.put("drinking",pdto.getDrinking());
		// 키워드 검색
		String title = "", writer = "", content = "", both = ""; 
		if(pdto.getText().equals("title")) {
			title = pdto.getSearch();

		}
		else if(pdto.getText().equals("writer")){
			writer = pdto.getSearch();
			System.out.println("W"+writer);
		}
		else if(pdto.getText().equals("content")) {
			content = pdto.getSearch();
			System.out.println("C"+content);
		}
		else if(pdto.getText().equals("both")) {
			both = pdto.getSearch();
		}
		param.put("title", title);
		param.put("writer", writer);
		param.put("content", content);
		param.put("both", both);

		return param;
	}
	// 태훈 맛집 top5 장소 아이디 리스트
	public Map<String,String> partyCountById(){
		List<String> list =  pdao.partyCountById();
		Map<String, String> param = new HashMap<>();
		for (int i=0; i<5; i++) {
			param.put("top"+(i+1),list.get(i));
		}
		return param;
	}
	// 예지 장소 아이디 별 모임 리스트
	public List<PartyDTO> selectByPageNo(int cpage, int place_id) throws Exception{
		return pdao.selectByPageNo(cpage, place_id);
	}
	// 예지 페이지 네비
	public String getPageNavi(int currentPage, int place_id) throws Exception{
		int recordTotalCount = pdao.getArticleCount(place_id); 
		int pageTotalCount = 0; 
		if(recordTotalCount % PartyConfiguration.RECORD_COUNT_PER_PAGE > 0) {
			pageTotalCount = recordTotalCount / PartyConfiguration.RECORD_COUNT_PER_PAGE + 1;			
		}else {
			pageTotalCount = recordTotalCount / PartyConfiguration.RECORD_COUNT_PER_PAGE;
		}
		if(currentPage < 1) {
			currentPage = 1;
		}else if(currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}
		int startNavi = (currentPage - 1) / PartyConfiguration.NAVI_COUNT_PER_PAGE * PartyConfiguration.NAVI_COUNT_PER_PAGE + 1;
		int endNavi = startNavi + PartyConfiguration.NAVI_COUNT_PER_PAGE - 1;
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		boolean needPrev = true; // <
		boolean needNext = true; // >
		if(startNavi == 1) {needPrev = false;}
		if(endNavi == pageTotalCount) {needNext = false;}

		StringBuilder sb = new StringBuilder();
		if(needPrev) {sb.append("<li class='page-item'><a class='page-link' href='selectMarkerInfo?cpage="+(startNavi-1)+"' tabindex='-1' aria-disabled='true'><i class=\"fas fa-chevron-left\"></i> </a></li>");}
		for(int i = startNavi;i <= endNavi;i++) {
			sb.append("<li class='page-item'><a class='page-link' href='selectMarkerInfo?cpage="+i+"&place_id="+place_id+"'>" + i + "</a></li>");
		}
		if(needNext) {sb.append("<li class='page-item'><a class='page-link' href='selectMarkerInfo?cpage="+(endNavi+1)+"'><i class=\"fas fa-chevron-right\"></i></a></li>");}
		return sb.toString();
	}
	// 예지 잘 모르겠음
	public int stopRecruit(String seq) throws Exception {
		return pdao.stopRecruit(seq);
	}
	// 예지 모임 전체 갯수
	public int selectAllCount() throws Exception{
		return pdao.selectAllCount();
	}
	// 지은 작성자 별 모임 리스트
	public List<PartyDTO> selectByWriterPage(String writer, int mcpage)throws Exception{
		int start = mcpage * Configuration.recordCountPerPage-(Configuration.recordCountPerPage-1);
		int end = start + (Configuration.recordCountPerPage-1);

		Map<String, Object> param = new HashMap<>();
		param.put("start", start);
		param.put("end", end);
		param.put("writer", writer);

		List<PartyDTO> partyList = pdao.selectByWriterPage(param);
		return partyList;
	}
	// 지은 페이지 네비
	public String getMyPageNav(int mcpage, String writer) throws Exception{
		int recordTotalCount = pdao.getMyPageArticleCount(writer); // 총 개시물의 개수
		int pageTotalCount = 0; // 전체 페이지의 개수

		if( recordTotalCount % Configuration.recordCountPerPage > 0) {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage +1;
		}else {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage;
		}

		if(mcpage < 1) {
			mcpage = 1;
		}else if(mcpage > pageTotalCount){
			mcpage = pageTotalCount;
		}

		int startNav = (mcpage-1)/Configuration.navCountPerPage * Configuration.navCountPerPage + 1;
		int endNav = startNav + Configuration.navCountPerPage - 1;
		if(endNav > pageTotalCount) {
			endNav = pageTotalCount;
		}

		boolean needPrev = true;
		boolean needNext = true;

		if(startNav == 1) {
			needPrev = false;
		}
		if(endNav == pageTotalCount) {
			needNext = false;
		}

		StringBuilder sb = new StringBuilder("<nav aria-label='Page navigation'><ul class='pagination justify-content-center'>");

		if(needPrev) {
			sb.append("<li class='page-item'><a class='page-link' href='/party/selectByWriter?mcpage="+(startNav-1)+"' id='prevPage' tabindex='-1' aria-disabled='true'>Previous</a></li>");
		}

		for(int i=startNav; i<=endNav; i++) {  
			if(mcpage == i) {
				sb.append("<li class='page-item active' aria-current='page'><a class='page-link' href='/party/selectByWriter?mcpage="+i+"'>"+i+"<span class=sr-only>(current)</span></a></li>");
				//sb.append("<li class='page-item active' aria-current='page'>"+i+"<span class='sr-only'>(current)</span></li>");
			}else {
				sb.append("<li class='page-item'><a class='page-link' href='/party/selectByWriter?mcpage="+i+"'>"+i+"</a></li>");
			}
		}

		if(needNext) {
			sb.append("<li class=page-item><a class=page-link href='/party/selectByWriter?mcpage="+(endNav+1)+"' id='nextPage'>다음</a></li> ");
		}		
		sb.append("</ul></nav>");
		return sb.toString();
	}
	// 이지미 클롤링
	public String clew(String str) throws Exception {
		Document doc = Jsoup.connect("https://m.map.kakao.com/actions/searchView?q="+str).get();
		Element linkTag = doc.selectFirst("ul#placeList>li>a>span");
		String img = linkTag.html();
		if(img.contains("fname=")) {
			return img.split("fname=")[1].split("\"")[0];
		}
		else {
			// 이미지 소스 없는 가게 에러 해결 위해 추가 - 태훈
			return "https://tpc.googlesyndication.com/simgad/11554535643826380039?sqp=4sqPyQQ7QjkqNxABHQAAtEIgASgBMAk4A0DwkwlYAWBfcAKAAQGIAQGdAQAAgD-oAQGwAYCt4gS4AV_FAS2ynT4&rs=AOga4qnk_Y1zzDS1b6Wu1KYZ-_e0LjecDg";
		}
	}

}
