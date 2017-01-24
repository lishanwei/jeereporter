/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.rpt.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 配置上锌量偏差量Entity
 * @author aiqing.chu
 * @version 2016-09-01
 */
public class BaseZinccoatingthicknessConfig extends DataEntity<BaseZinccoatingthicknessConfig> {
	
	private static final long serialVersionUID = 1L;
	private String configname;		// 配置项名称
	private String zincratetargetfront;		// 正面目标上锌量
	private String zincratetargetfrontmaxval;		// 正面目标上锌量最大值
	private String zincratetargetfrontminval;		// 正面目标上锌量最小值
	private String zincratetargetreverse;		// 反面目标上锌量
	private String zincratetargetreversemaxval;		// 反面目标上锌量最大值
	private String zincratetargetreverseminval;		// 反面目标上锌量最小值
	private Date createtime;		// 创建时间
	private String createuser;		// 创建人
	private Date updatetime;		// 修改时间
	private String updateuser;		// 修改人
	
	public BaseZinccoatingthicknessConfig() {
		super();
	}

	public BaseZinccoatingthicknessConfig(String id){
		super(id);
	}

	@Length(min=1, max=50, message="配置项名称长度必须介于 1 和 50 之间")
	public String getConfigname() {
		return configname;
	}

	public void setConfigname(String configname) {
		this.configname = configname;
	}
	
	public String getZincratetargetfront() {
		return zincratetargetfront;
	}

	public void setZincratetargetfront(String zincratetargetfront) {
		this.zincratetargetfront = zincratetargetfront;
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
	
	public String getZincratetargetreverse() {
		return zincratetargetreverse;
	}

	public void setZincratetargetreverse(String zincratetargetreverse) {
		this.zincratetargetreverse = zincratetargetreverse;
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@Length(min=0, max=50, message="创建人长度必须介于 0 和 50 之间")
	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@Length(min=0, max=50, message="修改人长度必须介于 0 和 50 之间")
	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}
	
}