package coma.spring.scheduler;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import coma.spring.dto.ChatDTO;
import coma.spring.statics.ChatStatics;

@EnableScheduling
@Component
public class Scheduler {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	@Scheduled(fixedDelay = 10000)
	public void scheduleTest() {
		ChatStatics.savedChat = !ChatStatics.savedChat;
		long start = System.currentTimeMillis();
		if(!ChatStatics.chats1.isEmpty() && !ChatStatics.savedChat) {
			mybatis.insert("Chats.insertChat",ChatStatics.chats1);
			for(ChatDTO dto : ChatStatics.chats1) {
				ChatStatics.savedChats.get(dto.getChatSeq_parent()).add(dto);
			}
			ChatStatics.chats1.clear();
		}else if(!ChatStatics.chats2.isEmpty() && ChatStatics.savedChat) {
			mybatis.insert("Chats.insertChat",ChatStatics.chats2);
			for(ChatDTO dto : ChatStatics.chats2) {
				ChatStatics.savedChats.get(dto.getChatSeq_parent()).add(dto);
			}
			ChatStatics.chats2.clear();			
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}