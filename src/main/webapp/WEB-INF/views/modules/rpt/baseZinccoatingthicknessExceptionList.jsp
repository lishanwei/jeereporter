<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>异常历史数据管理</title>
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
		<li class="active"><a href="${ctx}/rpt/baseZinccoatingthicknessException/">异常历史数据列表</a></li>
		<shiro:hasPermission name="rpt:baseZinccoatingthicknessException:edit"><li><a href="${ctx}/rpt/baseZinccoatingthicknessException/form">异常历史数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="baseZinccoatingthicknessException" action="${ctx}/rpt/baseZinccoatingthicknessException/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>记录时间：</label>
				<input name="beginLogtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${baseZinccoatingthicknessException.beginLogtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endLogtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${baseZinccoatingthicknessException.endLogtime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>班组：</label>
				<form:select path="loggroup" class="input-medium">
					<form:option value="" label=""/>
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
				<th>主键</th>
				<th>记录时间</th>
				<th>时间毫秒值</th>
				<th>班组</th>
				<th>生产编号</th>
				<th>生成编号</th>
				<th>测厚仪工作模式</th>
				<th>带宽</th>
				<th>带厚</th>
				<th>线速</th>
				<th>钢卷行走长度</th>
				<th>单侧目标上锌量</th>
				<th>正面目标上锌量</th>
				<th>反面目标上锌量</th>
				<th>正面检测位置</th>
				<th>反面检测位置</th>
				<th>正面上实时上锌量</th>
				<th>反面上实时上锌量</th>
				<th>接头信号: 0和1.</th>
				<th>正面目标上锌量最大值</th>
				<th>正面目标上锌量最小值</th>
				<th>正面上锌量偏差值</th>
				<th>反面目标上锌量最大值</th>
				<th>反面目标上锌量最小值</th>
				<th>反面上锌量偏差值</th>
				<shiro:hasPermission name="rpt:baseZinccoatingthicknessException:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="baseZinccoatingthicknessException">
			<tr>
				<td nowrap style="text-align:right;"><a href="${ctx}/rpt/baseZinccoatingthicknessException/form?id=${baseZinccoatingthicknessException.id}">
					${baseZinccoatingthicknessException.id}
				</a></td>
				<td nowrap style="text-align:center;">
					<fmt:formatDate value="${baseZinccoatingthicknessException.logtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.msvalue}
				</td>
				<td nowrap style="text-align:center;">
					${fns:getDictLabel(baseZinccoatingthicknessException.loggroup, 'RPT_WORK_TEAM', '')}
				</td>
				<td nowrap style="text-align:left;">
					${baseZinccoatingthicknessException.prodcode}
				</td>
				<td nowrap style="text-align:left;">
					${baseZinccoatingthicknessException.gencode}
				</td>
				<td nowrap style="text-align:center;">
					${baseZinccoatingthicknessException.workmode}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.bandwidth}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.bandthickness}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.linespeed}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.walklen}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.zincrateunilateral}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.zincratetargetfront}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.zincratetargetreverse}
				</td>
				<td nowrap style="text-align:left;">
					${baseZinccoatingthicknessException.detectionpositionfront}
				</td>
				<td nowrap style="text-align:left;">
					${baseZinccoatingthicknessException.detectionpositionreverse}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.zincratefront}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.zincratereverse}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.flag}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.zincratetargetfrontmaxval}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.zincratetargetfrontminval}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.zincratetargetfrontoffset}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.zincratetargetreversemaxval}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.zincratetargetreverseminval}
				</td>
				<td nowrap style="text-align:right;">
					${baseZinccoatingthicknessException.zincratetargetreverseoffset}
				</td>
				<shiro:hasPermission name="rpt:baseZinccoatingthicknessException:edit"><td nowrap style="text-align:center;">
    				<a href="${ctx}/rpt/baseZinccoatingthicknessException/form?id=${baseZinccoatingthicknessException.id}">修改</a>
					<a href="${ctx}/rpt/baseZinccoatingthicknessException/delete?id=${baseZinccoatingthicknessException.id}" onclick="return confirmx('确认要删除该异常历史数据吗？', this.href)">删除</a>
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