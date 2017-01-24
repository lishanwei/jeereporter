$(function() {
	//App.init();
	
	if (jQuery().datepicker) {
		$('.date-picker').datepicker({
			//rtl : App.isRTL(),
			autoclose : true
		});
	}
});

function toSearch() {
	document.getElementById('hotellist').submit();
	
}
function doNewSearch() {
	$("#totalItem").val(0);
	$("#index").val(1);
	toSearch();
}
function confirmorder(divid,orderid){
	var onOk = function(data, status, xhr) {
		var fresult = data.result;
		if(fresult=='SUCCESS'){
			layer.msg("确认订单成功!", {
				icon : 1
			});
			window.location.href =bathpath + "order/list";
		}else{
			layer.msg(fresult, {
				icon : 2
			});
		}
	}
	var onErr = function(data, status, xhr) {
		layer.msg('确认订单失败', {
			icon : 2
		});
	}
	var loginuser = $("#loginusername").val();
	var param = {
			'orderid' : orderid,
			'loginuser':loginuser
			};
	AjaxEx("json", "POST", bathpath +"order/confirmOrder", param, onOk, onErr,
			false, true);
}
function refuseorder(divid,orderid){
	var onOk = function(data, status, xhr) {
		var fresult = data.result;
		if(fresult=='SUCCESS'){
			layer.msg("拒绝订单成功!", {
				icon : 1
			});
			window.location.href =bathpath + "order/list";
		}else{
			layer.msg(fresult, {
				icon : 2
			});
		}
	}
	var onErr = function(data, status, xhr) {
		layer.msg('拒绝订单失败', {
			icon : 2
		});
	}
	var loginuser = $("#loginusername").val();
	var param = {
			'orderid' : orderid,
			'loginuser':loginuser
			};
	AjaxEx("json", "POST", bathpath +"order/refuseOrder", param, onOk, onErr,
			false, true);
}
function showorderdetail(divid,orderid){
	var para="orderid="+orderid;
	var onOk = function(data, status, xhr) {
		$("#fav_content").html(data);
	}
	
	AjaxEx("html", "POST",bathpath + "order/detail", para, onOk, null,
			false, true);
	$('#'+divid).modal('show');
}
function fillPage(value) {
	$("#index").val(value);
	$("#totalItem").val($("#totalPageItem").val());
	toSearch();
}