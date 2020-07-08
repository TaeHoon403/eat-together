package coma.spring.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import coma.spring.dao.MemberDAO;
import coma.spring.dao.MemberFileDAO;
import coma.spring.dto.MemberDTO;
import coma.spring.dto.MemberFileDTO;

@Service
public class MemberService {

	@Autowired
	private MemberDAO mdao;
	
	@Autowired
	private MemberFileDAO mfdao;

	//비밀번호 암호화하기
	public String getSha512(String pw) throws Exception{
		String encPw = "";
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		byte[] bytes = pw.getBytes(Charset.forName("UTF-8"));
		md.update(bytes);
		encPw = Base64.getEncoder().encodeToString(md.digest());

		return encPw;
	}

	//회원가입하기
	@Transactional("txManager")
	public int signUp(MemberDTO mdto, MemberFileDTO mfdto) throws Exception{
		System.out.println("원래 비밀번호 : " + mdto.getPw());
		String encPw = this.getSha512(mdto.getPw());
		mdto.setPw(encPw);
		System.out.println("암호화된 비밀번호 : " + mdto.getPw());
		
		Map<String, Object> signUpParam = new HashMap<>();
		signUpParam.put("mdto", mdto);
		signUpParam.put("mfdto", mfdto);
		
		int result = mdao.signUp(signUpParam);
		mfdao.uploadProc(mfdto);

		return result;
	}

	//로그인하기
	public boolean logIn(Map<String, String> param)throws Exception{
		boolean result = mdao.logIn(param);
		return result;
	}

	//내 정보 보기
	public MemberDTO selectMyInfo(String id)throws Exception{
		MemberDTO mdto = mdao.selectMyInfo(id);
		return mdto;
	}

	//회원가입시 이메일 중복검사
	public boolean isEmailAvailable(String account_email)throws Exception{
		boolean result = mdao.isEmailAvailable(account_email);
		return result;
	}
	//회원가입시 아이디 중복검사
	public boolean isIdAvailable(String id)throws Exception{
		boolean result = mdao.isIdAvailable(id);
		return result;
	}

	//회원탈퇴
	public int deleteMember(Map<String, String> param) throws Exception{
		int result = mdao.deleteMember(param);
		return result;
	}
	//내정보수정하기
	public int editMyInfo(MemberDTO mdto, MemberFileDTO mfdto) throws Exception{
		
		Map<String, Object> editParam = new HashMap<>();
		editParam.put("targetColumn1", "birth");
		editParam.put("targetValue1", mdto.getBirth());
		editParam.put("targetColumn2", "account_email");
		editParam.put("targetValue2", mdto.getAccount_email());
		editParam.put("targetColumn3", "gender");
		editParam.put("targetValue3", mdto.getGender());
		editParam.put("targetColumn4", "id");
		editParam.put("targetValue4", mdto.getId());
		
		int result = mdao.editMyInfo(editParam);
		return result;
	}
	//비밀번호 수정하기
	public int editPw(Map<String, String> param) throws Exception{
		int result = mdao.editPw(param);
		return result;
	}

	//아이디 찾기용 이메일 체크하기
	public MemberDTO emailCheck(String account_email) throws Exception{
		MemberDTO mdto = mdao.emailCheck(account_email);
		return mdto;
	}

	//카카오톡 로그인을 위한 어세스토큰 가져오기
	public String getAccessToken (String code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			//    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);

			//    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=39543f4353dc8ce2c9268fc23c6d67e4");
			sb.append("&redirect_uri=http://localhost/member/kakaoLogin");
			sb.append("&code=" + code);
			bw.write(sb.toString());
			bw.flush();

			//    결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			//    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			//    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonElement element = JsonParser.parseString(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return access_Token;
	}

	//카카오톡 정보 가져오기
	public MemberDTO getloginInfo (String access_Token)throws Exception {

		//    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
		MemberDTO mdto = new MemberDTO();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");

			//    요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			JsonElement element = JsonParser.parseString(result);

			String id = element.getAsJsonObject().get("id").getAsString();
			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

			String nickname = properties.getAsJsonObject().get("nickname").getAsString();

			if(mdao.selectMyInfo(id)==null) {
				mdto.setId(id);
				mdto.setNickname(nickname);
				mdto.setPw("");
				mdto.setBirth("1999-12-31");
				mdto.setAccount_email("need@eat-together.com");

				int kakaoSignUpResult = mdao.signUpKakao(mdto);
				System.out.println("카카오톡 회원가입 진행 성공1 실패0 : " + kakaoSignUpResult);
			}else {
				System.out.println("이미 회원가입된 카카오계정입니다.");
				System.out.println(mdto.getAccount_email());
				mdto = mdao.selectMyInfo(id);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mdto;


	}

	//카카오톡 로그아웃하기
	public void kakaoLogout(String access_Token) {
		String reqURL = "https://kapi.kakao.com/v1/user/logout";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String result = "";
			String line = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
