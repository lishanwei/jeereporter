var pageSize = 20;
var otatype;
//var otatypename;
$(function() {
	App.init();
	//初始化时间选择器
	$(".form_datetime").datetimepicker({
        autoclose: true,
        format: "yyyy-mm-dd hh:ii",
        pickerPosition: (App.isRTL() ? "bottom-right" : "bottom-left")
    });
    
    otatype = $.query.get("otatype");
    //otatypename = $.query.get("otatypename");
    
	if(otatype){
		//查看
		search();
	}
});
function search(pageNo){
	var hotelid=$("#hotelid").val();
	var hotelname = $("#hotelname").val();
	var onOk = function(data,status,xhr){
		$("#otatypename").html(data.otatypename);
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
	if (!checkNum(hotelid)) {
		layer.msg('酒店id请输入数字', {
			icon : 2
		});
		return;
	}
	var query = {
			'pageNo' : pageNo,
			'pageSize' : pageSize,
			'otatype' : otatype,
			'hotelid' : hotelid,
			'hotelname' : hotelname
		};
    AjaxEx("json","POST",bathpath + "otahotel/queryotahotels",query,onOk,onErr,false,true);
}

function provisioning(hotelid){
	window.location.href = bathpath + "distributor/list/suply?hotelid="+hotelid+"&otatype="+otatype;
}
function stockquery(hotelid){
	window.location.href = bathpath + "distributor/list/stockquery?hotelid="+hotelid+"&otatype="+otatype;
}
function searchChooseHotels(pageNo){
	$(".btn_addrelation").removeAttr("disabled");
	
	var onOk = function(data,status,xhr){
		if(data.state==0){
			// 分页
			pagerEx('chooseHotelpagediv', pageNo, data.rs.page.totalPage, searchChooseHotels);
			var html = template("chooseHotelTemplate", data.rs);
			$("#chooseHotelsDatatable").html(html);
		}else if(data.state==1){
			$("#chooseHotelsDatatable").empty();
			layer.msg(data.msg, {
				icon : 2
			});
		}
		
	}
	var onErr = function(data, status, xhr) {
		layer.msg('获取数据失败', {
			icon : 2
		});
	}
	var hotelname = $("#queryform").find("#q_hotelname").val();
	var provcode = $("#queryform").find("#q_provcode").val();
	var citycode = $("#queryform").find("#q_citycode").val();
	var hoteltype = $("#queryform").find("#q_hoteltype").val();
	var busiarea = new Array();
	$.each($("input[name='q_hoteltype']:checked"), function() {
		busiarea.push($(this).val());
	});
	var feature = new Array();
	$.each($("input[name='q_feature']:checked"), function() {
		feature.push($(this).val());
	});
	var facilities = new Array();
	$.each($("input[name='q_facilities']:checked"), function() {
		facilities.push($(this).val());
	});
	var other = new Array();
	$.each($("input[name='q_other']:checked"), function() {
		other.push($(this).val());
	});


	var query = {
		'pageNo': pageNo,
		'pageSize': pageSize,
		'otatype': otatype,
		'hotelname': hotelname,
		'provcode': provcode,
		'citycode': citycode,
		'hoteltype': hoteltype,
		'busiarea': busiarea,
		'feature': feature,
		'facilities': facilities,
		'other': other
	};
	AjaxEx("json","POST",bathpath + "hotel/queryhotelsByOtatype",query,onOk,onErr,false,true);
}
//按搜索条件关联
function relateByCondition(){
	$(".btn_addrelation").attr("disabled",true);
	var onOk = function(data,status,xhr){
		if(data.state==0){
			layer.msg("已处理关联请求,请耐心等待", {
				icon : 1,time: 1000
			}, function() {
				$(".btn_addrelation").removeAttr("disabled");
				$('#relationmodal').modal('hide');
				/*searchChooseHotels(1);
				search();*/
			});
		}else if(data.state==1){
			layer.msg(data.msg, {
				icon : 2
			});
			$(".btn_addrelation").removeAttr("disabled");
		}
	}
	var hotelname = $("#queryform").find("#q_hotelname").val();
	var provcode = $("#queryform").find("#q_provcode").val();
	var citycode = $("#queryform").find("#q_citycode").val();
	var hoteltype = $("#queryform").find("#q_hoteltype").val();
	var busiarea = new Array();
	$.each($("input[name='q_hoteltype']:checked"), function() {
		busiarea.push($(this).val());
	});
	var feature = new Array();
	$.each($("input[name='q_feature']:checked"), function() {
		feature.push($(this).val());
	});
	var facilities = new Array();
	$.each($("input[name='q_facilities']:checked"), function() {
		facilities.push($(this).val());
	});
	var other = new Array();
	$.each($("input[name='q_other']:checked"), function() {
		other.push($(this).val());
	});


	var query = {
		'pageNo': 1,
		'pageSize': pageSize,
		'otatype': otatype,
		'hotelname': hotelname,
		'provcode': provcode,
		'citycode': citycode,
		'hoteltype': hoteltype,
		'busiarea': busiarea,
		'feature': feature,
		'facilities': facilities,
		'other': other
	};
	AjaxEx("json","POST",bathpath + "hotel/relateByCondition",query,onOk,null,false,true);
}

function chooseHotel(){
	$(".btn_addrelation").attr("disabled",true);
    var ids = "";
	$("#chooseHotelsDatabody :checkbox").each(function() {
		if ($(this).prop("checked")) {
			ids += $(this).attr("value") + ",";
		}
	});
	if (ids.length > 0) {
		ids = ids.substring(0, ids.length - 1);
		var onOk = function(data, status, xhr) {
			if(data.state==0){
				layer.msg("已处理关联请求,请耐心等待并持续刷新", {
					icon : 1,time: 3000
				}, function() {
					$(".btn_addrelation").removeAttr("disabled");
					$('#relationmodal').modal('hide');
					/*searchChooseHotels(1);*/
					search();
				});
    		}else if(data.state==1){
    			layer.msg(data.msg, {
    				icon : 2
    			});
    			$(".btn_addrelation").removeAttr("disabled");
    		}
		}
		var onErr = function(data, status, xhr) {
			layer.msg('关联失败', {
				icon : 2
			});
			$(".btn_addrelation").removeAttr("disabled");
		}
		AjaxEx("json", "POST", bathpath + "otahotel/bindrelation", {
			'ids' : ids,'otatype':otatype
		}, onOk, onErr, false,true);
	} else {
		layer.msg("请选择需要关联的酒店", {
			icon : 2
		});
		$(".btn_addrelation").removeAttr("disabled");
	}
}
function initprovince(){
	//初始化省市
	var onOk = function(data,status,xhr){
    	var provinces = data.result;
    	$("#q_provcode").append('<option provinceid="" value="">ALL</option>');
    	$.each(provinces, function (n, value) { 
    		$("#q_provcode").append('<option provinceid='+value.id+' value='+value.code+'>'+value.name+'</option>');
        });
    	changeCity();
    }
    AjaxEx("json","POST",bathpath + "province/getAllProvinces",null,onOk,null,true,false);
}
function changeCity(){
    var onOk = function(data,status,xhr){
    	var provinces = data.result;
    	$("#q_citycode").empty();
    	$("#q_citycode").append('<option value="">ALL</option>');
    	$.each(provinces, function (n, value) { 
    		$("#q_citycode").append('<option value='+value.code+'>'+value.name+'</option>');
        });
    }
	var province = $("#q_provcode").find("option:selected").attr("provinceid");
	if(province){
		AjaxEx("json","POST",bathpath+"province/getAllCitys",{'proid':province},onOk,null,true,false);
	}else{
		$("#q_citycode").empty();
		$("#q_citycode").append('<option value="">ALL</option>');
	}
}

function batchDelete() {
	var ids = "";
	$("#databody :checkbox").each(function() {
		if ($(this).prop("checked")) {
			ids += $(this).attr("value") + ",";
		}
	});
	if (ids.length > 0) {
		layer.confirm("确定删除吗？", function() {
			ids = ids.substring(0, ids.length - 1);
			var onOk = function(data, status, xhr) {
				layer.msg("删除成功", {
					icon : 1,time: 1000
				}, function() {
					search();
				});
			}
			var onErr = function(data, status, xhr) {
				layer.msg('删除失败', {
					icon : 2
				});
			}
			AjaxEx("json", "POST", bathpath + "otahotel/batchdelete", {
				'ids' : ids
			}, onOk, onErr, false,true);
		})
	} else {
		layer.msg("请选择需要删除的记录", {
			icon : 2
		});
	}
}
function del(id) {
	layer.confirm("确定删除吗？", function() {
		var onOk = function(data, status, xhr) {
			layer.msg("删除成功!", {
				icon : 1,time: 1000
			}, function() {
				search();
			});
		}
		var onErr = function(data, status, xhr) {
			layer.msg('删除失败', {
				icon : 2
			});
		}
		AjaxEx("json", "POST", bathpath + "otahotel/delete", {
			'id' : id
		}, onOk, onErr, false,true);
	})
}