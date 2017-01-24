/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 锌层测厚日志Entity
 * @author aiqing.chu
 * @version 2016-07-07
 */
public class ZinccoatingthicknessLog extends DataEntity<ZinccoatingthicknessLog> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7151018980659965802L;
	private String logtime;		// 记录时间
	private String loggroup;		// 班组
	private String prodcode;		// 生产编号
	private String gencode;		// 生成编号
	private String workmode;		// 测厚仪工作模式
	private String bandwidth;		// 带宽
	private String bandthickness;		// 带厚
	private String linespeed;		// 线速
	private String walklen;		// 钢卷行走长度
	private String zincrateunilateral;		// 单侧目标上锌量
	private Double zincratetargetfront;    // 正面目标上锌量
	private Double zincratetargetreverse;    //背面目标上锌量
	private String detectionpositionfront;		// 正面检测位置
	private String detectionpositionreverse;		// 反面检测位置
	private String zincratefront;		// 正面上实时上锌量
	private String zincratereverse;		// 反面上实时上锌量
	private Integer flag;    // 接头信号标识
	private String beginLogtime;		// 开始 记录时间
	private String endLogtime;		// 结束 记录时间
	private Double maxOffsetFront;  // 正面上锌量最大偏差值
	private Double minOffsetFront;  // 正面上锌量最小偏差值
	private Double maxOffsetReverse;  // 反面上锌量最大偏差值
	private Double minOffsetReverse;  // 反面上锌量最小偏差值
	
	
	public ZinccoatingthicknessLog() {
		super();
	}

	public ZinccoatingthicknessLog(String id){
		super(id);
	}

	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="记录时间不能为空")
	public String getLogtime() {
		return logtime;
	}

	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}
	
	@Length(min=0, max=1, message="班组长度必须介于 0 和 1 之间")
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
	
	@Length(min=0, max=1, message="测厚仪工作模式长度必须介于 0 和 1 之间")
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

	public Double getZincratetargetfront() {
		return zincratetargetfront;
	}

	public void setZincratetargetfront(Double zincratetargetfront) {
		this.zincratetargetfront = zincratetargetfront;
	}

	public Double getZincratetargetreverse() {
		return zincratetargetreverse;
	}

	public void setZincratetargetreverse(Double zincratetargetreverse) {
		this.zincratetargetreverse = zincratetargetreverse;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Double getMaxOffsetFront() {
		return maxOffsetFront;
	}

	public void setMaxOffsetFront(Double maxOffsetFront) {
		this.maxOffsetFront = maxOffsetFront;
	}

	public Double getMinOffsetFront() {
		return minOffsetFront;
	}

	public void setMinOffsetFront(Double minOffsetFront) {
		this.minOffsetFront = minOffsetFront;
	}

	public Double getMaxOffsetReverse() {
		return maxOffsetReverse;
	}

	public void setMaxOffsetReverse(Double maxOffsetReverse) {
		this.maxOffsetReverse = maxOffsetReverse;
	}

	public Double getMinOffsetReverse() {
		return minOffsetReverse;
	}

	public void setMinOffsetReverse(Double minOffsetReverse) {
		this.minOffsetReverse = minOffsetReverse;
	}

}