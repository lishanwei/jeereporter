<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>锌层测厚日志管理</title>
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
		<li class="active"><a href="${ctx}/rpt/zinccoatingthicknessLog/list">锌层测厚日志列表</a></li>
		<shiro:hasPermission name="rpt:zinccoatingthicknessLog:edit"><li><a href="${ctx}/rpt/zinccoatingthicknessLog/form">锌层测试日志添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="zinccoatingthicknessLog" action="${ctx}/rpt/zinccoatingthicknessLog/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>记录时间：</label>
				<input name="beginLogtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${zinccoatingthicknessLog.beginLogtime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/> - 
				<input name="endLogtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${zinccoatingthicknessLog.endLogtime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});"/>
			</li>
			<li><label>班组：</label>
				<form:select path="loggroup" class="input-medium">
                    <form:option value="" label="--所有班组--"/>
                    <form:options items="${fns:getDictList('RPT_WORK_TEAM')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</li>
			<li><label>生产编号：</label>
				<form:input path="prodcode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>生成编号：</label>
				<form:input path="gencode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<c:choose>
	  <c:when test="${principal=='1'}">
	<div id="messageBox" class="alert alert-info"><button data-dismiss="alert" class="close">×</button>${taketimes}</div>
	  </c:when>
	  <c:otherwise>
	<sys:message content="${message}"/>
	  </c:otherwise>
	</c:choose>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>主键</th>
				<th>记录时间</th>
				<th>班组</th>
				<th>生产编号</th>
				<th>生成编号</th>
				<th>测厚仪工作模式</th>
				<th>带宽</th>
				<th>带厚</th>
				<th>线速</th>
				<th>钢卷行走长度</th>
				<th>单侧目标上锌量</th>
				<th>正面检测位置</th>
				<th>反面检测位置</th>
				<th>正面上实时上锌量</th>
				<th>反面上实时上锌量</th>
				<shiro:hasPermission name="rpt:zinccoatingthicknessLog:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="zinccoatingthicknessLog">
			<tr>
				<td nowrap><a href="${ctx}/rpt/zinccoatingthicknessLog/form?id=${zinccoatingthicknessLog.id}">
					${zinccoatingthicknessLog.id}
				</a></td>
				<td nowrap style="text-align:center;">
					<%-- <fmt:formatDate value="${zinccoatingthicknessLog.logtime}" pattern="yyyy-MM-dd HH:mm:ss S"/> --%>
					${zinccoatingthicknessLog.logtime}
				</td>
				<td nowrap style="text-align:center;">
					${fns:getDictLabel(zinccoatingthicknessLog.loggroup, 'RPT_WORK_TEAM', '')}
				</td>
				<td nowrap>
					${zinccoatingthicknessLog.prodcode}
				</td>
				<td nowrap>
					${zinccoatingthicknessLog.gencode}
				</td>
				<td nowrap style="text-align:center;">
					${fns:getDictLabel(zinccoatingthicknessLog.workmode, 'RPT_WORK_MODE', '')}
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingthicknessLog.bandwidth}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingthicknessLog.bandthickness}" pattern="0.00"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingthicknessLog.linespeed}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingthicknessLog.walklen}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingthicknessLog.zincrateunilateral}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingthicknessLog.detectionpositionfront}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingthicknessLog.detectionpositionreverse}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingthicknessLog.zincratefront}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingthicknessLog.zincratereverse}" pattern="0.0"/>
				</td>
				<shiro:hasPermission name="rpt:zinccoatingthicknessLog:edit"><td nowrap>
    				<a href="${ctx}/rpt/zinccoatingthicknessLog/form?id=${zinccoatingthicknessLog.id}">修改</a>
					<a href="${ctx}/rpt/zinccoatingthicknessLog/delete?id=${zinccoatingthicknessLog.id}" onclick="return confirmx('确认要删除该锌层测试日志吗？', this.href)">删除</a>
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