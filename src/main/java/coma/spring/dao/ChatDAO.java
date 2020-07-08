package coma.spring.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import coma.spring.dto.ChatDTO;
import coma.spring.dto.PartyDTO;

@Repository
public class ChatDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// roomNum 방번호의 채팅 내용을 전부 가져옴
	public List<ChatDTO> selectChats(int roomNum){
		return mybatis.selectList("selectChats", roomNum);
	}
	public int insertChatRoom(PartyDTO pdto){
		return mybatis.insert("insertChatRoom" , pdto);
	}

}
