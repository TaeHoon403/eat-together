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

<!-- editpw css  -->
<link rel="stylesheet" type="text/css" href="/resources/css/editpw.css">
<!-- ******************* -->
<meta charset="UTF-8">
<title>비밀번호 수정</title>
</head>
<body>
	<form id="editPwForm" action="editPwProc">
		<div class="article container">
			<div class="editpw_text">
				<label for="pw" class="editpw_text">비밀번호</label> <input
					type="password" class="form-control" id="pw" name="pw"
					placeholder="특수문자+숫자+영문 6~12글자">
			</div>
			<div class="editpw_text">
				<label for="pw" class="editpw_text">비밀번호 확인</label> <input
					type="password" class="form-control" id="pwCorrection">
				<div id="pw_text" style="display: none;"></div>
			</div>
		</div>
		<button type="button" class="btn btn-warning" id="editPwBtn">
			수정하기</button>
		<button type=button class="btn btn-light" id="back"
			onClick="window.close()">되돌아가기</button>
	</form>
	<script>
		$("#editPwBtn").on("click", function() {
			alert("비밀번호가 수정되었습니다.");
			document.getElementById('editPwForm').submit();
			window.open("about:blank", "_self").close();
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
	</script>
</body>
</html>