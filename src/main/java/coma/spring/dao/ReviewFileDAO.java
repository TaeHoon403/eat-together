package coma.spring.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import coma.spring.dto.ReviewFileDTO;

@Repository
public class ReviewFileDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int insert(ReviewFileDTO rfdto) {
		return mybatis.insert("Review.insertFile",rfdto);
	}
	
	public ReviewFileDTO selectFileByPseq(int parent_seq) {
		return mybatis.selectOne("Review.selectFileByPseq",parent_seq);
	}

}
