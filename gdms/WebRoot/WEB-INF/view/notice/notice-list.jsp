<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../common-path.jsp" %>

<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="res/lib/html5.js"></script>
<script type="text/javascript" src="res/lib/respond.min.js"></script>
<script type="text/javascript" src="res/lib/PIE_IE678.js"></script>
<![endif]-->
<link href="res/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="res/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="res/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="res/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>通知公告</title>
</head>
<body>
<div class="pd-20" style="padding-top:20px;">
  <p class="f-20 text-success">欢迎使用计科系毕业设计管理系统</p>
  <div class="panel panel-default">
	<div class="panel-header">通知公告</div>
	<div class="panel-body">
		<div class="row cl">
				    	<ul>
				    	<c:forEach var="notice" items="${teacherNoticeList}">
					    	<li><a href="notice/detail?notice_id=${notice.id}">[老师公告]<c:out value="${notice.subject}"></c:out></a><span style="float:right"><c:out value="${notice.dateline}"></c:out></span></li>
					    </c:forEach>
					    <c:forEach var="notice" items="${majorNoticeList}">
					    	<li><a href="notice/detail?notice_id=${notice.id}">[专业公告]<c:out value="${notice.subject}"></c:out></a><span style="float:right"><c:out value="${notice.dateline}"></c:out></span></li>
					    </c:forEach>
					    <c:forEach var="notice" items="${collegeNoticeList}">
					    	<li><a href="notice/detail?notice_id=${notice.id}">[学院公告]<c:out value="${notice.subject}"></c:out></a><span style="float:right"><c:out value="${notice.dateline}"></c:out></span></li>
					    </c:forEach>
					    </ul>
    	</div>
	</div>
</div>
</div>
<%@include file="../common-footer.jsp" %>
<script type="text/javascript" src="res/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="res/js/H-ui.js"></script>
</body>
</html>