<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>房型开关管理</title>
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

			$("#q_roomtype_all").click(function () {
				$(".subcontrol").not(this).prop('checked', this.checked);
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:if test="${page_type == 'order' }">
			<li><a href="${ctx}/datacenter/orders/cslist">分销订单确认列表</a></li>
		</c:if>
		<c:if test="${page_type == 'hotel' }">
			<li><a href="${ctx}/hotel/hotel">酒店中心</a></li>
		</c:if>
		<c:if test="${page_type == 'order_query' }">
			<li><a href="${ctx}/datacenter/orders/list">分销订单列表</a></li>
		</c:if>
		<li><a href="${ctx}/hotel/hotelOpensetting?hotelid=${hotelOpensetting.hotelid}&page_type=${page_type}">房型开关列表</a></li>
		<li class="active"><a href="${ctx}/hotel/hotelOpensetting/form?id=${hotelOpensetting.id}&hotelid=${hotelOpensetting.hotelid}&page_type=${page_type}">房型开关<shiro:hasPermission name="hotel:hotelOpensetting:edit">${not empty hotelOpensetting.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hotel:hotelOpensetting:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="hotelOpensetting" action="${ctx}/hotel/hotelOpensetting/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="hotelid"/>
		<input type="hidden" id="page_type" name="page_type" value="${page_type}">
		<sys:message content="${message}"/>
		<%--<div class="control-group">
			<label class="control-label">酒店id：</label>
			<div class="controls">
				<form:input path="hotelid" htmlEscape="false" maxlength="20" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">房型：</label>
			<div class="controls">
				<%--<form:select path="roomtypeid" htmlEscape="false" maxlength="10" class="input-xlarge required digits">
					<form:option value="" label=""/>
					<form:options items="${dic_roomtype}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>--%>
				<c:choose>
					<c:when test="${hotelOpensetting.id!=null}">
						<form:select path="roomtypeid" htmlEscape="false" maxlength="10" class="input-xlarge required digits">
							<form:option value="" label=""/>
							<form:options items="${dic_roomtype}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</c:when>
					<c:otherwise>
						<input type="checkbox" id="q_roomtype_all" name="q_roomtype_all" value="">
						<label for="q_roomtype_all">全部</label>
						<c:forEach items="${dic_roomtype}" var="item" varStatus="status">
							&nbsp;
							<input type="checkbox" id="q_roomtype_${status.index}" name="q_roomtype" value="${item.value}" class="subcontrol input-xlarge required" >
							<label for="q_roomtype_${status.index}">${item.label}</label>
						</c:forEach>
						<span class="help-inline"><font color="red">*</font> </span>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始日期：</label>
			<div class="controls">
				<input name="begindate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${hotelOpensetting.begindate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束日期：</label>
			<div class="controls">
				<input name="enddate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${hotelOpensetting.enddate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">开关状态：</label>
			<div class="controls">
				<form:input path="state" htmlEscape="false" maxlength="1" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<%--<div class="control-group">
			<label class="control-label">创建时间：</label>
			<div class="controls">
				<input name="createtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${hotelOpensetting.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" disabled="true"/>
				&lt;%&ndash;<span class="help-inline"><font color="red">*</font> </span>&ndash;%&gt;
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间：</label>
			<div class="controls">
				<input name="updatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${hotelOpensetting.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" disabled="true"/>
				&lt;%&ndash;<span class="help-inline"><font color="red">*</font> </span>&ndash;%&gt;
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建人：</label>
			<div class="controls">
				<form:input path="createuser" htmlEscape="false" maxlength="20" class="input-xlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">修改人：</label>
			<div class="controls">
				<form:input path="updateuser" htmlEscape="false" maxlength="20" class="input-xlarge " disabled="true"/>
			</div>
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="hotel:hotelOpensetting:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>