<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상호찾기</title>
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

<script>

function btnClick(clicked_id){
	
	console.log("와우!" + clicked_id);
		opener.document.getElementById("parent_name").value=document.getElementById("place_name"+clicked_id).innerHTML;
		opener.document.getElementById("parent_address").value =document.getElementById("road_address_name"+clicked_id).innerHTML;
		opener.document.getElementById("place_id").value = document.getElementById("api_id"+clicked_id).value;
		opener.document.getElementById("lng").value = document.getElementById("lng"+clicked_id).value;
		opener.document.getElementById("lat").value = document.getElementById("lat"+clicked_id).value;
		
		var parent_name = document.getElementById("place_name"+clicked_id).innerHTML;
		var ct = $('input:radio[name=category]:checked').val();
		
		if(ct=='c'){
		opener.document.getElementById("category").value="카페";
		}else{
			opener.document.getElementById("category").value="음식점";
		}
		opener.document.getElementById("phone").value = document.getElementById("phone"+clicked_id).innerHTML;
		opener.document.getElementById("place_url").value = document.getElementById("place_url"+clicked_id).value;
		opener.document.getElementById("address_name").value = document.getElementById("address_name"+clicked_id).value;
		var imgaddr="";
		$.ajax({
			url : "/party/clewimg?parent_name="+parent_name,
			type : "get"
		}).done(function(resp) {
			alert(resp);
			opener.document.getElementById("imgaddr").value = ""+resp;
			opener.document.getElementById("img-area").insertAdjacentHTML("");
			var src = '<img src ='+resp+'>';
			opener.document.getElementById("img-area").insertAdjacentHTML('afterend', src);

		});
		
		

		
		
	window.close();
};

$(function(){
	$("#back").on("click",function(){
		if(page>1){
			page=page-1;
			search(page);
		}else{
			alert("첫페이지입니다.");
		};
	});
	$("#next").on("click",function(){
		if(page==lastpage){
			alert("마지막페이지입니다.");
		}else{
		page=page+1;
		search(page);
		}
	});
});


$(document).ready(function(){
		var page = 1;
		var lastpage=1;
		
		
		
		
		function search(page){
			var keyword = $("#keyword").val();
			var category = $('input:radio[name=category]:checked').val();
			console.log(category);
			
			$.ajax({
				url : "/party/searchStoreProc?keyword=" + keyword +"&category=" + category+"&page="+page,
				dataType : "json"
			}).done(function(resp) {
				console.log(resp);
				$("#resultdiv").html("");
			
				
				/* var totalcount = resp.meta.total_count;
				lastpage=totalcount.toFixed(0);
				console.log("검색결과 = "+ totalcount);
		*/		
				$("#searchstore_totalcount").html(resp.documents.length + "건");
		 
				//for(var i=0;i+)
				var line = $("<div></div>");
				var test = $("<div></div>");
				for(var i=0; i< resp.documents.length;i++){
					/* line.append("<div class=row>")
					line.append(resp.documents[i].place_name);
					line.append("<div class=col-6>"+resp.documents[i].phone);
					line.append("<div class=row><div class=col-12>"+resp.documents[i].road_address_name+"</div>");
					line.append("<div class=row><div class=col-12><button class='btn btn-primary' id=button"+i+">선택</button></div>");
 */
 					test.append("<div>"+ "<input type='hidden' id=api_id"+i+" value='"+resp.documents[i].id +"'>"+
 					"<input type='hidden' id='lat"+i+"' name='lat' value='"+resp.documents[i].y+"'>"+
 					"<input type='hidden' name='lng' id='lng"+i+"' value='"+resp.documents[i].x+"'>"+
 					"<input type='hidden' name='place_url' id='place_url"+i+"' value='"+resp.documents[i].place_url+"'>"+
 					"<input type='hidden' name='address_name' id='address_name"+i+"' value='"+resp.documents[i].address_name+"'>"+
 					"<div id=place_name"+i+">"+resp.documents[i].place_name + 
 					"</div><div id=phone"+i+">" +resp.documents[i].phone + 
 					"</div><div id=road_address_name"+i+">" + resp.documents[i].road_address_name + 
 					"</div><button class='btn btn-primary' id="+i+" onClick='btnClick(this.id)')>선택</button></div>"); 
			
				}
				$("#resultdiv").html(test);
				$("#resultdiv").append("<br><br>");
				//$("#resultdiv").append("<button id=back>◀ </button>  <button id=next>▶</button>");
			});
		}
		
		
		
	
		
		$("#searchBtn").on("click", function() {
			if ($.trim($("#keyword").val()) == "") {
				alert("키워드를 입력해주세요");
				return false;
			}
			
			var isCategoryCk = $('input:radio[name=category]').is(':checked');
			
			if(!isCategoryCk){
				alert('검색 카테고리를 선택해주세요');
				return false;
			};

			search(page);
			
		});
		
		$("#keyword").keydown(function(e){
			var keyCode = e.which || e.keyCode;
			if(keyCode == 13){
				 $('#searchBtn').click();
		         return false;
				
			}  
				
		});
	});
</script>

</head>
<body>
	<div class="container">
		<form id="form" name="form" action="/party/searchStoreProc">
			<div class="row mt-3">
				<div class="col-12">
					<h2>상호명 찾기</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-12">
				<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" name="category"
							value="f" id="ct1"> <label class="form-check-label"
							for="ct1">음식점</label>
					</div>

					<div class="form-check form-check-inline">
						<input class="form-check-input" type="radio" name="category"
							value="c" id="ct2" > <label
							class="form-check-label" for="ct2">카페</label>
					</div>
				</div>
			</div>
			<div class="row mb-3">

				<div class="col-8">
					<input type="text" id="keyword" name="keyword" class="form-control"
						aria-describedby="countHelpInline"> <small id="HelpInline"
						class="text-muted"> 예시 : 지역명+상호명 / 을지로 스타벅스, 홍대 락희돈 </small>
				</div>
				<div class="col-3">
					<button type=button id=searchBtn class="btn btn-primary">검색</button>
				</div>
			</div>
		</form>
		
		
		<!--  출력 라인  -->
		
		
		<div class="row mb-2">
			<div class="col-12">
				<h5>검색결과 <span id="searchstore_totalcount"></span></h5>
			</div>
		</div>
		
	<%-- 	<c:forEach var="i" items="${obj.document}" varStatus="status"> --%>
	<div class="row">
	<div class="col-12" id="resultdiv">
	
	</div>
	</div>
		<%-- <div class="row" id="resultdiv">
			<div class="col-12">
				<div class="row">
					<div class="col-7">${i.place_name}</div>
					<div class="col-5">${i.phone}</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-12">
				<div class="row">
					<div class="col-12">${i.road_address_name}</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-12">
				<button id="chooseBtn" class="btn btn-light">선택</button>
				<hr />
			</div>
		</div> --%>
		<%-- </c:forEach> --%>
		<!--  출력 라인  끝-->

	</div>



</body>
</html>