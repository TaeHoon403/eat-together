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
<!-- mypage용 css  -->
<link rel="stylesheet" type="text/css" href="/resources/css/mypage.css">
<!-- ******************* -->
<!-- menubar용 css  -->
<link rel="stylesheet" type="text/css" href="/resources/css/menubar.css">
<!-- ******************* -->
<meta charset="UTF-8">
<title>내 정보</title>
</head>
<body>
	<!-- ******************* -->
	<!-- header  -->
	<jsp:include page="/WEB-INF/views/include/header.jsp" />
	<!-- header  -->
	<!-- ******************* -->


	<div id=mypage-container>
		<jsp:include page="/WEB-INF/views/include/menubar.jsp" />
		<div id=contents>
			<table class="table">
				<thead class="thead-dark">
					<tr>
						<th scope="col" colspan=12>My Information</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th scope="row">ID</th>
						<td class="myinfo_text">${loginInfo.id}</td>
					</tr>
					<tr>
						<th scope="row">NICKNAME</th>
						<td class="myinfo_text">${loginInfo.nickname}</td>
					</tr>
					<tr>
						<th scope="row">BIRTH</th>
						<td class="myinfo_text">${loginInfo.birth}</td>
					</tr>
					<tr>
						<th scope="row">EMAIL</th>
						<td class="myinfo_text">${loginInfo.account_email}</td>
					</tr>
					<tr>
						<th scope="row"></th>
						<td class="myinfo_text">
							<button type="button" class="btn btn-warning" id="editMyInfo">내정보
								수정</button>
							<button type=button class="btn btn-light" id="withdraw">회원탈퇴</button>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<script>
		$("#withdraw").on("click", function() {
			var result = confirm("정말로 회원탈퇴를 진행하시겠습니까?");
			console.log(result);
			if(result){
				location.replace('/member/withdrawView');
			}else{
				location.replace('/member/mypage_myinfo')
			}

		})
		$("#editMyInfo").on("click", function() {
			location.replace('/member/editMyInfo');
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