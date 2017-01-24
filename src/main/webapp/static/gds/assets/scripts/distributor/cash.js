var otatype;
$(function() {
	//App.init();
	otatype = $.query.get("otatype");
});

function save(){
	var onOk = function(data, status, xhr) {
		if(data.result=='success'){
			layer.msg("保存成功!", {
				icon : 1,time: 1000
			}, function() {
				window.location.href = bathpath + "distributor/list";
			});
		}
		if(data.result=='fail'){
			layer.msg("保存失败!", {
				icon : 1,time: 1000
			});
		}
	}
	var onErr = function(data, status, xhr) {
		layer.msg('保存失败', {
			icon : 2,time: 1000
		});
	}
	var cash = $("#cash").val();
	if(!cash){
		layer.msg('请填写充值金额', {
			icon : 2,time: 1000
		});
		return;
	}
	//var otatype = $("#otatype").val();

	var param = {
		'otatype' : otatype,
		'cash' : cash
	};
	AjaxEx("json", "POST", bathpath + "recharge/save", param, onOk, null,false, true);
}
function cancel(){
	window.location.href = bathpath + "distributor/list";
}

function checkNum(){
	var obj = $("#cash").val();
	if(!obj){
		//return true;
		$("#cash").css("border","1px solid #00EE00");
	}
	var re = /^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/;
	if (!re.test(obj)) {
		//return false;
		layer.msg('请填写充值金额', {
			icon : 2
		});
		$("#cash").css("border","1px solid #FF0000");
		return;
	} else {
		//return true;
		$("#cash").css("border","1px solid #00EE00");
	}
}