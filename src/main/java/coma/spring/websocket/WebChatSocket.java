package coma.spring.websocket;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import coma.spring.config.HttpSessionConfigurator;
import coma.spring.dto.ChatDTO;
import coma.spring.dto.MemberDTO;
import coma.spring.statics.ChatStatics;

@ServerEndpoint(value="/chat", configurator = HttpSessionConfigurator.class)
public class WebChatSocket {
	// clients : 현재 접속한 세션이 어떤 방에 접속중인지 방 번호를 저장 
	private static Map<Session , Integer> clients = Collections.synchronizedMap(new HashMap<>());
	// memebers : 방번호마다 현재 접속하고 있는 멤버의 목록을 저장  
	private static Map<Integer , List<Session>> members = Collections.synchronizedMap(new HashMap<>());
	// 세션값
	private HttpSession session;
	// 로그인 정보,들어온 방의 번호 : 세션값을 통해 가져옴
	private int roomNum;
	private MemberDTO mdto;

	// 채팅방 접속시
	@OnOpen
	public void onConnect(Session client , EndpointConfig config) throws Exception {
		// 접속시 세션값 저장
		this.session = (HttpSession)config.getUserProperties().get("session");
		// 세션의 로그인 정보를 저장, 방 번호를 저장한 후 방번호의 세션값 삭제
		mdto = (MemberDTO)this.session.getAttribute("loginInfo");
		roomNum = (int)this.session.getAttribute("roomNum");
		this.session.removeAttribute("roomNum");

		//clients에 세션정보와 방의 번호를 저장
		clients.put(client , roomNum);

		// members 내에 roomNum 방번호가 존재하면 방을 만들지 않음, 존재하지 않으면 방을 만듬
		boolean memberExist = true;
		for(int i : members.keySet()) {
			if(i == roomNum) {memberExist = false;break;}
		}
		if(memberExist) {members.put(roomNum, new ArrayList<>());}

		// members 맵에 해당 방번호 list에 세션정보를 추가한다 
		members.get(roomNum).add(client);

		// 이전 채팅내용을 가져오기 위해 basic 선언
		Basic basic = client.getBasicRemote();

		//basic.sendText("hereasd:여거까지일그서씁니다");
		// 채팅 내용을 가져오기
		for(ChatDTO d : ChatStatics.savedChats.get(roomNum)) {
			if(mdto.getNickname().contentEquals(d.getWriter())) {
				basic.sendText("나:" +d.getContent());
			}else {
				basic.sendText(d.getWriter() + ":" +d.getContent());
			}

		}
		// 현재 서버에 저장된 채팅 가져오기
		List<ChatDTO> list = (ChatStatics.savedChat ? ChatStatics.chats1 : ChatStatics.chats2);
		for(ChatDTO d : list) {
			// 방번호에 맞는 채팅만 가져오기
			if(d.getChatSeq_parent() == roomNum) {
				if(mdto.getNickname().contentEquals(d.getWriter())) {
					basic.sendText("나:" +d.getContent());
				}else {
					basic.sendText(d.getWriter() + ":" +d.getContent());
				}
			}
		}
	}

	//메세지를 보낼 시
	@OnMessage
	public void onMessage(Session session , String message) {
		// 채팅 저장변수 선언 (방번호,채팅SEQ,메세지,닉네임,현재시간,조회수)
		ChatDTO c = new ChatDTO(roomNum,
				ChatStatics.savedChatsSeq.get(roomNum),
				message ,
				mdto.getNickname() ,
				new Timestamp(System.currentTimeMillis()) ,
				0);

		// 서버 저장 맵에 해당 채팅 저장
		if(ChatStatics.savedChat) {ChatStatics.chats1.add(c);}
		else					 {ChatStatics.chats2.add(c);}

		// 해당 채팅의 seq 넘버 증가
		ChatStatics.savedChatsSeq.put(roomNum, ChatStatics.savedChatsSeq.get(roomNum)+1);
		//members 필드 내에 roomNum번호의 리스트의 세션들에서 메세지를 보냄
		synchronized (members.get(roomNum)) {
			for(Session client : members.get(roomNum)) {
				System.out.println(members.get(roomNum).size());
				if(clients.get(client)==roomNum) {
					Basic basic = client.getBasicRemote();
					try {
						if(client.equals(session)) {
							basic.sendText("나:" + message);
						}else {
							basic.sendText(mdto.getNickname() + ":" +message);
						}
					}catch(Exception e) {	e.printStackTrace();}
				}
			}
		}	
	}
	//채팅창 닫을시
	@OnClose
	public void onClose(Session session) {
		//member와 client의 정보를 모두 삭제
		members.get(roomNum).remove(session);
		clients.remove(session);
	}
	//에러 발생시
	@OnError
	public void onError(Session session, Throwable t) {
		//member와 client의 정보를 모두 삭제
		members.get(roomNum).remove(session);
		clients.remove(session);
	}
}
