package coma.spring.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coma.spring.dao.NoticeFileDAO;
import coma.spring.dto.NoticeFileDTO;

@Service
public class NoticeFileService {
	@Autowired
	private NoticeFileDAO nfdao;
	
	@Autowired
	private HttpSession session;
	
	public List<NoticeFileDTO> getFileList(int seq){
		return nfdao.getFileList(seq);
	}
}
