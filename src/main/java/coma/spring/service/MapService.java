package coma.spring.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coma.spring.dao.MapDAO;
import coma.spring.dto.MapDTO;

@Service
public class MapService {
	@Autowired
	private MapDAO mapdao;
	public int insert(MapDTO mdto) throws Exception{
		return mapdao.insert(mdto);
	}
	public boolean insertPossible(String place_url) throws Exception{
		return mapdao.insertPossible(place_url);
	}
	public List<MapDTO> selectAll() throws Exception{
		return mapdao.selectAll();
	}
	public List<MapDTO> searchByPlace_id(int place_id) throws Exception{
		return mapdao.searchByPlace_id(place_id);
	}
	public int selectPartyOn(int place_id) throws Exception{
		return mapdao.selectPartyOn(place_id);
	}
	public MapDTO selectOne(int place_id) throws Exception{
		return mapdao.selectOne(place_id);
	}
	public List<MapDTO> searchByKeyword(String keyword) throws Exception{
		return mapdao.searchByKeyword(keyword);
	}
	public List<MapDTO> searchByCategory(String category) throws Exception{
		return mapdao.searchByCategory(category);
	}
	public List<MapDTO> selectTopStroe(Map<String, String> param) throws Exception{
		return mapdao.selectTopStore(param);
	}
}
