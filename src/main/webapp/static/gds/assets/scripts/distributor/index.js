var pageSize = 10;
$(function() {
	App.init();
	if (jQuery().datepicker) {
		$('.date-picker').datepicker({
			rtl : App.isRTL(),
			autoclose : true
		});
	}
	//初始化分销商查询条件
	var onOk = function(data,status,xhr){
    	var otatypes = data.result;
    	$.each(otatypes, function (n, value) { 
    		$("#otatype").append('<option value='+value.otatype+'>'+value.name+'</option>');
    		$("#loginaccount").append('<option value='+value.loginaccount+'>'+value.loginaccount+'</option>');
        });
    }
    AjaxEx("json","POST",bathpath + "distributor/gdstypeinfo",null,onOk,null,true,false);
	isearch();
});
function isearch(pageNo) {
	var onOk = function(data, status, xhr) {
		// 分页
		pagerEx('pagediv', pageNo, data.page.totalPage, isearch);
		var html = template("listTemplate", data);
		$("#datatable").html(html);
	}
	var onErr = function(data, status, xhr) {
		layer.msg('查询失败', {
			icon : 2
		});
	}
	var otatype = $("#otatype").val();
	var loginaccount = $("#loginaccount").val();
	var contactor = $("#contactor").val();
	var contactphone = $("#contactphone").val();
	var begintime_from = $("#begintime_from").val();
	var begintime_end = $("#begintime_end").val();
	var endtime_from = $("#endtime_from").val();
	var endtime_end = $("#endtime_end").val();
	/*var status = $("#status").val();*/
	var query = {
		'pageNo' : pageNo,
		'pageSize' : pageSize,
		'otatype' : otatype,
		'loginaccount' : loginaccount,
		'contactor' : contactor,
		'contactphone' : contactphone,
		'begintime_from' : begintime_from,
		'begintime_end' : begintime_end,
		'endtime_from' : endtime_from,
		'endtime_end' : endtime_end
		/*'status':status*/
	};
	AjaxEx("json", "POST", bathpath + "distributor/list", query, onOk, onErr,
			false, true);
}
function batchDelete() {
	var ids = "";
	$("#databody :checkbox").each(function() {
		if ($(this).prop("checked")) {
			ids += $(this).attr("value") + ",";
		}
	});
	var loginnames = "";
	$("#databody :checkbox").each(function() {
		if ($(this).prop("checked")) {
			loginnames += $(this).attr("account") + ",";
		}
	});
	if (ids.length > 0) {
		layer.confirm("确定删除吗？", function() {
			ids = ids.substring(0, ids.length - 1);
			loginnames = loginnames.substring(0, loginnames.length - 1);
			var onOk = function(data, status, xhr) {
				if(data.result==1){
					layer.msg("删除成功!", {
						icon : 1,time: 1000
					}, function() {
						isearch();
					});
				}else{
					layer.msg('删除失败,统一配置删除用户有问题', {
						icon : 2
					});
				}
			}
			var onErr = function(data, status, xhr) {
				layer.msg('删除失败', {
					icon : 2
				});
			}
			AjaxEx("json", "POST", bathpath + "distributor/batchdelete", {
				'ids' : ids,'loginnames':loginnames
			}, onOk, onErr, false,true);
		})
	} else {
		layer.msg("请选择需要删除的记录", {
			icon : 2
		});
	}
}
function del(id,loginname) {
	layer.confirm("确定删除吗？", function() {
		var onOk = function(data, status, xhr) {
			if(data.result==1){
				layer.msg("删除成功!", {
					icon : 1,time: 1000
				}, function() {
					isearch();
				});
			}else{
				layer.msg('删除失败,统一配置删除用户有问题', {
					icon : 2
				});
			}
		}
		var onErr = function(data, status, xhr) {
			layer.msg('删除失败', {
				icon : 2
			});
		}
		AjaxEx("json", "POST", bathpath + "distributor/delete", {
			'id' : id,'loginname':loginname
		}, onOk, onErr, false,true);
	})
}
function add() {
	window.location.href = bathpath + "distributor/list/edit";
}
function edit(id) {
	window.location.href = bathpath + "distributor/list/edit?id=" + id;
}
function relation(otatype,otatypename) {
	window.location.href = bathpath + "distributor/list/hotelrelation?otatype=" + otatype +"&otatypename="+otatypename;
}
function recharge(otatype) {
	window.location.href = bathpath + "recharge/cash?otatype=" + otatype;
}

function checkAccType(otatype) {
		var onOk = function(data, status, xhr) {
			if(data.result==2){
				layer.msg("该用户为后付费账户，不能充值!", {
					icon : 1,time: 1000
				});
				return;
			}else{
				window.location.href = bathpath + "recharge/cash?otatype=" + otatype;
			}
		}
		var onErr = function(data, status, xhr) {
			layer.msg('账户充值验证失败', {
				icon : 2
			});
		}
		AjaxEx("json", "POST", bathpath + "distributor/rechargecheck", {
			'otatype' : otatype
		}, onOk, onErr, false,true);
}