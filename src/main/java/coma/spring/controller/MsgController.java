package coma.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import coma.spring.dto.MemberDTO;
import coma.spring.dto.MsgDTO;
import coma.spring.service.MsgService;

@Controller
@RequestMapping("/msg/")
public class MsgController {
	//@Autowired
	//private MsgDAO msgdao;
	
	@Autowired
	private MsgService msgservice;
	
	@Autowired
	private HttpSession session;
	
	//받은 쪽지함
	@ExceptionHandler
	public String exceptionHandler(NullPointerException npe) {
		npe.printStackTrace();
		System.out.println("NullPointerException Handler : 에러가 발생하였습니다.");
		return "member/loginview";
	}
	
	
	@RequestMapping("msg_list_sender")
	public String msglist_sender(HttpServletRequest request)throws Exception{
		MemberDTO mdto = (MemberDTO)session.getAttribute("loginInfo");
		String msg_receiver = mdto.getId();
		System.out.println(msg_receiver+"의 받은 쪽지함");
		int newMsg=msgservice.newmsg(msg_receiver);
		
		if(session.getAttribute("msgcpage")==null) {
			session.setAttribute("msgcpage", 1);
		}
		try { 
			session.setAttribute("msgcpage", Integer.parseInt(request.getParameter("msgcpage")));
		} catch (Exception e) {}
		int msgcpage=(int)session.getAttribute("msgcpage");
		List<MsgDTO> dto = msgservice.selectBySender(msgcpage,msg_receiver);
		String navi = msgservice.Sendnavi(msgcpage,msg_receiver);
		request.setAttribute("newMsg", newMsg);
		request.setAttribute("navi", navi);
		request.setAttribute("list", dto);
		return "msg/mypage_sendmsg";
	}
	//관리자
	
	@RequestMapping("msg_list_admin")
	public String msglist_admin(HttpServletRequest request)throws Exception{
		MemberDTO mdto = (MemberDTO)session.getAttribute("loginInfo");
		String msg_receiver = mdto.getId();
		System.out.println(msg_receiver+"의 관리자 쪽지함");
		
		if(session.getAttribute("msgcpage")==null) {
			session.setAttribute("msgcpage", 1);
		}
		try { 
			session.setAttribute("msgcpage", Integer.parseInt(request.getParameter("msgcpage")));
		} catch (Exception e) {}
		int msgcpage=(int)session.getAttribute("msgcpage");

		List<MsgDTO> dto = msgservice.selectByAdmin(msgcpage,msg_receiver);
		String navi = msgservice.Adminnavi(msgcpage, msg_receiver);
		
		request.setAttribute("navi", navi);
		request.setAttribute("list", dto);
		
		return "msg/mypage_sendmsg";
	}
	//보낸쪽지함
	
	@RequestMapping("msg_list_receiver")
	public String msglist_receiver(HttpServletRequest request)throws Exception{
		MemberDTO mdto = (MemberDTO)session.getAttribute("loginInfo");
		String msg_receiver = mdto.getId();
		System.out.println(msg_receiver+"의 보낸 쪽지함");
		
		if(session.getAttribute("msgcpage")==null) {
			session.setAttribute("msgcpage", 1);
		}
		try { 
			session.setAttribute("msgcpage", Integer.parseInt(request.getParameter("msgcpage")));
		} catch (Exception e) {}
		
		int msgcpage=(int)session.getAttribute("msgcpage");
		
		List<MsgDTO> dto = msgservice.selectByReceiver(msgcpage,msg_receiver);
		String navi =msgservice.Receivenavi(msgcpage, msg_receiver);
		
		request.setAttribute("navi", navi);
		request.setAttribute("list", dto);
		return "msg/mypage_receivemsg";
	}
	
	@RequestMapping("msgWrite")
	public String msgWrite() {
		return "msg/msgWrite";
	}
	
	@RequestMapping("msgSend")
	public String msgSend(MsgDTO msgdto)throws Exception{
		MemberDTO mdto = (MemberDTO)session.getAttribute("loginInfo");
		String msg_sender=mdto.getId();
		msgdto.setMsg_sender(msg_sender);
		//메세지 보낸사람 넣기
		int result = msgservice.insert(msgdto);
		return "msg/msgWriteResult";
	}
	
	//받은쪽지함에서 눌러볼때
	@RequestMapping("msgView")
	public String msgView(HttpServletRequest request,int msg_seq)throws Exception{
		
		MsgDTO msgDTO = msgservice.selectBySeq(msg_seq);

		//읽음처리되는것
		int result = msgservice.updateView(msg_seq);

		request.setAttribute("msgView", msgDTO);
		return "msg/msgView";
	}
	//보낸쪽지함에서 눌러볼때
	@RequestMapping("msgViewSend")
	public String msgViewSend(HttpServletRequest request,int msg_seq)throws Exception{
		
		MsgDTO msgDTO = msgservice.selectBySeq(msg_seq);
		request.setAttribute("msgView", msgDTO);
		return "msg/msgView";
	}
	
	//받은쪽지함 삭제
	
	@RequestMapping("msgReceiverDel")
	public String ReceiverDel(int msg_seq)throws Exception{
		int result = msgservice.receiver_del(msg_seq);
		
		return "redirect:msg_list_sender";
	}
	
	//보낸쪽지함 삭제
	@RequestMapping("msgSenderDel")
	public String SenderDel(int msg_seq)throws Exception{
		System.out.println(msg_seq);
		int result = msgservice.sender_del(msg_seq);

		return "redirect:msg_list_receiver";
	}
}

