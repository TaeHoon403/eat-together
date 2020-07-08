package coma.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coma.spring.dao.MsgDAO;
import coma.spring.dto.MsgDTO;



@Service
public class MsgService {
	@Autowired
	private MsgDAO msgdao;

	//받은쪽지함
	public List<MsgDTO> selectBySender(int cpage,String msg_receiver) throws Exception{
		List<MsgDTO> dto = msgdao.selectBySender(cpage,msg_receiver);
		return dto;
	}
	//보낸쪽지함
	public List<MsgDTO> selectByReceiver(int cpage,String msg_receiver) throws Exception{
		List<MsgDTO> dto = msgdao.selectByReceiver(cpage,msg_receiver);
		return dto;
	}
	//관리자쪽지함
	public List<MsgDTO> selectByAdmin(int cpage,String msg_receiver) throws Exception{
		List<MsgDTO> dto = msgdao.selectByAdmin(cpage,msg_receiver);
		return dto;
	}
	//쪽지 보기
	public MsgDTO selectBySeq(int msg_seq)throws Exception{
		MsgDTO msgdto = msgdao.selectBySeq(msg_seq);
		return msgdto;
	}
	//쪽지보내기
	public int insert(MsgDTO msgdto)throws Exception {
		int result = msgdao.insert(msgdto);
		return result;
	}
	//가입축하쪽지
	public int insertWelcome(String msg_receiver)throws Exception{
		int result = msgdao.insertWelcome(msg_receiver);
		return result;
	}
	//쪽지읽음처리
	public int updateView(int msg_seq)throws Exception{
		int result = msgdao.updateView(msg_seq);
		return result;
	}
	//받은사람삭제
	public int receiver_del(int msg_seq)throws Exception{
		int result = msgdao.receiver_del(msg_seq);
		return result;
	}
	//보낸사람삭제
	public int sender_del(int msg_seq)throws Exception{
		int result =msgdao.sender_del(msg_seq);
		return result;
	}
	//새로운쪽지
	public int newmsg(String msg_receiver)throws Exception{
		int result = msgdao.newmsg(msg_receiver);
		return result;
	}

	//관리자 쪽지함 네비
	public String Adminnavi (int cpage,String msg_receiver) throws Exception{
		String navi = msgdao.getAdminPageNav(cpage,msg_receiver);
		return navi;
	}
	//네비
	public String Sendnavi (int cpage,String msg_receiver) throws Exception{
		String navi = msgdao.getSenderPageNav(cpage,msg_receiver);
		return navi;
	}
	//네비
	public String Receivenavi (int cpage,String msg_receiver) throws Exception{
		String navi = msgdao.getReceiverPageNav(cpage,msg_receiver);
		return navi;
	}
}
