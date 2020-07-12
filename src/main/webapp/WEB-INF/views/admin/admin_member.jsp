<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/admin.css">
<title>Admin-회원관리</title>
</head>
<body>
	<div class="container-fluid mx-0 px-0">
		<div class="row mx-0">

			<div class="col-3 mx-0 px-0"><jsp:include
					page="/WEB-INF/views/include/admin_sidebar.jsp" /></div>
			<div class="col-9">
				<div class="row">
					<div class="col-12 col-sm-12 mt-3">
						<h2 class="admin-h2">회원관리</h2>
					</div>
				</div>
				<div class="row">
					<div class="col-12 col-sm-12 mt-3">
						<form action="/admin/searchByOption" method="post">
							<div class="form-group">
								<label for="selectByOption">조건정렬</label> <select
									class="form-control" id="selectByOptionn" name=option>
									<option value="join_date">가입일자</option>
									<option value="report_count">신고 수</option>
								</select><br>
								<button type="submit" class="btn btn-dark">검색</button>
							</div>
						</form>
					</div>
				</div>
				<div class="row">
					<div class="col-12  col-sm-12">
						<div class="row">
							<div class="table-responsive">
								<table class="table border-bottom border-dark">
									<thead class="thead-dark">
										<tr>
											<th scope="col">아이디</th>
											<th scope="col">닉네임</th>
											<th scope="col">생년월일</th>
											<th scope="col">성별</th>
											<th scope="col">이메일</th>
											<th scope="col">가입일자</th>
											<th scope="col">누적신고수</th>
											<th scope="col">탈퇴</th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${empty mlist}">
												<tr>
													<td colspan=12 class="myinfo_text">가입한 회원이 존재하지 않습니다.</td>
												</tr>
											</c:when>
											<c:when test="${!empty mlist}">
												<c:forEach var="i" items="${mlist}">
													<tr>
														<td class="admin_text">${i.id}</td>
														<td class="admin_text">${i.nickname}</td>
														<td class="admin_text">${i.birth}</td>
														<td class="admin_text" id='gender'>${i.gender}</td>
														<td class="admin_text">${i.account_email}</td>
														<td class="admin_text">${i.sdate}</td>
														<td class="admin_text">${i.report_count}</td>
														<td class="myinfo_text">탈퇴</td>
													</tr>
												</c:forEach>
											</c:when>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
						<div class="row mb-5">
							<div class="col-2"></div>
							<div class="col-8">${navi}</div>
							<div class="col-2">
								<c:if test="${sessionScope.loginInfo.id eq 'administrator'}">
									<button class="btn btn-primary" id="toWriteBtn">글쓰기</button>
								</c:if>
							</div>
						</div>
					</div>
				</div>

			</div>

		</div>

	</div>
	<script>
		//by 지은, 성별의 int 값을 jsp에서 남여로 출력하는 과정_20200708
		var gender = $("#gender").val();
		if (gender == 1) {
			$("#gender_text").html("남");
		} else {
			$("#gender_text").html("여");
		}
	</script>
</body>
</html>

