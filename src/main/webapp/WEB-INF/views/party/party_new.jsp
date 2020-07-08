<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>모임 모집하기</title>
<!-- BootStrap4 -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src="http://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- BootStrap4 End-->

<!-- google font -->
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"
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
	href="/resources/css/party-css.css">
<link rel="stylesheet" type="text/css"
	href="http://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script>
	//유효성 - 미성년자 음주불가
	$("#drinking1").attr('disabled', true);
	$(function() {
		$("#age1").on("click", function() {
			if ($('input:checkbox[id=age1]').is(":checked") == true) {
				$("#drinking1").attr('disabled', true);
				$("#drinking1").prop("checked", false);
				$("#drinking2").prop("checked", true);
			} else {
				$("#drinking1").attr('disabled', false);
			}
		});
	});
	//유효성 - 미성년자 음주불가 끝

	$(function() {
		$("#party_date").datepicker({
			dateFormat : 'yy-mm-dd',
			minDate : 0,
			dayNamesShort : [ "일", "월", "화", "수", "목", "금", "토" ],

		});

		var birthday = "${age}";
		var yyyy = birthday.substr(0, 4);
		var mm = birthday.substr(5, 2);
		var dd = birthday.substr(8, 2);

		var ymd = new Date(yyyy, mm - 1, dd);
		var today = new Date();
		var years = today.getFullYear() - ymd.getFullYear();
		console.log('계산 된 나이 : ' + years);
		var agech = "";
		if (years >= 50) {
			$("input:checkbox[id='age5']").prop("checked", true);
			$("input:checkbox[id='age5']").attr("disabled", true);
			agech = "age5";
		} else if (years >= 40) {
			$("input:checkbox[id='age4']").prop("checked", true);
			$("input:checkbox[id='age4']").attr("disabled", true);
			agech = "age4";
		} else if (years >= 30) {
			$("input:checkbox[id='age3']").prop("checked", true);
			$("input:checkbox[id='age3']").attr("disabled", true);
			agech = "age3";
		} else if (years >= 20) {
			$("input:checkbox[id='age2']").prop("checked", true);
			$("input:checkbox[id='age2']").attr("disabled", true);
			agech = "age2";
		} else if (years >= 14) {
			$("input:checkbox[id='age1']").prop("checked", true);
			$("input:checkbox[id='age1']").attr("disabled", true);
			agech = "age1";
		}
		;

		$("#search_parent_name")
				.on(
						"click",
						function() {
							window.name = "parentForm";
							openWin = window
									.open("/party/toSearchStore", "childForm",
											"width=650, height=600, resizable = no, scrollbars = no");
						});

		/* $("#toCheckAddr").on("click",function(){
				var lng = $("#lng").val();
				var lat = $("#lat").val();
				if($("#category").val()=='c'){
					var category = 'cafe';
				}else{
					var category='food';
				}
				console.log(category);
				console.log(lat);
				console.log(lng);
				
				$.ajax({
					url : "/map/" + category+"?lat="+lat+"&lng="+lng,
					dataType : "json"
				}).done(function(resp) {
					console.log(resp);
					var pid = $("#place_id").val();
					for(var i=0; i< resp.documents.length;i++){
					if(resp.documents[i].place_id==pid){
						console.log(resp.documents[i].place_name);
					}
					}
					
				});
			
			
		}); */

		$("#submitBtn").on("click", function() {
			var parent_name = $("#parent_name").val();
			var parent_address = $("#parent_address").val();
			var title = $("#party_title").val();
			var date = $("#party_date").val();
			var time = $("#party_time").val();
			var count = $("#party_count").val();
			var content = $("#content").val();
			var isCheckGender = $('input:radio[name=gender]').is(':checked');
			var isCheckAge = $('input:checkbox[name=age]').is(':checked');
			if (agech == "age5") {
				$("input:checkbox[id=age5]").attr("disabled", false);
			} else if (agech == "age4") {
				$("input:checkbox[id=age4]").attr("disabled", false);
			} else if (agech == "age3") {
				$("input:checkbox[id=age3]").attr("disabled", false);
			} else if (agech == "age2") {
				$("input:checkbox[id=age2]").attr("disabled", false);
			} else if (agech == "age1") {
				$("input:checkbox[id=age1]").attr("disabled", false);
			}

			console.log(title);
			console.log(date);
			console.log(time);
			console.log(count);
			console.log(content);
			console.log(isCheckGender);
			console.log(isCheckAge);

			if ($.trim(parent_name) == '') {
				alert('상호명 찾기버튼으로 모임장소를 등록해주세요');
				return false;
			}
			;

			if ($.trim(title) == '') {
				alert('모임 제목을 입력해주세요');
				return false;
			}
			;

			if (!date) {
				alert('모임날짜를 선택해주세요');
				return false;
			}
			;

			if (!time) {
				alert("모임시간을 선택해주세요");
				return false;
			}
			;

			if ($.trim(count) == '') {
				alert("모임인원을 선택해주세요");
				return false;
			}
			;

			if (!isCheckGender) {
				alert('멤버구성을 선택해주세요');
				return false;
			}
			;

			if (!isCheckAge) {
				alert('모집하고자하는 연령대를 선택해주세요');
				return false;
			}
			;

			if ($.trim(content) == '') {
				alert('모임 소개를 작성해주세요');
				return false;
			}
			;

			let today = new Date();
			console.log(today);
			console.log(date);

			let tyear = today.getFullYear(); // 년도
			let tmonth = today.getMonth() + 1; // 월
			let tdate = today.getDate(); // 날짜
			let tday = today.getDay(); // 요일

			if (today > date) {
				alert('선택된 날짜가 과거입니다.');
				return false;
			}

			document.form.submit();

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



	<c:if test="${empty sessionScope.loginInfo }">
		<h3 class="text-center my-5">로그인 후 이용해주세요.</h3>
	</c:if>
	<c:if test="${!empty sessionScope.loginInfo }">
		<form id="form" name="form" method="post"
			action="/party/party_New_Proc">
			<div class="container">
				<div class="row">
					<div class="col-12 col-sm-8 formdiv">
						<div class="row mb-3">
							<div class="col-sm-12">
								<h2 class="party_headline">모임 모집하기</h2>
							</div>
							<input type="hidden" name="writer">
						</div>
						<div class="row mb-1">
							<div class="col-sm-2">상호명</div>
							<div class="col-sm-3">
								<input type="text" class="form-control" name="parent_name"
									id="parent_name" readonly>
							</div>
							<div class="col-sm-3">
								<button id="search_parent_name" class="btn btn-primary"
									type=button>상호 찾기</button>
							</div>
						</div>
						<div class="row mb-1">
							<div class="col-sm-2">위치</div>
							<div class="col-sm-6">
								<input type="text" class="form-control" name="parent_address"
									id="parent_address" readonly> <input type="hidden"
									name="place_id" id="place_id"> <input type="hidden"
									name="lng" id="lng"> <input type="hidden" name="lat"
									id="lat"> <input type="hidden" name="category"
									id="category"> <input type="hidden" name="phone"
									id="phone"> <input type="hidden" name="address_name"
									id="address_name"> <input type="hidden"
									name="place_url" id="place_url">
									<input type="hidden" name="imgaddr" id="imgaddr">
							</div>
						</div>

						<div class="row mb-1">
							<div class="col-sm-2">제목</div>
							<div class="col-sm-6">
								<input class="form-control" type="text" name="title"
									id="party_title">
							</div>
						</div>
						<div class="row mb-1">
							<div class="col-sm-2">모임날짜</div>
							<div class="col-lg-2">
								<input class="form-control" type="text" name="date"
									id="party_date">
							</div>
						</div>
						<div class="row mb-1">
							<div class="col-sm-2">시간</div>
							<div class="col-lg-2">
								<input class="form-control" type="time" step="300" name="time"
									id="party_time">
							</div>
						</div>
						<div class="row mb-1">
							<div class="col-sm-2">인원</div>
							<div class="col-sm-2">
								<input class="form-control" type="number" name="count" min=2
									max=4 id="party_count" aria-describedby="countHelpInline">
							</div>
							<div class="col-sm-5">
								<small id="countHelpInline" class="text-muted"> 인원수는
									2~4명 사이로 설정가능합니다. </small>
							</div>

						</div>
						<div class="row mb-1">
							<div class="col-sm-2">멤버구성</div>
							<div class="col-sm-10">
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" name="gender"
										value="m" id="gender1"> <label
										class="form-check-label" for="gender1">남자만</label>
								</div>

								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" name="gender"
										value="f" id="gender2"> <label
										class="form-check-label" for="gender2">여자만</label>
								</div>

								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" name="gender"
										value="a" id="gender3"> <label
										class="form-check-label" for="gender3">남녀무관</label>
								</div>
							</div>
						</div>

						<div class="row mb-1">
							<div class="col-sm-2">연령대</div>
							<div class="col-sm-10">
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="checkbox" name="age"
										value="10" id="age1"> <label class="form-check-label"
										for="age1">10대</label>
								</div>
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="checkbox" name="age"
										value="20" id="age2"> <label class="form-check-label"
										for="age2">20대</label>
								</div>
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="checkbox" name="age"
										value="30" id="age3"> <label class="form-check-label"
										for="age3">30대</label>
								</div>
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="checkbox" name="age"
										value="40" id="age4"> <label class="form-check-label"
										for="age4">40대</label>
								</div>
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="checkbox" name="age"
										value="50" id="age5"> <label class="form-check-label"
										for="age5">50대 이상</label>
								</div>
							</div>
						</div>

						<div class="row mb-1" id="adult_q">
							<div class="col-sm-2">음주</div>
							<div class="col-sm-10">
								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" name="drinking"
										value="1" id="drinking1"> <label
										class="form-check-label" for="drinking1">OK</label>
								</div>

								<div class="form-check form-check-inline">
									<input class="form-check-input" type="radio" name="drinking"
										value="0" id="drinking2" checked="checked"> <label
										class="form-check-label" for="drinking2">NO</label>
								</div>
							</div>
						</div>
					</div>
					<div class="col-12 col-sm-3" id="img-area"></div>
				</div>
			</div>
			<div class="container formdiv">
				<div class="row mb-1">
					<div class="col-2">소개</div>
					<div class="col-10">
						<textarea class="form-control " id="content" name="content"
							placeholder="소개를 입력해주세요" rows="10"></textarea>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-12">
						<button type=button id="submitBtn"
							class="btn btn-primary btn-lg btn-block">모임 등록하기</button>
					</div>

				</div>

			</div>
		</form>
	</c:if>


	<!-- ******************* -->
	<!-- footer  -->
	<jsp:include page="/WEB-INF/views/include/footer.jsp" />

	<!-- footer  -->
	<!-- ******************* -->
</body>
</html>