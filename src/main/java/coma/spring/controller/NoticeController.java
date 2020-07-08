package coma.spring.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import coma.spring.dto.NoticeDTO;
import coma.spring.dto.NoticeFileDTO;
import coma.spring.service.NoticeFileService;
import coma.spring.service.NoticeService;

@Controller
@RequestMapping("/notice/")
public class NoticeController {

	
	@Autowired
	private NoticeService nservice;
	
	@Autowired
	private NoticeFileService nfservice;
	
	@Autowired
	private HttpSession session;
	
	@ExceptionHandler
	public String exceptionHandler(Exception e) { //일괄적 예외처리 : ExceptionHandler를 통해서 클래스 내의 모든 메서드의 예외를 받을 수 있다.(다른 메서드에는 모두 throws Exception을 붙여줌)
		e.printStackTrace();
		System.out.println("Exception Handler : 에러가 발생했습니다.");
		return "error";
	}
	
	@RequestMapping("write")
	public String toWrite() {
		return "/notice/notice_write";
	}
	
	@RequestMapping(value="writeProc", method = RequestMethod.POST)
	public String writeProc(NoticeDTO ndto, MultipartFile[] attfiles) throws Exception {
		
		if(ndto.getImportance() != 1) {
			ndto.setImportance(0);
		}else {
			ndto.setImportance(1);
		}
		

		/*파일*/         
		String realPath = session.getServletContext().getRealPath("upload/notice/");

		File filePath = new File(realPath);
		if(!filePath.exists()) {
			filePath.mkdir(); //폴더 만들기
		}

		/* 파일 업로드 */      
		List <NoticeFileDTO> filelist = new ArrayList<NoticeFileDTO>();
		
		ndto.setAttachment("0");
		if(attfiles.length != 0) { // 한개라도 폴더가 들어가면 나옴
			ndto.setAttachment("1");
			int num = 0;
			for(MultipartFile file : attfiles) {
				if(!file.isEmpty()) { // 파일이 있는지 없는지 확인
					NoticeFileDTO singleNFDTO= new NoticeFileDTO();
					UUID uuid = UUID.randomUUID();

					singleNFDTO.setOriname(file.getOriginalFilename());
					
					singleNFDTO.setSysname(uuid+"_"+file.getOriginalFilename());               
					String systemFileName = uuid+"_"+file.getOriginalFilename();
					System.out.println(systemFileName);
					File flieDownload = new File(realPath + "/" + systemFileName);
					file.transferTo(flieDownload); // 파일 생성
					System.out.println(singleNFDTO.getSysname());
					filelist.add(singleNFDTO);
				}            
			}
		}
		

		nservice.writeProc(filelist,ndto,realPath);
		
		return "redirect:contents?seq="+ndto.getSeq();
	}
	
	
	@RequestMapping("list")
	public String list(HttpServletRequest request) throws Exception {
		if(session.getAttribute("ncpage")==null) {
			session.setAttribute("ncpage", 1);
		}
		try { 
			session.setAttribute("ncpage", Integer.parseInt(request.getParameter("ncpage")));
		} catch (Exception e) {}
		int ncpage= (int) session.getAttribute("ncpage");
		List<NoticeDTO> dto = nservice.selectByPage(ncpage);
		String navi = nservice.navi(ncpage);
		request.setAttribute("list", dto);
		request.setAttribute("navi", navi);
		return "/notice/notice_list";
	}
	
	@RequestMapping("contents")
	public String notice_content(int seq, HttpServletRequest request) throws Exception{
		NoticeDTO dto = nservice.selectBySeq(seq);
		request.setAttribute("contents", dto);
		request.setAttribute("fileList", nfservice.getFileList(seq));
		
		return "/notice/notice_content";
	}
	
	@RequestMapping("delete")
	public String delete(String seq) throws Exception{
		nservice.delete(seq);
		return "redirect:list";
	}

	@RequestMapping("modify")
	public String toModify(int seq, HttpServletRequest request) throws Exception {
		NoticeDTO dto = nservice.selectBySeq(seq);
		request.setAttribute("contents", dto);
		return "notice/notice_modify";
	}

	@RequestMapping("modifyProc")
	public String Modify(NoticeDTO dto, MultipartFile[] attfiles) throws Exception {
		/*파일*/         
		String realPath = session.getServletContext().getRealPath("upload/notice/");

		File filePath = new File(realPath);
		if(!filePath.exists()) {
			filePath.mkdir(); //폴더 만들기
		}

		/* 파일 업로드 */      
		List <NoticeFileDTO> filelist = new ArrayList<NoticeFileDTO>();
		
		dto.setAttachment("0");
		if(attfiles.length != 0) { // 한개라도 폴더가 들어가면 나옴
			dto.setAttachment("1");
			int num = 0;
			for(MultipartFile file : attfiles) {
				if(!file.isEmpty()) { // 파일이 있는지 없는지 확인
					NoticeFileDTO singleNFDTO= new NoticeFileDTO();
					UUID uuid = UUID.randomUUID();

					singleNFDTO.setOriname(file.getOriginalFilename());
					
					singleNFDTO.setSysname(uuid+"_"+file.getOriginalFilename());               
					String systemFileName = uuid+"_"+file.getOriginalFilename();
					System.out.println(systemFileName);
					File flieDownload = new File(realPath + "/" + systemFileName);
					file.transferTo(flieDownload); // 파일 생성
					System.out.println(singleNFDTO.getSysname());
					filelist.add(singleNFDTO);
				}            
			}
		}else {
			dto.setAttachment("0");
		}
		nservice.update(filelist,dto,realPath);
		return "redirect:contents?seq="+dto.getSeq();
	}

}
