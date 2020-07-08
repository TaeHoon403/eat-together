package coma.spring.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import coma.spring.dto.MemberDTO;
import coma.spring.dto.MemberFileDTO;
import coma.spring.service.MemberFileService;
import coma.spring.service.MemberService;
import coma.spring.service.MsgService;

@Controller
@RequestMapping("/member/")
public class MemberController {
	//By 지은, 회원서비스 관련 기능을 모아놓은 컨트롤러

	@Autowired
	private MemberService mservice;

	@Autowired 
	private MemberFileService mfservice;

	@Autowired
	private MsgService msgservice;	

	@Autowired
	private HttpSession session;

	//by지은, try-catch 예외처리 대체할 수 있는 메서드, ExceptionHandler_20200701
	@ExceptionHandler
	public String exceptionHandler(Exception e) {
		e.printStackTrace();
		System.out.println("에러가 발생하였습니다.");
		return "error";
	}

	//로그인 페이지로 이동하기
	@RequestMapping("loginview")
	public String getLoginView() {
		return "member/loginview";
	}

	//회원가입 페이지 중 약관동의로 이동
	@RequestMapping("signup_check")
	public String getSignupCheckView() {
		return "member/signup_check";
	}

	//회원가입, 약관동의 후 정보입력 페이지로 이동
	@RequestMapping("signup_info")
	public String getSignupInfoView() {
		return "member/signup_info";
	}

	//by지은, 마이페이지에서 내정보view로 이동_20200704
	@RequestMapping("mypage_myinfo")
	public ModelAndView getMyInfoView() throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/mypage_myinfo");
		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		String id = mdto.getId();

