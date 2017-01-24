<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/log/bisLog/">日志列表</a></li>
		<shiro:hasPermission name="log:bisLog:edit"><li><a href="${ctx}/log/bisLog/form">日志添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bisLog" action="${ctx}/log/bisLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>系统：</label>
				<form:select path="system" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('system')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>业务ID：</label>
				<form:input path="bussinessId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>业务类型：</label>
				<form:select path="bussinssType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bussinssType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%-- <li><label>内容：</label>
				<form:input path="content" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li> --%>
			<li><label>操作人：</label>
				<form:input path="operator" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>系统</th>
				<th>业务ID</th>
				<th>业务类型</th>
				<th>内容</th>
				<th>操作人</th>
				<th>创建时间</th>
				<shiro:hasPermission name="log:bisLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bisLog">
			<tr>
				<td>
					${fns:getDictLabel(bisLog.system, 'system', bisLog.system)}
				</td>
				<td>
					${bisLog.bussinessId}
				</td>
				<td>
					${fns:getDictLabel(bisLog.bussinssType, 'bussinssType', bisLog.bussinssType)}
				</td>
				<td>
					${bisLog.content}
				</td>
				<td>
					${bisLog.operator}
				</td>
				<td>
					<fmt:formatDate value="${bisLog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="log:bisLog:edit"><td>
    				<a href="${ctx}/log/bisLog/form?id=${bisLog.id}">修改</a>
					<a href="${ctx}/log/bisLog/delete?id=${bisLog.id}" onclick="return confirmx('确认要删除该日志吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<%-- <div class="pagination">${page}</div> --%>
</body>
</html>