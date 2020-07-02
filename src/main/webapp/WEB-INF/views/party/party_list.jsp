<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
<link rel="stylesheet" type="text/css" href="/resources/css/index-css.css">
<!-- header,footer용 css  -->
<!-- ******************* -->
<title>모임 리스트</title>
<style>
	.aa{
		width: 80%;
		margin: auto;
	}
</style>
<script>
	$(function(){
		var area0 = ["시/도 선택","서울특별시","인천광역시","대전광역시","광주광역시","대구광역시","울산광역시","부산광역시","경기도","강원도","충청북도","충청남도","전라북도","전라남도","경상북도","경상남도","제주도"];
		var area1 = ["강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구","중랑구"];
		var area2 = ["계양구","남구","남동구","동구","부평구","서구","연수구","중구","강화군","옹진군"];
		var area3 = ["대덕구","동구","서구","유성구","중구"];
		var area4 = ["광산구","남구","동구","북구","서구"];
		var area5 = ["남구","달서구","동구","북구","서구","수성구","중구","달성군"];
		var area6 = ["남구","동구","북구","중구","울주군"];
		var area7 = ["강서구","금정구","남구","동구","동래구","부산진구","북구","사상구","사하구","서구","수영구","연제구","영도구","중구","해운대구","기장군"];
		var area8 = ["고양시 덕양구","고양시 일산동구","고양시 일산서구","과천시","광명시","광주시","구리시","군포시","김포시","남양주시","동두천시","부천시 소사구","부천시 오정구","부천시 원미구","성남시 분당구","성남시 수정구","성남시 중원구","수원시 권선구","수원시 영통구","수원시 장안구","수원시 팔달구","시흥시","안산시 단원구","안산시 상록구","안성시","안양시 동안구","안양시 만안구","양주시","오산시","용인시 기흥구","용인시 수지구","용인시 처인구","의왕시","의정부시","이천시","파주시","평택시","포천시","하남시","화성시","가평군","양평군","여주군","연천군"];
		var area9 = ["강릉시","동해시","삼척시","속초시","원주시","춘천시","태백시","고성군","양구군","양양군","영월군","인제군","정선군","철원군","평창군","홍천군","화천군","횡성군"];
		var area10 = ["제천시","청주시","충주시","괴산군","단양군","보은군","영동군","옥천군","음성군","증평군","진천군","청원군"];
		var area11 = ["계룡시","공주시","논산시","보령시","서산시","아산시","천안시","금산군","당진군","부여군","서천군","연기군","예산군","청양군","태안군","홍성군"];
		var area12 = ["군산시","김제시","남원시","익산시","전주시","정읍시","고창군","무주군","부안군","순창군","완주군","임실군","장수군","진안군"];
		var area13 = ["광양시","나주시","목포시","순천시","여수시","강진군","고흥군","곡성군","구례군","담양군","무안군","보성군","신안군","영광군","영암군","완도군","장성군","장흥군","진도군","함평군","해남군","화순군"];
		var area14 = ["경산시","경주시","구미시","김천시","문경시","상주시","안동시","영주시","영천시","포항시","고령군","군위군","봉화군","성주군","영덕군","영양군","예천군","울릉군","울진군","의성군","청도군","청송군","칠곡군"];
		var area15 = ["거제시","김해시","마산시","밀양시","사천시","양산시","진주시","진해시","창원시","통영시","거창군","고성군","남해군","산청군","의령군","창녕군","하동군","함안군","함양군","합천군"];
		var area16 = ["서귀포시","제주시"];
		
		// 시/도 선택 박스 초기화
		$.each(area0,function(){
			$("#sido").append("<option value='"+this+"'>"+this+"</option>");
		});
		$("#gugun").append("<option value=''>구/군 선택</option>");
		
		// 시/도 선택시 구/군 설정
		$("#sido").change(function(){
			var areaindex = $('option:selected',$(this)).index();
			$("option",$("#gugun")).remove();
			if(idx==0){
				$("#gugun").append("<option value=''>구/군 선택</option>");
			}
			else{
				$.each(eval("area"+areaindex),function(){
					$("#gugun").append("<option value='"+this+"'>"+this+"</option>");
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
                    <div class="row">
                        <div class="col mb-2">
                            <div class="card">
                                <img src="/resources/img/itsearth.jpg" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h3 class="card-title">지구당</h3>
                                    <p class="card-text">가성비 좋은 텐동 맛집. 곱배기로 시키면 추가 금액 없이 밥이 곱배기.</p>
                                    <a href="#" class="btn btn-primary btn-sm">모집하러 가기</a>
                                </div>
                            </div>
                        </div>
                        <div class="col mb-2">
                            <div class="card">
                                <img src="/resources/img/sixme.jpg" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h3 class="card-title">육미 식당</h3>
                                    <p class="card-text">점심에는 매일 다른 고정 메뉴가 나오는 선술식당. 공기밥도 무료고 배고플때 가면 아주 굳</p>
                                    <a href="#" class="btn btn-primary btn-sm">모집하러 가기</a>
                                </div>
                            </div>
                        </div>
                        <div class="col mb-2">
                            <div class="card">
                                <img src="/resources/img/bab.jpg" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h5 class="card-title">바비공방</h5>
                                    <p class="card-text">양은 조금 적지만 맛은 보장되는 맛집.</p>
                                    <a href="#" class="btn btn-primary btn-sm">모집하러 가기</a>
                                </div>
                            </div>
                        </div>
                        <div class="col mb-2">
                            <div class="card">
                                <img src="/resources/img/janga.jpg" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h5 class="card-title">Card title</h5>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <a href="#" class="btn btn-primary btn-sm">모집하러 가기</a>
                                </div>
                            </div>
                        </div>
                        <div class="col mb-2">
                            <div class="card">
                                <img src="/resources/img/janga.jpg" class="card-img-top" alt="...">
                                <div class="card-body">
                                    <h5 class="card-title">Card title</h5>
                                    <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                                    <a href="#" class="btn btn-primary btn-sm">모집하러 가기</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row aa">
                <div class="col-12 jumbotron">
                    <span class="listtitle">상세 검색</span>
                    <div id="areacheck">
                        지역:  
                        <select name="sido" id="sido"></select>
                        <select name="gugun" id="gugun"></select>    
                    </div>
                    <div id="gendercheck">
                        성별 :   
                        <input type="radio" name="gender" value="man"/>남자만
                        <input type="radio" name="gender" value="woman"/>여자만
                        <input type="radio" name="gender" value="both" checked="checked"/>남녀무관
                    </div>
                    <div id="agecheck">
                        연령 : 
                        <input type="checkbox" name="age" value="10" checked/>10대
                        <input type="checkbox" naem="age" value="20"/>20대
                        <input type="checkbox" name="age" value="30"/>30대
                        <input type="checkbox" name="age" value="40"/>40대
                        <input type="checkbox" name="age" value="50"/>50대 이상
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Dropdown</button>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="#">Action</a>
                                <a class="dropdown-item" href="#">Another action</a>
                                <a class="dropdown-item" href="#">Something else here</a>
                                <div role="separator" class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#">Separated link</a>
                            </div>
                        </div>
                        <input type="text" class="form-control" aria-label="Text input with dropdown button">
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