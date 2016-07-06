<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common-path.jsp" %>
<html>
<head>
	<%@ include file="../common-header.jsp" %>
</head>
<body>
	<div class="pd-20">
		<div class="row cl bk-gary radius">
		<div class="text-c">
			<span>站内信</span>
		</div>
		</div>
		<c:forEach items="${ msgList }" var="msg">
		<div class="row cl">
			<ul class="commentList">
			<c:if test="${user.id!=msg.fromId}">
            <li class="item cl"> <a href="#"><i class="avatar size-L radius"><img alt="" src="http://static.h-ui.net/h-ui/images/ucnter/avatar-default.jpg"></i></a>
              <div class="comment-main">
                <header class="comment-header">
                  <div class="comment-meta"><a class="comment-author" href="#"><c:out value="${msg.fromer.name}"></c:out></a> 
                    <time title="<c:out value="${msg.dateline}"></c:out>" datetime="<c:out value="${msg.dateline}"></c:out>"><c:out value="${msg.dateline}"></c:out></time>
                  </div>
                </header>
                <div class="comment-body">
                  <p><a href="#">@<c:out value="${msg.recver.name}"></c:out></a> <c:out value="${msg.note}"></c:out></p>
                </div>
              </div>
            </li>
            </c:if>
            <c:if test="${ user.id == msg.fromId }">
            <li class="item cl comment-flip"> <a href="#"><i class="avatar size-L radius"><img alt="" src="http://static.h-ui.net/h-ui/images/ucnter/avatar-default.jpg"></i></a>
              <div class="comment-main">
                <header class="comment-header">
                  <div class="comment-meta"><a class="comment-author" href="#"><c:out value="${msg.fromer.name}"></c:out></a> 
                    <time title="<c:out value="${msg.dateline}"></c:out>" datetime="<c:out value="${msg.dateline}"></c:out>"><c:out value="${msg.dateline}"></c:out></time>
                  </div>
                </header>
                <div class="comment-body">
                  <p><a href="#">@<c:out value="${msg.recver.name}"></c:out></a> <c:out value="${msg.note}"></c:out></p>
                </div>
              </div>
            </li>
            </c:if>
          </ul>
		</div>
		</c:forEach>
		<form action="message/send" method="post">
		<div class="row cl pt-20">
		    <input type="hidden" name="recvId" value="${message.fromId}">
			<div class="formControls col-12">
	        <textarea name="note" cols="" rows="" class="textarea"  placeholder="回复" dragonfly="true" onKeyUp="textarealength(this,255)"></textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/255</p>
	      </div>
		</div>
		<div class="row cl pt-20">
			<input type="submit" class="btn btn-primary btn-block" value="回复"/>
		</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="res/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="res/js/H-ui.js"></script> 

<script type="text/javascript">
$(function(){
	$.Huitab("#tab_demo .tabBar span","#tab_demo .tabCon","current","click","0");
	});
</script>
</html>