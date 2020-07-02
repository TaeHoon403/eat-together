package coma.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coma.spring.dao.MapDAO;
import coma.spring.dto.MapDTO;

@Service
public class MapService {
	@Autowired
	private MapDAO mdao;
	public int insert(MapDTO mdto) throws Exception{
		return mdao.insert(mdto);
	}
	public boolean insertPossible(String place_url) throws Exception{
		return mdao.insertPossible(place_url);
	}
	public List<MapDTO> selectAll() throws Exception{
		return mdao.selectAll();
	}
	public List<MapDTO> searchByKeyword(int place_id) throws Exception{
		return mdao.searchByKeyword(place_id);
	}
}
