package coma.spring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coma.spring.dao.MapDAO;
import coma.spring.dao.ReviewDAO;
import coma.spring.dao.ReviewFileDAO;
import coma.spring.dto.MapDTO;
import coma.spring.dto.PartyDTO;
import coma.spring.dto.ReviewDTO;
import coma.spring.dto.ReviewFileDTO;
import coma.spring.dto.TopFiveStoreDTO;
import coma.spring.statics.Configuration;

@Service
public class ReviewService {
	@Autowired
	private ReviewDAO rdao;
	@Autowired
	private MapDAO mapdao;
	@Autowired
	private ReviewFileDAO rfdao;
	@Transactional("txManager")
	public void write(ReviewDTO rdto) throws Exception{
		rdao.insert(rdto);
		mapdao.updateRatingAvg(rdto.getParent_seq());
	}
	@Transactional("txManager")
	public void write(ReviewDTO rdto, ReviewFileDTO rfdto) throws Exception{
		rdao.insert(rdto);
		rfdao.insert(rfdto);
		mapdao.updateRatingAvg(rdto.getParent_seq());
	}
	public List<ReviewDTO> selectByPseq(int parent_seq) throws Exception{
		return rdao.selectByPseq(parent_seq);
	}
	
	public ReviewFileDTO selectFileByPseq(int parent_seq) throws Exception{
		return rfdao.selectFileByPseq(parent_seq);
	}
	
	//by지은, 마이페이지 - 내모임 리스트 출력하는 select 문 수정_20200709
	public List<ReviewDTO> selectById(String id)throws Exception{		
		List<ReviewDTO> reviewList = rdao.selectById(id);
		return reviewList;
	}
	

	//by지은, 마이페이지 - 내 리뷰리스트 출력을 위한 네비바 생성_20200707
	public String getMyPageNav(int mcpage, String id) throws Exception{
		int recordTotalCount = rdao.getMyPageArticleCount(id); // 총 개시물의 개수
		int pageTotalCount = 0; // 전체 페이지의 개수

		if( recordTotalCount % Configuration.recordCountPerPage > 0) {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage +1;
		}else {
			pageTotalCount = recordTotalCount / Configuration.recordCountPerPage;
		}

		if(mcpage < 1) {
			mcpage = 1;
		}else if(mcpage > pageTotalCount){
			mcpage = pageTotalCount;
		}

		int startNav = (mcpage-1)/Configuration.navCountPerPage * Configuration.navCountPerPage + 1;
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
			sb.append("<li class='page-item'><a class='page-link' href='/review/selectById?mcpage="+(startNav-1)+"' id='prevPage' tabindex='-1' aria-disabled='true'>Previous</a></li>");
		}

		for(int i=startNav; i<=endNav; i++) {  
			if(mcpage == i) {
				sb.append("<li class='page-item active' aria-current='page'><a class='page-link' href='/review/selectById?mcpage="+i+"'>"+i+"<span class=sr-only>(current)</span></a></li>");
				//sb.append("<li class='page-item active' aria-current='page'>"+i+"<span class='sr-only'>(current)</span></li>");
			}else {
				sb.append("<li class='page-item'><a class='page-link' href='/review/selectById?mcpage="+i+"'>"+i+"</a></li>");
			}
		}

		if(needNext) {
			sb.append("<li class=page-item><a class=page-link href='/review/selectById?mcpage="+(endNav+1)+"' id='nextPage'>다음</a></li> ");
		}		
		sb.append("</ul></nav>");
		return sb.toString();
	}
	// 태훈 리뷰 긁어고기
	public Map<Integer, Object> getReview(List<MapDTO> top){
		List<TopFiveStoreDTO> reviewList = rdao.getReview(top);
		Map<Integer, Object> review = new HashMap<Integer, Object>();
		for(int i=0; i<reviewList.size(); i++) {
			review.put(reviewList.get(i).getParent_seq(), reviewList.get(i));
		}
		return review;
	}

}
