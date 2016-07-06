<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="../common-path.jsp" %>
<title>导师资料</title>
<link href="res/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="res/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="res/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="cl pd-20" style=" background-color:#5bacb6">
  <img class="avatar size-XL l" src="res/images/user.png">
  <dl style="margin-left:80px; color:#fff">
    <dt><span class="f-18"><c:out value="${teacher.name}"></c:out></span> <span class="pl-10 f-12">职称：<c:out value="${teacher.jobtitle}"></c:out><a onclick="layer_show('发送消息给<c:out value="${teacher.name}"></c:out>','message/send?recvId=${teacher.id}',300,300);">发送消息</a>
	</span></dt>
    <dd class="pt-10 f-12" style="margin-left:0"><c:out value="${teacher.introduction}"></c:out></dd>
  </dl>
</div>
	<div class="pd-20">
		<table class="table table-border table-bordered">
		<thead>
			<tr class="text-c">
				<th>选择课题</th>
				<th>课题名称</th>
			</tr>
		</thead>
    <tbody>
    <c:forEach items="${issueList}" var="issue">
    <tr class="text-c"">
        <th class="text-c"">
        	<input type="radio" value="${issue.id}" name="cid"/>
        </th>
        <td>
        	<a onclick="layer_show('导师资料','issue/issue-detail?issue_id=${issue.id}',300,300);"><c:out value="${issue.subject}"></c:out></a>
        </td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
  <br>
  <input class="btn btn-block btn-primary radius" id="submit" type="button" value="确认选择">
	</div>
</body>
<script type="text/javascript" src="res/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="res/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="res/js/H-ui.js"></script> 
<script type="text/javascript" src="res/js/H-ui.admin.js"></script> 
<script type="text/javascript">
$(function(){
	$("#submit").click(function(){
		$("input[name='cid']").each(function(){
			if($(this).is(":checked")){
				var cid = $(this).val();
				$.ajax({ 
					type: "POST", 
					url: "issue/choise?issue_id=" + cid, 
					success: function(data) { 
						layer.msg(data['msg'],{icon: 6,time:1000});
						clock();
					} ,
					error : function(e) {
						layer.msg('选择课题失败',{icon: 5,time:1000});
					}
				}); 
			}
		});
		//layer.msg('请至少选择一项',{icon: 5,time:1000});
		return false;
	});
});
var i=2;
function closewin(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index); 
}
function clock(){
	i=i-1;
	if(i>0){
		setTimeout("clock();",1000);
	}
	else {
		closewin();
	}
}
</script>
</html>