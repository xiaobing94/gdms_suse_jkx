<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="../common-path.jsp" %>
<title>添加课题</title>
<link href="res/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="res/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="res/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="pd-20">
	<form action=""  method="post" class="form form-horizontal" id="form-topic-add">
		<div class="row cl">
	      <label class="form-label col-3"><span class="c-red">*</span>课题名称：</label>
	      <div class="formControls col-7">
	        <input type="text" class="input-text" value="" placeholder="" id="topic" name="subject" datatype="*2-30" nullmsg="不能为空">
	      </div>
	      <div class="col-2"> </div>
	    </div>
	    <div class="row cl">
	      <label class="form-label col-3">说明：</label>
	      <div class="formControls col-7">
	        <textarea name="introduction" cols="" rows="" class="textarea"  placeholder="课题简介" dragonfly="true" onKeyUp="textarealength(this,255)"></textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/255</p>
	      </div>
	      <div class="col-2"> </div>
    </div>
	    <br>
	    <div class="row cl">
	    <div class="col-10">
        <input id="submit" class="btn btn-primary btn-block radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
        </div>
    </div>
	</form>
</div>
<script type="text/javascript" src="res/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="res/lib/Validform/5.3.2/Validform.min.js"></script>
<script type="text/javascript" src="res/js/H-ui.js"></script> 
<script type="text/javascript" src="res/js/H-ui.admin.js"></script>
<script type="text/javascript">
	$(function(){
		$("#form-topic-add").Validform({
			tiptype:2,
			callback:function(form){
				form[0].submit();
				var index = parent.layer.getFrameIndex(window.name);
				parent.$('.btn-refresh').click();
				parent.layer.close(index);
			}
		});
	});
</script>
</body>
</html>