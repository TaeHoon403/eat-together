package coma.spring.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import coma.spring.dto.PartyDTO;
import coma.spring.statics.PartyConfiguration;


@Repository
public class PartyDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 수지 모임 다음 시퀀스
	public int getNextVal() {
		return mybatis.selectOne("Party.getNextval");
	}
	// 수지 모임 생성
	public int insert(PartyDTO dto) {
		return mybatis.insert("Party.insert",dto);
	}
	// 수지 모임 삭제
	public int delete(String seq) throws Exception{
		return mybatis.delete("Party.delete",seq);
	}
	// 수지 모임 내용
	public PartyDTO selectBySeq(int seq) throws Exception{
		return mybatis.selectOne("Party.selectBySeq",seq);
	} 
	// 수지 모임 수정
	public int update(PartyDTO dto) throws Exception{
		//		 Map<String, String> param = new HashMap<>();
		//	      param.put("columnName1", "title");
		//	      param.put("changeValue1", dto.getTitle());
		//	      param.put("columnName2", "contents");
		//	      param.put("changeValue2", dto.getContents());
		//	      param.put("targetColumn", "seq");
		//	      param.put("targetValue", Integer.toString(dto.getSeq()));
		//	      
		//	      System.out.println(param.size());
		//	      
		//		return mybatis.update("Board.update",param);

		return mybatis.update("Party.update",dto);
	}
	// 예지 장소 아이디 별 모임 갯수
	public int getArticleCount(int place_id) throws Exception{
		return mybatis.selectOne("Party.getArticleCount", place_id);
	}
	// 예지 장소 아이디 별 모임 리스트
	public List<PartyDTO> selectByPageNo(int cpage, int place_id) throws Exception{
		int start = cpage * PartyConfiguration.RECORD_COUNT_PER_PAGE - (PartyConfiguration.RECORD_COUNT_PER_PAGE-1);
		int end = start + (PartyConfiguration.RECORD_COUNT_PER_PAGE-1);
		Map<String, Integer> param = new HashMap<>();
		param.put("start", start);
		param.put("end", end);
		param.put("place_id", place_id);
		return mybatis.selectList("Party.selectByPageNo",param);
	}
	// 예지 잘 모르겠음
	public int stopRecruit(String seq) throws Exception {
		return mybatis.update("Party.stopRecruit",seq);
	}
	// 예지 모임 전체 갯수
	public int selectAllCount() throws Exception{
		return mybatis.selectOne("Party.selectAllCount");
	}
	// 태훈 모임 리스트 뽑기
//	public List<PartyDTO> selectList() {
//		return mybatis.selectList("Party.selectList");
//	}
	// 태훈 모임 리스트 뽑기 네비 포함
	public List<PartyDTO> selectList(int cpage) {
		int start = cpage * PartyConfiguration.SEARCH_COUNT_PER_PAGE - (PartyConfiguration.SEARCH_COUNT_PER_PAGE-1);
		int end = start + (PartyConfiguration.SEARCH_COUNT_PER_PAGE-1);
		Map<String, Integer> param = new HashMap<>();
		param.put("start", start);
		param.put("end", end);
		return mybatis.selectList("Party.selectList" ,param);
	}
	// 태훈 열려있는 모임 전체 갯수
	public int getListCount() {
		return mybatis.selectOne("Party.getListCount");
	}
	// 태훈 모임 통합 검색
	public List<PartyDTO> partySearch(Map<String, Object> param){	
		return mybatis.selectList("Party.partySearch", param);
	}
	// 태훈 모임 순위순 장소 아이디 리스트
	public List<String> partyCountById() {
		return mybatis.selectList("Party.PartyCountById");
	}
	// 지은 작성자 별 모임 리스트
	public List<PartyDTO> selectByWriterPage(Map<String, Object> param)throws Exception{
		return mybatis.selectList("Party.selectByWriterPage", param);
	}
	// 지은 작성자 별 모임 갯수
	public int getMyPageArticleCount(String writer) throws Exception{
		return mybatis.selectOne("Party.getMyPageArticleCount", writer);
	}

}
