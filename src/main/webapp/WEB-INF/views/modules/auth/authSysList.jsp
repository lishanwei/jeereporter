<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统管理</title>
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
		<li class="active"><a href="${ctx}/auth/authSys/">系统列表</a></li>
		<shiro:hasPermission name="auth:authSys:edit"><li><a href="${ctx}/auth/authSys/form">系统添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="authSys" action="${ctx}/auth/authSys/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>负责人：</label>
				<form:input path="master" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>系统编码：</label>
				<form:input path="code" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>负责人</th>
				<th>系统编码</th>
				<th>系统url</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="auth:authSys:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="authSys">
			<tr>
				<td><a href="${ctx}/auth/authSys/form?id=${authSys.id}">
					${authSys.name}
				</a></td>
				<td>
					${authSys.master}
				</td>
				<td>
					${authSys.code}
				</td>
				<td>
					${authSys.url}
				</td>
				<td>
					<fmt:formatDate value="${authSys.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${authSys.remarks}
				</td>
				<shiro:hasPermission name="auth:authSys:edit"><td>
    				<a href="${ctx}/auth/authSys/form?id=${authSys.id}">修改</a>
					<a href="${ctx}/auth/authSys/delete?id=${authSys.id}" onclick="return confirmx('确认要删除该系统吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>