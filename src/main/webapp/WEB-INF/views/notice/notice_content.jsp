<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>맛집갔다갈래 - ${contents.title}</title>
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
</head>
<script>
	$(function() {
		$("#NoticeModify").on("click", function() {
			location.href = "/notice/modify?seq=${contents.seq}";
		});

		$("#NoticeDelete").on("click", function() {
			var ask = confirm("정말 삭제하겠습니까?");
			if (ask) {
				location.href = "/notice/delete?seq=${contents.seq}";
			}
		});

		$("#toNoticeList").on("click", function() {
			location.href = "/notice/list";
		});

	})
</script>
<body>
	<!-- ******************* -->
	<!-- header  -->
	<jsp:include page="/WEB-INF/views/include/header.jsp" />
	<!-- hedaer  -->
	<!-- ******************* -->

	<div class="container">
		<div class="row">
			<div class="col-12 mt-3  mb-3">
				<h2 class="notice-title">공지사항 게시판</h2>
			</div>
		</div>


		<div class="row">
			<div class="col-sm-12">
				<div class="row">
					<div class="col-sm-12">
						<c:if test="${contents.importance eq '1'}">[중요]</c:if>
						<c:if test="${contents.importance eq '0'}">[일반]</c:if>

					</div>

				</div>
				<div class="row border-bottom border-dark">
					<div
						class="col-sm-12 board_subtitle notice-content-title  font-weight-bolder h3"><c:out value="${contents.title}" />
					</div>
				</div>

				<div class="row border-bottom border-dark">
					<div
						class="col-sm-2 board_subtitle  font-weight-bolder  text-center">글번호</div>
					<div class="col-sm-10">${contents.seq}</div>
				</div>
				<div class="row border-bottom border-dark">
					<div
						class="col-sm-2 board_subtitle  font-weight-bolder  text-center">작성일</div>
					<div class="col-sm-10">${contents.sDate}</div>
				</div>
				<div class="row border-bottom border-dark">
					<div
						class="col-sm-2 board_subtitle font-weight-bolder  text-center">조회수</div>
					<div class="col-sm-10">${contents.view_count}</div>
				</div>
				<div class="row border-bottom border-dark">
					<div
						class="col-sm-2 board_subtitle font-weight-bolder align-middle text-center py-3">내용</div>
					<div class="col-sm-10 align-middle py-3">${contents.contents}</div>
				</div>

				<div class="row border-bottom border-dark">
					<div
						class="col-sm-2 board_subtitle font-weight-bolder  text-center">첨부파일</div>
					<div class="col-sm-10">
						<c:if test="${!empty fileList }">
							<ul>
								<c:forEach var="i" items="${fileList }">
									<li><a href='/NoticeFile/downloadFile?seq=${i.seq}'>${i.oriname}</a></li>
								</c:forEach>
							</ul>
						</c:if>

					</div>
				</div>





				<div class="row mb-3">
					<div class="col-12">
						<c:if test="${sessionScope.loginInfo.id eq 'administrator'}">
							<button type="button" id="NoticeModify" class="btn btn-warning">수정하기</button>
							<button type="button" id="NoticeDelete" class="btn btn-danger">삭제하기</button>
						</c:if>
						<button type="button" id="toNoticeList" class="btn btn-secondary">목록으로</button>

					</div>

				</div>


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