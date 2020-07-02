package coma.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import coma.spring.dto.MemberDTO;
import coma.spring.service.MemberService;

@Controller
@RequestMapping("/mail/")
public class EmailController {

	@Inject
	JavaMailSender mailSender; 

	@Autowired
	private MemberService mservice;
	
	@Autowired
	private HttpSession session;

	//mailSending 랜덤 문자열 생성하기
	public String getRandomString(){
		char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};

		StringBuffer sb = new StringBuffer();
		Random rn = new Random();

		for( int i = 0 ; i < 15 ; i++ ){
			sb.append( charaters[ rn.nextInt( charaters.length ) ] );
		}
		return sb.toString();
	}

	//회원가입시 인증 메일 보내기
	@RequestMapping("mailSending")
	@ResponseBody
	public String mailSender(@RequestParam String account_email) throws Exception{ 
		String username = "eat-together";
		String password = "aktwlqrkTekrkffo?";
		String resp;

		boolean result = mservice.isEmailAvailable(account_email);
		System.out.println("이메일 사용가능한가? :" + false);
		if(result==false) {
			resp = "";
			return resp;
		}else {

			String dice = this.getRandomString();
			System.out.println("랜덤문자열 : " + dice);

			String recipient = account_email;
			String subject = "맛집갔다갈래 회원가입인증 이메일입니다.";
			String body = dice + " 인증문자열를 이메일 인증란에 입력하여주시기 바랍니다.";

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.daum.net");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,password);
				}
			});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("no-reply@eat-together.net"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(account_email));
				message.setSubject(subject);
				message.setText(body);
				Transport.send(message);
				resp = dice;

				System.out.println("OK");
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

			return resp;
		}
	}
	
	//아이디 찾기 인증 메일 보내기
	@RequestMapping("mailSendingForId")
	@ResponseBody
	public List<String> mailSenderForId(@RequestParam String account_email) throws Exception{ 
		String username = "eat-together";
		String password = "aktwlqrkTekrkffo?";
		List<String> resplist = new ArrayList<>();

		String input_account_email = account_email;
		System.out.println("아이디 찾기용 입력한 이메일 : " + account_email);
		MemberDTO mdto = mservice.emailCheck(input_account_email);
		
		
		if(mdto==null) {
			System.out.println("이메일 불일치");
			resplist = null;
			return resplist;
			
		}else {
			System.out.println("이메일 일치");
			String dice = this.getRandomString();
			System.out.println("랜덤문자열 : " + dice);
			
			String id = mdto.getId();
			resplist.add(0, dice);
			resplist.add(1, id);

			String recipient = account_email;
			String subject = "맛집갔다갈래 회원가입인증 이메일입니다.";
			String body = dice + " 인증문자열를 이메일 인증란에 입력하여주시기 바랍니다.";

			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.daum.net");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,password);
				}
			});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("no-reply@eat-together.net"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(account_email));
				message.setSubject(subject);
				message.setText(body);
				Transport.send(message);

				System.out.println("OK");
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

			return resplist;
		}
	}

	
//	//비밀번호 찾기 인증 메일 보내기
//	@RequestMapping("mailSendingForPw")
//	@ResponseBody
//	public String mailSenderForPw(@RequestParam String account_email, String id) throws Exception{
//		String username = "eat-together";
//		String password = "aktwlqrkTekrkffo?";
//		String resp;
//		
//		if() {
//			System.out.println("이메일 불일치");
//			resp = "";
//			return resp;
//		}else {
//			System.out.println("이메일 일치");
//			String dice = this.getRandomString();
//			System.out.println("랜덤문자열 : " + dice);
//
//			String recipient = account_email;
//			String subject = "맛집갔다갈래 회원가입인증 이메일입니다.";
//			String body = dice + " 인증문자열를 이메일 인증란에 입력하여주시기 바랍니다.";
//
//			Properties props = new Properties();
//			props.put("mail.smtp.host", "smtp.daum.net");
//			props.put("mail.smtp.socketFactory.port", "465");
//			props.put("mail.smtp.socketFactory.class",
//					"javax.net.ssl.SSLSocketFactory");
//			props.put("mail.smtp.auth", "true");
//			props.put("mail.smtp.port", "465");
//
//			Session session = Session.getDefaultInstance(props,
//					new javax.mail.Authenticator() {
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(username,password);
//				}
//			});
//
//			try {
//
//				Message message = new MimeMessage(session);
//				message.setFrom(new InternetAddress("no-reply@eat-together.net"));
//				message.setRecipients(Message.RecipientType.TO,
//						InternetAddress.parse(account_email));
//				message.setSubject(subject);
//				message.setText(body);
//				Transport.send(message);
//				resp = dice;
//
//				System.out.println("OK");
//			} catch (MessagingException e) {
//				throw new RuntimeException(e);
//			}
//
//			return resp;
//		}
//	}
	
	
}
