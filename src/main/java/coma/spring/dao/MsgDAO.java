package coma.spring.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import coma.spring.dto.MsgDTO;
import coma.spring.statics.Configuration;

@Repository
public class MsgDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	
	
	//받은쪽지함
	public List<MsgDTO> selectBySender(int cpage,String msg_receiver) throws Exception{
		int start = cpage*Configuration.recordMsgCountPerPage - (Configuration.recordMsgCountPerPage - 1);
		int end = start + (Configuration.recordMsgCountPerPage - 1);
		
		Map<String,String> param = new HashMap<>();
		param.put("start",String.valueOf(start));
		param.put("end",String.valueOf(end));
		param.put("msg_receiver",msg_receiver);
		
		return mybatis.selectList("msg.selectBySender",param);
	}
	//보낸쪽지함
	public List<MsgDTO> selectByReceiver(int cpage,String msg_receiver) throws Exception{
		int start = cpage*Configuration.recordMsgCountPerPage - (Configuration.recordMsgCountPerPage - 1);
		int end = start + (Configuration.recordMsgCountPerPage - 1);
		
		Map<String,String> param = new HashMap<>();
		param.put("start",String.valueOf(start));
		param.put("end",String.valueOf(end));
		param.put("msg_receiver",msg_receiver);
		
		return mybatis.selectList("msg.selectByReceiver",param);
	}
	//관리자쪽지함
	public List<MsgDTO> selectByAdmin(int cpage,String msg_receiver) throws Exception{
		int start = cpage*Configuration.recordMsgCountPerPage - (Configuration.recordMsgCountPerPage - 1);
		int end = start + (Configuration.recordMsgCountPerPage - 1);
		
		Map<String,String> param = new HashMap<>();
		param.put("start",String.valueOf(start));
		param.put("end",String.valueOf(end));
		param.put("msg_receiver",msg_receiver);
		
		return mybatis.selectList("msg.selectByAdmin",param);
	}
	//쪽지보기
	public MsgDTO selectBySeq(int msg_seq)throws Exception{
		return mybatis.selectOne("msg.selectBySeq",msg_seq);
	}
	
	//쪽지보내기
	public int insert(MsgDTO msgdto)throws Exception{
		return mybatis.insert("msg.insert",msgdto);
	}
	//쪽지읽음처리
	public int updateView(int msg_seq)throws Exception{
		return mybatis.update("msg.view",msg_seq);
	}
	//보낸사람삭제
	public int sender_del(int msg_seq)throws Exception{
		return mybatis.update("msg.sender_del",msg_seq);
	}
	//받는사람삭제
	public int receiver_del(int msg_seq)throws Exception{
		return mybatis.update("msg.receiver_del",msg_seq);
	}
	//새로운쪽지
	public int newmsg(String msg_receiver)throws Exception{
		return mybatis.selectOne("msg.newmsg",msg_receiver);
	}
	
	//회원가입축하
	public int insertWelcome(String msg_receiver)throws Exception{
		return mybatis.insert("msg.insertWelcome",msg_receiver);
	}
	
	
	//네비 메뉴
	//관리자쪽지함 카운트
	public int getAdminCount(String msg_receiver) throws Exception{
		return mybatis.selectOne("msg.getAdminCount",msg_receiver);
	}
	//받은쪽지함카운트
	public int getSenderCount(String msg_receiver) throws Exception{
		return mybatis.selectOne("msg.getSenderCount",msg_receiver);
	}
	//보낸쪽지함카운트
	public int getReceiverCount(String msg_receiver) throws Exception{
		return mybatis.selectOne("msg.getReceiverCount",msg_receiver);

	}
	
	//관리자 쪽지함 네비
	public String getAdminPageNav(int currentPage,String msg_receiver) throws Exception{
		int recordTotalCount = this.getAdminCount(msg_receiver); // 총 개시물의 개수
		int pageTotalCount = 0; // 전체 페이지의 개수

		if( recordTotalCount % Configuration.recordMsgCountPerPage > 0) {
			pageTotalCount = recordTotalCount / Configuration.recordMsgCountPerPage +1;
		}else {
			pageTotalCount = recordTotalCount / Configuration.recordMsgCountPerPage;
		}

		if(currentPage < 1) {
			currentPage = 1;
		}else if(currentPage > pageTotalCount){
			currentPage = pageTotalCount;
		}

		int startNav = (currentPage-1)/Configuration.navMsgCountPerPage * Configuration.navMsgCountPerPage + 1;
		int endNav = startNav + Configuration.navMsgCountPerPage - 1;
		if(endNav > pageTotalCount) {
			endNav = pageTotalCount;
		}

		boolean needPrev = true;
		boolean needNext = true;

		if(startNav == 1) {
			needPrev = false;
		}
		if(endNav == pageTotalCount) {
			needNext = false;
		}

		StringBuilder sb = new StringBuilder("<nav aria-label='Page navigation'><ul class='pagination justify-content-center'>");
		
		if(needPrev) {
			sb.append("<li class='page-item'><a class='page-link' href='msg_list_admin?msgpage="+(startNav-1)+"' id='prevPage' tabindex='-1' aria-disabled='true'>Previous</a></li>");
		}

		for(int i=startNav; i<=endNav; i++) {
			if(currentPage == i) {
				sb.append("<li class='page-item active' aria-current='page'><a class='page-link' href='msg_list_admin?msgpage="+i+"'>"+i+"<span class=sr-only>(current)</span></a></li>");
				//sb.append("<li class='page-item active' aria-current='page'>"+i+"<span class='sr-only'>(current)</span></li>");
			}else {
				sb.append("<li class='page-item'><a class='page-link' href='msg_list_admin?msgpage="+i+"'>"+i+"</a></li>");
			}
		}

		if(needNext) {
			sb.append("<li class=page-item><a class=page-link href='msg_list_admin?msgpage="+(endNav+1)+"' id='nextPage'>다음</a></li> ");
		}		
		sb.append("</ul></nav>");
		return sb.toString();
	}
	//받은 쪽지함 네비
		public String getSenderPageNav(int currentPage,String msg_receiver) throws Exception{
			int recordTotalCount = this.getSenderCount(msg_receiver); // 총 개시물의 개수
			int pageTotalCount = 0; // 전체 페이지의 개수

			if( recordTotalCount % Configuration.recordMsgCountPerPage > 0) {
				pageTotalCount = recordTotalCount / Configuration.recordMsgCountPerPage +1;
			}else {
				pageTotalCount = recordTotalCount / Configuration.recordMsgCountPerPage;
			}

			if(currentPage < 1) {
				currentPage = 1;
			}else if(currentPage > pageTotalCount){
				currentPage = pageTotalCount;
			}

			int startNav = (currentPage-1)/Configuration.navMsgCountPerPage * Configuration.navMsgCountPerPage + 1;
			int endNav = startNav + Configuration.navMsgCountPerPage - 1;
			if(endNav > pageTotalCount) {
				endNav = pageTotalCount;
			}

			boolean needPrev = true;
			boolean needNext = true;

			if(startNav == 1) {
				needPrev = false;
			}
			if(endNav == pageTotalCount) {
				needNext = false;
			}

			StringBuilder sb = new StringBuilder("<nav aria-label='Page navigation'><ul class='pagination justify-content-center'>");
			
			if(needPrev) {
				sb.append("<li class='page-item'><a class='page-link' href='msg_list_sender?msgcpage="+(startNav-1)+"' id='prevPage' tabindex='-1' aria-disabled='true'>Previous</a></li>");
			}

			for(int i=startNav; i<=endNav; i++) {
				if(currentPage == i) {
					sb.append("<li class='page-item active' aria-current='page'><a class='page-link' href='msg_list_sender?msgcpage="+i+"'>"+i+"<span class=sr-only>(current)</span></a></li>");
					//sb.append("<li class='page-item active' aria-current='page'>"+i+"<span class='sr-only'>(current)</span></li>");
				}else {
					sb.append("<li class='page-item'><a class='page-link' href='msg_list_sender?msgcpage="+i+"'>"+i+"</a></li>");
				}
			}

			if(needNext) {
				sb.append("<li class=page-item><a class=page-link href='msg_list_sender?msgcpage="+(endNav+1)+"' id='nextPage'>다음</a></li> ");
			}		
			sb.append("</ul></nav>");
			return sb.toString();
		}
		//보낸 쪽지함 네비
		public String getReceiverPageNav(int currentPage,String msg_receiver) throws Exception{
			int recordTotalCount = this.getReceiverCount(msg_receiver); // 총 개시물의 개수
			int pageTotalCount = 0; // 전체 페이지의 개수

			if( recordTotalCount % Configuration.recordMsgCountPerPage > 0) {
				pageTotalCount = recordTotalCount / Configuration.recordMsgCountPerPage +1;
			}else {
				pageTotalCount = recordTotalCount / Configuration.recordMsgCountPerPage;
			}

			if(currentPage < 1) {
				currentPage = 1;
			}else if(currentPage > pageTotalCount){
				currentPage = pageTotalCount;
			}

			int startNav = (currentPage-1)/Configuration.navMsgCountPerPage * Configuration.navMsgCountPerPage + 1;
			int endNav = startNav + Configuration.navMsgCountPerPage - 1;
			if(endNav > pageTotalCount) {
				endNav = pageTotalCount;
			}

			boolean needPrev = true;
			boolean needNext = true;

			if(startNav == 1) {
				needPrev = false;
			}
			if(endNav == pageTotalCount) {
				needNext = false;
			}

			StringBuilder sb = new StringBuilder("<nav aria-label='Page navigation'><ul class='pagination justify-content-center'>");
			
			if(needPrev) {
				sb.append("<li class='page-item'><a class='page-link' href='msg_list_receiver?msgcpage="+(startNav-1)+"' id='prevPage' tabindex='-1' aria-disabled='true'>Previous</a></li>");
			}

			for(int i=startNav; i<=endNav; i++) {
				if(currentPage == i) {
					sb.append("<li class='page-item active' aria-current='page'><a class='page-link' href='msg_list_receiver?msgcpage="+i+"'>"+i+"<span class=sr-only>(current)</span></a></li>");
					//sb.append("<li class='page-item active' aria-current='page'>"+i+"<span class='sr-only'>(current)</span></li>");
				}else {
					sb.append("<li class='page-item'><a class='page-link' href='msg_list_receiver?msgcpage="+i+"'>"+i+"</a></li>");
				}
			}

			if(needNext) {
				sb.append("<li class=page-item><a class=page-link href='list?ncpage="+(endNav+1)+"' id='nextPage'>다음</a></li> ");
			}		
			sb.append("</ul></nav>");
			return sb.toString();
		}
	
	
	
}