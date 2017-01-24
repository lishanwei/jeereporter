package com.thinkgem.jeesite.modules.rpt.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 锌层测厚周报表Entity.
 * 
 * @author aiqing.chu
 * @version 2016-07-08
 *
 */
public class ZinccoatingWeeklyReport extends DataEntity<ZinccoatingWeeklyReport> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5253236623132531369L;
	
	private Date logtime;
	private String loggroup;
	private String loggroupname;
	private String genqty;
	private Double totallen;
	private Double totalsteel;    // 过钢量(吨)
	private Double zincratetotal;    // 总上锌量（千克）
	private String beginLogtime;		// 开始 记录时间
	private String endLogtime;		// 结束 记录时间
	
	
	public Date getLogtime() {
		return logtime;
	}
	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}
	public String getLoggroup() {
		return loggroup;
	}
	public void setLoggroup(String loggroup) {
		this.loggroup = loggroup;
	}
	public String getLoggroupname() {
		return loggroupname;
	}
	public void setLoggroupname(String loggroupname) {
		this.loggroupname = loggroupname;
	}
	public String getGenqty() {
		return genqty;
	}
	public void setGenqty(String genqty) {
		this.genqty = genqty;
	}
	public Double getTotallen() {
		return totallen;
	}
	public void setTotallen(Double totallen) {
		this.totallen = totallen;
	}
	public Double getTotalsteel() {
		return totalsteel;
	}
	public void setTotalsteel(Double totalsteel) {
		this.totalsteel = totalsteel;
	}
	public Double getZincratetotal() {
		return zincratetotal;
	}
	public void setZincratetotal(Double zincratetotal) {
		this.zincratetotal = zincratetotal;
	}
	public String getBeginLogtime() {
		return beginLogtime;
	}
	public void setBeginLogtime(String beginLogtime) {
		this.beginLogtime = beginLogtime;
	}
	public String getEndLogtime() {
		return endLogtime;
	}
	public void setEndLogtime(String endLogtime) {
		this.endLogtime = endLogtime;
	}

}
