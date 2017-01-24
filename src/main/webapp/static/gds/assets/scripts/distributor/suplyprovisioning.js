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
	AjaxEx("json", "POST", bathpath + "suply/priceinfos", query, onOk, onErr,
			false, true);
}

function search(pageNo) {
	var onOk = function(data, status, xhr) {
		// 分页
		pagerEx('pagediv', pageNo, data.page.totalPage, search);
		var html = template("listTemplate", data);
		$("#datatable").html(html);
	}
	var onErr = function(data, status, xhr) {
		layer.msg('获取数据失败', {
			icon : 2
		});
	}

	var query = {
		'pageNo' : pageNo,
		'pageSize' : pageSize,
		'hotelid' : hotelid,
		'otatype' : otatype
	};
	AjaxEx("json", "POST", bathpath + "suply/provisioning", query, onOk, onErr,
			false, true);
}
function update(pageNo) {

	var onOk = function(data, status, xhr) {

		if (data == true) {
			layer.msg("更新成功", {
				icon : 1,time: 1000
			}, function() {
				search();
			});
		} else {
			layer.msg('更新失败', {
				icon : 2
			});
		}

	}
	var onErr = function(data, status, xhr) {
		layer.msg('更新失败', {
			icon : 2
		});
	}
	var para = "[";
	var check = true;
	$("#datatable").find("tr").each(function() {
		var tdArr = $(this).children();
		var otaroomtypeid = tdArr.eq(0).find("input").attr("value");
		var roomnum = tdArr.eq(2).find("input").val();
		var hotelprice = tdArr.eq(3).find("input").val();
		var saleprice = tdArr.eq(4).find("input").val();
		var totalnum = tdArr.eq(5).find("input").val();

		if(!roomnum){
			layer.msg('请输入房间数', {
				icon : 2
			});
			check = false;
			return;
		}
		/*if(!hotelprice){
			layer.msg('请输入结算价', {
				icon : 2
			});
			check = false;
			return;
		}*/
		if(!saleprice){
			layer.msg('请输入销售价', {
				icon : 2
			});
			check = false;
			return;
		}
		if (!checkNum(roomnum)) {
			layer.msg('房间数请输入数字', {
				icon : 2
			});
			check = false;
			return;
		}
		
		if(parseInt(roomnum)>parseInt(totalnum)){
			layer.msg('房间数超过总房间数，请重新设置。', {
				icon : 2
			});
			check = false;
			return;
		}

		/*if (!checkNum(hotelprice)) {
			layer.msg('结算价请输入数字', {
				icon : 2
			});
			check = false;
			return;
		}*/

		if (!checkNum(saleprice)) {
			layer.msg('销售价请输入数字', {
				icon : 2
			});
			check = false;
			return;
		}

		para = para + "{otaroomtypeid:" + otaroomtypeid;
		para = para + ",roomnum:" + roomnum;
		para = para + ",hotelprice:" + hotelprice;
		para = para + ",hotelid:" + hotelid;
		para = para + ",saleprice:" + saleprice + "},";

	});

	if (check) {
		para = para.substr(0, para.length - 1) + "]";
		var query = {
			'pageNo' : pageNo,
			'pageSize' : pageSize,
			'otatype' : otatype,
			'suplyBeans' : para
		};
		AjaxEx("json", "POST", bathpath + "suply/update", query, onOk, onErr,
				false, true);
	}

}


function configUpperPrice(){
	var onOk = function(data, status, xhr) {
		$("#configform").find("#otahotelid").val(data.id);
		$("#configform").find("#upper").val(data.upper);
		$("#configform").find("#upprice").val(data.upprice);
	}
	
	var onErr = function(data, status, xhr) {
		layer.msg('获取数据失败', {
			icon : 2
		});
	}

	var query = {
		'hotelid' : hotelid,
		'otatype' : otatype
	};
	AjaxEx("json", "POST", bathpath + "otahotel/queryotahotel", query, onOk, onErr,
			false, true);
}
function saveConfigUpperPrice(){
	var onOk = function(data, status, xhr) {
		layer.msg("保存成功", {
			icon : 1,time: 1000
		}, function() {
			$('#relationmodal').modal('hide');
			showweek(currentweek());
		});
	}
	var onErr = function(data, status, xhr) {
		layer.msg('获取数据失败', {
			icon : 2
		});
	}
	var otahotelid = $("#configform").find("#otahotelid").val();
	var upper = $("#configform").find("#upper").val();
	var upprice = $("#configform").find("#upprice").val();
	if (!checkNum(upprice)) {
		layer.msg('上浮金额请输入数字', {
			icon : 2
		});
		return;
	}
	if (!checkNum(upper)) {
		layer.msg('上浮比例请输入数字', {
			icon : 2
		});
		return;
	}

	var query = {
		'otahotelid' : otahotelid,
		'upper' : upper,
		'upprice' : upprice
	};
	AjaxEx("json", "POST", bathpath + "otahotel/saveConfigUpperPrice", query, onOk, onErr,
			false, true);
}
function saveSaleRoomNum(otaroomtypeid,roomnum,totalnum){
	var onOk = function(data, status, xhr) {
		layer.msg("保存成功", {
			icon : 1,time: 1000
		}, function() {
			showweek(currentweek());
		});
	}
	var onErr = function(data, status, xhr) {
		layer.msg('保存失败', {
			icon : 2
		});
	}
	if (!roomnum) {
		layer.msg('请输入分配房间数', {
			icon : 2
		});
		return;
	}
	if (!checkNum(roomnum)) {
		layer.msg('分配房间数请输入数字', {
			icon : 2
		});
		return;
	}
	if(parseInt(roomnum)>parseInt(totalnum)){
		layer.msg('房间数超过总房间数，请重新设置。', {
			icon : 2
		});
		return;
	}
	var query = {
			'otaroomtypeid' : otaroomtypeid,
			'roomnum' : roomnum
	};
	AjaxEx("json", "POST", bathpath + "suply/saveSaleRoomNum", query, onOk, onErr,
			false, true);
}
function saveChannelPrice(roomtypeid,day,saleprice){
	var onOk = function(data, status, xhr) {
		layer.msg("保存成功", {
			icon : 1,time: 1000
		}, function() {
			showweek(currentweek());
		});
	}
	var onErr = function(data, status, xhr) {
		layer.msg('保存失败', {
			icon : 2
		});
	}
	if (!checkNum(saleprice)) {
		layer.msg('渠道价请输入数字', {
			icon : 2
		});
		return;
	}
	if(!day){
		layer.msg('此房型还未设置分销价,不能设置渠道价', {
			icon : 2
		});
		return;
	}
	var query = {
			'hotelid' : hotelid,
			'otatype' : otatype,
			'roomtypeid' : roomtypeid,
			'day' : day,
			'saleprice' : saleprice
	};
	AjaxEx("json", "POST", bathpath + "suply/saveChannelPrice", query, onOk, onErr,
			false, true);
}
