<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 게시판 - 글목록</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
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
	
<link rel="stylesheet" type="text/css"
	href="/resources/css/notice-css.css">
	
	<script>
$(function(){

	$("#toWriteBtn").on("click",function(){
		location.href="/notice/write";
	});
	
});

</script>
</head>
<body>
<!-- ******************* -->
	<!-- header  -->
	<jsp:include page="/WEB-INF/views/include/header.jsp" />
	<!-- hedaer  -->
	<!-- ******************* -->
<div class="container">
		<div class="row">
			<div class="col-12 mt-3">
				<h2 class="notice-title">공지사항 게시판</h2>
			</div>
		</div>
		<div class="row">
			<div class="table-responsive">
				<table class="table border-bottom border-dark">
					<thead class="thead-dark">
						<tr>
							<th scope="col" class="text-center">#</th>
							<th scope="col">글제목</th>
							<th scope="col">작성일</th>
							<th scope="col">첨부파일</th>
							<th scope="col">조회수</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="i" items="${list}" varStatus="status">
						<tr>
							
							<c:if test="${i.importance eq '1'}">
							<th scope="row" class="imp text-center" style="background-color:#ff7761;color:#ffffff;">
							중요
							</th>
							</c:if>
							
							<c:if test ="${i.importance eq '0'}">
							<th scope="row" class="text-center">
							${i.seq}
							</th>
							</c:if>
							
							<td><a href="/notice/contents?seq=${i.seq}"><c:out value="${i.title}"/></a></td>
							<td>${i.sDate}</td>
							<td><c:if test="${i.attachment ne '0'}">&#x1F4BE;</c:if></td>
							<td>${i.view_count}</td>
							
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row mb-5">
			<div class="col-2">
				
			</div>
			<div class="col-8">
				${navi}
			</div>
			<div class="col-2">
			<c:if test="${sessionScope.loginInfo.id eq 'administrator'}">
				<button class="btn btn-primary" id="toWriteBtn">글쓰기</button>
			</c:if>
			</div>
		</div>
	</div>




	<!-- ******************* -->
	<!-- footer  -->
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />

	<!-- footer  -->
	<!-- ******************* -->
</body>
</html>