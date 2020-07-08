<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맛집갔다갈래 - 제작팀 소개</title>
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
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap"
	rel="stylesheet">

<!-- google font end-->



<!-- ******************* -->
<!-- header,footer용 css  -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/index-css.css">
<!-- header,footer용 css  -->
<!-- ******************* -->
<link rel="stylesheet" type="text/css" href="/resources/css/info.css">
<style>
body {
	overflow-x: hidden;
}
</style>
<script>
	$(function() {
		$("#toJoinBtn").on("click", function() {
			location.href = "/member/signup_check";
		});
	})
</script>
</head>

<body>
	<!-- ******************* -->
	<!-- header  -->
	<jsp:include page="/WEB-INF/views/include/header.jsp" />
	<!-- hedaer  -->
	<!-- ******************* -->

	<div class="container-fluid big-title mt-5 pt-3 mb-3 pb-3">
		<div class="row">
			<div class="col-12 col-sm-12 text-center">
				<h4 class="info-s-title">맛집 동행 찾기 서비스</h4>
			</div>
		</div>
		<div class="row">
			<div class="col-12 col-sm-12 text-center">
				<h1 class="info-title text-center">맛집 갔다 갈래</h1>

			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-12 my-3"></div>
		</div>
		<div class="row">
			<div class="col-12 col-sm-5">
				<img src="/resources/img/logo/eattogether-logo-rectangle.png"
					class="info-logo">
			</div>
			<div class="col-12 col-sm-7 text-center">
				<h3 class="info-copyright mt-5 pt-5 ">
					"우리는 사람과 식탁을 연결하고,<br>더 즐거운 삶의 방식을 만듭니다."
				</h3>
				<a href="/info/toIntroduction" class="badge badge-warning">사이트
					소개</a> <a href="/info/aboutUs" class="badge badge-success">제작팀 소개</a>

			</div>
		</div>
		<div class="row">
			<div class="col-12 col-sm-12 my-1"></div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-12 col-sm-12 text-center mt-5 pt-5">
				<h3 class="page-title">제작팀 소개</h3>
				<hr class="title-hr" />
			</div>

		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-12 col-sm-12 my-5 text-center">
			<img src="/resources/img/team-photo/teamphoto.jpg" class="teamphoto" alt="team photo"/>
			</div>
		</div>
	</div>

	<div class="container-fluid mx-0 px-0">
		<div class="row">
			<div class="col-12 col-sm-12">
				<h1 class="strapline">MEET THE TEAM</h1>

			</div>
		</div>
	</div>
	<div class="container-fluid mx-0 px-0 back-00bfff">
		<div class="container">
			<div class="row">
			<div class="col-12 col-sm-12 my-5"></div>
			</div>
			<div class="row">
					<div class="col-12 col-sm-4 text-center">
						<img src="/resources/img/team-photo/suji.png" alt="SUJI LEE">
					</div>
					<div class="col-12 col-sm-4 text-center">
						<img src="/resources/img/team-photo/yeji.png" alt="YEJI SHIN">
					</div>
					<div class="col-12 col-sm-4 text-center">
						<img src="/resources/img/team-photo/jieun.png" alt="JIEUN PARK">
					</div>
				
			</div>
			<div class="row">
					<div class="col-12 col-sm-4 text-center">
						<p class="team-name">이수지</p>
						<p class="deprt-name">Developer</p> 
						<p class="work">스크롤로 만들고 시파다아아아아</p>
						<p class="contact">&#128231; sj3821@gmail.com</p>
						
					</div>
					<div class="col-12 col-sm-4 text-center">
						<p class="team-name">신예지</p>
						<p class="deprt-name">Developer</p> 
							<p class="work">스크롤로 만들고 시파다아아아아</p>
							<p class="contact">&#128231; sinyeji958@gmail.com</p>
						
					</div>
					<div class="col-12 col-sm-4 text-center">
						<p class="team-name">박지은</p>
						<p class="deprt-name">Developer</p> 
							<p class="work">스크롤로 만들고 시파다아아아아</p>
						<p class="contact">&#127968; blog.naver.com/cloudia101</p>
					</div>
			</div>
			<div class="row mt-5">
					<div class="col-12 col-sm-4 text-center">
						<img src="/resources/img/team-photo/sangbin.png" alt="SANGBIN YOON">
					</div>
					<div class="col-12 col-sm-4 text-center">
						<img src="/resources/img/team-photo/yooseung.png" alt="YOOSEUNG CHA">
					</div>
					<div class="col-12 col-sm-4 text-center">
						<img src="/resources/img/team-photo/taehoon.png" alt="TAEHOON KIM">
					</div>
				
			</div>
			<div class="row">
					<div class="col-12 col-sm-4 text-center">
						<p class="team-name">윤상빈</p>
						<p class="deprt-name">Developer</p> 
							<p class="work">스크롤로 만들고 시파다아아아아</p>
							<p class="contact">&#128231; hamthink@gmail.com</p>
					</div>
					<div class="col-12 col-sm-4 text-center">
						<p class="team-name">차유승</p>
						<p class="deprt-name">Developer</p> 
							<p class="work">스크롤로 만들고 시파다아아아아</p>
						<p class="contact">&#127968; blog.naver.com/uououoi</p>
					</div>
					<div class="col-12 col-sm-4 text-center">
						<p class="team-name">김태훈</p>
						<p class="deprt-name">Developer</p>
							<p class="work">스크롤로 만들고 시파다아아아아</p>
							<p class="contact">&#128231; taehoon830@gmail.com</p>
							 
					</div>
			</div>
			<div class="row">
			<div class="col-12 col-sm-12 my-5">
			
			</div></div>
		</div>

	</div>


	<!-- ******************* -->
	<!-- footer  -->
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />

	<!-- footer  -->
	<!-- ******************* -->
</body>
</html>