package coma.spring.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import coma.spring.dto.MemberFileDTO;

@Repository
public class MemberFileDAO {
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	//프로필 업로드하기
	public int uploadProfile(MemberFileDTO mfdto)throws Exception{
		return mybatis.insert("MemberFile.uploadProfile", mfdto);
	}
	
	//아아디로 파일 찾아오기
	public MemberFileDTO getFileById(String parent_id)throws Exception{
		return mybatis.selectOne("MemberFile.getFileById", parent_id);
	}
	
	//프로필 사진 삭제하기
	public int deleteFilebyId(String parent_id)throws Exception{
		return mybatis.delete("MemberFile.deleteFilebyId", parent_id);
	}
}
