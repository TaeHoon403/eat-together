<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
	$(function() {

		$("#partyModify").on("click", function() {
			location.href = "/party/partymodify?seq=${con.seq}";
		});

		$("#partyDelete").on("click", function() {
			var ask = confirm("정말 삭제하겠습니까?");
			if (ask) {
				location.href = "/party/partydelete?seq=${con.seq}";
			}
		});

	})
</script>

<!-- 맛집 참가 modal -->

<div class="modal-header">
	<h5 class="modal-title" id="exampleModalLabel">
		<b>${con.title}</b>
	</h5>
	<button type="button" class="close" data-dismiss="modal"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
</div>
<div class="modal-body">
	<table class="table">
		<tbody>
			<tr>
				<td class="badges">
					<div>
						<span class="badge badge-pill badge-light drinking"> <c:choose>
								<c:when test="${con.drinking  eq '1'}">
														음주OK
											</c:when>
								<c:otherwise>
														음주NO
											</c:otherwise>
							</c:choose>
						</span>
					</div>
					<div>
						<span class="badge badge-pill badge-light gender"> <c:if
								test="${con.gender  eq 'm'}">남성만</c:if> <c:if
								test="${con.gender  eq 'f'}">여성만</c:if> <c:if
								test="${con.gender  eq 'a'}">혼성가능</c:if>
						</span>
					</div>
					<div class="age"></div>
				</td>
			</tr>
			<tr>
				<th scope="row">상호명</th>
				<td class="parent_name">${con.parent_name }</td>
				<th scope="row">위치</th>
				<td class="parent_address">${con.parent_address }</td>
			</tr>
			<tr>
				<th scope="row">모임날짜</th>
				<td class="meetdate">${con.sDate }</td>
				<td class="meetdate">${con.sTime }</td>
			</tr>
			<tr>
				<th scope="row">인원</th>
				<td class="count">${con.count }</td>
			</tr>
			<tr>
				<th scope="row" colspan="2">소개말</th>
			</tr>
			<tr>
				<td scope="row" class="content" colspan="2">${con.content }</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="modal-footer">
	<!-- 수정, 삭제 버튼 : 로그인 세션과 작성자 아이디 비교 필요 -->
	<c:if test="${con.writer eq sessionScope.loginInfo.id }">
		<button type="button" class="btn btn-primary" id="partyModify">수정</button>
		<button type="button" class="btn btn-primary" id="partyDelete">삭제</button>
	</c:if>
	<button type="button" class="btn btn-primary" id="joinParty">채팅방
		입장하기</button>
	<button type="button" class="btn btn-secondary" data-dismiss="modal">목록으로</button>
</div>
