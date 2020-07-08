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
<title>내 리뷰</title>
</head>
<body>
	<!-- ******************* -->
	<!-- header  -->
	<jsp:include page="/WEB-INF/views/include/header.jsp" />
	<!-- hedaer  -->
	<!-- ******************* -->


	<div id=mypage-container>
		<jsp:include page="/WEB-INF/views/include/menubar.jsp" />
		<div id=contents>
			<table class="table" id="mypage_table">
				<thead class="thead-dark">
					<tr>
						<th scope="col" colspan=12>My Review</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th scope="row" class="myinfo_text">No.</th>
						<td class="myinfo_text">리뷰내용</td>
						<td class="myinfo_text">별점</td>
					</tr>

					<c:if test="${empty reviewList}">
						<tr>
							<td colspan=12 class="myinfo_text">직접 남긴 리뷰가 없습니다. 맛집을 탐방해보세요!</td>
						</tr>
						<tr>
							<td class="myinfo_text"><button type="button" class="btn btn-warning"
									text-center onclick="location.href='/party/partylist'">모임 검색하기</button></td>
						</tr>
					</c:if>
					<c:if test="${!empty reviewList}">
						<c:forEach var="i" items="${reviewList}">
							<tr>
								<th scope="row">${i.seq}</th>
								<td class="myinfo_text">${i.content}</td>
								<td class="myinfo_text">${i.rating}</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan=12>
								<nav id="pagenavi">
									<ul class="pagination justify-content-center" id="navibtn">
										${navi}
									</ul>
								</nav>
							</td>
						</tr>
					</c:if>

				</tbody>
			</table>
		</div>
	</div>



	<!-- ******************* -->
	<!-- footer  -->
	<div id=footer-container>
		<jsp:include page="/WEB-INF/views/include/footer.jsp" />
	</div>
	<!-- footer  -->
	<!-- ******************* -->
</body>
</html>