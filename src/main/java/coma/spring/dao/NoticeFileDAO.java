package coma.spring.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import coma.spring.dto.NoticeFileDTO;

@Repository
public class NoticeFileDAO {
	@Autowired
	private SqlSessionTemplate mybatis;

	public int insert(NoticeFileDTO dto) {
		return mybatis.insert("NoticeFile.insert",dto);
	}
	
	public NoticeFileDTO getFileBySeq(int seq) {
		return mybatis.selectOne("NoticeFile.getFileBySeq",seq);
	}
	
	public List<NoticeFileDTO> getFileList(int seq) {
		return mybatis.selectList("NoticeFile.getFileList",seq);
	}
}
