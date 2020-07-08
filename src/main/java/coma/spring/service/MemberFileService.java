package coma.spring.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import coma.spring.dao.MemberFileDAO;
import coma.spring.dto.MemberFileDTO;

@Service
public class MemberFileService {

	@Autowired
	private MemberFileDAO mfdao;

	//프로필 이미지 업로드하기
	public int uploadProfile(MemberFileDTO mfdto)throws Exception {
		int result = mfdao.uploadProfile(mfdto);
		return result;
	}
	
	//프로필이미지 다운로드
	public MemberFileDTO getFilebyId(String parent_id)throws Exception{
		MemberFileDTO mfdto = mfdao.getFileById(parent_id);
		return mfdto;
	}
	
	//프로필이미지 삭제하기
	public int deleteFilebyId(String parent_id)throws Exception{
		int result = mfdao.deleteFilebyId(parent_id);
		return result;
	}

}
