$(function() {
	$("#postPayThresholdDiv").hide();
	App.init();
	//初始化时间选择器
	$(".form_datetime").datetimepicker({
        autoclose: true,
        isRTL: App.isRTL(),
        format: "yyyy-mm-dd hh:ii",
        pickerPosition: ("bottom-left")
    });
	//初始化省市
	var onOk = function(data,status,xhr){
    	var provinces = data.result;
    	$.each(provinces, function (n, value) { 
    		$("#provcode").append('<option provinceid='+value.id+' value='+value.code+'>'+value.name+'</option>');
        });
    	changeCity();
    }
    AjaxEx("json","POST",bathpath + "province/getAllProvinces",null,onOk,null,true,false);
    //初始化结算方式
   // initSettlementCurrency();
    //初始化渠道id
    initChannelid();
    
    var id = $.query.get("id");
	if(id){
		//登录账户名改为只读
		$("#loginaccount").attr("disabled",true);
		//结算规则改为只读
		$("#settlementCurrency").attr("disabled",true);
		$("#postPayThreshold").attr("disabled",true);
		//查看
		detailInit(id);
	}else{
		addInit();
	}
});
function changeCurrency(){
	var currency = $("#settlementCurrency").val();
	if(currency==2){
		$("#postPayThresholdDiv").show();
	}else if(currency==1){
		$("#postPayThresholdDiv").hide();
	}
}
function addInit(){
	//初始化otatype
    initOtatype();
}
function detailInit(id){
	var onOk = function(data,status,xhr){
    	var bean = data.result;
    	if('F'==bean.autoRelateSwitch){
    		$('#autoRelateSwitch').bootstrapSwitch('setState', false);
    	}else if('T'==bean.autoRelateSwitch){
    		$('#autoRelateSwitch').bootstrapSwitch('setState', true);
    	}
    	$("#otatype").val(bean.otatype);
    	$("#id").val(bean.id);
    	$("#name").val(bean.name);
    	$("#otatype").val(bean.otatype);
    	$("#loginaccount").val(bean.loginaccount);
    	$("#contact").val(bean.contact);
    	$("#contactposition").val(bean.contactposition);
    	$("#contactphone").val(bean.contactphone);
    	$("#contactemail").val(bean.contactemail);
    	$("#telephone").val(bean.telephone);
    	$("#qqNumber").val(bean.qqNumber);
    	$("#website").val(bean.website);
    	$("#faxNumber").val(bean.faxNumber);
    	if(bean.settlementCurrency==1){
    		$("#postPayThresholdDiv").hide();
    		
    	}else if(bean.settlementCurrency==2){
    		$("#postPayThresholdDiv").show();
    		$("#postPayThreshold").val(bean.postPayThreshold);
    	}
    	$("#settlementCurrency").val(bean.settlementCurrency);
    	$("#upper").val(bean.upper);
		$("#upprice").val(bean.upprice);
		$("#channelid").val(bean.channelid);
    	$("#provcode").val(bean.provcode);
    	changeCity();
    	$("#citycode").val(bean.citycode);
    	if(bean.logopath){
    		var img_logo = document.getElementById("img_logo");
        	img_logo.setAttribute("src","http://"+bean.logopath);
        	$("#logopath").val(bean.logopath);
        	//document.getElementById("pickfiles").style.display="none";
    	}else{
    		document.getElementById("img_logo").style.display="none";
    	}
    	
    	//$("#img_logo").setAttribute("src",bean.logopath);
    	$("#address").val(bean.address);
    	$("#commission").val(bean.commission);
    	$("#introduction").val(bean.introduction);
    	$("#serviceStarttimedesc").val(bean.serviceStarttimedesc);
    	$("#serviceEndtimedesc").val(bean.serviceEndtimedesc);
    	$("#bankname").val(bean.bankname);
    	$("#companyname").val(bean.companyname);
    	$("#bankaccount").val(bean.bankaccount);
    	$("#tariffno").val(bean.tariffno);
    	
    }
	var onErr = function(data, status, xhr) {
		layer.msg('获取数据失败', {
			icon : 2
		});
	}
    AjaxEx("json","POST",bathpath + "distributor/distributorinfo",{'id':id},onOk,onErr,false,false);
}
function initOtatype(){
	var onOk = function(data,status,xhr){
    	var otatype = data.result;
    	$("#otatype").val(otatype);
    }
    AjaxEx("json","POST",bathpath + "distributor/getRandomNumber",null,onOk,null,false,false);
}
function initChannelid(){
	var onOk = function(data,status,xhr){
    	var datas = data.rs;
    	$.each(datas, function (n, value) { 
    		$("#channelid").append('<option value='+value.key+'>'+value.value+'</option>');
        });
    }
    AjaxEx("json","POST",bathpath + "distributor/querychannelid",null,onOk,null,true,false);
}
/*function initSettlementCurrency(){
	var onOk = function(data,status,xhr){
    	var datas = data.result;
    	$.each(datas, function (n, value) { 
    		$("#settlementCurrency").append('<option value='+value.id+'>'+value.description+'</option>');
        });
    }
    AjaxEx("json","POST",bathpath + "distributor/settlementinfo",null,onOk,null,true,false);
}*/
/*function initDistribution(){
	var onOk = function(data,status,xhr){
    	var otatypes = data.result;
    	$.each(otatypes, function (n, value) { 
    		$("#otatype").append('<option value='+value.otatype+'>'+value.name+'</option>');
        });
    }
    AjaxEx("json","POST",bathpath + "distributor/gdstypeinfo",null,onOk,null,true,false);
}*/
function changeCity(){
    var onOk = function(data,status,xhr){
    	var provinces = data.result;
    	$("#citycode").empty();
    	$.each(provinces, function (n, value) { 
    		$("#citycode").append('<option value='+value.code+'>'+value.name+'</option>');
        });
    }
	var province = $("#provcode").find("option:selected").attr("provinceid");
	AjaxEx("json","POST",bathpath+"province/getAllCitys",{'proid':province},onOk,null,true,false);
}
function save(){
	var onOk = function(data, status, xhr) {
		if(data.result=='999'){
			layer.msg("保存成功!", {
				icon : 1,time: 1000
			}, function() {
				window.location.href = bathpath + "distributor/list";
			});
		}
		if(data.result=='1'){
			layer.msg('调用同步接口不通，请联系注册中心', {
				icon : 2
			});
		}
		if(data.result=='2'){
			layer.msg('调用同步接口不通，请联系注册中心', {
				icon : 2
			});
		}
		if(data.result=='3'){
			layer.msg('用户名和密码已存在', {
				icon : 2
			});
		}
		if(data.result=='4'){
			layer.msg('调用同步接口不通，请联系注册中心', {
				icon : 2
			});
		}
		if(data.result=='5'){
			layer.msg('本地数据保存失败', {
				icon : 2
			});
		}
	}
	var id = $("#id").val();
	var name = $("#name").val();
	if(!name){
		layer.msg('请填写分销商名称', {
			icon : 2
		});
		return;
	}
	var otatype = $("#otatype").val();
	var loginaccount = $("#loginaccount").val();
	if(!loginaccount){
		layer.msg('请填写登陆账号', {
			icon : 2
		});
		return;
	}
	var contact = $("#contact").val();
	if(!contact){
		layer.msg('请填写联系人姓名', {
			icon : 2
		});
		return;
	}
	var contactposition = $("#contactposition").val();
	var contactphone = $("#contactphone").val();
	if(!contactphone){
		layer.msg('请填写联系人手机', {
			icon : 2	
		});
		$("#contactphone").css("border","1px solid #FF0000");
		return;
	}else{
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
		if(!myreg.test(contactphone)) 
		{ 
			layer.msg('请填写有效的手机号', {
				icon : 2
			});
			$("#contactphone").css("border","1px solid #FF0000");
		    return ; 
		}else{
			$("#contactphone").css("border","1px solid #00EE00");
		} 
	}
	var contactemail = $("#contactemail").val();
	var telephone = $("#telephone").val();
	var qqNumber = $("#qqNumber").val();
	var website = $("#website").val();
	var faxNumber = $("#faxNumber").val();
	var settlementCurrency = $("#settlementCurrency").val();
	var postPayThreshold = $("#postPayThreshold").val();
	var provcode = $("#provcode").val();
	var citycode = $("#citycode").val();
	var address = $("#address").val();
	var autoRelateSwitch = $('#autoRelateSwitch').bootstrapSwitch('status')==true?'T':'F';
	if(!address){
		layer.msg('请填写详细地址', {
			icon : 2
		});
		return;
	}
	var bankname = $("#bankname").val();
	var companyname = $("#companyname").val();
	var bankaccount = $("#bankaccount").val();
	var tariffno = $("#tariffno").val();
	
	
	var logopath = $("#logopath").val();
	if(!logopath){
		layer.msg('请上传图标', {
			icon : 2
		});
		return;
	}
	var commission = $("#commission").val();
	var introduction = $("#introduction").val();
	var serviceStarttimedesc = $("#serviceStarttimedesc").val();
	var serviceEndtimedesc = $("#serviceEndtimedesc").val();
	var upper = $("#upper").val();
	var upprice = $("#upprice").val();
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
	if(settlementCurrency==2 && !checkNum(postPayThreshold)){
		layer.msg('阈值请输入数字', {
			icon : 2
		});
		return;
	}
	var channelid = $("#channelid").val();
	var param = {
		'id' : id,
		'name' : name,
		'otatype' : otatype,
		'loginaccount' : loginaccount,
		'contact' : contact,
		'contactposition' : contactposition,
		'contactphone' : contactphone,
		'contactemail' : contactemail,
		'telephone' : telephone,
		'qqNumber' : qqNumber,
		'website' : website,
		'faxNumber' : faxNumber,
		'settlementCurrency' : settlementCurrency,
		'provcode' : provcode,
		'citycode' : citycode,
		'address' : address,
		'introduction' : introduction,
		'commission' : commission,
		'serviceStarttimedesc' : serviceStarttimedesc,
		'serviceEndtimedesc' : serviceEndtimedesc,
		'bankname':bankname,
		'companyname':companyname,
		'bankaccount':bankaccount,
		'tariffno':tariffno,
		'logopath':logopath,
		'upper':upper,
		'upprice':upprice,
		'channelid':channelid,
		'postPayThreshold':postPayThreshold,
		'autoRelateSwitch':autoRelateSwitch
	};
	AjaxEx("json", "POST", bathpath + "distributor/save", param, onOk, null,
			false, true);
}
function cancel(){
	window.location.href = bathpath + "distributor/list";
}
function checkLoginaccount(){
	var onOk = function(data, status, xhr) {
		if(data.result=='999'){
			$("#loginaccount").css("border","1px solid #00EE00");
		}
		if(data.result=='1'){
			layer.msg('调用同步接口不通，请联系注册中心', {
				icon : 2
			});
		}
		if(data.result=='2'){
			layer.msg('调用同步接口不通，请联系注册中心', {
				icon : 2
			});
		}
		if(data.result=='3'){
			layer.msg('注册中心DB_ERROR，请联系注册中心', {
				icon : 2
			});
		}
		if(data.result=='4'){
			$("#loginaccount").css("border","1px solid #FF0000");
			layer.msg('登陆账号已存在', {
				icon : 2
			});
		}
		if(data.result=='0'){
			layer.msg('其它错误，请联系管理员', {
				icon : 2
			});
		}
	}
	var loginaccount = $("#loginaccount").val();
	if(!loginaccount){
		layer.msg('请填写登陆账号', {
			icon : 2
		});
		$("#loginaccount").css("border","1px solid #FF0000");
		return;
	}else{
		var myreg = /^[a-zA-Z\d]+$/; 
		if(!myreg.test(loginaccount)) 
		{ 
			layer.msg('登陆账号只能为英文字母和数字', {
				icon : 2
			});
			$("#loginaccount").css("border","1px solid #FF0000");
		    return ; 
		}else{
			var param = {
					'loginaccount' : loginaccount};
			AjaxEx("json", "POST", bathpath + "distributor/checkLoginName", param, onOk, null,
					false, true);
		}
	}
	
}
function checkname(){
	var name = $("#name").val();
	if(!name){
		layer.msg('请填写分销商名称', {
			icon : 2
		});
		$("#name").css("border","1px solid #FF0000");
		return;
	}else{
		$("#name").css("border","1px solid #00EE00");
	}
}
function checkcontact(){
	var contact = $("#contact").val();
	if(!contact){
		layer.msg('请填写联系人姓名', {
			icon : 2
		});
		$("#contact").css("border","1px solid #FF0000");
		return;
	}else{
		$("#contact").css("border","1px solid #00EE00");
	}
}
function checkcontactphone(){
	
	var contactphone = $("#contactphone").val();
	if(!contactphone){
		layer.msg('请填写联系人手机', {
			icon : 2
		});
		$("#contactphone").css("border","1px solid #FF0000");
		return;
	}else{
		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
		if(!myreg.test(contactphone)) 
		{ 
			layer.msg('请填写有效的手机号', {
				icon : 2
			});
			$("#contactphone").css("border","1px solid #FF0000");
		    return ; 
		}else{
			$("#contactphone").css("border","1px solid #00EE00");
		} 
	}
}
function checkaddress(){
	var address = $("#address").val();
	if(!address){
		layer.msg('请填写详细地址', {
			icon : 2
		});
		$("#address").css("border","1px solid #FF0000");
		return;
	}else{
		$("#address").css("border","1px solid #00EE00");
	}
}

function checkbankname(){
	var bankname = $("#bankname").val();
	if(!bankname){
		layer.msg('请填写开户行', {
			icon : 2
		});
		$("#bankname").css("border","1px solid #FF0000");
		return;
	}else{
		$("#bankname").css("border","1px solid #00EE00");
	}
}

function checkcompanyname(){
	var companyname = $("#companyname").val();
	if(!companyname){
		layer.msg('请填写开户行单位全称', {
			icon : 2
		});
		$("#companyname").css("border","1px solid #FF0000");
		return;
	}else{
		$("#companyname").css("border","1px solid #00EE00");
	}
}

function checkbankaccount(){
	var bankaccount = $("#bankaccount").val();
	if(!bankaccount){
		layer.msg('请填写账号', {
			icon : 2
		});
		$("#bankaccount").css("border","1px solid #FF0000");
		return;
	}else{
		$("#bankaccount").css("border","1px solid #00EE00");
	}
}
function checktariffno(){
	var tariffno = $("#tariffno").val();
	if(!tariffno){
		layer.msg('请填写税号', {
			icon : 2
		});
		$("#tariffno").css("border","1px solid #FF0000");
		return;
	}else{
		$("#tariffno").css("border","1px solid #00EE00");
	}
}
