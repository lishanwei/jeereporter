<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分配角色</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/auth/authRole/">系统角色列表</a></li>
		<shiro:hasPermission name="auth:authRole:edit">
			<li class="active"><a href="${ctx}/auth/authRole/assign">系统角色分配</a></li>
		</shiro:hasPermission>
	</ul>
	<div class="container-fluid breadcrumb">
		<div class="row-fluid span12">
			<span class="span2">归属系统: <b>${authSys.name}</b></span>
            <span class="span2">角色编码: <b>${authRole.code}</b></span>
			<span class="span2">角色名称: <b>${authRole.name}</b></span>
			<span class="span2">英文名称: <b>${authRole.enname}</b></span>
		</div>
	</div>
	<sys:message content="${message}"/>
	<div class="breadcrumb">
		<form id="assignRoleForm" action="${ctx}/auth/authRole/assignrole" method="post" class="hide">
			<input type="hidden" name="id" value="${authRole.id}"/>
			<input id="ids" type="hidden" name="ids" value=""/>
            <input id="preIds" type="hidden" name="preIds" value=""/>
		</form>
		<input id="assignButton" class="btn btn-primary" type="submit" value="分配用户"/>
		<script type="text/javascript">
			$("#assignButton").click(function(){
				top.$.jBox.open("iframe:${ctx}/auth/authRole/usertorole?id=${authRole.id}", "分配角色",810,$(top.document).height()-240,{
					buttons:{"确定分配":"ok", "清除已选":"clear", "关闭":true}, bottomText:"通过选择人员分配角色。",submit:function(v, h, f){
						var pre_ids = h.find("iframe")[0].contentWindow.pre_ids;
						var ids = h.find("iframe")[0].contentWindow.ids;
						//nodes = selectedTree.getSelectedNodes();
						if (v=="ok"){
							// 删除''的元素
							if(ids[0]==''){
								ids.shift();
								pre_ids.shift();
							}
							if(pre_ids.sort().toString() == ids.sort().toString()){
								top.$.jBox.tip("未给角色【${role.name}】分配新成员！", 'info');
								return false;
							};

					    	// 执行保存
					    	loading('正在提交，请稍等...');
					    	$('#ids').val(ids);
                            $('#preIds').val(pre_ids);
					    	$('#assignRoleForm').submit();
					    	return true;
						} else if (v=="clear"){
							h.find("iframe")[0].contentWindow.clearAssign();
							return false;
		                }
					}, loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
			});
		</script>
	</div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
				<th width="200">用户名</th>
				<th width="200">更新时间</th>
				<th>备注</th>
			<shiro:hasPermission name="auth:authRole:edit"><th>操作</th></shiro:hasPermission></tr>
		</thead>
		<tbody>
		<c:forEach items="${userList}" var="user">
			<tr>
				<td>		${user.username }</td>				
				<td><fmt:formatDate value="${user.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>	${user.remarks}	</td>
				<shiro:hasPermission name="auth:authRole:edit"><td>
					<a href="${ctx}/auth/authRole/outrole?userid=${user.id}&roleid=${authRole.id}" 
						onclick="return confirmx('确认要将用户<b>[${user.username}]</b>从<b>[${authRole.name}]</b>角色中移除吗？', this.href)">移除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
