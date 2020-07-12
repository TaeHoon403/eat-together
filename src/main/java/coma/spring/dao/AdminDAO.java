package coma.spring.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import coma.spring.dto.MemberDTO;




@Repository
public class AdminDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	//by 지은, 회원정보 리스트 출력하기_20200712
	public List<MemberDTO> memberList(Map<String, Integer> param){
		return mybatis.selectList("Admin.memberList", param);
	}
	
	public int getAllMemberCount() {
		return mybatis.selectOne("Admin.getAllMemberCount");
	}
	
	//by 지은, 회원정보 조건검색 select 문 연결_20200712
	public List<MemberDTO> selectByOption(Map<String, Object> param){
		return mybatis.selectList("Admin.selectByOption", param);
	}
	
}