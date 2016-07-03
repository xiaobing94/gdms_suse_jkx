<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common-path.jsp" %>
<html>
<head>
<%@ include file="../common-header.jsp" %>
<title>添加评论</title>
</head>
<body>
<form action="" method="post">
<input type="hidden" name="degreeId" value="${degreeId}">
<div class="pd-20">
	<div class="row cl">
		<div class="formControls col-12">
	        <textarea name="text" cols="" rows="" class="textarea"  placeholder="输入评论" dragonfly="true" onKeyUp="textarealength(this,255)"></textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/255</p>
	     </div>
	</div>
	<br>
	<div class="row cl">
		<div class="col-12">
		<input id="submit" class="btn btn-primary btn-block radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
		</div>
	</div>
</div>
</form>
</body>
<script type="text/javascript" src="res/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="res/js/H-ui.js"></script> 
<script type="text/javascript" src="res/js/H-ui.admin.js"></script>
</html>