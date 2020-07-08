package coma.spring.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import coma.spring.dto.NoticeDTO;
import coma.spring.statics.Configuration;

@Repository
public class NoticeDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int getNextVal() {
		return mybatis.selectOne("Notice.getNextval");
	}
	
	public int insert(NoticeDTO dto) throws Exception {
		return mybatis.insert("Notice.insert",dto);
	}
	

	public List<NoticeDTO> selectByPage(int cpage) throws Exception{
		int start = cpage*Configuration.recordCountPerPage - (Configuration.recordCountPerPage - 1);
		int end = start + (Configuration.recordCountPerPage - 1);
		
		Map<String,Integer> param = new HashMap<>();
		param.put("start",start);
		param.put("end",end);

		return mybatis.selectList("Notice.selectByPage",param);
	}
	
	public int getArticleCount() throws Exception{
		return mybatis.selectOne("Notice.getArticleCount");

	}
	
	public int view_count(int seq) throws Exception{
		return mybatis.update("Notice.view_count",seq);
	}

	public NoticeDTO selectBySeq(int seq) throws Exception{
		return mybatis.selectOne("Notice.selectBySeq",seq);
	}
	
	public int delete(String seq) throws Exception{
		return mybatis.delete("Notice.delete",seq);
	}

	public int update(NoticeDTO dto) throws Exception{
		 Map<String, String> param = new HashMap<>();
	      param.put("columnName1", "title");
	      param.put("changeValue1", dto.getTitle());
	      param.put("columnName2", "contents");
	      param.put("changeValue2", dto.getContents());
	      param.put("columnName3", "attachment");
	      param.put("changeValue3", dto.getAttachment());
	      param.put("targetColumn", "seq");
	      param.put("targetValue", Integer.toString(dto.getSeq()));
	     	      
	      System.out.println(param.size());
	      
		return mybatis.update("Notice.update",param);
	}

}
