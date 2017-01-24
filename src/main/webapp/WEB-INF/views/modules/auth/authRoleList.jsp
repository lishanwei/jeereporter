<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统角色管理</title>
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
		<li class="active"><a href="${ctx}/auth/authRole/">系统角色列表</a></li>
		<shiro:hasPermission name="auth:authRole:edit"><li><a href="${ctx}/auth/authRole/form">系统角色添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="authRole" action="${ctx}/auth/authRole/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>归属系统：</label>
				<sys:treeselect id="authsysId" name="authsysId" value="${authRole.authsysId}" labelName="" labelValue="${fns:getAuthSysById(authRole.authsysId).name}"
					title="部门" url="/auth/authSys/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>角色编码：</label>
				<form:input path="code" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>角色名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>归属系统</th>
				<th>角色编码</th>
				<th>角色名称</th>
				<th>英文名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="auth:authRole:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="authRole">
			<tr>
				<td><a href="${ctx}/auth/authRole/form?id=${authRole.id}">
				    ${fns:getAuthSysById(authRole.authsysId).name}
				</a></td>
				<td>
					${authRole.code}
				</td>
				<td>
					${authRole.name}
				</td>
				<td>
					${authRole.enname}
				</td>
				<td>
					<fmt:formatDate value="${authRole.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${authRole.remarks}
				</td>
				<shiro:hasPermission name="auth:authRole:edit"><td>
					<a href="${ctx}/auth/authRole/assign?id=${authRole.id}" >分配用户</a>
    				<a href="${ctx}/auth/authRole/form?id=${authRole.id}">修改</a>
					<a href="${ctx}/auth/authRole/delete?id=${authRole.id}" onclick="return confirmx('确认要删除该系统角色吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>