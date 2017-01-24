/**
 *  js工具类
 *  jianghe
 */
/**
 * AJAX工具中间件
 * @param dataType  数据类型
 * @param methodType POST or GET
 * @param url   请求地址
 * @param params    参数
 * @param onComplete    成功回调
 * @param onError   错误回调
 * @param issync   是否同步
 * @param isloading   是否弹出全屏遮罩层
 * @constructor
 *  Modify by jianghe
 */
function AjaxEx(dataType,methodType, url, params, onComplete, onError, issync,isloading) {
	issync = !issync;// TODO 暂用为是否同步 传true 同步

	var index;
    if(isloading != undefined && isloading){
        index = layer.load(0, {shade: false});
    }
    $.ajax({
            type : methodType,
            dataType : dataType,
            url : url,
            data : params,
            async: issync,
            success : function(data, status, xhr) {
                switch (xhr.status) {
                    case 200:
                        if (onComplete == null || onComplete == undefined) {
                            // 状态为200且没有给出适用函数
                        } else {
                            if (dataType != "JSON" && dataType != 'json') {
                                // 返回数据不为json直接调用适用函数
                                onComplete(data, status, xhr);
                                if(isloading != undefined && isloading){
                                	layer.close(index);
                                }
                            } else {
                                if (data != null) {
                                    // 返回为json且返回successed为true调用适用函数
                                    onComplete(data, status, xhr);
                                    if(isloading != undefined && isloading){
	                                	layer.close(index);
	                                }
                                }
                            }
                        }
                        break;
                    default:
                        //不可预知的错误发生,请稍后再试
                }
            },
            complete : function(xhr, textStatus) {
                if (xhr.status == 200) {
                    return;
                }
                switch (xhr.status) {
                    case 400:
                        break;
                    case 401:
                        break;
                    case 403:
                        layer.alert("对不起,您没有权限访问此功能.如需访问请联系管理员.");
                        break;
                    case 404:
                        break;
                    case 500:
                        break;
                    case 503:
                        break;
                    case 0:
                        break;
                    default:
                        console.log("错误状态："+xhr.status);
                }
            }
        })

}
/**
 * 分页工具
 * @param divid   分页divid
 * @param pageNo   当前页
 * @param totalpage   总页数
 * @param method  查询方法名
 * @constructor
 *  Modify by jianghe
 */
function pagerEx(divid,pageNo,totalpage,method){
	laypage({
        cont: divid, //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
        pages: totalpage, //通过后台拿到的总页数
        curr: pageNo || 1, //当前页
        skin: 'yahei', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
        groups: 7, //连续显示分页数
        jump: function(obj, first){ //触发分页后的回调
            if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
            	method(obj.curr);
            }
        }
    });
}

//tr选中
function checkToggle(obj){
	 var $ch = $(obj);
	 if (!$ch.hasClass("selected")) {  
	       $ch.addClass("selected").addClass("danger").find(":checkbox").prop("checked", true);  
	   }  
	   else {  
	       $ch.removeClass("selected").removeClass("danger").find(":checkbox").attr("checked", false);  
	   }  
}
//全选
function checkAll(obj){
	if(obj.checked){
		$(obj).closest("table").find("tbody tr").find("input[name='checkbox1']").prop("checked", true);
	}else{
		$(obj).closest("table").find("tbody tr").find("input[name='checkbox1']").prop("checked", false);
	}
}

//检查是否是数字
function checkNum(obj) {
	if(!obj){
		return true;
	}
	var re = /^-?[1-9]+(\.\d+)?$|^-?0(\.\d+)?$|^-?[1-9]+[0-9]*(\.\d+)?$/;
	if (!re.test(obj)) {
		return false;
	} else {
		return true;
	}
}