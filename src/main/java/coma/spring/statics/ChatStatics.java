package coma.spring.statics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import coma.spring.dto.ChatDTO;
public class ChatStatics {
	public static Map<Integer , List<ChatDTO>> savedChats = Collections.synchronizedMap(new HashMap<>());
	public static Map<Integer , Integer> savedChatsSeq = Collections.synchronizedMap(new HashMap<>());
	public static List<ChatDTO> chats1 = Collections.synchronizedList(new ArrayList<>());
	public static List<ChatDTO> chats2 = Collections.synchronizedList(new ArrayList<>());
	public static boolean savedChat = true;		
	
}
