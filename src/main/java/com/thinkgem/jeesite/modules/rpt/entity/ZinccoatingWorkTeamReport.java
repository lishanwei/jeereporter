package com.thinkgem.jeesite.modules.rpt.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 锌层测厚班报表Entity.
 * 
 * @author aiqing.chu
 * @version 2017-07-07
 *
 */
public class ZinccoatingWorkTeamReport extends DataEntity<ZinccoatingWorkTeamReport> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8577779633531787114L;
	
	private Date logtime;
	private String loggroup;
	private String loggroupname;
	private String prodcode;
	private String gencode;
	private String workmode;
	private String workmodename;
	private Double bandwidth;
	private Double bandthickness;
	private Double linespeed;
	private Double totallen;
	private Double totalsteel;    // 过钢量(吨)
	private Double zincratetarget;    // 目标上锌量(克)
	private Double zincratetargetfront;    // 正面目标上锌量(克)
    private Double zincratetargetreverse;    // 反面目标上锌量(克)
	private Double zincrateavgfront;    // 正面平米上锌量平均值(克)
	private Double zincrateavgreverse;    // 反面平米上锌量平均值(克)
	private Double zinctotalinc;    // 上锌总增重(千克)
	private Double zinctotalincfront;    // 正面上锌增重(千克)
	private Double zinctotalincreverse;    // 反面上锌增重(千克)
	private Date beginLogtime;		// 开始 记录时间
	private Date endLogtime;		// 结束 记录时间
	
	/** getters and setters */
	
	@ExcelField(title="记录时间", align=2, sort=10)
	public Date getLogtime() {
		return logtime;
	}
	
	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}
	
	@ExcelField(title="班组", align=2, sort=20)
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
	
	@ExcelField(title="生成编号", align=1, sort=30)
	public String getProdcode() {
		return prodcode;
	}
	
	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}
	
	@ExcelField(title="生成编号", align=1, sort=40)
	public String getGencode() {
		return gencode;
	}
	
	public void setGencode(String gencode) {
		this.gencode = gencode;
	}
	
	@ExcelField(title="测厚仪工作模式", align=2, sort=50)
	public String getWorkmode() {
		return workmode;
	}
	
	public void setWorkmode(String workmode) {
		this.workmode = workmode;
	}
	
	public String getWorkmodename() {
		return workmodename;
	}
	
	public void setWorkmodename(String workmodename) {
		this.workmodename = workmodename;
	}
	
	@ExcelField(title="带宽", align=3, sort=60)
	public Double getBandwidth() {
		return bandwidth;
	}
	
	public void setBandwidth(Double bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	@ExcelField(title="带厚", align=3, sort=70)
	public Double getBandthickness() {
		return bandthickness;
	}
	
	public void setBandthickness(Double bandthickness) {
		this.bandthickness = bandthickness;
	}
	
	@ExcelField(title="平均线速", align=3, sort=80)
	public Double getLinespeed() {
		return linespeed;
	}
	
	public void setLinespeed(Double linespeed) {
		this.linespeed = linespeed;
	}
	
	@ExcelField(title="钢卷总长", align=3, sort=90)
	public Double getTotallen() {
		return totallen;
	}
	
	public void setTotallen(Double totallen) {
		this.totallen = totallen;
	}
	
	@ExcelField(title="过钢量（吨）", align=3, sort=100)
	public Double getTotalsteel() {
		return totalsteel;
	}
	
	public void setTotalsteel(Double totalsteel) {
		this.totalsteel = totalsteel;
	}
	
	public Double getZincratetarget() {
		return zincratetarget;
	}
	
	public void setZincratetarget(Double zincratetarget) {
		this.zincratetarget = zincratetarget;
	}
	
	@ExcelField(title="正面目标上锌量（克）", align=3, sort=110)
	public Double getZincratetargetfront() {
		return zincratetargetfront;
	}

	public void setZincratetargetfront(Double zincratetargetfront) {
		this.zincratetargetfront = zincratetargetfront;
	}

	@ExcelField(title="反面目标上锌量（克）", align=3, sort=111)
	public Double getZincratetargetreverse() {
		return zincratetargetreverse;
	}

	public void setZincratetargetreverse(Double zincratetargetreverse) {
		this.zincratetargetreverse = zincratetargetreverse;
	}

	@ExcelField(title="正面平米上锌量平均值（克）", align=3, sort=130)
	public Double getZincrateavgfront() {
		return zincrateavgfront;
	}
	
	public void setZincrateavgfront(Double zincrateavgfront) {
		this.zincrateavgfront = zincrateavgfront;
	}
	
	@ExcelField(title="反面平米上锌量平均值（克）", align=3, sort=131)
	public Double getZincrateavgreverse() {
		return zincrateavgreverse;
	}
	
	public void setZincrateavgreverse(Double zincrateavgreverse) {
		this.zincrateavgreverse = zincrateavgreverse;
	}
	
	@ExcelField(title="上锌总增重（千克）", align=3, sort=140)
	public Double getZinctotalinc() {
		return zinctotalinc;
	}
	
	public void setZinctotalinc(Double zinctotalinc) {
		this.zinctotalinc = zinctotalinc;
	}
	
	@ExcelField(title="正面上锌增重（千克）", align=3, sort=141)
	public Double getZinctotalincfront() {
		return zinctotalincfront;
	}
	
	public void setZinctotalincfront(Double zinctotalincfront) {
		this.zinctotalincfront = zinctotalincfront;
	}
	
	@ExcelField(title="反面上锌增重（千克）", align=3, sort=142)
	public Double getZinctotalincreverse() {
		return zinctotalincreverse;
	}
	
	public void setZinctotalincreverse(Double zinctotalincreverse) {
		this.zinctotalincreverse = zinctotalincreverse;
	}
	
	public Date getBeginLogtime() {
		return beginLogtime;
	}

	public void setBeginLogtime(Date beginLogtime) {
		this.beginLogtime = beginLogtime;
	}
	
	public Date getEndLogtime() {
		return endLogtime;
	}

	public void setEndLogtime(Date endLogtime) {
		this.endLogtime = endLogtime;
	}

}
