package coma.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coma.spring.dao.NoticeDAO;
import coma.spring.dao.NoticeFileDAO;
import coma.spring.dto.NoticeDTO;
import coma.spring.dto.NoticeFileDTO;
import coma.spring.statics.Configuration;

@Service
public class NoticeService {
	@Autowired
	private NoticeDAO ndao;
	
	@Autowired
	private NoticeFileDAO nfdao;
	
	@Transactional("txManager")
	public void writeProc(List<NoticeFileDTO> fileList, NoticeDTO ndto, String realPath) throws Exception {
		
		int seq = ndao.getNextVal();
		ndto.setSeq(seq);
		ndto.setContents(ndto.getContents().replaceAll("(\r\n|\r|\n|\n\r)", " "));
		ndao.insert(ndto);
		for(NoticeFileDTO file : fileList) {
			file.setParent_seq(seq);
			nfdao.insert(file);
		}
	}
	
	public int update(List<NoticeFileDTO> fileList, NoticeDTO dto, String realPath) throws Exception{
		int result= ndao.update(dto);
		int seq= dto.getSeq();
		
		for(NoticeFileDTO file : fileList) {
			dto.setAttachment("1");
			file.setParent_seq(seq);
			nfdao.insert(file);
		}
		return result;
	}
	
	public List<NoticeDTO> selectByPage(int cpage) throws Exception{
		List<NoticeDTO> dto = ndao.selectByPage(cpage);
		return dto;
	}
	
	public String getPageNav(int currentPage) throws Exception{
		int recordTotalCount = ndao.getArticleCount(); // 총 개시물의 개수
		int pageTotalCount = 0; // 전체 페이지의 개수

		if( recordTotalCount % Configuration.recordCountPerPage > 0) {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage +1;
		}else {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage;
		}

		if(currentPage < 1) {
			currentPage = 1;
		}else if(currentPage > pageTotalCount){
			currentPage = pageTotalCount;
		}

		int startNav = (currentPage-1)/Configuration.navCountPerPage * Configuration.navCountPerPage + 1;
		int endNav = startNav + Configuration.navCountPerPage - 1;
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
			sb.append("<li class='page-item'><a class='page-link' href='list?ncpage="+(startNav-1)+"' id='prevPage' tabindex='-1' aria-disabled='true'>Previous</a></li>");
		}

		for(int i=startNav; i<=endNav; i++) {
			if(currentPage == i) {
				sb.append("<li class='page-item active' aria-current='page'><a class='page-link' href='list?ncpage="+i+"'>"+i+"<span class=sr-only>(current)</span></a></li>");
				//sb.append("<li class='page-item active' aria-current='page'>"+i+"<span class='sr-only'>(current)</span></li>");
			}else {
				sb.append("<li class='page-item'><a class='page-link' href='list?ncpage="+i+"'>"+i+"</a></li>");
			}
		}

		if(needNext) {
			sb.append("<li class=page-item><a class=page-link href='list?ncpage="+(endNav+1)+"' id='nextPage'>다음</a></li> ");
		}		
		sb.append("</ul></nav>");
		return sb.toString();
	}
	
	public String navi (int cpage) throws Exception{
		String navi = this.getPageNav(cpage);
		return navi;
	}
	
	public NoticeDTO selectBySeq(int seq) throws Exception {
		int result = ndao.view_count(seq); // 조회수 +1
		NoticeDTO dto = ndao.selectBySeq(seq); // 읽어오기
		return dto;
	}
	
	public int delete(String seq) throws Exception{
		int result=ndao.delete(seq);
		return result;
	}
	
	
	
	
}
