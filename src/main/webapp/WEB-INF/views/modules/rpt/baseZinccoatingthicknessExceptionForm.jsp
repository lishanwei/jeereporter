<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>异常历史数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/rpt/baseZinccoatingthicknessException/">异常历史数据列表</a></li>
		<li class="active"><a href="${ctx}/rpt/baseZinccoatingthicknessException/form?id=${baseZinccoatingthicknessException.id}">异常历史数据<shiro:hasPermission name="rpt:baseZinccoatingthicknessException:edit">${not empty baseZinccoatingthicknessException.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="rpt:baseZinccoatingthicknessException:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="baseZinccoatingthicknessException" action="${ctx}/rpt/baseZinccoatingthicknessException/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">记录时间：</label>
			<div class="controls">
				<input name="logtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${baseZinccoatingthicknessException.logtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">时间毫秒值：</label>
			<div class="controls">
				<form:input path="msvalue" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">班组：</label>
			<div class="controls">
				<form:select path="loggroup" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('RPT_WORK_TEAM')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生产编号：</label>
			<div class="controls">
				<form:input path="prodcode" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生成编号：</label>
			<div class="controls">
				<form:input path="gencode" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">测厚仪工作模式：</label>
			<div class="controls">
				<form:input path="workmode" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">带宽：</label>
			<div class="controls">
				<form:input path="bandwidth" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">带厚：</label>
			<div class="controls">
				<form:input path="bandthickness" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">线速：</label>
			<div class="controls">
				<form:input path="linespeed" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">钢卷行走长度：</label>
			<div class="controls">
				<form:input path="walklen" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单侧目标上锌量：</label>
			<div class="controls">
				<form:input path="zincrateunilateral" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">正面目标上锌量：</label>
			<div class="controls">
				<form:input path="zincratetargetfront" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反面目标上锌量：</label>
			<div class="controls">
				<form:input path="zincratetargetreverse" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">正面检测位置：</label>
			<div class="controls">
				<form:input path="detectionpositionfront" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反面检测位置：</label>
			<div class="controls">
				<form:input path="detectionpositionreverse" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">正面上实时上锌量：</label>
			<div class="controls">
				<form:input path="zincratefront" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反面上实时上锌量：</label>
			<div class="controls">
				<form:input path="zincratereverse" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">接头信号: 0和1.：</label>
			<div class="controls">
				<form:input path="flag" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">正面目标上锌量最大值：</label>
			<div class="controls">
				<form:input path="zincratetargetfrontmaxval" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">正面目标上锌量最小值：</label>
			<div class="controls">
				<form:input path="zincratetargetfrontminval" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">正面上锌量偏差值：</label>
			<div class="controls">
				<form:input path="zincratetargetfrontoffset" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反面目标上锌量最大值：</label>
			<div class="controls">
				<form:input path="zincratetargetreversemaxval" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反面目标上锌量最小值：</label>
			<div class="controls">
				<form:input path="zincratetargetreverseminval" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反面上锌量偏差值：</label>
			<div class="controls">
				<form:input path="zincratetargetreverseoffset" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="rpt:baseZinccoatingthicknessException:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>