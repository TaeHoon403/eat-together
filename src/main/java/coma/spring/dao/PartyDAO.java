package coma.spring.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import coma.spring.dto.PartyDTO;

@Repository
public class PartyDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int getNextVal() {
		return mybatis.selectOne("Party.getNextval");
	}
	
	public int insert(PartyDTO dto) {
		return mybatis.insert("Party.insert",dto);
	}
	
	public int delete(String seq) throws Exception{
		return mybatis.delete("Party.delete",seq);
	}
	
	public PartyDTO selectBySeq(int seq) throws Exception{
		return mybatis.selectOne("Party.selectBySeq",seq);
	} 
	
	public int update(PartyDTO dto) throws Exception{
  		return mybatis.update("Party.update",dto);
	}
	
}
