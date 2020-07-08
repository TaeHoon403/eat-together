<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- menubar -->
<div id="menubar" class="h4 text-center">
	<div class="menu">
		<a href="/member/mypage_myinfo" class="menubar-list">내 정보</a>
	</div>
	<div class="menu">
		<a href="/msg/msg_list_sender" class="menubar-list">쪽지함</a>
	</div>
	<div class="menu">
		<a href="/party/selectByWriter?mcpage=0" class="menubar-list">내 모임</a>
	</div>
	<div class="menu">
		<a href="/review/selectById?mcpage=0" class="menubar-list">내 리뷰</a>
	</div>
</div>