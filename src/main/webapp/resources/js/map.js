$(function(){
		/****************** 스타일 관련 영역 ******************/
		$('#Progress_Loading').hide(); //첫 시작시 로딩바를 숨겨준다.
	    $('.cropping img').each(function (index, item) {
	        if ($(this).height() / $(this).width() < 0.567) {
	            $(this).addClass('landscape').removeClass('portrait');
	        } else {
	            $(this).addClass('portrait').removeClass('landscape');
	        }
	    });
		/****************** 카카오맵 영역 ******************/
		// MakrerImage 객체를 생성하여 반환하는 함수입니다
		function createMarkerImage(markerSize, offset, markerImageSrc) {
		    var markerImage = new kakao.maps.MarkerImage(
		    	markerImageSrc, // 마커 이미지 URL
		        markerSize, // 마커의 크기
		        {
		            offset: offset, // 마커 이미지에서의 기준 좌표
		        }
		    );
		    return markerImage;
		}
	    // 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
	    function makeOverListener(map, marker, infowindow) {
	        return function() {
	            infowindow.open(map, marker);
	        };
	    }

	    // 인포윈도우를 닫는 클로저를 만드는 함수입니다 
	    function makeOutListener(infowindow) {
	        return function() {
	            infowindow.close();
	        };
	    }
	    function addMarker(po) {
			var markerSize = new kakao.maps.Size(10, 10),
				markerOffset = new kakao.maps.Point(0, 0);
			var normalImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/img.png';
			var hoverImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/img-hover.png';
			
	        var normalImage = createMarkerImage(markerSize, markerOffset, normalImageSrc),
	        	hoverImage = createMarkerImage(markerSize, markerOffset, hoverImageSrc);
	        var marker = new kakao.maps.Marker({
	            map: map,
	            position: po.latlng,
	            image: normalImage
	        });

	        var customOverlay = new kakao.maps.CustomOverlay({
	            position: po.latlng,
	            content: po.content
	        });
	        // 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
			function infoOverSet(map) {
			    customOverlay.setMap(map);
			}
			// 인포윈도우를 닫는 클로저를 만드는 함수입니다 
			function infoOverClose() {
			    customOverlay.setMap(null);
			}
	        marker.normalImage = normalImage;
	        kakao.maps.event.addListener(marker, 'mouseover', function() {
	                marker.setImage(hoverImage);
	                marker.setZIndex(1);
	                infoOverSet(marker.getMap());
	        });
	        kakao.maps.event.addListener(marker, 'mouseout', function() {
	                marker.setImage(normalImage);
	                infoOverClose();
	                marker.setZIndex(0);
	        });
	        return marker;
	    }

	    var kakaoCafeMarkers = [];
	    var kakaoFoodMarkers = [];
		/*$.get("/resources/json/cafe.json",function(data){
		
			console.log(data);
			var positions = [];
			
			$.each(data, function(i, item) {
				for(var a = 0;a < data.cafe_list.length; a++){
					positions.push({
		    	        content: '<div>'+ data.cafe_list[a].cafe.place_name +'</div>', 
		    	        latlng: new kakao.maps.LatLng(data.cafe_list[a].cafe.y, data.cafe_list[a].cafe.x)
					});
				}
			});
		    for (var i = 0; i < positions.length; i ++) {
			    var marker = addMarker(positions[i]);
			    kakaoCafeMarkers.push({marker:marker});
			}
		});
		

  		$.get("/resources/json/food.json",function(data){
  			console.log(data);
			var positions = [];

			$.each(data, function(i, item) {
				for(var a = 0;a < data.food_list.length; a++){
					positions.push({
		    	        content: '<div>'+ data.food_list[a].food.place_name +'</div>', 
		    	        latlng: new kakao.maps.LatLng(data.food_list[a].food.y, data.food_list[a].food.x)
					});
				}
			});
		    for (var i = 0; i < positions.length; i ++) {
		    	var marker = addMarker(positions[i]);
			    kakaoFoodMarkers.push({marker:marker});
			}
		});*/
		

		
		function createMapTableMarker(positions, image){
			var markers = [];
			positions.forEach(function(pos){
				var marker = new kakao.maps.Marker({
			        map: map, 
			        position: pos.latlng,
			        image: image
			    });
				var customOverlay = new kakao.maps.CustomOverlay({
		            position: pos.latlng,
		            content: pos.content
		        });
				function infoOverSet(map) {
					customOverlay.setMap(map);
				}
				function infoOverClose() {
					customOverlay.setMap(null);
				}
			    kakao.maps.event.addListener(marker, 'click', function(mouseEvent) {        
			        location.href = "/map/selectMarkerInfo?place_id="+pos.place_id;
			    });
				kakao.maps.event.addListener(marker, 'mouseover', function() {
	                infoOverSet(marker.getMap());
	                marker.setZIndex(1);
		        });
		        kakao.maps.event.addListener(marker, 'mouseout', function() {
		            infoOverClose();
	                marker.setZIndex(0);
		        });
                markers.push({
	    	        marker: marker
	    		})
			});
			return markers;
		}
		
		var cafeMarkers = null;
		var foodMarkers = null;
		var cafePartyMarkers = null;
		var foodPartyMarkers = null;
		var cafeTopMarkers = null;
		var foodTopMarkers = null;
		
		$.get("/resources/json/mapData.json",function(data){
			var cafePositions = [];
			var foodPositions = [];
			var cafePartyPositions = [];
			var foodPartyPositions = [];
			var cafeTopPositions = [];
			var foodTopPositions = [];
			
			var normalCafeImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/Asset6.png', 
			normalFoodImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/Asset5.png'
			partyCafeImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/Asset3.png',
			partyFoodImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/Asset4.png', 
			topCafeImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/Asset2.png',
			topFoodImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/Asset1.png',  
		    baseImageSize = new kakao.maps.Size(40, 60), // 마커이미지의 크기입니다
		    baseImageOption = {offset: new kakao.maps.Point(20, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
		    var normalCafeImage = new kakao.maps.MarkerImage(normalCafeImageSrc, baseImageSize, baseImageOption);
		    var normalFoodImage = new kakao.maps.MarkerImage(normalFoodImageSrc, baseImageSize, baseImageOption);
		    var partyCafeImage = new kakao.maps.MarkerImage(partyCafeImageSrc, baseImageSize, baseImageOption);
		    var partyFoodImage = new kakao.maps.MarkerImage(partyFoodImageSrc, baseImageSize, baseImageOption);
		    var topCafeImage = new kakao.maps.MarkerImage(topCafeImageSrc, baseImageSize, baseImageOption);
		    var topFoodImage = new kakao.maps.MarkerImage(topFoodImageSrc, baseImageSize, baseImageOption);
			
			$.each(data, function(i, item) { 
				if(item.top!=undefined){
					if(item.category == '카페'){
						cafeTopPositions.push({
							content: '<div class="mapcustomoverlay">' +
			    	        '  <a href="'+item.place_url+'" target="_blank">' +
			    	        '    <span class="category">'+ item.category +'</span>' +
			    	        '    <span class="title">'+ item.name +'</span>' +
			    	        '    <span class="address">'+ item.address +'</span>' +
			    	        '  </a>' +
			    	        '</div>', 
							latlng: new kakao.maps.LatLng(item.lat, item.lng),
							place_id: item.place_id
						})
					}else{
						foodTopPositions.push({
							content: '<div class="mapcustomoverlay">' +
			    	        '  <a href="'+item.place_url+'" target="_blank">' +
			    	        '    <span class="category">'+ item.category +'</span>' +
			    	        '    <span class="title">'+ item.name +'</span>' +
			    	        '    <span class="address">'+ item.address +'</span>' +
			    	        '  </a>' +
			    	        '</div>', 
							latlng: new kakao.maps.LatLng(item.lat, item.lng),
							place_id: item.place_id
						})
					}
				}else{
					if(item.category == '카페'){
						if(item.partyOn > 0){
							cafePartyPositions.push({
								content: '<div class="mapcustomoverlay">' +
				    	        '  <a href="'+item.place_url+'" target="_blank">' +
				    	        '    <span class="category">'+ item.category +'</span>' +
				    	        '    <span class="title">'+ item.name +'</span>' +
				    	        '    <span class="address">'+ item.address +'</span>' +
				    	        '  </a>' +
				    	        '</div>', 
								latlng: new kakao.maps.LatLng(item.lat, item.lng),
								place_id: item.place_id
							});				
						}else if(item.partyOn == 0){
							cafePositions.push({
								content: '<div class="mapcustomoverlay">' +
				    	        '  <a href="'+item.place_url+'" target="_blank">' +
				    	        '    <span class="category">'+item.category +'</span>' +
				    	        '    <span class="title">'+ item.name +'</span>' +
				    	        '    <span class="address">'+ item.address +'</span>' +
				    	        '  </a>' +
				    	        '</div>', 
								latlng: new kakao.maps.LatLng(item.lat, item.lng),
								place_id: item.place_id
							});		
						}
					}else if(item.category == '음식점'){
						if(item.partyOn > 0){
							foodPartyPositions.push({
								content: '<div class="mapcustomoverlay">' +
				    	        '  <a href="'+item.place_url+'" target="_blank">' +
				    	        '    <span class="category">'+ item.category +'</span>' +
				    	        '    <span class="title">'+ item.name +'</span>' +
				    	        '    <span class="address">'+ item.address +'</span>' +
				    	        '  </a>' +
				    	        '</div>', 
								latlng: new kakao.maps.LatLng(item.lat, item.lng),
								place_id: item.place_id
							});
						}else if(item.partyOn == 0){
							foodPositions.push({
								content: '<div class="mapcustomoverlay">' +
				    	        '  <a href="'+item.place_url+'" target="_blank">' +
				    	        '    <span class="category">'+ item.category +'</span>' +
				    	        '    <span class="title">'+ item.name +'</span>' +
				    	        '    <span class="address">'+ item.address +'</span>' +
				    	        '  </a>' +
				    	        '</div>', 
								latlng: new kakao.maps.LatLng(item.lat, item.lng),
								place_id: item.place_id
							});
						}
					}					
				}
			});
			cafeMarkers = createMapTableMarker(cafePositions, normalCafeImage); // 일반 카페
			foodMarkers = createMapTableMarker(foodPositions, normalFoodImage); // 일반 음식점
			cafePartyMarkers = createMapTableMarker(cafePartyPositions, partyCafeImage); // 모임 모집중인 카페
			foodPartyMarkers = createMapTableMarker(foodPartyPositions, partyFoodImage); // 모임 모집중인 음식점
			cafeTopMarkers = createMapTableMarker(cafeTopPositions, topCafeImage); // 리뷰 평균 Top 카페
			foodTopMarkers = createMapTableMarker(foodTopPositions, topFoodImage); // 리뷰 평균 Top 음식점
		});
		
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	    mapOption = { 
	        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
	        level: 3 // 지도의 확대 레벨
	    };
		// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
		var map = new kakao.maps.Map(mapContainer, mapOption); 
		
		$("#centerLat").text(map.getCenter().getLat());
	    $("#centerLng").text(map.getCenter().getLng());
		
	    if($("#markerLat").text()!=""){
			map.setCenter(new kakao.maps.LatLng($("#markerLat").text(), $("#markerLng").text()));
	    }
	    
		// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
		var zoomControl = new kakao.maps.ZoomControl();
		map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

		// 지도가 확대 또는 축소되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
		/* kakao.maps.event.addListener(map, 'zoom_changed', function() {        
		    
		    // 지도의 현재 레벨을 얻어옵니다
		    var level = map.getLevel();
		    
		    var message = '지도를 확대해주세요.';
		    var resultDiv = document.getElementById('result');  
		    resultDiv.innerHTML = message;
		    
		}); */
		
		// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new kakao.maps.services.Geocoder();

		var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
		infowindow = new kakao.maps.InfoWindow({zindex:30}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

		// 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
		searchAddrFromCoords(map.getCenter(), displayCenterInfo);

		// 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
		kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
		    searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
		        if (status === kakao.maps.services.Status.OK) {
		            var detailAddr = !!result[0].road_address ? '<div class="selectedRoad">도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
		            detailAddr += '<div class="selectedAddress">지번 주소 : ' + result[0].address.address_name + '</div>';
		            
		            var content = '<div class="bAddr">' +
		                            '<span class="title">주소정보</span>' + 
		                            detailAddr + 
		                        '</div>';

		            // 마커를 클릭한 위치에 표시합니다 
		            marker.setPosition(mouseEvent.latLng);
		            marker.setMap(map);

		            // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
		            infowindow.setContent(content);
		            infowindow.open(map, marker);
		            $(".lat").html(mouseEvent.latLng.getLat());
		            $(".lng").html(mouseEvent.latLng.getLng());
		        }   
		    });
		});
		// 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
		kakao.maps.event.addListener(map, 'idle', function() {
		    searchAddrFromCoords(map.getCenter(), displayCenterInfo);
		    $("#centerLat").text(map.getCenter().getLat());
		    $("#centerLng").text(map.getCenter().getLng());
		});

		function searchAddrFromCoords(coords, callback) {
		    // 좌표로 행정동 주소 정보를 요청합니다
		    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
		}

		function searchDetailAddrFromCoords(coords, callback) {
		    // 좌표로 법정동 상세 주소 정보를 요청합니다
		    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
		}

		// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
		function displayCenterInfo(result, status) {
		    if (status === kakao.maps.services.Status.OK) {
		        var infoDiv = document.getElementById('centerAddr');

		        for(var i = 0; i < result.length; i++) {
		            // 행정동의 region_type 값은 'H' 이므로
		            if (result[i].region_type === 'H') {
		                // infoDiv.innerHTML = result[i].address_name;
		                break;
		            }
		        }
		    }    
		}
		$(".map_add").on("click",function(){
			var selectedRoad = $(".selectedRoad").text().split(" : ")[1];
			var selectedAddress = $(".selectedAddress").text().split(" : ")[1];
			$(".modal-body .road_address").html(selectedRoad);
			$(".modal-body .address").html(selectedAddress);
		})
		
        var positions = [];
		$(".food").on("click",function(){
        	$.ajax({
        		url:"/map/food",
        		type:"get",
        		data:{
        			lat:$("#centerLat").text(),
        			lng:$("#centerLng").text()},
        		dataType:"JSON"
        	}).done(function(resp){
        		console.log(resp.documents.length);
        		for(var i = 0;i < resp.documents.length;i++){
            		//console.log(resp.documents[i].place_name);
            		//console.log(resp.documents[i].x + " : " + resp.documents[i].y);
            		//console.log(resp.documents[i].place_url);
            		// 마커를 표시할 위치와 내용을 가지고 있는 객체 배열입니다 
            		positions.push({
            		        content: '<form action="/map/insert" method="get" id="inputForm"><div style="padding:5px;">'
            		        			+'<div><input type=text readonly name="name" value="'+resp.documents[i].place_name+'"></div>'
            		        			+'<div><input type=text readonly name="address" value="'+resp.documents[i].address_name+'"></div>'
            		        			+'<div><input type=text readonly name="road_address" value="'+resp.documents[i].road_address_name+'"></div>'
            		        			+'<div><input type=text readonly name="category" value="'+resp.documents[i].category_group_name+'"></div>'
            		        			+'<div><input type=text readonly name="lat" value="'+resp.documents[i].y+'"></div>'
            		        			+'<div><input type=text readonly name="lng" value="'+resp.documents[i].x+'"></div>'
            		        			+'<div><input type=text readonly name="phone" value="'+resp.documents[i].phone+'"></div>'
            		        			+'<div><input type=text readonly name="place_url" value="'+resp.documents[i].place_url+'"></div>'
            		        			+'<div><input type=text readonly name="detail_category" value="'+resp.documents[i].category_name+'"></div>'
            		        			+'<div><input type=text readonly name="place_id" value="'+resp.documents[i].id+'"></div>'
            		        			+'<button type="submit" id="detail">맛집 등록</button></div></form>', 
            		        latlng: new kakao.maps.LatLng(resp.documents[i].y, resp.documents[i].x)
            		});
        		}
        		
        	    var imageSrc = 'https://i.imgur.com/rzDIRIP.png', // 마커이미지의 주소입니다    
        	    imageSize = new kakao.maps.Size(40, 60), // 마커이미지의 크기입니다
        	    imageOption = {offset: new kakao.maps.Point(20, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
        	    
        		// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
        		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
        		
        		for (var i = 0; i < positions.length; i ++) {
        		    // 마커를 생성합니다
        		    var marker = new kakao.maps.Marker({
        		        map: map, // 마커를 표시할 지도
        		        position: positions[i].latlng, // 마커의 위치
        		        image: markerImage // 마커이미지 설정 
        		    });
        		    
        		    var iwRemoveable = true;
        		    // 마커에 표시할 인포윈도우를 생성합니다 
        		    var infowindow = new kakao.maps.InfoWindow({
        		        content: positions[i].content, // 인포윈도우에 표시할 내용
        		        removable : iwRemoveable
        		    });

        		    // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
        		    // 이벤트 리스너로는 클로저를 만들어 등록합니다
        		 	// 마커에 클릭이벤트를 등록합니다
        		 	
        		    kakao.maps.event.addListener(marker, 'click', makeOverListener(map, marker, infowindow));
        		}
        	}).fail(function(error1,error2){
        		console.log(error1);
        		console.log(error2);
        	})
		})
		$(".cafe").on("click",function(){ // 카페
        	$.ajax({
        		url:"/map/cafe",
        		type:"get",
        		data:{
        			lat:$("#centerLat").text(),
        			lng:$("#centerLng").text()},
        		dataType:"JSON"
        	}).done(function(resp){
        		console.log(resp.documents.length);
        		var positions = [];
        		for(var i = 0;i < resp.documents.length;i++){
            		console.log(resp.documents[i].id);
            		//console.log(resp.documents[i].place_name);
            		//console.log(resp.documents[i].x + " : " + resp.documents[i].y);
            		//console.log(resp.documents[i].place_url);
            		positions.push({
        		        content: '<form action="/map/insert" method="get" id="inputForm"><div style="padding:5px;">'
        		        			+'<div><input type=text readonly name="name" value="'+resp.documents[i].place_name+'"></div>'
        		        			+'<div><input type=text readonly name="address" value="'+resp.documents[i].address_name+'"></div>'
        		        			+'<div><input type=text readonly name="road_address" value="'+resp.documents[i].road_address_name+'"></div>'
        		        			+'<div><input type=text readonly name="category" value="'+resp.documents[i].category_group_name+'"></div>'
        		        			+'<div><input type=text readonly name="lat" value="'+resp.documents[i].y+'"></div>'
        		        			+'<div><input type=text readonly name="lng" value="'+resp.documents[i].x+'"></div>'
        		        			+'<div><input type=text readonly name="phone" value="'+resp.documents[i].phone+'"></div>'
        		        			+'<div><input type=text readonly name="place_url" value="'+resp.documents[i].place_url+'"></div>'
        		        			+'<div><input type=text readonly name="detail_category" value="'+resp.documents[i].category_name+'"></div>'
        		        			+'<div><input type=text readonly name="place_id" value="'+resp.documents[i].id+'"></div>'
        		        			+'<button type="submit" id="detail">맛집 등록</button></div></form>', 
        		        latlng: new kakao.maps.LatLng(resp.documents[i].y, resp.documents[i].x)
        			});
        		}
        		
        	    var imageSrc = 'https://i.imgur.com/kKE28hx.png', // 마커이미지의 주소입니다    
        	    imageSize = new kakao.maps.Size(40, 60), // 마커이미지의 크기입니다
        	    imageOption = {offset: new kakao.maps.Point(20, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
        	    
        		// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
        		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
        		for (var i = 0; i < positions.length; i ++) {
        		    var marker = new kakao.maps.Marker({
        		        map: map, 
        		        position: positions[i].latlng,
        		        image: markerImage
        		    });
        		    
        		    var iwRemoveable = true;
        		    var infowindow = new kakao.maps.InfoWindow({
        		        content: positions[i].content, 
        		        removable : iwRemoveable
        		    });
        		    kakao.maps.event.addListener(marker, 'click', makeOverListener(map, marker, infowindow));
        		}
        	}).fail(function(error1,error2){
        		console.log(error1);
        		console.log(error2);
        	})
		})
		$(".foodInsert").on("click",function(){ // 음식점 입력
        	$.ajax({
        		url:"/map/foodInsert",
        		type:"get",
        		data:{
        			lat:$("#centerLat").text(),
        			lng:$("#centerLng").text()},
        		dataType:"JSON"
        	}).done(function(resp){
        		console.log(resp);
        		alert("입력 완료");
        	}).fail(function(error1,error2){
        		var iCHK = 1;
        		for(var i = 0;i<iCHK;i++)
        		{
        		document.location.reload();
        		}
        		console.log(error1);
        		console.log(error2);
        	})
		})
		$(".cafeInsert").on("click",function(){ // 카페 입력
        	$.ajax({
        		url:"/map/cafeInsert",
        		type:"get",
        		data:{
        			lat:$("#centerLat").text(),
        			lng:$("#centerLng").text()},
        		dataType:"JSON"
        	}).done(function(resp){
        		var iCHK = 1;
        		for(var i = 0;i<iCHK;i++)
        		{
        		document.location.reload();
        		}
        		console.log(resp);
        		alert("입력 완료");
        	}).fail(function(error1,error2){
        		console.log(error1);
        		console.log(error2);
        	})
		})
		$(".join").on("click",function(){ // 참가
			$.ajax({
				url:"/map/getPartyInfo",
				data:{seq:$(this).parent().find(".seq").text()}
			}).done(function(resp){
				$("#partyModal #exampleModalLabel b").text(resp.title);
				$("#partyModal .parent_name").text(resp.parent_name);
				$("#partyModal .parent_address").text(resp.parent_address);
				$("#partyModal .meetdate").text(resp.sDate);
				$("#partyModal .count").text(resp.count);
				if(resp.gender == 'a'){
					$("#partyModal .gender").text("혼성모임");
				}else if(resp.gender == 'f'){
					$("#partyModal .gender").text("여성만");
				}else if(resp.gender == 'm'){
					$("#partyModal .gender").text("남성만");
				}
				$("#partyModal .age").html("");
				if(resp.age.includes(",")){
					var ages = resp.age.split(",");
					for(var i = 0;i < ages.length;i++){
						$("#partyModal .age").append('<span class="badge badge-pill badge-light">'+ ages[i] + '대</span>');
					}
				}else{
					$("#partyModal .age").append("<span class='badge badge-pill badge-light'>" + resp.age + "대만</span>");
				}
				if(resp.drinking == 1){
					$("#partyModal .drinking").text("음주OK");
				}else if(resp.drinking == 0){
					$("#partyModal .drinking").text("음주NO");
				}
				$("#partyModal .content").text(resp.content);
			})
		})
			
		var positions = [];
		// 인포윈도우를 표시하는 클로저를 만드는 함수입니다 
		function infoOverSet(customOverlay,map) {
			customOverlay.setMap(map);
		}
		// 인포윈도우를 닫는 클로저를 만드는 함수입니다 
		function infoOverClose(customOverlay) {
			customOverlay.setMap(null);
		}
		var customOverlayArray = [];
		var searchResultArray = [];
		var markers = [];
		$("#search").on("click",function(){ // 키워드 검색
			$.ajax({
				url:"/map/search",
				data:{keyword:$("#keyword").val()},
				dataType:"JSON"
			}).done(function(resp){
				$.each(customOverlayArray, function(i, item) {
					infoOverClose(item.customOverlay);
				});
				/* markers 초기화 */
				cafeMarkers.forEach(function(item) {item.marker.setVisible(false);});
				foodMarkers.forEach(function(item) {item.marker.setVisible(false);});
				cafeTopMarkers.forEach(function(item) {item.marker.setVisible(false);});
				foodTopMarkers.forEach(function(item) {item.marker.setVisible(false);});
				cafePartyMarkers.forEach(function(item) {item.marker.setVisible(false);});
				foodPartyMarkers.forEach(function(item) {item.marker.setVisible(false);});
				kakaoFoodMarkers.forEach(function(item){item.marker.setVisible(false);});
				kakaoCafeMarkers.forEach(function(item){item.marker.setVisible(false);});
				positions= [];
				if(markers.length != 0){
					$.each(markers,function(i, item){
						item.marker.setVisible(false);
					});
					//markers.forEach(function(item){item.marker.setVisible(false);});
				}
				
				$(".choose_info").html("");
				$(".search_result").html("");
				var cafePositions = [];
				var foodPositions = [];
				var cafePartyPositions = [];
				var foodPartyPositions = [];
				var cafeTopPositions = [];
				var foodTopPositions = [];
				var count = resp.map_list.length + resp.cafe_list.length + resp.food_list.length;
				if(count == 0){
					$(".search_result").append("<div class='search_count'><b>장소</b> "+count+"</div>");
					$(".search_result").append("<div style='font-size:10pt;padding-left:30px;padding-right:30px;'>검색결과가 존재하지 않습니다.</div>");
					$(".search_result").css('height','80vh');
				}else{
					$(".search_result").append("<div class='search_count'><b>장소</b> "+count+"</div>");
					$(".search_result").css('height','80vh');
					
				    var normalCafeImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/Asset6.png', 
					normalFoodImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/Asset5.png'
					partyCafeImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/Asset3.png',
					partyFoodImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/Asset4.png', 
					topCafeImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/Asset2.png',
					topFoodImageSrc = 'https://eat-together.s3.ap-northeast-2.amazonaws.com/Asset1.png',  
				    baseImageSize = new kakao.maps.Size(40, 60), // 마커이미지의 크기입니다
				    baseImageOption = {offset: new kakao.maps.Point(20, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
				    var normalCafeImage = new kakao.maps.MarkerImage(normalCafeImageSrc, baseImageSize, baseImageOption);
				    var normalFoodImage = new kakao.maps.MarkerImage(normalFoodImageSrc, baseImageSize, baseImageOption);
				    var partyCafeImage = new kakao.maps.MarkerImage(partyCafeImageSrc, baseImageSize, baseImageOption);
				    var partyFoodImage = new kakao.maps.MarkerImage(partyFoodImageSrc, baseImageSize, baseImageOption);
				    var topCafeImage = new kakao.maps.MarkerImage(topCafeImageSrc, baseImageSize, baseImageOption);
				    var topFoodImage = new kakao.maps.MarkerImage(topFoodImageSrc, baseImageSize, baseImageOption);
				    $.each(resp.map_list, function(i, item) { 
						if(item.top!=undefined){
							if(item.category == '카페'){
								cafeTopPositions.push({
									content: '<div class="mapcustomoverlay">' +
					    	        '  <a href="'+item.place_url+'" target="_blank">' +
					    	        '    <span class="category">'+ item.category +'</span>' +
					    	        '    <span class="title">'+ item.name +'</span>' +
					    	        '    <span class="address">'+ item.address +'</span>' +
					    	        '  </a>' +
					    	        '</div>', 
									latlng: new kakao.maps.LatLng(item.lat, item.lng),
									place_id: item.place_id
								})
							}else{
								foodTopPositions.push({
									content: '<div class="mapcustomoverlay">' +
					    	        '  <a href="'+item.place_url+'" target="_blank">' +
					    	        '    <span class="category">'+ item.category +'</span>' +
					    	        '    <span class="title">'+ item.name +'</span>' +
					    	        '    <span class="address">'+ item.address +'</span>' +
					    	        '  </a>' +
					    	        '</div>', 
									latlng: new kakao.maps.LatLng(item.lat, item.lng),
									place_id: item.place_id
								})
							}
						}else{
							if(item.category == '카페'){
								if(item.partyOn > 0){
									cafePartyPositions.push({
										content: '<div class="mapcustomoverlay">' +
						    	        '  <a href="'+item.place_url+'" target="_blank">' +
						    	        '    <span class="category">'+ item.category +'</span>' +
						    	        '    <span class="title">'+ item.name +'</span>' +
						    	        '    <span class="address">'+ item.address +'</span>' +
						    	        '  </a>' +
						    	        '</div>', 
										latlng: new kakao.maps.LatLng(item.lat, item.lng),
										place_id: item.place_id
									});				
								}else if(item.partyOn == 0){
									cafePositions.push({
										content: '<div class="mapcustomoverlay">' +
						    	        '  <a href="'+item.place_url+'" target="_blank">' +
						    	        '    <span class="category">'+ item.category +'</span>' +
						    	        '    <span class="title">'+ item.name +'</span>' +
						    	        '    <span class="address">'+ item.address +'</span>' +
						    	        '  </a>' +
						    	        '</div>', 
										latlng: new kakao.maps.LatLng(item.lat, item.lng),
										place_id: item.place_id
									});		
								}
							}else if(item.category == '음식점'){
								if(item.partyOn > 0){
									foodPartyPositions.push({
										content: '<div class="mapcustomoverlay">' +
						    	        '  <a href="'+item.place_url+'" target="_blank">' +
						    	        '    <span class="category">'+ item.category +'</span>' +
						    	        '    <span class="title">'+ item.name +'</span>' +
						    	        '    <span class="address">'+ item.address +'</span>' +
						    	        '  </a>' +
						    	        '</div>', 
										latlng: new kakao.maps.LatLng(item.lat, item.lng),
										place_id: item.place_id
									});
								}else if(item.partyOn == 0){
									foodPositions.push({
										content: '<div class="mapcustomoverlay">' +
						    	        '  <a href="'+item.place_url+'" target="_blank">' +
						    	        '    <span class="category">'+ item.category +'</span>' +
						    	        '    <span class="title">'+ item.name +'</span>' +
						    	        '    <span class="address">'+ item.address +'</span>' +
						    	        '  </a>' +
						    	        '</div>', 
										latlng: new kakao.maps.LatLng(item.lat, item.lng),
										place_id: item.place_id
									});
								}
							}					
						}
					});
					cafeMarkers = createMapTableMarker(cafePositions, normalCafeImage); // 일반 카페
					foodMarkers = createMapTableMarker(foodPositions, normalFoodImage); // 일반 음식점
					cafePartyMarkers = createMapTableMarker(cafePartyPositions, partyCafeImage); // 모임 모집중인 카페
					foodPartyMarkers = createMapTableMarker(foodPartyPositions, partyFoodImage); // 모임 모집중인 음식점
					cafeTopMarkers = createMapTableMarker(cafeTopPositions, topCafeImage); // 리뷰 평균 Top 카페
					foodTopMarkers = createMapTableMarker(foodTopPositions, topFoodImage); // 리뷰 평균 Top 음식점
					
					var line = $("<div class='search_list'></div>");
					for(var i = 0; i < resp.map_list.length; i++){
						var food = $("<div class='map_info'></div>");
						food.append("<img src='/resources/img/premium.png' style='height:25px;'>")
						food.append("<div class='place_name'>"+resp.map_list[i].name+"</div>");
						food.append("<div class='place_id' style='display:none;'>"+resp.map_list[i].place_id+"</div>");
						food.append("<div class='address_name'>"+resp.map_list[i].address+"</div>");
						food.append("<div class='category_name'>"+resp.map_list[i].category+"</div>");
						if(resp.map_list[i].phone != undefined){
							food.append("<div class='phone'>"+resp.map_list[i].phone+"</div>");						
						}
						line.append(food);			
					}
					for(var i = 0; i < resp.cafe_list.length; i++){
						var cafe = $("<div class='cafe_info'></div>");
						cafe.append("<div class='place_name'>"+resp.cafe_list[i].place_name+"</div>");
						cafe.append("<div class='place_id' style='display:none;'>"+resp.cafe_list[i].id+"</div>");
						cafe.append("<div class='address_name'>"+resp.cafe_list[i].address_name+"</div>");
						cafe.append("<div class='category_name'>"+resp.cafe_list[i].category_group_name+"</div>");
						cafe.append("<div class='phone'>"+resp.cafe_list[i].phone+"</div>");
						line.append(cafe);	
						positions.push({
			    	        content: '<div class="cafecustomoverlay">' +
			    	        '  <div>' +
			    	        '    <span class="title">'+ resp.cafe_list[i].place_name +'</span>' +
			    	        '  </div>' +
			    	        '</div>', 
			    	        latlng: new kakao.maps.LatLng(resp.cafe_list[i].y, resp.cafe_list[i].x)
						});
					}
					for(var i = 0; i < resp.food_list.length; i++){
						var food = $("<div class='food_info'></div>");
						food.append("<div class='place_name'>"+resp.food_list[i].place_name+"</div>");
						food.append("<div class='place_id' style='display:none;'>"+resp.food_list[i].id+"</div>");
						food.append("<div class='address_name'>"+resp.food_list[i].address_name+"</div>");
						food.append("<div class='category_name'>"+resp.food_list[i].category_group_name+"</div>");
						food.append("<div class='phone'>"+resp.food_list[i].phone+"</div>");
						line.append(food);	
						positions.push({
			    	        content: '<div class="foodcustomoverlay">' +
			    	        '  <div>' +
			    	        '    <span class="title">'+ resp.food_list[i].place_name +'</span>' +
			    	        '  </div>' +
			    	        '</div>', 
			    	        latlng: new kakao.maps.LatLng(resp.food_list[i].y, resp.food_list[i].x)
						});			
					}
					$(".search_result").append(line);
					var lat = resp.map_list[0].lat,
					lng = resp.map_list[0].lng;
					map.setCenter(new kakao.maps.LatLng(lat, lng));
					$.each(positions,function(i, item){
						var marker = addMarker(item);
						markers.push({marker:marker});
					});
					/* cafe_list, food_list 에 대한 marker 정보만 표시 */
				}
				
			})
		})
		$("#cafeBtn").on("click",function(){ // 카테고리 검색 - '카페' 버튼
			$.ajax({
				url:"/map/searchCafeBtn",
				dataType:"JSON"
			}).done(function(resp){
				$(".choose_info").html("");
				$(".search_result").html("");
				var count = resp.map_list.length + resp.cafe_list.length;
				$(".search_result").append("<div class='search_count'><b>장소</b> "+count+"</div>");
				$(".search_result").css('height','80vh');
				var line = $("<div class='search_list'></div>");
				for(var i = 0; i < resp.map_list.length; i++){
					var food = $("<div class='map_info'></div>");
					food.append("<img src='/resources/img/premium.png' style='height:25px;'>")
					food.append("<div class='place_name'>"+resp.map_list[i].name+"</div>");
					food.append("<div class='place_id' style='display:none;'>"+resp.map_list[i].place_id+"</div>");
					food.append("<div class='address_name'>"+resp.map_list[i].address+"</div>");
					food.append("<div class='category_name'>"+resp.map_list[i].category+"</div>");
					if(resp.map_list[i].phone != undefined){
						food.append("<div class='phone'>"+resp.map_list[i].phone+"</div>");						
					}
					line.append(food);			
				}
				if(resp.cafe_list.length > 0){
					for(var i = 0; i < resp.cafe_list.length; i++){
						var cafe = $("<div class='cafe_info'></div>");
						cafe.append("<div class='place_name'>"+resp.cafe_list[i].place_name+"</div>");
						cafe.append("<div class='address_name'>"+resp.cafe_list[i].address_name+"</div>");
						cafe.append("<div class='category_name'>"+resp.cafe_list[i].category_group_name+"</div>");
						cafe.append("<div class='phone'>"+resp.cafe_list[i].phone+"</div>");
						line.append(cafe);		
					}
				}
				$(".search_result").append(line);
				console.log(resp);
			})
		})
		$("#foodBtn").on("click",function(){ // 카테고리 검색 - '음식점' 버튼
			$.ajax({
				url:"/map/searchFoodBtn",
				dataType:"JSON"
			}).done(function(resp){
				$(".choose_info").html("");
				$(".search_result").html("");
				var count = resp.map_list.length + resp.food_list.length;
				$(".search_result").append("<div class='search_count'><b>장소</b> "+count+"</div>");
				$(".search_result").css('height','80vh');
				var line = $("<div class='search_list'></div>");
				for(var i = 0; i < resp.map_list.length; i++){
					var food = $("<div class='map_info'></div>");
					food.append("<img src='/resources/img/premium.png' style='height:25px;'>")
					food.append("<div class='place_name'>"+resp.map_list[i].name+"</div>");
					food.append("<div class='place_id' style='display:none;'>"+resp.map_list[i].place_id+"</div>");
					food.append("<div class='address_name'>"+resp.map_list[i].address+"</div>");
					food.append("<div class='category_name'>"+resp.map_list[i].category+"</div>");
					if(resp.map_list[i].phone != undefined){
						food.append("<div class='phone'>"+resp.map_list[i].phone+"</div>");						
					}
					line.append(food);			
				}
				if(resp.food_list.length > 0){
					for(var i = 0; i < resp.food_list.length; i++){
						var food = $("<div class='food_info'></div>");
						food.append("<div class='place_name'>"+resp.food_list[i].place_name+"</div>");
						food.append("<div class='address_name'>"+resp.food_list[i].address_name+"</div>");
						food.append("<div class='category_name'>"+resp.food_list[i].category_group_name+"</div>");
						food.append("<div class='phone'>"+resp.food_list[i].phone+"</div>");
						line.append(food);			
					}					
				}
				$(".search_result").append(line);
				console.log(resp);
			})
		})
		$(document).on("click",".map_info",function(){
			console.log($(this).find(".place_id").text());
			$.ajax({
				url:'/map/chooseMapInfo',
				type:"get",
				data:{place_id:$(this).find(".place_id").text()},
				dataType:"JSON"
			}).done(function(resp){
			    map.setCenter(new kakao.maps.LatLng(resp[0].lat, resp[0].lng));
				$.each(customOverlayArray, function(i, item) {
					infoOverClose(item.customOverlay);
				});
			    customOverlay = new kakao.maps.CustomOverlay({
		            position: map.getCenter(),
		            content: '<div class="mapcustomoverlay">' +
	    	        '  <a href="'+resp[0].place_url+'" target="_blank">' +
	    	        '    <span class="category">'+ resp[0].category +'</span>' +
	    	        '    <span class="title">'+ resp[0].name +'</span>' +
	    	        '    <span class="address">'+ resp[0].address +'</span>' +
	    	        '  </a>' +
	    	        '</div>'
		        });
				customOverlayArray.push({customOverlay : customOverlay});
		        infoOverSet(customOverlay,map);
			})
		})
		$(document).on("click",".cafe_info",function(){
			console.log($(this).find(".place_id").text());
			$.ajax({
				url:'/map/chooseCafeInfo',
				type:"get",
				data:{place_id:$(this).find(".place_id").text()},
				dataType:"JSON"
			}).done(function(resp){
			    map.setCenter(new kakao.maps.LatLng(resp.result.cafe.y, resp.result.cafe.x));
				$.each(customOverlayArray, function(i, item) {
					infoOverClose(item.customOverlay);
				});
			    customOverlay = new kakao.maps.CustomOverlay({
		            position: map.getCenter(),
		            content: '<div class="cafecustomoverlay">' +
	    	        '  <div>' +
	    	        '    <span class="title">'+ resp.result.cafe.place_name +'</span>' +
	    	        '  </div>' +
	    	        '</div>'
		        });
				customOverlayArray.push({customOverlay : customOverlay});
		        infoOverSet(customOverlay,map);
			})
		})
		$(document).on("click",".food_info",function(){
			console.log($(this).find(".place_id").text());
			$.ajax({
				url:'/map/chooseFoodInfo',
				type:"get",
				data:{place_id:$(this).find(".place_id").text()},
				dataType:"JSON"
			}).done(function(resp){
			    map.setCenter(new kakao.maps.LatLng(resp.result.food.y, resp.result.food.x));
				$.each(customOverlayArray, function(i, item) {
					infoOverClose(item.customOverlay);
				});
			    customOverlay = new kakao.maps.CustomOverlay({
		            position: map.getCenter(),
		            content: '<div class="foodcustomoverlay">' +
	    	        '  <div>' +
	    	        '    <span class="title">'+ resp.result.food.place_name +'</span>' +
	    	        '  </div>' +
	    	        '</div>'
		        });
				customOverlayArray.push({customOverlay : customOverlay});
		        infoOverSet(customOverlay,map);
			})
		})
		/****************** 리뷰 및 기타 영역 ******************/		
		$("#review_write").on("submit",function(){
			var result = false;
			if($(".reviewlist .writer b").text()==""){
				alert("로그인을 해주세요.");
			}else if($(".input_content").text()==""){
				alert("리뷰 내용을 입력해주세요.");
			}else if(!$("input[name='rating']").is(':checked')){
				alert("별점을 선택해주세요.");
			}else{
				result = true;
				$("#review_content").val($(".input_content").text());
			}
			return result;
		})
		$('#imgFile').on("change",function() {
			// files 로 해당 파일 정보 얻기.
			var file = this.files;
			// file[0].name 은 파일명 입니다.
			// 정규식으로 확장자 체크
			if(!/\.(gif|jpg|jpeg|png)$/i.test(file[0].name)) {
				alert('이미지 파일만 선택해 주세요.\n\n현재 파일 : ' + file[0].name);
				$(".filebox i").css('color','none');
			}
			else {// 체크를 통과했다면 종료.
				this.outerHTML = this.outerHTML;
				$(".filebox i").css('color','#038cfc');
				return;
			}
			// 체크에 걸리면 선택된  내용 취소 처리를 해야함.
			// 파일선택 폼의 내용은 스크립트로 컨트롤 할 수 없습니다.
			// 그래서 그냥 새로 폼을 새로 써주는 방식으로 초기화 합니다.
			// 이렇게 하면 간단 !?
		});
		$(".toLogin").on("click",function(){
			location.href = "/member/loginview";
		})
		$(".toSignUp").on("click",function(){
			location.href = "/member/signup_check";
		})
	})
	.ajaxStart(function(){
		$('#Progress_Loading').show(); //ajax실행시 로딩바를 보여준다.
	})
	.ajaxStop(function(){
		$('#Progress_Loading').hide(); //ajax종료시 로딩바를 숨겨준다.
	});	