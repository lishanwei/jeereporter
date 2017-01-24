<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>锌层测厚月报表</title>
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
		<li class="active"><a href="${ctx}/rpt/zinccoatingMonthlyReport/">锌层测厚月报表列表</a></li>
		<li><a href="${ctx}/rpt/zinccoatingMonthlyReport/chartform">图表展示</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="zinccoatingWeeklyReport" action="${ctx}/rpt/zinccoatingMonthlyReport/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>记录时间：</label>
				<input name="beginLogtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${zinccoatingWeeklyReport.beginLogtime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/> - 
				<input name="endLogtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="${zinccoatingWeeklyReport.endLogtime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>班组：</label>
				<form:select path="loggroup" class="input-medium">
                    <form:option value="" label="--所有班组--"/>
                    <form:options items="${fns:getDictList('RPT_WORK_TEAM')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
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
				<th>记录时间</th>
				<th>班组</th>
				<th>成品件数</th>
				<th>钢卷总长(米)</th>
				<th>过钢量(吨）</th>
				<th>总上锌量（千克）</th>
				<shiro:hasPermission name="rpt:zinccoatingMonthlyReport:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="zinccoatingMonthlyReport">
			<tr>
				<td nowrap style="text-align:center;">
					<fmt:formatDate value="${zinccoatingMonthlyReport.logtime}" pattern="yyyy-MM-dd"/>
				</td>
				<td nowrap style="text-align:center;">
					${fns:getDictLabel(zinccoatingMonthlyReport.loggroup, 'RPT_WORK_TEAM', '')}
				</td>
				<td nowrap>
					${zinccoatingMonthlyReport.genqty}
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingMonthlyReport.totallen}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingMonthlyReport.totalsteel}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingMonthlyReport.zincratetotal}" pattern="0.0"/>
				</td>
				<shiro:hasPermission name="rpt:zinccoatingMonthlyReport:edit"><td nowrap>
    				<a href="${ctx}/rpt/zinccoatingMonthlyReport/form?id=${zinccoatingMonthlyReport.id}">修改</a>
					<a href="${ctx}/rpt/zinccoatingMonthlyReport/delete?id=${zinccoatingMonthlyReport.id}" onclick="return confirmx('确认要删除该锌层测试日志吗？', this.href)">删除</a>
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