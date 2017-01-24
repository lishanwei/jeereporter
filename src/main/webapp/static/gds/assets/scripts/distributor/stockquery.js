var pageSize = 10;
var hotelid;
var otatype;
$(function() {
	App.init();
	// 初始化时间选择器
	$(".form_datetime").datetimepicker({
		autoclose : true,
		isRTL : App.isRTL(),
		format : "yyyy-mm-dd hh:ii",
		pickerPosition : (App.isRTL() ? "bottom-right" : "bottom-left")
	});
	otatype = $.query.get("otatype");
	hotelid = $.query.get("hotelid");
	if (hotelid) {
		//search();
	}
	showweek(currentweek());
	/*$('input:radio[name="optionsRadios"]').change(function() {
		alert($('input:radio[name="optionsRadios"]:checked').attr("ref"));
	});*/

});

function showweek(week){
	$("#weektable").find("span.title").html(week[0]);
	$("#weektable").find("span.title1").each(function(i,value) {
		$(this).html(week[i]);
	});
	var onOk = function(data, status, xhr) {
		$("#otaname").html(data.otaname);
		$("#hotelname").html(data.hotelname);
		// 分页
		var html = template("listTemplate1", data);
		$("#xxtable").html(html);
	}
	var onErr = function(data, status, xhr) {
		layer.msg('获取数据失败', {
			icon : 2
		});
	}

	var query = {
		'hotelid' : hotelid,
		'otatype' : otatype,
		'days':week
	};
	AjaxEx("json", "POST", bathpath + "stockquery/query", query, onOk, onErr,
			false, true);
}
