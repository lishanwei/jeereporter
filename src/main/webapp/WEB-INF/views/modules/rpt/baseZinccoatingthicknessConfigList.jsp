<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上锌量配置管理</title>
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
		<li class="active"><a href="${ctx}/rpt/baseZinccoatingthicknessConfig/">上锌量配置列表</a></li>
		<shiro:hasPermission name="rpt:baseZinccoatingthicknessConfig:edit"><li><a href="${ctx}/rpt/baseZinccoatingthicknessConfig/form">上锌量配置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="baseZinccoatingthicknessConfig" action="${ctx}/rpt/baseZinccoatingthicknessConfig/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>配置项名称：</label>
				<form:input path="configname" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>配置项名称</th>
				<th>正面目标上锌量</th>
				<th>正面目标上锌量最大值</th>
				<th>正面目标上锌量最小值</th>
				<th>反面目标上锌量</th>
				<th>反面目标上锌量最大值</th>
				<th>反面目标上锌量最小值</th>
				<th>创建时间</th>
				<th>创建人</th>
				<th>修改时间</th>
				<th>修改人</th>
				<shiro:hasPermission name="rpt:baseZinccoatingthicknessConfig:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="baseZinccoatingthicknessConfig">
			<tr>
				<td nowrap style="text-align:left;"><a href="${ctx}/rpt/baseZinccoatingthicknessConfig/form?id=${baseZinccoatingthicknessConfig.id}">
					${baseZinccoatingthicknessConfig.configname}
				</a></td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessConfig.zincratetargetfront}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessConfig.zincratetargetfrontmaxval}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessConfig.zincratetargetfrontminval}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessConfig.zincratetargetreverse}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessConfig.zincratetargetreversemaxval}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessConfig.zincratetargetreverseminval}
				</td>
				<td nowrap style="text-align:center;">
					<fmt:formatDate value="${baseZinccoatingthicknessConfig.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td nowrap style="text-align:left;">
					${baseZinccoatingthicknessConfig.createuser}
				</td>
				<td nowrap style="text-align:center;">
					<fmt:formatDate value="${baseZinccoatingthicknessConfig.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td nowrap style="text-align:left;">
					${baseZinccoatingthicknessConfig.updateuser}
				</td>
				<shiro:hasPermission name="rpt:baseZinccoatingthicknessConfig:edit"><td nowrap style="text-align:center;">
    				<a href="${ctx}/rpt/baseZinccoatingthicknessConfig/form?id=${baseZinccoatingthicknessConfig.id}">修改</a>
					<a href="${ctx}/rpt/baseZinccoatingthicknessConfig/delete?id=${baseZinccoatingthicknessConfig.id}" onclick="return confirmx('确认要删除该上锌量配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		<c:if test="${page.list.size() == 0 }">
          <tr>
            <td colspan="100%" style="word-wrap:break-word;word-break:break-all;">
                未查找到符合条件的数据.
            </td>
          </tr>
        </c:if>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>