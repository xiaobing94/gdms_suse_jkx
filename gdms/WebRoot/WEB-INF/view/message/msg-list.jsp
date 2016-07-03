<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common-path.jsp" %>
<html>
<head>
	<%@ include file="../common-header.jsp" %>
</head>
<body>
	<div class="pd-20">
		<div id="tab_demo" class="HuiTab" role="tablist">
          <div class="tabBar cl">
          <span role="tab">未读消息</span>
          <span role="tab">已读消息 </span>
          </div>
          <div class="tabCon bk_gray pd-10" role="tabpanel" style="display: block;">
          	<ul>
          	<c:forEach items="${ unreadmessageList }" var="msg">
          		<li>
          			<a href="message/detail?msg_id=${msg.id}">${msg.note}</a>
          			<span style="float:right">${msg.dateline}</span>
          		</li>
          	</c:forEach>
          	</ul>
          </div>
          <div class="tabCon bk_gray pd-10" role="tabpanel" style="display: none;">
          		<ul>
          		<c:forEach items="${ readmessageList }" var="msg">
          		<li>
          			<a href="#">${msg.note}</a>
          			<span style="float:right">${msg.dateline}</span>
          		</li>
          		</c:forEach>
          	</ul>
          </div>
        </div>
	</div>
</body>
<script type="text/javascript" src="res/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="res/js/H-ui.js"></script> 
<script type="text/javascript" src="res/js/H-ui.admin.js"></script>

<script type="text/javascript">
$(function(){
	$.Huitab("#tab_demo .tabBar span","#tab_demo .tabCon","current","click","0");
	});
</script>
</html>