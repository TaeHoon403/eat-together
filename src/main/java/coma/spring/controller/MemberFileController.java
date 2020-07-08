package coma.spring.controller;

import java.awt.List;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

//import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import coma.spring.dto.MemberDTO;
import coma.spring.dto.MemberFileDTO;
import coma.spring.service.MemberFileService;

@Controller
@RequestMapping("/memberfile/")
public class MemberFileController {
	private static final Logger logger = LoggerFactory.getLogger(MemberFileController.class);

	//@Resource(name="uploadPath")
	//String uploadPath;

	@Autowired
	private MemberFileService mfservice;

	@Autowired
	private HttpSession session;

	//프로필 사진 변경하는 팝업창 열기
	@RequestMapping("editProfileImage")
	public String getEditProfileImageView() {
		return "member/editprofileimage";
	}

	//프로필사진 업로드
	@RequestMapping(value="uploadProc",  method=RequestMethod.POST, headers = ("content-type=multipart/*"))
	public String uploadProc(MemberFileDTO mfdto, MultipartFile file)throws Exception {
		System.out.println("파일업로드 Proc 접속하기");

		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		String parent_id = mdto.getId();

		System.out.println("프로필 이미지 변경하는 아이디 : "+ parent_id);
		String filePath = session.getServletContext().getRealPath("upload/" +parent_id+"/" );

		File tempFilePath = new File(filePath);

		if(!tempFilePath.exists()) {
			tempFilePath.mkdirs();
		}

		UUID uuid = UUID.randomUUID();
		String sysname = uuid.toString()+"_"+ file.getOriginalFilename();
		String oriname = file.getOriginalFilename();
		System.out.println("Oriname: " + oriname);
		System.out.println("Sysname: "+sysname);
		System.out.println("====파일 이름 얻기 =====");

		File targetLoc = new File(filePath + "/" + sysname);
		file.transferTo(targetLoc);
		System.out.println("파일경로 : " + filePath);

		mfdto.setOriname(oriname);
		mfdto.setSysname(sysname);
		mfdto.setParent_id(parent_id);
		System.out.println("====파일 mfdto에 저장하기=====");
		System.out.println(mfdto.getOriname() + ":"+ mfdto.getSysname() + ":"+ mfdto.getParent_id());
		

		int uploadResult = mfservice.uploadProfile(mfdto);
		System.out.println("파일업로드 성공1 실패0 :" + uploadResult);

		System.out.println("oriName : " + oriname);
		System.out.println("sysName : " + sysname);

		return "member/editprofileimage";
	}

	// 프로필 사진 삭제하기
	@RequestMapping("deleteFileById")
	public String deleteFileById()throws Exception{
		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		String id = mdto.getId();
		String filepath = session.getServletContext().getRealPath("upload/" +id+"/" );

		File deleteFile = new File(filepath);

		System.out.println("삭제할 파일 : " + deleteFile);
		if(deleteFile.exists() == true){
			deleteFile.delete();
			System.out.println("파일 삭제 완료");
			int deleteResult = mfservice.deleteFilebyId(id); //DB상에서 파일 삭제하는 코드
			System.out.println("db상 삭제된 파일 개수 : " + deleteResult);
			//서버 상에서 파일 삭제하는 코드
		}else {
			System.out.println("파일이 존재하지 않습니다.");
		}
		return "member/editprofileimage";


	}
	//by지은, 이미지가 업로드되어있는 경로 가져오기
	@RequestMapping(value = "/getPic", method = RequestMethod.GET)
	@ResponseBody
	public String getPic(String id, HttpServletRequest request)throws Exception {

		MemberFileDTO mfdto = new MemberFileDTO();
		String parent_id = id;
		mfdto = mfservice.getFilebyId(parent_id);          // 이미지 정보 가져오기
		String sysname = mfdto.getSysname();
		System.out.println("getPic에서 찾아온 sysname: " + sysname);
		String path = session.getServletContext().getRealPath("upload/" +id+"/"+ sysname);   // 이미지 경로

		return path;
	}



	//아이디로 파일 찾기
	@RequestMapping("getFileById")
	public ModelAndView getFileById(String id)throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/editmyinfo");

		String parent_id = id;
		MemberFileDTO mfdto = mfservice.getFilebyId(parent_id);

		mav.addObject("mfdto", mfdto);
		return mav;
	}
}
