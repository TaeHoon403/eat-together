package coma.spring.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import coma.spring.dao.NoticeFileDAO;
import coma.spring.dto.NoticeFileDTO;

@Controller
@RequestMapping("/NoticeFile/")
public class NoticeFileController {
	@Autowired
	private NoticeFileDAO nfdao;
	
	@Autowired
	private HttpSession session;
	
	
	@RequestMapping("downloadFile")
	public void download(int seq, HttpServletResponse resp) throws Exception{
		NoticeFileDTO dto = nfdao.getFileBySeq(seq);
		String filePath = session.getServletContext().getRealPath("upload");

		File target = new File(filePath+"/"+dto.getSysname());

		try(DataInputStream dis = new DataInputStream(new FileInputStream(target));
				ServletOutputStream sos = resp.getOutputStream();){
			
			String fileName = new String(dto.getOriname().getBytes("utf8"),"iso-8859-1"); //크롬은 iso-8859-1 인코딩을 사용해서 그걸로 변환해준 것.
			
			byte[] fileContents = new byte[(int)target.length()];
			dis.readFully(fileContents);

			//response의 디폴트 액션은 SourceCode이다. 그래서 그것을 리셋하여서, response로 지금 보내는 것이 머냐면~ 파일내용을 보내는 String값(application/octet-stream)을 보내고 있다고 알려주는 것. => 소스코드처럼 렌더링하면 안된다고 알려주는 것.
			resp.reset();
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-disposition", "attachment;filename="+fileName+";");

			sos.write(fileContents);
			sos.flush();
		}
	}

}
