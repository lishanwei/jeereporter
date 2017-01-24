<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>锌层测厚班组报表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnDownloadReport").click(function(){
				top.$.jBox.confirm("确认要导出报表数据吗？","系统提示",function(v,h,f){
					if (v=="ok") {
						$("#searchForm").attr("action","${ctx}/rpt/zinccoatingWorkTeamReport/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/rpt/zinccoatingWorkTeamReport/list");
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/rpt/zinccoatingWorkTeamReport/">锌层测厚班报表列表</a></li>
		<li><a href="${ctx}/rpt/zinccoatingWorkTeamReport/chartform">图表展示</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="zinccoatingWorkTeamReport" action="${ctx}/rpt/zinccoatingWorkTeamReport/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>记录时间：</label>
				<input name="beginLogtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${zinccoatingWorkTeamReport.beginLogtime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true});"/> - 
				<input name="endLogtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${zinccoatingWorkTeamReport.endLogtime}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true});"/>
			</li>
			<li><label>班组：</label>
				<form:select path="loggroup" class="input-medium">
                    <form:option value="" label="--所有班组--"/>
                    <form:options items="${fns:getDictList('RPT_WORK_TEAM')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/></li>
			<!-- <li class="btns"><input id="btnExport2Excel" class="btn btn-primary" type="button" value="导出Excel"/></li> -->
			<!-- <li class="btns"><input id="btnViewReport" class="btn btn-primary" type="button" value="查看报表"/></li> -->
			<li class="btns"><input id="btnDownloadReport" class="btn btn-primary" type="button" value="下载报表"/></li>
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
				<th>生产编号</th>
				<th>生成编号</th>
				<th>测厚仪工作模式</th>
				<th>带宽</th>
				<th>带厚</th>
				<th>平均线速</th>
				<th>钢卷总长</th>
				<th>过钢量(吨）</th>
				<th>正面目标上锌量(克)</th>
				<th>反面目标上锌量(克)</th>
				<th>正面平米上锌量平均值（克）</th>
				<th>反面平米上锌量平均值（克）</th>
				<th>上锌总增重（千克）</th>
				<th>正面上锌增重（千克）</th>
				<th>反面上锌增重（千克）</th>
				<shiro:hasPermission name="rpt:zinccoatingWorkTeamReport:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="zinccoatingWorkTeamReport">
			<tr>
				<td nowrap style="text-align:center;">
					<fmt:formatDate value="${zinccoatingWorkTeamReport.logtime}" pattern="yyyy-MM-dd HH:mm"/>
				</td>
				<td nowrap style="text-align:center;">
					${fns:getDictLabel(zinccoatingWorkTeamReport.loggroup, 'RPT_WORK_TEAM', '')}
				</td>
				<td nowrap>
					${zinccoatingWorkTeamReport.prodcode}
				</td>
				<td nowrap>
					${zinccoatingWorkTeamReport.gencode}
				</td>
				<td nowrap style="text-align:center;">
					${fns:getDictLabel(zinccoatingWorkTeamReport.workmode, 'RPT_WORK_MODE', '')}
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingWorkTeamReport.bandwidth}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingWorkTeamReport.bandthickness}" pattern="0.00"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingWorkTeamReport.linespeed}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingWorkTeamReport.totallen}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingWorkTeamReport.totalsteel}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingWorkTeamReport.zincratetargetfront}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingWorkTeamReport.zincratetargetreverse}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingWorkTeamReport.zincrateavgfront}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingWorkTeamReport.zincrateavgreverse}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingWorkTeamReport.zinctotalinc}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingWorkTeamReport.zinctotalincfront}" pattern="0.0"/>
				</td>
				<td nowrap style="text-align:right;">
					<fmt:formatNumber value="${zinccoatingWorkTeamReport.zinctotalincreverse}" pattern="0.0"/>
				</td>
				<shiro:hasPermission name="rpt:zinccoatingWorkTeamReport:edit"><td nowrap>
    				<a href="${ctx}/rpt/zinccoatingWorkTeamReport/form?id=${zinccoatingWorkTeamReport.id}">修改</a>
					<a href="${ctx}/rpt/zinccoatingWorkTeamReport/delete?id=${zinccoatingWorkTeamReport.id}" onclick="return confirmx('确认要删除该锌层测试日志吗？', this.href)">删除</a>
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