<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ include file="../common-path.jsp" %>
<title>设置时间</title>
<link href="res/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="res/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="res/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="pd-20">
	
	<form id="form-grade-add" method="post" action="">
		<div class="row cl">
		<div  class="col-2"></div>
		<label class="form-label col-3 text-l">选择年级：</label>
	      <div class="formControls col-5">
	      	<select class="select" name="grade" size="1">
				<option value="2013" selected="">2013</option>
				<option value="2014">2014</option>
				<option value="2015">2015</option>
				<option value="2016">2016</option>
				<option value="2017">2017</option>
				<option value="2018">2018</option>
				<option value="2019">2019</option>
			</select>
	      </div>
	     <div  class="col-2"></div>
	     </div>
	     <br>
	     <div class="row cl">
	     	<div class="formControls col-8 col-offset-2">
	     		<input class="btn btn-primary btn-block radius" type=submit id="submit"/>
	     	</div>
	     </div>
	     </form>
	
</div>
</body>
<script type="text/javascript" src="res/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="res/lib/icheck/jquery.icheck.min.js"></script> 
<script type="text/javascript" src="res/lib/Validform/5.3.2/Validform.min.js"></script>
<script type="text/javascript" src="res/js/H-ui.js"></script> 
<script type="text/javascript" src="res/js/H-ui.admin.js"></script>
<script type="text/javascript" src="http://pwms.vwycm.cn:80/res/lib/My97gradePicker/WgradePicker.js"></script> 
<script type="text/javascript">
$(function(){
	$("#form-grade-add").Validform({
		tiptype:2,
		callback:function(form){
			form[0].submit();
			var index = parent.layer.getFrameIndex(window.name);
			parent.$('.btn-refresh').click();
			parent.layer.close(index);
		}
	});
	$("#submit").click(function(){
		$("#form-grade-add").submit();
	});
});
</script>

</html>