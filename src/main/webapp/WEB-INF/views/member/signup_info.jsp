<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
<!-- google font end-->

<!-- header,footer용 css  -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/index-css.css">
<!-- header,footer용 css  -->

<!-- signup_check css -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/signup-info.css">
<!-- signup_check css -->

<title>회원가입</title>
</head>
<body>
	<!-- ******************* -->
	<!-- header  -->
	<jsp:include page="/WEB-INF/views/include/header.jsp" />
	<!-- hedaer  -->
	<!-- ******************* -->

	<form action="/member/signupProc" method="post">
		<div class="article container">
			<div class="signup_text">
				<label for="id" class="signup_text">아이디</label> <input type="text"
					class="form-control" id="id" name="id" placeholder="영문+숫자 4~10글자">
				<button type=button id=dublCheck class="btn btn-light">중복확인</button>
				<div id=id_text style="display: none;"></div>
			</div>
			<div class="signup_text">
				<label for="pw" class="signup_text">비밀번호</label> <input
					type="password" class="form-control" id="pw" name="pw"
					placeholder="특수문자+숫자+영문 6~12글자">
			</div>
			<div class="signup_text">
				<label for="pw" class="signup_text">비밀번호 확인</label> <input
					type="password" class="form-control" id="pwCorrection">
				<div id="pw_text" style="display: none;"></div>
			</div>

			<div class="signup_text">
				<label for="nickname" class="signup_text">닉네임</label> <input
					type="text" class="form-control" id="nickname" name="nickname"
					placeholder="한글 2~6자">
			</div>

			<div class="signup_text">
				<label for="account_email" class="signup_text">이메일</label> <input
					type="text" class="form-control" id="account_email"
					name="account_email" placeholder="ex)eat-together@naver.com">
				<button type=button id=mail class="btn btn-light">인증하기</button><br>
				<div id=mail_div style="display: none;">
					인증번호 : <input type=text id=mail_text>
					<button type=button id=mail_accept class="btn btn-light">인증</button>
				</div>
			</div>

			<div class="signup_text">
				<label for="birth" class="signup_text">생년월일</label> <input
					type="text" class="form-control" id="birth" name="birth"
					placeholder="ex)19941122">
			</div>

			<div align=center>
				<button type=submit id=btn class="btn btn-warning signup_text">가입하기</button>
				<button type=reset class="btn btn-light signup_text" style="margin-left: 5px;">다시 작성하기</button>
			</div>
		</div>
	</form>

	<script>
		// form submit 전 체크하는 사항
		$("#btn").on("click", function() {
			if ($("#id_text").html() == "사용가능한 id입니다.") {
				if ($("#pw_text").html() == "비밀번호가 일치합니다.") {
					if ($("#nickname").val() != "") {
						if ($("#mail_text").val() != "") {
							if ($("#birth").val() != "") {
								alert("맛집갔다갈래 회원이 되신 것을 환영합니다!");
								return true;

							} else {
								alert("생년월일을 입력해주세요.");
							}
						} else {
							alert("이메일 인증을 해주세요.");
						}
					} else {
						alert("닉네임을 입력해주세요");
					}
				} else {
					alert("비밀번호를 확인해주세요.");
				}
			} else {
				alert("아이디 중복체크를 진행해주세요.");
			}

			return false;
		})

		//다시작성 버튼
		$("#reset").on("click", function() {
			$("#id").val("");
			$("#id_text").html("");
			$("#pw").val("");
			$("#pwCorrection").val("");
			$("#nickname").val("");
			$("#account_email").val("");
			$("#birth").val("");
		})

		//id regex
		$("#id").focusout(function() {
			var id = $("#id").val();
			var idregex = /^[a-z][a-zA-Z0-9]{4,10}$/;
			if ($("#id").val() != "") {
				if (!idregex.test(id)) {
					alert("아이디 조건을 확인하세요.");
					$("#id").val("");
				}
			}
		})

		//id 중복체크 후 수정 시 중복체크 다시 하도록 설정
		$("#id").keydown(function() {
			if ($("#id_text").html() != "") {
				$("#id_text").html("");
			}
		})

		//pw regex
		$("#pw")
				.focusout(
						function() {
							var pw = $("#pw").val();
							var pwregex = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{6,12}$/;
							if ($("#pw").val() != "") {
								if (!pwregex.test(pw)) {
									alert("비밀번호 조건을 확인하세요.");
									$("#pw").val("");
								}
							}
						})

		//pw 재확인
		$("#pwCorrection").focusin(function() {
			if ($("#pw").val() == "") {
				alert("비밀번호를 먼저 입력해주세요.");
				$("#pw").focus();
			} else {
				$("#pwCorrection").focusout(function() {
					if ($("#pwCorrection").val() != "") {
						if ($("#pw").val() != $("#pwCorrection").val()) {
							$("#pwCorrection").val("");
							alert("비밀번호가 일치하지 않습니다.");
							$("#pwCorrection").focus();
						} else {
							$("#pw_text").css("display", "block");
							$("#pw_text").css("color", "blue");
							$("#pw_text").html("비밀번호가 일치합니다.");
						}
					}
				})
			}
		})

		//nickname regex
		$("#nickname").focusout(function() {
			var nickname = $("#nickname").val();
			var nicknameregex = /^[가-힣]{2,6}$/;
			if ($("#nickname").val() != "") {
				if (!nicknameregex.test(nickname)) {
					$("#nickname").val("");
					alert("한글 2~6글자를 입력하세요.");
					$("#nickname").focus();
				}
			}
		})

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

		//birth regex
		$("#birth")
				.focusout(
						function() {
							var birth = $("#birth").val();
							var regex1 = /^(19[0-9][0-9]|200[0-5])(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;
							if ($("#birth").val() != "") {
								if (regex1.test(birth)) {
									birth = birth.replace(/^(19[0-9][0-9]|200[0-5])(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/,"$1-$2-$3");
									$("#birth").val(birth);
								} else {
									$("#birth").val("");
									alert("2005년 이전 출생자만 이용가능합니다.\n올바른 생년월일을 입력해주세요.");
									$("#birth").focus();
								}
							}
						})

		//아이디 중복체크
		$("#dublCheck").on("click", function() {
			if ($("#id").val() != "") {
				$.ajax({
					url : "/member/isIdAvailable",
					type : "post",
					data : {
						id : $("#id").val()
					}
				}).done(function(resp) {
					$("#id_text").css("display", "block");
					if (resp == "true") {
						$("#id_text").css("color", "blue");
						$("#id_text").html("사용가능한 id입니다.");
					} else if (resp == "false") {
						$("#id_text").css("color", "red");
						$("#id_text").html("사용 불가능한 id입니다.");
						$("#id").val("");
					}
				}).fail(function(error1, error2) {
					console.log(error1);
					console.log(error2);
				})
			} else {
				alert("아이디를 입력해주세요.");
			}

		})

		//메일 인증 
		$("#mail").on("click", function() {
			if ($("#account_email").val() == "") {
				alert("이메일을 입력해주십시오.");
				$("#account_email").focus();
			} else {
				$.ajax({
					url : "/mail/mailSending",
					type : "post",
					data : {
						account_email : $("#account_email").val()
					}
				}).done(function(resp) {
					if (resp != "") {
						alert("인증메일이 발송되었습니다.");
						$("#mail_div").css("display", "block");
						$("#mail_accept").on("click", function() {
							if ($("#mail_text").val() == resp) {
								$("#mail_text").attr("readonly", true);
								$("#mail_text").css("color", "blue");
								$("#mail_text").val("인증에 성공하였습니다.");
							} else {
								alert("인증문자열을 확인해주세요.");
								$("#mail_text").val("");
								$("#mail_text").focus();
							}
						})
					} else {
						alert("이미 사용중인 이메일입니다.");
						$("#mail_text").val("");
						$("#mail_text").focus();
					}

				})
			}
		})
	</script>

	<!-- ******************* -->
	<!-- footer  -->
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
	<!-- footer  -->
	<!-- ******************* -->

</body>
</html>