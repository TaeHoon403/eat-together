<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
<script>
	$(function() {
		var scrolled = false;
		var viewed;
		$(".input-area").empty();
		var ws = new WebSocket("ws://192.168.60.19/chat")
		ws.onmessage = function(e) {
			var some = e.data.split(":");
			var str = some[1];
			for (var i = 2; i < some.length; i++) {
				str += ":"+some[i];
			}
			if (some[0] == $(".message-area>div:last").attr("class")) {
				$(".message-area>div:last").append("<br><div>" + str);
			} else {
				var line = $("<div>");
				line.attr("class", some[0]);
				line.append(some[0]);
				line.append("<br><div>" +str)
				if (some[0] == "hereasd") {
					viewed = $('.message-area')[0].scrollHeight;
				}
				$(".message-area").append(line);
			}
			if (scrolled) {
				$('.message-area')
						.scrollTop($('.message-area')[0].scrollHeight);
			}
		}

		$("#aaa").on("click", function() {
			$('.message-area').scrollTop(viewed);
		})
		$(".message-area")
				.on(
						"mousewheel",
						function(e) {
							var wheel = e.originalEvent.wheelDelta;
							if (wheel > 0) {
								scrolled = false;
							}
							if ($('.message-area')[0].scrollHeight - 50 <= ($(
									'.message-area').scrollTop() + $('.chat-box')[0].scrollHeight)) {
								$(".hereasd").remove();

								scrolled = true;
							}
							console.log(scrolled);
						});

		$(".input-area").keydown(function(key) {
			if (key.keyCode == 13) {
				var text = $(".input-area").text();
				if (text.trim() != "") {
					scrolled = true;
					ws.send(text.trim());
				}
				$(".input-area").empty();
				return false;
			}
		})
	})
</script>
<style>

            *{font-family: 'Jua', sans-serif;}
            html{height: 100%;}
            body {background-image: linear-gradient(90deg, #09203f 0%, #537895 80%);
                height: 100%;
            }
            

            .chat-box {
                width: 420px;
                height: 800px;
                margin: auto;
                border-radius: 50px;
                background-color: white;
            }

            .message-area {
                overflow-y: scroll;
                white-space: pre-line;
                margin: auto;
                width: 90%;
                height: 84%;background-image: linear-gradient(-225deg, #69EACB 0%, #EACCF8 48%, #6654F1 100%);
                background: linear-gradient( 45deg, #a1c4fd, #c2e9fb , white);
            }

            .message-area::-webkit-scrollbar {
                width: 10px;
            }

            .message-area::-webkit-scrollbar-thumb {
                background-color: #ffd04f;
            }

            .message-area::-webkit-scrollbar-track {
                background-color: #00000000;
            }

            .input-area{
                overflow:hidden;
                outline: none;
                margin: auto;
                margin-top: 1.5%;
                width: 80%;
                height: 6%;
                background-color: white;
                border: 0.5px solid #00000010;
                border-radius: 10px;
            }

            .head-area {
                margin: auto;
                width: 95%;
                height: 8%;
            }

            b {
                font-size: 30px;
            }
            .message-area>*{
                margin: 10px;
            }
            .message-area>*>div {
                max-width: 100%;
                overflow: hidden;
                white-space: normal;
                word-break: break-all;
                font-size: 20px;
                background: linear-gradient( 270deg,#f3e7e9 ,#e3eeff, white);
                color : #00000095;
                display: inline-block;
                border-radius: 10px;
                border-top-left-radius: 0px;
            }

            .나 {
                text-align: right;
            }

            .나>div {
                background: linear-gradient( 270deg,#fbed96 , white);
                color: #537895;
                border-radius: 10px;
                border-bottom-right-radius: 0px;
            }

            [contenteditable=true]:empty:before{
                content: attr(placeholder);
                color:#00000030;
                font-size: 30px;
                display: block;
            }

            div[contenteditable=true] {
                color : black;
                font-size: 15px;
            }
        </style>
</head>
<body>
	<div class="chat-box">
		<div class="head-area">
			<br>
			<b>CHATROOM#</b>
		</div>
		<div class="message-area"></div>
		<div class="input-area" contenteditable="true" placeholder="Type a message"></div>
	</div>
</body>
</html>