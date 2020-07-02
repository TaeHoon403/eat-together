package coma.spring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import coma.spring.dto.MemberDTO;
import coma.spring.service.MemberService;

@Controller
@RequestMapping("/member/")
public class MemberController {

	@Autowired
	private MemberService mservice;

	@Autowired
	private HttpSession session;

	//try-catch 예외처리 대체할 수 있는 메서드, ExceptionHandler
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

	//마이페이지에서 내정보view로 이동
	@RequestMapping("mypage_myinfo")
	public String getMyInfoView() {
		return "member/mypage_myinfo";
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

	//내정보 수정 페이지로 이동하기
	@RequestMapping("editMyInfo")
	public String getEditMyInfoView() {
		return "member/editmyinfo";
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
		session.invalidate();
		return "home";
	}

	//회원가입하기
	@RequestMapping("signupProc")
	public String signUp(MemberDTO mdto)throws Exception {

		int result = mservice.signUp(mdto);
		System.out.println("signupProc 비밀번호 : " + mdto.getPw());

		System.out.println("회원가입 성공");
		return "home";
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

		if(result==true) {
			MemberDTO mdto = mservice.selectMyInfo(id);
			session.setAttribute("loginInfo", mdto);
			System.out.println("로그인 성공");
			return "home";
		}else {
			System.out.println("id : " + id);
			System.out.println("pw : " + protectedpw);
			System.out.println("로그인 실패");
			return "home";
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
				session.invalidate();
				return "home";
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
			return "/member/editMyInfo";
		}


	}


	//내정보 수정하기
	@RequestMapping("editMyInfoProc")
	public String editMyInfoProc(String nickname, String account_email) throws Exception {
		MemberDTO mdto = (MemberDTO) session.getAttribute("loginInfo");
		String id = mdto.getId();
		System.out.println("수정할 아이디 : " + id);
		System.out.println("수정할 닉네임 : " + nickname);
		System.out.println("수정할 이메일 : " + account_email);

		Map<String, String> param = new HashMap<>();
		param.put("targetColumn1", "nickname");
		param.put("targetValue1", nickname);
		param.put("targetColumn2", "account_email");
		param.put("targetValue2", account_email);
		param.put("targetColumn3", "id");
		param.put("targetValue3", id);

		int result = mservice.editMyInfo(param);

		mdto.setNickname(nickname);
		mdto.setAccount_email(account_email);

		System.out.println("회원정보수정 결과 1-성공 0-실패 : " + result);
		return "redirect:/member/mypage_myinfo";
	}


	//	
	//	@RequestMapping("/kakaologin")
	//	public String login(@RequestParam("code") String code, HttpSession session) {
	//		System.out.println("code : "+ code);
	//	    String access_Token = mservice.getAccessToken(code);
	//	    System.out.println(access_Token);
	//	    HashMap<String, Object> userInfo = mservice.getUserInfo(access_Token);
	//	    System.out.println("login Controller : " + userInfo);
	//	    
	//	    //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
	//	    if (userInfo.get("id") != null) {
	//	        session.setAttribute("id", userInfo.get("id"));
	//	        session.setAttribute("access_Token", access_Token);
	//	    }
	//	    return "index";
	//	}
	//
	//	
	//	@RequestMapping("/logout")
	//	public String logout(HttpSession session) {
	//	    mservice.kakaoLogout((String)session.getAttribute("access_Token"));
	//	    session.removeAttribute("access_Token");
	//	    session.removeAttribute("id");
	//	    return "index";
	//	}



}
