package coma.spring.dao;

import java.util.List;

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
	
	public List<MapDTO> searchByKeyword(int place_id) throws Exception{
		return mybatis.selectList("Map.searchByKeyword", place_id);
	}
}
