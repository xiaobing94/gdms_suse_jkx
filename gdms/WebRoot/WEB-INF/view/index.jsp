<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="common-path.jsp" %>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="/favicon.ico" >
<LINK rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="res/lib/html5.js"></script>
<script type="text/javascript" src="res/lib/respond.min.js"></script>
<script type="text/javascript" src="res/lib/PIE_IE678.js"></script>
<![endif]-->
<link href="res/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="res/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="res/skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
<link href="res/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="res/css/style.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>毕业设计管理系统</title>
</head>
<body>
<header class="Hui-header cl"> <a class="Hui-logo l" title="毕业设计管理系统" href="">毕业设计管理系统</a>
	<nav class="mainnav cl" id="Hui-nav">
		<ul>
			<li class="dropDown dropDown_click">
				<ul class="dropDown-menu radius box-shadow">
				</ul>
			</li>
		</ul>
	</nav>
	<ul class="Hui-userbar">
		<li class="dropDown dropDown_hover"><a href="#" class="dropDown_A">${ user.name } <i class="Hui-iconfont">&#xe6d5;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="login">切换账户</a></li>
				<li><a href="logout">退出</a></li>
			</ul>
		</li>
		<li id="Hui-msg"> <a onclick="layer_show('消息列表','message/list',500,400);" title="消息"><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
		<li id="Hui-skin" class="dropDown right dropDown_hover"><a href="javascript:;" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
				<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
				<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
				<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
				<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
				<li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
			</ul>
		</li>
	</ul>
	<a aria-hidden="false" class="Hui-nav-toggle" href="#"></a> </header>
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
	    <c:if test="${ user.haveDirectorPermission() || user.haveLeaderPermission()}">
		<dl id="menu-member">
			<dt><i class="Hui-iconfont">&#xe60d;</i> 用户管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a href="javascript:;" onclick="member_add('导师信息导入','imporTeachertXls','','300')">导师信息导入</a></li> 
					<li><a href="javascript:;" onclick="member_add('学生信息导入','imporStudentXls','','300')">学生信息导入</a></li>
					<li><a href="javascript:;" onclick="member_add('导出学生信息','choise/xlsexport','','300')">导出学生信息</a></li>
				</ul>
			</dd>
		</dl>
		</c:if>
		<c:if test="${ user.haveTeacherPermission()||user.isStudent()}">
		<dl id="menu-shcool">
			<dt><i class="Hui-iconfont">&#xe616;</i> 阶段管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					
					<li><a onclick="layer_show('添加课题','issue/create',550,320);">添加课题</a></li>
					<c:if test="${ user.haveTeacherPermission() }">
					<li><a _href="issue/issueList">删除课题</a></li>
					<li><a _href="degree/getStudentList" href="javascript:void(0)">学生列表</a></li>
					</c:if>
					<c:if test="${ user.isStudent() }">
					<li><a onclick="layer_show('导师资料','issue/getMyTutor',400,400);">导师资料</a></li>
					<li><a _href="degree/getmydegreelist" href="javascript:void(0)">我的课题阶段列表</a></li>
					<li><a onclick="layer_show('上传阶段成果','degree/upload_degree',400,400);">上传阶段成果</a></li>
					</c:if>
					<!-- <li><a onclick="layer_show('导师资料','leader/setStart',400,400);">设置开始时间</a></li>
					<li><a onclick="layer_show('导师资料','leader/setGrade',400,400);">设置当前年级</a></li> -->
				</ul>
			</dd>
		</dl>
		</c:if>
		<dl id="menu-member">
			<dt><i class="Hui-iconfont">&#xe611;</i> 通知公告<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="notice/notice-list" href="javascript:;">通知公告列表</a></li> 
					<c:if test="${ !user.isStudent() }">
					<li><a _href="notice/add-notice" href="javascript:;">添加通知公告</a></li> 
					<li><a _href="notice/del-notice-list" href="javascript:;">管理通知公告</a></li> 
					</c:if>
				</ul>
			</dd>
		</dl>
		<c:if test="${ user.isStudent()||user.haveTeacherPermission()||user.haveDirectorPermission() }">
		<dl id="menu-article">
			<dt><i class="Hui-iconfont">&#xe616;</i> 毕业设计<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
				    <c:if test="${ user.isStudent() }">
					<li><a _href="choise/teacher_list" href="javascript:void(0)">选择导师</a></li>
					</c:if>
					<c:if test="${ user.haveTeacherPermission() }">
					<li><a _href="choise/student_list" href="javascript:void(0)">选择学生</a></li>
					</c:if>
					<c:if test="${ user.haveDirectorPermission() }">
					<li><a _href="choise/teachermajor" href="javascript:void(0)">分配学生</a></li>
					</c:if>
				</ul>
			</dd>
		</dl>
		</c:if>
		<c:if test="${ user.haveLeaderPermission() }">
		<dl id="menu-article">
			<dt><i class="Hui-iconfont">&#xe616;</i> 系统设置<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a onclick="layer_show('设置开始时间','leader/setStart',400,400);">设置开始时间</a></li>
					<li><a onclick="layer_show('设置当前年级','leader/setGrade',400,400);">设置当前年级</a></li>
				</ul>
			</dd>
		</dl>
		</c:if>
		<dl id="menu-article">
			<dt><i class="Hui-iconfont">&#xe616;</i> 我的资料<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a onclick="layer_show('密码修改','modifypassword',400,400);">修改密码</a></li>
					<li><a onclick="layer_show('查看我的信息','myinfo',400,400);">查看我的信息</a></li>
					<li><a onclick="layer_show('修改我的信息','modifyinfo',400,400);">修改我的信息</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-article">
			<dt><i class="Hui-iconfont">&#xe616;</i> 站内信<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a onclick="layer_show('消息列表','message/list',500,400);">消息列表</a></li>
				</ul>
			</dd>
		</dl>
	</div>
</aside>
<div class="dislpayArrow"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span title="我的桌面" data-href="notice/notice-list">通知公告</span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="notice/notice-list"></iframe>
		</div>
	</div>
</section>
<script type="text/javascript" src="res/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="res/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="res/js/H-ui.js"></script> 
<script type="text/javascript" src="res/js/H-ui.admin.js"></script> 
<script type="text/javascript">
function article_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
function picture_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
function product_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}
</script> 
<script type="text/javascript">
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s)})();
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html>