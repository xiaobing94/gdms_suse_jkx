<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="../common-path.jsp" %>
<title>课题介绍</title>
<link href="res/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="res/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="res/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
</head>
<body>
<c:forEach items="${ commentList }" var="comment">
<div class="cl pd-20">
  <dl style="margin-left:10px">
    <dd class="pt-10 f-13" style="margin-left:0">
    	<c:out value="${comment.text}"></c:out>
    </dd>
  </dl>
  <dl style="margin-right:10px">
    <dd class="pt-10 f-13" style="margin-left:0">
    	<c:out value="${comment.dateline}"></c:out>
    </dd>
  </dl>
</div>
</c:forEach>
</body>
<script type="text/javascript" src="res/lib/jquery/1.9.1/jquery.min.js"></script> 
</html>