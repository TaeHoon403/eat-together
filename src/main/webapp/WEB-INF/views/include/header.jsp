
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- header -->
<div class="container-fluid ">
	<div class="header-wrap  mt-3 px-0 mx-0">
		<div class="row">
			<div class="col-sm-1 logo-image ">
				<a href="/"><img src="/resources/img/logo.png"></a>
			</div>
			<div class="col-sm-10 mb-3">
				<div class="row navibar h5 text-center">
					<div class="col-sm-1">
						<div class="navi-menu">사이트소개</div>
					</div>
					<div class="col-sm-1">
						<div class="navi-menu">
							<a href="/party/toParty_New" id="toPartyNew">모임모집</a>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="navi-menu">
							<a href="/map/toMap" id="toMap">맛집검색</a>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="navi-menu">
							<a href="/party/partylist" id="toPartyList">모임검색</a>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="navi-menu">FAQ</div>
					</div>
					<div class="col-sm-1">
						<div class="navi-menu">공지사항</div>
					</div>
					<div class="col-sm-1">
						<div class="navi-menu">
						<c:choose>
							<c:when test="${loginInfo.id == 'administrator'}">	
								<a href="#" onclick="window.open('/admin/toAdmin', 'Admin','width=1300, height=800, location=no'); return false">관리자페이지</a></span>
							</c:when>
							<c:otherwise>
								<div id="goToMyPage">마이페이지</div>
							</c:otherwise>
						</c:choose>
						</div>
					</div>
				</div>
			</div>
			<c:if test="${loginInfo.id == null}">
				<div class="col-sm-1">
					<span class="main-login"> <a href="/member/loginview">로그인
					</a> / <a href="/member/signup_check">회원가입</a></span>
				</div>
			</c:if>
			<c:if test="${loginInfo.id != null}">
			<c:choose>
				<c:when test="${loginInfo.id == 'administrator'}">
					<div class="col-sm-1">
					<span class="main-login"> 관리자님, 환영합니다. </a> / <a
						href="/member/logoutProc" id="logout">로그아웃</a></span>			
					</div>
				</c:when>
				<c:otherwise>
					<div class="col-sm-1">
					<span class="main-login"> ${loginInfo.id}님, 환영합니다. </a> / <a
						href="/member/logoutProc" id="logout">로그아웃</a></span>
				</div>
				</c:otherwise>
			</c:choose>	
			</c:if>
		</div>
		
		
	</div>
</div>

<script>
	$("#logout").on("click", function() {
		alert("로그아웃되었습니다.");
	})
	$("#goToMyPage").on("click", function() {
		if ("${loginInfo.id}" == "") {
			alert("로그인 후 이용해주세요");
			location.replace('/');
		}else{
			location.replace('/member/mypage_myinfo');
		}
	})
</script>

