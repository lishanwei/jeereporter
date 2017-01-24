<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#btnImport").click(function(){
                $.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
            });
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
    <div id="importBox" class="hide">
        <form id="importForm" action="${ctx}/auth/authUser/import" method="post" enctype="multipart/form-data"
              class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
            <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
            <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
            <a href="${ctx}/auth/authUser/import/template">下载模板</a>
        </form>
    </div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/auth/authUser/">系统用户列表</a></li>
		<shiro:hasPermission name="auth:authUser:edit"><li><a href="${ctx}/auth/authUser/form">系统用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="authUser" action="${ctx}/auth/authUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="username" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
                <input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th>来源</th>
				<th>更新时间</th>
                <th>状态</th>
				<th>备注</th>
				<shiro:hasPermission name="auth:authUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="authUser">
			<tr>
				<td><a href="${ctx}/auth/authUser/form?id=${authUser.id}">
					${authUser.username}
				</a></td>
				<td>
					${authUser.source}
				</td>
				<td>
					<fmt:formatDate value="${authUser.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
                <td>
                    ${fns:getDictLabel(authUser.delFlag, 'del_flag', '0')}
                    <%--${authUser.delFlag}--%>
                </td>
				<td>
					${authUser.remarks}
				</td>
				<shiro:hasPermission name="auth:authUser:edit"><td>
                    <c:if test="${authUser.delFlag == 0}">
    				<a href="${ctx}/auth/authUser/form?id=${authUser.id}">修改</a>
					<a href="${ctx}/auth/authUser/delete?id=${authUser.id}" onclick="return confirmx('确认要删除该系统用户吗？', this.href)">删除</a>
                    <a href="${ctx}/auth/authUser/resetPass?id=${authUser.id}">重置密码</a>
                        <a href="${ctx}/auth/authUser/syncUser?id=${authUser.id}">同步账号</a>
                    </c:if>
                    <c:if test="${authUser.delFlag == 1}">
                        <a href="${ctx}/auth/authUser/resetUser?id=${authUser.id}" onclick="return confirmx('确认要重启该系统用户吗？', this.href)">重启</a>
                    </c:if>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>