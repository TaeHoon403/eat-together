package coma.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coma.spring.dao.ChatDAO;
import coma.spring.dto.ChatDTO;
import coma.spring.dto.PartyDTO;
import coma.spring.statics.ChatStatics;

@Service
public class ChatService {
	@Autowired
	ChatDAO cdao;
	
	// ChatStatic에
	public void savedChat(int roomNum) {
		List<ChatDTO> list = cdao.selectChats(roomNum);	
		ChatStatics.savedChats.put(roomNum, list);
		ChatStatics.savedChatsSeq.put(roomNum, list.size());		
	}
	public int insertChatRoom(PartyDTO pdto) {
		return cdao.insertChatRoom(pdto);
	}
	
}
