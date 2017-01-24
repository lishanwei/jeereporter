/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 异常历史Entity
 * @author aiqing.chu
 * @version 2016-09-01
 */
public class BaseZinccoatingthicknessException extends DataEntity<BaseZinccoatingthicknessException> {
	
	private static final long serialVersionUID = 1L;
	private Date logtime;		// 记录时间
	private String msvalue;		// 时间毫秒值
	private String loggroup;		// 班组
	private String prodcode;		// 生产编号
	private String gencode;		// 生成编号
	private String workmode;		// 测厚仪工作模式
	private String bandwidth;		// 带宽
	private String bandthickness;		// 带厚
	private String linespeed;		// 线速
	private String walklen;		// 钢卷行走长度
	private String zincrateunilateral;		// 单侧目标上锌量
	private String zincratetargetfront;		// 正面目标上锌量
	private String zincratetargetreverse;		// 反面目标上锌量
	private String detectionpositionfront;		// 正面检测位置
	private String detectionpositionreverse;		// 反面检测位置
	private String zincratefront;		// 正面上实时上锌量
	private String zincratereverse;		// 反面上实时上锌量
	private String flag;		// 接头信号: 0和1.
	private String zincratetargetfrontmaxval;		// 正面目标上锌量最大值
	private String zincratetargetfrontminval;		// 正面目标上锌量最小值
	private String zincratetargetfrontoffset;		// 正面上锌量偏差值
	private String zincratetargetreversemaxval;		// 反面目标上锌量最大值
	private String zincratetargetreverseminval;		// 反面目标上锌量最小值
	private String zincratetargetreverseoffset;		// 反面上锌量偏差值
	private Date beginLogtime;		// 开始 记录时间
	private Date endLogtime;		// 结束 记录时间
	
	public BaseZinccoatingthicknessException() {
		super();
	}

	public BaseZinccoatingthicknessException(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLogtime() {
		return logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}
	
	@Length(min=0, max=11, message="时间毫秒值长度必须介于 0 和 11 之间")
	public String getMsvalue() {
		return msvalue;
	}

	public void setMsvalue(String msvalue) {
		this.msvalue = msvalue;
	}
	
	@Length(min=0, max=10, message="班组长度必须介于 0 和 10 之间")
	public String getLoggroup() {
		return loggroup;
	}

	public void setLoggroup(String loggroup) {
		this.loggroup = loggroup;
	}
	
	@Length(min=0, max=50, message="生产编号长度必须介于 0 和 50 之间")
	public String getProdcode() {
		return prodcode;
	}

	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}
	
	@Length(min=0, max=50, message="生成编号长度必须介于 0 和 50 之间")
	public String getGencode() {
		return gencode;
	}

	public void setGencode(String gencode) {
		this.gencode = gencode;
	}
	
	@Length(min=0, max=10, message="测厚仪工作模式长度必须介于 0 和 10 之间")
	public String getWorkmode() {
		return workmode;
	}

	public void setWorkmode(String workmode) {
		this.workmode = workmode;
	}
	
	public String getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	public String getBandthickness() {
		return bandthickness;
	}

	public void setBandthickness(String bandthickness) {
		this.bandthickness = bandthickness;
	}
	
	public String getLinespeed() {
		return linespeed;
	}

	public void setLinespeed(String linespeed) {
		this.linespeed = linespeed;
	}
	
	public String getWalklen() {
		return walklen;
	}

	public void setWalklen(String walklen) {
		this.walklen = walklen;
	}
	
	public String getZincrateunilateral() {
		return zincrateunilateral;
	}

	public void setZincrateunilateral(String zincrateunilateral) {
		this.zincrateunilateral = zincrateunilateral;
	}
	
	public String getZincratetargetfront() {
		return zincratetargetfront;
	}

	public void setZincratetargetfront(String zincratetargetfront) {
		this.zincratetargetfront = zincratetargetfront;
	}
	
	public String getZincratetargetreverse() {
		return zincratetargetreverse;
	}

	public void setZincratetargetreverse(String zincratetargetreverse) {
		this.zincratetargetreverse = zincratetargetreverse;
	}
	
	public String getDetectionpositionfront() {
		return detectionpositionfront;
	}

	public void setDetectionpositionfront(String detectionpositionfront) {
		this.detectionpositionfront = detectionpositionfront;
	}
	
	public String getDetectionpositionreverse() {
		return detectionpositionreverse;
	}

	public void setDetectionpositionreverse(String detectionpositionreverse) {
		this.detectionpositionreverse = detectionpositionreverse;
	}
	
	public String getZincratefront() {
		return zincratefront;
	}

	public void setZincratefront(String zincratefront) {
		this.zincratefront = zincratefront;
	}
	
	public String getZincratereverse() {
		return zincratereverse;
	}

	public void setZincratereverse(String zincratereverse) {
		this.zincratereverse = zincratereverse;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getZincratetargetfrontmaxval() {
		return zincratetargetfrontmaxval;
	}

	public void setZincratetargetfrontmaxval(String zincratetargetfrontmaxval) {
		this.zincratetargetfrontmaxval = zincratetargetfrontmaxval;
	}
	
	public String getZincratetargetfrontminval() {
		return zincratetargetfrontminval;
	}

	public void setZincratetargetfrontminval(String zincratetargetfrontminval) {
		this.zincratetargetfrontminval = zincratetargetfrontminval;
	}
	
	public String getZincratetargetfrontoffset() {
		return zincratetargetfrontoffset;
	}

	public void setZincratetargetfrontoffset(String zincratetargetfrontoffset) {
		this.zincratetargetfrontoffset = zincratetargetfrontoffset;
	}
	
	public String getZincratetargetreversemaxval() {
		return zincratetargetreversemaxval;
	}

	public void setZincratetargetreversemaxval(String zincratetargetreversemaxval) {
		this.zincratetargetreversemaxval = zincratetargetreversemaxval;
	}
	
	public String getZincratetargetreverseminval() {
		return zincratetargetreverseminval;
	}

	public void setZincratetargetreverseminval(String zincratetargetreverseminval) {
		this.zincratetargetreverseminval = zincratetargetreverseminval;
	}
	
	public String getZincratetargetreverseoffset() {
		return zincratetargetreverseoffset;
	}

	public void setZincratetargetreverseoffset(String zincratetargetreverseoffset) {
		this.zincratetargetreverseoffset = zincratetargetreverseoffset;
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