		mdto = mservice.selectMyInfo(id);
		mav.addObject("mdto", mdto);
		return mav;
	}

	//마이페이지에서 쪽지함으로 이동
	@RequestMapping("mypage_msglist")
	public String getMyMsgView() {
		return "member/mypage_msglist";
	}

	//마이페이지에서 내모임리스트로 이동
	@RequestMapping("mypage_chatlist")
	public String getChatlistView() {
		return "member/mypage_chatlist";
	}

	//마이페이지에서 내리뷰리스트로 이동
	@RequestMapping("mypage_reviewlist")
	public String getReviewlistView() {
		return "member/mypage_reviewlist";
	}
	//회원탈퇴 페이지로 이동하기
	@RequestMapping("withdrawView")
	public String getWithdrawView() {
		return "member/withdraw";
	}

	//비밀번호 수정하기 팝업 열기
	@RequestMapping("editPw")
	public String openEditPwView() {
		return "member/editpw";
	}

	//By지은,  내정보 수정 페이지로 이동하기_20200704
	@RequestMapping("editMyInfo")
	public ModelAndView getEditMyInfoView()throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/editmyinfo");

		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		String id = mdto.getId();

		mdto = mservice.selectMyInfo(id);
		mav.addObject("mdto", mdto);
		return mav;

	}


	//아이디 찾기 팝업 열기
	@RequestMapping("findid")
	public String findId() {
		return "member/findid";
	}


	//비밀번호 찾기 팝업 열기
	@RequestMapping("findpw")
	public String findpw() {
		return "member/findpw";
	}


	//로그아웃하기
	@RequestMapping("logoutProc")
	public String logoutProc() {
		//By지은, 카카오톡 로그인의 경우 access_Token 로그아웃이 필요하다_20200705
		if(session.getAttribute("access_Token")!=null) {
			mservice.kakaoLogout((String)session.getAttribute("access_Token"));
			session.invalidate();
			return "redirect:/";
			//카카오톡 로그인이 아닌 경우
		}else {
			session.invalidate();
			return "redirect:/";
		}
	}

	//회원가입하기
	@RequestMapping("signupProc")
	public String signUp(MemberDTO mdto)throws Exception {

		int result = mservice.signUp(mdto);
		//회원가입축하메세지 입니다.
		int msgresult= msgservice.insertWelcome(mdto.getId());
		System.out.println("signupProc 비밀번호 : " + mdto.getPw());

		System.out.println("회원가입 성공");
		return "redirect:/";
	}

	//회원가입시 아이디 중복체크
	@RequestMapping("isIdAvailable")
	@ResponseBody
	public String isIdAvailable(String id)throws Exception {
		boolean result=true;

		result = mservice.isIdAvailable(id);
		System.out.println("아이디 중복체크 결과 : " + result);
		return String.valueOf(result);
	}

	//로그인하기
	@RequestMapping("login")
	public String login(String id, String pw)throws Exception {

		System.out.println("id : " + id);
		String protectedpw = mservice.getSha512(pw);
		System.out.println("pw : " + protectedpw);

		Map<String, String> param = new HashMap<>();
		param.put("targetColumn1", "id");
		param.put("targetValue1", id);
		param.put("targetColumn2", "pw");
		param.put("targetValue2", protectedpw);

		boolean result = mservice.logIn(param);

		System.out.println("loginResult 결과 : "+ result);

		//by 지은, 로그인 성공시 세션에 값을 저장해준다_20200706
		if(result==true) {
			MemberDTO mdto = mservice.selectMyInfo(id);
			String msg_receiver=mdto.getId();
			System.out.println("아이디는"+msg_receiver);
			int newmsg = msgservice.newmsg(msg_receiver);
			System.out.println("새로운메세지"+newmsg);
			session.setAttribute("loginInfo", mdto);
			//새로운메세지 확인
			session.setAttribute("newMsg", newmsg);
			System.out.println("로그인 성공");
			return "redirect:/";
		}else {
			System.out.println("id : " + id);
			System.out.println("pw : " + protectedpw);
			System.out.println("로그인 실패");
			return "redirect:/";
		}

	}

	//로그인 시 유효성 검사
	@RequestMapping("isPwCorrect")
	@ResponseBody
	public String isPwCorrect(@RequestParam String id, @RequestParam String pw)throws Exception {
		System.out.println("id : " + id);
		String protectedpw = mservice.getSha512(pw);
		System.out.println("pw : " + protectedpw);

		Map<String, String> param = new HashMap<>();
		param.put("targetColumn1", "id");
		param.put("targetValue1", id);
		param.put("targetColumn2", "pw");
		param.put("targetValue2", protectedpw);

		boolean result = mservice.logIn(param);

		System.out.println("loginResult 결과 : "+ result);

		//by 지은, 아이디와 입력한 비밀번호가 일치한 경우 correct를 반환, 틀린 경우 uncorrect 반환_20200706
		if(result==true) {
			String msg = "correct";
			return msg;
		}else {
			String msg = "uncorrect";
			return msg;
		}
	}

	//회원탈퇴하기
	@RequestMapping("withdrawProc")
	public String withdrawProc(String pw) throws Exception{
		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		String id = mdto.getId();
		String protectedpw = mdto.getPw();
		String inputpw = mservice.getSha512(pw);

		System.out.println("회원탈퇴하는 id : " + id);
		System.out.println("회원탈퇴하는 pw : " + protectedpw);
		System.out.println("입력한 pw : " + inputpw);

		if(protectedpw.contentEquals(inputpw)) {
			Map<String, String> param = new HashMap<>();
			param.put("targetColumn1", "id");
			param.put("targetValue1", id);
			param.put("targetColumn2", "pw");
			param.put("targetValue2", protectedpw);

			int result = mservice.deleteMember(param);
			System.out.println("회원 탈퇴 성공 : " + result);


			if(result>0) {
				System.out.println("회원 탈퇴 완료");
				//By지은, 카카오톡 로그인의 경우 access_Token로그아웃이 필요하다_20200706
				if(session.getAttribute("access_Token")!=null) {
					mservice.kakaoLogout((String)session.getAttribute("access_Token"));
					session.invalidate();
					return "redirect:/";
					//카카오톡 로그인이 아닌 경우
				}else {
					session.invalidate();
					return "redirect:/";
				}
			}else {
				System.out.println("회원 탈퇴 실패, 관리자에게 문의하세요.");
				return "error";
			}
		}else {
			System.out.println("비밀번호 불일치");
			return "error";	
		}
	}

	//비밀번호 수정하기
	@RequestMapping("editPwProc")
	@ResponseBody
	public String editPwProc(String pw)throws Exception {
		System.out.println("컨트롤러로 값 전달 성공");
		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		String id = mdto.getId();
		String oriprotectedpw = mdto.getPw(); 
		String newprotectedpw = mservice.getSha512(pw);

		if(oriprotectedpw.contentEquals(newprotectedpw)) {
			System.out.println("수정하려는 비밀번호가 기존과 일치하여 에러 발생");
			return "error";
		}else {
			Map<String, String> param = new HashMap<>();
			param.put("targetColumn1", "pw");
			param.put("targetValue1", newprotectedpw);
			param.put("targetColumn2", "id");
			param.put("targetValue2", id);

			int result = mservice.editPw(param);
			System.out.println("비밀번호 수정 성공 :" + result);
			mdto.setPw(newprotectedpw);
			session.setAttribute("loginInfo", mdto);

			return "/member/editMyInfo";
		}


	}


	//내정보 수정하기
	@RequestMapping("editMyInfoProc")
	public ModelAndView editMyInfoProc(String nickname, String birth, String account_email) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/mypage_myinfo");

		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		String id = mdto.getId();
		System.out.println("수정할 아이디 : " + id);
		System.out.println("수정할 생년월일 : " + birth);
		System.out.println("수정할 이메일 : " + account_email);

		Map<String, String> param = new HashMap<>();
		param.put("targetColumn1", "birth");
		param.put("targetValue1", birth);
		param.put("targetColumn2", "account_email");
		param.put("targetValue2", account_email);
		param.put("targetColumn3", "id");
		param.put("targetValue3", id);

		int result = mservice.editMyInfo(param);
		System.out.println("회원정보수정 결과 1-성공 0-실패 : " + result);

		mdto.setBirth(birth);
		mdto.setAccount_email(account_email);
		session.setAttribute("loginInfo", mdto);

		mdto = mservice.selectMyInfo(id);
		mav.addObject("mdto", mdto);
		return mav;


	}


	//카카오톡 로그인하기
	@RequestMapping("/kakaoLogin")
	public String kakaoLogin(@RequestParam("code") String code, HttpSession session)throws Exception {
		System.out.println("code : "+ code);
		String access_Token = mservice.getAccessToken(code);
		System.out.println("controller access_token : " + access_Token);
		MemberDTO mdto = mservice.getloginInfo(access_Token);
		System.out.println("login Controller : " + mdto);

		//    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
		if (mdto.getId() != null) {
			session.setAttribute("loginInfo", mdto);
			session.setAttribute("access_Token", access_Token);
		}
		return "redirect:/";
	}



}
