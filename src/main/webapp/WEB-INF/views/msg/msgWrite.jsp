<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://ssl.pstatic.net/static.gn/js/clickcrD.js"
	id="gnb_clickcrD" charset="utf-8"></script>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<title>Insert title here</title>

<script>
	$(function() {
		$("#idcheck").on("click", function() {
			var id = $("#msg_receiver").val();
			$.ajax({
				url : '../member/isIdAvailable?id=' + id,
				dataType : 'json',
				success : function(resp) {
					console.log(resp);
					if (resp == false) {
						alert("존재하는 회원입니다.");
						$("#msg_receiver").val(id);
						$("#msg_receiver").attr("readonly",true);
						$("#msg_receiver").css("background-color","DarkGray");
						$("#idcheck").val("확인됨");
					} else {
						alert("존재하지 않는 회원입니다.");
						$("#msg_receiver").val("");
						$("#idcheck").val("확인");
					}
				}
			})
		});
	})
</script>
</head>
<style>
.container {
	border: 1px solid black;
}
.idcheck{
	hide();
}
.container>div>div {
	border-bottom: 1px solid black;
}
</style>
<body>
	<div class="container" align="center">

		<div class="row">
			<div class="col-12">쪽지보내기</div>
		</div>
		<form action="msgSend" method="post">
			<div class="row" align="center">
				<div class="col-4">닉네임</div>
				<div class="col-8">
					<input type="text" name="msg_receiver" id="msg_receiver">
					<input type="button" id="idcheck" value="확인">
				</div>
				<div class="col-4">제목</div>
				<div class="col-8">
					<input type="text" name="msg_title" id="msg_title">
				</div>
				<div class="col-12">내용</div>
				<div class="col-12">
					<input type="text" name="msg_text" id="msg_text">
				</div>
				<div class="col-12">
					<button type="submit" id="submit">보내기</button>
					<button type="button" id="cancel">취소</button>
				</div>
			</div>
		</form>
	</div>
	<script>
		$("#cancle").on("click",function(){
			window.close();
		})
		
		$("#submit").on("click",function(){
			var result = $("#idcheck").val();
			console.log(result);
			
			if(result == '확인됨'){
				if($("#msg_title").val() != ""){
					if($("msg_text").val() != ""){
						return true;
					}else{
						alert("내용을 입력하세요");
						return false;
					}
				}else{
					alert("제목을 입력하세요");
					return false;
				}
			}else{
				alert("받는사람을 확인하세요.");
				return false;
			}
		})
	</script>
</body>
</html>
