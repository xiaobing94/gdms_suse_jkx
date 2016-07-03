<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<base href="<%=basePath%>"></base>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,member-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="../lib/html5.js"></script>
<script type="text/javascript" src="../lib/respond.min.js"></script>
<script type="text/javascript" src="../lib/PIE_IE678.js"></script>
<![endif]-->
<link href="res/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="res/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="res/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet"
	type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>导师列表</title>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>
		毕业设计 <span class="c-gray en">&gt;</span> 选择导师<a
			class="btn btn-success radius r mr-20"
			style="line-height:1.6em;margin-top:3px"
			href="javascript:location.replace(location.href);" title="刷新"><i
			class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="pd-20">
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="r">共有数据：<strong>88</strong> 条
			</span>
		</div>
		<div class="mt-20">
			<table
				class="table table-border table-bordered table-hover table-bg table-sort">
				<thead>
					<tr class="text-c">
						<th width="80">ID</th>
						<th width="100">导师姓名</th>
						<th>导师简介</th>
						<th width="100">选择该导师人数</th>
						<th width="90">状态</th>
						<th width="100">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="teacher" items="${teacherList}">
						<tr class="text-c">
							<td>${teacher.id}</td>
							<td>${teacher.name}</td>
							<td>${teacher.introduction}</td>
							<td>${teacher.total}</td>
							<td class="td-status"><span
								class="label label-defaunt radius">未选择</span></td>
							<td class="td-manage"><a style="text-decoration:none"
								onclick="choose_teacher(this,${teacher.id})" href="javascript:;"
								title="选择导师"><i class="Hui-iconfont"></i></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript" src="res/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="res/lib/layer/1.9.3/layer.js"></script>
	<script type="text/javascript" src="res/lib/laypage/1.2/laypage.js"></script>
	<script type="text/javascript"
		src="res/lib/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript"
		src="res/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="res/js/H-ui.js"></script>
	<script type="text/javascript" src="res/js/H-ui.admin.js"></script>
	<script type="text/javascript">
		$(function() {
			$('.table-sort').dataTable();
			$('.table-sort tbody').on('click', 'tr', function() {
				if ($(this).hasClass('selected')) {
					$(this).removeClass('selected');
				} else {
					table.$('tr.selected').removeClass('selected');
					$(this).addClass('selected');
				}
			});
		});
		/*选择-导师*/
		function choose_teacher(obj, id) {
			var flag;
			$(obj).parents("tbody").children().each(function() {
				var v = $(this).find("span.label");
				flag = (v.text() == '已选择');
				console.log(v.text() + flag);
				if (flag) {
					console.log("已经选择!");
					layer.msg('只能选择一位导师!', {
						icon : 5,
						time : 1000
					});
					return false;
				}
			});
			layer
					.confirm(
							'确认要选择吗？',
							function(index) {
								$(obj)
										.parents("tr")
										.find(".td-manage")
										.prepend(
												'<a style="text-decoration:none" onclick="choose_cancel(this,id)" href="javascript:;" title="取消选择"><i class="Hui-iconfont"></i></a>');
								$(obj)
										.parents("tr")
										.find(".td-status")
										.html(
												'<span class="label label-success radius">已选择</span>');
								$(obj).remove();
								$.ajax({
									url: "choise/choise-teacher?teacher_id="+id,
									type: "GET",
									dataType:'json',
									success:function(data){
										if(data["msg"]=="成功"){
											layer.msg('选择成功!', {
												icon : 6,
												time : 1000
											});
										}else{
											layer.msg(data["msg"], {
												icon : 5,
												time : 1000
											});
										}
									},
									error:function(er){
										layer.msg('错误!', {
											icon : 6,
											time : 1000
										});
										}
								});
							});
		}
		/*取消-选择*/
		function choose_cancel(obj, id) {
			layer
					.confirm(
							'确认要取消选择吗？',
							function(index) {
								$(obj)
										.parents("tr")
										.find(".td-manage")
										.prepend(
												'<a style="text-decoration:none" onclick="choose_teacher(this,id)" href="javascript:;" title="选择导师"><i class="Hui-iconfont"></i></a>');
								$(obj)
										.parents("tr")
										.find(".td-status")
										.html(
												'<span class="label label-defaunt radius">未选择</span>');
								$(obj).remove();
								layer.msg('已取消!', {
									icon : 5,
									time : 1000
								});
							});
		}
	</script>
</body>
</html>