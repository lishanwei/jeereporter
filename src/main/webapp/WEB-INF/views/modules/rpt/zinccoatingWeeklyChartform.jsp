<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="default"/>
<title>周报图表展示</title>
<script type="text/javascript" src="${ctxStatic}/echarts/echarts.js"></script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="zinccoatingWeeklyReport" action="${ctx}/rpt/zinccoatingWeeklyReport/chartform" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>记录时间：</label>
				<input id="beginLogtime" name="beginLogtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${zinccoatingWeeklyReport.beginLogtime}" pattern="yyyy-MM-dd HH"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH',isShowClear:true});"/> - 
				<input id="endLogtime" name="endLogtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${zinccoatingWeeklyReport.endLogtime}" pattern="yyyy-MM-dd HH"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH',isShowClear:true});"/>
			</li>
			<li><label>班组：</label>
				<form:select id="loggroup" path="loggroup" class="input-medium">
                    <form:option value="" label="--所有班组--"/>
                    <form:options items="${fns:getDictList('RPT_WORK_TEAM')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
			</li>
			<li class="btns"><input id="btnSubmit" onclick="javascript:loadChartData();" class="btn btn-primary" type="button" value="查询"/></li>
			<li class="btns"><input id="btnBack" onclick="history.go(-1);" class="btn btn-primary" type="button" value="返回"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 100%;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '周报柱形图'
        },
        tooltip: {},
        legend: {
            data:['上锌量(千克)', '过钢量(吨)']
        },
        xAxis: {
            data: ["RS160501001","RS160501002","RS160501003"]
        },
        yAxis: {},
        series: [{
            name: '上锌量(千克)',
            type: 'bar',
            data: [5, 20, 36]
        },
        {
            name: '过钢量(吨)',
            type: 'bar',
            data: [10, 10, 20]
        }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    //myChart.setOption(option);
    
    //查询  
    function loadChartData() {
    	var now = new Date().getTime();
    	var beginLogtime = $("#beginLogtime").val();
    	var endLogtime = $("#endLogtime").val();
    	var loggroup = $("#loggroup").val();
    	var params = {"beginLogtime":beginLogtime, 
    			"endLogtime":endLogtime,
    			"loggroup":loggroup
    	};
    	console.log(params);
    	myChart.clear();  
    	myChart.showLoading({text: '正在努力的读取数据中...'});  
        $.getJSON('${ctx}/jechart/weekly/queryEchartOptionData?now='+now, params, function (data) {
            if (data.success) {
            	var dataOption = data.option;
            	option.title.text = dataOption.title.text;
            	option.legend.data = dataOption.legend.data;
            	option.xAxis.data = dataOption.xAxis[0].data;
            	
            	option.series[0].name = dataOption.series[0].name;
            	option.series[0].type = dataOption.series[0].type;
            	option.series[0].data = dataOption.series[0].data;
            	
            	option.series[1].name = dataOption.series[1].name;
            	option.series[1].type = dataOption.series[1].type;
            	option.series[1].data = dataOption.series[1].data;
            	myChart.setOption(option, true);
            	myChart.hideLoading();
            } else {
                alert('提示', data.msg);
            }  
        });  
    }  
    //载入图表  
    loadChartData();
</script>
</body>
</html>