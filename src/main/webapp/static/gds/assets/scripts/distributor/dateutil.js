var current = 0;
// 获得本周的周一
function getMonday() {
	var now = new Date();
	var currentWeek = now.getDay();
	if (currentWeek == 0) {
		currentWeek = 7;
	}

	var monday = now.getTime() - (currentWeek - 1+current*7) * 24 * 60 * 60 * 1000; // 星期一
	var new_monday = formatDate(new Date(monday));
	return new_monday;

}
function formatDate(date) {
	var myyear = date.getFullYear();
	var mymonth = date.getMonth() + 1;
	var myweekday = date.getDate();

	if (mymonth < 10) {
		mymonth = "0" + mymonth;
	}
	if (myweekday < 10) {
		myweekday = "0" + myweekday;
	}
	return (myyear + "-" + mymonth + "-" + myweekday);
}
function currentweek(){
	var week = new Array();
	var monday = getMonday();
	week[0] = monday;
	week[1] = getOverData(monday,0);
	week[2] = getOverData(monday,1);
	week[3] = getOverData(monday,2);
	week[4] = getOverData(monday,3);
	week[5] = getOverData(monday,4);
	week[6] = getOverData(monday,5);
	return week;
}
function left(){
	current+=1;
	showweek(currentweek());
	//alert(currentweek());
}
function right(){
	current-=1;
	showweek(currentweek());
	//alert(currentweek());
}
function getOverData(overTime,num)
{
    var mydata; // 日期
    var days = 28; //天数; 默认为普通年2月
    var flag = false ;  //sign
    var MonthThirtyOne = [1,3,5,7,8,10,12]; // 31天月
    var MonthThirty = [4,6,9,11];     // 30天月
   
    //-------
    if(overTime != null) {
        var dataArr = overTime.split("-"); // dataArr[0] is year,
                                           // [1] is month
                                           // [2] is day
        var year = +dataArr[0];
        var month = +dataArr[1];
        var day = +dataArr[2];
       
        for(var i = 0 ; i < MonthThirtyOne.length ; i++) {
            if(month == MonthThirtyOne[i]) {
                days = 31; // 31天
                flag = true;
                break;
            }
        }
        // 若不为31天
        if(!flag) {
            for(var i = 0 ; i < MonthThirty .length ; i++) {
                if(month == MonthThirty[i]) {
                    days = 30;
                    break;
                }
            }
        }
        if(days == 28)
            if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                 days = 29;
                
        mydata = calcData(year,month,day,days,num);
        return mydata;
    }
}


function calcData(year,month,day,days,num) {

    if(day < (days - num)){
    	var theday = day+num+1;
    	if (month < 10) {
    		month = "0" + month;
    	}
    	if (theday < 10) {
    		theday = "0" + theday;
    	}
    	return year + "-" + month + "-" + theday;
    }
    else {
        day = day + num + 1 - days ;
        month = month + 1;
        if(month > 12) {
            month = month -12;
            year = year + 1;
        }
        if (month < 10) {
        	month = "0" + month;
    	}
    	if (day < 10) {
    		day = "0" + day;
    	}
        return year + "-" + month + "-" + day;
    }
} 
