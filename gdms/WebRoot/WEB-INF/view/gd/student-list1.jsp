<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../common-path.jsp" %>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,member-scalable=no" />
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
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>导师列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页  <span class="c-gray en">&gt;</span> 毕业设计 <span class="c-gray en">&gt;</span> 学生列表<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div class="mt-20">
	<form action="choise/distribute?teacherId=${teacherId}" method="post" id="form-assign-student">
	<table class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width="25" class="sorting_disabled" rowspan="1" colspan="1" aria-label="" style="width: 25px;"><input type="checkbox" name="" value=""></th>
				<th width="80">ID</th>
				<th width="100">学生姓名</th>
				<th>学生简介</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="student" items="${studentList}">
			<tr class="text-c">
				<td><input type="checkbox" value="${student.id}" name="studentId"></td>
				<td><c:out value="${student.workId}"></c:out></td>
				<td><c:out value="${student.name}"></c:out></td>
				<td><c:out value="${student.introduction}"></c:out></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="row cl">
      <div class="col-12">
        <input id="submit" class="btn btn-primary radius btn-block" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
      </div>
    </div>
	</form>
	</div>
</div>
<script type="text/javascript" src="res/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="res/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="res/lib/laypage/1.2/laypage.js"></script> 
<script type="text/javascript" src="res/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="res/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="res/js/H-ui.js"></script> 
<script type="text/javascript" src="res/js/H-ui.admin.js"></script> 
<script type="text/javascript">
$(function(){
	$('.table-sort').dataTable();
	$('.table-sort tbody').on( 'click', 'tr', function () {
		if ( $(this).hasClass('selected') ) {
			$(this).removeClass('selected');
		}
		else {
			table.$('tr.selected').removeClass('selected');
			$(this).addClass('selected');
		}
	});
});
$(function(){
	$("#submit").click(function(){
		var checkFlag = false;
		$("input[name='studentId']").each(function(){
			if($(this).is(":checked")){
				checkFlag = true;
			}
		});
		if(!checkFlag){
			layer.msg('请至少选择一位学生！',{icon: 5,time:1000});
			return false;
		}
		$("form-assign-student").submit();
		
		window.close();
	});
});
</script> 
</body>
</html>