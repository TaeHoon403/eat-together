<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<!-- BootStrap4 -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<!-- BootStrap4 End-->

<!-- google font -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500;900&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap"
	rel="stylesheet">

<!-- findid css  -->
<link rel="stylesheet" type="text/css" href="/resources/css/findid.css">
<!-- ******************* -->
<meta charset="UTF-8">
<meta charset="UTF-8">
<title>아이디 찾기</title>
</head>
<body>

	<fieldset class = "findid_text">
		<legend>아이디 찾기</legend>
		<div class="signup_text">
			<label for="account_email" class="signup_text">이메일</label> <input
				type="text" class="form-control" id="account_email"
				name="account_email" placeholder="ex)asdf1234@naver.com"> <input
				type=button id=mail value="인증하기" class="btn btn-warning"><br>
			<div id=mail_div style="display: none;">
				인증번호 : <input type=text id=mail_text>
				<button type=button id=mail_accept class="btn btn-light">인증</button>
			</div>
			<div id=findid_result style="display:none;"></div>
		</div>
	</fieldset>

	<script>
		//account_email regex
		$("#account_email")
				.focusout(
						function() {
							if ($("#account_email").val() != "") {
								var account_email = $("#account_email").val();
								var account_emailregex = /[a-zA-Z0-9]*@[a-zA-Z0-9]*[.]{1}[a-zA-Z]{2,3}|([.]{1}[a-zA-Z]{2,3})$/;
								if (!account_emailregex.test(account_email)) {
									$("#account_email").val("");
									alert("유효한 이메일을 입력해주세요.");
									$("#account_email").focus();
								}
							}
						})

		//메일 인증 
		$("#mail").on("click", function() {
			if ($("#account_email").val() == "") {
				alert("이메일을 입력해주십시오.");
				$("#account_email").focus();
			} else {
				$.ajax({
					url : "/mail/mailSendingForId",
					type : "post",
					dataType : "json",
					data : {
						account_email : $("#account_email").val()
					}
				}).done(function(resplist) {
					if (resplist!=null) {
						alert("인증메일이 발송되었습니다.");
						$("#mail_div").css("display", "block");
						$("#mail_accept").on("click", function() {
							if ($("#mail_text").val() == resplist[0]) {
								$("#mail_text").attr("readonly", true);
								$("#mail_text").css("color", "blue");
								$("#mail_text").val("인증에 성공하였습니다.");
								
								var id = resplist[1];
								var maskId =  id.replace(/.{3}$/gi, "***");// 아이디 마스킹처리 정규식
								
								$("#findid_result").css("display", "block");
								$("#findid_result").append("<div>회원님의 아이디는"+ maskId +"입니다.</div>");
								$("#findid_result").append("<input type=button id=OkBtn value=확인 class='btn btn-warning' onclick='window.close();'>");
							} else {
								alert("회원가입시 입력한 이메일과 일치하지 않습니다.");
								$("#mail_text").val("");
								$("#mail_text").focus();
							}
						})
					} else {
						alert("이메일을 잘못 입력하였습니다.");
						$("#mail_text").val("");
						$("#mail_text").focus();
					}

				})
			}
		})

	</script>
</body>
</html>