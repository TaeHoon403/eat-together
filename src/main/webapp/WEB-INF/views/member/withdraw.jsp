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
<!-- header,footer용 css  -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/index-css.css">
<!-- header,footer용 css  -->
<!-- ******************* -->
<!-- withdraw용 css -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/withdraw.css">
<!-- ************* -->
<meta charset="UTF-8">
<title>회원탈퇴</title>
</head>
<body>
	<!-- ******************* -->
	<!-- header  -->
	<jsp:include page="/WEB-INF/views/include/header.jsp" />
	<!-- header  -->
	<!-- ******************* -->


	<div class="jumbotron jumbotron-fluid">
		<div class="container" id="withdraw-container">
			<h1 class="display-4">${loginInfo.id}
				님이 참여한 모임 00개로<br>00명의 새로운 맛집 친구를 만났습니다.
			</h1>
			<br>
			<p class="lead">
				앞으로 더 많은 맛집 친구들이 기다리고 있습니다.<br>정말로 탈퇴하시겠습니까?
			</p>
		</div>
	</div>
	<form action="/member/withdrawProc" method="post" class="withdraw_text">
		<div class="withdraw_text">
			<label for="pw" class="withdraw_text">비밀번호</label> <input
				type="password" class="form-control" id="pw" name="pw">
		</div>
		<div class="withdraw_text">
			<label for="pw" class="withdraw_text">비밀번호 확인</label> <input
				type="password" class="form-control" id="pwCorrection">
			<div id="pw_text" style="display: none;"></div>
		</div>
		<button type=submit class="btn btn-light" id="withdraw">탈퇴하기</button>
		<button type="button" class="btn btn-warning" id="back">되돌아가기</button>
	</form>

	<script>
		$("#withdraw").on("click", function() {
			if ($("#pw").val() == "") {
				alert("비밀번호를 먼저 입력해주세요.");
				return false;
			} else if ($("#pwCorrection").val() == "") {
				alert("비밀번호를 한번 더 입력해주세요.");
				return false;
			} else if ($("#pw").val() != $("#pwCorrection").val()) {
				alert("비밀번호가 일치하지 않습니다.");
				return false;
			} else {
				alert("탈퇴가 완료되었습니다.\n#See_you_again!");
				return true;
			}
		})
		$("#back").on("click", function(){
			location.replace("/member/mypage_myinfo");
		})
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