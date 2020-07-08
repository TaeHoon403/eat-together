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
<title>Insert title here</title>
<style>
	#msg_title>a{
		color: black;
	}
</style>

<script>
	$(function() {
		$("#list_admin").on("click", function() {
			location.href = "msg_list_admin";
		})
		$("#list_sender").on("click", function() {
			location.href = "msg_list_sender";
		})
		$("#list_receiver").on("click", function() {
			location.href = "msg_list_receiver";
		})
		$(".msg_text").hide();
		$(".msg_title").on("click",function(){
			$(".msg_text").show();
		})
		
	})
</script>
<script>
function msgSenderDel(msg_seq){
	location.href="msgSenderDel?msg_seq="+msg_seq;
		alert("삭제성공");
}
function msgWritePopUp(){
	var name = "popup.test";
	var option ="width=500,height=400 location=no";
	window.open("msgWrite",name,option);
}
function msgViewPopUp(msg_seq){
	var name=msg_seq;
	var option = "width=500,height=400 location=no";
	window.open("msgViewSend?msg_seq="+msg_seq,msg_seq,option);
}
</script>
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
			<div class="container">
				<div class="row">
					<div class="col-12" align="center">
						<button type=button id=list_admin>관리자</button>
						<button type=button id=list_sender>받은 쪽지함</button>
						<button type=button id=list_receiver>보낸 쪽지함</button>
					</div>
				</div>
				<div class="row" align="center">
					<div class="col-2">읽음</div>
					<div class="col-2">받는사람</div>
					<div class="col-4">제목</div>
					<div class="col-2">날짜</div>
					<div class="col-2">삭제</div>
					<c:if test="${empty list}">
						<div class="col-12" align>쪽지가 없습니다.</div>
					</c:if>


					<c:forEach var="i" items="${list}" varStatus="status">
						<div class="col-2">

							<c:choose>
								<c:when test="${i.msg_view==0}">
									<svg width="1em" height="1em" viewBox="0 0 16 16"
										class="bi bi-envelope" fill="currentColor"
										xmlns="http://www.w3.org/2000/svg"> <path
											fill-rule="evenodd"
											d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2zm13 2.383l-4.758 2.855L15 11.114v-5.73zm-.034 6.878L9.271 8.82 8 9.583 6.728 8.82l-5.694 3.44A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.739zM1 11.114l4.758-2.876L1 5.383v5.73z" />
</svg>
								</c:when>
								<c:when test="${i.msg_view==1}">
									<svg width="1em" height="1em" viewBox="0 0 16 16"
										class="bi bi-envelope-open" fill="currentColor"
										xmlns="http://www.w3.org/2000/svg">
  <path fill-rule="evenodd"
											d="M8.47 1.318a1 1 0 0 0-.94 0l-6 3.2A1 1 0 0 0 1 5.4v.818l5.724 3.465L8 8.917l1.276.766L15 6.218V5.4a1 1 0 0 0-.53-.882l-6-3.2zM15 7.388l-4.754 2.877L15 13.117v-5.73zm-.035 6.874L8 10.083l-6.965 4.18A1 1 0 0 0 2 15h12a1 1 0 0 0 .965-.738zM1 13.117l4.754-2.852L1 7.387v5.73zM7.059.435a2 2 0 0 1 1.882 0l6 3.2A2 2 0 0 1 16 5.4V14a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V5.4a2 2 0 0 1 1.059-1.765l6-3.2z" />
</svg>
								</c:when>
							</c:choose>
						</div>


						<div class="col-2">${i.msg_receiver}</div>
						<div class="col-4 msg_title" id="msg_title">
							<a href="javascript:msgViewPopUp(${i.msg_seq})"><c:out
									value="${i.msg_title}" /></a>
						</div>
						<div class="col-2">${i.msg_date}</div>
						<div class="col-2">
							<button type="button"
								onclick="location.href='javascript:msgSenderDel(${i.msg_seq})'">삭제</button>

						</div>

					</c:forEach>
				</div>
				<div class="row">
				<div class="col-12">
					${navi}
				</div>
				</div>
				<div class="row">
					<div class="col-12" align="center">
						<button id="writeMsg">쪽지보내기</button>
					</div>
				</div>

			</div>
		</div>
	</div>

	<!-- ******************* -->
	<!-- footer  -->
	<div id=footer-container>
		<jsp:include page="/WEB-INF/views/include/footer.jsp" />
	</div>
	<!-- footer  -->
	<!-- ******************* -->
	<script>
		$("#writeMsg").on("click", function() {
			location.href = "javascript:msgWritePopUp()";
		})
	</script>
</body>
</html>