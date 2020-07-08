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

<!-- google font end-->

<!-- ******************* -->
<!-- header,footer용 css  -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/index-css.css">
<!-- header,footer용 css  -->
<!-- ******************* -->
<!-- page용 css  -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/editmyinfo.css">
<!-- ******************* -->
<!-- menubar용 css  -->
<link rel="stylesheet" type="text/css" href="/resources/css/menubar.css">
<!-- ******************* -->
<meta charset="UTF-8">
<title>내 정보 수정</title>
</head>
<body>
	<!-- ******************* -->
	<!-- header  -->
	<jsp:include page="/WEB-INF/views/include/header.jsp" />
	<!-- header  -->
	<!-- ******************* -->

	<!-- contextpath 변수 정의-->
	<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>

	<!-- contextpath 변수정의 -->


	<div id=mypage-container>
		<jsp:include page="/WEB-INF/views/include/menubar.jsp" />
		<div id=contents>
			<form action="/member/editMyInfoProc" method="post"
				enctype="multipart/form-data">
				<table class="table" id="mypage_table">
					<thead class="thead-dark">
						<tr>
							<th scope="col" colspan=12>My Information</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th scope="row">PROFILE IMAGE</th>
							<td class="myinfo_text" id="profile_box"><br>
								<div class="edit_text">
									<div id='preview'></div>
									<br> <label class="btn btn-secondary btn-file">
										업로드하기 <input type="file" id="profile" name="profile"
										style="display: none;">
									</label>
								</div></td>
						</tr>
						<tr>
							<th scope="row">ID</th>
							<td class="edit_text">${loginInfo.id}</td>
						</tr>
						<tr>
							<th scope="row">PASSWORD</th>
							<td class="edit_text"><button type=button
									class="btn btn-light"
									onclick="window.open('/member/editPw','비밀번호 수정하기','width=430,height=500,location=no,status=no,scrollbars=yes');"
									id="pwEdit">비밀번호 수정하기</button></td>
						</tr>
						<tr>
							<th scope="row">NICKNAME</th>
							<td class="edit_text">${mdto.nickname}</td>
						</tr>
						<tr>
							<th scope="row">BIRTH</th>
							<td class="edit_text"><input type="text"
								class="form-control" id="birth" name="birth"
								value="${mdto.birth}"></td>
						</tr>
						<tr>
							<th scope="row">GENDER</th>
							<td class="edit_text">
								<div class="edit_text">
									<input type="radio" id="gender_1" name="gender" value="1"><label
										for="gender_1" id="gender_text">남</label> <input type="radio"
										id="gender_2" name="gender" value="2"><label
										for="gender_2" id="gender_text">여</label>
								</div>
							</td>
						</tr>
						<tr>
							<th scope="row">EMAIL</th>
							<td class="edit_text"><input type=text id="account_email"
								name="account_email" value="${mdto.account_email}"> <input
								type=button id=mail value="인증하기"><input type=text id="isEmailEdited" style="display: none;" value="0"><br>
								<div id=mail_div style="display: none;">
									인증번호 : <input type=text id=mail_text>
									<button type=button id=mail_accept>인증</button>
								</div></td>
						</tr>
						<tr>
							<th scope="row"></th>
							<td class="edit_text">
								<button type="submit" class="btn btn-warning" id="editBtn">
									변경사항 저장하기</button>
								<button type=button class="btn btn-light" id="back">되돌아가기</button>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
	<script>
		window.onload = function() {
			
			//이메일을 수정했을 경우 인증이 필요하다
			$("#account_email").keydown(function() {
				$("#isEmailEdited").val("");
			})

			// form submit 전 체크하는 사항
			$("#editBtn").on("click", function() {
				if ($("#nickname").val() != "") {
					if ($("#isEmailEdited").val() != "") {
						if ($("#birth").val() != "") {
							if ($('input:radio[name=gender]').is(':checked')) {
								alert("회원정보가 수정되었습니다!");
								return true;
							} else {
								alert("성별을 입력해주세요.");
							}
						} else {
							alert("생년월일을 입력해주세요.");
						}
					} else {
						alert("수정한 이메일 인증을 해주세요.");
					}
				} else {
					alert("닉네임을 입력해주세요");
				}
				return false;
			})

			//by 지은, 이미지를 수정할 때에 미리보기로 자신이 없로드한 사진을 보여준다_20200708
			var upload = document.querySelector('#profile');
			var preview = document.querySelector('#preview');

			upload.addEventListener('change', function(e) {
				var get_file = e.target.files;
				var image = document.createElement('img');
				var reader = new FileReader();

				reader.onload = (function(aImg) {
					console.log(1);
					return function(e) {
						console.log(3);
						aImg.src = e.target.result
					}
				})(image)

				if (get_file) {
					reader.readAsDataURL(get_file[0]);
					console.log(2);
				}

				preview.appendChild(image);
			})

			//by지은, back버튼 생성_20200708
			$("#back").on("click", function() {
				location.replace('/member/mypage_myinfo');
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
			//birth regex
			$("#birth")
					.focusout(
							function() {
								var birth = $("#birth").val();
								var regex1 = /^(19[0-9][0-9]|200[0-5])(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;
								if ($("#birth").val() != "") {
									if (regex1.test(birth)) {
										birth = birth
												.replace(
														/^(19[0-9][0-9]|200[0-5])(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/,
														"$1-$2-$3");
										$("#birth").val(birth);
									} else {
										$("#birth").val("");
										alert("2005년 이전 출생자만 이용가능합니다.\n올바른 생년월일을 입력해주세요.");
										$("#birth").focus();
									}
								}
							})

			//이메일 regex
			$("#account_email")
					.focusout(
							function() {
								if ($("#account_email").val() != "") {
									var account_email = $("#account_email")
											.val();
									var account_emailregex = /[a-zA-Z0-9]*@[a-zA-Z0-9]*[.]{1}[a-zA-Z]{2,3}|([.]{1}[a-zA-Z]{2,3})$/;
									if (!account_emailregex.test(account_email)) {
										$("#account_email").val("");
										alert("유효한 이메일을 입력해주세요.");
										$("#account_email").focus();
									}
								}
							})

			//이메일 중복체크
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
									$("#isEmailEdited").val("0");
								} else {
									alert("인증문자열을 확인해주세요.");
									$("#mail_text").val("");
									$("#mail_text").focus();
								}
							})
						} else {
							alert("이메일 변동사항이 없습니다.")
							$("#isEmailEdited").val("0");
						}

					})
				}
			})

		}
	</script>

	<!-- ******************* -->
	<!-- footer  -->
	<div id=footer-container>
		<jsp:include page="/WEB-INF/views/include/footer.jsp" />
	</div>
	<!-- footer  -->
	<!-- ******************* -->
</body>
</html>