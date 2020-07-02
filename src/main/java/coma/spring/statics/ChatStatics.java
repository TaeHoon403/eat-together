package coma.spring.statics;

import java.util.ArrayList;
import java.util.List;

import coma.spring.dto.ChatDTO;

public class ChatStatics {
	public static List<ChatDTO> loaded = new ArrayList<>();
	public static List<ChatDTO> chats1 = new ArrayList<>();
	public static List<ChatDTO> chats2 = new ArrayList<>();
	public static int seq;	
	public static boolean savedChat = true;	
	public static boolean chatLoaded = true;	

}
