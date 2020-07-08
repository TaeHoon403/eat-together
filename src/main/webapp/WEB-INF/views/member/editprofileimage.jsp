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

<!-- editprofileimage css  -->
<link rel="stylesheet" type="text/css"
	href="/resources/css/editprofileimage.css">
<!-- ******************* -->
<meta charset="UTF-8">
<title>프로필 이미지 수정</title>
</head>
<body>
	<form id="editProfileImageForm" action="/memberfile/uploadProc"
		enctype="multipart/form-data" method="post">
		<input type=file name=file id=upload>
		<div id='preview'></div>
		<button type="button" id="editProfileBtn" class="btn btn-warning">프로필이미지
			변경하기</button>
		<br>
		<button type=button class="btn btn-light" id="back"
			onClick="window.close()">창닫기</button>
	</form>

	<script>
		$("#editProfileBtn").on("click", function() {
			alert("프로필 이미지가 변경되었습니다.");
			document.getElementById('editProfileImageForm').submit();
			
		})

		var upload = document.querySelector('#upload');
		var preview = document.querySelector('#preview');

		upload.addEventListener('change', function(e) {
			var get_file = e.target.files;
			var image = document.createElement('img');
			var reader = new FileReader();

			reader.onload = (function(aImg) {
				console.log(1);
				return function(e) {
					console.log(3);
					aImg.src = e.target.result
				}
			})(image)

			if (get_file) {
				reader.readAsDataURL(get_file[0]);
				console.log(2);
			}

			preview.appendChild(image);
		})
	</script>
</body>
</html>