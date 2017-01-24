<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>房型开关设置</title>
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
		<c:if test="${page_type == 'order' }">
			<li><a href="${ctx}/datacenter/orders/cslist">分销订单确认列表</a></li>
		</c:if>
		<c:if test="${page_type == 'hotel' }">
			<li><a href="${ctx}/hotel/hotel">酒店中心</a></li>
		</c:if>
		<c:if test="${page_type == 'order_query' }">
			<li><a href="${ctx}/datacenter/orders/list">分销订单列表</a></li>
		</c:if>
		<li class="active"><a href="${ctx}/hotel/hotelOpensetting?hotelid=${hotel.id}&page_type=${page_type}">房型开关列表</a></li>
		<shiro:hasPermission name="hotel:hotelOpensetting:edit"><li><a href="${ctx}/hotel/hotelOpensetting/form?hotelid=${hotel.id}&page_type=${page_type}">房型开关添加</a></li></shiro:hasPermission>
	</ul>
	<div class="container-fluid breadcrumb">
		<div class="row-fluid span12">
			<span class="span3">酒店编码: <b>${hotel.id}</b></span>
			<span class="span3">酒店名称:  <b>${hotel.hotelname}</b></span>
			<span class="span3"><%--OTA名称: <b>${distributorconfig.name}</b>--%></span>
			<span class="span3"></span>
		</div>
	</div>
	<%--<form:form id="searchForm" modelAttribute="hotelOpensetting" action="${ctx}/hotel/hotelOpensetting/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>--%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<%--<th>酒店id</th>--%>
				<th>房型</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<shiro:hasPermission name="hotel:hotelOpensetting:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hotelOpensetting">
			<tr>
				<%--<td>${hotelOpensetting.hotelid}</td>--%>
				<td>${map_roomtype.get(hotelOpensetting.roomtypeid)}</td>
				<td><fmt:formatDate value="${hotelOpensetting.begindate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${hotelOpensetting.enddate}" pattern="yyyy-MM-dd"/></td>
				<shiro:hasPermission name="hotel:hotelOpensetting:edit"><td>
    				<a href="${ctx}/hotel/hotelOpensetting/form?id=${hotelOpensetting.id}&hotelid=${hotel.id}&page_type=${page_type}">修改</a>
					<a href="${ctx}/hotel/hotelOpensetting/delete?id=${hotelOpensetting.id}&hotelid=${hotel.id}&page_type=${page_type}" onclick="return confirmx('确认要删除该房型开关吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>