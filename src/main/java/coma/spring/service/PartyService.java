package coma.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coma.spring.dao.PartyDAO;
import coma.spring.dto.PartyDTO;

@Service
public class PartyService {
	@Autowired
	private PartyDAO pdao;
	
	public int partyInsert(PartyDTO dto) {
		int seq = pdao.getNextVal();
		dto.setSeq(seq);
		pdao.insert(dto);
		return seq;
	}
	
	
	public PartyDTO selectBySeq(int seq) throws Exception {
		PartyDTO dto = pdao.selectBySeq(seq); // 읽어오기
		return dto;
	}
	

	public int delete(String seq) throws Exception{
		int result=pdao.delete(seq);
		return result;
	}
	
	public int update(PartyDTO dto) throws Exception{
		int result= pdao.update(dto);
		return result;
	}
}
