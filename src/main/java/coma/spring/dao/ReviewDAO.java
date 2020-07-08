package coma.spring.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import coma.spring.dto.ReviewDTO;

@Repository
public class ReviewDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int insert(ReviewDTO rdto) throws Exception{
		return mybatis.insert("Review.insert",rdto);
	}
	public List<ReviewDTO> selectByPseq(int parent_seq) throws Exception{
		return mybatis.selectList("Review.selectByPseq",parent_seq);
	}
	
	//by지은, 마이페이지 내리뷰리스트 출력을 위한 select문_20200707
	public List<ReviewDTO> selectById(Map<String, Object >param)throws Exception{
		return mybatis.selectList("Review.selectById", param);
	}
	//by지은, 마이페이지 내리뷰리스트 출력을 위한 네비바_20200707
	public int getMyPageArticleCount(String id)throws Exception{
		return mybatis.selectOne("Review.getMyPageArticleCount", id);
	}
}
