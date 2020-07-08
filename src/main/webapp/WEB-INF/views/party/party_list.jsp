<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
<title>모임 리스트</title>
<style>
.aa {
	width: 80%;
	margin: auto;
}

.listtitle {
	font-size: 35px;
}

.partylist {
	margin-bottom: 15px;
}
.featImgWrap {
   height: 250px;
   position: relative;
   padding-top: 56.57%;
   /* 16:9 ratio */
   overflow: hidden;
}

.featImgWrap .cropping {
   position: absolute;
   top: 0;
   left: 0;
   right: 0;
   bottom: 0;
   -webkit-transform: translate(50%, 50%);
   -ms-transform: translate(50%, 50%);
   transform: translate(50%, 50%);
}

.featImgWrap .cropping img {
   position: absolute;
   top: 0;
   left: 0;
   max-width: 100%;
   height: auto;
   -webkit-transform: translate(-50%, -50%);
   -ms-transform: translate(-50%, -50%);
   transform: translate(-50%, -50%);
}

.featImgWrap .cropping img.landscape {
   max-height: 100%;
   height: 100%;
   max-width: none;
}

.featImgWrap .cropping img.portrait {
   max-width: 100%;
   width: 100%;
   max-height: none;
   border: 1px solid black;
}
</style>
<script>
	$(function() {
		var area0 = [ "시/도 선택", "서울", "인천", "대전", "광주", "대구", "울산", "부산", "경기",
				"강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주특별자치도" ];
		var area1 = [ "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구",
				"노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구",
				"성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구" ];
		var area2 = [ "계양구", "남구", "남동구", "동구", "부평구", "서구", "연수구", "중구",
				"강화군", "옹진군" ];
		var area3 = [ "대덕구", "동구", "서구", "유성구", "중구" ];
		var area4 = [ "광산구", "남구", "동구", "북구", "서구" ];
		var area5 = [ "남구", "달서구", "동구", "북구", "서구", "수성구", "중구", "달성군" ];
		var area6 = [ "남구", "동구", "북구", "중구", "울주군" ];
		var area7 = [ "강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구",
				"사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구", "기장군" ];
		var area8 = [ "고양시 덕양구", "고양시 일산동구", "고양시 일산서구", "과천시", "광명시", "광주시",
				"구리시", "군포시", "김포시", "남양주시", "동두천시", "부천시 소사구", "부천시 오정구",
				"부천시 원미구", "성남시 분당구", "성남시 수정구", "성남시 중원구", "수원시 권선구",
				"수원시 영통구", "수원시 장안구", "수원시 팔달구", "시흥시", "안산시 단원구", "안산시 상록구",
				"안성시", "안양시 동안구", "안양시 만안구", "양주시", "오산시", "용인시 기흥구",
				"용인시 수지구", "용인시 처인구", "의왕시", "의정부시", "이천시", "파주시", "평택시",
				"포천시", "하남시", "화성시", "가평군", "양평군", "여주군", "연천군" ];
		var area9 = [ "강릉시", "동해시", "삼척시", "속초시", "원주시", "춘천시", "태백시", "고성군",
				"양구군", "양양군", "영월군", "인제군", "정선군", "철원군", "평창군", "홍천군", "화천군",
				"횡성군" ];
		var area10 = [ "제천시", "청주시", "충주시", "괴산군", "단양군", "보은군", "영동군", "옥천군",
				"음성군", "증평군", "진천군", "청원군" ];
		var area11 = [ "계룡시", "공주시", "논산시", "보령시", "서산시", "아산시", "천안시", "금산군",
				"당진군", "부여군", "서천군", "연기군", "예산군", "청양군", "태안군", "홍성군" ];
		var area12 = [ "군산시", "김제시", "남원시", "익산시", "전주시", "정읍시", "고창군", "무주군",
				"부안군", "순창군", "완주군", "임실군", "장수군", "진안군" ];
		var area13 = [ "광양시", "나주시", "목포시", "순천시", "여수시", "강진군", "고흥군", "곡성군",
				"구례군", "담양군", "무안군", "보성군", "신안군", "영광군", "영암군", "완도군", "장성군",
				"장흥군", "진도군", "함평군", "해남군", "화순군" ];
		var area14 = [ "경산시", "경주시", "구미시", "김천시", "문경시", "상주시", "안동시", "영주시",
				"영천시", "포항시", "고령군", "군위군", "봉화군", "성주군", "영덕군", "영양군", "예천군",
				"울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군" ];
		var area15 = [ "거제시", "김해시", "마산시", "밀양시", "사천시", "양산시", "진주시", "진해시",
				"창원시", "통영시", "거창군", "고성군", "남해군", "산청군", "의령군", "창녕군", "하동군",
				"함안군", "함양군", "합천군" ];
		var area16 = [ "서귀포시", "제주시" ];

		// 시/도 선택 박스 초기화
		$.each(area0,
				function() {
					$("#sido").append(
							"<option value='"+this+"'>" + this + "</option>");
				});
		$("#gugun").append("<option value=''>구/군 선택</option>");

		// 시/도 선택시 구/군 설정
		$("#sido").change(
				function() {
					var areaindex = $('option:selected', $(this)).index();
					$("option", $("#gugun")).remove();
					if (areaindex == 0) {
						$("#gugun").append("<option value=''>구/군 선택</option>");
					} else {
						$.each(eval("area" + areaindex), function() {
							$("#gugun").append(
									"<option value='"+this+"'>" + this
											+ "</option>");
						});
					}
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

	<div class="container-fluid">
		<div class="row aa">
			<div class="col-12 jumbotron">
				<span class="listtitle">이번주 인기 맛집 Top 5!</span>
				<div class="row ingi">
					<!-- <div class="col-12 col-sm-4 col-md-2">
						<div class="card">
							<img src="/resources/img/itsearth.jpg" class="card-img-top"
								alt="...">
							<div class="card-body">
								<h3 class="card-title">지구당</h3>
								<p class="card-text">가성비 좋은 텐동 맛집. 곱배기로 시키면 추가 금액 없이 밥이 곱배기.</p>
								<a href="#" class="btn btn-primary btn-sm">모집하러 가기</a>
							</div>
						</div>
					</div> -->
					<c:forEach var="top" items="${top}" varStatus="status">
						<div class="col-sm-12 col-md-3">
							<div class="card partylist">
								<img src="${imglist2[status.index]}" class="card-img-top">
								<div class="card-body">
									<h5 class="card-title">${top.name }</h5>
									<p class="card-text">
										
									</p>
									<input type="hidden" class="store_seq" value="${top.seq}">
									<button type="button" class="btn btn-info btn-lg topBtn">모집하러 가기</button>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="row aa">
			<div class="col-12 jumbotron">
				<form action="/party/partysearch" method="post">
					<span class="listtitle">통합 검색</span>
					<div id="areacheck">
						지역: <select name="sido" id="sido"></select> <select name="gugun"
							id="gugun"></select>
					</div>
					<div id="gendercheck">
						성별 : <input type="radio" name="gender" value="m" />남자만 <input
							type="radio" name="gender" value="f" />여자만 <input type="radio"
							name="gender" value="a" />남녀무관
					</div>
					<div id="agecheck">
						연령 : <input type="checkbox" name="age" value="10" />10대 <input
							type="checkbox" name="age" value="20" />20대 <input
							type="checkbox" name="age" value="30" />30대 <input
							type="checkbox" name="age" value="40" />40대 <input
							type="checkbox" name="age" value="50" />50대 이상
					</div>

					<div id="drinkingcheck">
						음주 : <input type="radio" name="drinking" value="0" />불가능 <input
							type="radio" name="drinking" value="1" />가능 <input type="hidden"
							name="drinking" value="2" />
					</div>

					<div>
						<select name="text">
							<option value="title">제목</option>
							<option value="writer">작성자</option>
							<option value="content">내용</option>
							<option value="both">제목 + 내용</option>
						</select> <input type="text" name="search"> <input type="submit"
							value="검색">
					</div>

				</form>
			</div>
		</div>
		<div class="row aa">
			<div class="col-12 jumbotron">
				<div class="row">
					<c:choose>
						<c:when test="${empty list}">
							<div class="col-sm-12 col-md-3">
								<span>검색 된 내용이 없습니다.</span>
							</div>
						</c:when>
						<c:otherwise>
							<c:forEach var="partyList" items="${list}" varStatus="status">
								<div class="col-sm-12 col-md-3">
									<div class="card partylist">
										<img src="${imglist[status.index]}" class="card-img-top">
										<div class="card-body">
											<h5 class="card-title">${partyList.parent_name }</h5>
											<p class="card-text">
												날짜 : ${partyList.meetdate}<br>인원 : ${partyList.count }
											</p>
											<input type="hidden" class="party_seq"
												value="${partyList.seq}">
											<button type="button" class="btn btn-info btn-lg myBtn">상세
												보기</button>
										</div>
									</div>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
				<nav aria-label="Page navigation example">
				  <ul class="pagination justify-content-center">
				  	${navi}
				  </ul>
				</nav>
			</div>
		</div>
	</div>
	<!-- <div class="modal fade" id="partyModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-centered"
			role="document">
			<div class="modal-content" id="aaa">
			</div>
		</div>
	</div>
	 -->
	<div class="modal fade" id="mymodal" role="dialog">
		<div class="modal-dialog modal-xl">
			<!-- Modal content-->
			<div class="modal-content" id="aaa">
				
			</div>
		</div>
	</div>

	<!-- ******************* -->
	<!-- footer  -->
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />
	<!-- footer  -->
	<!-- ******************* -->
</body>
<script>
	$(".myBtn").on("click", function() {
		if ("${loginInfo.id}" == "") {
			alert("로그인 후 이용해주세요");
		} else {
			var select_seq = $(this).siblings(".party_seq").val();
			$("#aaa").empty();
			$.ajax({
				url:"/party/party_content_include",
				//url : "/party/party_content_include2",
				data : {
					seq : select_seq
				}
			}).done(function(con) {
				console.log(con);
				$("#aaa").append(con);

				//$("#partyModal").modal();
				$("#mymodal").modal();
			});
		}
	});
</script>
</html>