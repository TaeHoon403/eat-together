package coma.spring.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import coma.spring.dto.MapDTO;

@Repository
public class MapDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	public int insert(MapDTO mdto) throws Exception{
		return mybatis.insert("Map.insert",mdto);
	}
	public boolean insertPossible(String place_url) throws Exception{
		int result = mybatis.selectOne("Map.insertPossible",place_url);
		if(result > 0) return false;
		else return true;
	}
	public List<MapDTO> selectAll() throws Exception{
		return mybatis.selectList("Map.selectAll");
	}
	
	public List<MapDTO> searchByPlace_id(int place_id) throws Exception{
		return mybatis.selectList("Map.searchByPlace_id", place_id);
	}
	
	public int selectPartyOn(int place_id) throws Exception{
		return mybatis.selectOne("Map.selectPartyOn",place_id);
	}
	
	public MapDTO selectOne(int place_id) throws Exception{
		return mybatis.selectOne("Map.selectOne",place_id);
	}
	
	public List<MapDTO> searchByKeyword(String keyword) throws Exception{
		return mybatis.selectList("Map.searchByKeyword",keyword);
	}
	
	public List<MapDTO> searchByCategory(String category) throws Exception{
		return mybatis.selectList("Map.searchByCategory",category);
	}
	// 태훈 순위 맛집 찾기
	public List<MapDTO> selectTopStore(Map<String, String> param){	
		return mybatis.selectList("Map.topStore", param);
	}
}
