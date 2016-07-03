<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common-path.jsp" %>
<html>
<head>
<%@ include file="../common-header.jsp" %>
<title>添加阶段</title>
</head>
<body>
<div class="pd-20">
  <form name="uploadfile" action="" method="post" class="form form-horizontal" id="form-file-add" enctype="multipart/form-data">
    <div class="row cl">
      <label class="form-label col-3">附件：</label>
      <div class="formControls col-5"> <span class="btn-upload form-group col-12">
        <input class="input-text upload-url" type="text" name="uploadfile-2" id="uploadfile-2" readonly  datatype="*" nullmsg="请添加附件！">
        <a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 添加</a>
        <input type="file" multiple name="file" class="input-file" accept="">
        </span> 
        <input type="hidden" name="type" value="${type}">
        </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
    	<label class="form-label col-3">说明：</label>
    	<div class="formControls col-5">
	        <textarea name="explain" cols="" rows="" class="textarea"  placeholder="阶段说明" dragonfly="true" onKeyUp="textarealength(this,255)"></textarea>
	        <p class="textarea-numberbar"><em class="textarea-length">0</em>/255</p>
	      </div>
	      <div class="col-2"> </div>
    </div>
    <div class="row cl">
      <div class="col-9 col-offset-3">
        <input id="submit" class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
      </div>
    </div>
  </form>
</div>

<script type="text/javascript" src="res/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="res/lib/Validform/5.3.2/Validform.min.js"></script>
<script type="text/javascript" src="res/js/H-ui.js"></script> 
<script type="text/javascript" src="res/js/H-ui.admin.js"></script>
<script type="text/javascript">
$("#form-file-add").Validform({
	tiptype:2,
	callback:function(form){
		form[0].submit();
		var index = parent.layer.getFrameIndex(window.name);
		parent.$('.btn-refresh').click();
		parent.layer.close(index);
	}
});
</script>
</body>
</html>